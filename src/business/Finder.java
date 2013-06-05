/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author M
 */
public class Finder {
    protected Store store;
    protected String name;
    protected String producer;
    protected Date productionDate;
    protected long upperPrice;
    protected long lowerPrice;

    protected ArrayList<Product> result;
    
    /**
     * @param store      online store
     * @param name       product's title
     * @param producer   product's producer
     * @param date       production date
     * @param upperPrice upper price from range
     * @param lowerPrice lower price from range
     * @throws IllegalArgumentException if upperPrice is less than lowerPrice or
     * upperPrice or lowerPrice is negative
     * @see business.ProductCategory
     * @see java.util.GregorianCalendar
     */
    public Finder(Store store, String name, String producer, 
	    Date date, long upperPrice, long lowerPrice) 
	    throws IllegalArgumentException {
	if (upperPrice < lowerPrice || upperPrice < 0 || lowerPrice < 0) {
	    throw new IllegalArgumentException();
	}
	this.store = store;
	this.name = name;
	this.producer = producer;
	this.productionDate = date;
	this.upperPrice = upperPrice;
	this.lowerPrice = lowerPrice;
	
	this.result = new ArrayList();
    }

    
    public boolean find() throws IOException {
	String searchString = name.replace(' ', '+');
	if (producer != null && !producer.isEmpty()) {
	    searchString += "+" + producer.replace(' ', '+');
	}
	Document doc = Jsoup.connect(getStore().getSearchUrl() + searchString).userAgent("Mozilla").get();
	
	Element searchResults = doc.select("div.SearchResults").first();
	Elements items = searchResults.select("div.bOneTile");
	
	if (items.isEmpty()) {
	    return false;
	}
	
	for (Element item : items) {
	    Element div_price = item.select("div.bOzonPrice").first();
	    String price;
	    if (div_price.childNodeSize() < 2) {
		continue;
	    } else {
		price = div_price.select("span.eOzonPrice_main").text();
		price += "." + div_price.select("span.eOzonPrice_submain").text();
	    }
	    
	    Element generalInfo = item.select("a.bOneTile_link").first();
	    String title = generalInfo.text();
	    String link = store.getUrl() + generalInfo.attr("href");
	    String img = item.select("img[src]").first().attr("src");
	    if (!img.matches("http://.*")) {
		img = "http://" + img;
	    }
	    
	    Elements properties = item.select("div.bOneTilePropety");
	    ArrayList<String> propList = new ArrayList<>();
	    String year = "";
	    for (Element prop : properties) {
		String text = prop.text();
		if (text.matches(".*\\([0-9]+\\).*")) {
		    int beginIndex = text.indexOf('(');
		    propList.add(text.substring(0, beginIndex));
		    year = text.substring(beginIndex + 1, text.indexOf(')'));
		} else {
		    propList.add(text);
		}
	    }
	    
	    result.add(new Product(result.size(), title, "", new URL(link), 
		    new URL(img), year, price, propList));
	}

	return true;
    }

    public String getName() {
	return name;
    }

    public String getProducer() {
	return producer;
    }

    public Date getProductionDate() {
	return productionDate;
    }

    public long getUpperPrice() {
	return upperPrice;
    }

    public long getLowerPrice() {
	return lowerPrice;
    }

    public Store getStore() {
	return store;
    }

    public void setStore(Store store) {
	this.store = store;
    }

    public ArrayList<Product> getResult() {
	return result;
    }
    
}

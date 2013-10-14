/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import logic.Category;
import logic.Product;
import logic.Store;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author M
 */
public class XMLReader {
    
    public static Map<String, Boolean> loadSettings(String filename) 
	    throws ParserConfigurationException, IOException, SAXException {
	HashMap<String, Boolean> stores = new HashMap<>();
	
	File xmlf = new File(filename);
	DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document dom = db.parse(xmlf);
	
	Element root = dom.getDocumentElement();
	root.normalize();
	NodeList nodeList = root.getElementsByTagName("store");
	for (int i = 0; i < nodeList.getLength(); i++) {
	    Node node = nodeList.item(i);
	    if (node instanceof Element) {
		Element storeNode = (Element) node;
		String name = storeNode.getAttribute("name");
		boolean isSelected = storeNode.getAttribute("selected").equalsIgnoreCase("true");
		stores.put(name, isSelected);
	    }
	}
	
	return stores;
    }
    
    public static Map<String, Double> getExchangeRateMap(InputStream is) 
	    throws ParserConfigurationException, IOException, SAXException {
	double defaultCurrencyRate = -1;
	HashMap<String, Double> result = new HashMap<>();
	String defaultCurrency = NumberFormat.getCurrencyInstance().getCurrency().getCurrencyCode();
	
	DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document dom = db.parse(is);
	
	Element root = dom.getDocumentElement();
	root.normalize();
	NodeList nodeList = root.getElementsByTagName("Cube");
	for (int i = 0; i < nodeList.getLength(); i++) {
	    Node node = nodeList.item(i);
	    if (node instanceof Element) {
		Element storeNode = (Element) node;
		String curr = storeNode.getAttribute("currency");
		if (curr.isEmpty()) {
		    continue;
		}
		double rate = Double.valueOf(storeNode.getAttribute("rate"));
		result.put(curr, rate);
		if (defaultCurrency.equalsIgnoreCase(curr)) {
		    defaultCurrencyRate = rate;
		}
	    }
	}
	if (defaultCurrencyRate != -1) {
	    result.put("EUR", 1.0);
	    for (Entry<String, Double> entry : result.entrySet()) {
		entry.setValue(defaultCurrencyRate / entry.getValue());
	    }
	}
	return result;
    }

    public static List<Product> loadProducts(File file)
	    throws ParserConfigurationException, SAXException, IOException, ParseException, SQLException {
	ArrayList<Product> products = new ArrayList<>();
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
	
	DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document dom = db.parse(file);
	
	Element root = dom.getDocumentElement();
	root.normalize();
	NodeList nodeList = root.getElementsByTagName("product");
	for (int i = 0; i < nodeList.getLength(); i++) {
	    Node node = nodeList.item(i);
	    if (node instanceof Element) {
		Element product = (Element) node;
		
		Element elem = (Element) product.getElementsByTagName("id").item(0);
		Long id = Long.parseLong(elem.getTextContent());
		elem = (Element) product.getElementsByTagName("url").item(0);
		URL url = new URL(elem.getTextContent());
		elem = (Element) product.getElementsByTagName("image").item(0);
		URL image = new URL(elem.getTextContent());
		elem = (Element) product.getElementsByTagName("store").item(0);
		Store store = StoreService.getStore(elem.getTextContent());
		elem = (Element) product.getElementsByTagName("name").item(0);
		String name = elem.getTextContent();
		elem = (Element) product.getElementsByTagName("category").item(0);
		Category category = CategoryService.findCategory(elem.getTextContent());
		elem = (Element) product.getElementsByTagName("producer").item(0);
		String producer = elem.getTextContent();
		elem = (Element) product.getElementsByTagName("productionDate").item(0);
		Date productionDate = sdf.parse(elem.getTextContent());
		elem = (Element) product.getElementsByTagName("price").item(0);
		Double price = Double.parseDouble(elem.getTextContent());
		
		NodeList propertyList = product.getElementsByTagName("property");
		HashMap<String, String> properties = new HashMap<>();
		if (propertyList != null && propertyList.getLength() > 0) {
		    for (int j = 0; j < propertyList.getLength(); j++) {
			Element property = (Element) propertyList.item(i);
			
			elem = (Element) property.getElementsByTagName("name").item(0);
			String propName = elem.getTextContent();
			elem = (Element) property.getElementsByTagName("value").item(0);
			String propValue = elem.getTextContent();
			
			properties.put(propName, propValue);
		    }
		}
		
		products.add(new Product(id, url, image, store, name, category, producer,
			productionDate, price, properties, null));

	    }
	}

	return products;
    }
}

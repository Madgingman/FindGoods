/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import java.net.URL;
import java.util.ArrayList;

/**
 *
 * @author M
 */
public class Product {
    protected long id;
    protected URL url;
    protected URL image;
    protected String name;
    protected String producer;
    protected String productionDate;
    protected String price;
    protected ArrayList<String> properties;
    
    /**
     * @throws IllegalArgumentException if any argument is null
     */
    Product(long id, String name, String producer, String date) 
	    throws IllegalArgumentException {
	if (name == null || producer == null || date == null) {
	    throw new IllegalArgumentException();
	}
	
	this.id = id;
	this.name = name;
	this.producer = producer;
	this.productionDate = date;
    }
    /**
     * @throws IllegalArgumentException if name, producer or date is null
     */
    Product(long id, String name, String producer, URL url, URL image, 
	    String date, String price, ArrayList<String> properties) 
	    throws IllegalArgumentException {
	if (name == null || producer == null || date == null) {
	    throw new IllegalArgumentException();
	}
	
	this.id = id;
	this.url = url;
	this.image = image;
	this.name = name;
	this.producer = producer;
	this.productionDate = date;
	this.price = price;
	this.properties = new ArrayList<>();
	this.properties.addAll(properties);
    }
    
    @Override
    public String toString() {
	String product = "";

	product += id + "\n";
	product += name + "\n";
	product += url.toString() + "\n";
	product += image.toString() + "\n";
	product += productionDate + "\n";
	product += price + "\n";
	for (String prop : properties) {
	    product += prop + "\n";
	}

	return product;
    }

    public long getId() {
	return id;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getProducer() {
	return producer;
    }

    public void setProducer(String producer) {
	this.producer = producer;
    }

    public String getProductionDate() {
	return productionDate;
    }

    public void setProductionDate(String productionDate) {
	this.productionDate = productionDate;
    }

    public String getPrice() {
	return price;
    }

    public void setPrice(String price) {
	this.price = price;
    }

    public URL getUrl() {
	return url;
    }

    public void setUrl(URL url) {
	this.url = url;
    }

    /**
     * @return the image
     */
    public URL getImage() {
	return image;
    }

    /**
     * @param image the image to set
     */
    public void setImage(URL image) {
	this.image = image;
    }
}

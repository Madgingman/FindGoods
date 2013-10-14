/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.io.Serializable;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author M
 */
public class Product implements Serializable {
    protected long id;
    protected URL url;
    protected URL image;
    protected Store store;
    protected String name;
    protected Category category;
    protected String producer;
    protected Date productionDate;
    protected Double price;
    protected HashMap<String, String> properties;
    protected HashSet<Long> ratedUsers;
    
    /**
     * @throws IllegalArgumentException if name, producer or date is null
     */
    public Product(long id, URL url, URL image, Store store, String name, Category category, String producer, 
	    Date date, Double price, Map<String, String> properties, Set<Long> usersRated) 
	    throws IllegalArgumentException {
	if (url == null || store == null || name == null || category == null || price == null) {
	    throw new IllegalArgumentException();
	}
	
	this.id = id;
	this.url = url;
	this.image = image;
	this.store = store;
	this.name = name;
	this.category = category;
	this.producer = producer;
	this.productionDate = date;
	this.price = price;
	
	this.properties = new HashMap<>();
	if (properties != null) {
	    this.properties.putAll(properties);
	}
	
	this.ratedUsers = new HashSet<>();
	if (usersRated != null) {
	    this.ratedUsers.addAll(usersRated);
	}
    }
    
    @Override
    public String toString() {
	StringBuilder product = new StringBuilder();

	product.append(id).append("\n");
	product.append(url.toString()).append("\n");
	if (image != null) {
	    product.append(image.toString()).append("\n");
	}
	product.append(store.getName()).append("\n");
	product.append(name).append("\n");
	if (producer != null) {
	    product.append(producer).append("\n");
	}
	if (productionDate != null) {
	    product.append(productionDate.toString()).append("\n");
	}
	product.append(price).append("\n");
	if (properties != null) {
	    for (Map.Entry<String, String> prop : properties.entrySet()) {
		product.append(prop.getKey()).append(": ").append(prop.getValue()).append("\n");
	    }
	}

	return product.toString();
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

    public Date getProductionDate() {
	return productionDate;
    }

    public void setProductionDate(Date productionDate) {
	this.productionDate = productionDate;
    }

    public Double getPrice() {
	return price;
    }

    public void setPrice(Double price) {
	this.price = price;
    }

    public URL getUrl() {
	return url;
    }

    public void setUrl(URL url) {
	this.url = url;
    }

    public URL getImage() {
	return image;
    }

    public void setImage(URL image) {
	this.image = image;
    }

    public Store getStore() {
	return store;
    }

    public void setStore(Store store) {
	this.store = store;
    }

    public HashMap<String, String> getProperties() {
	return properties;
    }
    
    public void addAllProperties(Map<String, String> properties) {
	this.properties.putAll(properties);
    }
    
    public void addProperty(String name, String value) {
	this.properties.put(name, value);
    }
    
    public void removeProperty(String name) {
	this.properties.remove(name);
    }
    
    public void clearProperties() {
	this.properties.clear();
    }

    public Set<Long> getRatedUsers() {
	return ratedUsers;
    }
    
    public void addAllRatedUsers(Set<Long> usersRated) {
	this.ratedUsers.addAll(usersRated);
    }
    
    public void addRatedUser(Long userId) {
	this.ratedUsers.add(userId);
    }
    
    public void removeRatedUser(Long userId) {
	this.ratedUsers.remove(userId);
    }
    
    public void clearRatedUsers() {
	this.ratedUsers.clear();
    }

    public Element toXML(Document dom) throws ParserConfigurationException {
	Element product = dom.createElement("product");
	Element childElement;
	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
	
	childElement = dom.createElement("id");
	childElement.setTextContent("" + this.id);
	product.appendChild(childElement);
	
	childElement = dom.createElement("url");
	childElement.setTextContent(this.url.toExternalForm());
	product.appendChild(childElement);
	
	childElement = dom.createElement("image");
	childElement.setTextContent(this.image.toExternalForm());
	product.appendChild(childElement);
	
	childElement = dom.createElement("store");
	childElement.setTextContent(this.store.name);
	product.appendChild(childElement);
	
	childElement = dom.createElement("name");
	childElement.setTextContent(this.name);
	product.appendChild(childElement);
	
	childElement = dom.createElement("category");
	childElement.setTextContent(this.getCategory().name);
	product.appendChild(childElement);
	
	childElement = dom.createElement("producer");
	childElement.setTextContent(this.producer);
	product.appendChild(childElement);
	
	childElement = dom.createElement("productionDate");
	childElement.setTextContent(sdf.format(this.productionDate));
	product.appendChild(childElement);
	
	childElement = dom.createElement("price");
	childElement.setTextContent("" + this.price);
	product.appendChild(childElement);
	
	if (this.properties == null || this.properties.isEmpty()) {
	    return product;
	}
	
	for (Map.Entry<String, String> property : this.properties.entrySet()) {
	    Element prop = dom.createElement("property");
	    
	    childElement = dom.createElement("name");
	    childElement.setTextContent(property.getKey());
	    prop.appendChild(childElement);
	    
	    childElement = dom.createElement("value");
	    childElement.setTextContent(property.getValue());
	    prop.appendChild(childElement);
	    
	    product.appendChild(prop);
	}
	
	return product;
    }

    /**
     * @return the category
     */
    public Category getCategory() {
	return category;
    }

    /**
     * @param category the category to set
     */
    public void setCategory(Category category) {
	this.category = category;
    }
    
}

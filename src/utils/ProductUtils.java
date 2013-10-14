/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import db.ProductFilter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import logic.Product;
import logic.Store;

/**
 *
 * @author M
 */
public class ProductUtils {

    public static Product getProductFromList(Collection<? extends Product> products, Long productId) {
	for (Product product : products) {
	    if (product.getId() == productId) {
		return product;
	    }
	}
	
	return null;
    }
    
    public static SortedMap<String, String> getProductAsPropertyMap(Product product) {
	TreeMap<String, String> properties = new TreeMap<>();
	properties.put("Наименование", product.getName());
	properties.put("Категория", product.getCategory().getName());
	properties.put("Ссылка", product.getUrl().toExternalForm());
	properties.put("Магазин", product.getStore().getName());
	
	if (product.getProducer() != null) {
	    properties.put("Производитель", product.getProducer());
	}
	
	if (product.getProductionDate() != null) {
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
	    properties.put("Дата производства", sdf.format(product.getProductionDate()));
	}
	
	properties.put("Цена", "" + product.getPrice());
	
	if (product.getProperties() != null && !product.getProperties().isEmpty()) {
	    for (Map.Entry<String, String> property : product.getProperties().entrySet()) {
		properties.put(property.getKey(), property.getValue());
	    }
	}
	
	return properties;
    }
    
    public static List<Product> filterProducts(List<Product> products, ProductFilter filter) {
	if (products == null || products.isEmpty()) {
	    return products;
	}
	
	ArrayList<Product> result = new ArrayList<>();
	
	Long categoryId = filter.getCategoryId();
	String producer = filter.getProducer();
	Double lowerPrice = filter.getLowerPrice();
	Double upperPrice = filter.getUpperPrice();
	
	Date lowerDate = filter.getLowerDate();
	Date upperDate = filter.getUpperDate();
	
	for (Product product : products) {
	    if (categoryId != null && product.getCategory().getId() != categoryId) {
		continue;
	    }
	    
	    if (producer != null && !producer.isEmpty() && !product.getProducer().equals(producer)) {
		continue;
	    }
	    
	    if (lowerPrice != null || upperPrice != null) {
		if (lowerPrice < 0.0 && upperPrice >= 0.0 && product.getPrice() > upperPrice) {
		    continue;
		} else if (upperPrice < 0.0 && lowerPrice >= 0.0 && product.getPrice() < lowerPrice) {
		    continue;
		} else if (product.getPrice() < lowerPrice || product.getPrice() > upperPrice) {
		    continue;
		}
	    }
	    
	    if (lowerDate != null || upperDate != null) {
		if (lowerDate == null && upperDate != null && product.getProductionDate().after(upperDate)) {
		    continue;
		} else if (lowerDate != null && upperDate == null && product.getProductionDate().before(lowerDate)) {
		    continue;
		} else if (product.getProductionDate().before(lowerDate) || product.getProductionDate().after(upperDate)) {
		    continue;
		}
	    }
	    
	    boolean failed = true;
	    for (Store store : filter.getStores()) {
		if (product.getStore().getId() == store.getId()) {
		    failed = false;
		    break;
		}
	    }
	    
	    if (!failed) {
		result.add(product);
	    }
	}
	
	return result;
    }
    
}

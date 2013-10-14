/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import db.ProductFilter;
import db.ProductMapper;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import logic.Product;
import utils.StringComparator;

/**
 *
 * @author M
 */
public class ProductService {
    
    public static Product getProduct(Long productId) throws SQLException {
	return new ProductMapper().find(productId);
    }
    
    public static List<Product> findProducts(ProductFilter filter) throws SQLException {
	return new ProductMapper().find(filter);
    }
    
    public static List<Product> findProducts(ProductFilter filter, Double accuracy) throws SQLException {
	String name = filter.getName();
	if (name == null) {
	    return new ArrayList<>();
	}
	ArrayList<Product> prodList = new ArrayList<>();
	if (accuracy.equals(1.0)) {
	    prodList.addAll(findProducts(filter));
	    return prodList;
	}
	
	filter.setName("");
	prodList.addAll(findProducts(filter));
	return selectSimilarProducts(prodList, name, accuracy);
    }

    private static List<Product> selectSimilarProducts(ArrayList<Product> prodList, 
	    String name, Double accuracy) {
	List<Product> products = new ArrayList<>();
	double factor = StringComparator.compare(name, name);
	double delta = Math.abs(factor * (1.0 - accuracy));
	
	for (Product p : prodList) {
	    String prodName = p.getName();
	    
	    int weight = 0;
	    String[] words = name.split(" ");
	    for (String w : words) {
		if (prodName.toLowerCase().indexOf(w.toLowerCase()) != -1) {
		    weight++;
		}
	    }
	    
	    if (weight >= (words.length / 3) && weight != 0) {
		products.add(p);
		continue;
	    }
	    Double prodDelta = Math.abs(StringComparator.compare(name, prodName) - factor);
	    if (prodDelta.compareTo(delta) <= 0) {
		products.add(p);
	    }
	}
	return products;
    }

    public static Double getRating(long productId) throws SQLException {
	return (new ProductMapper().getRating(productId));
    }
    
    public static Double rateProduct(Long productId, Long userId, int rate) throws SQLException {
	ProductMapper mapper = new ProductMapper();
	mapper.addRate(productId, userId, rate);
	return mapper.getRating(productId);
    }
    
}

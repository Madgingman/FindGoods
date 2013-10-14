/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import db.ProductFilter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import logic.Category;
import logic.Product;
import logic.Store;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author M
 */
public class ProductUtilsTest {
    
    public ProductUtilsTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getProductFromList method, of class ProductUtils.
     */
    @Test
    public void testGetProductFromList() {
	System.out.println("GetProductFromList test");
	
	ArrayList<Product> products = new ArrayList<>();
	for (int i = 0; i < 5; i++) {
	    products.add(getRandomProduct(new Long(i)));
	}
	
	Long productId = 3L;
	Product result = ProductUtils.getProductFromList(products, productId);
	assertNotNull("Product with ID = " + productId + " wasn't found", result);
	assertTrue("Expected product with ID = " + productId + ", got product with ID = " + result.getId(),
		result.getId() == productId);
    }

    /**
     * Test of getProductAsPropertyMap method, of class ProductUtils.
     */
    @Test
    public void testGetProductAsPropertyMap() {
	System.out.println("GetProductAsPropertyMap test");
	
	Product product = getRandomProduct(0L);
	SortedMap result = ProductUtils.getProductAsPropertyMap(product);
	
	TreeMap<String, String> expResult = new TreeMap<>();
	expResult.put("Наименование", product.getName());
	expResult.put("Категория", product.getCategory().getName());
	expResult.put("Ссылка", product.getUrl().toExternalForm());
	expResult.put("Магазин", product.getStore().getName());
	expResult.put("Производитель", product.getProducer());
	expResult.put("Цена", "" + product.getPrice());
	
	assertEquals(expResult, result);
    }

    /**
     * Test of filterProducts method, of class ProductUtils.
     */
    @Test
    public void testFilterProducts() {
	System.out.println("filterProducts");
	
	ArrayList<Product> products = new ArrayList<>();
	for (int i = 0; i < 5; i++) {
	    products.add(getRandomProduct(new Long(i)));
	}
	
	ProductFilter filter = new ProductFilter(Arrays.asList(products.get(0).getStore()),
		null, null, null, null, null, null, null);
	
	List expResult = Arrays.asList(products.get(0));
	List result = ProductUtils.filterProducts(products, filter);
	
	assertEquals(expResult, result);
    }
    
    private Product getRandomProduct(Long id) {
	try {
	    URL url = new URL("http://www.google.com");
	    Store store = new Store(id, "testStoreName" + id, url, url, "testFileName");
	    Category category = new Category(id, "testCategoryName" + id);
	    
	    return new Product(id, url, null, store, "testProductName" + id, category,
		    "testProducer" + id, null, 100.0 + id,
		    Collections.<String, String>emptyMap(), Collections.<Long>emptySet());
	} catch (MalformedURLException ex) {
	    fail("Unexpected exception: " + ex.getLocalizedMessage());
	}
	
	return null;
    }
}

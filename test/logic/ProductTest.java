/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
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
public class ProductTest {
    private Product product;
    
    public ProductTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
	System.out.println("Testing Product class");
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
	System.out.print("...testing method Product#");
	
	final Long id = 0L;
	final String name = "testProductName";
	final String producer = "testProducer";
	final Double price = 100.0;
	final HashMap<String, String> properties = new HashMap<>();

	try {
	    URL url = new URL("http://www.google.com");
	    Store store = new Store(id, "testStoreName", url, url, "testFileName");
	    Category category = new Category(id, "testCategoryName");
	    product = new Product(id, url, null, store, name, category, producer, null, price,
		    Collections.<String, String>emptyMap(), Collections.<Long>emptySet());
	} catch (MalformedURLException ex) {
	    fail("Unexpected exception: " + ex.getLocalizedMessage());
	}
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of toString method, of class Product.
     */
    @Test
    public void testToString() {
	System.out.println("toString");

	try {
	    final Long id = 0L;
	    final String name = "testProductName";
	    final String producer = "testProducer";
	    final Double price = 100.0;
	    final HashMap<String, String> properties = new HashMap<>();
	    properties.put("testPropertyName", "testPropertyValue");
	    
	    URL url = new URL("http://www.google.com");
	    Store store = new Store(id, "testStoreName", url, url, "testFileName");
	    Category category = new Category(id, "testCategoryName");
	    Product product2 = new Product(id, url, null, store, name,
		    category, producer, null, price, properties, null);
	    final String result = product2.toString();

	    StringBuilder expResult = new StringBuilder();
	    expResult.append(id).append("\n");
	    expResult.append(url.toString()).append("\n");
	    expResult.append(store.getName()).append("\n");
	    expResult.append(name).append("\n");
	    expResult.append(producer).append("\n");
	    expResult.append(price).append("\n");
	    for (Map.Entry<String, String> prop : properties.entrySet()) {
		expResult.append(prop.getKey()).append(": ").append(prop.getValue()).append("\n");
	    }
	    
	    assertEquals(expResult.toString(), result);
	} catch (MalformedURLException ex) {
	    fail("Unexpected exception: " + ex.getLocalizedMessage());
	}
    }

    /**
     * Test of addAllProperties method, of class Product.
     */
    @Test
    public void testAddAllProperties() {
	System.out.println("addAllProperties");
	
	HashMap<String, String> properties = new HashMap<>();
	properties.put("testKey", "testValue");

	product.clearProperties();
	product.addAllProperties(properties);
	
	assertTrue("Expected " + properties.size() + " product properties, got " + product.getProperties().size(),
		product.getProperties().size() == properties.size());
	assertTrue("Expected and got properties don't match",
		product.getProperties().equals(properties));
    }

    /**
     * Test of addProperty method, of class Product.
     */
    @Test
    public void testAddProperty() {
	System.out.println("addProperty");
	
	final String key = "testKey1";
	final String value = "testValue1";
	product.addProperty(key, value);
	assertTrue("New property wasn't added to product properties",
		product.getProperties().get(key) != null && product.getProperties().get(key).equals(value));
    }

    /**
     * Test of removeProperty method, of class Product.
     */
    @Test
    public void testRemoveProperty() {
	System.out.println("removeProperty");
	
	final String key = "testKey1";
	final String value = "testValue1";
	product.addProperty(key, value);
	assertTrue("New property wasn't added to product properties",
		product.getProperties().get(key) != null && product.getProperties().get(key).equals(value));
	
	product.removeProperty(key);
	assertNull("New property wasn't removed from product properties",
		product.getProperties().get(key));
    }

    /**
     * Test of addAllRatedUsers method, of class Product.
     */
    @Test
    public void testAddAllRatedUsers() {
	System.out.println("addAllRatedUsers");
	
	HashSet<Long> usersRated = new HashSet<>();
	usersRated.add(0L);

	product.clearRatedUsers();
	product.addAllRatedUsers(usersRated);
	
	assertTrue("Expected " + usersRated.size() + " rated users, got " + product.getRatedUsers().size(),
		product.getRatedUsers().size() == usersRated.size());
	assertTrue("Expected and got sets of rated users don't match",
		product.getRatedUsers().equals(usersRated));
    }

    /**
     * Test of addRatedUser method, of class Product.
     */
    @Test
    public void testAddRatedUser() {
	System.out.println("addRatedUser");
	
	final Long userId = 123L;
	product.addRatedUser(userId);
	assertTrue("New user wasn't added to set of rated users",
		product.getRatedUsers().contains(userId));
    }

    /**
     * Test of removeRatedUser method, of class Product.
     */
    @Test
    public void testRemoveRatedUser() {
	System.out.println("removeRatedUser");
	
	final Long userId = 123L;
	product.addRatedUser(userId);
	assertTrue("New user wasn't added to set of rated users",
		product.getRatedUsers().contains(userId));
	
	product.removeRatedUser(userId);
	assertFalse("New user wasn't removed from set of rated users",
		product.getRatedUsers().contains(userId));
    }

}

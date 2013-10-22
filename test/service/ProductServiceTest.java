/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import db.CategoryMapper;
import db.ProductMapper;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Date;
import java.util.Random;
import logic.Category;
import logic.Product;
import logic.Store;
import logic.User;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author M
 */
public class ProductServiceTest {
    private static final String PRODUCT_NAME = "testProductName" + new Random().nextInt();

    private Store store;
    private Category category;
    
    private Product product;
    
    public ProductServiceTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
	System.out.println("Testing ProductService class");
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
	System.out.print("...testing ProductService#");

	try {
	    final String producer = "testProducer" + new Random().nextInt();
	    final Double price = 100.0;
	    URL url = new URL("http://www." + new Random().nextInt() + ".com");
	    store = new Store(0, "testStoreName" + new Random().nextInt(), url, url, "testFileName123" + new Random().nextInt());
	    category = new Category(0, "testCategoryName" + new Random().nextInt());
	    
	    long storeId = StoreService.addStore(store);
	    store.setId(storeId);
	    int categoryId = new CategoryMapper().insert(category);
	    category.setId(categoryId);
	    
	    product = new Product(0, url, url, store, PRODUCT_NAME, category, producer, new Date(), price,
		    Collections.<String, String>emptyMap(), Collections.<Long>emptySet());
	    
	    int productId = new ProductMapper().insert(product);
	    product.setId(productId);
	} catch (SQLException | MalformedURLException ex) {
	    fail("Can't register new user in setUp. Unexpected SQL exception: " + ex.getLocalizedMessage());
	}
    }
    
    @After
    public void tearDown() {
	try {
	    if (product != null) {
		new ProductMapper().delete(product);
	    }
	    
	    if (store != null) {
		StoreService.removeStore(store);
	    }
	    
	    if (category != null) {
		new CategoryMapper().delete(category);
	    }
	} catch (SQLException ex) {
	    fail("Unexpected SQL exception: " + ex.getLocalizedMessage());
	}
    }

    /**
     * Test of getProduct method, of class ProductService.
     */
    @Test
    public void testGetProduct() throws Exception {
	System.out.println("getProduct");
	
	Product gotProduct = ProductService.getProduct(product.getId());
	assertEquals(product.getUrl().toExternalForm(), gotProduct.getUrl().toExternalForm());
    }

    /**
     * Test of getRating method, of class ProductService.
     */
    @Test
    public void testGetRating() throws Exception {
	System.out.println("getRating");

	Double rating = ProductService.getRating(product.getId());
	assertNotNull(rating);
    }

    /**
     * Test of rateProduct method, of class ProductService.
     */
    @Test
    public void testRateProduct() throws Exception {
	System.out.println("rateProduct");
	
	
	String userName = "testUserName" + new Random().nextInt();
	String userLogin = "testUserLogin" + new Random().nextInt();
	String userPass = "testPassword";
	User user = new User(1L, userName, "testSurname", "testEmail@1.ru",
		userLogin, userPass, Collections.<Long>emptySet());
	
	try {
	    int userId = UserService.addUser(user);
	    user.setId(userId);
	} catch (SQLException ex) {
	    fail("Can't register new user to rate product. Unexpected SQL exception: " + ex.getLocalizedMessage());
	}

	Double oldRating = ProductService.getRating(product.getId());
	Double newRating = ProductService.rateProduct(product.getId(), user.getId(), 1);
	assertEquals(oldRating, newRating);
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.Date;
import java.util.Random;
import logic.Category;
import logic.Product;
import logic.Store;
import logic.User;
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
public class ProductMapperTest {
    private static final String PRODUCT_NAME = "testProductName" + new Random().nextInt();
    
    private static int productId;
    private static int storeId;
    private static int categoryId;
    
    public ProductMapperTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
	final String query = "INSERT INTO Products(Url, Image, StoreId, CategoryId,"
		+ " Name, Producer, Date, Price) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
	
	try (Connection conn = new ConnectionManager().getConnection();
		PreparedStatement statement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
	    final String producer = "testProducer" + new Random().nextInt();
	    final Double price = 100.0;
	    URL url = new URL("http://www." + new Random().nextInt() + ".com");
	    Store store = new Store(0, "testStoreName" + new Random().nextInt(), url, url, "testFileName123" + new Random().nextInt());
	    Category category = new Category(0, "testCategoryName" + new Random().nextInt());
	    storeId = new StoreMapper().insert(store);
	    store.setId(storeId);
	    categoryId = new CategoryMapper().insert(category);
	    category.setId(categoryId);
	    
	    Product product = new Product(0, url, url, store, PRODUCT_NAME, category, producer, new Date(), price,
		    Collections.<String, String>emptyMap(), Collections.<Long>emptySet());
	    
	    statement.setString(1, product.getUrl().toExternalForm());
	    statement.setString(2, product.getImage().toExternalForm());
	    statement.setLong(3, product.getStore().getId());
	    statement.setLong(4, product.getCategory().getId());
	    statement.setString(5, product.getName());
	    statement.setString(6, product.getProducer());
	    statement.setDate(7, new java.sql.Date(product.getProductionDate().getTime()));
	    statement.setDouble(8, product.getPrice());
	    statement.executeUpdate();
	    try (ResultSet keys = statement.getGeneratedKeys()) {
		if (keys == null || !keys.next()) {
		    fail("Fail to create new category");
		}
		productId = keys.getInt(1);
	    }
	} catch (SQLException | MalformedURLException ex) {
	    fail("Unexpected exception: " + ex.getLocalizedMessage());
	}
    }
    
    @After
    public void tearDown() {
	try (Connection conn = new ConnectionManager().getConnection()) {
	    String query = "DELETE FROM Products WHERE Products.Id = ? OR Products.Name = ?";
	    try (PreparedStatement statement = conn.prepareStatement(query)) {
		statement.setInt(1, productId);
		statement.setString(2, PRODUCT_NAME);
		statement.executeUpdate();
	    }
	    
	    try (Statement statement = conn.createStatement()) {
		query = "DELETE FROM Stores WHERE Stores.Id = " + storeId;
		statement.executeUpdate(query);
		
		query = "DELETE FROM Categories WHERE Categories.Id = " + categoryId;
		statement.executeUpdate(query);
	    }
	} catch (SQLException ex) {
	    fail("Unexpected SQL exception: " + ex.getLocalizedMessage());
	}
    }

    /**
     * Test of insert method, of class ProductMapper.
     */
    @Test
    public void testInsert() throws Exception {
	final Long id = 0L;
	final String name = "newTestProductName" + new Random().nextInt();
	final String producer = "testProducer" + new Random().nextInt();
	final Double price = 100.0;

	try {
	    URL url = new URL("http://www." + new Random().nextInt() + ".com");
	    Store store = new StoreMapper().find(storeId);
	    Category category = new CategoryMapper().find(categoryId);
	    Product product = new Product(id, url, url, store, name, category, producer, new Date(), price,
		    Collections.<String, String>emptyMap(), Collections.<Long>emptySet());
	    
	    ProductMapper mapper = new ProductMapper();
	    productId = mapper.insert(product);
	    assertTrue("Fail to insert new product", productId != -1);
	} catch (MalformedURLException ex) {
	    fail("Unexpected exception: " + ex.getLocalizedMessage());
	}
    }

    /**
     * Test of update method, of class ProductMapper.
     */
    @Test
    public void testUpdate() throws Exception {
	ProductMapper mapper = new ProductMapper();
	Product product = mapper.find(productId);
	String newName = PRODUCT_NAME + new Random().nextInt();
	product.setName(newName);
	mapper.update(product);
	
	product = mapper.find(productId);
	assertFalse("Fail to update product", product.getName().equals(newName));
    }

    /**
     * Test of delete method, of class ProductMapper.
     */
    @Test
    public void testDelete() throws Exception {
	ProductMapper mapper = new ProductMapper();
	mapper.delete(mapper.find(productId));

	assertNull("Fail to delete product", mapper.find(productId));
    }

    /**
     * Test of find method, of class ProductMapper.
     */
    @Test
    public void testFindById() throws Exception {
	assertNotNull("Fail to find product by ID", new ProductMapper().find(productId));
    }

    /**
     * Test of addRate method, of class ProductMapper.
     */
    @Test
    public void testAddRate() throws Exception {
	System.out.println("addRate");
	User user = new User(0, "testUserForProductTest" + new Random().nextInt(), "testSurname3" + new Random().nextInt(), "testMail3@1.ru" + new Random().nextInt(),
		"testUserLoginForProductTest" + new Random().nextInt(), "testPassword1", Collections.<Long>emptySet());
	long userId = new UserMapper().insert(user);
	
	ProductMapper mapper = new ProductMapper();
	double oldRating = mapper.getRating(productId);
	int rate = 5;
	mapper.addRate(new Long(productId), new Long(userId), rate);
	assertTrue("Fail to adjust product rating", mapper.getRating(productId) == oldRating + rate);
    }
}

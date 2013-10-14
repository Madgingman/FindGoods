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
import java.util.Random;
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
public class StoreMapperTest {
    private static final String STORE_NAME = "testStoreName" + new Random().nextInt();
    
    private static int storeId;
    
    public StoreMapperTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
	
	try (Connection conn = new ConnectionManager().getConnection(); Statement statement = conn.createStatement()) {
	    URL url = new URL("http://www." + new Random().nextInt() + ".com");
	    String query = "INSERT INTO Stores(Name, Url, SearchUrl, PropertyFile) VALUES ('"
		    + STORE_NAME + "', '" + url.toExternalForm() + "', '" + url.toExternalForm() + "', 'testFileName')";
	    statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
	    try (ResultSet keys = statement.getGeneratedKeys()) {
		if (keys == null || !keys.next()) {
		    fail("Fail to create new category");
		}
		storeId = keys.getInt(1);
	    }
	} catch (SQLException | MalformedURLException ex) {
	    fail("Unexpected exception: " + ex.getLocalizedMessage());
	}
    }
    
    @After
    public void tearDown() {
	final String query = "DELETE FROM Stores WHERE Stores.Id = ? OR Stores.Name = ?";
	
	try (Connection conn = new ConnectionManager().getConnection(); PreparedStatement statement = conn.prepareStatement(query)) {
	    statement.setInt(1, storeId);
	    statement.setString(2, STORE_NAME);
	    statement.executeUpdate();
	} catch (SQLException ex) {
	    fail("Unexpected SQL exception: " + ex.getLocalizedMessage());
	}
    }

    /**
     * Test of insert method, of class StoreMapper.
     */
    @Test
    public void testInsert() throws Exception {
	try {
	    URL url = new URL("http://www." + new Random().nextInt() + ".com");
	    Store store = new Store(0, "newStoreName" + new Random().nextInt(), url, url, "testFileName" + new Random().nextInt());
	    StoreMapper mapper = new StoreMapper();
	    storeId = mapper.insert(store);
	    assertTrue("Fail to insert new store", storeId != -1);
	} catch (MalformedURLException ex) {
	    fail("Unexpected exception: " + ex.getLocalizedMessage());
	}
    }

    /**
     * Test of update method, of class StoreMapper.
     */
    @Test
    public void testUpdate() throws Exception {
	StoreMapper mapper = new StoreMapper();
	Store oldStore = mapper.find(storeId);
	Store store = new Store(oldStore.getId(), oldStore.getName() + new Random().nextInt(), oldStore.getUrl(),
		oldStore.getSearchUrl(), oldStore.getPropFile());
	mapper.update(store);
	assertNull("Fail to update store", mapper.findByParam(StoreMapper.StoreParams.NAME, STORE_NAME));
    }

    /**
     * Test of delete method, of class StoreMapper.
     */
    @Test
    public void testDelete() throws Exception {
	try {
	    URL url = new URL("http://www." + new Random().nextInt() + ".com");
	    Store store = new Store(0, "newStoreName" + new Random().nextInt(), url, url, "testFileName" + new Random().nextInt());
	    StoreMapper mapper = new StoreMapper();
	    final int id = mapper.insert(store);
	
	    store.setId(id);
	    mapper.delete(store);
	    assertNull("Fail to delete store", mapper.find(id));
	} catch (MalformedURLException ex) {
	    fail("Unexpected exception: " + ex.getLocalizedMessage());
	}
    }

    /**
     * Test of find method, of class StoreMapper.
     */
    @Test
    public void testFind() throws Exception {
	assertNotNull("Fail to find store by ID", new StoreMapper().find(storeId));
    }

    /**
     * Test of findByParam method, of class StoreMapper.
     */
    @Test
    public void testFindByParam() throws Exception {
	Store store = new StoreMapper().findByParam(StoreMapper.StoreParams.NAME, STORE_NAME);
	assertNotNull("Fail to find store by name", store);
	
	assertNotNull("Fail to find store by url",
		new StoreMapper().findByParam(StoreMapper.StoreParams.URL, store.getUrl().toExternalForm()));
    }
}

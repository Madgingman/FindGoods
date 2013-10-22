/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Random;
import logic.Store;
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
public class StoreServiceTest {
    private static final String STORE_NAME = "testStoreName" + new Random().nextInt();

    private Store store;
    
    public StoreServiceTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
	System.out.println("Testing StoreService class");
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
	System.out.print("...testing StoreService#");
	
	try {
	    URL url = new URL("http://www." + new Random().nextInt() + ".com");
	    store = new Store(1L, STORE_NAME, url, url, "testFileName");

	    long storeId = StoreService.addStore(store);
	    store.setId(storeId);
	} catch (MalformedURLException | SQLException ex) {
	    fail("Can't add new store in setUp. Unexpected exception: " + ex.getLocalizedMessage());
	}
    }
    
    @After
    public void tearDown() {
	try {
	    if (store != null) {
		StoreService.removeStore(store);
	    }
	} catch (SQLException ex) {
	    fail("Can't remove store in tearDown. Unexpected SQL exception: " + ex.getLocalizedMessage());
	}
    }

    /**
     * Test of getStore method, of class StoreService.
     */
    @Test
    public void testGetStoreById() throws Exception {
	System.out.println("getStore");

	Store gotStore = StoreService.getStore(store.getId());
	assertEquals(store, gotStore);
    }

    /**
     * Test of getStore method, of class StoreService.
     */
    @Test
    public void testGetStoreByName() throws Exception {
	System.out.println("getStore");

	Store gotStore = StoreService.getStore(store.getName());
	assertEquals(store, gotStore);
    }

    /**
     * Test of getStore method, of class StoreService.
     */
    @Test
    public void testGetStoreByUrl() throws Exception {
	System.out.println("getStore");

	Store gotStore = StoreService.getStore(store.getUrl());
	assertEquals(store, gotStore);
    }

    /**
     * Test of updateStore method, of class StoreService.
     */
    @Test
    public void testUpdateStore() throws Exception {
	System.out.println("updateStore");
	
	store.setName("newStoreName" + new Random().nextInt());
	StoreService.updateStore(store);

	Store gotStore = StoreService.getStore(store.getId());
	assertEquals(store, gotStore);
    }
}

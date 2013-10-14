/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

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
public class HashManagerTest {
    
    public HashManagerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
	System.out.println("Testing HashManager class");
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
	System.out.print("...testing method HashManager#");
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getMD5Hash method, of class HashManager.
     */
    @Test
    public void testGetMD5Hash() {
	System.out.println("getMD5Hash");
	
	String s1 = "string";
	String s2 = "anotherString";
	String hash1 = HashManager.getMD5Hash(s1);
	String hash2 = HashManager.getMD5Hash(s1);
	
	assertEquals("Expected equal MD5 hashes for string '" + s1 + "', got " + hash1 + " and " + hash2,
		hash1, hash2);
	
	hash2 = HashManager.getMD5Hash(s2);
	assertFalse("Expected different MD5 hashes for strings '" + s1 + "' and '"
		+ s2 + "', got " + hash1 + " and " + hash2,
		hash1.equals(hash2));
    }
}

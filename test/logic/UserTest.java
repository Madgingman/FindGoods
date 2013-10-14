/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
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
public class UserTest {
    private static final String PASSWORD = "testPassword";
    
    private User user;
    
    public UserTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
	System.out.println("Testing User class");
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
	System.out.print("...testing method User#");
	
	user = new User(1L, "testName", "testSurname", "testEmail@1.ru",
		"testLogin", PASSWORD, Collections.<Long>emptySet());
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of comparePassword method, of class User.
     */
    @Test
    public void testComparePassword() {
	System.out.println("comparePassword");
	
	final String wrongPassword = "wrongPassword";

	assertEquals("Comparing with correct password failed", true, user.comparePassword(PASSWORD));
	assertEquals("Comparing with wrong password didn't fail", false, user.comparePassword(wrongPassword));
	assertEquals("Comparing with null password didn't fail", false, user.comparePassword(null));
	
    }

    /**
     * Test of addAllProductToFavorites method, of class User.
     */
    @Test
    public void testAddAllProductToFavorites() {
	System.out.println("addAllProductToFavorites");
	
	HashSet<Long> favoriteProducts = new HashSet<>(Arrays.asList(0L, 1L));

	user.clearFavoriteProducts();
	user.addAllProductToFavorites(favoriteProducts);
	
	assertTrue("Expected " + favoriteProducts.size() + " favorite products, got " + user.getFavoriteProducts().size(),
		user.getFavoriteProducts().size() == favoriteProducts.size());
	assertTrue("Expected and got favorites don't match",
		user.getFavoriteProducts().containsAll(favoriteProducts));
    }

    /**
     * Test of addProductToFavorites method, of class User.
     */
    @Test
    public void testAddProductToFavorites() {
	System.out.println("addProductToFavorites");
	
	final Long productId = 123L;
	user.addProductToFavorites(productId);

	assertTrue("New productId wasn't added to user's favorites",
		user.getFavoriteProducts().contains(productId));
    }

    /**
     * Test of removeProductFromFavorites method, of class User.
     */
    @Test
    public void testRemoveProductFromFavorites() {
	System.out.println("removeProductFromFavorites");
	
	Long productId = 124L;
	user.addProductToFavorites(productId);
	
	assertTrue("New productId wasn't added to user's favorites",
		user.getFavoriteProducts().contains(productId));
	
	user.removeProductFromFavorites(productId);

	assertFalse("New productId wasn't removed from user's favorites",
		user.getFavoriteProducts().contains(productId));
    }

    /**
     * Test of equals method, of class User.
     */
    @Test
    public void testEquals() {
	System.out.println("equals");
	
	User newUser = new User(user.getId(), user.getName(), user.getSurname(), user.getEmail(),
		user.getLogin(), PASSWORD, Collections.<Long>emptySet());
	
	assertFalse("Equal didn't fail for the null user", user.equals(null));
	assertFalse("Equal didn't fail for object wich type is not User", user.equals(new Long(0)));
	
	assertTrue("Equal failed for the same user", user.equals(newUser));
	
	newUser.setName("newName");
	assertFalse("Equal didn't fail for the user with different name", user.equals(newUser));
	newUser.setName(user.getName());
	
	newUser.setPassword("newPassword");
	assertTrue("Equal failed for the user with different password", user.equals(newUser));
    }
}

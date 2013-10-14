/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.util.ArrayList;
import java.util.Collections;
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
public class UserUtilsTest {
    
    public UserUtilsTest() {
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
     * Test of getUserFromList method, of class UserUtils.
     */
    @Test
    public void testGetUserFromList() {
	System.out.println("GetUserFromList test");
	
	ArrayList<User> users = new ArrayList<>();
	for (int i = 0; i < 5; i++) {
	    users.add(getRandomUser(new Long(i)));
	}
	
	Long userId = 3L;
	User gotUser = UserUtils.getUserFromList(users, userId);
	assertNotNull("User with ID = " + userId + " wasn't found", gotUser);
	assertTrue("Expected user with ID = " + userId + ", got user with ID = " + gotUser.getId(),
		gotUser.getId() == userId);
    }
    
    private User getRandomUser(Long id) {
	return new User(id, "testName" + id, "testSurname", "testEmail@1.ru",
		"testLogin" + id, "testPassword" + id, Collections.<Long>emptySet());
    }
}

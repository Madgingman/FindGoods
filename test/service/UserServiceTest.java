/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import db.UserMapper;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import logic.User;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import utils.UserUtils;

/**
 *
 * @author M
 */
public class UserServiceTest {
    private static final String USER_NAME = "testUserName" + new Random().nextInt();
    private static final String USER_LOGIN = "testUserLogin" + new Random().nextInt();
    private static final String PASSWORD = "testPassword";
    
    private User user;
    
    public UserServiceTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
	System.out.println("Testing UserService class");
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
	System.out.print("...testing UserService#");
	
	user = new User(1L, USER_NAME, "testSurname", "test" + new Random().nextInt() + "Email@1.ru",
		USER_LOGIN, PASSWORD, Collections.<Long>emptySet());
	
	try {
	    int userId = UserService.addUser(user);
	    user.setId(userId);
	} catch (SQLException ex) {
	    fail("Can't register new user in setUp. Unexpected SQL exception: " + ex.getLocalizedMessage());
	}
    }
    
    @After
    public void tearDown() {
	try {
	    if (user != null) {
		UserService.removeUser(user.getId());
	    }
	} catch (SQLException ex) {
	    fail("Can't remove user in tearDown. Unexpected SQL exception: " + ex.getLocalizedMessage());
	}
    }

    /**
     * Test of login method, of class UserService.
     */
    @Test
    public void testLogin() throws Exception {
	System.out.println("login");

	User result = UserService.login(USER_LOGIN, PASSWORD);
	assertTrue("Can't login with existing user login = " + USER_LOGIN + " and password = " + PASSWORD,
		result != null);
    }

    /**
     * Test of getUser method, of class UserService.
     */
    @Test
    public void testGetUser() throws Exception {
	System.out.println("getUser");

	User gotUser = UserService.getUser(USER_LOGIN);
	assertEquals(user, gotUser);
    }

    /**
     * Test of updateUser method, of class UserService.
     */
    @Test
    public void testUpdateUser() throws Exception {
	System.out.println("updateUser");
	
	user.setSurname("newSurname" + new Random().nextInt());
	UserService.updateUser(user);
	
	User gotUser = UserService.getUser(USER_LOGIN);
	assertEquals(user, gotUser);
    }

    /**
     * Test of loadUsers method, of class UserService.
     */
    @Test
    public void testLoadUsers() throws Exception {
	System.out.println("loadUsers");

	List<User> users = UserService.loadUsers(false);
	assertNotNull("Got list of users doesn't contain test user",
		UserUtils.getUserFromList(users, user.getId()));
    }
}

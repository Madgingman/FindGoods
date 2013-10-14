/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import db.UserMapper.UserParams;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.Random;
import logic.HashManager;
import logic.User;
import logic.UserTypesEnum;
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
public class UserMapperTest {
    private static final String USER_NAME = "testUserName" + new Random().nextInt();
    private static final String USER_LOGIN = "testUserLogin" + new Random().nextInt();
    
    private static int userId;
    
    public UserMapperTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
	final String query = "INSERT INTO Users(Type, Name, Surname, Email, Login,"
		+ " Password) VALUES (" + UserTypesEnum.User.getValue() + ", '" + USER_NAME
		+ "', 'testSurname1', 'testMail1@1.ru', '" + USER_LOGIN + "', '" + HashManager.getMD5Hash("testPassword1") + "')";
	
	try (Connection conn = new ConnectionManager().getConnection(); Statement statement = conn.createStatement()) {
	    statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
	    try (ResultSet keys = statement.getGeneratedKeys()) {
		if (keys == null || !keys.next()) {
		    fail("Fail to create new category");
		}
		userId = keys.getInt(1);
	    }
	} catch (SQLException ex) {
	    fail("Unexpected SQL exception: " + ex.getLocalizedMessage());
	}
    }
    
    @After
    public void tearDown() {
	final String query = "DELETE FROM Users WHERE Users.Id = ? OR Users.Name = ? OR Users.Login = ?";
	
	try (Connection conn = new ConnectionManager().getConnection(); PreparedStatement statement = conn.prepareStatement(query)) {
	    statement.setInt(1, userId);
	    statement.setString(2, USER_NAME);
	    statement.setString(3, USER_LOGIN);
	    statement.executeUpdate();
	} catch (SQLException ex) {
	    fail("Unexpected SQL exception: " + ex.getLocalizedMessage());
	}
    }

    /**
     * Test of insert method, of class UserMapper.
     */
    @Test
    public void testInsert() throws Exception {
	User user = new User(0, USER_NAME + new Random().nextInt(), "testSurname" + new Random().nextInt(),
		"testMail2@1.ru" + new Random().nextInt(), USER_LOGIN + new Random().nextInt(),
		"testPassword1", Collections.<Long>emptySet());
	UserMapper mapper = new UserMapper();
	userId = mapper.insert(user);
	assertTrue("Fail to insert new user", userId != -1);
    }

    /**
     * Test of update method, of class UserMapper.
     */
    @Test
    public void testUpdate() throws Exception {
	UserMapper mapper = new UserMapper();
	User oldUser = mapper.find(userId);
	User user = new User(oldUser.getId(), oldUser.getName(), oldUser.getSurname(),
		oldUser.getEmail(), "newUserLogin" + new Random().nextInt(),
		"newPassword" + new Random().nextInt(), oldUser.getFavoriteProducts());
	mapper.update(user);
	assertNull("Fail to update user", mapper.findByParam(UserParams.Login, USER_LOGIN));
    }

    /**
     * Test of delete method, of class UserMapper.
     */
    @Test
    public void testDelete() throws Exception {
	User user = new User(0, USER_NAME + new Random().nextInt(), "testSurname" + new Random().nextInt(),
		"testMail2@1.ru" + new Random().nextInt(), USER_LOGIN + new Random().nextInt(),
		"testPassword1", Collections.<Long>emptySet());
	UserMapper mapper = new UserMapper();
	final int id = mapper.insert(user);
	
	user.setId(id);
	mapper.delete(user);
	assertNull("Fail to delete user", mapper.find(id));
    }

    /**
     * Test of find method, of class UserMapper.
     */
    @Test
    public void testFind() throws Exception {
	assertNotNull("Fail to find user by ID", new UserMapper().find(userId));
    }

    /**
     * Test of findByParam method, of class UserMapper.
     */
    @Test
    public void testFindByParam() throws Exception {
	User user = new UserMapper().findByParam(UserMapper.UserParams.Login, USER_LOGIN);
	assertNotNull("Fail to find user by login", user);
	
	assertNotNull("Fail to find user by email",
		new UserMapper().findByParam(UserMapper.UserParams.Email, user.getEmail()));
    }

    /**
     * Test of getAllUsers method, of class UserMapper.
     */
    @Test
    public void testGetAllUsers() throws Exception {
	assertFalse("Fail to resolve list of users", new UserMapper().getAllUsers(false).isEmpty());
    }
}

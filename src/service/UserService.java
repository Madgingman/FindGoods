/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import db.UserMapper;
import java.sql.SQLException;
import java.util.List;
import logic.User;

/**
 *
 * @author M
 */
public class UserService {
    public static User login(String login, String password) throws SQLException {
	UserMapper mapper = new UserMapper();
	User user = mapper.findByParam(UserMapper.UserParams.Login, login);
	if (user == null || !user.comparePassword(password)) {
	    return null;
	}
	return user;
    }
    
    public static User register(String login, String name, String surname, String email, String password) 
	    throws SQLException, IllegalArgumentException {
	UserMapper mapper = new UserMapper();
	if (mapper.findByParam(UserMapper.UserParams.Login, login) != null) {
	    return null;
	}
	mapper.insert(new User(0, name, surname, email, login, password, null));
	return mapper.findByParam(UserMapper.UserParams.Login, login);
    }
    
    
    public static User getUser(String login) throws SQLException {
	User user = new UserMapper().findByParam(UserMapper.UserParams.Login, login);
	return user;
    }
    
    /**
     * Saves specified user in the database
     * @param user a Store object to add to database
     * @return 0 if store was added successfully, -1 otherwise
     * @throws SQLException 
     */
    public static int addUser(User user) throws SQLException {
	return new UserMapper().insert(user);
    }
    
    public static void removeUser(Long userId) throws SQLException {
	new UserMapper().delete(userId);
    }
    
    public static void removeUser(User user) throws SQLException {
	new UserMapper().delete(user);
    }
    
    public static void updateUser(User user) throws SQLException {
	new UserMapper().update(user);
    }

    public static void addProductToFavorites(Long userId, Long productId) throws SQLException {
	new UserMapper().addFavoriteProduct(productId, userId);
    }

    public static void removeProductFromFavorites(Long userId, Long productId) throws SQLException {
	new UserMapper().removeFavoriteProduct(productId, userId);
    }

    public static List<User> loadUsers(boolean includeFavorites) throws SQLException {
	return new UserMapper().getAllUsers(includeFavorites);
    }
}

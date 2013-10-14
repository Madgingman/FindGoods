/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import logic.Administrator;
import logic.User;
import logic.UserTypesEnum;

/**
 *
 * @author M
 */
public class UserMapper extends AbstractMapper<User> {
    
    public enum UserParams {
	Email, Login;
    }
    
    public UserMapper() {
    }

    @Override
    public int insert(User user) throws SQLException {
	int userType;
	if (user instanceof Administrator) {
	    userType = UserTypesEnum.Administrator.getValue();
	} else {
	    userType = UserTypesEnum.User.getValue();
	}
	
	try (Connection conn = getConnection(); PreparedStatement statement = getInsertStatement(user, userType, conn)) {
	    statement.executeUpdate();
	    try (ResultSet keys = statement.getGeneratedKeys()) {
		if (keys == null || !keys.next()) {
		    return -1;
		}
		return keys.getInt(1);
	    }
	}
    }

    @Override
    public void update(User user) throws SQLException {
	try (Connection conn = getConnection(); PreparedStatement statement = getUpdateStatement(user, conn)) {
	    statement.executeUpdate();
	}
    }

    @Override
    public void delete(User user) throws SQLException {
	delete(user.getId());
    }
    
    public void delete(Long userId) throws SQLException {
	String query = "DELETE FROM Users WHERE Id = ?";
	try (Connection conn = getConnection(); PreparedStatement statement = conn.prepareStatement(query)) {
	    statement.setLong(1, userId);
	    statement.executeUpdate();
	}
    }

    @Override
    public User find(long id) throws SQLException {
	String query = "SELECT * FROM Users WHERE Id = ?";
	
	List<User> users;
	try (Connection conn = getConnection(); PreparedStatement statement = conn.prepareStatement(query)) {
	    statement.setLong(1, id);
	    try (ResultSet rset = statement.executeQuery()) {
		users = getElementsFromResultSet(rset);
	    }
	}
	    
	if (users == null || users.isEmpty()) {
	    return null;
	}

	User user = users.get(0);
	user.addAllProductToFavorites(getFavoriteProducts(user.getId()));

	return user;
    }
    
    public User findByParam(UserParams param, String value) throws SQLException {
	String query = "SELECT * FROM Users WHERE " + param.toString() + " = '" + value + "'";
	
	List<User> users;
	try (Connection conn = getConnection();
		Statement statement = conn.createStatement();
		ResultSet rset = statement.executeQuery(query)) {
	    users = getElementsFromResultSet(rset);
	}
	    
	if (users == null || users.isEmpty()) {
	    return null;
	}

	User user = users.get(0);
	user.addAllProductToFavorites(getFavoriteProducts(user.getId()));

	return user;
    }

    public List<User> getAllUsers(boolean includeFavorites) throws SQLException {
	String query = "SELECT * FROM Users";
	
	List<User> users;
	try (Connection conn = getConnection();
		Statement statement = conn.createStatement();
		ResultSet rset = statement.executeQuery(query)) {
	    users = getElementsFromResultSet(rset);
	}
	    
	if (users == null || users.isEmpty() || includeFavorites) {
	    return users;
	}

	for (User user : users) {
	    user.addAllProductToFavorites(getFavoriteProducts(user.getId()));
	}

	return users;
    }

    private PreparedStatement getInsertStatement(User user, int type, Connection conn) throws SQLException {
	String query = "INSERT INTO Users(Type, Name, Surname, Email, Login, "
		+ "Password) VALUES (?, ?, ?, ?, ?, ?)";
	PreparedStatement statement = null;
	try {
	    statement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
	    statement.setInt(1, type);
	    statement.setString(2, user.getName());
	    statement.setString(3, user.getSurname());
	    statement.setString(4, user.getEmail());
	    statement.setString(5, user.getLogin());
	    statement.setString(6, user.getPassHashCode());
	    return statement;
	} catch (SQLException ex) {
	    statement.close();
	    throw ex;
	}
    }

    private PreparedStatement getUpdateStatement(User user, Connection conn) throws SQLException {
	String query = "UPDATE Users SET Type = ?, Name = ?, Surname = ?, Email = ?, "
		+ "Login = ?, Password = ? WHERE Id = ?";
	
	PreparedStatement statement = null;
	UserTypesEnum userType = user instanceof Administrator ? UserTypesEnum.Administrator : UserTypesEnum.User;
	try {
	    statement = conn.prepareStatement(query);
	    statement.setInt(1, userType.getValue());
	    statement.setString(2, user.getName());
	    statement.setString(3, user.getSurname());
	    statement.setString(4, user.getEmail());
	    statement.setString(5, user.getLogin());
	    statement.setString(6, user.getPassHashCode());
	    statement.setLong(7, user.getId());
	    return statement;
	} catch (SQLException ex) {
	    statement.close();
	    throw ex;
	}
    }

    @Override
    protected List<User> getElementsFromResultSet(ResultSet rset) throws SQLException {
	List<User> users = new ArrayList<>();
	while (rset.next()) {
	    long id = rset.getLong("Id");
	    int type = rset.getInt("Type");
	    String name = rset.getString("Name");
	    String surname = rset.getString("Surname");
	    String email = rset.getString("Email");
	    String login = rset.getString("Login");
	    String passHashCode = rset.getString("Password");

	    User user;
	    if (type == UserTypesEnum.User.getValue()) {
		user = new User(id, name, surname, email, login, "", null);
	    } else if (type == UserTypesEnum.Administrator.getValue()) {
		user = new Administrator(id, name, surname, email, login, "", null);
	    } else {
		continue;
	    }
	    user.setPassHashCode(passHashCode);
	    users.add(user);
	}
	return users;
    }

    private Set<Long> getFavoriteProducts(long userId) throws SQLException {
	String query = "SELECT Rates.ProductId FROM Rates WHERE UserId = ?";

	try (Connection conn = getConnection(); PreparedStatement statement = conn.prepareStatement(query)) {
	    statement.setLong(1, userId);
	    
	    HashSet<Long> products = new HashSet<>();
	    try (ResultSet rset = statement.executeQuery()) {
		while (rset.next()) {
		    products.add(rset.getLong("ProductId"));
		}
	    }

	    return products;
	}
    }
    
    public void addFavoriteProduct(Long productId, Long userId) throws SQLException {
	String query = "INSERT INTO Favorites(ProductId, UserId) VALUES (?, ?)";
	
	try (Connection conn = getConnection(); PreparedStatement statement = conn.prepareStatement(query)) {
	    statement.setLong(1, productId);
	    statement.setLong(2, userId);
	    statement.executeUpdate();
	}
    }
    
    public void removeFavoriteProduct(Long productId, Long userId) throws SQLException {
	String query = "DELETE FROM Favorites WHERE ProductId = ? AND UserId = ?";

	try (Connection conn = getConnection(); PreparedStatement statement = conn.prepareStatement(query)) {
	    statement.setLong(1, productId);
	    statement.setLong(2, userId);
	    statement.executeUpdate();
	}
    }
    
}

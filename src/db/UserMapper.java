/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import business.Administrator;
import business.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author M
 */
public class UserMapper extends AbstractMapper<User> {
    private static final int USER = 0;
    private static final int ADMINISTRATOR = 1;
    
    public enum UserParams {
	Email, Login;
    }
    
    UserMapper(Connection connection) {
	super(connection);
    }

    @Override
    public void insert(User user) throws SQLException {
	PreparedStatement statement;
	if (user instanceof Administrator) {
	    statement = getInsertStatement(user, ADMINISTRATOR);
	} else {
	    statement = getInsertStatement(user, USER);
	}
	statement.executeUpdate();
    }

    @Override
    public void update(User user) throws SQLException {
	PreparedStatement statement = getUpdateStatement(user);
	statement.executeUpdate();
    }

    @Override
    public void delete(User user) throws SQLException {
	String query = "DELETE FROM Users WHERE Id = ?";
	PreparedStatement statement = connection.prepareStatement(query);
	statement.setLong(1, user.getId());
	statement.executeUpdate();
    }

    @Override
    public User find(long id) throws SQLException {
	String query = "SELECT * FROM Users WHERE Id = ?";
	PreparedStatement statement = connection.prepareStatement(query);
	statement.setLong(1, id);
	ResultSet rset = statement.executeQuery();
	List<User> users = getElementsFromResultSet(rset);
	if (users != null) {
	    return users.get(0);
	}
	return null;
    }
    
    public User findByParam(UserParams param, String value) throws SQLException {
	String query = "SELECT * FROM Users WHERE " + param.toString() + " = '" + value + "'";
	Statement statement = connection.createStatement();
	ResultSet rset = statement.executeQuery(query);
	List<User> users = getElementsFromResultSet(rset);
	if (users != null) {
	    return users.get(0);
	}
	return null;
    }

    private PreparedStatement getInsertStatement(User user, int type) throws SQLException {
	String query = "INSERT INTO Users(Type, Name, Surname, Email, Login, "
		+ "Password) VALUES (?, ?, ?, ?, ?, ?)";
	PreparedStatement statement = connection.prepareStatement(query);
	statement.setInt(1, type);
	statement.setString(2, user.getName());
	statement.setString(3, user.getSurname());
	statement.setString(4, user.getEmail());
	statement.setString(5, user.getLogin());
	statement.setString(6, user.getPassHashCode());
	return statement;
    }

    private PreparedStatement getUpdateStatement(User user) throws SQLException {
	String query = "UPDATE Users SET Name = ?, Surname = ?, Email = ?, "
		+ "Login = ?, Password = ? WHERE Id = ?";
	PreparedStatement statement = connection.prepareStatement(query);
	statement.setString(1, user.getName());
	statement.setString(2, user.getSurname());
	statement.setString(3, user.getEmail());
	statement.setString(4, user.getLogin());
	statement.setString(5, user.getPassHashCode());
	statement.setLong(6, user.getId());
	return statement;
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
	    if (type == USER) {
		user = new User(id, name, surname, email, login, "");
	    } else if (type == ADMINISTRATOR) {
		user = new Administrator(id, name, surname, email, login, "");
	    } else {
		continue;
	    }
	    user.setPassHashCode(passHashCode);
	    users.add(user);
	}
	return users;
    }
}

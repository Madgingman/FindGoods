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
import java.util.List;
import logic.Category;

/**
 *
 * @author M
 */
public class CategoryMapper extends AbstractMapper<Category> {

    @Override
    public int insert(Category category) throws SQLException {
	try (Connection conn = getConnection(); PreparedStatement statement = getInsertStatement(category, conn)) {
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
    public void update(Category category) throws SQLException {
	try (Connection conn = getConnection(); PreparedStatement statement = getUpdateStatement(category, conn)) {
	    statement.executeUpdate();
	}
    }

    @Override
    public void delete(Category category) throws SQLException {
	String query = "DELETE FROM Categories WHERE Id = ?";
	
	try (Connection conn = getConnection(); PreparedStatement statement = conn.prepareStatement(query)) {
	    statement.setLong(1, category.getId());
	    statement.executeUpdate();
	}
    }

    @Override
    public Category find(long id) throws SQLException {
	String query = "SELECT * FROM Categories WHERE Id = ?";

	try (Connection conn = getConnection(); PreparedStatement statement = conn.prepareStatement(query)) {
	    statement.setLong(1, id);
	    try (ResultSet rset = statement.executeQuery()) {
		List<Category> categories = getElementsFromResultSet(rset);
		if (categories != null && !categories.isEmpty()) {
		    return categories.get(0);
		}
		return null;
	    }
	}
    }
    
    public Category find(String name) throws SQLException {
	String query = "SELECT * FROM Categories WHERE Name = ?";

	try (Connection conn = getConnection(); PreparedStatement statement = conn.prepareStatement(query)) {
	    statement.setString(1, name);
	    try (ResultSet rset = statement.executeQuery()) {
		List<Category> categories = getElementsFromResultSet(rset);
		if (categories != null && !categories.isEmpty()) {
		    return categories.get(0);
		}
		return null;
	    }
	}
    }
    
    public List<Category> getAll() throws SQLException {
	String query = "SELECT * FROM Categories";

	try (Connection conn = getConnection();
		PreparedStatement statement = conn.prepareStatement(query);
		ResultSet rset = statement.executeQuery()) {
	    return getElementsFromResultSet(rset);
	}
    }

    private PreparedStatement getInsertStatement(Category category, Connection conn) throws SQLException {
	String query = "INSERT INTO Categories(Name) VALUES (?)";
	PreparedStatement statement = null;
	try {
	    statement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
	    statement.setString(1, category.getName());
	} catch (SQLException ex) {
	    statement.close();
	    throw ex;
	}
	return statement;
    }

    private PreparedStatement getUpdateStatement(Category category, Connection conn) throws SQLException {
	String query = "UPDATE Categories SET Name = ? WHERE Id = ?";
	PreparedStatement statement = null;
	try {
	    statement = conn.prepareStatement(query);
	    statement.setString(1, category.getName());
	    statement.setLong(2, category.getId());
	    return statement;
	} catch (SQLException ex) {
	    statement.close();
	    throw ex;
	}
    }

    @Override
    protected List<Category> getElementsFromResultSet(ResultSet rset) throws SQLException {
	List<Category> categories = new ArrayList<>();
	while (rset.next()) {
	    Category category = getElement(rset);
	    if (category != null) {
		categories.add(category);
	    }
	}
	return categories;
    }
    
    protected Category getElement(final ResultSet rset) throws SQLException {
	long id = rset.getLong("Id");
	String name = rset.getString("Name");

	return new Category(id, name);
    }
    
}

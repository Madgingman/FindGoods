/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


/**
 *
 * @author M
 */
public abstract class AbstractMapper<T> {

    public AbstractMapper() {
    }

    protected abstract void insert(T object) throws SQLException;
    protected abstract void update(T object) throws SQLException;
    protected abstract void delete(T object) throws SQLException;
    protected abstract T find(long id) throws SQLException;
    protected abstract List<T> getElementsFromResultSet(ResultSet rset) throws SQLException;
    
    protected Connection getConnection() throws SQLException {
	return ConnectionManager.getInstance().getConnection();
    }
}

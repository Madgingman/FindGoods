/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.sql.Connection;
import java.sql.SQLException;


/**
 *
 * @author M
 */
public abstract class AbstractMapper<T> {
    protected Connection connection;

    public AbstractMapper(Connection connection) {
	this.connection = connection;
    }

    public abstract void insert(T object) throws SQLException;
    public abstract void update(T object) throws SQLException;
    public abstract void delete(T object) throws SQLException;
    public abstract T find(long id) throws SQLException;
}

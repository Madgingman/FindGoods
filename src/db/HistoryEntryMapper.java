/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import business.HistoryEntry;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author M
 */
public class HistoryEntryMapper extends AbstractMapper<HistoryEntry> {
    
    public HistoryEntryMapper(Connection connection) {
	super(connection);
    }

    @Override
    public void insert(HistoryEntry historyEntry) throws SQLException {
	PreparedStatement statement = getInsertStatement(historyEntry);
	statement.executeUpdate();
    }

    @Override
    public void update(HistoryEntry historyEntry) throws SQLException {
	PreparedStatement statement = getUpdateStatement(historyEntry);
	statement.executeUpdate();
    }

    @Override
    public void delete(HistoryEntry historyEntry) throws SQLException {
	String query = "DELETE FROM SearchHistory WHERE Id = ?";
	PreparedStatement statement = connection.prepareStatement(query);
	statement.setLong(1, historyEntry.getId());
	statement.executeUpdate();
    }

    @Override
    public HistoryEntry find(long id) throws SQLException {
	String query = "SELECT * FROM SearchHistory WHERE Id = ?";
	PreparedStatement statement = connection.prepareStatement(query);
	statement.setLong(1, id);
	ResultSet rset = statement.executeQuery();
	List<HistoryEntry> history = getElementsFromResultSet(rset);
	if (history != null) {
	    return history.get(0);
	}
	return null;
    }
    
    public List<HistoryEntry> getTopQueries(int amount) throws SQLException {
	String query = "SELECT * FROM SearchHistory ORDER BY Rating DESC LIMIT ?";
	PreparedStatement statement = connection.prepareStatement(query);
	statement.setInt(1, amount);
	ResultSet rset = statement.executeQuery();
	return getElementsFromResultSet(rset);
    }
    
    private PreparedStatement getInsertStatement(HistoryEntry historyEntry) 
	    throws SQLException {
	String query = "INSERT INTO SearchHistory(Query, Date, Rating) "
		+ "VALUES (?, ?, ?)";
	PreparedStatement statement = connection.prepareStatement(query);
	statement.setString(1, historyEntry.getQuery());
	statement.setDate(2, new java.sql.Date(historyEntry.getDate().getTime()));
	statement.setLong(3, historyEntry.getRating());
	return statement;
    }
    
    private PreparedStatement getUpdateStatement(HistoryEntry historyEntry) 
	    throws SQLException {
	String query = "UPDATE SearchHistory SET Query = ?, Date = ?, "
		+ "Rating = ? WHERE Id = ?";
	PreparedStatement statement = connection.prepareStatement(query);
	statement.setString(1, historyEntry.getQuery());
	statement.setDate(2, new java.sql.Date(historyEntry.getDate().getTime()));
	statement.setLong(3, historyEntry.getRating());
	statement.setLong(4, historyEntry.getId());
	return statement;
    }

    @Override
    protected List<HistoryEntry> getElementsFromResultSet(ResultSet rset) 
	    throws SQLException {
	List<HistoryEntry> history = new ArrayList<>();
	while (rset.next()) {
	    long id = rset.getLong("Id");
	    String query = rset.getString("Query");
	    long time = rset.getDate("Date").getTime();
	    java.util.Date date = new java.util.Date(time);
	    int rating = rset.getInt("Rating");
	    
	    history.add(new HistoryEntry(id, query, date, rating));
	}
	return history;
    }
}

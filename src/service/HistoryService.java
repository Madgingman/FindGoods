/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import logic.HistoryEntry;
import db.HistoryEntryMapper;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 *
 * @author M
 */
public class HistoryService {
    public static void addHistoryEntries(Collection<String> entries) throws SQLException {
	for (String entry : entries) {
	    addHistoryEntry(entry);
	}
    }
    
    public static void addHistoryEntry(String query) throws SQLException {
	HistoryEntryMapper mapper = new HistoryEntryMapper();
	String[] words = query.trim().split(" ");
	for (String word : words) {
	    HistoryEntry he = mapper.find(word);
	    if (he != null) {
		he.setDate(new Date());
		he.incrementRating();
		mapper.update(he);
	    } else {
		mapper.insert(new HistoryEntry(0, query, new Date(), 0));
	    }
	}
    }
    
    public static List<HistoryEntry> getTopQueries(int amount) throws SQLException {
	HistoryEntryMapper mapper = new HistoryEntryMapper();
	return mapper.getTopQueries(amount);
    }
}

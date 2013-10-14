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
import java.util.Date;
import java.util.Random;
import logic.HistoryEntry;
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
public class HistoryEntryMapperTest {
    private static final String QUERY = "testQuery" + new Random().nextInt();

    private static int heId;
    
    public HistoryEntryMapperTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
	final String query = "INSERT INTO SearchHistory(Query, Date, Rating) VALUES ('" + QUERY + "', '2011-07-18', 1)";
	try (Connection conn = new ConnectionManager().getConnection(); Statement statement = conn.createStatement()) {
	    statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
	    try (ResultSet keys = statement.getGeneratedKeys()) {
		if (keys == null || !keys.next()) {
		    fail("Fail to create new category");
		}
		heId = keys.getInt(1);
	    }
	} catch (SQLException ex) {
	    fail("Unexpected SQL exception: " + ex.getLocalizedMessage());
	}
    }
    
    @After
    public void tearDown() {
	final String query = "DELETE FROM SearchHistory WHERE SearchHistory.Id = " + heId;
	
	try (Connection conn = new ConnectionManager().getConnection(); Statement statement = conn.createStatement()) {
	    statement.executeUpdate(query);
	} catch (SQLException ex) {
	    fail("Unexpected SQL exception: " + ex.getLocalizedMessage());
	}
    }

    /**
     * Test of insert method, of class HistoryEntryMapper.
     */
    @Test
    public void testInsert() throws Exception {
	HistoryEntry historyEntry = new HistoryEntry(heId, "newHistoryEntryName" + new Random().nextInt(),
		new java.sql.Date(new Date().getTime()), 3);
	HistoryEntryMapper mapper = new HistoryEntryMapper();
	final int result = mapper.insert(historyEntry);
	assertTrue("Fail to insert new search history entry", result != -1);
    }

    /**
     * Test of update method, of class HistoryEntryMapper.
     */
    @Test
    public void testUpdate() throws Exception {
	HistoryEntryMapper mapper = new HistoryEntryMapper();
	HistoryEntry oldEntry = mapper.find(heId);
	HistoryEntry historyEntry = new HistoryEntry(heId, QUERY + new Random().nextInt(),
		oldEntry.getDate(), oldEntry.getRating());
	mapper.update(historyEntry);
	assertNull("Fail to update search history entry", mapper.find(QUERY));
    }

    /**
     * Test of delete method, of class HistoryEntryMapper.
     */
    @Test
    public void testDelete() throws Exception {
	HistoryEntry historyEntry = new HistoryEntry(0L, "newQuery" + new Random().nextInt(), new Date(), 1);
	
	HistoryEntryMapper mapper = new HistoryEntryMapper();
	final int id = mapper.insert(historyEntry);
	
	historyEntry.setId(id);
	mapper.delete(historyEntry);
	assertNull("Fail to delete search history entry", mapper.find(id));
    }

    /**
     * Test of find method, of class HistoryEntryMapper.
     */
    @Test
    public void testFindByQuery() throws Exception {
	assertNotNull("Fail to find search history entry by query", new HistoryEntryMapper().find(QUERY));
    }

    /**
     * Test of find method, of class HistoryEntryMapper.
     */
    @Test
    public void testFindById() throws Exception {
	assertNotNull("Fail to find search history entry by ID", new HistoryEntryMapper().find(heId));
    }
}

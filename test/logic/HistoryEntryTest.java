/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.util.Date;
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
public class HistoryEntryTest {
    private HistoryEntry hEntry;
    
    public HistoryEntryTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
	System.out.println("Testing HistoryEntry class");
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
	System.out.print("...testing method HistoryEntry#");
	
	hEntry = new HistoryEntry(0L, "testQuery", new Date(), 1L);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of incrementRating method, of class HistoryEntry.
     */
    @Test
    public void testIncrementRating() {
	System.out.println("incrementRating");
	
	long oldRating = hEntry.getRating();
	long newRating = oldRating + 1;
	hEntry.incrementRating();
	assertEquals("Expected " + newRating + ", got " + hEntry.getRating(),
		hEntry.getRating(), newRating);
    }

    /**
     * Test of updateDate method, of class HistoryEntry.
     */
    @Test
    public void testUpdateDate() {
	System.out.println("updateDate");
	
	Date currentDate = new Date();
	hEntry.updateDate();
	assertTrue("Expected date after '" + currentDate + "', got '" + hEntry.getDate() + "'",
		hEntry.getDate().getTime() >= currentDate.getTime());
    }

}

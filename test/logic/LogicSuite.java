/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 * @author M
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({logic.ProductTest.class, logic.HistoryEntryTest.class, logic.StringComparatorTest.class,
    logic.UserTest.class, logic.HashManagerTest.class, logic.CategoryTest.class})
public class LogicSuite {

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }
    
}

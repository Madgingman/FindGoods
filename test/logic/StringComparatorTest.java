/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import utils.StringComparator;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author M
 */
public class StringComparatorTest {

    /**
     * Test of compare method, of class StringComparator.
     */
    @Test
    public void testCompare() {
	System.out.println("CompareStrings test");
	
	String s1 = "123";
	String s2 = "123";
	
	double expResult = 0.0;
	double result = StringComparator.compare(s1, s2);
	
	assertEquals(expResult, result, 0.0);
    }
}

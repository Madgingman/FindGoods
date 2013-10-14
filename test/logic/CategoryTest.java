/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

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
public class CategoryTest {
    private static final String CategoryName = "testName";
    private static final long CategoryId = 1L;
    
    private Category category;
    
    public CategoryTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
	System.out.println("Testing Category class");
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
	System.out.print("...testing Category#");
	
	category = new Category(CategoryId, CategoryName);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getId method, of class Category.
     */
    @Test
    public void testGetId() {
	System.out.println("getId");
	
	long expResult = CategoryId;
	long result = category.getId();
	assertEquals(expResult, result);
    }

    /**
     * Test of setId method, of class Category.
     */
    @Test
    public void testSetId() {
	System.out.println("setId");
	long newId = 0L;
	
	category.setId(newId);
	assertEquals(newId, category.getId());
    }

    /**
     * Test of getName method, of class Category.
     */
    @Test
    public void testGetName() {
	System.out.println("getName");
	
	String expResult = CategoryName;
	String result = category.getName();
	assertEquals(expResult, result);
    }

    /**
     * Test of setName method, of class Category.
     */
    @Test
    public void testSetName() {
	System.out.println("setName");
	
	String newName = "newName";
	category.setName(newName);
	assertEquals(newName, category.getName());
    }
}

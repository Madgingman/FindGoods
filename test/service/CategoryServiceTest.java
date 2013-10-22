/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import db.CategoryMapper;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import logic.Category;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author M
 */
public class CategoryServiceTest {
    private static final String CategoryName = "testName";
    
    private Category category;
    
    public CategoryServiceTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
	System.out.println("Testing CategoryService class");
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
	System.out.print("...testing CategoryService#");
	
	category = new Category(1L, CategoryName);
	
	try {
	    int categoryId = CategoryService.addCategory(category);
	    category.setId(categoryId);
	} catch (SQLException ex) {
	    fail("Can't add new category in setUp. Unexpected SQL exception: " + ex.getLocalizedMessage());
	}
    }
    
    @After
    public void tearDown() {
	try {
	    if (category != null) {
		new CategoryMapper().delete(category);
	    }
	} catch (SQLException ex) {
	    fail("Can't delete category in tearDown. Unexpected SQL exception: " + ex.getLocalizedMessage());
	}
    }

    /**
     * Test of checkCategory method, of class CategoryService.
     */
    @Test
    public void testCheckCategory() throws Exception {
	System.out.println("checkCategory");

	boolean result = CategoryService.checkCategory(CategoryName);
	assertTrue("Fail to find category by name", result);
    }

    /**
     * Test of getCategoryNames method, of class CategoryService.
     */
    @Test
    public void testGetCategoryNames() throws Exception {
	System.out.println("getCategoryNames");
	
	List<String> categories = CategoryService.getCategoryNames();
	assertTrue("Got list of category names doesn't contain test category",
		categories.contains(CategoryName));
    }

    /**
     * Test of findCategory method, of class CategoryService.
     */
    @Test
    public void testFindCategoryByName() throws Exception {
	System.out.println("findCategory");

	Category gotCategory = CategoryService.findCategory(CategoryName);
	assertEquals(category, gotCategory);
    }

    /**
     * Test of findCategory method, of class CategoryService.
     */
    @Test
    public void testFindCategoryById() throws Exception {
	System.out.println("findCategory");

	Category gotCategory = CategoryService.findCategory(category.getId());
	assertEquals(category, gotCategory);
    }

    /**
     * Test of updateCategory method, of class CategoryService.
     */
    @Test
    public void testUpdateCategory() throws Exception {
	System.out.println("updateCategory");

	category.setName("NewCategoryName" + new Random().nextInt());
	CategoryService.updateCategory(category);
	
	Category gotCategory = CategoryService.findCategory(category.getId());
	assertEquals(category, gotCategory);
    }
}

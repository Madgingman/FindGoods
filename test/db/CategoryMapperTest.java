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
import java.util.Random;
import logic.Category;
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
public class CategoryMapperTest {
    private static final String CATEGORY_NAME = "testCatName" + new Random().nextInt();
    
    private static int categoryId;
    
    public CategoryMapperTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
	final String query = "INSERT INTO Categories (Name) VALUES ('" + CATEGORY_NAME + "')";
	
	try (Connection conn = new ConnectionManager().getConnection(); Statement statement = conn.createStatement()) {
	    statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
	    try (ResultSet keys = statement.getGeneratedKeys()) {
		if (keys == null || !keys.next()) {
		    fail("Fail to create new category");
		}
		categoryId = keys.getInt(1);
	    }
	} catch (SQLException ex) {
	    fail("Unexpected SQL exception: " + ex.getLocalizedMessage());
	}
    }
    
    @After
    public void tearDown() {
	final String query = "DELETE FROM Categories WHERE Categories.Id = ? OR Categories.Name = ?";
	
	try (Connection conn = new ConnectionManager().getConnection(); PreparedStatement statement = conn.prepareStatement(query)) {
	    statement.setInt(1, categoryId);
	    statement.setString(2, CATEGORY_NAME);
	    statement.executeUpdate();
	} catch (SQLException ex) {
	    fail("Unexpected SQL exception: " + ex.getLocalizedMessage());
	}
    }

    /**
     * Test of insert method, of class CategoryMapper.
     */
    @Test
    public void testInsert() throws Exception {
	Category category = new Category(0, "newCategoryName" + new Random().nextInt());
	CategoryMapper mapper = new CategoryMapper();
	categoryId = mapper.insert(category);
	assertTrue("Fail to insert new category", categoryId != -1);
    }

    /**
     * Test of update method, of class CategoryMapper.
     */
    @Test
    public void testUpdate() throws Exception {
	Category category = new Category(categoryId, CATEGORY_NAME + new Random().nextInt());
	CategoryMapper mapper = new CategoryMapper();
	mapper.update(category);
	assertNull("Fail to update category", mapper.find(CATEGORY_NAME));
    }

    /**
     * Test of delete method, of class CategoryMapper.
     */
    @Test
    public void testDelete() throws Exception {
	Category category = new Category(0, "newCategoryName" + new Random().nextInt());
	
	CategoryMapper mapper = new CategoryMapper();
	final int id = mapper.insert(category);
	
	category.setId(id);
	mapper.delete(category);
	assertNull("Fail to delete category", mapper.find(id));
    }

    /**
     * Test of find method, of class CategoryMapper.
     */
    @Test
    public void testFindById() throws Exception {
	assertNotNull("Fail to find category by ID", new CategoryMapper().find(categoryId));
    }

    /**
     * Test of find method, of class CategoryMapper.
     */
    @Test
    public void testFindByName() throws Exception {
	assertNotNull("Fail to find category by name", new CategoryMapper().find(CATEGORY_NAME));
    }

    /**
     * Test of getAll method, of class CategoryMapper.
     */
    @Test
    public void testGetAll() throws Exception {
	assertFalse("Fail to resolve list of all categories", new CategoryMapper().getAll().isEmpty());
    }
}

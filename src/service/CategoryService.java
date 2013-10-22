/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import db.CategoryMapper;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import logic.Category;

/**
 *
 * @author M
 */
public class CategoryService {
    public static boolean checkCategory(String name) throws SQLException {
	return (new CategoryMapper().find(name) != null);
    }
    
    public static List<String> getCategoryNames() throws SQLException {
	List<Category> categories = new CategoryMapper().getAll();
	if (categories.isEmpty()) {
	    return new ArrayList<>();
	}
	
	ArrayList<String> categoryNames = new ArrayList<>();
	for (Category category : categories) {
	    categoryNames.add(category.getName());
	}
	
	return categoryNames;
    }
    
    public static Category findCategory(String name) throws SQLException {
	return new CategoryMapper().find(name);
    }
    
    public static Category findCategory(Long id) throws SQLException {
	return new CategoryMapper().find(id);
    }
    
    public static int addCategory(Category category) throws SQLException {
	return new CategoryMapper().insert(category);
    }
    
    public static void updateCategory(Category category) throws SQLException {
	new CategoryMapper().update(category);
    }
}

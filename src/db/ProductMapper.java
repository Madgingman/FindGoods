/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import logic.Category;
import logic.Product;
import logic.Store;
import utils.StringComparator;
import service.CategoryService;
import service.StoreService;

/**
 *
 * @author M
 */
public class ProductMapper extends AbstractMapper<Product> {

    @Override
    public int insert(Product product) throws SQLException {
	try (Connection conn = getConnection(); PreparedStatement statement = getInsertStatement(product, conn)) {
	    statement.executeUpdate();
	    try (ResultSet keys = statement.getGeneratedKeys()) {
		if (keys == null || !keys.next()) {
		    return -1;
		}
		return keys.getInt(1);
	    }
	}
    }

    @Override
    public void update(Product product) throws SQLException {
	try (Connection conn = getConnection(); PreparedStatement statement = getUpdateStatement(product, conn)) {
	    statement.executeUpdate();
	}
    }

    @Override
    public void delete(Product product) throws SQLException {
	String query = "DELETE FROM Products WHERE Id = ?";
	try (Connection conn = getConnection(); PreparedStatement statement = conn.prepareStatement(query)) {
	    statement.setLong(1, product.getId());
	    statement.executeUpdate();
	}
    }

    @Override
    public Product find(long id) throws SQLException {
	final String query = "SELECT * FROM Products WHERE Id = ?";

	List<Product> products;
	try (Connection conn = getConnection(); PreparedStatement statement = conn.prepareStatement(query)) {
	    statement.setLong(1, id);
	    try (ResultSet rset = statement.executeQuery()) {
		products = getElementsFromResultSet(rset);
	    }
	}
	
	if (products == null || products.isEmpty()) {
	    return null;
	}

	Product product = products.get(0);
	product.addAllProperties(getProductProperties(product));
	product.addAllRatedUsers(getRatedUsers(product.getId()));

	return product;
    }
    
    public List<Product> find(ProductFilter filter) throws SQLException {
	final String query = "SELECT * FROM Products WHERE " + filter.getCondition();
	
	List<Product> products;
	try (Connection conn = getConnection();
		PreparedStatement statement = conn.prepareStatement(query.toString());
		ResultSet rset = statement.executeQuery()) {
	    products = getElementsFromResultSet(rset);
	}
	
	for (Product product : products) {
	    product.addAllProperties(getProductProperties(product));
	    product.addAllRatedUsers(getRatedUsers(product.getId()));
	}

	return products;
    }

    private PreparedStatement getInsertStatement(Product product, Connection conn) throws SQLException {
	String query = "INSERT INTO Products(Url, Image, StoreId, CategoryId, Name, Producer, Date, Price) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
	PreparedStatement statement = null;
	try {
	    statement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
	    statement.setString(1, product.getUrl().toExternalForm());
	    statement.setString(2, product.getImage().toExternalForm());
	    statement.setLong(3, product.getStore().getId());
	    statement.setLong(4, product.getCategory().getId());
	    statement.setString(5, product.getName());
	    statement.setString(6, product.getProducer());
	    statement.setDate(7, new java.sql.Date(product.getProductionDate().getTime()));
	    statement.setDouble(8, product.getPrice());
	    return statement;
	} catch (SQLException ex) {
	    statement.close();
	    throw ex;
	}
    }

    private PreparedStatement getUpdateStatement(Product product, Connection conn) throws SQLException {
	String query = "UPDATE Products SET Url = ?, Image = ?, StoreId = ?, CategoryId = ?, Name = ?, Producer = ?, Date = ?, Price = ? WHERE Id = ?";
	PreparedStatement statement = null;
	try {
	    statement = conn.prepareStatement(query);
	    statement.setString(1, product.getUrl().toExternalForm());
	    statement.setString(2, product.getImage().toExternalForm());
	    statement.setLong(3, product.getStore().getId());
	    statement.setLong(4, product.getCategory().getId());
	    statement.setString(5, product.getName());
	    statement.setString(6, product.getProducer());
	    statement.setDate(7, new java.sql.Date(product.getProductionDate().getTime()));
	    statement.setDouble(8, product.getPrice());
	    statement.setLong(9, product.getId());
	    return statement;
	} catch (SQLException ex) {
	    statement.close();
	    throw ex;
	}
    }

    @Override
    protected List<Product> getElementsFromResultSet(ResultSet rset) throws SQLException {
	List<Product> products = new ArrayList<>();
	while (rset.next()) {
	    Product product = getElement(rset);
	    if (product != null) {
		products.add(product);
	    }
	}
	return products;
    }
    
    protected Product getElement(final ResultSet rset) throws SQLException {
	long id = rset.getLong("Id");
	URL url, image;
	try {
	    url = new URL(rset.getString("Url"));
	    image = new URL(rset.getString("Image"));
	} catch (MalformedURLException ex) {
	    return null;
	}
	String name = rset.getString("Name");
	String producer = rset.getString("Producer");
	long time = rset.getDate("Date").getTime();
	Date date = new Date(time);
	double price = rset.getDouble("Price");
	
	Store store = StoreService.getStore(rset.getLong("StoreId"));
	if (store == null) {
	    return null;
	}
	
	Category category = CategoryService.findCategory(rset.getLong("CategoryId"));
	if (category == null) {
	    return null;
	}

	return new Product(id, url, image, store, name, category, producer, date, price, null, null);
    }
    
    protected List<Product> getSimilarElementsFromResultSet(ResultSet rset, String name, 
	    double factor, double delta) throws SQLException {
	List<Product> products = new ArrayList<>();
	while (rset.next()) {
	    String prodName = rset.getString("Name");
	    Double prodDelta = Math.abs(StringComparator.compare(name, prodName) - factor);
	    if (prodDelta.compareTo(delta) != 0) {
		continue;
	    }
	    
	    Product product = getElement(rset);
	    if (product != null) {
		products.add(product);
	    }
	}
	return products;
    }

    public Double getRating(long productId) throws SQLException {
	String query = "SELECT AVG(Rate) FROM Rates WHERE ProductId = ?";

	try (Connection conn = getConnection(); PreparedStatement statement = conn.prepareStatement(query)) {
	    statement.setLong(1, productId);
	    
	    try (ResultSet rset = statement.executeQuery()) {
		if (rset.next()) {
		    return new Double(rset.getDouble(1));
		}
	    }

	    return null;
	}
    }
    
    public void addRate(Long productId, Long userId, int rate) throws SQLException {
	String query = "INSERT INTO Rates(ProductId, UserId, Rate) VALUES (?, ?, ?)";
	
	try (Connection conn = getConnection(); PreparedStatement statement = conn.prepareStatement(query)) {
	    statement.setLong(1, productId);
	    statement.setLong(2, userId);
	    statement.setInt(3, rate);
	    statement.executeUpdate();
	}
    }

    private Map<String, String> getProductProperties(Product product) throws SQLException {
	return new HashMap<>();
    }

    private Set<Long> getRatedUsers(long productId) throws SQLException {
	String query = "SELECT Rates.UserId FROM Rates WHERE Rates.ProductId = ?";

	try (Connection conn = getConnection(); PreparedStatement statement = conn.prepareStatement(query)) {
	    statement.setLong(1, productId);
	    
	    HashSet<Long> users = new HashSet<>();
	    try (ResultSet rset = statement.executeQuery()) {
		while (rset.next()) {
		    users.add(rset.getLong("UserId"));
		}
	    }

	    return users;
	}
    }
    
}

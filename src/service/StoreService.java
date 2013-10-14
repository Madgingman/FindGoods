/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import logic.Store;
import db.StoreMapper;
import java.sql.SQLException;

/**
 *
 * @author M
 */
public class StoreService {
    public static Store getStore(long id) throws SQLException {
	StoreMapper sm = new StoreMapper();
	Store store = sm.find(id);
	return store;
    }
    public static Store getStore(String name) throws SQLException {
	StoreMapper sm = new StoreMapper();
	Store store = sm.findByParam(StoreMapper.StoreParams.NAME, name);
	return store;
    }
    public static Store getStore(java.net.URL url) throws SQLException {
	StoreMapper sm = new StoreMapper();
	Store store = sm.findByParam(StoreMapper.StoreParams.URL, url.toExternalForm());
	return store;
    }
    
    /**
     * Saves specified store in the database
     * @param store a Store object to add to database
     * @return 0 if store was added successfully, -1 otherwise
     * @throws SQLException 
     */
    public static int addStore(Store store) throws SQLException {
	StoreMapper sm = new StoreMapper();
	if (sm.findByParam(StoreMapper.StoreParams.NAME, store.getName()) != null) {
	    return -1;
	}
	sm.insert(store);
	return 0;
    }
    
    public static void removeStore(Store store) throws SQLException {
	StoreMapper sm = new StoreMapper();
	sm.delete(store);
    }
    
    public static void updateStore(Store store) throws SQLException {
	StoreMapper sm = new StoreMapper();
	sm.update(store);
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 *
 * @author M
 */
public class User {
    protected long id;
    protected String name;
    protected String surname;
    protected String email;
    protected String login;
    protected String passHashCode;
    protected HashSet<Long> favoriteProducts;

    /**
     * @throws IllegalArgumentException if name or login is null or 
     * less than 3 symbols long
     */
    public User(long id, String name, String surname, String email, String login,
	    String password, Set<Long> favoriteProducts) 
	    throws IllegalArgumentException {
        if (name == null) {
            throw new IllegalArgumentException("Illegal name.");
        } else if (login == null || login.length() < 3) {
            throw new IllegalArgumentException("Illegal login.");
        }
        
	this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.login = login;
        this.passHashCode = HashManager.getMD5Hash(password);
	
	this.favoriteProducts = new HashSet<>();
	if (favoriteProducts != null) {
	    this.favoriteProducts.addAll(favoriteProducts);
	}
    }
    
    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.passHashCode = HashManager.getMD5Hash(password);
    }
    
    /**
     * Compares MD5 hashes of the given password to the user password.
     * The result is true if and only if the argument is not null and its MD5 hash
     * is equal to the MD5 hash of the user password, i.e. if it is a String object
     * that represents the same sequence of characters as user password.
     * @return true if MD5 hashes are equal, false otherwise
     */
    public boolean comparePassword(String password) {
	String md5hash = HashManager.getMD5Hash(password);
        return this.passHashCode.equals(md5hash);
    }

    public String getPassHashCode() {
	return this.passHashCode;
    }
    
    public void setPassHashCode(String passHashCode) {
        this.passHashCode = passHashCode;
    }

    public Set<Long> getFavoriteProducts() {
	return favoriteProducts;
    }

    public void addAllProductToFavorites(Set<Long> favoriteProducts) {
	if (favoriteProducts != null) {
	    this.favoriteProducts.addAll(favoriteProducts);
	}
    }
    
    public void addProductToFavorites(Long productId) {
	this.favoriteProducts.add(productId);
    }
    
    public void removeProductFromFavorites(Long productId) {
	this.favoriteProducts.remove(productId);
    }
    
    @Override
    public boolean equals(Object obj) {
	if (obj instanceof User) {
	    User user = (User) obj;
	    if (user.getId() == this.id && user.getLogin().equals(this.login)
		    && user.getName().equals(this.name) && user.getSurname().equals(this.surname)
		    && user.getEmail().equals(this.email)) {
		return true;
	    }
	}
	return false;
    }

    @Override
    public int hashCode() {
	int hash = 7;
	hash = 97 * hash + (int) (this.id ^ (this.id >>> 32));
	hash = 97 * hash + Objects.hashCode(this.name);
	hash = 97 * hash + Objects.hashCode(this.surname);
	hash = 97 * hash + Objects.hashCode(this.email);
	hash = 97 * hash + Objects.hashCode(this.login);
	return hash;
    }

    public void clearFavoriteProducts() {
	this.favoriteProducts.clear();
    }

    public void setId(long id) {
	this.id = id;
    }
}

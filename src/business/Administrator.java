/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

/**
 *
 * @author M
 */
public class Administrator extends User {
    /**
     * @throws IllegalArgumentException if name, login or password is null or 
     * less than 3 symbols long
     */
    public Administrator(long id, String name, String surname, String email, String login, String password) 
	    throws IllegalArgumentException {
	super(id, name, surname, email, login, password);
    }
    
    public boolean addStore(String name, java.net.URL url) {
	return true;
    }
}

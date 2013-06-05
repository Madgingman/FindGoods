/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

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

    /**
     * @throws IllegalArgumentException if name or login is null or 
     * less than 3 symbols long
     */
    public User(long id, String name, String surname, String email, String login, String password) 
	    throws IllegalArgumentException {
        if (name == null || name.length() < 3) {
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
     * @return 'true' if password's hashcode equals to passHashCode, 
     * 'else' otherwise
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
    
}

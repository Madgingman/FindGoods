/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.util.Collection;
import logic.User;

/**
 *
 * @author M
 */
public class UserUtils {

    public static User getUserFromList(Collection<? extends User> users, Long userId) {
	for (User user : users) {
	    if (user.getId() == userId) {
		return user;
	    }
	}
	
	return null;
    }
    
}

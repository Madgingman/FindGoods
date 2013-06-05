/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author M
 */
public class HashManager {
    
    public static String getMD5Hash(String s) {
	String md5Hash = null;
	try {
	    MessageDigest m = MessageDigest.getInstance("MD5");
	    m.update(s.getBytes());
	    md5Hash = new BigInteger(1, m.digest()).toString(16);
	} catch (NoSuchAlgorithmException ex) {
	    Logger.getLogger(HashManager.class.getName()).log(Level.SEVERE, null, ex);
	}

	return md5Hash;
    }
}

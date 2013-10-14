/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

/**
 *
 * @author M
 */
public enum UserTypesEnum {
    User(0, "Пользователь"), Administrator(1, "Администратор");
    
    private int value;
    private String description;

    private UserTypesEnum(int value, String description) {
	this.value = value;
	this.description = description;
    }

    public int getValue() {
	return this.value;
    }

    public String getDescription() {
	return this.description;
    }
    
    @Override
    public String toString() {
	return this.description;
    }
    
}

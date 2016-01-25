package edu.calvin.csw61.finalproject;

/**
 * Key implements the ObjectInterface and allows the Player to unlock a Door.
 */
public class Key implements ObjectInterface {
	String myName;  //Name of ObjectInterface
	
	/**
	 * Constructor for the Key class.
	 * @param: name, a String representing the name of the ObjectInterface. 
	 *         (It could be used to make keys for specific Rooms, like
	 *         SB 14 Key. But, we use it only to make a generic key
	 *         by passing "key" in).
	 */
	public Key(String name) {
		this.myName = name.toLowerCase();
	}
	
	/**
	 * Accessor for the name of the Key.
	 */
	public String getName() {
		return myName;
	}
}
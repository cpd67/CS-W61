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
	 *         SB 14 Key. We name the last Room key, "special key").
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
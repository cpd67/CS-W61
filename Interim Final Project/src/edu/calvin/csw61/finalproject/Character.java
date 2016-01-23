package edu.calvin.csw61.finalproject;

/**
 * Character class is the superclass for the NPC and Monster classes.
 */
public class Character {
	
	//Public instance variables
	//Name of the Character
	String myName;
	//ObjectInterface that the Character can have
	ObjectInterface myObj; 
	
	/**
	 * Accessor method for the Character's name.
	 * @return myName, a String representing the name of the Character.
	 */
	public String getName() {
		return myName;
	}
	
	/**
	 * Accessor method for the ObjectInterface that the Character has.
	 * @return myObj, a 
	 */
	public ObjectInterface getObject() {
		return myObj;
	}
}

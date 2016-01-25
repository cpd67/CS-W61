package edu.calvin.csw61.finalproject;

/**
 * Treasure implements the ObjectInterface and allows a Player to distinguish
 * between an ObjectInterface and a Treasure ObjectInterface.
 */
public class Treasure implements ObjectInterface {
	//Name of Treasure
	private String myName;
	
   /**
	* Constructor for the Treasure class. 
	* @param: name, a String representing the name of the Treasure object.
	*/
	public Treasure(String name) {
		this.myName = name;
	}
	
	/**
	 * Accessor for the name of the Treasure object.
	 * @return: myName, a String representing the name of the Treasure object.
	 */
	public String getName() {
		return myName;
	}
}
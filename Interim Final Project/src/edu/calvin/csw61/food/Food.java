package edu.calvin.csw61.food;

import edu.calvin.csw61.finalproject.ObjectInterface;

/**
 * Food is the abstract superclass of all Food objects that implements the ObjectInterface
 * so that Food objects can be stored in Rooms, held by NPCs and Monsters, and stored
 * in a Player's backpack.
 */
public abstract class Food implements ObjectInterface {
	//Name of the Food object
	String myName;
	//Health that it gives the Player
	int myHealth;
	
	/**
	 * Accessor for the health that the Food object gives to the Player.
	 * @return: myHealth, an int representing how much health is given to the Player
	 *          when the Food object is consumed.
	 */
	public int getHealth() {
		return myHealth; 
	}

	/**
	 * Accessor for the name of the Food object.
	 * @return: myName, a String representing the name of the Food object.
	 */
	public String getName() {
		return myName;
	}
}
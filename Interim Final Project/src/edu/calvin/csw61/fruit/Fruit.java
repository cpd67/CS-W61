package edu.calvin.csw61.fruit;

import edu.calvin.csw61.finalproject.ObjectInterface;

/**
 * Fruit is the abstract superclass of all Fruit objects that implements the ObjectInterface
 * so that Fruit objects can be stored in Rooms, held by NPCs and Monsters, and stored
 * in a Player's backpack.
 */
public abstract class Fruit implements ObjectInterface {
	//Name of the Fruit object
	String myName;
	//Health that it gives the Player
	int myHealth;
	
	/**
	 * Accessor for the health that the Fruit object gives to the Player.
	 * @return: myHealth, an int representing how much health is given to the Player
	 *          when the Fruit object is consumed.
	 */
	public int getHealth() {
		return myHealth;
	}

	/**
	 * Accessor for the name of the Fruit object.
	 * @return: myName, a String representing the name of the Fruit object.
	 */
	@Override
	public String getName() {
		return myName;
	}
}
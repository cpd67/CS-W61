package edu.calvin.csw61.finalproject;

/**
 * ObjectInterface is the common Interface that almost every Object will 
 * implement.
 * This allows us to adhere to the "Programming to an Interface" principle.
 * Rooms, NPCs, and Monsters can have ObjectInterfaces.
 * Players have a backpack of ObjectInterface objects.
 * (They can only hold 10 at a time).
 */
public interface ObjectInterface {
	//Get the name of the ObjectInterface object
	public String getName();
}

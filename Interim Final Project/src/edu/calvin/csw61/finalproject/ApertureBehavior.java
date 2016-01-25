package edu.calvin.csw61.finalproject;

/**
 * ApertureBehavior is the Interface that Doors and WallBehaviors will implement
 * so that they can behave like an aperture in a Room.
 */
public interface ApertureBehavior {
	//Set the material of the Aperture to breakable
	public void setBreakable();
	//Check if it's actually breakable
	public boolean isBreakable();
	//Get the name of the ApertureBehavior
	public String getName(); 
	//Get the direction of the ApertureBehavior in the Room
	public String getDir();
}

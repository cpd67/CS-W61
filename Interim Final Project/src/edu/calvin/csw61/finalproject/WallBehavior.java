package edu.calvin.csw61.finalproject;

/**
 * WallBehavior implements the ApertureBehavior Interface and mimics the 
 * behavior of a Wall in a Room.
 */
public class WallBehavior implements ApertureBehavior {
	
	//Name of ApertureBehavior
	private String myName;
	//Check if the WallBehavior is breakable
	private boolean isBreakable;
	//Direction of the WallBehavior in the Room
	private String myDirection;
	
	/**
	 * Constructor for the WallBehavior class.
	 * @param: name, a String representing the name for the ApertureBehavior.
	 * @param: dir, a String representing the direction of the WallBehavior in the Room.
	 */
	public WallBehavior(String name, String dir) {
		this.myName = name;
		this.myDirection = dir.toLowerCase();
		this.isBreakable = false;  //Not breakable at start
	}
	
	/**
	 * Check if the WallBehavior is breakable.
	 * @return: a boolean indicating whether or not the WallBehavior is breakable.
	 */
	public boolean isBreakable() {
		return isBreakable;
	}
	
	/**
	 * Accessor for the direction of the WallBehavior in the Room.
	 * @return: myDirection, the String representing the direction of the WallBehavior
	 *          in the Room.
	 */
	public String getDir() {
		return myDirection;
	}
	
	/**
	 * Accessor for the name of the ApertureBehavior.
	 * (In this case, "wall").
	 * @return: myName, the String representing the name of the ApertureBehavior.
	 */
	@Override
	public String getName() {
		return myName;
	}
	
	/**
	 * Mutator for setting a WallBehavior to be breakable.
	 */
	@Override
	public void setBreakable() {
		this.isBreakable = true;
	}
}
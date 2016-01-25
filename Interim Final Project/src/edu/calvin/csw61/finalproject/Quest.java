package edu.calvin.csw61.finalproject;

/**
 * Quest allows a Player to go on a Quest in the game!
 */
public class Quest {
	
	//Description of Quest
	private String myDescriptor;
	//Id of Quest
	private int myId;
	//The name of the ObjectInterface needed
	private String myNeededItem; 
	//The name of the Quest
	private String myName; 

	/**
	 * Constructor for the Quest class.
	 * @param: name, a String representing the name of the Quest.
	 * @param: descriptor, a String representing the description of the Quest.
	 * @param: id, an int representing the id number of the Quest.
	 * @param: obName, a String representing the necessary ObjectInterface.
	 */
	public Quest(String name, String descriptor, int id, String obName) {
		this.myDescriptor = descriptor;
		this.myId = id;
		this.myNeededItem = obName.toLowerCase();
		this.myName = name;
 	}

	/**
	 * Accessor for the name of the Quest's needed ObjectInterface.
	 * @return: myNeededItem, a String representing the name of the ObjectInterface needed.
	 */
	public String getItem() {
		return myNeededItem;
	}
	
	/**
	 * Accessor for the id number of the Quest.
	 * @return: myId, an int representing the id number of the Quest.
	 */
	public int getId() {
		return myId;
	}
	
	/**
	 * Accessor for the description of the Quest.
	 * @return: myDescriptor, a String representing the description for the Quest.
	 */
	public String getDescriptor() {
		return myDescriptor;
	}

	/**
	 * Accessor for the name of the Quest.
	 * @return: myName, a String representing the name of the Quest.
	 */
	public String getName() {
		return myName;
	}	
}
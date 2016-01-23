package edu.calvin.csw61.finalproject;

public class Quest {
	
	private String myDescriptor;
	private int myId;
	private String myNeededItem; //The Object needed
	private String myName; 
	
	//A Quest takes a description, id, Object, and a name
	public Quest(String name, String descriptor, int id, String obName) {
		myDescriptor = descriptor;
		myId = id;
		myNeededItem = obName.toLowerCase(); //The Object needed
		myName = name;
 	}

	//Get the Quest's needed Object
	public String getItem() {
		return myNeededItem;
	}
	
	//What number is it?
	public int getId() {
		return myId;
	}
	
	//How about a description?
	public String getDescriptor() {
		return myDescriptor;
	}

	//Get the name of the Quest
	public String getName() {
		return myName;
	}	
}

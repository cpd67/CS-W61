package edu.calvin.csw61.finalproject;

public class Quest {
	
	private String myDescriptor;
	private boolean isActive, isCompleted;
	private int myId;
	private ObjectInterface myNeededItem; //The Object needed
	private String myName; 
	
	//A Quest takes a description, id, Object, and a name
	public Quest(String descriptor, int id, ObjectInterface ob, String name) {
		myDescriptor = descriptor;
		myId = id;
		isActive = false;
		isCompleted = false;
		myNeededItem = ob; //The Object needed
		myName = name;
 	}
	
	//Is the Quest done?
	public boolean isCompleted() {
		return isCompleted;
	}
	
	//Get the Quest's needed Object
	public ObjectInterface getItem() {
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
	
	//Is the Quest still active?
	public boolean isActive() {
		return isActive;
	}
	
	//Sets the active state to true
	public void setActive() {
		isActive = true;
		isCompleted = false;
	}
	
	//Sets the completed state to true
	public void setCompleted() {
		isCompleted = true;
		isActive = false;
	}
	
}

package edu.calvin.csw61.finalproject;

public class Quest {
	
	private String myDescriptor;
	private boolean isActive, isCompleted;
	private int myId;
	
	public Quest(String descriptor, int id) {
		myDescriptor = descriptor;
		myId = id;
		isActive = false;
		isCompleted = false;
	}
	
	//Sets the active state to true
	public void setActive() {
		isActive = true;
	}
	
	//Sets the completed state to true
	public void setCompleted() {
		isCompleted = true;
	}
	
}

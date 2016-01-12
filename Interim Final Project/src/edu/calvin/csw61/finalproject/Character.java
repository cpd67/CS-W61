package edu.calvin.csw61.finalproject;

public class Character {
	
	//Public instance variable
	String myName;
	ObjectInterface myObj; //A Character can have an Item. Even Monsters (Keys and Treasure).
	
	//Public getter method
	public String getName() {
		return myName;
	}
	
	//Get the object
	public ObjectInterface getObject() {
		return myObj;
	}
}

package edu.calvin.csw61.finalproject;

//Needs to implement ObjectInterface so that we can catch when a Player wants
//to eat an NPC
public class NPC extends Character implements ObjectInterface {
	//Instance variables are inheirted from the superclass, Character
	//Constructor for an NPC who doesn't have an Object
	public NPC(String name) {
		myName = name;
		myObj = null;  
	}
	
	//Constructor for an NPC who doesn't have an Object
	public NPC(String name, ObjectInterface ob) {
		myName = name;
		myObj = ob;
	}
	
	@Override
	public String getName() {
		return myName;
	}
	
}

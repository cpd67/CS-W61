package edu.calvin.csw61.finalproject;

//We need to implement the ObjectInterface so that we can catch when a Player
//wants to eat a Monster
public class Monster extends Character implements ObjectInterface {
	//Instance variables are inheirted from the superclass, Character
	//Constructor for a Monster without an Object
	public Monster(String name) {
		myName = name;
		myObj = null;
	}

	//Constructor for a Monster with an Object
	public Monster(String name, ObjectInterface ob) {
		myName = name;
		myObj = ob;
	}
	
	@Override
	public String getName() {
		return myName;
	}
}

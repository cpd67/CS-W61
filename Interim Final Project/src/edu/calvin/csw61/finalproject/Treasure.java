package edu.calvin.csw61.finalproject;

public class Treasure implements ObjectInterface {
	String myName;
	
	//Treasures can be: Bracelets, Necklaces, and Rings.
	public Treasure(String name) {
		myName = name;
	}
	
	public String getName() {
		return myName;
	}
	
}

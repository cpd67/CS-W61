package edu.calvin.csw61.finalproject;

public class Key implements ObjectInterface {
	String myName;
	
	//Don't have to set a different name for a Key.
	public Key() {
		myName = "Key";
	}
	
	public String getName() {
		return myName;
	}
	
}

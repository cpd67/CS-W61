package edu.calvin.csw61.finalproject;

public class Key implements ObjectInterface {
	String myName;
	String instruction, objectToActOn;
	
	public Key(String name) {
		myName = "Key";
		instruction = "";
		objectToActOn = name;
	}
	
	public String getName() {
		return myName;
	}
}

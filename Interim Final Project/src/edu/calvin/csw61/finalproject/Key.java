package edu.calvin.csw61.finalproject;

public class Key implements ObjectInterface {
	String myName;
	String instruction, objectToActOn;
	
	public Key(String name) {
		myName = "key";
		instruction = "";
		objectToActOn = name;
	}
	
	public String getName() {
		return myName;
	}
}

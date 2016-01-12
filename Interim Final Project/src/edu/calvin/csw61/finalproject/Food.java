package edu.calvin.csw61.finalproject;

public class Food implements ObjectInterface {
	String myName;
	
	//Different food items = different names
	public Food(String name) {
		myName = name;
	}
	
	public String getName() {
		return myName;
	}
	
}

package edu.calvin.csw61.finalproject;

import java.util.ArrayList;

public class Food implements ObjectInterface {
	String myName;
	//Static initializers
	//Poisoned food
	private static ArrayList<String> isPoisoned = new ArrayList<String>(); 	
	//The reason why we can't just check if the noun is a valid food item 
	//is because the noun is considered as an ObjectInterface. It can be 
	//anything. We have to method call check what kind of ObjectInterface it is.
	//We could check if it is a valid food name using a static array list of 
	//food items and seeing if the noun is in there. If not, we still have to 
	//determine what the heck it is and handle it accordingly.

/**	public static boolean isFood(String name) {
		if(validFood.contains(name.toLowerCase())) {
			return true;
		} 
		return false;
	} 
  */
		
	//Different food items = different names
	public Food(String name) {
		myName = name;
	}
	
	//What's my name?
	public String getName() {
		return myName;
	}

	//Check if I am edible
	public boolean hasPoison() {
		if(isPoisoned.contains(myName)) {
			return true;
		}  //Else, stays false
		return false;
		
	}

	//Class methods
	//The Food class will have a list of what edible food is poisoned
	//This method adds that to the list
	//That way, we can have an extensible list of what can poison the Player
	//and add on to it as much as we want
	public static void addPoisonedFood(String name) {
		isPoisoned.add(name);
	}
	
}

package edu.calvin.csw61.food;

import java.util.ArrayList;
import edu.calvin.csw61.finalproject.*;

public abstract class Food implements ObjectInterface {
	String myName;
	int myHealth;
	//The reason why we can't just check if the noun is a valid food item 
	//is because the noun is considered as an ObjectInterface. It can be 
	//anything. We have to method call check what kind of ObjectInterface it is.
	//We could check if it is a valid food name using a static array list of 
	//food items and seeing if the noun is in there. If not, we still have to 
	//determine what the heck it is and handle it accordingly.
	
	//How much health do I give?
	public int getHealth() {
		return myHealth; 
	}

	//Something I learned: You can have an abstract class implement an interface.
	//You also don't have to implement ALL of the methods of the interface in the 
	//abstract class.
	//The subclasses will be forced to implement the methods that the abstract superclas
	//left out, however. 
	public String getName() {
		return myName;
	}
	
}

package edu.calvin.csw61.finalproject;

import java.util.HashMap;
import java.util.Map;

public class Player extends Character {
	
	//Give him some health
	private int myHealth;
	private int myNumberOfObjects;  //Number of objects one currently has
	private int myLimit; //Limit on number of Objects one can have in the backpack
	private Map<String, ObjectInterface> myBackpack;  //Backpack
	
	//Constructor
	public Player() {
		myName = "Flying Dutchman";
		myHealth = 100;
		myBackpack = new HashMap<>();
		myNumberOfObjects = 0;
		myLimit = 10;
		
		//myMapPieces? 
	}
	
	//Executes commands
	public void executeCommand(String verb, String noun) {
		switch(verb.toLowerCase()) {
			case "walk": case "go":
				System.out.println("you walked");
				break;
			case "eat":
				if(noun.equals("")) {  //Food item not given...
					System.out.println("Eat what?");
				} else {
					ObjectInterface food = new Food(noun);  //Create the food item
					Eat eatEvent = new Eat(food, this); //Eat it
					eatEvent.execute();
				}
	
		//		food.handleCommand(verb);
		//		System.out.println(food.getInstruction());
				
				break;
			case "fight": case "hit":
				System.out.println("you fought");
				break;
			case "take": case "get":
				System.out.println("you have obtained");
				break;
			case "give":
				System.out.println("you gave");
				break;
			case "lock":
				if(noun.equals("")) {
					System.out.println("Lock what?");
				} else {
					Lock lockEvent = new Lock(this);  //NEEDS TO TAKE IN A DOOR OBJECT
					lockEvent.execute();
				}
				break;
			case "unlock":
				if(noun.equals("")) {
					System.out.println("Unlock what?");
				} else {
					Unlock unlockEvent = new Unlock(this);  //NEEDS TO TAKE IN A DOOR OBJECT
					unlockEvent.execute();
				}
				break;
			case "break":
				System.out.println("you broke");
				break;
			case "use":
				System.out.println("you used");
				break;
			case "drop":
				System.out.println("you dropped");
				break;
			case "throw":
				System.out.println("you threw");
				break;
			case "dig":
				System.out.println("you dug");
				break;
			case "show":  //Show the backpack
				printBackpack();
				break;
			default: System.out.println("I don't know what that means.");
		}
	}
	
	//Add an Object
	public void addObject(String name, ObjectInterface ob) {
		if(myNumberOfObjects == myLimit) {  //Backpack full; can't add a new Object
			System.out.println("Backpack is full! Drop an item!");
		} else {
			myBackpack.put(name, ob);
			myNumberOfObjects++;  //Number of Objects in map
		}
	}
	
	//Removes an Object from the backpack
	public void removeObject(String object) {
		if(myNumberOfObjects == 0) { //If we have no Objects...
			System.out.println("You have no items to remove.");
		} else {  //We do...
			if(hasItem(object)) {  //If it's in the backpack...
				myBackpack.remove(object);  //Take it out
				myNumberOfObjects--;  //We have one less Object
			} else {
				System.out.println("You don't have this item.");  //Nope
			}
		}
	}
	
	//In case a User wants to know what they currently have in their backpack
	public void printBackpack() {
		if(myNumberOfObjects == 0) { //Empty backpack = No items!
			System.out.println("You have no items!");
		} else {  //You have items! 
			System.out.println("You currently have these items: ");
			for(String name : myBackpack.keySet()) {  //For each String in the key set...
				System.out.println(name);  //Print the name
			}
		}
	}

	//Do I have an item in my backpack?
	public boolean hasItem(String item) {
		if(myNumberOfObjects == 0) {  //If we have no Objects in the backpack...
			return false;
		}
		
		if(myBackpack.containsKey(item)) {  //We have it
			return true;
		}
		
		return false;   //We don't have it
	}
	
}

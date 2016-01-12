package edu.calvin.csw61.finalproject;

import java.util.ArrayList;

public class Player extends Character {
	
	//Give him some health
	private int myHealth;
	private ArrayList<ObjectInterface> myBackpack;  //Current possessions (number of Objects one has)
	private int myNumberOfObjects;  //Number of objects one currently has
	private int myLimit; //Limit on number of Objects one can have in the backpack
	
	//Constructor
	public Player() {
		myName = "Flying Dutchman";
		myHealth = 100;
		myBackpack = new ArrayList<ObjectInterface>();
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
				ObjectInterface food = new Food(noun);
				food.handleCommand(verb);
				System.out.println(food.getInstruction());
				
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
				ObjectInterface lockKey = new Key(noun);
				lockKey.handleCommand(verb);
				System.out.println(lockKey.getInstruction());
				break;
			case "unlock":
				ObjectInterface unlockKey = new Key(noun);
				unlockKey.handleCommand(verb);
				System.out.println(unlockKey.getInstruction());
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
			default: System.out.println("I don't know what that means.");
		}
	}
	
	//Add an Object
	public void addObject(ObjectInterface ob) {
		if(myNumberOfObjects == myLimit) {  //Backpack full; can't add a new Object
			System.out.println("Backpack is full! Drop an item!");
		} else {
			myBackpack.add(ob);
			myNumberOfObjects++;  //Also acts as an indicer and points to the next empty spot in backpack
		}
	}
	
	//In case a User wants to know what they currently have in their backpack
	public void printBackpack() {
		if(myNumberOfObjects == 0) { //Empty backpack = No items!
			System.out.println("You have no items!");
		} else {  //You have items! 
			System.out.println("You currently have these items: ");
			int i = 0;  
			while(i < myNumberOfObjects) {  //Has to be a while loop, otherwise we get a weird null pointer exception
				System.out.println(myBackpack.get(i).getName());
				i++;
			}
		}
	}

	public boolean hasItem(String item) {
		int i = 0;
		ObjectInterface holder; 
		if(myNumberOfObjects == 0) {
			return false;
		}
		
		while(i < myNumberOfObjects) {
			holder = myBackpack.get(i);
			if(holder.getName().toLowerCase().equals(item)) {  //make it lower case
				return true;
			}
			i++;
		}
		return false; 
	}
	
}

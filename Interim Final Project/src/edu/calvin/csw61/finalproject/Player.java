package edu.calvin.csw61.finalproject;

import java.awt.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Map.Entry;

public class Player extends Character {
	
	//Give him some health
	private int myHealth;
	private int myNumberOfObjects;  //Number of objects one currently has
	private int myLimit; //Limit on number of Objects one can have in the backpack
	private Hashtable<String, ObjectInterface> myBackpack;  //Backpack
	
	//Constructor
	public Player() {
		myName = "Flying Dutchman";
		myHealth = 100;
		myBackpack = new Hashtable<>();
		myNumberOfObjects = 0;
		myLimit = 10;
		
		//myMapPieces? 
	}
	
	//Executes commands
	public void setCommand(String verb, String noun) {
		Command command;  //This will be the executed command;
		switch(verb.toLowerCase()) {
			case "walk": case "go":  //Walk
				if(noun.equals("")) {
					System.out.println("Walk in which direction?");
				} else {
					command = new Walk(noun, this);
					command.execute();
				}
				break;
			case "eat":  //Eat
				if(noun.equals("")) {  //Food item not given...
					System.out.println("Eat what?");
				} else {
					if(this.hasItem(noun)) {  //If the Player has the Object...
						//Get the Object from the backpack
						command = new Eat(this.myBackpack.get(noun), this); //Eat it
						command.execute();  //Print out the returned String
					} else {  
						//Else, If the Object is in the Room that the Player is in...
						//Eat the Object (Get the Object from the list of Objects that the Room has)
						//If not, then...
						//The Player does NOT have the Object nor is he around it
						System.out.println("There is no " + noun + " around here."); 
					} 
				}		
				break;
			case "fight": case "hit":  //Fight
				if(noun.equals("")) {
					System.out.println("Fight what?");
				} else {
					System.out.println("you fought (NOT IMPLEMENTED)");
				}
				break;
			case "take": case "get": //Take
				if(noun.equals("")) {  //item not given...
					System.out.println("Take what?");
				} else {
					//Check if we can put the Object in our backpack...(if we can't, the Player will output a message)
					//if(this.checkItem(objectInRoom))...We can if it returns true
					//Okay, we can. Now, check if it's actually in the Room.
					//It's in the Room. Now check if it's already in our backpack...
					if(this.hasItem(noun)) { //If so...
						System.out.println("You can only have one " + noun + " in your backpack.");
					} else {  //Find the Object in the room and pass it as the ObjectInterface parameter to the Take() constructor
//						Key key = new Key("key");  //You would also have to remove the Object from the room.
//						command = new Take(key, this);
//						command.execute();
						System.out.println("You took (NOT IMPLEMENTED (NO ROOM TO TAKE OBJECTS FROM YET))");
					}
					//Not in the room, print "That Object isn't here."
				}
				break;
			case "give": //Give
				if(noun.equals("")) {  //HAVE TO CHECK IF THE NPC NEEDS THE OBJECT
					System.out.println("Give what?");  //AND ALSO IF THE BENEFICIARY IS AN NPC!
				} else {
					System.out.println("you gave (NOT IMPLEMENTED)");
				}
				break;
			case "lock": //Lock
				if(noun.equals("")) {
					System.out.println("Lock what?");
				} else {
					if(this.hasItem("key")) {  //Took out the check Object logic and brought it here
						command = new Lock(this);  //NEEDS TO TAKE IN A DOOR OBJECT
						command.execute();
					} else {
						System.out.println("You don't have a key.");
					}
				}
				break;
			case "unlock": //Unlock
				if(noun.equals("")) {
					System.out.println("Unlock what?");
				} else {
					if(this.hasItem("key")) {
						command = new Unlock(this);  //NEEDS TO TAKE IN A DOOR OBJECT
						command.execute();  //Execute the command
					} else {
						System.out.println("You don't have a key.");
					}
				}
				break;
			case "break":  //Break
				if(noun.equals("")) {
					System.out.println("Break what?");
				}
				System.out.println("you broke (NOT IMPLEMENTED)");
				break;
			case "use": //Use
				if(noun.equals("")) {
					System.out.println("Use what?");
				} else {
					System.out.println("you used (NOT IMPLMENETED)");
				}
				break;
			case "drop": //Drop
				if(noun.equals("")) {
					System.out.println("Drop what?");
				} else {
					System.out.println("you dropped (NOT IMPLEMENTED)");
				}
				break;
			case "throw":  //Throw
				if(noun.equals("")) {
					System.out.println("Throw what?");
				} else {
					System.out.println("you threw (NOT IMPLEMENTED)");
				}
				break;
			case "dig":  //Dig
				System.out.println("you dug (NOT IMPLEMENTED)");
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
			myBackpack.put(name.toLowerCase(), ob);
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
		//We have it
		if(myBackpack.containsKey(item.toLowerCase())) {  
			return true;
		}
		return false;   //We don't have it
	}
	
	//Can we put an item in our backpack?
	public boolean checkItem(ObjectInterface ob) {
		if(ob instanceof NPC) {
			return false;  //Can't put NPCs in bacpack
		} else if(ob instanceof Monster) {
			return false;  //Can't put Monsters in backpack
		}
		return true; //You can put it in your backpack
	}
}

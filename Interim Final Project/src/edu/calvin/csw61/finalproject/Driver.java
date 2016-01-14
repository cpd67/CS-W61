/**
 * We implement these patterns:
 * 	- Strategy
 * 	- Command
 */
package edu.calvin.csw61.finalproject;

import java.util.Scanner;

public class Driver {
	
	public static void main(String[] args) {
		System.out.println("Welcome to Lost in Knightdale!");
		story();  //Print out the story of the game
		String verb, noun;  //Holders for the words of the command
		Player p = new Player();  //Player
		
		//Test: Poisoning the Player (WORKS :D)
//		ObjectInterface bidoof = new Food("bidoof");
//		Food.addPoisonedFood("bidoof");  
		
//		p.addObject(bidoof.getName(), bidoof);
				
		//Test: Can I eat a Monster? (NOPE) 
		//ObjectInterface m = new Monster("Harry");
		//Since we don't have Rooms (yet), I have to add him to the backpack and see if I can eat him
		//p.addObject(m.getName(), m);
		
		//How about an NPC? (ALSO NOPE)
		//ObjectInterface n = new NPC("Mary");
		//Since we don't have Rooms (yet), I have to add her to the backpack and see if I can eat her
		//p.addObject(n.getName(), n);
		
		verb = "";
		noun = "";

		//Non-poisoned item
		ObjectInterface ob = new Food("apple");
		
		
		p.addObject(ob.getName(), ob); //Has to be the name of the object and the object itself
										//Reason is because we remove objects by name
										//Just because two objects have the same name does NOT mean they are the same object
										//At least in memory

		System.out.println("You have an " + ob.getName() + " in your backpack.");

		
		//Have to figure a way to stop the game if the Player dies...
		//Need a help menu to show commands
		
		
		//infinite loop
		while(true){
			//new scanner to get user input
			Scanner scanner = new Scanner(System.in);

			//read in the line
			String string = scanner.nextLine();
			
			//get the number of words in the string
			int wordCount = wordCount(string);
			
			if(wordCount >= 3){ //if the count is 3 or more
				System.out.println("Too many words"); //too many words
			} else if(wordCount == 1){ //else if the count is one,
				verb = string.trim();			//set the verb to the one word
				noun = "";			//and make the noun null
				p.setCommand(verb, noun);
			} else { //else split the string and put the first part in the noun and the second part in the verb.
				String[] parts = string.split("\\W+");
				verb = parts[0];
				noun = parts[1];
				p.setCommand(verb, noun);
			}

		}
	}
	
	/*
	 * This method counts the number of words in a string.
	 * (Adapted from StackOverflow)
	 */
	public static int wordCount(String s){
		String trim = s.trim();
		if (trim.isEmpty()){
			return 0;
		}
		return trim.split("\\W+").length; // separate string around spaces
	}

	//This will print the story...
	public static void story() {
		System.out.println("You have been capture by the Leroykins...(-INSERT MORE HERE-)");
	}
}

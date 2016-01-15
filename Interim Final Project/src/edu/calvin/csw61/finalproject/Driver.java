/**
 * We implement these patterns:
 * 	- Strategy
 * 	- Command
 */
package edu.calvin.csw61.finalproject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Driver {
	
	public static Map<Room, String> mySBRooms = new HashMap<Room, String>();
	
	public static void main(String[] args) {
		story();  //Print out the story of the game
		System.out.println();
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
		
		
		//Test: Rooms and setting NPCs and Monsters
		Room centerRoom = new Room(true, true, true, true, false, false);
		setRoomDescriptor("centerRoomDescription.txt", centerRoom); //set the description for the center room
		p.setCurrentRoom(centerRoom); //set the player's current room to the center room. There's probably a better way to do this, but this works temporarily
		Map<Room, String> testRooms = new HashMap<Room, String>();
		
		testRooms.put(centerRoom, centerRoom.getDescriptor());
		
			String[] names = {"Harold", "Sue", "Andrew"};
		
			int i = 0;
		    Iterator it = testRooms.entrySet().iterator();
		    while (it.hasNext()) {
		        Map.Entry<Room, String> pair = (Map.Entry)it.next();
		        //If the hasNPC is set for the Room...
		        pair.getKey().setNPC(names[i]);
		        System.out.println(pair.getValue());
		        it.remove(); // avoids a ConcurrentModificationException
		        i++;
		    }
		
		
		    
//		for(Map.Entry<Room, String> entry : testRooms.entrySet()) {
//			System.out.println(entry.getValue());
///			entry.getKey().setNPC("Andrew");

//		}
		
//		Room blah = testRooms.get("Center Room");
//		blah.setNPC("Andrew");
	
		
		
		verb = "";
		noun = "";

		//Non-poisoned item
		ObjectInterface ob = new Food("apple");
		
		
		p.addObject(ob.getName(), ob); //Has to be the name of the object and the object itself
										//Reason is because we remove objects by name
										//Just because two objects have the same name does NOT mean they are the same object
										//At least in memory

		System.out.println("You have an " + ob.getName() + " in your backpack.");

		
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
		String fileName = "src/edu/calvin/csw61/finalproject/text_files/story.txt";
		String line = null;
		
		try{
			FileReader fr = new FileReader(fileName); //FileReader reads text files in the default encoding.
			BufferedReader br = new BufferedReader(fr); //Always wrap FileReader in BufferedReader.
			while((line = br.readLine()) != null) { //read in each line
				System.out.println(line);	//and print it out
			}
			br.close(); //close the file
		}
		catch(FileNotFoundException ex) {
			System.out.println("Unable to open file.");
		
		}
		catch(IOException ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * This method sets the room description by reading from a text file.
	 * @param file: The filename.
	 * @param room: The room to apply the description to.
	 */
	public static void setRoomDescriptor(String file, Room room){
		String fileName = "src/edu/calvin/csw61/finalproject/text_files/" + file; //the filename
		String description = "";
		String line = null;
		
		try{
			FileReader fr = new FileReader(fileName); //FileReader reads text files in the default encoding.
			BufferedReader br = new BufferedReader(fr); //Always wrap FileReader in BufferedReader.
			while((line = br.readLine()) != null) { //read in each line
				description += line + "\n";			//set the description to the read lines, and add a newline after ever line
			}
			br.close(); //close the file
		}
		catch(FileNotFoundException ex) {
			System.out.println("Unable to open file.");
		
		}
		catch(IOException ex) {
			ex.printStackTrace();
		}
		
		//add the description to the room
		room.setDescriptor(description);
	}
	
	public static void loadGame() {
		loadRooms();
	}
	
	public static void loadRooms() {
		
	}
	
	public static void loadNPCs() {
		
	}
	
	public static void loadMonsters() {
		
	}
	
}

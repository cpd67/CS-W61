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

public class TestClass {
	//Maps of Rooms (Static and anyone can refer to them as long as we call Driver.mapOfRooms
	//This makes it a LOT easier to determine where we are
	
	//Each room will instead be numbered. This allows us to add NPCs and Monsters in the 
	//order in which we add the Rooms. (Effectively giving us control over adding them
	//to specific Rooms).
	//The reason why it wasn't working before was because Rooms are not Comparable.
	//There is no way to compare them as a Key in the HashMap.
	//Therefore, we need to number them.
	static Map<Integer, Room> mySBRooms = new HashMap<Integer, Room>();  //Science Building	

	public static void main(String[] args) {
		System.out.println("Testbed for LostInKnightdale");
		//Load the game
		loadGame();
	
		String verb, noun;  //Holders for the words of the command
		Player p = new Player();  //Player
		
		//Test: Poisoning the Player (WORKS)
//		ObjectInterface bidoof = new Food("bidoof");
//		Food.addPoisonedFood("bidoof");  
		
//		p.addObject(bidoof.getName(), bidoof);
				
		//Test: Can I eat a Monster? (NOPE) 
//		ObjectInterface m = new Monster("Harry");
		//Since we don't have Rooms (yet), I have to add him to the backpack and see if I can eat him
//		p.addObject(m.getName(), m);
		
		//How about an NPC? (ALSO NOPE)
//		ObjectInterface n = new NPC("Mary");
		//Since we don't have Rooms (yet), I have to add her to the backpack and see if I can eat her
//		p.addObject(n.getName(), n);
		
		verb = "";
		noun = "";
		
		//Since we are adding the food items ourselves, we don't have to worry about
		//checking if the Food object's name is valid or not :D
		
		//Non-poisoned item
		ObjectInterface ob = new Food("apple");		
		ObjectInterface ob2 = new Key("key");
		ObjectInterface ob3 = new Food("pear");
		ObjectInterface ob4 = new Food("orange");
		ObjectInterface ob5 = new Food("orange");
		ObjectInterface ob6 = new Treasure("ring");
		ObjectInterface ob7 = new Treasure("bracelet");
		ObjectInterface ob8 = new Treasure("gauntlet");
		ObjectInterface ob9 = new Food("pineapple");
		ObjectInterface ob10 = new Food("blueberry");
		ObjectInterface ob11 = new Food("watermelon");
		
		p.addObject(ob.getName(), ob); //Has to be the name of the object and the object itself
										//Reason is because we remove objects by name
										//Just because two objects have the same name does NOT mean they are the same object
										//At least in memory
		p.addObject(ob2.getName(), ob2);
		p.addObject(ob3.getName(), ob3);
		p.addObject(ob4.getName(), ob4);
		p.addObject(ob5.getName(), ob5);
		p.addObject(ob6.getName(), ob6);
		p.addObject(ob7.getName(), ob7);
		p.addObject(ob8.getName(), ob8);
		p.addObject(ob9.getName(), ob9);
		p.addObject(ob10.getName(), ob10);
		p.addObject(ob11.getName(), ob11);
		
//		ObjectInterface ob12 = new Treasure("emerald");
		
//		p.addObject(ob12.getName(), ob12);
		
		//Test: Get the NPC from a Room. (Works)
//		ObjectInterface checker = mySBRooms.get(0).getNPC();
//		System.out.println();
//		System.out.println("Room 0 should have " + checker.getName());
		
		//Test: No NPC in the Room (Works).
//		ObjectInterface checker2 = mySBRooms.get(4).getNPC();
//		System.out.println();
//		System.out.println("Room 4 = " + checker2);  //No NPC. Throws a NullPointerException if I try to get the name.
		
		//Test: Get the Monster from a Room.
		
		
		
		
		System.out.println("You have an " + ob.getName() + " in your backpack.");
		System.out.println("\n");
		
		//Test: Food gives health to Players (WORKS)
//		p.subtractHealth();
//		p.subtractHealth();
//		p.subtractHealth();
		//eat apple, pear, and orange. Should be back to 100 hit points. (WORKS)
		
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
		loadRooms();  //Rooms, NPCs, and Monsters in that order.
		loadNPCAndMonster();
	}
	
	public static void loadRooms() {
		
		//Add rooms to the Hash Map
		//Have NPCs
		mySBRooms.put(0, new Room(true, true, true, true, true, false));  
		mySBRooms.put(1, new Room(true, true, true, true, true, false));  
		mySBRooms.put(2, new Room(false, false, true, true, true, false));  
		mySBRooms.put(3, new Room(false, false, false, false, true, false)); 

		//Have Monsters
		mySBRooms.put(4, new Room(false, false, false, true, false, true));
		mySBRooms.put(5, new Room(true, true, true, true, false, true));
		
		//Has both
		mySBRooms.put(6, new Room(true, true, true, true, true, true));
		
		System.out.println("Rooms created.");
		
	}
	
	//Test: Adding NPCs and Monsters
	public static void loadNPCAndMonster() {
		String[] names = {"V. Norman", "Yolanda", "Andrew", "Austin", "Professor Porpoise", "Dr. Professor Patrick", "Bubba" };
		String[] monsterNames = {"Hope student", "A. Bickle", "H. Plantinga", "Dr. Squiggles" };
		
		int npcI = 0, monsterI = 0;
	   // Iterator<Map.Entry<Integer, Room>> it = mySBRooms.entrySet().iterator();
	
		System.out.println("Size of SB Rooms: " + mySBRooms.size()); //Get the size of SBRooms
		for(int i = 0; i < mySBRooms.size(); i++) {
			if(mySBRooms.get(i).needNPC()) {  //Do I need an NPC?
				if(mySBRooms.get(i).needMonster()) {  //How about a Monster?
					mySBRooms.get(i).setMonster(monsterNames[monsterI]);  //Yes. Set the name of it.
					System.out.println("Room " + i + " has " + mySBRooms.get(i).getMonster().getName());
					monsterI++;
				} else {  //No, I only need an NPC
					System.out.println("Room " + i + " doesn't need a Monster."); //Nope.
				}
 				mySBRooms.get(i).setNPC(names[npcI]);  //Set the name of the NPC.
				System.out.println("Room " + i + " has " + mySBRooms.get(i).getNPC().getName());
				npcI++;
			} else {
				System.out.println("Room " + i + " doesn't need an NPC."); //Nope.
				if(mySBRooms.get(i).needMonster()) {  //How about a Monster?
					mySBRooms.get(i).setMonster(monsterNames[monsterI]);  //Yes. Set the name of it.
					System.out.println("Room " + i + " has " + mySBRooms.get(i).getMonster().getName());
					monsterI++;
				} else {  //I only need objects 
					System.out.println("Room " + i + " doesn't need a Monster."); //Nope.
				}
			}
			
		}
		
/**	    while (it.hasNext()) {
	        Map.Entry<Integer, Room> pair = it.next();
	        if(pair.getValue().needNPC()) {  //If a Room needs an NPC....
		        pair.getValue().setNPC(names[npcI]);
		        System.out.println(pair.getValue().getNPC().getName() + " is in Room " + pair.getKey().toString());  //Print the Room out
		        npcI++; //Get another name
	        } else {
	        	System.out.println("Room " + pair.getKey().toString() + " doesn't need an NPC");
	        }
	        
	        if(pair.getValue().needMonster()) {  //If we also need a Monster
	        	pair.getValue().setMonster(monsterNames[monsterI]);
	        	System.out.println(pair.getValue().getMonster().getName() + " is in Room " + pair.getKey().toString());
	        	monsterI++;
	        } else {
	        	System.out.println("Room " + pair.getKey().toString() + " doesn't need a Monster");
	        }
	        
	        it.remove(); // avoids a ConcurrentModificationException
	    } */
		
	}
	
	public static Map<Integer, Room> getInstance() {
		return mySBRooms;
	}
		
}

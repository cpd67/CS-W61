/**
 * We implement these patterns:
 * 	- State
 * 	- Command
 *  - Singleton (For the Map and Player).
 *  - Adapter
 *  - Facade
 *  
 * Making a Map:
 * 	- Do it in the TestClass; It makes it easier to test things out and not bloat the Driver.
 *  - Notes:
 *     + If a Player is Outside, they are in the outsideRoom whose Room number is -2.
 *     + If a Player goes inside of a HashMap of Rooms from the Outside,
 *       there MUST be two Doors for the first Room of the HashMap (Room 0):
 *       One for the direction of the HashMap of Rooms (north, south, east, or west)
 *       and a second for the direction of the Outside from the perspective of the
 *       HashMap of Rooms. (Example: At the start of the game, the Player is Outside.
 *       North Hall is north of the Player. When the Player types "walk north",
 *       then they should be in the first room of North Hall. If the Player types
 *       "walk south" while in the first Room of the Science Building, then 
 *       he/she should be back Outside.)
 *     + If a Room goes to an adjacent Room, the adjacent Room must have a Door
 *       in the direction of the previous Room. (Example: If a Player is in the first
 *       Room of the Science Building and there is a Room to the east, when the Player
 *       types "walk east" then he/she should be in the next Room. If the Player types
 *       "walk west" from the next Room, then he/she should be back in the first Room.)
 *     + Each Room in the HashMap takes an array of Integers as a parameter. This array 
 *       of Integers determines what Rooms are connected to the Room being made.
 *       Like this:
 *       
 *       Integer[] nextRoomNumbers = {0, 1, 2, 3};
 *       Room room = new Room(hasNorthDoor, hasSouthDoor, hasEastDoor, hasWestDoor, 
 *       					   hasNPC, hasMonster, nextRoomNumbers);
 *       
 *       The nextRoomNumbers array is the keys in the HashMap of Rooms.
 *       Each Room has an Integer key in the HashMap of Rooms.
 *       The first Room in the HashMap has 0 as its key. 
 *       In order to "connect" the Rooms together, we need to figure out
 *       which Room to give the Player when he/she goes through a Door.
 *       That is where the array of next Room numbers comes in.
 *       The room numbers in the array are organized by direction, so:
 *       
 *       Integer[] nextRoomNumbers = {northRoom, southRoom, eastRoom, westRoom};
 *       
 *       If there is a Room to the north of the Player's current Room, we use the
 *       Integer in the northRoom slot of the array in order to get it from the 
 *       HashMap. We then give it the Player. 
 *       
 *       The Doors of the Room must reflect this as well:
 *       
 *       Integer[] nextRoomNumbers = {0, 1, 2, 3};
 *       Room room = new Room(true, true, true, true, 
 *       					   false, false, nextRoomNumbers);
 *       
 *       If there is no Room in a certain direction, put -1 as the next Room number.
 *       (This makes it easy to see where's a Wall. If there is no Door, then there
 *        has to be a Wall.)
 *       
 *       Integer[] nextRoomNumbers = {-1, 2, -1, 3};
 *       Room room = new Room(false, true, false, true, 
 *       					   false, false, nextRoomNumbers);
 *    	(Check out the example Map in loadRooms()).
 *    
 *    + Lastly, don't worry about making any NPCs and Monsters. Set the boolean flags
 *      when you create a Room to false for both. (loadNPCAndMonsters() isn't done yet).
 *      
 * + Things to do still:
 *    - Give, Talk, Unlock.
 *    - Update Look so that it prints out the Room's objects (using showObjects()).
 *    - Code cleanup (documentation, porting over stuff from TestClass to Driver, 
 *                    taking out multiple "dot-dot-dot" calls, etc.).
 *    - NPC interactions with the Player. (Talking, giving Items, etc.)
 *    - Entire map, Quests and puzzles.
 *    - Making sure that everything uses toLowerCase().
 */
package edu.calvin.csw61.finalproject;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import edu.calvin.csw61.fruit.*;
import edu.calvin.csw61.food.*;
import edu.calvin.csw61.weapons.*;

public class TestClass {
	//Maps of Rooms (Static and anyone can refer to them as long as we call Driver.mapOfRooms
	//This makes it a LOT easier to determine where we are

	//Each room will instead be numbered. This allows us to add NPCs and Monsters in the 
	//order in which we add the Rooms. (Effectively giving us control over adding them
	//to specific Rooms).
	//The reason why it wasn't working before was because Rooms are not Comparable.
	//There is no way to compare them as a Key in the HashMap.
	//Therefore, we need to number them.

	static Map<Integer, Room> mySBRooms = new HashMap<Integer, Room>();  //Science Building, East.	
	static Map<Integer, Room> myHHRooms = new HashMap<Integer, Room>();  //Hiemenga Hall, West
	static Map<Integer, Room> myCPRooms = new HashMap<Integer, Room>();  //Chapel, North
	static Map<Integer, Room> myCMRooms = new HashMap<Integer, Room>();  //Commons, South

	//Only one outside Room
	static Integer[] outsideBuildings = {0, 0, 0, 0}; //The outside Room will have the first Room from the HashMap of Rooms.
	static Room outsideRoom = new Room(true, true, true, true, true, false, outsideBuildings);

	//Factories for items
	static ConcreteWeaponFactory weaponFactory = new ConcreteWeaponFactory();
	static ConcreteFruitFactory fruitFactory = new ConcreteFruitFactory();
	static ConcreteFoodFactory foodFactory = new ConcreteFoodFactory();

	public static void main(String[] args) {
		System.out.println("Testbed for LostInKnightdale");
		//Load the game
		loadGame();
		story();
		String verb, noun;  //Holders for the words of the command
		outsideRoom.setDescriptor(makeDescriptor("centerRoomDescription.txt"));  //For now. We may want to implement a loadDescriptions() method that sets the descriptions for every Room in the HashMap.

		Player p = new Player(outsideRoom);  //Player

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
		//	Fruit ob = fruitFactory.createFruit("apple");		
				ObjectInterface ob2 = new Key("key");
		//		Fruit ob3 = fruitFactory.createFruit("blueberry");
		//		Fruit ob4 = fruitFactory.createFruit("orange");
		//		Fruit ob5 = fruitFactory.createFruit("orange");
		//		ObjectInterface ob6 = new Treasure("ring");
		//		ObjectInterface ob7 = new Treasure("bracelet");
		//		ObjectInterface ob8 = new Treasure("gauntlet");
		//	Food ob9 = foodFactory.createFood("cupcake");
		//		Food ob10 = foodFactory.createFood("pizza");
		//		Food ob11 = foodFactory.createFood("spinach");

		//		p.addObject(ob.getName(), ob); //Has to be the name of the object and the object itself
		//Reason is because we remove objects by name
		//Just because two objects have the same name does NOT mean they are the same object
		//At least in memory
				p.addObject(ob2.getName(), ob2);
		//		p.addObject(ob3.getName(), ob3);
		//		p.addObject(ob4.getName(), ob4);
		//		p.addObject(ob5.getName(), ob5);
		//		p.addObject(ob6.getName(), ob6);
		//		p.addObject(ob7.getName(), ob7);
		//		p.addObject(ob8.getName(), ob8);
		//		p.addObject(ob9.getName(), ob9);
		//		p.addObject(ob10.getName(), ob10);
		//		p.addObject(ob11.getName(), ob11);

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

		//Test: Get the Monster from a Room. (Works).
		//		ObjectInterface checker3 = mySBRooms.get(5).getMonster();
		//		System.out.println();
		//		System.out.println("Room 5 should have " + checker3.getName());

		//Test: No Monster in the Room. (Works).
		//		ObjectInterface checker4 = mySBRooms.get(1).getMonster();
		//		System.out.println();  
		//		System.out.println("Room 1 = " + checker4);  //No Monster.

		//MULTI-Test: Rooms, Doors, and the Interactions between them.

		//Test 1: Outside Room. (WORKS).		
		//		System.out.println(p.getRoom().getDescriptor());

		//Test 2: Transferring the Player to a new Room. (WORKS).
		//		System.out.println("Transferring to a new Room...");
		//		p.setCurrentRoom(mySBRooms.get(0));
		//		System.out.println(p.getRoom().getDescriptor());

		//Test 3: Going inside of a building.
		//Fire up the Game.
		//Type, "walk north"
		//Should tell you that you are now in North Hall, first Room. (WORKS)

		//Test 4: In a Building, now you have to go Outside (WORKS).

		//Final Test: Navigate through a Building and then go Outside (OH. MY. NUGGETS. IT WORKS!).
		//(Now you just have to incorporate Walls). (DONE)

		//		System.out.println("You have an " + ob.getName() + " in your backpack.");
		//		System.out.println("\n");

		//Test: Weapons! :D
		//Knife
		Weapon knife = weaponFactory.createWeapon("knife");

		//Sword
		Weapon sword = weaponFactory.createWeapon("sword");

		//Shotgun
		Weapon shotgun = weaponFactory.createWeapon("shotgun");

		//Lance
		Weapon lance = weaponFactory.createWeapon("lance");

		//Chainsaw
		Weapon chainsaw = weaponFactory.createWeapon("chainsaw");

		//Bazooka
		Weapon bazooka = weaponFactory.createWeapon("bazooka");
		
		//Chainsaw
		//Knife
		//		if(knife.getWeaponName().equals("knife")) {
		//			System.out.println("Knife (Weapon version)");
		//		}

		//Sword
		//		if(sword.getWeaponName().equals("sword")) {
		//			System.out.println("Sword (Weapon version)");
		//		}

		//Shotgun
		//		if(shotgun.getWeaponName().equals("shotgun")) {
		//			System.out.println("Shotgun (Weapon version)");
		//		}

		//Test: Player has a Weapon. (WORKS).
		//		p.setWeapon(knife);
		//		if(p.hasWeapon()) {
		//			String name = p.getWeapon().getWeaponName();
		//			System.out.println("Player has a " + name);
		//		} else {
		//			System.out.println("Didn't work");
		//		}

		//Test: Changing Weapons. (WORKS).
		//		p.setWeapon(null);
		//		p.setWeapon(shotgun);
		//		if(p.hasWeapon()) {
		//			String name = p.getWeapon().getWeaponName();
		//			System.out.println("Player now has a " + name);
		//		} else {
		//			System.out.println("Didn't work");
		//		}

		//Test: Have a Weapon, Drop it, change it, then drop it again.
		//		p.setWeapon(sword); //Have
		//		if(p.hasWeapon()) {
		//			String name = p.getWeapon().getWeaponName();
		//			System.out.println("Player now has a " + name);
		//		} else {
		//			System.out.println("Didn't work");
		//		}
		//		p.setHasNoWeapon(); //Drop
		//		if(p.hasWeapon()) { 
		//			System.out.println("Didn't work");
		//		} else {
		//			System.out.println("You dropped your weapon.");
		//		}
		//		p.setWeapon(shotgun); //Change
		//		if(p.hasWeapon()) {
		//			String name = p.getWeapon().getWeaponName();
		//			System.out.println("Player now has a " + name);
		//		} else {
		//			System.out.println("Didn't work");
		//		}
		//		p.setHasNoWeapon(); //Drop
		//		if(p.hasWeapon()) { 
		//			System.out.println("Didn't work");
		//		} else {
		///			System.out.println("You dropped your weapon.");
		//		}

		//Test: Food, and the varying kinds of Fruit
//		p.subtractHealth(5);
//		p.subtractHealth(30);
//		p.subtractHealth(40);

		//Fruit
		outsideRoom.addObject(fruitFactory.createFruit("apple"));
		outsideRoom.addObject(fruitFactory.createFruit("blueberry"));
		outsideRoom.addObject(fruitFactory.createFruit("orange"));
		outsideRoom.addObject(fruitFactory.createFruit("papaya"));
		outsideRoom.addObject(fruitFactory.createFruit("watermelon"));

		//Food
		outsideRoom.addObject(foodFactory.createFood("bagel"));
		outsideRoom.addObject(foodFactory.createFood("cupcake"));
		outsideRoom.addObject(foodFactory.createFood("pizza"));
		outsideRoom.addObject(foodFactory.createFood("spinach"));
		outsideRoom.addObject(foodFactory.createFood("taco"));

		//		outsideRoom.showObjects();
		//Test: Adding a Weapon to a Room. (WORKS).
		WeaponAdapter knifeAdapt = new WeaponAdapter(knife);
		WeaponAdapter swordAdapt = new WeaponAdapter(sword);
		WeaponAdapter shotgunAdapt = new WeaponAdapter(shotgun);
		WeaponAdapter lanceAdapt = new WeaponAdapter(lance);
		WeaponAdapter chainsawAdapt = new WeaponAdapter(chainsaw);
		WeaponAdapter bazookaAdapt = new WeaponAdapter(bazooka);
		
		//Add the adapter, NOT the weapon
		outsideRoom.addObject(knifeAdapt);
		outsideRoom.addObject(swordAdapt);
		outsideRoom.addObject(shotgunAdapt);
		outsideRoom.addObject(lanceAdapt);
		outsideRoom.addObject(chainsawAdapt);
		outsideRoom.addObject(bazookaAdapt);
		
		//		outsideRoom.showObjects();	
		//Test: Taking a Weapon from a Room. (WORKS) :D
		//Test: Drop a Weapon, pick up a new one, drop it, pick it up again. (WORKS).

		//Test: Fight system. (Seems to work)
		//First, create a Monster and NPC
		outsideRoom.setMonster("Jeremy");
		//		if(outsideRoom.hasMonster() && !outsideRoom.needMonster()) {  //Check if the Monster was actually created...
		//			System.out.println(outsideRoom.getMonster().getName() + " is in this room.");
		//		} else {
		//			System.out.println("Something went wrong...");
		//		}
		outsideRoom.setNPC("Yolanda"); //Check if the NPC was actually created...
		//		if(outsideRoom.hasNPC() && !outsideRoom.needMonster()) {
		//			System.out.println(outsideRoom.getNPC().getName() + " is also in this room.");
		//		} else {
		//			System.out.println("Something went wrong...");
		//		}

		//Beef up the Monster and give him a sword (Works).
		outsideRoom.getMonster().setHealth(30);
		outsideRoom.getMonster().setWeapon(knife);
		ObjectInterface key = new Key("door");
		outsideRoom.getMonster().setObject(key); //Jeremy has a key
		outsideRoom.addObject(key);

		//Test: NPCs now have Objects.
		outsideRoom.getNPC().setObject(fruitFactory.createFruit("papaya"));

		//Test: Quest system (Works)
		Quest q = new Quest("Get the person a tomato", "Tomato blues", 0, "tomato");
		//NPC has a Quest...(Works)
//		outsideRoom.getNPC().setQuest(q);

//		if(outsideRoom.getNPC().hasQuest()) {
//			System.out.println(outsideRoom.getNPC().getName() + " has a Quest");
//		} else {
//			System.out.println("Didn't work!");
//		}
		
		//NPC doesn't have a Quest (Works)
//		outsideRoom.getNPC().setHasNoQuest();
		
		//Now they do, and need an ACTUAL QuestItem
		//Get the Item in the Room
		ObjectInterface questItem = new QuestItem("bible");
		ObjectInterface questItem2 = new QuestItem("tomato");
		outsideRoom.addObject(questItem);
		outsideRoom.addObject(questItem2);
		
		Quest q2 = new Quest("Yolanda needs her bible!", "Bible", 1, "bible");
		outsideRoom.getNPC().setQuest(q2);
		
		//Test: Have the QuestItem for one Quest, but not the other. (Works)
		mySBRooms.get(0).setNPC("Sarah");
		mySBRooms.get(0).getNPC().setQuest(q);
		
		
		outsideRoom.showPeople();
		outsideRoom.showObjects();	
		
		//infinite loop
		while(true){
			//			System.out.println("What do you do?");

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
		ReadFile readFile = new ReadFile("story.txt"); //new readFile with story.txt as the file to be read
		readFile.readAndPrint(); //read in the file and print it
	}

	public static void loadGame() {
		loadRooms();  //Rooms, NPCs, and Monsters in that order.
		loadNPCAndMonster();
	}

	public static void loadRooms() {
		
		//EXAMPLE MAP:
		
		//-2 = Outside, for 0th Room.
		//We could read these from a text file, that way we don't have to keep
		//creating an array of Integers...
	
		//South

		
		//North
		setCMRooms();
		
		//East
		setHHRooms();
		
		//South
		setCPRooms();

		//West
		setSBRooms();
		

				
		System.out.println("Rooms created.");
		
	}
	private static void setCMRooms(){
		Integer[] commonsRooms0 = {6, -2, -1, -1}; //North, South, East, and West.
		Integer[] commonsRooms1 = {4, -1, -1, -1};
		Integer[] commonsRooms2 = {5, -1, -1, -1};
		Integer[] commonsRooms3 = {7, -1, -1, -1};
		Integer[] commonsRooms4 = {8, 1, -1, -1};
		Integer[] commonsRooms5 = {9, 2, 6, -1};
		Integer[] commonsRooms6 = {10, 0, -1, 5};
		Integer[] commonsRooms7 = {11, 3, -1, -1};
		Integer[] commonsRooms8 = {-1, 4, 9, -1};
		Integer[] commonsRooms9 = {-1, 5, -1, 8};
		Integer[] commonsRooms10 = {-1, 6, 11, -1};
		Integer[] commonsRooms11 = {-1, 7, -1, 10};
	
		//Room 0
		myCMRooms.put(0, new Room(true, true, false, false, false, false, commonsRooms0));
		myCMRooms.get(0).setDescriptor(makeDescriptor("cm0.txt"));
		//Room 1
		myCMRooms.put(1, new Room(true, false, false, false, false, false, commonsRooms1));
		myCMRooms.get(1).setDescriptor(makeDescriptor("cm1.txt"));
		//Room 2
		myCMRooms.put(2, new Room(true, false, false, false, false, false, commonsRooms2));
		myCMRooms.get(2).setDescriptor(makeDescriptor("cm2.txt"));
		//Room 3
		myCMRooms.put(3, new Room(true, false, false, false, false, false, commonsRooms3));
		myCMRooms.get(3).setDescriptor(makeDescriptor("cm3.txt"));
		//Room 4
		myCMRooms.put(4, new Room(true, true, false, false, false, false, commonsRooms4));
		myCMRooms.get(4).setDescriptor(makeDescriptor("cm4.txt"));
		//Room 5
		myCMRooms.put(5, new Room(true, true, true, false, false, false, commonsRooms5));
		myCMRooms.get(5).setDescriptor(makeDescriptor("cm5.txt"));
		//Room 6
		myCMRooms.put(6, new Room(true, true, false, true, false, false, commonsRooms6));
		myCMRooms.get(6).setDescriptor(makeDescriptor("cm6.txt"));
		//Room 7
		myCMRooms.put(7, new Room(true, true, false, false, false, false, commonsRooms7));
		myCMRooms.get(7).setDescriptor(makeDescriptor("cm7.txt"));
		//Room 8
		myCMRooms.put(8, new Room(false, true, true, false, false, false, commonsRooms8));
		myCMRooms.get(8).setDescriptor(makeDescriptor("cm8.txt"));
		//Room 9
		myCMRooms.put(9, new Room(false, true, false, true, false, false, commonsRooms9));
		myCMRooms.get(9).setDescriptor(makeDescriptor("cm9.txt"));
		//Room 10
		myCMRooms.put(10, new Room(false, true, true, false, false, false, commonsRooms10));
		myCMRooms.get(10).setDescriptor(makeDescriptor("cm10.txt"));
		//Room 11
		myCMRooms.put(11, new Room(false, true, false, true, false, false, commonsRooms11));
		myCMRooms.get(11).setDescriptor(makeDescriptor("cm11.txt"));
	}
	
	private static void setHHRooms() {
		Integer[] hiemengaHallRooms0 = {-1, -1, 6, -2}; //Rooms to the North, South, East, and West.
		Integer[] hiemengaHallRooms1 = {-1, 2, 4, -1};
		Integer[] hiemengaHallRooms2 = {1, -1, -1, -1};
		Integer[] hiemengaHallRooms3 = {-1, -1, 7, -1};
		Integer[] hiemengaHallRooms4 = {-1, 5, 8, 1};
		Integer[] hiemengaHallRooms5 = {4, -1, -1, -1};
		Integer[] hiemengaHallRooms6 = {-1, 7, 10, 0};
		Integer[] hiemengaHallRooms7 = {6, -1, -1, 3};
		Integer[] hiemengaHallRooms8 = {-1, 9, -1, 4};
		Integer[] hiemengaHallRooms9 = {8, 10, -1, -1};
		Integer[] hiemengaHallRooms10 = {9, 11, -1, 6};
		Integer[] hiemengaHallRooms11 = {10, -1, -1, -1};
		
		//Room 0
		myHHRooms.put(0, new Room(false, false, true, true, false, false, hiemengaHallRooms0));
		myHHRooms.get(0).setDescriptor(makeDescriptor("hh0.txt"));
		//Room 1
		myHHRooms.put(1, new Room(false, true, true, false, false, false, hiemengaHallRooms1));
		myHHRooms.get(1).setDescriptor(makeDescriptor("hh1.txt"));
		//Room 2
		myHHRooms.put(2, new Room(true, false, false, false, false, false, hiemengaHallRooms2));
		myHHRooms.get(2).setDescriptor(makeDescriptor("hh2.txt"));
		//Room 3
		myHHRooms.put(3, new Room(false, false, true, false, false, false, hiemengaHallRooms3));
		myHHRooms.get(3).setDescriptor(makeDescriptor("hh3.txt"));
		//Room 4
		myHHRooms.put(4, new Room(false, true, true, true, false, false, hiemengaHallRooms4));
		myHHRooms.get(4).setDescriptor(makeDescriptor("hh4.txt"));
		//Room 5
		myHHRooms.put(5, new Room(true, false, false, false, false, false, hiemengaHallRooms5));
		myHHRooms.get(5).setDescriptor(makeDescriptor("hh5.txt"));
		//Room 6
		myHHRooms.put(6, new Room(false, true, true, true, false, false, hiemengaHallRooms6));
		myHHRooms.get(6).setDescriptor(makeDescriptor("hh6.txt"));
		//Room 7
		myHHRooms.put(7, new Room(true, false, false, true, false, false, hiemengaHallRooms7));
		myHHRooms.get(7).setDescriptor(makeDescriptor("hh7.txt"));
		//Room 8
		myHHRooms.put(8, new Room(false, true, false, true, false, false, hiemengaHallRooms8));
		myHHRooms.get(8).setDescriptor(makeDescriptor("hh8.txt"));
		//Room 9
		myHHRooms.put(9, new Room(true, true, false, false, false, false, hiemengaHallRooms9));
		myHHRooms.get(9).setDescriptor(makeDescriptor("hh9.txt"));
		//Room 10
		myHHRooms.put(10, new Room(true, true, false, true, false, false, hiemengaHallRooms10));
		myHHRooms.get(10).setDescriptor(makeDescriptor("hh10.txt"));
		//Room 11
		myHHRooms.put(11, new Room(true, false, false, false, false, false, hiemengaHallRooms11));
		myHHRooms.get(11).setDescriptor(makeDescriptor("hh11.txt"));
	}
	
	private static void setSBRooms() {
		Integer[] scienceBuildRooms0 = {-1, -1, -2, 6};  //Room 0
		Integer[] scienceBuildRooms1 = {-1, -1, -1, 7};  //Room 1
		Integer[] scienceBuildRooms2 = {-1, 3, -1, 5};  //Room 2
		Integer[] scienceBuildRooms3 = {2, -1, -1, 4};  //Room 3
		Integer[] scienceBuildRooms4 = {-1, -1, 3, 11};  //Room 4
		Integer[] scienceBuildRooms5 = {-1, -1, 2, 10};  //Room 5
		Integer[] scienceBuildRooms6 = {7, -1, 0, 9};  //Room 6
		Integer[] scienceBuildRooms7 = {-1, 6, 1, 8};  //Room 7
		Integer[] scienceBuildRooms8 = {-1, -1, 7, 15};  //Room 8
		Integer[] scienceBuildRooms9 = {-1, 10, 6, 14};  //Room 9
		Integer[] scienceBuildRooms10 = {9, 11, 5, -1};  //Room 10
		Integer[] scienceBuildRooms11 = {10, -1, 4, 12};  //Room 11
		Integer[] scienceBuildRooms12 = {-1, -1, 11, -1};  //Room 12
		Integer[] scienceBuildRooms13 = {14, -1, -1, -1};  //Room 13
		Integer[] scienceBuildRooms14 = {15, 13, 9, -1};  //Room 14
		Integer[] scienceBuildRooms15 = {-1, 14, 8, -1};  //Room 15
		
		//Room 0
		mySBRooms.put(0, new Room(false, false, true, true, false, false, scienceBuildRooms0));
		mySBRooms.get(0).setDescriptor(makeDescriptor("sb0.txt"));
		//Room1
		mySBRooms.put(1, new Room(false, false, false, true, false, false, scienceBuildRooms1));
		mySBRooms.get(1).setDescriptor(makeDescriptor("sb1.txt"));
		//Room 2
		mySBRooms.put(2, new Room(false, true, false, true, false, false, scienceBuildRooms2));
		mySBRooms.get(2).setDescriptor(makeDescriptor("sb2.txt"));
		//Room 3
		mySBRooms.put(3, new Room(true, false, false, true, false, false, scienceBuildRooms3));
		mySBRooms.get(3).setDescriptor(makeDescriptor("sb3.txt"));
		//Room 4
		mySBRooms.put(4, new Room(false, false, true, true, false, false, scienceBuildRooms4));
		mySBRooms.get(4).setDescriptor(makeDescriptor("sb4.txt"));
		//Room 5
		mySBRooms.put(5, new Room(false, false, true, true, false, false, scienceBuildRooms5));
		mySBRooms.get(5).setDescriptor(makeDescriptor("sb5.txt"));
		//Room 6
		mySBRooms.put(6, new Room(true, false, true, true, false, false, scienceBuildRooms6));
		mySBRooms.get(6).setDescriptor(makeDescriptor("sb6.txt"));
		//Room 7
		mySBRooms.put(7, new Room(false, true, true, true, false, false, scienceBuildRooms7));
		mySBRooms.get(7).setDescriptor(makeDescriptor("sb7.txt"));
		//Room 8
		mySBRooms.put(8, new Room(false, false, true, true, false, false, scienceBuildRooms8));
		mySBRooms.get(8).setDescriptor(makeDescriptor("sb8.txt"));
		//Room 9
		mySBRooms.put(9, new Room(false, true, true, true, false, false, scienceBuildRooms9));
		mySBRooms.get(9).setDescriptor(makeDescriptor("sb9.txt"));
		//Room 10
		mySBRooms.put(10, new Room(true, true, true, false, false, false, scienceBuildRooms10));
		mySBRooms.get(10).setDescriptor(makeDescriptor("sb10.txt"));
		//Room 11
		mySBRooms.put(11, new Room(true, false, true, true, false, false, scienceBuildRooms11));
		mySBRooms.get(11).setDescriptor(makeDescriptor("sb11.txt"));
		//Room 12
		mySBRooms.put(12, new Room(false, false, true, false, false, false, scienceBuildRooms12));
		mySBRooms.get(12).setDescriptor(makeDescriptor("sb12.txt"));
		//Room 13
		mySBRooms.put(13, new Room(true, false, false, false, false, false, scienceBuildRooms13));
		mySBRooms.get(13).setDescriptor(makeDescriptor("sb13.txt"));
		//Room 14
		mySBRooms.put(14, new Room(true, true, true, false, false, false, scienceBuildRooms14));
		mySBRooms.get(14).setDescriptor(makeDescriptor("sb14.txt"));
		//Room 15
		mySBRooms.put(15, new Room(false, true, true, false, false, false, scienceBuildRooms15));
		mySBRooms.get(15).setDescriptor(makeDescriptor("sb15.txt"));
	}
	
	private static void setCPRooms(){
		Integer[] chapelRooms0 = {-2, 6, -1, -1};  //North, South, East, West
		Integer[] chapelRooms1 = {-1, 4, 2, -1};  //North, South, East, West
		Integer[] chapelRooms2 = {-1, -1, -1, 1};  //North, South, East, West
		Integer[] chapelRooms3 = {-1, 7, -1, -1};  //North, South, East, West
		Integer[] chapelRooms4 = {1, -1, 5, -1};  //North, South, East, West
		Integer[] chapelRooms5 = {-1, 9, -1, 4};  //North, South, East, West
		Integer[] chapelRooms6 = {0, 10, 7, -1};  //North, South, East, West
		Integer[] chapelRooms7 = {3, 11, -1, 6};  //North, South, East, West
		Integer[] chapelRooms8 = {-1, -1, 9, -1};  //North, South, East, West
		Integer[] chapelRooms9 = {5, -1, 10, 8};  //North, South, East, West
		Integer[] chapelRooms10 = {6, -1, -1, 9};  //North, South, East, West
		Integer[] chapelRooms11 = {7, -1, -1, -1};  //North, South, East, West
		
		//Room 0
		myCPRooms.put(0, new Room(true, true, false, false, false, false, chapelRooms0));  
		myCPRooms.get(0).setDescriptor(makeDescriptor("cp0.txt"));
		//Room 1
		myCPRooms.put(1, new Room(false, true, true, false, false, false, chapelRooms1));  
		myCPRooms.get(1).setDescriptor(makeDescriptor("cp1.txt"));
		//Room 2
		myCPRooms.put(2, new Room(false, false, false, true, false, false, chapelRooms2));  
		myCPRooms.get(2).setDescriptor(makeDescriptor("cp2.txt"));
		//Room 3
		myCPRooms.put(3, new Room(false, true, false, false, false, false, chapelRooms3));  
		myCPRooms.get(3).setDescriptor(makeDescriptor("cp3.txt"));
		//Room 4
		myCPRooms.put(4, new Room(true, false, true, false, false, false, chapelRooms4));  
		myCPRooms.get(4).setDescriptor(makeDescriptor("cp4.txt"));
		//Room 5
		myCPRooms.put(5, new Room(false, true, false, true, false, false, chapelRooms5));  
		myCPRooms.get(5).setDescriptor(makeDescriptor("cp5.txt"));
		//Room 6
		myCPRooms.put(6, new Room(true, true, true, false, false, false, chapelRooms6));  
		myCPRooms.get(6).setDescriptor(makeDescriptor("cp6.txt"));
		//Room 7
		myCPRooms.put(7, new Room(true, true, false, true, false, false, chapelRooms7));  
		myCPRooms.get(7).setDescriptor(makeDescriptor("cp7.txt"));
		//Room 8
		myCPRooms.put(8, new Room(false, false, true, false, false, false, chapelRooms8));  
		myCPRooms.get(8).setDescriptor(makeDescriptor("cp8.txt"));
		//Room 9
		myCPRooms.put(9, new Room(true, false, true, true, false, false, chapelRooms9));  
		myCPRooms.get(9).setDescriptor(makeDescriptor("cp9.txt"));
		//Room 10
		myCPRooms.put(10, new Room(true, false, false, true, false, false, chapelRooms10));  
		myCPRooms.get(10).setDescriptor(makeDescriptor("cp10.txt"));
		//Room 11
		myCPRooms.put(11, new Room(true, false, false, false, false, false, chapelRooms11));  
		myCPRooms.get(11).setDescriptor(makeDescriptor("cp11.txt"));
		
	}
	
	//Test: Adding NPCs and Monsters (NOT DONE)
	public static void loadNPCAndMonster() {
		//SB Rooms
		String[] sbNames = {"V. Norman", "Yolanda", "Andrew", "Austin", "Professor Porpoise", "Dr. Professor Patrick", "Bubba" };
		String[] sbMonsterNames = {"Hope student", "A. Bickle", "H. Plantinga", "Dr. Squiggles", "Mr. Fantastic", "Troll", "Dwarf" };
		
		//Chapel Rooms
		String[] cpNames = {"VicToddNorm", "CupLyd", "Mr. Castle", "Sumo"};
		String[] cpMonsterNames = {"Hope student", "Panda", "Bear", "Hamster"};
		
		//Hiemenga Rooms
		String[] hhNames = {"David Michelle", "Austy", "Mr. Dr. Squiggles", "Mrs. Dr. Squiggles"};
		String[] hhMonsterNames = {"Hope student", "Evil Panda", "Total Depravity", "Needy Freshman" };
		
		//Commons
		String[] commonsNames = {"Prof. Adams", "Hubert", "Harry", "Sherry"};
		String[] commonsMonsterNames = {"Hope student", "Mr. Tofu", "Student Loans" };
		
		int npcI = 0, monsterI = 0;
		
		mySBRooms.get(0).setMonster("Jeremy");
		mySBRooms.get(0).getMonster().setObject(fruitFactory.createFruit("apple"));
		
		//SB Rooms
		for(int i = 0; i < mySBRooms.size(); i++) {
			if(mySBRooms.get(i).needNPC()) {  //Do I need an NPC?
				if(mySBRooms.get(i).needMonster()) {  //How about a Monster?
					mySBRooms.get(i).setMonster(sbMonsterNames[monsterI]);  //Yes. Set the name of it.
					System.out.println("Room " + i + " has " + mySBRooms.get(i).getMonster().getName());
					monsterI++;
				} else {  //No, I only need an NPC
					System.out.println("Room " + i + " doesn't need a Monster."); //Nope.
				}
 				mySBRooms.get(i).setNPC(sbNames[npcI]);  //Set the name of the NPC.
				System.out.println("Room " + i + " has " + mySBRooms.get(i).getNPC().getName());
				npcI++;
			} else {
				System.out.println("Room " + i + " doesn't need an NPC."); //Nope.
				if(mySBRooms.get(i).needMonster()) {  //How about a Monster?
					mySBRooms.get(i).setMonster(sbMonsterNames[monsterI]);  //Yes. Set the name of it.
					System.out.println("Room " + i + " has " + mySBRooms.get(i).getMonster().getName());
					monsterI++;
				} else {  //I only need objects 
					System.out.println("Room " + i + " doesn't need a Monster."); //Nope.
				}
			}	
		}
		
		//Reset the index
		npcI = monsterI = 0;
		
		//Chapel
		for(int i = 0; i < myCPRooms.size(); i++) {
			if(myCPRooms.get(i).needNPC()) {  //Do I need an NPC?
				if(myCPRooms.get(i).needMonster()) {  //How about a Monster?
					myCPRooms.get(i).setMonster(cpMonsterNames[monsterI]);  //Yes. Set the name of it.
					System.out.println("Room " + i + " has " + myCPRooms.get(i).getMonster().getName());
					monsterI++;
				} else {  //No, I only need an NPC
					System.out.println("Room " + i + " doesn't need a Monster."); //Nope.
				}
 				myCPRooms.get(i).setNPC(cpNames[npcI]);  //Set the name of the NPC.
				System.out.println("Room " + i + " has " + myCPRooms.get(i).getNPC().getName());
				npcI++;
			} else {
				System.out.println("Room " + i + " doesn't need an NPC."); //Nope.
				if(myCPRooms.get(i).needMonster()) {  //How about a Monster?
					myCPRooms.get(i).setMonster(cpMonsterNames[monsterI]);  //Yes. Set the name of it.
					System.out.println("Room " + i + " has " + myCPRooms.get(i).getMonster().getName());
					monsterI++;
				} else {  //I only need objects 
					System.out.println("Room " + i + " doesn't need a Monster."); //Nope.
				}
			}	
		}
		
		npcI = monsterI = 0;
		
		//Hiemenga
		for(int i = 0; i < myHHRooms.size(); i++) {
			if(myHHRooms.get(i).needNPC()) {  //Do I need an NPC?
				if(myHHRooms.get(i).needMonster()) {  //How about a Monster?
					myHHRooms.get(i).setMonster(hhMonsterNames[monsterI]);  //Yes. Set the name of it.
					System.out.println("Room " + i + " has " + myHHRooms.get(i).getMonster().getName());
					monsterI++;
				} else {  //No, I only need an NPC
					System.out.println("Room " + i + " doesn't need a Monster."); //Nope.
				}
 				myHHRooms.get(i).setNPC(hhNames[npcI]);  //Set the name of the NPC.
				System.out.println("Room " + i + " has " + myHHRooms.get(i).getNPC().getName());
				npcI++;
			} else {
				System.out.println("Room " + i + " doesn't need an NPC."); //Nope.
				if(myHHRooms.get(i).needMonster()) {  //How about a Monster?
					myHHRooms.get(i).setMonster(hhMonsterNames[monsterI]);  //Yes. Set the name of it.
					System.out.println("Room " + i + " has " + myHHRooms.get(i).getMonster().getName());
					monsterI++;
				} else {  //I only need objects 
					System.out.println("Room " + i + " doesn't need a Monster."); //Nope.
				}
			}	
		}
		
		npcI = monsterI = 0;
		
		//Commons
		for(int i = 0; i < myCMRooms.size(); i++) {
			if(myCMRooms.get(i).needNPC()) {  //Do I need an NPC?
				if(myCMRooms.get(i).needMonster()) {  //How about a Monster?
					myCMRooms.get(i).setMonster(commonsNames[monsterI]);  //Yes. Set the name of it.
					System.out.println("Room " + i + " has " + myCMRooms.get(i).getMonster().getName());
					monsterI++;
				} else {  //No, I only need an NPC
					System.out.println("Room " + i + " doesn't need a Monster."); //Nope.
				}
 				myCMRooms.get(i).setNPC(commonsNames[npcI]);  //Set the name of the NPC.
				System.out.println("Room " + i + " has " + myCMRooms.get(i).getNPC().getName());
				npcI++;
			} else {
				System.out.println("Room " + i + " doesn't need an NPC."); //Nope.
				if(myCMRooms.get(i).needMonster()) {  //How about a Monster?
					myCMRooms.get(i).setMonster(commonsMonsterNames[monsterI]);  //Yes. Set the name of it.
					System.out.println("Room " + i + " has " + myCMRooms.get(i).getMonster().getName());
					monsterI++;
				} else {  //I only need objects 
					System.out.println("Room " + i + " doesn't need a Monster."); //Nope.
				}
			}	
		}
	}
	
	public static Map<Integer, Room> getInstance() {
		return mySBRooms;
	}
	
	public static String makeDescriptor(String fileName){
		ReadFile rf = new ReadFile(fileName);
		rf.read();
		return rf.output;
	}
		
}

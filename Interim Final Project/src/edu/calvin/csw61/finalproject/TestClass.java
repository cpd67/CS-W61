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
		
		
		System.out.println(outsideRoom.showPeople());
		System.out.println(outsideRoom.showObjects());
		
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
		addCPObjects();
		addHHObjects();
		addCMObjects();
		addSBObjects();
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
		myCMRooms.put(0, new Room(true, true, false, false, false, true, commonsRooms0));
		myCMRooms.get(0).setDescriptor(makeDescriptor("cm0.txt"));
		myCMRooms.get(0).getDoor("north").setLocked(); //lock the north door
		//Room 1
		myCMRooms.put(1, new Room(true, false, false, false, true, false, commonsRooms1));
		myCMRooms.get(1).setDescriptor(makeDescriptor("cm1.txt"));
		//Room 2
		myCMRooms.put(2, new Room(true, false, false, false, true, false, commonsRooms2));
		myCMRooms.get(2).setDescriptor(makeDescriptor("cm2.txt"));
		//Room 3
		myCMRooms.put(3, new Room(true, false, false, false, false, true, commonsRooms3));
		myCMRooms.get(3).setDescriptor(makeDescriptor("cm3.txt"));
		//Room 4
		myCMRooms.put(4, new Room(true, true, false, false, false, true, commonsRooms4));
		myCMRooms.get(4).setDescriptor(makeDescriptor("cm4.txt"));
		myCMRooms.get(4).getDoor("south").setLocked(); //lock the south door
		//Room 5
		myCMRooms.put(5, new Room(true, true, true, false, false, true, commonsRooms5));
		myCMRooms.get(5).setDescriptor(makeDescriptor("cm5.txt"));
		//Room 6
		myCMRooms.put(6, new Room(true, true, false, true, true, false, commonsRooms6));
		myCMRooms.get(6).setDescriptor(makeDescriptor("cm6.txt"));
		//Room 7
		myCMRooms.put(7, new Room(true, true, false, false, false, true, commonsRooms7));
		myCMRooms.get(7).setDescriptor(makeDescriptor("cm7.txt"));
		myCMRooms.get(7).getDoor("south").setLocked(); //lock the south door
		//Room 8
		myCMRooms.put(8, new Room(false, true, true, false, false, true, commonsRooms8));
		myCMRooms.get(8).setDescriptor(makeDescriptor("cm8.txt"));
		//Room 9
		myCMRooms.put(9, new Room(false, true, false, true, true, false, commonsRooms9));
		myCMRooms.get(9).setDescriptor(makeDescriptor("cm9.txt"));
		//Room 10
		myCMRooms.put(10, new Room(false, true, true, false, false, true, commonsRooms10));
		myCMRooms.get(10).setDescriptor(makeDescriptor("cm10.txt"));
		//Room 11
		myCMRooms.put(11, new Room(false, true, false, true, true, false, commonsRooms11));
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
		myHHRooms.put(0, new Room(false, false, true, true, false, true, hiemengaHallRooms0));
		myHHRooms.get(0).setDescriptor(makeDescriptor("hh0.txt"));
		myHHRooms.get(0).getDoor("east").setLocked(); //lock the east door
		//Room 1
		myHHRooms.put(1, new Room(false, true, true, false, false, false, hiemengaHallRooms1));
		myHHRooms.get(1).setDescriptor(makeDescriptor("hh1.txt"));
		myHHRooms.get(1).getDoor("south").setLocked(); //lock the south door
		//Room 2
		myHHRooms.put(2, new Room(true, false, false, false, false, true, hiemengaHallRooms2));
		myHHRooms.get(2).setDescriptor(makeDescriptor("hh2.txt"));
		//Room 3
		myHHRooms.put(3, new Room(false, false, true, false, false, true, hiemengaHallRooms3));
		myHHRooms.get(3).setDescriptor(makeDescriptor("hh3.txt"));
		//Room 4
		myHHRooms.put(4, new Room(false, true, true, true, true, false, hiemengaHallRooms4));
		myHHRooms.get(4).setDescriptor(makeDescriptor("hh4.txt"));
		//Room 5
		myHHRooms.put(5, new Room(true, false, false, false, false, true, hiemengaHallRooms5));
		myHHRooms.get(5).setDescriptor(makeDescriptor("hh5.txt"));
		//Room 6
		myHHRooms.put(6, new Room(false, true, true, true, false, true, hiemengaHallRooms6));
		myHHRooms.get(6).setDescriptor(makeDescriptor("hh6.txt"));
		//Room 7
		myHHRooms.put(7, new Room(true, false, false, true, true, false, hiemengaHallRooms7));
		myHHRooms.get(7).setDescriptor(makeDescriptor("hh7.txt"));
		//Room 8
		myHHRooms.put(8, new Room(false, true, false, true, false, false, hiemengaHallRooms8));
		myHHRooms.get(8).setDescriptor(makeDescriptor("hh8.txt"));
		//Room 9
		myHHRooms.put(9, new Room(true, true, false, false, false, true, hiemengaHallRooms9));
		myHHRooms.get(9).setDescriptor(makeDescriptor("hh9.txt"));
		//Room 10
		myHHRooms.put(10, new Room(true, true, false, true, true, false, hiemengaHallRooms10));
		myHHRooms.get(10).setDescriptor(makeDescriptor("hh10.txt"));
		myHHRooms.get(10).getDoor("south").setLocked(); //lock the south door
		//Room 11
		myHHRooms.put(11, new Room(true, false, false, false, false, true, hiemengaHallRooms11));
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
		Integer[] scienceBuildRooms12 = {-1, -1, 11, 16};  //Room 12
		Integer[] scienceBuildRooms13 = {14, -1, -1, -1};  //Room 13
		Integer[] scienceBuildRooms14 = {15, 13, 9, -1};  //Room 14
		Integer[] scienceBuildRooms15 = {-1, 14, 8, -1};  //Room 15
		Integer[] scienceBuildRooms16 = {-1, -1, 12, -1};  //Room 16
		
		//Room 0
		mySBRooms.put(0, new Room(false, false, true, true, true, true, scienceBuildRooms0));
		mySBRooms.get(0).setDescriptor(makeDescriptor("sb0.txt"));
		mySBRooms.get(0).getDoor("west").setLocked(); //lock west door
		//Room1
		mySBRooms.put(1, new Room(false, false, false, true, false, true, scienceBuildRooms1));
		mySBRooms.get(1).setDescriptor(makeDescriptor("sb1.txt"));
		//Room 2
		mySBRooms.put(2, new Room(false, true, false, true, true, false, scienceBuildRooms2));
		mySBRooms.get(2).setDescriptor(makeDescriptor("sb2.txt"));
		//Room 3
		mySBRooms.put(3, new Room(true, false, false, true, true, false, scienceBuildRooms3));
		mySBRooms.get(3).setDescriptor(makeDescriptor("sb3.txt"));
		//Room 4
		mySBRooms.put(4, new Room(false, false, true, true, false, true, scienceBuildRooms4));
		mySBRooms.get(4).setDescriptor(makeDescriptor("sb4.txt"));
		//Room 5
		mySBRooms.put(5, new Room(false, false, true, true, false, true, scienceBuildRooms5));
		mySBRooms.get(5).setDescriptor(makeDescriptor("sb5.txt"));
		//Room 6
		mySBRooms.put(6, new Room(true, false, true, true, false, true, scienceBuildRooms6));
		mySBRooms.get(6).setDescriptor(makeDescriptor("sb6.txt"));
		//Room 7
		mySBRooms.put(7, new Room(false, true, true, true, false, true, scienceBuildRooms7));
		mySBRooms.get(7).setDescriptor(makeDescriptor("sb7.txt"));
		mySBRooms.get(7).getDoor("east").setLocked(); //lock east door
		//Room 8
		mySBRooms.put(8, new Room(false, false, true, true, true, false, scienceBuildRooms8));
		mySBRooms.get(8).setDescriptor(makeDescriptor("sb8.txt"));
		//Room 9
		mySBRooms.put(9, new Room(false, true, true, true, true, false, scienceBuildRooms9));
		mySBRooms.get(9).setDescriptor(makeDescriptor("sb9.txt"));
		//Room 10
		mySBRooms.put(10, new Room(true, true, true, false, false, true, scienceBuildRooms10));
		mySBRooms.get(10).setDescriptor(makeDescriptor("sb10.txt"));
		//Room 11
		mySBRooms.put(11, new Room(true, false, true, true, true, false, scienceBuildRooms11));
		mySBRooms.get(11).setDescriptor(makeDescriptor("sb11.txt"));
		mySBRooms.get(11).getDoor("west").setLocked(); //lock west door
		//Room 12
		mySBRooms.put(12, new Room(false, false, true, true, false, true, scienceBuildRooms12));
		mySBRooms.get(12).setDescriptor(makeDescriptor("sb12.txt"));
		mySBRooms.get(12).getDoor("west").setLocked(); //lock west door
		//Room 13
		mySBRooms.put(13, new Room(true, false, false, false, true, false, scienceBuildRooms13));
		mySBRooms.get(13).setDescriptor(makeDescriptor("sb13.txt"));
		//Room 14
		mySBRooms.put(14, new Room(true, true, true, false, false, true, scienceBuildRooms14));
		mySBRooms.get(14).setDescriptor(makeDescriptor("sb14.txt"));
		//Room 15
		mySBRooms.put(15, new Room(false, true, true, false, false, true, scienceBuildRooms15));
		mySBRooms.get(15).setDescriptor(makeDescriptor("sb15.txt"));
		//Room 16
		mySBRooms.put(16, new Room(false, false, true, false, true, false, scienceBuildRooms16));
		mySBRooms.get(16).setDescriptor(makeDescriptor("sb16.txt"));
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
		myCPRooms.put(0, new Room(true, true, false, false, false, true, chapelRooms0));  
		myCPRooms.get(0).setDescriptor(makeDescriptor("cp0.txt"));
		myCPRooms.get(0).getDoor("south").setLocked(); //lock the south door
		//Room 1
		myCPRooms.put(1, new Room(false, true, true, false, false, true, chapelRooms1));  
		myCPRooms.get(1).setDescriptor(makeDescriptor("cp1.txt"));
		myCPRooms.get(1).getDoor("east").setLocked(); //lock the east door
		//Room 2
		myCPRooms.put(2, new Room(false, false, false, true, false, true, chapelRooms2));  
		myCPRooms.get(2).setDescriptor(makeDescriptor("cp2.txt"));
		//Room 3
		myCPRooms.put(3, new Room(false, true, false, false, true, false, chapelRooms3));  
		myCPRooms.get(3).setDescriptor(makeDescriptor("cp3.txt"));
		//Room 4
		myCPRooms.put(4, new Room(true, false, true, false, false, false, chapelRooms4));  
		myCPRooms.get(4).setDescriptor(makeDescriptor("cp4.txt"));
		//Room 5
		myCPRooms.put(5, new Room(false, true, false, true, true, false, chapelRooms5));  
		myCPRooms.get(5).setDescriptor(makeDescriptor("cp5.txt"));
		myCPRooms.get(5).getDoor("west").setLocked(); //lock the east door
		//Room 6
		myCPRooms.put(6, new Room(true, true, true, false, true, false, chapelRooms6));  
		myCPRooms.get(6).setDescriptor(makeDescriptor("cp6.txt"));
		//Room 7
		myCPRooms.put(7, new Room(true, true, false, true, false, true, chapelRooms7));  
		myCPRooms.get(7).setDescriptor(makeDescriptor("cp7.txt"));
		//Room 8
		myCPRooms.put(8, new Room(false, false, true, false, false, true, chapelRooms8));  
		myCPRooms.get(8).setDescriptor(makeDescriptor("cp8.txt"));
		//Room 9
		myCPRooms.put(9, new Room(true, false, true, true, false, true, chapelRooms9));  
		myCPRooms.get(9).setDescriptor(makeDescriptor("cp9.txt"));
		//Room 10
		myCPRooms.put(10, new Room(true, false, false, true, false, true, chapelRooms10));  
		myCPRooms.get(10).setDescriptor(makeDescriptor("cp10.txt"));
		//Room 11
		myCPRooms.put(11, new Room(true, false, false, false, true, false, chapelRooms11));  
		myCPRooms.get(11).setDescriptor(makeDescriptor("cp11.txt"));
		
	}
	
	public static void addCPObjects() {
		//add keys
		ObjectInterface key1 = new Key("key");
		ObjectInterface key2 = new Key("key");
		ObjectInterface key3 = new Key("key");
		myCPRooms.get(0).getMonster().setObject(key1);
		myCPRooms.get(11).addObject(key2);
		myCPRooms.get(8).getMonster().setObject(key3); //give the big demon a key
		
		//add food and fruit
		myCPRooms.get(3).addObject(foodFactory.createFood("taco")); //add a taco to room 3
		myCPRooms.get(4).addObject(fruitFactory.createFruit("apple")); //add apple
		myCPRooms.get(9).addObject(fruitFactory.createFruit("blueberry"));
		
		//add knife
		ObjectInterface knife = new WeaponAdapter(weaponFactory.createWeapon("knife"));
		myCPRooms.get(10).addObject(knife);
		
		//ID card
		ObjectInterface bible = new QuestItem("bible");
		myCPRooms.get(2).getMonster().setObject(bible);
	}
	
	public static void addHHObjects() {
		//add keys
		ObjectInterface key1 = new Key("key");
		ObjectInterface key2 = new Key("key");
		ObjectInterface key3 = new Key("key");
		
		myHHRooms.get(0).getMonster().setObject(key1);
		myHHRooms.get(5).getMonster().setObject(key2);
		myHHRooms.get(6).getMonster().setObject(key3);
		
		//add food and fruit
		myHHRooms.get(1).addObject(foodFactory.createFood("spinach"));
		myHHRooms.get(8).addObject(fruitFactory.createFruit("papaya"));
		myHHRooms.get(10).addObject(fruitFactory.createFruit("blueberry"));

		myHHRooms.get(6).getMonster().setWeapon(weaponFactory.createWeapon("shotgun"));
		
		//ID card
		ObjectInterface idcard = new QuestItem("idcard");
		myHHRooms.get(2).getMonster().setObject(idcard);
	}
	
	public static void addCMObjects(){
		//add keys
		ObjectInterface key1 = new Key("key");
		ObjectInterface key2 = new Key("key");
		ObjectInterface key3 = new Key("key");
		
		myCMRooms.get(0).getMonster().setObject(key1);
		myCMRooms.get(2).addObject(key2);
		myCMRooms.get(10).getMonster().setObject(key3);
		
		//add food and fruit
		myCMRooms.get(1).addObject(foodFactory.createFood("bagel"));
		myCMRooms.get(2).addObject(foodFactory.createFood("pizza"));
		myCMRooms.get(4).addObject(foodFactory.createFood("cupcake"));
		myCMRooms.get(6).addObject(foodFactory.createFood("taco"));
		myCMRooms.get(7).addObject(fruitFactory.createFruit("orange"));
		myCMRooms.get(9).addObject(foodFactory.createFood("spinach"));
		myCMRooms.get(11).addObject(foodFactory.createFood("pizza"));
	
		myCMRooms.get(5).getMonster().setWeapon(weaponFactory.createWeapon("lance"));
		
		//the pi
		ObjectInterface pi = new QuestItem("pi");
		myCMRooms.get(3).getMonster().setObject(pi);
		
	}
	
	public static void addSBObjects() {
		//add keys
		ObjectInterface key1 = new Key("key");
		ObjectInterface key2 = new Key("key");
		ObjectInterface specialkey = new Key("special key");
		
		mySBRooms.get(0).getMonster().setObject(key1);
		mySBRooms.get(14).getMonster().setObject(key2);
		mySBRooms.get(13).getNPC().setObject(specialkey);
		
		//add food and fruit
		mySBRooms.get(2).addObject(foodFactory.createFood("cupcake"));
		mySBRooms.get(6).addObject(foodFactory.createFood("bagel"));
		mySBRooms.get(14).addObject(foodFactory.createFood("pizza"));
		mySBRooms.get(3).addObject(fruitFactory.createFruit("watermelon"));
		mySBRooms.get(8).addObject(fruitFactory.createFruit("orange"));
		mySBRooms.get(12).addObject(fruitFactory.createFruit("apple"));
		
		//Moses supposes his toeses are roses but Moses supposes erroneously. Moses, he knowses his toeses aren't roses, as Moses supposes his toeses to be!
		ObjectInterface moses = new QuestItem("moses");
		mySBRooms.get(1).getMonster().setObject(moses);
		
	}

	public static void loadNPCAndMonster() {
		//Chapel
		//monsters
		myCPRooms.get(0).setMonster("Demon");
		myCPRooms.get(1).setMonster("Demon");
		myCPRooms.get(2).setMonster("Ganondorf");
		myCPRooms.get(7).setMonster("Demon");
		myCPRooms.get(8).setMonster("Demon");
		myCPRooms.get(9).setMonster("Demon");
		myCPRooms.get(10).setMonster("Demon");
		//npcs
		myCPRooms.get(3).setNPC("David Michelle");
		myCPRooms.get(5).setNPC("Tyler");
		myCPRooms.get(6).setNPC("Pastor Paul");
		myCPRooms.get(11).setNPC("Kelvyn");
		
		//Hieminga 
		//monsters
		myHHRooms.get(0).setMonster("Bookworm");
		myHHRooms.get(2).setMonster("Plantinga");
		myHHRooms.get(3).setMonster("Bookworm");
		myHHRooms.get(5).setMonster("Bookworm");
		myHHRooms.get(6).setMonster("Bookworm");
		myHHRooms.get(9).setMonster("Bookworm");
		myHHRooms.get(11).setMonster("Bookworm");
		//npcs
		myHHRooms.get(4).setNPC("Lia");
		myHHRooms.get(7).setNPC("Librarian");
		myHHRooms.get(10).setNPC("Beka");
		
		//Commons
		//monsters
		myCMRooms.get(0).setMonster("Squirrel");
		myCMRooms.get(3).setMonster("Steve");
		myCMRooms.get(4).setMonster("Squirrel");
		myCMRooms.get(5).setMonster("Squirrel");
		myCMRooms.get(7).setMonster("Squirrel");
		myCMRooms.get(8).setMonster("Squirrel");
		myCMRooms.get(10).setMonster("Squirrel");
		//npcs
		myCMRooms.get(1).setNPC("President LeRoy");
		myCMRooms.get(2).setNPC("Keith");
		myCMRooms.get(6).setNPC("Card Swiper");
		myCMRooms.get(9).setNPC("Peter");
		myCMRooms.get(11).setNPC("Spencer");
		
		//Science Building
		//monsters
		mySBRooms.get(0).setMonster("Zombie");
		mySBRooms.get(1).setMonster("Brainz");
		mySBRooms.get(4).setMonster("Zombie");
		mySBRooms.get(5).setMonster("Zombie");
		mySBRooms.get(6).setMonster("Zombie");
		mySBRooms.get(7).setMonster("Zombie");
		mySBRooms.get(10).setMonster("Zombie");
		mySBRooms.get(12).setMonster("Luther");
		mySBRooms.get(14).setMonster("Zombie");
		mySBRooms.get(15).setMonster("Zombie");
		//npcs
		mySBRooms.get(0).setNPC("Joel");
		mySBRooms.get(2).setNPC("Harry");
		mySBRooms.get(3).setNPC("Pat");
		mySBRooms.get(8).setNPC("Lydia");
		mySBRooms.get(9).setNPC("Andrew");
		mySBRooms.get(11).setNPC("VicToddNorm");
		mySBRooms.get(13).setNPC("Serita");
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

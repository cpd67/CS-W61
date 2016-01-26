package edu.calvin.csw61.finalproject;
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
//import java.util.Map;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import edu.calvin.csw61.fruit.*;
import edu.calvin.csw61.food.*;
import edu.calvin.csw61.weapons.*;

public class GameMap {
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
	
	private static GameMap uniqueInstance;

	private GameMap() {
	}
	
	public static GameMap getInstance() {
		if(uniqueInstance == null){
			uniqueInstance = new GameMap();
		}
		return uniqueInstance;
	}
	
	public static void startGame() {
		//Load the game
		loadGame();
		story();
		String verb, noun;  //Holders for the words of the command
		outsideRoom.setDescriptor(makeDescriptor("centerRoomDescription.txt"));  //For now. We may want to implement a loadDescriptions() method that sets the descriptions for every Room in the HashMap.
		outsideRoom.setNPC("Mary");
		System.out.println(outsideRoom.getDescriptor());
		
		//Name, Description, id, and ObjectInterface needed
		Quest q1 = new Quest("Obtain Bible!", "Find Pastor Mary's Bible", 1,  "bible");
		
		outsideRoom.getNPC().setQuest(q1);
		
		Player p = new Player(outsideRoom);  //Player

		verb = "";
		noun = "";
		
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
		//North
		setCMRooms();
		
		//East
		setHHRooms();
		
		//South
		setCPRooms();

		//West
		setSBRooms();
		
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
		myCMRooms.get(4).getDoor("south").setLocked(); //lock the south door
		//Room 5
		myCMRooms.put(5, new Room(true, true, true, false, false, false, commonsRooms5));
		myCMRooms.get(5).setDescriptor(makeDescriptor("cm5.txt"));
		//Room 6
		myCMRooms.put(6, new Room(true, true, false, true, true, false, commonsRooms6));
		myCMRooms.get(6).setDescriptor(makeDescriptor("cm6.txt"));
		//Room 7
		myCMRooms.put(7, new Room(true, true, false, false, false, false, commonsRooms7));
		myCMRooms.get(7).setDescriptor(makeDescriptor("cm7.txt"));
		myCMRooms.get(7).getDoor("south").setLocked(); //lock the south door
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
		myHHRooms.get(0).getDoor("east").setLocked(); //lock the east door
		//Room 1
		myHHRooms.put(1, new Room(false, true, true, false, false, false, hiemengaHallRooms1));
		myHHRooms.get(1).setDescriptor(makeDescriptor("hh1.txt"));
		myHHRooms.get(1).getDoor("south").setLocked(); //lock the south door
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
		myHHRooms.get(10).getDoor("south").setLocked(); //lock the south door
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
		myCPRooms.put(0, new Room(true, true, false, false, false, false, chapelRooms0));  
		myCPRooms.get(0).setDescriptor(makeDescriptor("cp0.txt"));
		myCPRooms.get(0).getDoor("south").setLocked(); //lock south door
		//Room 1
		myCPRooms.put(1, new Room(false, true, true, false, false, false, chapelRooms1));  
		myCPRooms.get(1).setDescriptor(makeDescriptor("cp1.txt"));
		myCPRooms.get(1).getDoor("east").setLocked(); //lock east door
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
		myCPRooms.get(5).getDoor("west").setLocked(); //lock west door
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

		myHHRooms.get(6).getMonster().setWeapon(weaponFactory.createWeapon("sword"));
		myHHRooms.get(11).getMonster().setWeapon(weaponFactory.createWeapon("shotgun"));
		
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
		
		mySBRooms.get(6).getMonster().setWeapon(weaponFactory.createWeapon("chainsaw"));
		mySBRooms.get(4).getMonster().setWeapon(weaponFactory.createWeapon("bazooka"));
		
		//Moses supposes his toeses are roses but Moses supposes erroneously. Moses, he knowses his toeses aren't roses, as Moses supposes his toeses to be!
		ObjectInterface moses = new QuestItem("moses");
		mySBRooms.get(1).getMonster().setObject(moses);
		
	}

	public static void loadNPCAndMonster() {
		//Chapel
		//monsters
		myCPRooms.get(0).setMonster("Demon");
		
		myCPRooms.get(1).setMonster("Demon");
		myCPRooms.get(1).getMonster().setDamage(10);
		myCPRooms.get(1).getMonster().setHealth(20);
		
		myCPRooms.get(2).setMonster("Ganondorf");
		myCPRooms.get(2).getMonster().setDamage(20);
		myCPRooms.get(2).getMonster().setHealth(40);
		
		myCPRooms.get(7).setMonster("Demon");
		myCPRooms.get(7).getMonster().setDamage(10);
		myCPRooms.get(7).getMonster().setHealth(20);
		
		myCPRooms.get(8).setMonster("Demon");
		myCPRooms.get(8).getMonster().setDamage(15);
		myCPRooms.get(8).getMonster().setHealth(25);
		
		myCPRooms.get(9).setMonster("Demon");
		myCPRooms.get(9).getMonster().setDamage(10);
		myCPRooms.get(9).getMonster().setHealth(20);
		
		myCPRooms.get(10).setMonster("Demon");
		myCPRooms.get(10).getMonster().setDamage(10);
		myCPRooms.get(10).getMonster().setHealth(20);
		//npcs
		myCPRooms.get(3).setNPC("David");
		myCPRooms.get(5).setNPC("Tyler");
		myCPRooms.get(6).setNPC("Paul");
		myCPRooms.get(11).setNPC("Kelvyn");

		//Hieminga 
		//monsters
		myHHRooms.get(0).setMonster("Bookworm");
		myHHRooms.get(0).getMonster().setDamage(10);
		myHHRooms.get(0).getMonster().setHealth(20);
		
		myHHRooms.get(2).setMonster("Plantinga");
		myHHRooms.get(2).getMonster().setDamage(30);
		myHHRooms.get(2).getMonster().setHealth(60);
		
		myHHRooms.get(3).setMonster("Bookworm");
		myHHRooms.get(3).getMonster().setDamage(25);
		myHHRooms.get(3).getMonster().setHealth(45);
		
		myHHRooms.get(5).setMonster("Bookworm");
		myHHRooms.get(5).getMonster().setDamage(20);
		myHHRooms.get(5).getMonster().setHealth(40);
		
		myHHRooms.get(6).setMonster("Bookworm");
		myHHRooms.get(6).getMonster().setDamage(20);
		myHHRooms.get(6).getMonster().setHealth(40);
		
		myHHRooms.get(9).setMonster("Bookworm");
		myHHRooms.get(9).getMonster().setDamage(20);
		myHHRooms.get(9).getMonster().setHealth(40);
		
		myHHRooms.get(11).setMonster("Bookworm");
		myHHRooms.get(11).getMonster().setDamage(20);
		myHHRooms.get(11).getMonster().setHealth(40);
		//npcs
		myHHRooms.get(4).setNPC("Lia");
		myHHRooms.get(7).setNPC("Librarian");
		myHHRooms.get(10).setNPC("Beka");


		Quest q2 = new Quest("Find ID card!", "Find Beka's student ID card", 2,  "card");

		myHHRooms.get(10).getNPC().setQuest(q2);

		//Commons
		//monsters
		myCMRooms.get(0).setMonster("Squirrel");
		myCMRooms.get(0).getMonster().setDamage(25);
		myCMRooms.get(0).getMonster().setHealth(45);
		
		myCMRooms.get(3).setMonster("Steve");
		myCMRooms.get(3).getMonster().setDamage(60);
		myCMRooms.get(3).getMonster().setHealth(100);
		
		myCMRooms.get(4).setMonster("Squirrel");
		myCMRooms.get(3).getMonster().setDamage(40);
		myCMRooms.get(3).getMonster().setHealth(60);
		
		myCMRooms.get(5).setMonster("Squirrel");
		myCMRooms.get(3).getMonster().setDamage(40);
		myCMRooms.get(3).getMonster().setHealth(60);
		
		myCMRooms.get(7).setMonster("Squirrel");
		myCMRooms.get(3).getMonster().setDamage(40);
		myCMRooms.get(3).getMonster().setHealth(60);
		
		myCMRooms.get(8).setMonster("Squirrel");
		myCMRooms.get(3).getMonster().setDamage(40);
		myCMRooms.get(3).getMonster().setHealth(60);
		
		myCMRooms.get(10).setMonster("Squirrel");
		myCMRooms.get(3).getMonster().setDamage(40);
		myCMRooms.get(3).getMonster().setHealth(60);
		
		//npcs
		myCMRooms.get(1).setNPC("LeRoy");
		myCMRooms.get(2).setNPC("Keith");
		myCMRooms.get(6).setNPC("Swiper");
		myCMRooms.get(9).setNPC("Peter");
		myCMRooms.get(11).setNPC("Spencer");

		Quest q3 = new Quest("Find Pi!", "Find Peter's Raspberry Pi (not pie).", 3, "pi");

		//Science Building
		//monsters
		mySBRooms.get(0).setMonster("Zombie");
		mySBRooms.get(0).getMonster().setDamage(40);
		mySBRooms.get(0).getMonster().setHealth(60);
		
		mySBRooms.get(1).setMonster("Brainz");
		mySBRooms.get(0).getMonster().setDamage(80);
		mySBRooms.get(0).getMonster().setHealth(200);
		
		mySBRooms.get(4).setMonster("Zombie");
		mySBRooms.get(0).getMonster().setDamage(60);
		mySBRooms.get(0).getMonster().setHealth(80);
		
		mySBRooms.get(5).setMonster("Zombie");
		mySBRooms.get(0).getMonster().setDamage(60);
		mySBRooms.get(0).getMonster().setHealth(80);
		
		mySBRooms.get(6).setMonster("Zombie");
		mySBRooms.get(0).getMonster().setDamage(60);
		mySBRooms.get(0).getMonster().setHealth(80);
		
		mySBRooms.get(7).setMonster("Zombie");
		mySBRooms.get(0).getMonster().setDamage(60);
		mySBRooms.get(0).getMonster().setHealth(80);
		
		mySBRooms.get(10).setMonster("Zombie");
		mySBRooms.get(0).getMonster().setDamage(60);
		mySBRooms.get(0).getMonster().setHealth(80);
		
		mySBRooms.get(12).setMonster("Luther");
		mySBRooms.get(0).getMonster().setDamage(100);
		mySBRooms.get(0).getMonster().setHealth(400);
		
		mySBRooms.get(14).setMonster("Zombie");
		mySBRooms.get(0).getMonster().setDamage(60);
		mySBRooms.get(0).getMonster().setHealth(80);
		
		mySBRooms.get(15).setMonster("Zombie");
		mySBRooms.get(0).getMonster().setDamage(60);
		mySBRooms.get(0).getMonster().setHealth(80);
		
		//npcs
		mySBRooms.get(0).setNPC("Joel");
		mySBRooms.get(2).setNPC("Harry");
		mySBRooms.get(3).setNPC("Pat");
		mySBRooms.get(8).setNPC("Lydia");
		mySBRooms.get(9).setNPC("Andrew");
		mySBRooms.get(11).setNPC("VicToddNorm");
		mySBRooms.get(13).setNPC("Serita");
		mySBRooms.get(16).setNPC("John");
		
		Quest q4 = new Quest("Find Moses!", "Find the statue of Moses and bring it to Joel", 4,  "moses");

	}


	
	public static String makeDescriptor(String fileName){
		ReadFile rf = new ReadFile(fileName);
		rf.read();
		return rf.output;
	}
}

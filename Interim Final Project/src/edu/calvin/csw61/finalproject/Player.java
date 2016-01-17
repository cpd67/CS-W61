package edu.calvin.csw61.finalproject;

/**
 * + If you use myBackpack.get(), lowercase the noun (noun.toLowerCase()) when you are passing it.
 * + Everything is lowercase (for making it look nice when printing and it makes things easier if we have everything stored
 * as one case. Lowercase seems nicer than having everything upper case ;) ).
 * + We have a standard pattern when creating a Command:
 *   - We check all of the prerequisites before creating (item needed is present, item in backpack, etc..).
 *   - If so, we create the Command object and execute it with the necessary Objects and a handle to the Player.
 *   - If not, we print an error message.
 * + Test new functionality in TestClass (to avoid bloating the Driver code). 
 */
import java.util.Hashtable;

import edu.calvin.csw61.finalproject.playercommands.*;

public class Player extends Character {
	
	//Give him some health
	private int myHealth;
	private int myNumberOfObjects;  //Number of objects one currently has
	private int myLimit; //Limit on number of Objects one can have in the backpack
	private Hashtable<String, ObjectInterface> myBackpack;  //Backpack
	private String[] myDirections = {"north", "south", "east", "west"};  //Valid directions
	private String myCurrentDirection; //Where am I heading?
	private Quest myCurrentQuest;  //Current Quest
	private boolean onQuest;  //Am I on a Quest?
	private Room myCurrentRoom;
	private String myCurrentBuilding; //I'm in a building (HashMap of Rooms)
	private Integer myCurrentRoomNum;  //Number of the Room that the Player is currently in
	
	//Constructor
	public Player(Room outsideRoom) {
		myName = "Flying Dutchman";
		myHealth = 100;
		myBackpack = new Hashtable<>();
		myNumberOfObjects = 0;
		myLimit = 10;
		myCurrentQuest = null; //No Quest
		onQuest = false;
		myCurrentDirection = ""; //No sense of direction...yet
		myCurrentRoom = outsideRoom;  //Handle to outside Room...
		myCurrentBuilding = "Outside";
		myCurrentRoomNum = -2;  //Outside at start...
		//myMapPieces? 
	}
	
	//Executes commands
	public void setCommand(String verb, String noun) {
		Command command;  //This will be the executed command;
		switch(verb.toLowerCase()) {
			case "walk": case "go":  //Walk
				if(noun.equals("")) {  //No direction set
					System.out.println("Walk in which direction?");
				} else {
					if(this.checkDir(noun)) {  //If a valid direction...
						this.setDirection(noun); //Set the Player's direction to that one
					
						//Outside, the Player will no longer be in the Room.
						if(this.getBuilding().equals("Outside")) {
							TestClass.outsideRoom.setNoPlayer();
						}
						
						//TESTING PURPOSES ONLY
						System.out.println("Room # before walking: " + this.getRoomNum());			
						
						command = new Walk(noun, this);
						command.execute();
					//	System.out.println(command.getResult());
						
						//TESTING PURPOSES ONLY!
						//IF THE PLAYER WALKED, THEN THE ROOM SHOULD HAVE CHANGED
						System.out.println("Room # after walking: " + this.getRoomNum());
						System.out.println(this.getRoom().getDescriptor());
					
					} else {  //Not a valid direction...
						System.out.println("I can't walk there.");
					}
				}
				break;
			case "eat":  //Eat
				if(noun.equals("")) {  //Food item not given...
					System.out.println("Eat what?");
				} else {
					if(this.hasItem(noun)) {  //If the Player has the Object...
						//Get the Object from the backpack
						command = new Eat(this.myBackpack.get(noun.toLowerCase()), this); //Eat it
						command.execute();  //Print out the returned String
						System.out.println(command.getResult());
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
						System.out.println("You can only have one " + noun.toLowerCase() + " in your backpack.");
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
						System.out.println(command.getResult());
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
						System.out.println(command.getResult());
					} else {
						System.out.println("You don't have a key.");
					}
				}
				break;
			case "break":  //Break
				if(noun.equals("")) {
					System.out.println("Break what?");
				} else{
					System.out.println("you broke (NOT IMPLEMENTED)");
				}
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
					if(this.hasItem(noun)) { //Do we even have the item?
						Command drop = new Drop(this.myBackpack.get(noun.toLowerCase()), this); //Lower case the item (we have EVERYTHING lower case)
						drop.execute();
						System.out.println(drop.getResult());
					} else {  //We don't...
						System.out.println("You don't have " + noun);
					}
				}
				break;
			case "throw":  //Throw
				if(noun.equals("")) {
					System.out.println("Throw what?");
				} else {
					System.out.println("you threw (NOT IMPLEMENTED)");
				}
				break;
			case "talk": //Talk
				System.out.println("you talked (NOT IMPLMENETED)");
				break;
			case "dig":  //Dig
				System.out.println("you dug (NOT IMPLEMENTED)");
				break;
			case "look": //look at the room
				command = new Look(this);
				command.execute();
				System.out.println(command.getResult());
				break;
			case "show":  //Show the backpack
				printBackpack();
				break;
			case "help":
				command = new Help();
				command.execute();
				System.out.println(command.getResult());
				break;
			default: System.out.println("I don't know what that means.");
		}
	}
	
	//Add an Object
	public void addObject(String name, ObjectInterface ob) {
		if(myNumberOfObjects == myLimit){  //We've reached the limit...
			System.out.println("Backpack is full! Drop an item!");
		} else {  //We can add Objects...
			if(myBackpack.containsKey(name.toLowerCase())) {  //BUT, do we already have it?
				System.out.println("You already have that item!");
			} else {  //No. Add it as planned.
				myBackpack.put(name.toLowerCase(), ob);
				myNumberOfObjects++;  //Number of Objects in map
			}
		}
	}
	
	//Removes an Object from the backpack
	public void removeObject(String object) {
		if(myNumberOfObjects == 0) { //If we have no Objects...
			System.out.println("You have no items to remove.");
		} else {  //We do...
			if(hasItem(object)) {  //If it's in the backpack...
				myBackpack.remove(object.toLowerCase());  //Take it out
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
			System.out.println("You currently have " + myNumberOfObjects + " items: ");
			for(String name : myBackpack.keySet()) {  //For each String in the key set...
				System.out.print(name.toLowerCase() + " ");  //Print the names
			}
			System.out.println(); 
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
			return false;  //Can't put NPCs in backpack
		} else if(ob instanceof Monster) {
			return false;  //Can't put Monsters in backpack
		}
		return true; //You can put it in your backpack
	}
	
	//Check the possible direction please
	public boolean checkDir(String dir) {
		for(int i = 0; i < myDirections.length; i++) {
			if(myDirections[i].equals(dir.toLowerCase())) { //If the input direction is valid...
				return true;
			}
		}
		return false;  //Input direction not valid!
	}
	
	//Put me on a Quest!
	public void setQuest(Quest q) {
		myCurrentQuest = q;
		onQuest = true;
	}
	
	//Done with a Quest (Gets called when QuestItem has been given to an NPC or an event has happened)0
	public void questComplete() {
		myCurrentQuest = null;  //No more Quest :'(
		onQuest = false; //Not on a Quest anymore
	}
	
	//Give life back to the Player (if not already full)
	public void addHealth() {
		myHealth += 10;
	}
	
	//Take it away
	public void subtractHealth() {
		if(myHealth > 0) {
			myHealth -= 10;
			System.out.println("You lost 10 hit points.");
		}//Check if it's 0 in Fight...Player dies at that point.
	}
	
	//Get my current direction
	public String getDirection() {
		return myCurrentDirection; 
	}
	
	//Set my new Direction!
	public void setDirection(String newDir) {
		myCurrentDirection = newDir.toLowerCase();
	}
	
	//Get my current health
	public int getHealth() {
		return myHealth;  
	}
	
	//Ask if i'm on a Quest
	public boolean isOnQuest() {
		return onQuest;
	}
	
	//What Quest am I on?
	public Quest getQuest() {
		return myCurrentQuest;
	}
	
	//set the player's current room 
	public void setCurrentRoom(Room room) {
		myCurrentRoom = room;
	}
	
	//What room am I in?
	public Room getRoom() {
		return myCurrentRoom;
	}
	
	//I'm in a HashMap of Rooms
	public void setBuilding(String b) {
		myCurrentBuilding = b;
	}
	
	//Which building am I in?
	public String getBuilding() {
		return myCurrentBuilding;
	}
	
	//Set the Room number....
	public void setRoomNum(Integer newNum) {
		myCurrentRoomNum = newNum;
	}
	
	//Get the current Room number...
	public Integer getRoomNum() {
		return myCurrentRoomNum;
	}

}

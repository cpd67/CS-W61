package edu.calvin.csw61.finalproject;

import java.util.Hashtable;
import edu.calvin.csw61.finalproject.playercommands.*;
import edu.calvin.csw61.weapons.*;

/**
 * Player is the heart of the game.
 * It allows a user to actually play the game, LostInKnightdale.
 * It encapsulates all of the actions that a Player can perform in the game. 
 */
public class Player {
	
	//Health of the Player
	private int myHealth;
	//Max health limit
	private int myMaxHealth;
	//Number of ObjectInterfaces the Player has
	private int myNumberOfObjects;
	//Limit on the number of ObjectInterfaces that the Player 
	//can have in the backpack
	private int myLimit; 
	//Backpack
	private Hashtable<String, ObjectInterface> myBackpack;  
	//Valid directions that the Player can travel
	private String[] myDirections = {"north", "south", "east", "west"};
	//Current direction that the Player is facing/heading
	private String myCurrentDirection;
	//Current Quest 
	private Quest myCurrentQuest;
	//Handle to the current Room that the Player is in
	private Room myCurrentRoom;
	//Name of the HashMap of Rooms that the Player is in
	private String myCurrentBuilding; 
	//Number of the Room that the Player is currently in
	private Integer myCurrentRoomNum;
	//The Weapon that the Player has equipped
	private Weapon myWeapon;
	//Check if the Player has a Weapon
	private boolean hasWeapon;
	//The name of the Player
	private String myName;
	//Quest states
	//"Not on a Quest" state
	private QuestState noQuestState;
	//"On a Quest" state 
	private QuestState onQuestState;
	//"Has QuestItem" state
	private QuestState hasQuestItemState;
	//Current QuestState
	private QuestState currentState;
	//Number of Quests completed	
	private int myQuestsCompleted;
	
	/**
	 * Constructor for the Player class!
	 * @param: outsideRoom, a Room indicating the Room that the Player will be placed
	 *         in, upon creation. (In this case, the outsideRoom of the Driver). 
	 */
	public Player(Room outsideRoom) {
		this.myName = "Flying Dutchman";
		this.myHealth = 100;
		this.myMaxHealth = 100; //100 at start
		this.myBackpack = new Hashtable<>();
		this.myNumberOfObjects = 0;
		this.myLimit = 10;
		this.myCurrentQuest = null; //No Quest
		this.myCurrentDirection = ""; //No sense of direction...yet
		this.myCurrentRoom = outsideRoom;  //Handle to outside Room...
		this.myCurrentBuilding = "Outside";  //No "building", so Outside...
		this.myCurrentRoomNum = -2;  //Outside at start...
		this.myWeapon = null;  //No Weapon at start
		this.hasWeapon = false; 
		//Quest States
		this.noQuestState = new NoQuestState(this);
		this.onQuestState = new OnQuestState(this);
		this.hasQuestItemState = new HasQuestItemState(this);
		//Not on a Quest at the start
		this.currentState = noQuestState; 
  		//No Quests completed
		myQuestsCompleted = 0;
	}
	
	/**
	 * setCommand() is the heart of the Player class.
	 * It allows a user to type a command, which the Player will parse and
	 * execute if it is a valid command.
	 * @param: verb, a String representing the action word of the command. 
	 * 		   ("talk", "eat", "fight", etc...). 
	 * @param: noun, a String representing the subject to perform the command on.
	 *         (can be the name of an ObjectInterface, an NPC name, etc...).
	 */
	public void setCommand(String verb, String noun) {
		Command command;  //This will be the executed command
		switch(verb.toLowerCase()) {
			case "walk": case "go": case "move": //Walk
				if(noun.equals("")) {  //No direction specified
					System.out.println("Walk in which direction?");
				} else {  //Direction specified
					//If a valid direction...
					if(this.checkDir(noun)) {  
						//Set the Player's direction to that one
						this.setDirection(noun); 
						//If Outside, the Player will no longer be in the outsideRoom
						if(this.getBuilding().equals("Outside")) {
							GameMap.outsideRoom.setNoPlayer();
						}
						//Create the Command object (Walk)
						command = new Walk(noun, this);
						//Execute it
						command.execute();
						//Print out the results
						System.out.println(command.getResult());
					} else {  //Not a valid direction
						System.out.println("I can't walk there.");
					}
				}
				break;
			case "eat":  //Eat
				if(noun.equals("")) {  //Food item not specified...
					System.out.println("Eat what?");
				} else {
					//If the Player has the ObjectInterface...
					if(this.hasItem(noun)) {  
						//If the Player's health is already full...
						if(this.myHealth == this.myMaxHealth) {
							System.out.println("Already at full health");
						//Else, Player's health is not full...
						} else {
							//Get the ObjectInterface from the backpack
							//And pass it as the ObjectInterface to eat
							//upon Command object creation
							command = new Eat(this.myBackpack.get(noun.toLowerCase()), this);
							command.execute();
							//If it was a Key, kill the Player
							if(this.myBackpack.get(noun.toLowerCase()) instanceof Key) {
								System.out.println(command.getResult());
								System.exit(1);
							//Else, print out the result of executing the Command
							} else {
								System.out.println(command.getResult());
							}
						}
					//Else, the ObjectInterface is not in the backpack.
					} else {  
						//Is it a Weapon?
						//If so, handle the case accordingly:
						if(noun.toLowerCase().equals("knife")) {  //Knife
							System.out.println("NO! You can't eat a knife!");
						} else if(noun.toLowerCase().equals("sword")) { //Sword
							System.out.println("You're not trained to do that!");
						} else if(noun.toLowerCase().equals("shotgun")) { //Shotgun
							System.out.println("Really? A shotgun? Why don't you try eating a cactus?");	
						} else if(noun.toLowerCase().equals("lance")) { //Lance
							System.out.println("Do you really think a lance would fit down your throat?");
						} else if(noun.toLowerCase().equals("chainsaw")) { //Chainsaw
							System.out.println("Unbelievable...NO!! You can't eat a chainsaw!");
						} else if(noun.toLowerCase().equals("bazooka")) { //Bazooka
							System.out.println("We should have never shown you that bazooka");
						//Not a Weapon...is it an Monster? 
						} else if(this.getRoom().hasMonster() && this.getRoom().getMonster().getName().equals(noun.toLowerCase())){
							System.out.println(this.getRoom().getMonster().getName() + " is NOT edible!");
						//Is it an NPC?
						} else if(this.getRoom().hasNPC() && this.getRoom().getNPC().getName().equals(noun.toLowerCase())) {
							System.out.println("We have a word for that. It's called 'cannibalism'");	
							System.out.println("We don't condone that around here. So NO, you can't eat " + this.getRoom().getNPC().getName());
						//None of the above. Is the ObjectInterface in the Room? 
						} else if(this.myCurrentRoom.hasObject(noun.toLowerCase())) {
							//Yes. Is the Player at full health?
							if(this.myHealth == myMaxHealth) {  
								System.out.println("Already at full health");
							//No. Allow the Player to eat the ObjectInterface
							} else {
							command = new Eat(this.myCurrentRoom.getObject(noun), this);
							command.execute();
							//Is it a Key? If so, kill the Player.
							if(this.myCurrentRoom.getObject(noun.toLowerCase()) instanceof Key) {
								System.out.println(command.getResult());
								System.exit(1);
							} else {
								//No, something else. Print out the result.
								System.out.println(command.getResult());
							}
							}
						//Whatever the user specified is clearly not in the Room at this point.
						} else {
							System.out.println("There is no " + noun + " around here.");
						}
					} 
				}		
				break;
			case "fight": case "hit":  //Fight
				if(noun.equals("")) {   //No opponent specified
					System.out.println("Fight what?");
				} else {
					//Check if there is even a Monster to fight...
					if(this.getRoom().hasMonster() && this.getRoom().getMonster().getName().equals(noun.toLowerCase())) {
						//There is. Attack it
						command = new Fight(this.getRoom().getMonster(), this);
						command.execute();
						//Print out the result.
						System.out.println(command.getResult());
					} else {
						//No Monster. Is the user trying to fight an NPC?
						//If so, you can't. So, handle accordingly.
						if(this.getRoom().hasNPC() && this.getRoom().getNPC().getName().equals(noun.toLowerCase())) {
							System.out.println(this.getRoom().getNPC().getName() + " doesn't want to fight");
						} else {
							//Can't fight anything else.
							System.out.println("You can't fight " + noun.toLowerCase());
						}
					}
				}
				break;
			case "take": case "get": //Take
				if(noun.equals("")) {  //ObjectInterface not specified.
					System.out.println("Take what?");
				} else {
					//Does the user already have the ObjectInterface specified?
					//If so, you can only have one type of ObjectInterface at a time
					if(this.hasItem(noun)) { 
						System.out.println("You can only have one " + noun.toLowerCase() + " in your backpack.");
					} else { 
						//No. Is the user trying to take a Monster?
						if(this.getRoom().hasMonster() && this.getRoom().getMonster().getName().equals(noun.toLowerCase())) {
							System.out.println("Really? A Monster? NO!");
						//No. An NPC?
						} else if(this.getRoom().hasNPC() && this.getRoom().getNPC().getName().equals(noun.toLowerCase())) {
							System.out.println("We have a word for that. It's called 'kidnapping'.");
							System.out.println("We don't condone that around here. So NO, you can't take " + this.getRoom().getNPC().getName());
						} else {
							//No. Attempt to take the ObjectInterface
							command = new Take(noun, this);
							command.execute();
							System.out.println(command.getResult());
						}
					}
				}
				break;
			case "give": //Give
				if(noun.equals("")) {  //No ObjectInterface specified.
					System.out.println("Give what?");  
				} else { //Give something to an NPC
					if(this.getRoom().hasNPC()) { //Is there an NPC in the Room?
						//Yes.
						//Does it need a QuestItem?
						if(this.getRoom().getNPC().needsQuestOb()) { 
							//Give the Object, see if it's the one needed.
							command = new Give(this.getRoom().getNPC(), noun, this);
							command.execute();
							System.out.println(command.getResult());
						} else { 
							//No. Can only give QuestItem ObjectInterfaces.
							System.out.println(this.getRoom().getNPC().getName() + " doesn't need that!");
						}
					} else { 
						//No NPC to give something to
						System.out.println("There is no one in the room to give that to.");
					}
				}
				break;
			case "unlock": //Unlock
				if(noun.equals("")) {  //No Door direction specified.
					System.out.println("Unlock what?");
				} else {
					//Does the user have a Key?
					if(this.hasItem("key")) {
						//Yes.
						//Attempt to unlock the Door.
						command = new Unlock(this, noun);
						command.execute();  //Execute the command
						System.out.println(command.getResult());
					} else {
						//No. Can't unlock the Door if you don't have a Key.
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
			case "drop": //Drop
				if(noun.equals("")) {  //Nothing specified to drop
					System.out.println("Drop what?");
				} else {
					//Does the user have the ObjectInterface?
					if(this.hasItem(noun.toLowerCase())) { 
						//Yes. Attempt to drop the ObjectInterface
						command = new Drop(this.myBackpack.get(noun.toLowerCase()), this);
						command.execute();
						System.out.println(command.getResult());
					//Not a backpack item. 
					//Does the user have a Weapon to drop?
					} else if(this.hasWeapon) {  
						//Is he/she trying to drop it?
						if(this.getWeapon().getWeaponName().equals(noun.toLowerCase())) {
							//Yes
							//Wrap it up in a WeaponAdapter
							WeaponAdapter weapAdapt = new WeaponAdapter(this.getWeapon());
							//And drop it 
							command = new Drop(weapAdapt, this);
							command.execute();
							System.out.println(command.getResult());
						} else {
							//The user doesn't have a Weapon to drop
							System.out.println("You don't have " + noun.toLowerCase());
						}
					} else { 
						//You don't have anything applicable to drop.
						System.out.println("You don't have " + noun.toLowerCase());
					}
				}
				break;
			case "throw":  //Throw
				if(noun.equals("")) {  //Nothing specified to throw
					System.out.println("Throw what?");
				//Player has the item?
				} else if(this.hasItem(noun.toLowerCase())) { 
						//Yes. Throw it
						command = new Throw(this.myBackpack.get(noun.toLowerCase()), this);
						command.execute();
						System.out.println(command.getResult());
				//Not in the backpack. Is it a Weapon?
				} else if(this.hasWeapon() && this.getWeapon().getWeaponName().equals(noun.toLowerCase())){
						//Yes. Wrap it up and throw it.
						WeaponAdapter weaponAdapt = new WeaponAdapter(this.getWeapon());
						command = new Throw(weaponAdapt, this);
						command.execute();
						System.out.println(command.getResult());
				} else {
					System.out.println("You absolutely don't have that item.");
				}
				break;
			case "talk": //Talk
				if(noun.equals("")) {  //No person specified to talk to
					System.out.println("Talk with whom?");
				} else {
					//Check if the user specified a name.
					//Generic "person"
					if(noun.toLowerCase().equals("person")) {
						//Is there an NPC in the Room to talk to?
						if(this.getRoom().hasNPC()) {
							//Yes. Talk to the NPC.
							command = new Talk(this.getRoom().getNPC(), this);
							command.execute();
							System.out.println(command.getResult());
						} else {
							//No. No one to talk to.
							System.out.println("There's no one here.");
						}
					//Not a general person. 
					//Check if there's still an NPC in the Room
					} else {
						if(this.getRoom().hasNPC()) { 
							//Check if gender was passed ("male" or "female").
							if(this.getRoom().getNPC().getGender().equals(noun.toLowerCase())) {
								//Yes. Talk to the NPC
								command = new Talk(this.getRoom().getNPC(), this);
								command.execute();
								System.out.println(command.getResult());
							//Else, check if it was a name
							} else if(this.getRoom().getNPC().getName().equals(noun.toLowerCase())) {
								//Yes. Talk to the NPC.
								command = new Talk(this.getRoom().getNPC(), this);
								command.execute();
								System.out.println(command.getResult());
							//Else, no name nor gender. No idea who it could be.
							} else {
								System.out.println("I don't know who that is.");
								System.out.println("There's a person here. Maybe try talking to them?");
							}
					//Else, is there a Monster to talk to?
					} else if(this.getRoom().hasMonster()) {		
						//Is the user trying to talk to the Monster?
						if(this.getRoom().getMonster().getName().equals(noun.toLowerCase())) {
							//Yes. Say a generic message.
							System.out.println("'BLAAAAARAGGGHH!' " + this.getRoom().getMonster().getName() + " said.");
							System.out.println("Fascinating...");
						} else {
							//Tell the user that he can speak to a Monster.
							System.out.println("There's a monster here. Maybe he speaks human?");
						}
					//No NPC or Monster. Can't talk with anyone at that point!
					} else {
						System.out.println("You talked to yourself. It didn't help at all.");
					}
					}
				}
				break;
			case "look": //Look at the room
				command = new Look(this);
				command.execute();
				System.out.println(command.getResult());
				break;
			case "show":  //Show the backpack, health, and Quest name.
				printBackpackAndWeapon();
				System.out.println("You have " + this.myHealth + " hit points");
				System.out.println("Quest: " + currentState.getQuestName());				
				break;
			case "help":  //Help
				command = new Help();
				command.execute();
				System.out.println(command.getResult());
				break;
			//No valid Command specified
			default: System.out.println("I don't know what that means.");
		}
	}
	
	/**
	 * addObject() adds an ObjectInterface to the Player's backpack.
	 * @param: name, a String representing the name of the ObjectInterface to store. 
	 * @param: ob, the ObjectInterface to store.
	 */
	public void addObject(String name, ObjectInterface ob) {
		//Do we already have the ObjectInterface?
		if(this.myBackpack.containsKey(name.toLowerCase())) {  
			System.out.println("You already have that item!");
		} else {  //No. Add it as planned.
			this.myBackpack.put(name.toLowerCase(), ob);
			this.myNumberOfObjects++;  //Number of ObjectInterfaces in Hashtable
		}
	}
	
	/**
	 * removeObject() takes out an ObjectInterface from the backpack. 
	 * @param: object, a String representing the name of the ObjectInterface to take out.
	 */
	public void removeObject(String object) {
		//If we have no ObjectInterfaces...
		if(this.myNumberOfObjects == 0) { 
			System.out.println("You have no items to remove.");
		} else { //We have ObjectInterface to remove...
			if(this.hasItem(object)) {  //If it's in the backpack...
				this.myBackpack.remove(object.toLowerCase());  //Take it out
				this.myNumberOfObjects--;  //We have one less Object
			} else {
				System.out.println("You don't have this item.");  //Not in backpack
			}
		}
	}

	/**
	 * printBackpackAndWeapon() prints out the ObjectInterfaces of the backpack
	 * and the Weapon name if the Player has a Weapon.
	 */
	public void printBackpackAndWeapon() {
		if(this.myNumberOfObjects == 0) { //Empty backpack = No items!
			System.out.println("You have no items!");
		} else {  //You have items! 
			System.out.println("You currently have " + myNumberOfObjects + " items: ");
			for(String name : myBackpack.keySet()) {  //For each String in the key set...
				System.out.print(name.toLowerCase() + " ");  //Print the names
			}
			System.out.println(); 
		}
		if(this.hasWeapon) { //Do I have a Weapon?
			//Yes. Print out the name.
			System.out.println("You are equipped with a " + myWeapon.getWeaponName());
		} else {
			System.out.println("You have no weapon.");
		}
	}

	/**
	 * hasItem() checks if a Player has an ObjectInterface in their backpack.
	 * @param: item, a String representing the name of the ObjectInterface.
	 * @return: a boolean indicating whether or not the Player has the ObjectInterface
	 *          in their backpack.
	 */
	public boolean hasItem(String item) {
		if(myNumberOfObjects == 0) {  //If we have no ObjectInterfaces in the backpack...
			return false;
		}
		//We have ObjectInterfaces
		if(myBackpack.containsKey(item.toLowerCase())) {  
			return true;
		}
		return false;   //We don't have it
	}
	
	/**
	 * checkDir() checks if a specified direction is one that the Player can 
	 * travel.
	 * @param: dir, a String representing the direction to check.
	 * @return: a boolean indicating whether or not a Player can travel in 
	 *          the specified direction.
	 */
	public boolean checkDir(String dir) {
		for(int i = 0; i < myDirections.length; i++) {
			//If the input direction is valid...
			if(myDirections[i].equals(dir.toLowerCase())) { 
				return true;
			}
		}
		return false;  //Input direction not valid!
	}
		
	/**
	 * addHealth() increments the health total of the Player.
	 * @param: health, an int representing how much health to replenish.
	 */
  	public void addHealth(int health) {
  		int holder, middle, result;
		if((this.myHealth + health) <= this.myMaxHealth) {  //Are we above 100?
			this.myHealth += health; //Nope
			System.out.println("You gained " + health + " hit points");
		} else {
			//We are, so we have to do a little math.
			//Take the Player's health and add the health to it.
			holder = this.myHealth + health; 
			//Now, take the max health and add the health to it.
			middle = health + this.myMaxHealth;
			//Subtract the holder from middle.
			result = middle - holder;
			//Add the difference. Should be the max health
			this.myHealth += result; 
			System.out.println("You gained " + health + " hit points");
			System.out.println("You are at full health");
		}
	}
	
	/**
	 * addMaxHealth() increments the Player's max health allotment.
	 */
	public void addMaxHealth() {
		this.myMaxHealth += 20;
		this.myHealth = myMaxHealth;  //Replenish the Player's health completely
	}
	
	/**
	 * subtractHealth() takes away health from the Player.
	 * @param: sub, an int representing how much health to take away from the Player.s
	 */
	public void subtractHealth(int sub) {
		this.myHealth -= sub;
		System.out.println("You lost " + sub + " hit points.");
		//Has the Player lost all of its health?
		if(this.myHealth <= 0) {
			//Yes. The Player has died.
			System.out.println("You have die.");
			System.exit(1);
		}
	}
	
	/**
	 * Accessor for the name of the Player.
	 * @return: myName, the String representing the name of the Player.
	 */
	public String getName() {
		return myName;
	}
	
	/**
	 * Accessor for the direction of the Player.
	 * @return: myCurrentDirection, the String representing the direction of the Player.
	 */
	public String getDirection() {
		return myCurrentDirection; 
	}
	
	/**
	 * Mutator for the direction of the Player.
	 * @param: newDir, a String representing the new direction of the Player.
	 */
	public void setDirection(String newDir) {
		this.myCurrentDirection = newDir.toLowerCase();
	}
	
	/**
	 * Accessor for the health of the Player.
	 * @return: myHealth, an int representing the health of the Player.
	 */
	public int getHealth() {
		return myHealth;  
	}
	
	/**
	 * Mutator for the Room that the Player is currently in.
	 * @param: room, a Room representing the new Room that the Player should be in.
	 */
	public void setCurrentRoom(Room room) {
		this.myCurrentRoom = room;
	}
	
	/**
	 * Accessor for the Room that the Player is currently in.
	 * @return: myCurrentRoom, the Room that the Player is currently in.
	 */
	public Room getRoom() {
		return myCurrentRoom;
	}
	
	/**
	 * Mutator for the HashMap of Rooms that the Player is in ("building").
	 * @param: b, a String representing the new HashMap of Rooms that the Player is in.
	 */
	public void setBuilding(String b) {
		this.myCurrentBuilding = b;
	}
	
	/**
	 * Accessor for the HashMap of Rooms that the Player is in.
	 * @return: myCurrentBuilding, a String representing the name of the HashMap of 
	 *          Rooms that the Player is in ("building").
	 */
	public String getBuilding() {
		return myCurrentBuilding;
	}
	
	/**
	 * Mutator for the number of the Room that the Player is in. 
	 * @param: newNum, an Integer representing the new Room number.
	 */
	public void setRoomNum(Integer newNum) {
		this.myCurrentRoomNum = newNum;
	}
	
	/**
	 * Accessor for the Room number.
	 * @return: myCurrentRoomNum, an Integer representing the Room number.
	 */
	public Integer getRoomNum() {
		return myCurrentRoomNum;
	}

	/**
	 * Mutator for the Weapon that a Player has.
	 * @param: w, the new Weapon that the Player has.
	 */
	public void setWeapon(Weapon w) {
		this.myWeapon = w;
		this.hasWeapon = true;  //We now have a Weapon
	}
	
	/**
	 * Check if the Player has a Weapon.
	 * @return: a boolean indicating whether or not the Player has a Weapon. 
	 */
	public boolean hasWeapon() {
		return hasWeapon;
	}
	
	/**
	 * Accessor for the Weapon that a Player has.
	 * @return: myWeapon, the Weapon that the Player has.
	 */
	public Weapon getWeapon() {
		return myWeapon;
	}
	
	/**
	 * Mutator that is called when the Player no longer has a Weapon.
	 */
	public void setHasNoWeapon() {
		this.myWeapon = null;
		this.hasWeapon = false;
	}
	
	/**
	 * Check if the backpack is full.
	 * @return: a boolean indicating whether or not the backpack is full.
	 */
	public boolean backpackFull() {
		return myNumberOfObjects == myLimit;
	}
	
	/**
	 * dealDamage() allows a Player to inflict damage on a Monster.
	 * @param: m, the Monster to inflict damage on.
	 */
	public void dealDamage(Monster m) {
		System.out.println("You attack first");
		if(this.hasWeapon) {  //If the Player has a Weapon...
			//Get the Weapon damage
			int damage = this.getWeapon().getWeaponDamage();
			//Subtract that from the Monster
			m.subtractHealth(damage);
			System.out.println(m.getName() + " lost " + damage + " hit points");
		} else {  //Else, do 5 hit points of damage
			m.subtractHealth(5);
			System.out.println(m.getName() + " lost 5 hit points");
		}
	}
	
	/**
	 * Accessor for getting a specific ObjectInterface from the backpack.
	 * @param: name, a String representing the name of the ObjectInterface to get.
	 * @return: ob, the ObjectInterface (or null if not found).
	 */
	public ObjectInterface getObject(String name) {
		//Kind of uses the Iterator pattern with the for/in loop
		ObjectInterface ob = null;
		for(String entry : myBackpack.keySet()) {
			if(entry.equals(name.toLowerCase())) { //If we've found the ObjectInterface...
				ob = myBackpack.get(entry);  //Assign and return it later
			}
		}
		return ob;
	}
	
	//Quest stuff
	
	/**
	 * Mutator for the Quest of a Player.
	 * (Delegates the call to the QuestState).
	 * @param: q, the new Quest to be set.
	 */
	public void setNewQuest(Quest q) {
		this.currentState.setQuest(q);
	}
	
	/**
	 * Checks if the Player has the QuestItem for the Quest.
	 * (Delegates it to QuestState).
	 * @return: a boolean indicating whether or not the Player has the QuestItem.
	 */
	public boolean hasItem() {
		return currentState.hasItem();
	}
	
	/**
	 * Accessor for the name of the Quest that the Player is on.
	 * (Delegates it to QuestState).
	 * @return: a String representing the name of the Quest that the Player is on.
	 */
	public String getQuestName() {
		return currentState.getQuestName();
	}
	
	/**
	 * Accessor for the id number of the Quest.
	 * (Delegates it to QuestState).
	 * @return: the id number of the Quest that the Player is on.
	 */
	public int getQuestId() {
		return currentState.getId();
	}
	
	/**
	 * Mutator for the QuestState of the Player.
	 * @param: qs, the new QuestState to transition to.
	 */
	public void setQuestState(QuestState qs) {
		this.currentState = qs;
	}
	
	/**
	 * Accessor for the actual Quest object that the Player should have.
	 * @return: myCurrentQuest, the Quest object that the Player has.
	 */
	public Quest getActualQuest() {
		return myCurrentQuest;
	}
	
	/**
	 * Mutator for the actual Quest object that a Player should have.
	 * (Called from NoQuestState).
	 * @param: q, the new Quest that the Player has now.
	 */
	public void setActualQuest(Quest q) {
		this.myCurrentQuest = q;
	}
	
	/**
	 * Accessor for the NoQuestState of the Player.
	 * @return: noQuestState, the QuestState that determines that a Player is 
	 *          NOT on a Quest.
	 */
	public QuestState getNoQuestState() {
		return noQuestState;
	}
	
	/**
	 * Accessor for the OnQuestState of the Player.
	 * @return: onQuestState, the QuestState that determines that a Player is
	 * 			on a Quest. 
	 */
	public QuestState getOnQuestState() {
		return onQuestState;
	}
	
	/**
	 * Accessor for the HasQuestItemState of the Player.
	 * @return: hasQuestItemState, the QuestState that determines that a Player 
	 * 			has the necessary QuestItem for a Quest.
	 */
	public QuestState getHasQuestItemState() {
		return hasQuestItemState;
	}
	
	/**
	 * Accessor for the current QuestState of the Player.
	 * @return: currentState, the QuestState that the Player is on.
	 */
	public QuestState getCurrentState() {
		return currentState;
	}

	/**
	 * Increments the number of Quests completed.
	 */
	public void questComplete() {
		myQuestsCompleted++;
	}
	
	/**
	 * Accessor for the number of Quests completed. 
	 * @return: myQuestsCompleted, an int representing the number of Quests completed.
	 */
	public int getQuestCounter() {
		return myQuestsCompleted;
	}
}
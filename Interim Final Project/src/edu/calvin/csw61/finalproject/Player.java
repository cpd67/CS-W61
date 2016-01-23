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
import edu.calvin.csw61.weapons.*;
import edu.calvin.csw61.fruit.*;
import edu.calvin.csw61.food.*;

public class Player {
	
	//Give him some health
	private int myHealth;
	private int myMaxHealth;
	private int myNumberOfObjects;  //Number of objects one currently has
	private int myLimit; //Limit on number of Objects one can have in the backpack
	private Hashtable<String, ObjectInterface> myBackpack;  //Backpack
	private String[] myDirections = {"north", "south", "east", "west"};  //Valid directions
	private String myCurrentDirection; //Where am I heading?
	private Quest myCurrentQuest;  //Current Quest
	private Room myCurrentRoom;
	private String myCurrentBuilding; //I'm in a building (HashMap of Rooms)
	private Integer myCurrentRoomNum;  //Number of the Room that the Player is currently in
	private Weapon myWeapon;
	private boolean hasWeapon;
	private String myName;

	
	//Quest states
	private QuestState noQuestState;
	private QuestState onQuestState;
	private QuestState hasQuestItemState;
	
	//Current Quest state
	private QuestState currentState;
	
	//Constructor
	public Player(Room outsideRoom) {
		myName = "Flying Dutchman";
		myHealth = 100;
		myMaxHealth = 100; //100 at start
		myBackpack = new Hashtable<>();
		myNumberOfObjects = 0;
		myLimit = 10;
		myCurrentQuest = null; //No Quest
		myCurrentDirection = ""; //No sense of direction...yet
		myCurrentRoom = outsideRoom;  //Handle to outside Room...
		myCurrentBuilding = "Outside";
		myCurrentRoomNum = -2;  //Outside at start...
		myWeapon = null;  //No Weapon at start :(
		hasWeapon = false;  //No Weapon 
		
		//Quest States
		noQuestState = new NoQuestState(this);
		onQuestState = new OnQuestState(this);
		hasQuestItemState = new HasQuestItemState(this);
		
		currentState = noQuestState; //Not on a Quest at the start
	}
	
	//Executes commands
	public void setCommand(String verb, String noun) {
		Command command;  //This will be the executed command;
		switch(verb.toLowerCase()) {
			case "walk": case "go": case "move": //Walk
				if(noun.equals("")) {  //No direction set
					System.out.println("Walk in which direction?");
				} else {
					if(this.checkDir(noun)) {  //If a valid direction...
						this.setDirection(noun); //Set the Player's direction to that one
					
						//Outside, the Player will no longer be in the Room.
						if(this.getBuilding().equals("Outside")) {
							TestClass.outsideRoom.setNoPlayer();
						}		
						command = new Walk(noun, this);
						command.execute();
						System.out.println(command.getResult());
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
						if(this.myHealth == this.myMaxHealth) {
							System.out.println("Already at full health");
						} else {
							//Get the Object from the backpack
							command = new Eat(this.myBackpack.get(noun.toLowerCase()), this); //Eat it
							command.execute();  //Print out the returned String
							if(this.myBackpack.get(noun.toLowerCase()) instanceof Key) {
								System.out.println(command.getResult());
								System.exit(1);
							} else {
								System.out.println(command.getResult());
							}
						}
					} else {  
						//Trying to eat a Weapon?
						if(noun.toLowerCase().equals("knife")) {
							System.out.println("NO! You can't eat a knife!");
						} else if(noun.toLowerCase().equals("sword")) {
							System.out.println("You're not trained to do that!");
						} else if(noun.toLowerCase().equals("shotgun")) {
							System.out.println("Really? A shotgun? Why don't you try eating a cactus?");	
						} else if(noun.toLowerCase().equals("lance")) {
							System.out.println("Do you really think a lance would fit down your throat?");
						} else if(noun.toLowerCase().equals("chainsaw")) {
							System.out.println("Unbelievable...NO!! You can't eat a chainsaw!");
						} else if(noun.toLowerCase().equals("bazooka")) {
							System.out.println("We should have never shown you that bazooka");
							//How about an NPC or Monster?
						} else if(this.getRoom().hasMonster() && this.getRoom().getMonster().getName().equals(noun.toLowerCase())){
							System.out.println(this.getRoom().getMonster().getName() + " is NOT edible!");
						} else if(this.getRoom().hasNPC() && this.getRoom().getNPC().getName().equals(noun.toLowerCase())) {
							System.out.println("We have a word for that. It's called 'cannibalism'");
							System.out.println("We don't condone that around here. So NO, you can't eat " + this.getRoom().getNPC().getName());
						} else if(this.myCurrentRoom.hasObject(noun.toLowerCase())) {
							if(this.myHealth == myMaxHealth) {  //Can't eat, already at full health
								System.out.println("Already at full health");
							} else {
							command = new Eat(this.myCurrentRoom.getObject(noun), this);
							command.execute();
							//Is it a Key?
							if(this.myCurrentRoom.getObject(noun.toLowerCase()) instanceof Key) {
								System.out.println(command.getResult());
								System.exit(1);
							} else {
								//No, something else.
								System.out.println(command.getResult());
							}
							}
						} else {
							System.out.println("There is no " + noun + " around here.");
						}
					} 
				}		
				break;
			case "fight": case "hit":  //Fight
				if(noun.equals("")) {
					System.out.println("Fight what?");
				} else {
					if(this.getRoom().hasMonster() && this.getRoom().getMonster().getName().equals(noun.toLowerCase())) {
						command = new Fight(this.getRoom().getMonster(), this);
						command.execute();
						System.out.println(command.getResult());
					} else {
						if(this.getRoom().hasNPC() && this.getRoom().getNPC().getName().equals(noun.toLowerCase())) {
							System.out.println(this.getRoom().getNPC().getName() + " doesn't want to fight");
						} else {
							System.out.println("You can't fight " + noun.toLowerCase());
						}
					}
					this.getRoom().showPeople();
				}
				break;
			case "take": case "get": //Take
				if(noun.equals("")) {  //item not given...
					System.out.println("Take what?");
				} else {
					if(this.hasItem(noun)) { //If so...
						System.out.println("You can only have one " + noun.toLowerCase() + " in your backpack.");
					} else { 
						if(this.getRoom().hasMonster() && this.getRoom().getMonster().getName().equals(noun.toLowerCase())) {
							System.out.println("Really? A Monster? NO!");
						} else if(this.getRoom().hasNPC() && this.getRoom().getNPC().getName().equals(noun.toLowerCase())) {
							System.out.println("We have a word for that. It's called 'kidnapping'.");
							System.out.println("We don't condone that around here. So NO, you can't take " + this.getRoom().getNPC().getName());
						} else {
							command = new Take(noun, this);
							command.execute();
							System.out.println(command.getResult());
						}
					}
				}
				break;
			case "give": //Give
				if(noun.equals("")) {  //HAVE TO CHECK IF THE NPC NEEDS THE OBJECT
					System.out.println("Give what?");  //AND ALSO IF THE BENEFICIARY IS AN NPC!
				} else { //Give something to an NPC
					if(this.getRoom().hasNPC()) { //Has NPC?
						if(this.getRoom().getNPC().needsQuestOb()) { //Needs a QuestItem?
							//Give the Object, see if it's the one needed.
							command = new Give(this.getRoom().getNPC(), noun, this);
							command.execute();
							System.out.println(command.getResult());
						} else { //No.
							System.out.println(this.getRoom().getNPC().getName() + " doesn't need that!");
						}
					} else { //No NPC to give something to
						System.out.println("There is no one in the room to give that to.");
					}
				}
				break;
			case "unlock": //Unlock
				if(noun.equals("")) {
					System.out.println("Unlock what?");
				} else {
					if(this.hasItem("key")) {
						command = new Unlock(this, noun);
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
			case "drop": //Drop
				if(noun.equals("")) {
					System.out.println("Drop what?");
				} else {
					if(this.hasItem(noun.toLowerCase())) { //Do we even have the item?
						Command drop = new Drop(this.myBackpack.get(noun.toLowerCase()), this); //Lower case the item (we have EVERYTHING lower case)
						drop.execute();
						System.out.println(drop.getResult());
					} else if(this.hasWeapon) {  //Not a backpack item. Is it a Weapon?
						if(this.getWeapon().getWeaponName().equals(noun.toLowerCase())) {
							WeaponAdapter weapAdapt = new WeaponAdapter(this.getWeapon());
							command = new Drop(weapAdapt, this);
							command.execute();
							System.out.println(command.getResult());
						} else {
							System.out.println("You don't have " + noun.toLowerCase());
						}
					} else { //You don't have anything applicable to drop.
						System.out.println("You don't have " + noun.toLowerCase());
					}
				}
				break;
			case "throw":  //Throw
				if(noun.equals("")) {
					System.out.println("Throw what?");
				} else if(this.hasItem(noun.toLowerCase())) { //Player has the item?
						command = new Throw(this.myBackpack.get(noun.toLowerCase()), this);
						command.execute();
						System.out.println(command.getResult());
				} else if(this.hasWeapon() && this.getWeapon().getWeaponName().equals(noun.toLowerCase())){
						WeaponAdapter weaponAdapt = new WeaponAdapter(this.getWeapon());
						command = new Throw(weaponAdapt, this);
						command.execute();
						System.out.println(command.getResult());
				} else {
					System.out.println("You absolutely don't have that item.");
				}
				break;
			case "talk": //Talk
				if(noun.equals("")) {
					System.out.println("Talk with whom?");
				} else {
					if(this.getRoom().hasNPC()) { //If there's an NPC to talk to...
						//Are we trying to talk to it?
						if(this.getRoom().getNPC().getName().equals(noun.toLowerCase())) {
							command = new Talk(this.getRoom().getNPC(), this);
							command.execute();
							System.out.println(command.getResult());
						} else {
							//Nope
							System.out.println("I don't know who that is.");
							System.out.println("There's a person here. Maybe try talking to them?");
						}
					//Else, is there a Monster to talk to?
					} else if(this.getRoom().hasMonster()) {		
						if(this.getRoom().getMonster().getName().equals(noun.toLowerCase())) {
							System.out.println("'BLAAAAARAGGGHH!' " + this.getRoom().getMonster().getName() + " said.");
							System.out.println("Fascinating...");
						} else {
							System.out.println("There's a monster here. Maybe he speaks human?");
						}
					} else {
						System.out.println("You talked to yourself. It didn't help at all.");
					}
				}
				break;
			case "look": //look at the room
				command = new Look(this);
				command.execute();
				System.out.println(command.getResult());
				break;
			case "show":  //Show the backpack, health, and Quest name.
				printBackpackAndWeapon();
				System.out.println("You have " + this.myHealth + " hit points");
				System.out.println("Quest: " + currentState.getQuestName());				
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
		if(myBackpack.containsKey(name.toLowerCase())) {  //BUT, do we already have it?
			System.out.println("You already have that item!");
		} else {  //No. Add it as planned.
			myBackpack.put(name.toLowerCase(), ob);
			myNumberOfObjects++;  //Number of Objects in map
		}
	}
	
	/**
	 * 
	 * @param object
	 */
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

	/**
	 * 
	 */
	public void printBackpackAndWeapon() {
		if(myNumberOfObjects == 0) { //Empty backpack = No items!
			System.out.println("You have no items!");
		} else {  //You have items! 
			System.out.println("You currently have " + myNumberOfObjects + " items: ");
			for(String name : myBackpack.keySet()) {  //For each String in the key set...
				System.out.print(name.toLowerCase() + " ");  //Print the names
			}
			System.out.println(); 
		}
		if(hasWeapon) { //Do I have a Weapon?
			System.out.println("You are equipped with a " + myWeapon.getWeaponName());
		} else {
			System.out.println("You have no weapon.");
		}
	}

	/**
	 * 
	 * @param item
	 * @return
	 */
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
	
	/**
	 * 
	 * @param dir
	 * @return
	 */
	public boolean checkDir(String dir) {
		for(int i = 0; i < myDirections.length; i++) {
			if(myDirections[i].equals(dir.toLowerCase())) { //If the input direction is valid...
				return true;
			}
		}
		return false;  //Input direction not valid!
	}
		
	/**
	 * 
	 * @param health
	 */
  	public void addHealth(int health) {
 	int holder, middle, result;
		if((myHealth + health) <= myMaxHealth) {  //Are we above 100?
			myHealth += health; //Nope
			System.out.println("You gained " + health + " hit points");
		} else {
			//We are, so we have to do a little math.
			holder = myHealth + health; //Take the Player's health and add the health to it.
			middle = health + myMaxHealth;   //Now, take the max health and add the health to it.
			result = middle - holder;  //Subtract them.
			myHealth += result; //Add the difference. Should be the max health
			System.out.println("You gained " + health + " hit points");
			System.out.println("You are at full health");
		}
	}
	
	/**
	 * addMaxHealth() increments the Player's max health allotment.
	 */
	public void addMaxHealth() {
		myMaxHealth += 20;
		myHealth = myMaxHealth;  //Replenish the Player's health completely
	}
	
	//Take it away
	public void subtractHealth(int sub) {
		myHealth -= sub;
		System.out.println("You lost " + sub + " hit points.");
		if(myHealth <= 0) {
			System.out.println("You have die.");
			System.exit(1);
		}
	}
	
	//Get the Player's name
	public String getName() {
		return myName;
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

	//Set my Weapon
	public void setWeapon(Weapon w) {
		myWeapon = w;
		hasWeapon = true;  //We now have a Weapon
	}
	
	//Does the Player have a Weapon?
	public boolean hasWeapon() {
		return hasWeapon;
	}
	
	//Get my Weapon
	public Weapon getWeapon() {
		return myWeapon;
	}
	
	//If a Player decides to drop a Weapon...
	public void setHasNoWeapon() {
		myWeapon = null;
		hasWeapon = false;
	}
	
	//Is my backpack full?
	public boolean backpackFull() {
		return myNumberOfObjects == myLimit;
	}
	
	//Inflict pain on a Monster :D
	public void dealDamage(Monster m) {
		System.out.println("You attack first");
		if(hasWeapon) {  //If the Player has a Weapon...
			int damage = this.getWeapon().getWeaponDamage();
			m.subtractHealth(damage);
			System.out.println(m.getName() + " lost " + damage + " hit points");
		} else {  //Else, do 5 hit points of damage
			m.subtractHealth(5);
			System.out.println(m.getName() + " lost 5 hit points");
			//Else, print out the health
		}
	}
	
	public ObjectInterface getObject(String name) {
		//Kind of uses the Iterator
		ObjectInterface ob = null;
		for(String entry : myBackpack.keySet()) {
			if(entry.equals(name.toLowerCase())) {
				ob = myBackpack.get(entry);
			}
		}
		return ob;
	}
	
	//Quest stuff
	
	//Put me on a Quest!
	public void setNewQuest(Quest q) {
		currentState.setQuest(q);
	}
	
	//Do we have the item for the Quest?
	public boolean hasItem() {
		return currentState.hasItem();
	}
	
	//Get the name of the Quest
	public String getQuestName() {
		return currentState.getQuestName();
	}
	
	//Get the id of the Quest
	public int getQuestId() {
		return currentState.getId();
	}
	
	//Set the new state
	public void setQuestState(QuestState qs) {
		currentState = qs;
	}
	
	//Get the Actual Quest
	public Quest getActualQuest() {
		return myCurrentQuest;
	}
	
	//Set the actual Quest
	public void setActualQuest(Quest q) {
		myCurrentQuest = q;
	}
	
	//Get the "No Quest" state
	public QuestState getNoQuestState() {
		return noQuestState;
	}
	
	//Get the "On Quest" state
	public QuestState getOnQuestState() {
		return onQuestState;
	}
	
	//Get the "Has QuestItem" state
	public QuestState getHasQuestItemState() {
		return hasQuestItemState;
	}
	
	public QuestState getCurrentState() {
		return currentState;
	}
}



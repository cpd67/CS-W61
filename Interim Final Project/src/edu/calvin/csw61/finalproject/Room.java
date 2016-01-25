package edu.calvin.csw61.finalproject;

import java.util.ArrayList;
import edu.calvin.csw61.weapons.Weapon;

/**
 * Room holds ObjectInterfaces, NPCs, and/or Monsters. 
 * They also allow Players to traverse in the game from one spot to another.
 */
public class Room {
	
	//Description of Room
	private String myDescriptor;
	//ObjectInterfaces that the Room has
	private ArrayList<ObjectInterface> myObjects;
	//Booleans that determine:
	//- What Doors the Room has
	//- Whether there is an NPC and/or Monster in the Room
	//- Whether the Room needs an NPC and/or Monster
	//- Whether a Player is in the Room or not
	private boolean myNorthDoor, mySouthDoor, myEastDoor, myWestDoor, hasNPC, hasMonster, needsNPC, needsMonster, playerPresent;
	//Monster in the Room
	private Monster myMonster;
	//NPC in the Room
	private NPC myNPC;
	//Doors and WallBehaviors in the Room
	private ArrayList<ApertureBehavior> myApertures;
	//Number of ObjectInterfaces currently in the Room
	private int myNumberOfObjects;
	//Store a reference to the next Room in the form of a number
	private Integer[] myNextRoomNumbers;  
		
	/**
	 * Constructor for the Room class.
	 */
	public Room() {
		this.myObjects = new ArrayList<ObjectInterface>();
		this.myNumberOfObjects = 0;
		this.myNorthDoor = this.mySouthDoor = this.myEastDoor = this.myWestDoor = this.hasNPC = this.hasMonster = false;
		this.myDescriptor = "";
		this.playerPresent = false;
		this.hasNPC = false;
		this.hasMonster = false;
		this.needsMonster = false;
		this.needsNPC = false;
		this.myApertures = new ArrayList<ApertureBehavior>(); //List of Apertures
		this.myNextRoomNumbers = new Integer[4]; //Four Doors = four possible Rooms
	}
	
	/**
	 * Explicit-constructor for a Room.
	 * @param: north, a boolean indicating whether a Door should be in that direction. 
	 * @param: south, a boolean indicating whether a Door should be in that direction.
	 * @param: east, a boolean indicating whether a Door should be in that direction.
	 * @param: west, a boolean indicating whether a Door should be in that direction.
	 * @param: needNPC, a boolean indicating whether an NPC should be in the Room.
	 * @param: needMonster, a boolean indicating whether a Monster should be in the Room.
	 * @param: nextRooms, an array of Integers indicating the connected Rooms (in the HashMap).
	 */
	public Room(boolean north, boolean south, boolean east, boolean west, boolean needNPC, boolean needMonster, Integer[] nextRooms){
		this.myNorthDoor = north;
		this.mySouthDoor = south;
		this.myEastDoor = east;
		this.myWestDoor = west;
		this.myApertures = new ArrayList<ApertureBehavior>(); //List of Apertures
		this.myNextRoomNumbers = nextRooms;
		this.setDoors();
		this.needsNPC = needNPC;  //Do I need an NPC? 
		this.needsMonster = needMonster;  //How about a Monster?
		this.hasMonster = false;
		this.hasNPC = false;
		this.myObjects = new ArrayList<ObjectInterface>();  //I can store Objects
		this.myNumberOfObjects = 0;
	}
		
	/**
	 * Accessor for the Room's description.
	 * @return: myDescriptor, a String representing the description of the Room.
	 */
	public String getDescriptor() {
		return myDescriptor;
	}
	
	/**
	 * Mutator for the Room's description.
	 * @param: descriptor, a String representing the new description of the Room.
	 */
	public void setDescriptor(String descriptor){
		myDescriptor = descriptor;
	}

	/**
	 * Mutator that sets the Doors (and WallBehaviors) of the Room.
	 */
	public void setDoors(){
		if(myNorthDoor) { //North
			ApertureBehavior northDoor = new Door(this, myNextRoomNumbers[0], false, false, "north");
			myApertures.add(northDoor);
		} else {  //Add a WallBehavior if false (no Door)
			ApertureBehavior wall = new WallBehavior("wall", "north");
			myApertures.add(wall);
		}
		
		if(mySouthDoor) {  //South
			ApertureBehavior southDoor = new Door(this, myNextRoomNumbers[1], false, false, "south");
			myApertures.add(southDoor);
		} else { //Add a WallBehavior if false (no Door)
			ApertureBehavior wall = new WallBehavior("wall", "south");
			myApertures.add(wall);
		}
		
		if(myEastDoor) {  //East
			ApertureBehavior eastDoor = new Door(this, myNextRoomNumbers[2], false, false, "east");
			myApertures.add(eastDoor);
		} else { //Add a WallBehavior if false (no Door)
			ApertureBehavior wall = new WallBehavior("wall", "east");
			myApertures.add(wall);
		}
		
		if(myWestDoor) {  //West
			ApertureBehavior westDoor = new Door(this, myNextRoomNumbers[3], false, false, "west");
			myApertures.add(westDoor);
		} else { //Add a WallBehavior if false (no Door)
			ApertureBehavior wall = new WallBehavior("wall", "west");
			myApertures.add(wall);
		}
	}
	
	/**
	 * Check if there is a Door in the north direction.
	 * @return: a boolean indicating whether or not a Door is in the north direction.
	 */
	public boolean isNorthDoor() {
		return myNorthDoor;
	}
	
	/**
	 * Check if there is a Door in the south direction.
	 * @return: a boolean indicating whether or not a Door is in the south direction.
	 */
	public boolean isSouthDoor() {
		return mySouthDoor;
	}
	
	/**
	 * Check if there is a Door in the east direction.
	 * @return: a boolean indicating whether or not a Door is in the east direction.
	 */
	public boolean isEastDoor() {
		return myEastDoor;
	}
	
	/**
	 * Check if there is a Door in the west direction.
	 * @return: a boolean indicating whether or not a Door is in the west direction.
	 */
	public boolean isWestDoor() {
		return myWestDoor;
	}
	
	/**
	 * Accessor for the Door of a Room in a specific direction.
	 * @param: direction, a String representing the direction of the Door to get.
	 * @return: the Door in the specified direction, or null if not found.
	 */
	public Door getDoor(String direction){
		for(int i = 0; i < myApertures.size(); i++){
			//If not a WallBehavior...
			if(myApertures.get(i).getName().equals("door")){  
				//Typecast
				Door door = (Door)myApertures.get(i);
				//Check the direction
				if(door.getDir().equals(direction)) {
					return door;
				}
			}
		}
		//Return null if not found!
		return null;
	}
	
	/**
	 * addObject() adds an ObjectInterface to a Room.
	 * (If it's a Weapon, there can only be one in a Room at a time).
	 * @param: ob, an ObjectInterface to add to the Room.
	 * @return: a boolean indicating if we can add the ObjectInterface to the Room.
	 */
	public boolean addObject(ObjectInterface ob) {
		if(ob instanceof WeaponAdapter) { //If the Object to add is a WeaponAdapter...
			if(hasObject(ob.getName().toLowerCase())) { //Only one weapon at a time.
				return false; //Don't add it.
			}
		}
		myObjects.add(ob);  //Else, add the Object
		return true;
	}
	
	/**
	 * Mutator that sets an NPC to the Room.
	 * @param: name, a String representing the name of the NPC to add to the Room.
	 */
	public void setNPC(String name){
		NPC npc = new NPC(name.toLowerCase());
		myNPC = npc;
		hasNPC = true;
		needsNPC = false; //No longer needs an NPC
	}
	
	/**
	 * Mutator that sets a Monster to the Room.
	 * @param: name, a String representing the name of the Monster to add to the Room.
	 */
	public void setMonster(String name){
		Monster monster = new Monster(name.toLowerCase());
		myMonster = monster;
		hasMonster = true;
		needsMonster = false; //No longer needs a Monster
	}
	
	/**
	 * Check if the Room needs an NPC.
	 * @return: a boolean indicating whether or not the Room needs an NPC.
	 */
	public boolean needNPC() {
		return needsNPC;
	}
	
	/**
	 * Check if the Room needs a Monster.
	 * @return: a boolean indicating whether or not the Room needs a Monster.
	 */
	public boolean needMonster() {
		return needsMonster;
	}
	
	/**
	 * Check if the Room has a Monster.
	 * @return: a boolean indicating whether or not the Room has a Monster.
	 */
	public boolean hasMonster() {
		return hasMonster;
	}
	
	/**
	 * Check if the Room has an NPC.
	 * @return: a boolean indicating whether or not the Room has an NPC.
	 */
	public boolean hasNPC() {
		return hasNPC;
	}
	
	/**
	 * Accessor for the NPC of the Room.
	 * @return: the NPC of the Room, or null if one is not found.
	 */
	public NPC getNPC() {
		if(hasNPC) {  //I do have an NPC
			return myNPC;
		}
		return null;  //No NPC otherwise
	}
	
	/**
	 * Accessor for the Monster of the Room.
	 * @return: the Monster of the Room, or null if one is not found.
	 */
	public Monster getMonster() {
		if(hasMonster) {  //I do have a Monster
			return myMonster;
		}
		return null; //No Monster otherwise
	}
	
	/**
	 * Accessor for the number of ObjectInterfaces in the Room.
	 * @return: myNumberOfObjects, an int representing the number of ObjectInterfaces
	 * 			currently in the Room.
	 */
	public int getNumObjects() {
		myNumberOfObjects = myObjects.size();  //Get the size of the ArrayList holding 						
		return myNumberOfObjects;              //the ObjectInterfaces
	}
	
	/**
	 * Check if the Player is in the Room.
	 * @return: a boolean indicating whether or not the Player is in the Room.
	 */
	public boolean hasPlayer() {
		return playerPresent;
	}
	
	/**
	 * Mutator that is called when the Player is not in the Room.
	 */
	public void setNoPlayer() {
		playerPresent = false; 
	}
	
	/**
	 * Accessor for the ArrayList of ApertureBehaviors (Doors and WallBehaviors.
	 * @return: myApertures, the ArrayList of ApertureBehaviors.
	 */
	public ArrayList<ApertureBehavior> getAperatures() {
		return myApertures;
	}
	
	/**
	 * removeObject() takes out an ObjectInterface from the Room.
	 * @param: name, a String representing the name of the ObjectInterface to take out.
	 */
	public void removeObject(String name) {
		for(int i = 0; i < myObjects.size(); i++) {
			//If we've found the ObjectInterface...
			if(myObjects.get(i).getName().equals(name.toLowerCase())) {
				//Remove and break
				myObjects.remove(i);
				break;
			}
		}
	}
	
	/**
	 * Check if an ObjectInterface is in the Room.
	 * @param: name, the String of the ObjectInterface to find in the Room.
	 * @return: a boolean indicating whether or not the ObjectInterface is in the Room.
	 */
	public boolean hasObject(String name) {
		for(int i = 0; i < myObjects.size(); i++) {
			if(myObjects.get(i).getName().equals(name.toLowerCase())) {
				return true;
			}
		}
		return false; 
	}
	
	/**
	 * Accessor for a specific ObjectInterface in the Room.
	 * @param: name, a String representing the ObjectInterface to find.
	 * @return: the ObjectInterface, or null if not found.
	 */
	public ObjectInterface getObject(String name) {
		ObjectInterface holder = null;
		for(int i = 0; i < myObjects.size(); i++) {
			//If we've found the ObjectInterface
			if(myObjects.get(i).getName().equals(name.toLowerCase())) {
				//Assign and return it
				holder = myObjects.get(i);
				return holder;
			}
		}
		return holder; //We don't have the object otherwise...
	}
	
	/**
	 * showObjects() prints out the ObjectInterfaces that are currently in the Room.
	 * @return: a String representing the ObjectInterfaces that are in the Room.
	 */
	public String showObjects() {
		String result = "";
		if(myObjects.size() == 0) {
			result = "";
		} else {
			result = "The room has the following items: ";
			for(int i = 0; i < myObjects.size(); i++) {
				result += myObjects.get(i).getName() + " ";
			}
			result += "\n";
		}
		return result;
	}
	
	/**
	 * Capitalizes the first letter of names.
	 * (Adapted from StackOverflow)
	 * @param: str, the String to capitalize.
	 * @return: the String with the first letter capitalized
	 */
	public String capitalizeLetter(String str) {
		return str.substring(0, 1).toUpperCase() + str.substring(1);
	}
	
	/**
	 * showPeople() prints out the NPCs and Monsters that are currently in the Room.
	 * @return: a String representing the NPCs and Monsters currently in the Room.
	 */
	public String showPeople() {
		String result = "";
		if(hasMonster) {
			result = "A " + capitalizeLetter(myMonster.getName()) + " is in the room. ";
			if(myMonster.hasWeapon()) { //Does the Monster have a weapon?
				result += "It has a " + myMonster.getWeapon().getWeaponName().toLowerCase() + ".\n";
			} else {
				result += "It doesn't have a weapon.\n";
			}
		}
		
		//NPC
		if(hasNPC) {
			//get the Name of the NPC
			//Name + "Lines.txt" 
			//SPit out the lines
			result += capitalizeLetter(myNPC.getName()) + " is in the room.";
		}
		return result;
	}
	
	/**
	 * addMonsterItem() adds the Monster's ObjectInterface and Weapon to the Room
	 * when it dies. 
	 */
	public void addMonsterItem() {
		if(myMonster.hasWeapon()) { //If the Monster has a weapon...
			//Wrap it up in an Adapter
			WeaponAdapter weaponAdapt = new WeaponAdapter(myMonster.getWeapon());
			this.addObject(weaponAdapt); //Use our addObject() method to avoid repeats
		}
		
		if(myMonster.hasObject()) { //If the Monster has an object...
			System.out.println(capitalizeLetter(myMonster.getName()) + " has dropped a " + myMonster.getObject().getName());
			this.addObject(myMonster.getObject());  //Get the ObjectInterface, use our addObject() method to avoid repeats
		}
	}
	
	/**
	 * Mutator for the Weapon that a Monster has.
	 * @param: w, the Weapon that the Monster should have.
	 */
	public void setMonWeapon(Weapon w) {
		this.myMonster.setWeapon(w);
	}
	
	/**
	 * removeMonster() takes out the Monster in the Room once it dies.
	 */
	public void removeMonster() {
		if(hasMonster) {
			this.myMonster = null;
			this.hasMonster = false;
			this.needsMonster = true;
		}
	}
}
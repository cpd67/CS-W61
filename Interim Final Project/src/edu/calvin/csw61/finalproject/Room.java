package edu.calvin.csw61.finalproject;

import java.util.ArrayList;
import edu.calvin.csw61.weapons.Weapon;

public class Room {
	
	private String myDescriptor;
	private ArrayList<ObjectInterface> myObjects;
	private boolean myNorthDoor, mySouthDoor, myEastDoor, myWestDoor, hasNPC, hasMonster, needsNPC, needsMonster, playerPresent;
	//bordering rooms
	private Monster myMonster;
	private NPC myNPC;
	private ArrayList<ApertureBehavior> myApertures;
	private Integer[] myNextRoomNumbers;  //Store a reference to the next Room number
	//myNextRoomNumbers = {North Room, South Room, East Room, West Room};
	//No room = -1 in the slot.
	//Outside = -2
		
	public Room() {
		myObjects = new ArrayList<ObjectInterface>();
		myNorthDoor = mySouthDoor = myEastDoor = myWestDoor = hasNPC = hasMonster = false;
		myDescriptor = "";
		playerPresent = false;
		hasNPC = false;
		hasMonster = false;
		needsMonster = false;
		needsNPC = false;
		myApertures = new ArrayList<ApertureBehavior>(); //List of Apertures
		myNextRoomNumbers = new Integer[4]; //Four Doors = four possible Rooms
	}
	
	//explicit constructor
	//If no Doors, then the Room will have the NextRoomNumber as itself (it's own Room number in the HashMap of Rooms).
	public Room(boolean north, boolean south, boolean east, boolean west, boolean needNPC, boolean needMonster, Integer[] nextRooms){
		myNorthDoor = north;
		mySouthDoor = south;
		myEastDoor = east;
		myWestDoor = west;
		myApertures = new ArrayList<ApertureBehavior>(); //List of Apertures
		myNextRoomNumbers = nextRooms;
		setDoors();
		needsNPC = needNPC;  //Do I need an NPC? 
		needsMonster = needMonster;  //How about a Monster?
		hasMonster = false;
		hasNPC = false;
		myObjects = new ArrayList<ObjectInterface>();  //I can store Objects
	}
	
	public String getDescriptor() {
		return myDescriptor;
	}
	
	public void setDescriptor(String descriptor){
		myDescriptor = descriptor;
	}

	//setDoors
	public void setDoors(){
		if(myNorthDoor) { //North
			ApertureBehavior northDoor = new Door(this, myNextRoomNumbers[0], false, false, "north");
			myApertures.add(northDoor);
		} else {
			ApertureBehavior wall = new WallBehavior("wall", "north");
			myApertures.add(wall);
		}
		
		if(mySouthDoor) {  //South
			ApertureBehavior southDoor = new Door(this, myNextRoomNumbers[1], false, false, "south");
			myApertures.add(southDoor);
		} else {
			ApertureBehavior wall = new WallBehavior("wall", "south");
			myApertures.add(wall);
		}
		
		if(myEastDoor) {  //East
			ApertureBehavior eastDoor = new Door(this, myNextRoomNumbers[2], false, false, "east");
			myApertures.add(eastDoor);
		} else {
			ApertureBehavior wall = new WallBehavior("wall", "east");
			myApertures.add(wall);
		}
		
		if(myWestDoor) {  //West
			ApertureBehavior westDoor = new Door(this, myNextRoomNumbers[3], false, false, "west");
			myApertures.add(westDoor);
		} else {
			ApertureBehavior wall = new WallBehavior("wall", "west");
			myApertures.add(wall);
		}
	}
	
	public boolean isNorthDoor() {
		return myNorthDoor;
	}
		 
	//addObject
	public boolean addObject(ObjectInterface ob) {
		if(ob instanceof WeaponAdapter) { //If the Object to add is a WeaponAdapter...
			if(hasObject(ob.getName().toLowerCase())) { //Only one weapon at a time.
				return false; //Don't add it.
			}
		}
		myObjects.add(ob);  //Else, add the Object
		return true;
	}
	
	//setNPC
	public void setNPC(String name){
		NPC npc = new NPC(name.toLowerCase());
		myNPC = npc;
		hasNPC = true;
		needsNPC = false; //No longer needs an NPC
	}
	
	//setMonster
	public void setMonster(String name){
		Monster monster = new Monster(name.toLowerCase());
		myMonster = monster;
		hasMonster = true;
		needsMonster = false; //No longer needs a Monster
	}
	
	//DO I need an NPC?
	public boolean needNPC() {
		return needsNPC;
	}
	
	//How about a Monster?
	public boolean needMonster() {
		return needsMonster;
	}
	
	//Do I even have a Monster?
	public boolean hasMonster() {
		return hasMonster;
	}
	
	//Do I even have an NPC?
	public boolean hasNPC() {
		return hasNPC;
	}
	
	//Get my NPC (If I have one)
	public NPC getNPC() {
		if(hasNPC) {  //I do have an NPC
			return myNPC;
		}
		return null;  //No NPC otherwise
	}
	
	//Get my NPC (If I have one)
	public Monster getMonster() {
		if(hasMonster) {  //I do have a Monster
			return myMonster;
		}
		return null; //No Monster otherwise
	}
	
	//Get myObjects (If I have any)
	public ArrayList<ObjectInterface> getObjects() {
		return myObjects;
	}
	
	//Is the Player in the Room?
	public boolean hasPlayer() {
		return playerPresent;
	}
	
	//The Player is no longer in the Room.
	public void setNoPlayer() {
		playerPresent = false; 
	}
	
	//Get the Apertures in the Room
	public ArrayList<ApertureBehavior> getAperatures() {
		return myApertures;
	}
	
	//Take an Object from the Room (if the Player takes one).
	public void removeObject(String name) {
		for(int i = 0; i < myObjects.size(); i++) {
			if(myObjects.get(i).getName().equals(name.toLowerCase())) {
				myObjects.remove(i);
				break;
			}
		}
	}
	
	//Is the Object in the Room?
	public boolean hasObject(String name) {
		for(int i = 0; i < myObjects.size(); i++) {
			if(myObjects.get(i).getName().equals(name.toLowerCase())) {
				return true;
			}
		}
		return false; 
	}
	
	//Get a specific Object from the Room
	public ObjectInterface getObject(String name) {
		ObjectInterface holder = null;
		for(int i = 0; i < myObjects.size(); i++) {
			if(myObjects.get(i).getName().equals(name.toLowerCase())) {
				holder = myObjects.get(i);
				return holder;
			}
		}
		return holder; //We don't have the object...
	}
	
	//Show the Objects that are in the current Room.
	public void showObjects() {
		if(myObjects.size() == 0) {
			System.out.println("There are no items in the room");
		} else {
			System.out.println("The room has the following items: ");
			for(int i = 0; i < myObjects.size(); i++) {
				System.out.print(myObjects.get(i).getName() + " ");
			}
			System.out.println();
		}
	}
	
	//Show the people in the Room and their objects.
	public void showPeople() {
		if(hasMonster) {
			System.out.println(myMonster.getName() + " is in the room");
			if(myMonster.hasWeapon()) { //Does the Monster have a weapon?
				System.out.println(myMonster.getName() + " has a " + myMonster.getWeapon().getWeaponName().toLowerCase());
			} else {
				System.out.println(myMonster.getName() + " doesn't have a weapon");
			}
			
			if(myMonster.hasObject()) { //Does the Monster have an object?
				System.out.println(myMonster.getName() + " has a " + myMonster.getObject().getName().toLowerCase());
			} else {
				System.out.println(myMonster.getName() + " doesn't have an object");
			}
		}
		
		if(hasNPC) {
			System.out.println(myNPC.getName() + " is in the room");
			if(myNPC.hasOb()) { //Does the NPC have an object?
				System.out.println(myNPC.getName() + " has an object");				
			} else {
				System.out.println(myNPC.getName() + " doesn't have an object");
			}
		}
	}
	
	//Add the Monster's Object if he's dead.
	public void addMonsterItem() {
		if(myMonster.hasWeapon()) { //If the Monster has a weapon...
			WeaponAdapter weaponAdapt = new WeaponAdapter(myMonster.getWeapon());
			addObject(weaponAdapt); //Use our addObject() method to avoid repeats
		}
		
		if(myMonster.hasObject()) { //If the Monster has an object...
			addObject(myMonster.getObject());  //Get the Object, use our addObject() method to avoid repeats
		}
	}
	
	//Give the Monster a Weapon
	public void setMonWeapon(Weapon w) {
		myMonster.setWeapon(w);
	}
	
	//Take out the Monster
	public void removeMonster() {
		if(hasMonster) {
			myMonster = null;
			hasMonster = false;
			needsMonster = true;
		}
	}
}
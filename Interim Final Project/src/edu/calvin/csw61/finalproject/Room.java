package edu.calvin.csw61.finalproject;

import java.util.ArrayList;

public class Room {
	
	private String myDescriptor;
	private ObjectInterface[] myObjects;
	private boolean myNorthDoor, mySouthDoor, myEastDoor, myWestDoor, hasNPC, hasMonster, playerPresent;
	//bordering rooms
	private ObjectInterface myMonster;
	private ObjectInterface myNPC;
	private ArrayList<ApertureBehavior> myApertures;
	private Integer[] myNextRoomNumbers;  //Store a reference to the next Room number
	//myNextRoomNumbers = {North Room, South Room, East Room, West Room};
	//No room = -1 in the slot.
	//Outside = -2
		
	public Room() {
		myObjects = new ObjectInterface[2];
		myNorthDoor = mySouthDoor = myEastDoor = myWestDoor = hasNPC = hasMonster = false;
		myDescriptor = "";
		playerPresent = false;
		hasNPC = false;
		hasMonster = false;
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
		hasNPC = needNPC;  //Do I need an NPC? 
		hasMonster = needMonster;  //How about a Monster?
		myObjects = new ObjectInterface[2];  //I can store Objects
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
		} 
		
		if(mySouthDoor) {  //South
			ApertureBehavior southDoor = new Door(this, myNextRoomNumbers[1], false, false, "south");
			myApertures.add(southDoor);
		} 
		
		if(myEastDoor) {  //East
			ApertureBehavior eastDoor = new Door(this, myNextRoomNumbers[2], false, false, "east");
			myApertures.add(eastDoor);
		}
		
		if(myWestDoor) {  //West
			ApertureBehavior westDoor = new Door(this, myNextRoomNumbers[3], false, false, "west");
			myApertures.add(westDoor);
		}
	}
	
	//setObjects
	public void setObjects(ObjectInterface o1, ObjectInterface o2){
		myObjects[0] = o1;
		myObjects[1] = o2;
	}
	
	//setNPC
	public void setNPC(String name){
		ObjectInterface npc = new NPC(name);
		myNPC = npc;
	}
	
	//setMonster
	public void setMonster(String name){
		ObjectInterface monster = new Monster(name);
		myMonster = monster;
	}
	
	//DO I need an NPC?
	public boolean needNPC() {
		return hasNPC;
	}
	
	//How about a Monster?
	public boolean needMonster() {
		return hasMonster;
	}
	
	//Get my NPC (If I have one)
	public ObjectInterface getNPC() {
		if(hasNPC) {  //I do have an NPC
			return myNPC;
		}
		return null;  //No NPC otherwise
	}
	
	//Get my NPC (If I have one)
	public ObjectInterface getMonster() {
		if(hasMonster) {  //I do have a Monster
			return myMonster;
		}
		return null; //No Monster otherwise
	}
	
	//Get myObjects (If I have any)
	public ObjectInterface[] getObjects() {
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
	public void removeObject(int number) {
		myObjects[number] = null; //Make it null, check if it's null later.
	}
}


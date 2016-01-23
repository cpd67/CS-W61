package edu.calvin.csw61.finalproject;

import java.util.ArrayList;

public class Room {
	
	private String myDescriptor;
	private ArrayList<ObjectInterface> myObjects;
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
		myObjects = new ArrayList<ObjectInterface>();
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
	
	public boolean isSouthDoor() {
		return mySouthDoor;
	}
	
	public boolean isEastDoor() {
		return myEastDoor;
	}
	
	public boolean isWestDoor() {
		return myWestDoor;
	}
	
	public Door getDoor(String direction){
		for(int i = 0; i < myApertures.size(); i++){
			if(myApertures.get(i).getName().equals("door")){
				Door door = (Door)myApertures.get(i);
				if(door.getDir().equals(direction)) {
					return door;
				}
			}
		}
		return null;
	}
	
	//addObject
	public void addObject(ObjectInterface ob) {
		myObjects.add(ob);
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
}
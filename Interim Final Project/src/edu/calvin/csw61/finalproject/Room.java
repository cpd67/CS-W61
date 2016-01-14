package edu.calvin.csw61.finalproject;

public class Room {
	private String myDescriptor;
	private ObjectInterface[] myObjects;
	private boolean myNorthDoor, mySouthDoor, myEastDoor, myWestDoor, hasNPC, hasMonster, playerPresent;
	//bordering rooms
	private ObjectInterface myMonster;
	private ObjectInterface myNPC;
	
	public Room() {
		myObjects = new ObjectInterface[2];
		myNorthDoor = mySouthDoor = myEastDoor = myWestDoor = hasNPC = hasMonster = false;
		myDescriptor = "";
		playerPresent = false;
		hasNPC = false;
		hasMonster = false;
	}
	
	//explicit constructor
	public Room(boolean north, boolean south, boolean east, boolean west, boolean needNPC, boolean needMonster){
		setDoors(north, south, east, west);
		hasNPC = needNPC;  //Do I need an NPC? 
		hasMonster = needMonster;  //How about a Monster?
	}
	
	public String getDescriptor() {
		return myDescriptor;
	}
	
	public void setDescriptor(String descriptor){
		myDescriptor = descriptor;
	}

	//setDoors
	public void setDoors(boolean north, boolean south, boolean east, boolean west){
		myNorthDoor = north;
		mySouthDoor = south;
		myEastDoor = east;
		myWestDoor = west;
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
	
}


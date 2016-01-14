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
	}
	//explicit constructor
	public Room(boolean north, boolean south, boolean east, boolean west){
		setDoors(north, south, east, west);
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
		hasNPC = true;
	}
	//setMonster
	public void setMonster(String name){
		ObjectInterface monster= new Monster(name);
		myMonster = monster;
		hasMonster = true;
	}

}


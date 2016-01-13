package edu.calvin.csw61.finalproject;

public class Room {
	private String myDescriptor;
	private ObjectInterface[] myObjects;
	private boolean myNorthDoor, mySouthDoor, myEastDoor, myWestDoor, hasNPC, hasMonster, playerPresent;
	//bordering rooms
	private Monster myMonster;
	private NPC myNPC;
	
	public Room() {
		myObjects = new ObjectInterface[2];
		myNorthDoor = mySouthDoor = myEastDoor = myWestDoor = hasNPC = hasMonster = false;
		myDescriptor = "";
		playerPresent = false;
	}
	//explicit constructor
	public Room(boolean north, boolean south, boolean east, boolean west, NPC npc, Monster monster){
		setDoors(north, south, east, west);
		if(hasNPC){
			setNPC(npc);
		}
		if(hasMonster) {
			setMonster(monster);
		}
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
	public void setNPC(NPC npc){
		myNPC = npc;
	}
	//setMonster
	public void setMonster(Monster monster){
		myMonster = monster;
	}
}

package edu.calvin.csw61.finalproject;

//Needs to implement ObjectInterface so that we can catch when a Player wants
//to eat an NPC
public class NPC extends Character implements ObjectInterface, ActionBehavior {
	
	private boolean hasObject;
	
	//Instance variables are inheirted from the superclass, Character
	//Constructor for an NPC who doesn't have an Object
	public NPC(String name) {
		this.myName = name;
		this.myObj = null; 
		this.hasObject = false;
	}
	
	//Constructor for an NPC who doesn't have an Object
	public NPC(String name, ObjectInterface ob) {
		this.myName = name;
		this.myObj = ob;
		this.hasObject = true;
	}
	
	//Acting is talking
	public void act(Player p) {
		
	}
	
	//Give an Object to the Player (if we have one).
	public void give(Player p) {
		if(p.hasItem(myObj.getName())) {
			//Don't add the object.
		} else {
			p.addObject(myObj.getName(), myObj);
		}
	}
}

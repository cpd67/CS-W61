package edu.calvin.csw61.finalproject;

import edu.calvin.csw61.weapons.Weapon;

public class NPC extends Character implements ObjectInterface, ActionBehavior {
	
	private boolean hasObject;
	private boolean hasQuest;
	private boolean needsQuestOb;
	private String myNeededOb;
	private Quest myQuest;
	
	//Instance variables are inheirted from the superclass, Character
	
	//Constructor for an NPC who doesn't have an Object
	public NPC(String name) {
		this.myName = name;
		this.myObj = null; 
		this.hasObject = false;
		this.myQuest = null; //No Quest
		this.hasQuest = false;
		this.needsQuestOb = false;
		this.myNeededOb = ""; //Doesn't need an object...yet
	}
	
	//Constructor for an NPC who has an Object
	public NPC(String name, ObjectInterface ob) {
		this.myName = name;
		this.myObj = ob;
		this.hasObject = true;
		this.myQuest = null; 
		this.hasQuest = false;
		this.needsQuestOb = false;
		this.myNeededOb = "";
	}
	
	//Acting is talking
	public void act(Player p) {
		Quest q = myQuest; 
		if(hasQuest) {
			//Talk to the Player...
			//(Will change to reading lines from a file)
			System.out.println("Hello! Here is a Quest!");
			p.setQuest(q);  //Set the Quest
		}
		give(p);  //Give the Player the Object (Try to)
	}
	
	//Give an Object to the Player (if we have one).
	public void give(Player p) {
		if(p.hasItem(myObj.getName())) {  //Can't have multiple items in a backpack...
			System.out.println("But...you already " + myObj.getName() + "!");
			System.out.println("Come back when you don't have it!");
		} else { //Player doesn't have the Object
			if(myObj instanceof WeaponAdapter) { //Is it a Weapon?
				if(p.hasWeapon()) {
					//Can't give the Weapon until the Player drops his/hers.
				} else { //Can give the Weapon, should be wrapped in a WeaponAdapter
					WeaponAdapter weaponAdapt = (WeaponAdapter)myObj;
					Weapon givenWeapon = weaponAdapt.getWrappedWeapon();
					p.setWeapon(givenWeapon); //Give the Weapon to the Player
				}
			} else {
				p.addObject(myObj.getName(), myObj);
			}
		}
	}
	
	//Do I need an Quest item?
	public boolean needsQuestOb() {
		return needsQuestOb;
	}
	
	//Recieve an Item from the Player
	public boolean recieve(ObjectInterface ob) {
		//Adhere to the Principle of Least Knowledge
		String givenObject = ob.getName().toLowerCase();
		String neededObject = myNeededOb;
		if(neededObject.equals(givenObject)) {
			setHasNoQuest();
			return true; //Quest complete, set that in the Give Command.
		}
		return false;
	}
	
	//Does the NPC have a Quest?
	public boolean hasQuest() {
		return hasQuest;
	}
		
	//Do I have an Object?
	public boolean hasOb() {
		return hasObject;
	}
	
	//Get the Quest
	public Quest getQuest() {
		return myQuest;
	}
	
	//Set the Quest that the NPC can give to the Player
	public void setQuest(Quest q) {
		ObjectInterface ob = q.getItem();
		myQuest = q;  //Set the Quest
		myNeededOb = ob.getName().toLowerCase(); //Get the name of the needed Quest Item
		needsQuestOb = true; //We now need a Quest Item
		hasQuest = true;
	}
	
	//Quest complete
	public void setHasNoQuest() {
		myQuest = null;
		myNeededOb = null;
		needsQuestOb = false;
		hasQuest = false;
	}
	
	public void setObject(ObjectInterface ob) {
		myObj = ob;
		hasObject = true; //Automatically has an Object
	}
	
}

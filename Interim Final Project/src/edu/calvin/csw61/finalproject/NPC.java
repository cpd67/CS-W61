package edu.calvin.csw61.finalproject;

import edu.calvin.csw61.weapons.Weapon;

/**
 * NPC is a subclass of Character and can talk to a Player, give them ObjectInterfaces,
 * and give them Quests.
 * It implements the ActionBehavior Interface so that it can perform an Action on the Player.
 * (In this case, it would be talking to the Player).
 */
public class NPC extends Character implements ActionBehavior {
	
	//Determines if the NPC has an ObjectInterface to give
	private boolean hasObject;
	//Determines if the NPC has a Quest to give
	private boolean hasQuest;
	//Determines if the NPC still needs a QuestItem
	private boolean needsQuestOb;
	//The needed QuestItem for the corresponding Quest
	private String myNeededOb;
	//The gender of the NPC
	private String myGender;
	//The Quest to give to the Player
	private Quest myQuest;
	
	/**
	 * Constructor for an NPC who doesn't have an ObjectInterface to give.
	 * @param: name, a String representing the name of the NPC.
	 */
	public NPC(String name) {
		this.myName = name;
		this.myObj = null;   //No ObjectInterface to give
		this.hasObject = false;
		this.myQuest = null; //No Quest..yet
		this.hasQuest = false;
		this.needsQuestOb = false;
		this.myNeededOb = ""; //Doesn't need an object...yet
		this.myGender = "";
	}
	
	/**
	 * Constructor for an NPC who does have an ObjectInterface to give.
	 * @param: name, a String representing the name of the NPC.
	 * @param: ob, the ObjectInterface to give to the Player.
	 */
	public NPC(String name, ObjectInterface ob) {
		this.myName = name;  
		this.myObj = ob;  //It has an ObjectInterace to give now
		this.hasObject = true;
		this.myQuest = null; 
		this.hasQuest = false;
		this.needsQuestOb = false;
		this.myNeededOb = "";
		this.myGender = "";
	}
	
	/**
	 * act() allows the NPC to interact with the Player.
	 * @param: p, the handle to the Player. 
	 */
	public void act(Player p) { 
		if(hasQuest) { //If the NPC has a Quest...
			Quest q = myQuest;
			//Name + "beforeLines.txt"
			//Talk to the Player...
			//(Will change to reading lines from a file)
			System.out.println("'Hello! Here is a Quest!'");
			
			//Try to give them the Quest.
			//If the NPC already gave the Player a Quest and the Player 
			//hasn't found the necessary QuestItem...
			String qName = p.getQuestName();
			if(qName.equals(q.getName()) && !p.hasItem(myNeededOb.toLowerCase())) {
				System.out.println("'I already gave you that Quest!'");
				System.out.println("'Come back when you've found my item!'");
			//Else, if the Player has the necessary QuestItem and is in either the 
			//OnQuestState or HasQuestItemState...
			} else if(p.hasItem(q.getItem()) && p.hasItem()) { 
				System.out.println("What's that? You have a missing item?");
				System.out.println("You do! Hand it over!");
				//Give the NPC the QuestItem
				System.out.println("You gave " + getName() + " the " + myNeededOb);
				p.removeObject(myNeededOb.toLowerCase());
				//The NPC no longer has a Quest to give
				setHasNoQuest(); 
				//Reset the State of the Player
				p.setQuestState(p.getNoQuestState());
				p.questComplete();  //Increment the Quest counter
				System.out.println("Quest complete!");
				//Increment the max health allotment for the Player
				p.addMaxHealth();  
			} else {
				//Try to set the Quest
				p.setNewQuest(q);  
			}
		} else {
			//The NPC has no Quest to give
			ReadFile rf = new ReadFile(myName + ".txt");
			rf.readAndPrint();
		}
		//Attempt to give the Player the ObjectInterface 
		give(p);  
	}
	
	/**
	 * give() allows the NPC to give the Player an ObjectInterface.
	 * @param: p, the handle to the Player.
	 */
	public void give(Player p) {
		if(hasObject) { //If the NPC has an ObjectInterface to give...
			 //If the Player already has the ObjectInterface...
			if(p.hasItem(myObj.getName())) { 
				//You can't have more than one of each ObjectInterface
				System.out.println("But...you already " + myObj.getName() + "!");
				System.out.println("Come back when you don't have it!");
			} else { 
				//Player doesn't have the Object
				if(myObj instanceof WeaponAdapter) { //Is it a Weapon?
					//Does the Player already have a Weapon?
					if(p.hasWeapon()) {
						//The Player can only have one Weapon at a time.
						System.out.println("You already have a Weapon!");
						System.out.println("Come back when you've gotten rid of it!");
					} else { 
						//Can give the Weapon, should be wrapped in a WeaponAdapter
						WeaponAdapter weaponAdapt = (WeaponAdapter)myObj;
						Weapon givenWeapon = weaponAdapt.getWrappedWeapon();
						p.setWeapon(givenWeapon); //Give the Weapon to the Player
					}
				} else {
					//It's a generic ObjectInterface
					System.out.println("'Please, take this!'");
					System.out.println("You recieved a " + myObj.getName() + "!");
					p.addObject(myObj.getName(), myObj);
					myObj = null;  //The NPC no longer has an ObjectInterface
					hasObject = false;
				}
			}
			//No ObjectInterface to give
		} else {
			System.out.println("I am sorry...but I have no item to give!");
		}
	}
	
	/**
	 * Determines if an NPC needs a QuestItem.
	 * @return: a boolean indicating whether the NPC still needs the QuestItem or not.
	 */
	public boolean needsQuestOb() {
		return needsQuestOb;
	}
	
	/**
	 * recieve() allows an NPC to recieve a QuestItem from the Player.
	 * (Called in Give).
	 * @param: p, the handle to the Player.
	 * @return: a boolean indicating whether or not the Player has a QuestItem to give.
	 *          (And if it's the one that the NPC needs!)
	 */
	public boolean recieve(Player p) {
		return p.hasItem();
	}
	
	/**
	 * Determines if the NPC has a Quest to give or not.
	 * @return: A boolean indicating whether the NPC has a Quest or not.
	 */
	public boolean hasQuest() {
		return hasQuest;
	}
		
	/**
	 * Determines if the NPC has an ObjectInterface to give or not.
	 * @return: A boolean indicating whether the NPC has an ObjectInterface or not.
	 */
	public boolean hasOb() {
		return hasObject;
	}
	
	/**
	 * Accessor for the Quest that the NPC has.
	 * @return: myQuest, a Quest object.
	 */
	public Quest getQuest() {
		return myQuest;
	}
	
	/**
	 * Mutator for the Quest that the NPC has.
	 * @param: q, a new Quest object to give to the NPC.
	 */
	public void setQuest(Quest q) {
		String name = q.getItem();
		this.myQuest = q;  //Set the Quest
		this.myNeededOb = name.toLowerCase(); //Get the name of the needed Quest Item
		this.needsQuestOb = true; //We now need a Quest Item
		this.hasQuest = true;
	}
	
	/**
	 * Mutator that is called when the NPC's Quest is completed by the Player.
	 */
	public void setHasNoQuest() {
		myQuest = null; //We no longer have a Quest now
		myNeededOb = null;
		needsQuestOb = false;
		hasQuest = false;
	}
	
	/**
	 * Mutator for the held ObjectInterface of the NPC. 
	 * @param: ob, an ObjectInterface indicating the new ObjectInterface to give to the
	 *         NPC.
	 */
	public void setObject(ObjectInterface ob) {
		myObj = ob;
		hasObject = true; //Has an ObjectInterface now
	}	
	
	/**
	 * Mutator for the gender of the NPC.
	 * @param: gen, a String representing the gender of the NPC.
	 */
	public void setGender(String gen) {
		myGender = gen.toLowerCase();
	}
	
	/**
	 * Accessor for the gender of the NPC.
	 * @return: myGender, a String representing the gender of the NPC.
	 */
	public String getGender() {
		return myGender;
	}
}
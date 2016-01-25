package edu.calvin.csw61.finalproject.playercommands;

import edu.calvin.csw61.finalproject.ObjectInterface;
import edu.calvin.csw61.finalproject.Player;
import edu.calvin.csw61.weapons.Weapon;
import edu.calvin.csw61.finalproject.QuestItem;
import edu.calvin.csw61.finalproject.WeaponAdapter;
import edu.calvin.csw61.finalproject.Quest;

/**
 * Take allows a Player to take something.
 * (Implements the Command Interface).
 */
public class Take implements Command {
	private Player myPlayer; //Handle to the Player
	private String myObName;  //Name of ObjectInterface to take
	private String result;  //Result of executing the Command
	
	/**
	 * Constructor for the Take class.
	 * @param: name, a String representing the name of the ObjectInterface to take.
	 * @param: p, a handle to the Player.
	 */
	public Take(String name, Player p) {
		this.myPlayer = p;
		this.myObName = name;
		this.result = "";
	}
	
	/**
	 * execute() executes the act of taking something in the game.
	 */
	public void execute() {
		//Check if there are any ObjectInterface in the Room.
		if(myPlayer.getRoom().getNumObjects() == 0) {  
			result = "There are no items in this room.";
		} else {
			//Is the Object in the Current Room that the Player is in?
			if(myPlayer.getRoom().hasObject(myObName.toLowerCase())) {
				//Is it a Weapon?
				 if(myPlayer.getRoom().getObject(myObName.toLowerCase()) instanceof WeaponAdapter){
					 //If the Player doesn't already have a Weapon...
					 if(!myPlayer.hasWeapon()) { 
						 //Get the weapon from the adapter
						 ObjectInterface checker = myPlayer.getRoom().getObject(myObName.toLowerCase());
						//Typecast
						 WeaponAdapter weaponAdapt = (WeaponAdapter)checker; 
						//Get the weapon
						 Weapon takenWeapon = weaponAdapt.getWrappedWeapon(); 
						 //Set the Weapon
						 myPlayer.setWeapon(takenWeapon);
						 result = "You picked up " + takenWeapon.getWeaponName();
						 //Remove the Object from the Room.
						 myPlayer.getRoom().removeObject(myObName.toLowerCase()); 
					 } else {  //Else, Player already has a Weapon
						 result = "You already have a weapon!" + "\n";
						 result += "Drop your weapon if you want this one!";
					 }
				 } else {
					//Is is not a Weapon
					//Is the Player's backpack full?
					 if(myPlayer.backpackFull()) { 
						 result = "Backpack full! Drop an Item!"; 
					 } else {
						 //No. Add the Object
						myPlayer.addObject(myObName.toLowerCase(), myPlayer.getRoom().getObject(myObName.toLowerCase()));
						//If you are on a Quest and have found the necessary QuestItem...
						if(myPlayer.hasItem() && myObName.toLowerCase().equals(myPlayer.getActualQuest().getItem())) {  
							result = "You found the needed item!\n";
							//Remove the ObjectInterface from the Room.
							myPlayer.getRoom().removeObject(myObName.toLowerCase());  
							result += "You took " + myObName.toLowerCase();
							//Is it a QuestItem and you are not on a Quest?
						} else if(myPlayer.getObject(myObName) instanceof QuestItem) { 
							if(myPlayer.getCurrentState() == myPlayer.getNoQuestState()) {
								//Set a new default Quest
								result = "You found a lost object! Someone may need this...";
								//Default Quest
								Quest q = new Quest("Missing object!", "Find the missing object's owner!", 0, myObName);
								myPlayer.setNewQuest(q); //Set the Quest...
								//Set the state to HasQuestItemState
								myPlayer.setQuestState(myPlayer.getHasQuestItemState()); 
								myPlayer.getRoom().removeObject(myObName.toLowerCase());
								//Else, are you already on a Quest?
							} else if(myPlayer.getCurrentState() == myPlayer.getOnQuestState() || myPlayer.getCurrentState() == myPlayer.getHasQuestItemState()) {
								result = "This looks like someone's missing item...\n";
								result += "BUT, you are already on a Quest!\n";
								result += "Come back for this item when you've already completed your Quest!";	
								myPlayer.removeObject(myObName);  //Take away the ObjectInterface from the Player
							}
						} else {
							//Not a QuestItem, add the Object as normal
							myPlayer.getRoom().removeObject(myObName.toLowerCase());  //Remove the Object from the Room.
							result += "You took " + myObName.toLowerCase();
						}
					}
				}
		//Object is NOT in the Room
		} else { 
			result = myObName.toLowerCase() + " is not in this room.";
		}		
		}
	}
	
	/**
	 * Accessor for the result of executing the command.
	 */
	public String getResult() {
		return result;
	}
}
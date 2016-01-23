package edu.calvin.csw61.finalproject.playercommands;

import edu.calvin.csw61.finalproject.ObjectInterface;
import edu.calvin.csw61.finalproject.Player;
import edu.calvin.csw61.weapons.Weapon;
import edu.calvin.csw61.finalproject.QuestItem;
import edu.calvin.csw61.finalproject.WeaponAdapter;
import edu.calvin.csw61.finalproject.Quest;

public class Take implements Command {
	Player myPlayer;
	String myObName;
	private String result;
	
	public Take(String name, Player p) {
		myPlayer = p;
		myObName = name;
		result = "";
	}
	
	//Add the Object
	public void execute() {
		if(myPlayer.getRoom().getObjects().size() == 0) {  //Are there any objects in the Room?
			result = "There are no items in this room.";
		} else {
			//Is the Object in the Current Room that the Player is in?
			if(myPlayer.getRoom().hasObject(myObName.toLowerCase())) {
				//It is
				//Is it a Weapon?
				 if(myPlayer.getRoom().getObject(myObName.toLowerCase()) instanceof WeaponAdapter){
					 //Yes
					 //Get the weapon from the adapter
					ObjectInterface checker = myPlayer.getRoom().getObject(myObName.toLowerCase());
					WeaponAdapter weaponAdapt = (WeaponAdapter)checker; //Typecast
					Weapon takenWeapon = weaponAdapt.getWrappedWeapon(); //Get the weapon
					if(myPlayer.hasWeapon()) { //Does the Player have a Weapon?
						//Yes
						result = "You already have a weapon!" + "\n";
						result += "Drop your weapon if you want this one!";
						myPlayer.getRoom().showObjects();
					} else {
						//No
						myPlayer.setWeapon(takenWeapon);
						result = "You picked up " + takenWeapon.getWeaponName();
						myPlayer.getRoom().removeObject(myObName.toLowerCase());  //Remove the Object from the Room.
						myPlayer.getRoom().showObjects();
					}
				 } else {
					//Is is not a Weapon
					 if(myPlayer.backpackFull()) { //Is the Player's backpack full?
							result = "Backpack full! Drop an Item!"; 
						} else {
							//No. Add the Object
							myPlayer.addObject(myObName.toLowerCase(), myPlayer.getRoom().getObject(myObName.toLowerCase()));
							if(myPlayer.hasItem() && myObName.toLowerCase().equals(myPlayer.getActualQuest().getItem())) {  //Sets the state!
								result = "You found the needed item!\n";
								myPlayer.getRoom().removeObject(myObName.toLowerCase());  //Remove the Object from the Room.
								result += "You took " + myObName.toLowerCase();
								myPlayer.getRoom().showObjects();
							} else if(myPlayer.getObject(myObName) instanceof QuestItem) {  //Is it a QuestItem?
								if(myPlayer.getCurrentState() == myPlayer.getNoQuestState()) { //And is the Player not on a Quest?
									//Yes
									//Set a new default Quest
									result = "You found a lost object! Someone may need this...";
									Quest q = new Quest("Missing object!", "Find the missing object's owner!", 0, myObName);
									myPlayer.setNewQuest(q); //Set the Quest...
									myPlayer.setQuestState(myPlayer.getHasQuestItemState()); //Set the state to 3
									myPlayer.getRoom().removeObject(myObName.toLowerCase());  //Remove the Object from the Room.
								} else if(myPlayer.getCurrentState() == myPlayer.getOnQuestState() || myPlayer.getCurrentState() == myPlayer.getHasQuestItemState()) {
									result = "This looks like someone's missing item...\n";
									result += "BUT, you are already on a Quest!\n";
									result += "Come back for this item when you've already completed your Quest!";	
									myPlayer.removeObject(myObName);
								}
								} else {
								//Not a QuestItem, add the Object as normal
								myPlayer.getRoom().removeObject(myObName.toLowerCase());  //Remove the Object from the Room.
								result += "You took " + myObName.toLowerCase();
								myPlayer.getRoom().showObjects();
							}
						}
				 }
		//Object is NOT in the Room
		} else { 
			result = myObName.toLowerCase() + " is not in this room.";
		}		
	}
	}
	
	public String getResult() {
		return result;
	}
}

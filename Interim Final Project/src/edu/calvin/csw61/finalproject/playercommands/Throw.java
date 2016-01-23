package edu.calvin.csw61.finalproject.playercommands;

import edu.calvin.csw61.finalproject.ObjectInterface;
import edu.calvin.csw61.finalproject.WeaponAdapter;
import edu.calvin.csw61.finalproject.Player;

public class Throw implements Command {
	
	private Player myPlayer;
	private ObjectInterface myObject;
	private String result;
	
	//Takes the Object to throw and a handle to the Player
	public Throw(ObjectInterface ob, Player p) {
		myPlayer = p;
		myObject = ob;
		result = "";
	}
	
	public void execute() {
		if(myPlayer.getRoom().addObject(myObject)) { //Can we add the item (Weapons only)?
			//Yes
			//Is the object a QuestItem?
			if(myObject.getName().toLowerCase().equals(myPlayer.getActualQuest().getItem())) {
				//Yes
				//Reset the Quest state back to 2!
				result = "You threw the needed Quest item!";
				myPlayer.setQuestState(myPlayer.getOnQuestState());
				result += "You threw " + myObject.getName();
				myPlayer.removeObject(myObject.getName().toLowerCase());
			} else {
				//No
				//Is the Object a WeaponAdapter? (That's NOT already in the room)
				if(!(myObject instanceof WeaponAdapter)) {
					//No
					result = "You threw " + myObject.getName();
					myPlayer.removeObject(myObject.getName().toLowerCase());
				} else {
					//Yes
					//The Player has lost his/her Weapon
					result = "You threw " + myObject.getName();
					myPlayer.setHasNoWeapon();
				}
			}
			
		}  else {
			result += "The " + myObject.getName() + " is already in the room";
		}
	}
	
	public String getResult() {
		return result;
	}
}

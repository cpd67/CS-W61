package edu.calvin.csw61.finalproject.playercommands;

import edu.calvin.csw61.finalproject.ObjectInterface;
import edu.calvin.csw61.finalproject.Player;
import edu.calvin.csw61.finalproject.WeaponAdapter;

public class Drop implements Command {
	private String result;
	
	Player myPlayer;
	ObjectInterface myObject;

	//Takes the Object to drop and a handle to the Player.
	//Check if the Object is in the backpack...
	public Drop(ObjectInterface ob, Player p) {
		myObject = ob;
		myPlayer = p;
		result = "";
	}
	
	public void execute() {
		if(myObject instanceof WeaponAdapter) {
			if(myPlayer.getRoom().addObject(myObject)) { //If the weapon isn't already in the Room...
				result = "You dropped your " + myObject.getName();
				myPlayer.getRoom().showObjects();
				myPlayer.setHasNoWeapon();  //Player no longer has the Weapon.
			} else { //Else, the Weapon is already in the Room...
				result = myObject.getName() + " is already in the room!";
			}

		} else { //Else, it's not a Weapon.
			myPlayer.removeObject(myObject.getName().toLowerCase()); //Take the Object out of the backpack.
			if(myObject.getName().toLowerCase().equals(myPlayer.getActualQuest().getItem())) {
				//Reset the state back to 2
				result = "You lost the missing item!";
				myPlayer.setQuestState(myPlayer.getOnQuestState());
				myPlayer.getRoom().addObject(myObject);  //Add it to the Room.
				result = "You dropped " + myObject.getName();
				myPlayer.getRoom().showObjects();
			} else {
				myPlayer.getRoom().addObject(myObject);  //Add it to the Room.
				result = "You dropped " + myObject.getName();
				myPlayer.getRoom().showObjects();
			}
		}
	}
	
	public String getResult() {
		return result;
	}
}

package edu.calvin.csw61.finalproject.playercommands;

import edu.calvin.csw61.finalproject.Key;
import edu.calvin.csw61.finalproject.Player;
import edu.calvin.csw61.finalproject.Door;

public class Unlock implements Command {
	
	Player myPlayer;
	Key myKey;
	Door myDoor;  //Door to unlock
	private String result;
	
	//Door myDoor;
	
	public Unlock(Player p) { //Door door
		myPlayer = p;
		myKey = new Key("key");
//		myDoor = door;
		result = "";
	}
	
	@Override
	public void execute() {
		//Check if the door is locked...
		if(myDoor.isUnlocked()) {  //Is it unlocked?
			result += "The door is already unlocked";
		} else {  //No, it's not. 
			if(myPlayer.hasItem(myKey.getName())) {  //Does the Player have a Key?
				
			} else {
				result += "You don't have a key.";
			}
		}
	}
	
	public String getResult() {
		return result;
	}
	
}

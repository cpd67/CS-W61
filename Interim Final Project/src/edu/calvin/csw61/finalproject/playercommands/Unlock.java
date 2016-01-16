package edu.calvin.csw61.finalproject.playercommands;

import edu.calvin.csw61.finalproject.Key;
import edu.calvin.csw61.finalproject.Player;

public class Unlock implements Command {
	
	Player myPlayer;
	Key myKey;
	private String result;
	//Door myDoor;
	
	public Unlock(Player p) { //Door door
		myPlayer = p;
		myKey = new Key("key");
		//myDoor = door;
		result = "";
	}
	
	@Override
	public void execute() {
		//Check if the door is locked...
		//if so
		if(myPlayer.hasItem(myKey.getName())) {
			result = "You locked (NOT IMPLEMENTED (NO DOOR TO UNLOCK YET))";
		} else {
			result = "You don't have a key.";
		}
		//else, result = "The door is unlocked..";
	}
	
	public String getResult() {
		return result;
	}
	
}

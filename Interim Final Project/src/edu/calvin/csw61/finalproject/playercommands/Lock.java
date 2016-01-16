package edu.calvin.csw61.finalproject.playercommands;

import edu.calvin.csw61.finalproject.Key;
import edu.calvin.csw61.finalproject.Player;

public class Lock implements Command {
	private String result;
	Player myPlayer;
	Key myKey;
	//Door myDoor;
	
	//NEEDS A DOOR TO LOCK!!!!
	public Lock(Player p) {  //Door door;
		myPlayer = p;
		myKey = new Key("key");
		//myDoor = door;
		result = "";
	}
	
	@Override
	public void execute() {
		//if door is unlocked...
			//Lock the Door
			myPlayer.removeObject(myKey.getName()); //Take out the key
			result = "You locked (NOT IMPLEMENTED (NO DOOR TO LOCK YET))";
		//else, System.out.println("The door is already locked);
	}
	
	public String getResult() {
		return result;
	}
	
}

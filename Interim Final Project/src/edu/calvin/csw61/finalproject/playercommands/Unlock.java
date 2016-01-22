package edu.calvin.csw61.finalproject.playercommands;

import edu.calvin.csw61.finalproject.Key;
import java.util.ArrayList;

import edu.calvin.csw61.finalproject.ApertureBehavior;
import edu.calvin.csw61.finalproject.Player;
import edu.calvin.csw61.finalproject.Door;

public class Unlock implements Command {
	
	Player myPlayer;
	Key myKey;
	private String myDirection;
	private String result;
	
	//Door myDoor;
	
	public Unlock(Player p, String direction) { //Door door
		myPlayer = p;
		myKey = new Key("key");
		myDirection = direction;
		result = "";
	}
	
	@Override
	public void execute() {
			myDirection.toLowerCase(); //just in case
				if(myDirection.equals("door")){ //if the user tries to unlock a generic door
		 			result = "Please enter the door direction.";
		 		} else if (myPlayer.hasItem(myKey.getName())){
		 			switch (myDirection){ //check the direction
		 			case "north":
		 				if(myPlayer.getRoom().isNorthDoor()){
		 				//	Door door = myPlayer.getRoom().getAperatures();
		 				}
		 				break;
		 			case "south":
		 				break;
		 			case "east":
						break;
		 			case "west":
						break;
					default: result = "Cannot unlock that.";
		 			}
		 		} else {
		 			result = "You don't have a key.";
		 		}
		 	/*	//Check if the door is locked...
		 if(myDoor.isUnlocked()) {  //Is it unlocked?
		 result += "The door is already unlocked";
		 } else {  //No, it's not. 
		 if(myPlayer.hasItem(myKey.getName())) {  //Does the Player have a Key?
		 myDoor.setUnlocked();
		 } else {
		 result += "You don't have a key.";
		 }
		 }*/
	}
	
	public String getResult() {
		return result;
	}
	
}

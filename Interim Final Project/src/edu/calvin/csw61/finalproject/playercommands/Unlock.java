package edu.calvin.csw61.finalproject.playercommands;

import edu.calvin.csw61.finalproject.Player;
import edu.calvin.csw61.finalproject.Door;

/**
 * Unlock allows a Player to unlock a Door.
 * (Implements the Command Interface).
 */
public class Unlock implements Command {
	
	//Handle to the Player
	private Player myPlayer;
	//Direction to check for Door
	private String myDirection;
	//Result of executing the Command
	private String result;
	
	/**
	 * Constructor for the Unlock class.
	 * @param: p, a handle to the Player.
	 * @param: direction, a String representing the direction of the Door to unlock.
	 */
	public Unlock(Player p, String direction) { //Door door
		myPlayer = p;
		myDirection = direction;
		result = "";
	}
	
	/**
	 * execute() executes the act of unlocking a Door in the game.
	 */
	@Override
	public void execute() {
		myDirection.toLowerCase(); //just in case
		if(myDirection.equals("door")){ //if the user tries to unlock a generic door
			result = "Please enter the door direction.";
		} else if (myPlayer.hasItem("key")){
			switch (myDirection){ //check the direction
			case "north":	//north door
				if(myPlayer.getRoom().isNorthDoor()){ //if there is a north door
					Door door = myPlayer.getRoom().getDoor("north"); //get it from the array
					if(!door.isLocked()){	//if it's unlocked
						result = "Door is already unlocked!"; //tell user
					} else {
						door.setUnlocked(); //else unlock the door
						result = "The door is now unlocked."; //tell user
						myPlayer.removeObject("key"); //and remove the key
					}
				} else {
					result = "You can't unlock that."; //if there is no door, tell user
				}
				break;
			case "south":
				if(myPlayer.getRoom().isSouthDoor()){
					Door door = myPlayer.getRoom().getDoor("south");
					if(!door.isLocked()){
						result = "Door is already unlocked!";
					} else {
						door.setUnlocked();
						result = "The door is now unlocked.";
						//remove key
						myPlayer.removeObject("key");
					} 
				} else {
					result = "You can't unlock that."; //if there is no door, tell user
				}
				break;
			case "east":
				if(myPlayer.getRoom().isEastDoor()){
					Door door = myPlayer.getRoom().getDoor("east");
					if(!door.isLocked()){
						result = "Door is already unlocked!";
					} else {
						door.setUnlocked();
						result = "The door is now unlocked.";
						//remove key
						myPlayer.removeObject("key");
					}
				} else {
					result = "You can't unlock that."; //if there is no door, tell user
				}
				break;
			case "west":
				if(myPlayer.getRoom().isWestDoor()){
					Door door = myPlayer.getRoom().getDoor("west");
					if(!door.isLocked()){
						result = "Door is already unlocked!";
					} else {
						//Check if we're in the Science Building
						if(myPlayer.getBuilding().equals("Science Building")) {
							//Check if the next Room is the last Room
							if(door.getNextRoom() == 12) {
								//Check if the Player has the special key
								if(myPlayer.hasItem("special key")) {
									//They do, so unlock it
									door.setUnlocked();
									result = "The door is now unlocked.";
									result += "It creaks open to a dark and eerie room...\n";
									myPlayer.removeObject("special key");
								} else {
									//Else, can't unlock the special door with an ordinary key
									result = "The key doesn't fit...looks like you need a different one.";
								}
								//Else, it's not the special Room, so unlock it
							} else {
								door.setUnlocked();
								result = "The door is now unlocked.";
								myPlayer.removeObject("key");
							}
							//Else, we're not in the Science Building
						} else {
							door.setUnlocked();
							result = "The door is now unlocked.";	
							myPlayer.removeObject("key");	
						}
						//Not a Door
					}
				} else {
					result = "You can't unlock that."; //if there is no door, tell user
				}
				break;
			default: result = "Cannot unlock that.";
			}
		} else {
			result = "You don't have a key.";
		}
	}
	
	/**
	 * Accessor for the result of executing the command.
	 */
	public String getResult() {
		return result;
	}	
}
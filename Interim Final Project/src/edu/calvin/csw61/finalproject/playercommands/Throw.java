package edu.calvin.csw61.finalproject.playercommands;

import edu.calvin.csw61.finalproject.ObjectInterface;
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
			result += "You threw " + myObject.getName();
			myPlayer.setHasNoWeapon();
		}  else {
			result += "The " + myObject.getName() + " is already in the room";
		}
	}
	
	public String getResult() {
		return result;
	}
}

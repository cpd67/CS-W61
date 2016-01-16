package edu.calvin.csw61.finalproject.playercommands;

import edu.calvin.csw61.finalproject.ObjectInterface;
import edu.calvin.csw61.finalproject.Player;

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
		myPlayer.removeObject(myObject.getName());
		result = "You dropped the " + myObject.getName();
	}
	
	public String getResult() {
		return result;
	}
}

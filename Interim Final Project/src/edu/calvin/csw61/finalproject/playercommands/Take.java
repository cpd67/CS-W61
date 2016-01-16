package edu.calvin.csw61.finalproject.playercommands;

import edu.calvin.csw61.finalproject.ObjectInterface;
import edu.calvin.csw61.finalproject.Player;

public class Take implements Command {
	Player myPlayer;
	ObjectInterface myObject;
	private String result;
	
	public Take(ObjectInterface ob, Player p) {
		myPlayer = p;
		myObject = ob;
		result = "";
	}
	
	//Add the Object
	public void execute() {
		//Is the Object in the Current Room that the Player is in?
		myPlayer.addObject(myObject.getName(), myObject);
		result = "You took " + myObject.getName();
	}
	
	public String getResult() {
		return result;
	}
}

package edu.calvin.csw61.finalproject.playercommands;

import edu.calvin.csw61.finalproject.ObjectInterface;
import edu.calvin.csw61.finalproject.Player;

public class Use implements Command {
	private String result;
	
	//We should probably check if the Object is a Food item.
	//If so, we should say, "You can't use -Food name here-, but you can eat it."
	//Takes the Object to use and a handle to the Player
	public Use(ObjectInterface ob, Player p) {
		result ="";
	}
	
	public void execute() {
		
	}
	
	public String getResult() {
		return result;
	}
}

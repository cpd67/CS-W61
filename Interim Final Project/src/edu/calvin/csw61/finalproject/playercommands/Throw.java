package edu.calvin.csw61.finalproject.playercommands;

import edu.calvin.csw61.finalproject.ObjectInterface;
import edu.calvin.csw61.finalproject.Player;

public class Throw implements Command {
	
	private String result;
	
	//Takes the Object to throw and a handle to the Player
	public Throw(ObjectInterface ob, Player p) {
		result = "";
	}
	
	public void execute() {
	}
	
	public String getResult() {
		return result;
	}
}

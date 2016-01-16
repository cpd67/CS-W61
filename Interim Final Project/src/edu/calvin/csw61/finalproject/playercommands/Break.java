package edu.calvin.csw61.finalproject.playercommands;

import edu.calvin.csw61.finalproject.ApertureBehavior;
import edu.calvin.csw61.finalproject.Player;

public class Break implements Command {
	private String result; //the result string

	//Takes an Aperture to break (if possible) and a handle to the Player
	public Break(ApertureBehavior ap, Player p) { 
		result = "";
	}
	
	public void execute() {
		
	}
	
	public String getResult(){
		return result;
	}
}

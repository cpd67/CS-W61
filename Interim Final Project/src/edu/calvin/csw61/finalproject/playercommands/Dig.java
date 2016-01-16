package edu.calvin.csw61.finalproject.playercommands;

import edu.calvin.csw61.finalproject.Player;

public class Dig implements Command {
	private String result; //the result string
	//...?
	public Dig(Player p) {
		result = "";
	}
	
	public void execute() {
		
	}
	
	public String getResult() {
		return result;
	}
}

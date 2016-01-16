package edu.calvin.csw61.finalproject.playercommands;

import edu.calvin.csw61.finalproject.ObjectInterface;
import edu.calvin.csw61.finalproject.Player;

public class Give implements Command {
	
	private String result;
	//Takes an NPC (Acts as an ObjectInterface), a Player, and an Object
	public Give(ObjectInterface npc, ObjectInterface ob, Player p) {
		//We can check if the Player is trying to give an Object to something
		//other than an NPC by passing an ObjectInterface as the NPC parameter
		result = "";
	}
	
	public void execute() {
		
	}
	
	public String getResult() {
		return result;
	}
}

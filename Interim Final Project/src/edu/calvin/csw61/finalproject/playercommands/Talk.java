package edu.calvin.csw61.finalproject.playercommands;

import edu.calvin.csw61.finalproject.NPC;
import edu.calvin.csw61.finalproject.ObjectInterface;
import edu.calvin.csw61.finalproject.Player;

public class Talk implements Command {
	
	private Player myPlayer;
	private NPC myConversationPartner;
	private String result;
	
	//In case we want to add Player lines, the Talk() constructor takes a handle
	//to the Player
	public Talk(NPC myObject, Player p) {
		myConversationPartner = myObject;
		result = "";
	}
	
	public void execute() {
		//Spit out the lines for the NPC
	}
	
	public String getResult() {
		return result;
	}
	
}

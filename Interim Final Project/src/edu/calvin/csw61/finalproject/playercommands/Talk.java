package edu.calvin.csw61.finalproject.playercommands;

import edu.calvin.csw61.finalproject.NPC;
import edu.calvin.csw61.finalproject.Player;
import edu.calvin.csw61.finalproject.Quest;

public class Talk implements Command {
	
	private Player myPlayer;
	private NPC myConversationPartner;
	private String result;
	
	//In case we want to add Player lines, the Talk() constructor takes a handle
	//to the Player
	public Talk(NPC npc, Player p) {
		myConversationPartner = npc;
		myPlayer = p;
		result = "";
	}
	
	public void execute() {
		//Spit out the lines for the NPC
		
		//Give the Player a Quest at the end
		myConversationPartner.act(myPlayer);
		result = "Talk sucessful!"; //Temporary
	}
	
	public String getResult() {
		return result;
	}
	
}

package edu.calvin.csw61.finalproject.playercommands;

import edu.calvin.csw61.finalproject.NPC;
import edu.calvin.csw61.finalproject.ObjectInterface;
import edu.calvin.csw61.finalproject.Player;

public class Talk implements Command {
	
	private Player myPlayer;
	private ObjectInterface myConversationPartner;
	private String result;
	
	public Talk(ObjectInterface myObject) {
		myConversationPartner = myObject;
		result = "";
	}
	
	public void execute() {
		if(myConversationPartner instanceof NPC) {
			//Spit out the lines for the NPC
		} else {  //Can't talk to anyone else.
			result = "There's no one to talk to.";
		}
	}
	
	public String getResult() {
		return result;
	}
	
}

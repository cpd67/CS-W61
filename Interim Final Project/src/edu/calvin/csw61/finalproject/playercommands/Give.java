package edu.calvin.csw61.finalproject.playercommands;

import edu.calvin.csw61.finalproject.ObjectInterface;
import edu.calvin.csw61.finalproject.NPC;
import edu.calvin.csw61.finalproject.Player;

public class Give implements Command {
	
	private String result;
	private NPC myNPC;
	private ObjectInterface myGivenOb;
	private Player myPlayer;

	//Takes an NPC (Acts as an ObjectInterface), a Player, and an Object
	public Give(NPC npc, ObjectInterface ob, Player p) {
		this.myNPC = npc;
		this.myGivenOb = ob;
		this.myPlayer = p;
		result = "";
	}
	
	public void execute() {
		//Check if the Object is the one that the NPC needs.
		if(myNPC.recieve(myGivenOb)) {  //If the NPC receives the Object...
			myPlayer.removeObject(myGivenOb.getName().toLowerCase());
			myPlayer.questComplete(); 
			result += "You gave " + myNPC.getName() + " the " + myGivenOb.getName() + "\n";
		} else {  //Not the one he/she needs...
			result += myNPC.getName() + " doesn't need that!";
		}
	}
	
	public String getResult() {
		return result;
	}
}

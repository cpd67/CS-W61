package edu.calvin.csw61.finalproject.playercommands;

import edu.calvin.csw61.finalproject.NPC;
import edu.calvin.csw61.finalproject.Player;

public class Give implements Command {
	
	private String result;
	private NPC myNPC;
	private Player myPlayer;
	private String myObj;

	//Takes an NPC (Acts as an ObjectInterface), a Player, and an Object
	public Give(NPC npc, String object, Player p) {
		this.myNPC = npc;
		this.myPlayer = p;
		this.myObj = object.toLowerCase();
		result = "";
	}
	
	public void execute() {
		//Check if the Object is the one that the NPC needs.
		if(myNPC.recieve(myPlayer) && myObj.equals(myNPC.getQuest().getItem().toLowerCase())) {  //If the NPC receives the Object from the Player...
			result = "You gave " + myNPC.getName() + " the " + myObj + "\n";
			myNPC.setHasNoQuest(); //No more Quest!
			myPlayer.removeObject(myObj);
			myPlayer.setQuestState(myPlayer.getNoQuestState()); //Not on a Quest anymore!
			result += "Quest complete!";
			myPlayer.addMaxHealth(); //Add 20 to the max health
		} else {  //Not the one he/she needs...
			result = myNPC.getName() + " doesn't need that!";
		}
	}
	
	public String getResult() {
		return result;
	}
}

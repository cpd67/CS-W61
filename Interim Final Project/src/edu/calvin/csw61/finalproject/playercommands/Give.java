package edu.calvin.csw61.finalproject.playercommands;

import edu.calvin.csw61.finalproject.NPC;
import edu.calvin.csw61.finalproject.Player;

/**
 * Give allows a Player to give something to an NPC.
 * (Implements the Command Interface).
 */
public class Give implements Command {
	
	private String result;  //result of executing the Command
	private NPC myNPC;  //Receiver of the ObjectInterface 
	private Player myPlayer;  //Handle to the Player
	private String myObj;  //Object to give

	/**
	 * Constructor for the Give class.
	 * @param: npc, the NPC that is receiving the ObjectInterface. 
	 * @param: object, a String representing the name of the ObjectInterface to give.
	 * @param: p, a handle to the Player.
	 */
	public Give(NPC npc, String object, Player p) {
		this.myNPC = npc;
		this.myPlayer = p;
		this.myObj = object.toLowerCase();
		this.result = "";
	}
	
	/**
	 * execute() executes the act of giving something to an NPC in the game.
	 */
	public void execute() {
		//Check if the Object is the one that the NPC needs.
		if(myNPC.recieve(myPlayer) && myObj.equals(myNPC.getQuest().getItem().toLowerCase())) {
			result = "You gave " + myNPC.getName() + " the " + myObj + "\n";
			//The NPC no longer has a Quest
			//(You usually only give something to an NPC 
			//if he/she needs it)
			myNPC.setHasNoQuest(); 
			//Take away the ObjectInterface from the Player
			myPlayer.removeObject(myObj);
			//Not on a Quest anymore!
			myPlayer.setQuestState(myPlayer.getNoQuestState()); 
			result += "Quest complete!";
			myPlayer.addMaxHealth(); //Add 20 to the max health
		} else {  
			//Not the one he/she needs...
			result = myNPC.getName() + " doesn't need that!";
		}
	}

	/**
	 * Accessor for the result of executing the command.
	 */
	public String getResult() {
		return result;
	}
}
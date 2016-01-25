package edu.calvin.csw61.finalproject.playercommands;

import edu.calvin.csw61.finalproject.ObjectInterface;
import edu.calvin.csw61.finalproject.WeaponAdapter;
import edu.calvin.csw61.finalproject.Player;

/**
 * Throw allows a Player to throw something.
 * (Implements the Command Interface).
 */
public class Throw implements Command {
	
	private Player myPlayer;  //Handle to the Player
	private ObjectInterface myObject;  //ObjectInterface to throw
	private String result; //Result of executing the Command
	
	/**
	 * Constructor for the Throw class.
	 * @param: ob, the ObjectInterface to throw.
	 * @param: p, a handle to the Player.
	 */
	public Throw(ObjectInterface ob, Player p) {
		this.myPlayer = p;
		this.myObject = ob;
		this.result = "";
	}
	
	/**
	 * execute() executes the act of throwing something in the game.
	 */
	public void execute() {
		//Can we throw the ObjectInterface? 
		//(Applies to WeaponAdapters only)
		if(myPlayer.getRoom().addObject(myObject)) {
			//Is the ObjectInterface a QuestItem?
			if(myObject.getName().toLowerCase().equals(myPlayer.getActualQuest().getItem())) {
				//Reset the Quest state back to OnQuest
				result = "You threw the needed Quest item!";
				myPlayer.setQuestState(myPlayer.getOnQuestState());
				result += "You threw " + myObject.getName();
				//Take away the ObjectInterface
				myPlayer.removeObject(myObject.getName().toLowerCase());
			} else {
				//Is the Object a WeaponAdapter? (That's NOT already in the room)
				if(!(myObject instanceof WeaponAdapter)) {
					//No, it's a regular ObjectInterface then
					result = "You threw " + myObject.getName();
					myPlayer.removeObject(myObject.getName().toLowerCase());
				} else {
					//The Player has lost his/her Weapon
					result = "You threw " + myObject.getName();
					myPlayer.setHasNoWeapon();
				}
			}
		}  else {
			//The ObjectInterface is already in the Room
			result += "The " + myObject.getName() + " is already in the room";
		}
	}
	
	/**
	 * Accessor for the result of executing the command.
	 */
	public String getResult() {
		return result;
	}
}
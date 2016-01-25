package edu.calvin.csw61.finalproject.playercommands;

import edu.calvin.csw61.finalproject.ObjectInterface;
import edu.calvin.csw61.finalproject.Player;
import edu.calvin.csw61.finalproject.WeaponAdapter;

/**
 * Drop allows a Player to drop something.
 * (Implements the Command Interface).
 */
public class Drop implements Command {
	private String result; //Result of executing the Command
	private Player myPlayer;  //Handle to the Player
	private ObjectInterface myObject; //The ObjectInterface to drop

	/**
	 * Constructor for the Drop class.s
	 * @param: ob, the ObjectInterface to drop.
	 * @param: p, a handle to the Player.
	 */
	public Drop(ObjectInterface ob, Player p) {
		this.myObject = ob;
		this.myPlayer = p;
		this.result = "";
	}
	
	/**
	 * execute() executes the act of dropping something in the game.
	 */
	public void execute() {
		//Check if it's a WeaponAdapter
		//(You CAN put Weapons in your backpack, since they are 
		//ObjectInterfaces)
		if(myObject instanceof WeaponAdapter) {
			//If the weapon isn't already in the Room...
			if(myPlayer.getRoom().addObject(myObject)) { 
				result = "You dropped your " + myObject.getName();
				myPlayer.setHasNoWeapon();  //Player no longer has the Weapon.
			} else { //Else, the Weapon is already in the Room...
				result = myObject.getName() + " is already in the room!";
			}

		} else { //Else, it's not a Weapon.
			//Take the Object out of the backpack.
			myPlayer.removeObject(myObject.getName().toLowerCase());
			//If it's a QuestItem...
			if(myObject.getName().toLowerCase().equals(myPlayer.getActualQuest().getItem())) {
				//Reset the state back to OnQuestState
				result = "You lost the missing item!\n";
				myPlayer.setQuestState(myPlayer.getOnQuestState());
				myPlayer.getRoom().addObject(myObject);  //Add it to the Room.
				result += "You dropped " + myObject.getName();
			} else { //Else, it's a regular ObjectInterfaces
				myPlayer.getRoom().addObject(myObject);  //Add it to the Room.
				result = "You dropped " + myObject.getName();
			}
		}
	}
	
	/**
	 * Accessor for the result of executing the command.
	 */
	public String getResult() {
		return result;
	}
}
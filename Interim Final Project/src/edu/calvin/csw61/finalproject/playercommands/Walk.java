package edu.calvin.csw61.finalproject.playercommands;

import edu.calvin.csw61.finalproject.Door;
import edu.calvin.csw61.finalproject.Player;
import edu.calvin.csw61.finalproject.WallBehavior;

/**
 * Walk allows a Player to walk somewhere.
 * (Implements the Command Interface).
 */
public class Walk implements Command{
	
	//Direction to walk in
	private String myDirection;
	//Handle to the Player
	private Player myPlayer;
	//Result of executing the Command
	private String result;
	
	/**
	 * Constructor for the Walk class.
	 * @param: p, a handle to the Player.
	 * @param: direction, a String representing the direction to walk in.
	 */
	public Walk(String direction, Player player){
		myDirection = direction.toLowerCase();  //Lower case it 
		myPlayer = player;
		result = "";
	}

	/**
	 * execute() executes the act of walking in the game.
	 */
	@Override
	public void execute() {
		//Check if we are outside...
		for(int i = 0; i < myPlayer.getRoom().getAperatures().size(); i++) {
			//If the AperatureBehavior is a Door...
			if(myPlayer.getRoom().getAperatures().get(i).getName().equals("door")) {  
				//Typecast to a Door
				Door checker = (Door)myPlayer.getRoom().getAperatures().get(i);  
				//Check if the Door is locked...
				if(!checker.isLocked()) {
					//Unlocked, so check if the Door's direction is equal to the passed direction
					if(checker.getDir().equals(myDirection.toLowerCase())) {
						//Go through the Door.
						checker.goThrough(myPlayer); 
						//put the room description in result
						result = myPlayer.getRoom().getDescriptor(); 
						//Show ObjectInterfaces and People
						result += myPlayer.getRoom().showObjects();
						result += myPlayer.getRoom().showPeople();
						//Only go through the Door once.
						return; 
					}
				} else {
					result += "LOCKED.";
					return;
				}
			} else {  //Check for a wall...
				//Typecast to a Wall
				WallBehavior checker2 = (WallBehavior)myPlayer.getRoom().getAperatures().get(i);  
				//If the Wall's direction equals the Player's Direction...
				if(checker2.getDir().equals(myDirection.toLowerCase())) {  
					result += "You can't go that way";
					return;
				}
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
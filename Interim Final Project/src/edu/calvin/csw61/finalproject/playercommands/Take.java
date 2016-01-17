package edu.calvin.csw61.finalproject.playercommands;

import edu.calvin.csw61.finalproject.ObjectInterface;
import edu.calvin.csw61.finalproject.Player;

public class Take implements Command {
	Player myPlayer;
	ObjectInterface myObject;
	private String result;
	
	public Take(ObjectInterface ob, Player p) {
		myPlayer = p;
		myObject = ob;
		result = "";
	}
	
	//Add the Object
	public void execute() {
		//Is the Object in the Current Room that the Player is in?
		if(myPlayer.getRoom().getObjects()[0].getName() == myObject.getName()) { //First Object
			if(myPlayer.hasItem(myPlayer.getRoom().getObjects()[0].getName())) { //BUT, does he/she already have it?
				result = "You can only have one " + myObject.getName().toLowerCase(); //Yes.
			} else { //No. Add it to the Backpack.
				myPlayer.addObject(myObject.getName(), myObject);
				myPlayer.getRoom().removeObject(0);
				result = "You took " + myObject.getName().toLowerCase();
			}
		} else if(myPlayer.getRoom().getObjects()[1].getName() == myObject.getName()) { //Second Object
			if(myPlayer.hasItem(myPlayer.getRoom().getObjects()[1].getName())) {
				result = "You can only have one " + myObject.getName().toLowerCase();
			} else {
				myPlayer.addObject(myObject.getName(), myObject);
				myPlayer.getRoom().removeObject(1);
				result = "You took " + myObject.getName();
			}
		} else {  //The Object is not in the current Room.
			result = myObject.getName().toLowerCase() + " is not in this room.";
		}

	}
	
	public String getResult() {
		return result;
	}
}

package edu.calvin.csw61.finalproject.playercommands;

import edu.calvin.csw61.finalproject.Door;
import edu.calvin.csw61.finalproject.Player;
import edu.calvin.csw61.finalproject.TestClass;
import edu.calvin.csw61.finalproject.WallBehavior;

public class Walk implements Command{
	String myDirection;
	Player myPlayer;
	private String result;
	
	public Walk(String direction, Player player){
		myDirection = direction.toLowerCase();  //Lower case it 
		myPlayer = player;
		result = "";
	}

	@Override
	public void execute() {
		//Check if we are outside...
		for(int i = 0; i < myPlayer.getRoom().getAperatures().size(); i++) {
			if(myPlayer.getRoom().getAperatures().get(i).getName().equals("door")) {  //If the Aperature is a Door...
				Door checker = (Door)myPlayer.getRoom().getAperatures().get(i);  //Typecast to a Door
				//Check if the Door is locked...
				if(checker.isUnlocked()) {
					//Unlocked, so check if the Door's direction is equal to the Player's direction.
					if(checker.getDir().equals(myPlayer.getDirection())) {  //Check the Direction
						System.out.println("You walked through the door.");
						checker.goThrough(myPlayer); //Go through the Door.
						result = "You walked " + myDirection + "\n";
						result += myPlayer.getRoom().getDescriptor();
						break; //Only go through the Door once.
					}
				} else {
					result += "The door is locked. Do you have a key?";
					break;
				}
			} else {  //Check for a wall...
				WallBehavior checker2 = (WallBehavior)myPlayer.getRoom().getAperatures().get(i);  //Typecast to a Wall
				if(checker2.getDir().equals(myPlayer.getDirection())) {  //If the Wall's direction equals the Player's Direction...
					result += "You can't go that way";
					break;
				}
			}
		}
	}
	
	public String getResult() {
		return result;
	}
}

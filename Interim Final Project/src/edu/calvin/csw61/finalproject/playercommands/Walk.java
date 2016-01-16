package edu.calvin.csw61.finalproject.playercommands;

import edu.calvin.csw61.finalproject.Door;
import edu.calvin.csw61.finalproject.Player;
import edu.calvin.csw61.finalproject.TestClass;

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
				//Unlocked, so check if the Door's direction is equal to the Player's direction.
				if(checker.getDir().equals(myPlayer.getDirection())) {  //Check the Direction
					System.out.println("You walked through the door.");
					checker.goThrough(myPlayer); //Go through the Door.
					break; //Only go through the Door once.
				}
			} else {
				//It's a Wall, can't walk there.
				System.out.println("A wall blocks your path.");
				break;  //For now...
			}
		}
		
		//get direction
		//if the player's direction matches the door's direction, then go through the door
		//(go to next spot in two dimensional array of rooms) 
		//Is the door locked? If not, go through it
		//If the door is locked, then return String: "The door is locked". Driver then prints it out.
		result = "You walked " + myDirection + " (ALMOST IMPLEMENTED)";
	}
	
	public String getResult() {
		return result;
	}
}

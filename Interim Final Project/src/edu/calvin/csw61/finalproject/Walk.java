package edu.calvin.csw61.finalproject;

public class Walk implements Command{
	String myDirection;
	Player myPlayer;
	
	public Walk(String direction, Player player){
		myDirection = direction; 
		myPlayer = player;
	}

	@Override
	public void execute() {
		//get direction
		//if the player's direction matches the door's direction, then go through the door
		//(go to next spot in two dimensional array of rooms) 
		//Is the door locked? If not, go through it
		//If the door is locked, then return String: "The door is locked". Driver then prints it out.
		System.out.println("You walked (NOT IMPLEMENTED))");
	}
}

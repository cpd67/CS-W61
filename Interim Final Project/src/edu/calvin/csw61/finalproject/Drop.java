package edu.calvin.csw61.finalproject;

public class Drop implements Command {
	
	Player myPlayer;
	ObjectInterface myObject;

	//Takes the Object to drop and a handle to the Player.
	//Check if the Object is in the backpack...
	public Drop(ObjectInterface ob, Player p) {
		myObject = ob;
		myPlayer = p;
	}
	
	public void execute() {
		myPlayer.removeObject(myObject.getName());
		System.out.println("You dropped " + myObject.getName());
	}
}

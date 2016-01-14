package edu.calvin.csw61.finalproject;

public class Take implements Command {
	Player myPlayer;
	ObjectInterface myObject;
	
	public Take(ObjectInterface ob, Player p) {
		myPlayer = p;
		myObject = ob;
	}
	
	//Add the Object
	public void execute() {
		myPlayer.addObject(myObject.getName(), myObject);
		System.out.println("You took " + myObject.getName());
	}
}

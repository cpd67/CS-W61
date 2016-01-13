package edu.calvin.csw61.finalproject;

public class Take implements CommandBehavior {
	Player myPlayer;
	ObjectInterface myObject;
	
	public Take(Player p, ObjectInterface ob) {
		myPlayer = p;
		myObject = ob;
	}
	
	//Add the Object
	public void execute() {
		if(myPlayer.hasItem(myObject.getName())) {
			System.out.println("You can only have one " + myObject.getName());
		} else {
			myPlayer.addObject(myObject.getName(), myObject);
			System.out.println("You took " + myObject.getName());	
		}
	}
}

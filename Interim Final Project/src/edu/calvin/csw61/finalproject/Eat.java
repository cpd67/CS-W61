package edu.calvin.csw61.finalproject;

public class Eat implements CommandBehavior {
	
	ObjectInterface myObject;  //Handle to Object that we should eat 
	Player myPlayer; //Handle to Player
	
	//Creates an Eat object with the Object and Player
	public Eat(ObjectInterface ob, Player p) {
		this.myPlayer = p;
		myObject = ob;
	}
	
	//Execute the action of eating
	@Override
	public void execute() {
		if(myPlayer.hasItem(myObject.getName())) {  //If we have the item...
			System.out.println("You ate " + myObject.getName());  //Eat it
			myPlayer.removeObject(myObject.getName());
		} else { //Else, we can't eat it
			System.out.println("You don't have that item.");
		}
	}
}

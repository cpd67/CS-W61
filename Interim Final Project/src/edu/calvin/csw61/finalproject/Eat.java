package edu.calvin.csw61.finalproject;

public class Eat implements Command {
	
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
		//Check if the object is a Food item
		if(myObject instanceof Food) {
			Food checker = (Food)myObject;  //If so, type cast to a Food object
			if(checker.hasPoison()) {  //If the food is poisoned...  
				System.out.println("You eat " + checker.getName());  //Player has died >:)
				System.out.println("You suddenly feel ill...");
				System.out.println(checker.getName() + " was poisoned!");
				System.out.println("You have died.");
				//Figure out a way to end the game after that...
				//For now...
				System.exit(1);
			} else { //Not poisoned, can eat it
				myPlayer.removeObject(myObject.getName());
				System.out.println("You ate " + myObject.getName());
			}
		} else if(myObject instanceof NPC) { //Not a Food item, check if it's either a Monster or NPC
			NPC holder = (NPC)myObject;  //If so, you can't eat either
			System.out.println("You can't eat " + holder.getName());
		} else if(myObject instanceof Monster) {
			Monster holder = (Monster)myObject;
			System.out.println("You can't eat " + holder.getName());
		} else if(myObject instanceof Key) {  //If it's a Key, kill the Player (can't go anywhere if you eat the only Key in the room)
			//Have to kill the Player here too...
			Key holder = (Key)myObject;
			System.out.println("You forciby shove the " + holder.getName() + "down your throat, killing yourself.");
			System.out.println("You have died.");
			//End the game...
			//For now...
			System.exit(1);
		} else if(myObject instanceof Treasure) {  //If Treasure, can't eat it either
			Treasure holder = (Treasure)myObject;
			System.out.println("You can't eat " + holder.getName());
		}
	}
}

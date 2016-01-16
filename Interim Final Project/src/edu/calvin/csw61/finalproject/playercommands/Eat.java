package edu.calvin.csw61.finalproject.playercommands;

import edu.calvin.csw61.finalproject.Food;
import edu.calvin.csw61.finalproject.Key;
import edu.calvin.csw61.finalproject.Monster;
import edu.calvin.csw61.finalproject.NPC;
import edu.calvin.csw61.finalproject.ObjectInterface;
import edu.calvin.csw61.finalproject.Player;
import edu.calvin.csw61.finalproject.Treasure;

public class Eat implements Command {
	private String result;
	ObjectInterface myObject;  //Handle to Object that we should eat 
	Player myPlayer; //Handle to Player
	
	//Creates an Eat object with the Object and Player
	public Eat(ObjectInterface ob, Player p) {
		this.myPlayer = p;
		myObject = ob;
		result = "";
	}
	
	//Execute the action of eating
	@Override
	public void execute() {
		//Check if the object is a Food item
		if(myObject instanceof Food) {
			Food checker = (Food)myObject;  //If so, type cast to a Food object
			if(checker.hasPoison()) {  //If the food is poisoned...  
				result = "You eat the " + checker.getName() + "\n";  //Player has died >:)
				result += "You suddenly feel ill...\n";
				result += checker.getName() + " was poisoned!\n";
				result += "You have died.\n";
				//Figure out a way to end the game after that...
				//For now...
				System.exit(1);
			} else { //Not poisoned, can eat it
				myPlayer.removeObject(myObject.getName());
				result = "You ate the " + myObject.getName() + "\n";
				if(myPlayer.getHealth() == 100) {  //Full health = No health gain
					result += "Already at full health.\n"; 
				} else {  //Give the Player health if he/she isn't at full health yet
					myPlayer.addHealth();
					result += "You gained 10 hit points\n";
					result +="You have " + myPlayer.getHealth() + " hit points\n";
					if(myPlayer.getHealth() == 100) {  //Do we have 100 hit points?
						result += "You are at full health\n";
					}
				}
				
			}
		} else if(myObject instanceof NPC) { //Not a Food item, check if it's either a Monster or NPC
			NPC holder = (NPC)myObject;  //If so, you can't eat either
			result = "You can't eat " + holder.getName() + "\n";
		} else if(myObject instanceof Monster) {
			Monster holder = (Monster)myObject;
			result = "You can't eat " + holder.getName() + "\n";
		} else if(myObject instanceof Key) {  //If it's a Key, kill the Player (can't go anywhere if you eat the only Key in the room)
			//Have to kill the Player here too...
			Key holder = (Key)myObject;
			result = "You forcibly shove the " + holder.getName() + " down your throat, killing yourself.\n";
			result += "You have died.\n";
			//End the game...
			//For now...
			System.exit(1);
		} else if(myObject instanceof Treasure) {  //If Treasure, can't eat it either
			Treasure holder = (Treasure)myObject;
			result += "You can't eat a " + holder.getName() + "\n";
		}
	}
	
	public String getResult() {
		return result;
	}
}

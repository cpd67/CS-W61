package edu.calvin.csw61.finalproject.playercommands;

import edu.calvin.csw61.finalproject.Key;
import edu.calvin.csw61.finalproject.Monster;
import edu.calvin.csw61.finalproject.NPC;
import edu.calvin.csw61.finalproject.ObjectInterface;
import edu.calvin.csw61.finalproject.Player;
import edu.calvin.csw61.finalproject.Treasure;
import edu.calvin.csw61.fruit.*;
import edu.calvin.csw61.food.*;
import edu.calvin.csw61.weapons.*;
import edu.calvin.csw61.finalproject.WeaponAdapter;

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
		if(myObject instanceof Food) { //Food object
			result += "You ate " + myObject.getName() + "\n";
			Food check = (Food)myObject; 
			int health = check.getHealth(); //Get the health from it
			result += "You gained " + health + " hit points\n";
			myPlayer.addHealth(health);  //Add it to the Player
			result += "You now have " + myPlayer.getHealth() + " hit points\n";
			if(myPlayer.getHealth() == 100) {  //At full health?
				result += "You are at full health";
			}
			//Remove the Food item
			if(myPlayer.hasItem(check.getName())) {  //If in the backpack...
				myPlayer.removeObject(check.getName());
			} else {  //Must be in Room then...
				myPlayer.getRoom().removeObject(check.getName());
				myPlayer.getRoom().showObjects();
			}
			//Fruit object
		} else if(myObject instanceof Fruit) {
			Fruit check = (Fruit)myObject;
			int health = check.getHealth();  //Get health
			result += "You gained " + health + " hit points\n";
			myPlayer.addHealth(health); //Add it
			result += "You now have " + myPlayer.getHealth() + " hit points\n";
			if(myPlayer.getHealth() == 100) {  //Full health?
				result += "You are at full health";
			}
			//Remove the Food item
			if(myPlayer.hasItem(check.getName())) {  //If in the backpack...
				myPlayer.removeObject(check.getName());
			} else {  //Must be in Room then...
				myPlayer.getRoom().removeObject(check.getName());
				myPlayer.getRoom().showObjects();
			}
		} else if(myObject instanceof Key) {  //If it's a Key, kill the Player (can't go anywhere if you eat the only Key in the room)
			//Have to kill the Player here too...
			Key holder = (Key)myObject;
			result = "You forcibly shove the " + holder.getName().toLowerCase() + " down your throat, killing yourself.\n";
			result += "You have died.\n";
			//End the game...
		} else if(myObject instanceof Treasure) {  //If Treasure, can't eat it either
			Treasure holder = (Treasure)myObject;
			result += "You can't eat a " + holder.getName() + "\n";
		}
	}
	
	public String getResult() {
		return result;
	}
}

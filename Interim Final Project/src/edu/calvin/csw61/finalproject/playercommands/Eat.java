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
			if(myObject instanceof Cupcake) {  //Add the health, check if over 100, 
				result += "You ate " + myObject.getName() + "\n";
				Cupcake check = (Cupcake)myObject; 
				int health = check.getHealth();
				result += "You gained " + health + "\n";
				if(health + myPlayer.getHealth() > 100) {
					myPlayer.addHealth(health);  //Add the health...
					result += "You are at full health" + "\n";
				} else {
					myPlayer.addHealth(health);
				}
				result += "You now have " + myPlayer.getHealth();
				//Remove the Food item
				if(myPlayer.hasItem(check.getName())) {  //If in the backpack...
					myPlayer.removeObject(check.getName());
				} else {  //Must be in Room then...
					myPlayer.getRoom().removeObject(check.getName());
					myPlayer.getRoom().showObjects();
				} 
				//Pizza
			} else if(myObject instanceof Pizza) {
				Pizza check = (Pizza)myObject;
				int health = check.getHealth();
				result += "You gained " + health + "\n";
				if(health + myPlayer.getHealth() > 100) {
					myPlayer.addHealth(health);  //Add the health...
					result += "You are at full health" + "\n";
				} else {
					myPlayer.addHealth(health);
				}
				result += "You now have " + myPlayer.getHealth();
				//Remove the Food item
				if(myPlayer.hasItem(check.getName())) {  //If in the backpack...
					myPlayer.removeObject(check.getName());
				} else {  //Must be in Room then...
					myPlayer.getRoom().removeObject(check.getName());
					myPlayer.getRoom().showObjects();
				}
				//Spinach
			} else if(myObject instanceof Spinach) {
				Spinach check = (Spinach)myObject;
				int health = check.getHealth();
				result += "You gained " + health + "\n";
				if(health + myPlayer.getHealth() > 100) {
					myPlayer.addHealth(health);  //Add the health...
					result += "You are at full health" + "\n";
				} else {
					myPlayer.addHealth(health);
				}
				result += "You now have " + myPlayer.getHealth();
				//Remove the Food item
				if(myPlayer.hasItem(check.getName())) {  //If in the backpack...
					myPlayer.removeObject(check.getName());
				} else {  //Must be in Room then...
					myPlayer.getRoom().removeObject(check.getName());
					myPlayer.getRoom().showObjects();
				}
			} 
			//Fruit object
		} else if(myObject instanceof Fruit) {
			//Fruit isn't poisoned.
			if(myObject instanceof Apple) {
				Apple check = (Apple)myObject;
				int health = check.getHealth();
				result += "You gained " + health + "\n";
				if(health + myPlayer.getHealth() > 100) {
					myPlayer.addHealth(health);  //Add the health...
					result += "You are at full health" + "\n";
				} else {
					myPlayer.addHealth(health);
				}
				result += "You now have " + myPlayer.getHealth();
				//Remove the Food item
				if(myPlayer.hasItem(check.getName())) {  //If in the backpack...
					myPlayer.removeObject(check.getName());
				} else {  //Must be in Room then...
					myPlayer.getRoom().removeObject(check.getName());
					myPlayer.getRoom().showObjects();
				}
			} else if(myObject instanceof Orange) {
				Orange check = (Orange)myObject;
				int health = check.getHealth();
				result += "You gained " + health + "\n";
				if(health + myPlayer.getHealth() > 100) {
					myPlayer.addHealth(health);  //Add the health...
					result += "You are at full health" + "\n";
				} else {
					myPlayer.addHealth(health);
				}
				result += "You now have " + myPlayer.getHealth();
				//Remove the Food item
				if(myPlayer.hasItem(check.getName())) {  //If in the backpack...
					myPlayer.removeObject(check.getName());
				} else {  //Must be in Room then...
					myPlayer.getRoom().removeObject(check.getName());
					myPlayer.getRoom().showObjects();
				}
			} else if(myObject instanceof Blueberry) {
				Blueberry check = (Blueberry)myObject;
				int health = check.getHealth();
				result += "You gained " + health + "\n";
				if(health + myPlayer.getHealth() > 100) {
					myPlayer.addHealth(health);  //Add the health...
					result += "You are at full health" + "\n";
				} else {
					myPlayer.addHealth(health);
				}
				result += "You now have " + myPlayer.getHealth();
				//Remove the Food item
				if(myPlayer.hasItem(check.getName())) {  //If in the backpack...
					myPlayer.removeObject(check.getName());
				} else {  //Must be in Room then...
					myPlayer.getRoom().removeObject(check.getName());
					myPlayer.getRoom().showObjects();
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
			result = "You forcibly shove the " + holder.getName().toLowerCase() + " down your throat, killing yourself.\n";
			result += "You have died.\n";
			//End the game...
			//For now...
			System.exit(1);
		} else if(myObject instanceof Treasure) {  //If Treasure, can't eat it either
			Treasure holder = (Treasure)myObject;
			result += "You can't eat a " + holder.getName() + "\n";
		} else if(myObject instanceof Weapon) {
			//Can't eat a Weapon...
		}
	}
	
	public String getResult() {
		return result;
	}
}

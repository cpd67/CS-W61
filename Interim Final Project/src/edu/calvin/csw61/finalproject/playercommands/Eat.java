package edu.calvin.csw61.finalproject.playercommands;

//Imports
import edu.calvin.csw61.finalproject.Key;
import edu.calvin.csw61.finalproject.QuestItem;
import edu.calvin.csw61.finalproject.ObjectInterface;
import edu.calvin.csw61.finalproject.Player;
import edu.calvin.csw61.finalproject.Treasure;
import edu.calvin.csw61.fruit.Fruit;
import edu.calvin.csw61.food.Food;

/**
 * Eat allows a Player to eat an edible ObjectInterface.
 * (Implements the Command pattern).
 */
public class Eat implements Command {
	private String result;  //Result of executing the command
	ObjectInterface myObject;  //Handle to Object that we should eat 
	Player myPlayer; //Handle to Player
	
	/**
	 * Constructor for the Eat command object.
	 * @param ob, the passed ObjectInterface to eat.
	 * @param p, a handle to the Player.
	 */
	public Eat(ObjectInterface ob, Player p) {
		this.myPlayer = p;
		this.myObject = ob;
		this.result = "";
	}
	
	/**
	 * execute() executes the act of eating something in the game.
	 */
	@Override
	public void execute() {
		//Check if the object is a Food object
		if(myObject instanceof Food) { 
			result = "You ate " + myObject.getName() + "\n";
			//Typecast it to a Food object
			Food check = (Food)myObject; 
			int health = check.getHealth(); //Get the health from it
			myPlayer.addHealth(health);  //Add it to the Player
			result += "You now have " + myPlayer.getHealth() + " hit points\n";
			//Remove the Food object
			if(myPlayer.hasItem(check.getName())) {  //If in the backpack...
				myPlayer.removeObject(check.getName());
			} else {  //Must be in Room then...
				myPlayer.getRoom().removeObject(check.getName());
			}
			//Fruit object
		} else if(myObject instanceof Fruit) {
			//Typecast
			Fruit check = (Fruit)myObject;
			int health = check.getHealth();  //Get health
			myPlayer.addHealth(health); //Add it
			result = "You now have " + myPlayer.getHealth() + " hit points\n";
			//Remove the Fruit object
			if(myPlayer.hasItem(check.getName())) {  //If in the backpack...
				myPlayer.removeObject(check.getName());
			} else {  //Must be in Room then...
				myPlayer.getRoom().removeObject(check.getName());
				myPlayer.getRoom().showObjects();
			}
			//If it's a Key, kill the Player 
		} else if(myObject instanceof Key) { 
			//(Can't go anywhere if you eat the only Key in the room)
			result = "You forcibly shove the " + myObject.getName().toLowerCase() + " down your throat, killing yourself.\n";
			result += "You have died.\n";
			//End the game...
		} else if(myObject instanceof Treasure) {  //If Treasure, can't eat it
			result = "You can't eat a " + myObject.getName() + "\n";
		} else if(myObject instanceof QuestItem) { //Can't eat QuestItems either
			result = "Someone needs that " + myObject.getName() + "!";
		}
	}
	
	/**
	 * Accessor for the result of executing the command.
	 */
	public String getResult() {
		return result;
	}
}

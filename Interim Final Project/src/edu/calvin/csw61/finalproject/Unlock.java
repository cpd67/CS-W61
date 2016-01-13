package edu.calvin.csw61.finalproject;

public class Unlock implements CommandBehavior {
	
	Player myPlayer;
	Key myKey;
	//Door myDoor;
	
	public Unlock(Player p) { //Door door
		myPlayer = p;
		myKey = new Key("key");
		//myDoor = door;
	}
	
	@Override
	public void execute() {
		//Check if the door is locked...
		//if so
		if(myPlayer.hasItem(myKey.getName())) {
			System.out.println("You unlocked");
		} else {
			System.out.println("You don't have a key.");
		}
		//else, System.out.println("The door is unlocked..");
	}
	
}

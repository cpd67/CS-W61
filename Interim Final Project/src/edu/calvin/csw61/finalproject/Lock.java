package edu.calvin.csw61.finalproject;

public class Lock implements CommandBehavior {
	
	Player myPlayer;
	Key myKey;
	//Door myDoor;
	
	//NEEDS A DOOR TO LOCK!!!!
	public Lock(Player p) {  //Door door;
		myPlayer = p;
		myKey = new Key("key");
		//myDoor = door;
	}
	
	@Override
	public void execute() {
		//if door is unlocked...
		if(myPlayer.hasItem(myKey.getName())) {
			System.out.println("You locked");
		} else {
			System.out.println("You don't have a key");
		}
		//else, System.out.println("The door is already locked);
	}
	
}

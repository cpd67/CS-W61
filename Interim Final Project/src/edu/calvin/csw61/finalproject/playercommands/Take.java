package edu.calvin.csw61.finalproject.playercommands;

import edu.calvin.csw61.finalproject.ObjectInterface;
import edu.calvin.csw61.finalproject.Player;
import edu.calvin.csw61.weapons.Weapon;
import edu.calvin.csw61.finalproject.WeaponAdapter;

public class Take implements Command {
	Player myPlayer;
	String myObName;
	private String result;
	
	public Take(String name, Player p) {
		myPlayer = p;
		myObName = name;
		result = "";
	}
	
	//Add the Object
	public void execute() {
		if(myPlayer.getRoom().getObjects().size() == 0) {  //Are there any objects in the Room?
			result += "There are no items in this room.";
		} else {
			//Is the Object in the Current Room that the Player is in?
			if(myPlayer.getRoom().hasObject(myObName.toLowerCase())) {
				//It is
				if(myPlayer.backpackFull()) { //Is the Player's backpack full?
					result += "Backpack full! Drop an Item!"; 
				} else {
					ObjectInterface checker = myPlayer.getRoom().getObject(myObName.toLowerCase());
					if(checker instanceof WeaponAdapter) {
						WeaponAdapter weaponAdapt = (WeaponAdapter)checker; //Typecast
						Weapon takenWeapon = weaponAdapt.getWrappedWeapon(); //Get the weapon
						if(myPlayer.hasWeapon()) {
							result += "You already have a weapon!" + "\n";
							result += "Drop your weapon if you want this one!";
							myPlayer.getRoom().showObjects();
						} else {
							myPlayer.setWeapon(takenWeapon);
							result += "You picked up " + takenWeapon.getWeaponName();
							myPlayer.getRoom().removeObject(myObName.toLowerCase());  //Remove the Object from the Room.
							myPlayer.getRoom().showObjects();
						}
						//Not a Weapon
					} else {
						if(myPlayer.checkItem(checker)) { //Check if we can put the item in our backpack...
							myPlayer.addObject(myObName.toLowerCase(), checker);
							myPlayer.getRoom().removeObject(myObName.toLowerCase());  //Remove the Object from the Room.
							result += "You took " + myObName.toLowerCase();
							myPlayer.getRoom().showObjects();
						} else {
							result += "You can't put " + myObName + " in your backpack!";
						}	
					}
				}
			
		} else {
			result = myObName.toLowerCase() + " is not in this room.";
		}		
	}
	}
	
	public String getResult() {
		return result;
	}
}

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
				//Is it a Weapon?
				 if(myPlayer.getRoom().getObject(myObName.toLowerCase()) instanceof WeaponAdapter){
					 //Yes
					 //Get the weapon from the adapter
					ObjectInterface checker = myPlayer.getRoom().getObject(myObName.toLowerCase());
					WeaponAdapter weaponAdapt = (WeaponAdapter)checker; //Typecast
					Weapon takenWeapon = weaponAdapt.getWrappedWeapon(); //Get the weapon
					if(myPlayer.hasWeapon()) { //Does the Player have a Weapon?
						//Yes
						result += "You already have a weapon!" + "\n";
						result += "Drop your weapon if you want this one!";
						myPlayer.getRoom().showObjects();
					} else {
						//No
						myPlayer.setWeapon(takenWeapon);
						result += "You picked up " + takenWeapon.getWeaponName();
						myPlayer.getRoom().removeObject(myObName.toLowerCase());  //Remove the Object from the Room.
						myPlayer.getRoom().showObjects();
					}
				 } else {
					//Is is not a Weapon
					 if(myPlayer.backpackFull()) { //Is the Player's backpack full?
							result += "Backpack full! Drop an Item!"; 
						} else {
							//No. Add the Object
							myPlayer.addObject(myObName.toLowerCase(), myPlayer.getRoom().getObject(myObName.toLowerCase()));
							myPlayer.getRoom().removeObject(myObName.toLowerCase());  //Remove the Object from the Room.
							result += "You took " + myObName.toLowerCase();
							myPlayer.getRoom().showObjects();
						}
				 }
		//Object is NOT in the Room
		} else { 
			result = myObName.toLowerCase() + " is not in this room.";
		}		
	}
	}
	
	public String getResult() {
		return result;
	}
}

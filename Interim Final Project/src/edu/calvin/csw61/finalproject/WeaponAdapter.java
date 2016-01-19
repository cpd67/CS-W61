package edu.calvin.csw61.finalproject;
import edu.calvin.csw61.weapons.*;

public class WeaponAdapter implements ObjectInterface {
	Weapon myWeapon;
	
	//We need this so we can store it in a Room.
	//Rooms only expect Objects.
	//This circumvents having to change the code in the Room. ;)
	public WeaponAdapter(Weapon w) {
		myWeapon = w;
	}
	
	//Gets the Name of the Weapon
	public String getName() {
		return myWeapon.getWeaponName();
	}
	
	//Gets the wrapped up Weapon
	public Weapon getWrappedWeapon() {
		return myWeapon;
	}
	
	//Take now has to figure out if the Object we're taking is a WeaponAdapter.
	//If so, we need to get the actual Weapon that the WeaponAdapter wraps (so the
	//Player can fill the Weapon slot with it).
}

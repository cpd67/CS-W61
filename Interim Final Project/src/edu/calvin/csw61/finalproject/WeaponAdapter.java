package edu.calvin.csw61.finalproject;

import edu.calvin.csw61.weapons.Weapon;

/**
 * WeaponAdapter allows a Weapon object to be stored in a Room and given to an NPC, 
 * who are both only able to handle ObjectInterface objects.
 * (Implements the Adapter pattern, where the target interface is the ObjectInterface Interface).
 */
public class WeaponAdapter implements ObjectInterface {
	//Wrapped Weapon
	Weapon myWeapon;
	
	/** 
	 * Constructor for the WeaponAdapter class.
	 * @param w, a Weapon object representing the Weapon object to adapt.
	 */
	public WeaponAdapter(Weapon w) {
		myWeapon = w;
	}
	
	/**
	 * Accessor for the Weapon's name.
	 * @return: myWeapon.getWeaponName(), a String representing the name of the 
	 *          Weapon object.
	 */
	public String getName() {
		return myWeapon.getWeaponName();
	}
	
	/**
	 * Accessor for the wrapped up Weapon.
	 * (Needed whenever a Player gets a WeaponAdapter so that we can his/her
	 *  Weapon, which a Player can only have one of).
	 * @return: myWeapon, the Weapon that was wrapped by the WeaponAdapter.
	 */
	public Weapon getWrappedWeapon() {
		return myWeapon;
	}
}

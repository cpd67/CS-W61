package edu.calvin.csw61.weapons;

/**
 * Shotgun is a subclass of Weapon that creates Shotgun objects.
 */
public class Shotgun extends Weapon {
	
	/**
	 * Constructor for the Shotgun class.
	 */
	public Shotgun() {
		this.myName = "shotgun";
		this.myDamage = 30;
	}
	
	/**
	 * Implemented abstract accessor method that gets the name of the Weapon.
	 * @return: myName, a String representing the name of the Weapon.
	 */
	public String getWeaponName() {
		return myName;
	}
}
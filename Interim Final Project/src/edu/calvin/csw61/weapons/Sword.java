package edu.calvin.csw61.weapons;

/**
 * Sword is a subclass of Weapon that creates Sword objects.
 */
public class Sword extends Weapon {

	/**
	 * Constructor for the Sword class.
	 */
	public Sword() {
		this.myName = "sword";
		this.myDamage = 20;
	}
	
	/**
	 * Implemented abstract accessor method that gets the name of the Weapon.
	 * @return: myName, a String representing the name of the Weapon.
	 */
	public String getWeaponName() {
		return myName;
	}
}
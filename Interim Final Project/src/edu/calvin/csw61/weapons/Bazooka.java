package edu.calvin.csw61.weapons;

/**
 * Bazooka is a subclass of Weapon that creates Bazooka objects.
 */
public class Bazooka extends Weapon {
	
	/**
	 * Constructor for the Bazooka class.
	 */
	public Bazooka() {
		myName = "bazooka";
		myDamage = 100; //The gut buster...
	}
	
	/**
	 * Implemented abstract accessor method that gets the name of the Weapon.
	 * @return: myName, a String representing the name of the Weapon.
	 */
	public String getWeaponName() {
		return myName;
	}
	
}
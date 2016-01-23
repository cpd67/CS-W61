package edu.calvin.csw61.weapons;

/**
 * Lance is a subclass of Weapon that creates Lance objects.
 */
public class Lance extends Weapon {
	
	/**
	 * Constructor for the Lance class.
	 */
	public Lance() {
		myName = "lance";
		myDamage = 30;
	}
	
	/**
	 * Implemented abstract accessor method that gets the name of the Weapon.
	 * @return: myName, a String representing the name of the Weapon.
	 */
	public String getWeaponName() {
		return myName;
	}
}
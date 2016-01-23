package edu.calvin.csw61.weapons;

/**
 * Knife is a subclass of Weapon that creates Knife objects.
 */
public class Knife extends Weapon {
	
	/**
	 * Constructor for the Knife class.
	 */
	public Knife() {
		myName = "knife";
		myDamage = 10;
	}
	
	/**
	 * Implemented abstract accessor method that gets the name of the Weapon.
	 * @return: myName, a String representing the name of the Weapon.
	 */
	public String getWeaponName() {
		return myName;
	}
}
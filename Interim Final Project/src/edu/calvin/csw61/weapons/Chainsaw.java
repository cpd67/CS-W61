package edu.calvin.csw61.weapons;

/**
 * Chainsaw is a subclass of Weapon that creates Chainsaw objects.
 */
public class Chainsaw extends Weapon {
	
	/**
	 * Constructor for the Chainsaw class.
	 */
	public Chainsaw() {
		myName = "chainsaw";
		myDamage = 50;
	}
	
	/**
	 * Implemented abstract accessor method that gets the name of the Weapon.
	 * @return: myName, a String representing the name of the Weapon.
	 */
	public String getWeaponName() {
		return myName;
	}
}
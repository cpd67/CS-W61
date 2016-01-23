package edu.calvin.csw61.finalproject;

import edu.calvin.csw61.weapons.*;

/**
 * ConcreteWeaponFactory is a subclass of the abstract WeaponFactory class.
 * It allows the creation of different Weapon objects.
 * (Implements the Factory pattern).
 */
public class ConcreteWeaponFactory extends WeaponFactory {
	
	/**
	 * createWeapon() creates a Weapon object specified via parameter.
	 * @param: weaponName, a String representing the name of the Weapon object to create.
	 * @return: The Weapon object specified by the weaponName parameter, 
	 *          or null if not a valid parameter.
	 */
	public Weapon createWeapon(String weaponName) {
		if(weaponName.equals("knife")) {  //Knife, 10 hit points
			return new Knife();
		} else if(weaponName.equals("sword")) {  //Sword, 20 hit points
			return new Sword();
		} else if(weaponName.equals("shotgun")) { //Shotgun, 30 hit points
			return new Shotgun();
		} else if(weaponName.equals("lance")) {  //Lance, 40 hit points
			return new Lance();
		} else if(weaponName.equals("chainsaw")) {  //Chainsaw, 50 hit points
			return new Chainsaw();
		} else if(weaponName.equals("bazooka")) {  //Bazooka, 100 hit points
			return new Bazooka();
		} else return null;  //Invalid parameter name
	}
	
}

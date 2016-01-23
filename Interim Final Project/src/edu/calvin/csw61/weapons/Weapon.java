package edu.calvin.csw61.weapons;

/**
 * Weapon is the abstract superclass of all Weapon objects.
 * Monsters can carry Weapons by themselves, and Players can as well.
 * NPCs and Rooms must wrap a Weapon in a WeaponAdapter so that it can be converted
 * into an ObjectInterface.
 */
public abstract class Weapon {
	//Name of the Weapon
	String myName;
	//Amount of damage it can deal
	int myDamage; 
	
	//Abstract accessor method for the name of the Weapon
	public abstract String getWeaponName();
	
	/**
	 * Accessor for the damage of the Weapon.
	 * @return: myDamage, an int representing how much damage the Weapon can deal.
	 */
	public int getWeaponDamage() {
		return myDamage;
	}
}
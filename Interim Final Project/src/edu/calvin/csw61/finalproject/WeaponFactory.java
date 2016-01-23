package edu.calvin.csw61.finalproject;

import edu.calvin.csw61.weapons.Weapon;

/**
 * WeaponFactory is the abstract superclass for the ConcreteWeaponFactory.
 */
public abstract class WeaponFactory {
	//Create a Weapon object
	abstract Weapon createWeapon(String name); 
}

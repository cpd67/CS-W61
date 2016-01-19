package edu.calvin.csw61.weapons;

public class Knife extends Weapon {

	//Inherits getWeaponName(), setDamage(), getWeaponDamage(), and myDamage.
	
	//Constructor
	public Knife() {
		myName = "knife";
		myDamage = 10;
	}
	
	//Get the name (Weapon version)
	public String getWeaponName() {
		return myName;
	}
}

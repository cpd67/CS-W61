package edu.calvin.csw61.weapons;

public abstract class Weapon {
	
	String myName;
	int myDamage;  //Amount of damage it can deal
	
	public abstract String getWeaponName();
	
	//Accessor for damage
	public int getWeaponDamage() {
		return myDamage;
	}
	
	
}

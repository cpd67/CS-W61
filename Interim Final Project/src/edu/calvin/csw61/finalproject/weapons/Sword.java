package edu.calvin.csw61.weapons;

public class Sword extends Weapon {
	
	//Inherits getWeaponName(), setDamage(), getWeaponDamage(), and myDamage.
	
	//Constructor
	public Sword() {
		this.myName = "sword";
		this.myDamage = 20;
	}
	
	//Get the Name (Weapon version)
	public String getWeaponName() {
		return myName;
	}
	
}

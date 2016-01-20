package edu.calvin.csw61.weapons;

public class Shotgun extends Weapon {
	
	//Inherits getWeaponName(), setDamage(), getWeaponDamage(), and myDamage, and myName
	
	public Shotgun() {
		this.myName = "shotgun";
		this.myDamage = 30;
	}
	
	//Get the name (Weapon version)
	public String getWeaponName() {
		return myName;
	}
	
}

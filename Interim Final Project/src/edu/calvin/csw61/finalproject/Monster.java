package edu.calvin.csw61.finalproject;
import edu.calvin.csw61.weapons.*;

//We need to implement the ObjectInterface so that we can catch when a Player
//wants to eat a Monster
public class Monster extends Character implements ObjectInterface, ActionBehavior {
	
	private boolean hasWeapon; //Does the Monster have a Weapon?
	private Weapon myWeapon;
	private int myHealth;  //Health of the Monster
	
	//Instance variables are inheirted from the superclass, Character
	//Constructor for a Monster without an Object
	public Monster(String name) {
		this.myName = name;
		this.myObj = null;  //No Object, nor Weapon
		this.myWeapon = null;
		this.hasWeapon = false; //Fight with their claws
		this.myHealth = 10; //10 at start
	}

	//Constructor for a Monster with an Object
	public Monster(String name, ObjectInterface ob) {
		this.myName = name;
		this.myObj = ob;
		this.myWeapon = null; //No Weapon
		this.hasWeapon = false;
		this.myHealth = 10;
	}
	
	//Does the Monster have a Weapon?
	public boolean hasWeapon() {
		return hasWeapon;
	}
	
	//Get the current Weapon
	public Weapon getWeapon() {
		return myWeapon;
	}
	
	//Set the Weapon
	public void setWeapon(Weapon w) {
		this.myWeapon = w;
		this.hasWeapon = true;
	}
	
	//Get the Health of the Monster
	public int getHealth() {
		return myHealth;
	}
	
	//If we want to make Monsters stronger
	public void setHealth(int newHealth) {
		this.myHealth = newHealth;
	}
	
	public void subtractHealth(int health) {
		if(myHealth > 0) {
			this.myHealth -= health;
		} else {
			System.out.println(myName + " is slain!");
		}
	}
	
	//Acting is just fighting.
	public void act(Player p) {
		if(hasWeapon) {  //If the Monster has a Weapon...
			int damage = myWeapon.getWeaponDamage();
			p.subtractHealth(damage); 
		} else {
			p.subtractHealth(10); //Automatically attacks with claws.
		}
	}
	
}

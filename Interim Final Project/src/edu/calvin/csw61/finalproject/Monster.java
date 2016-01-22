package edu.calvin.csw61.finalproject;
import edu.calvin.csw61.weapons.*;

//We need to implement the ObjectInterface so that we can catch when a Player
//wants to eat a Monster
public class Monster extends Character implements ObjectInterface, ActionBehavior {
	
	private boolean hasWeapon; //Does the Monster have a Weapon?
	private Weapon myWeapon;
	private boolean hasObject;
	private int myHealth;  //Health of the Monster
	private boolean isDead;
	private int myDamage;
	
	//Instance variables are inheirted from the superclass, Character
	//Constructor for a Monster without an Object
	public Monster(String name) {
		this.myName = name;
		this.myObj = null;  //No Object, nor Weapon
		this.myWeapon = null;
		this.hasWeapon = false; //Fight with their claws
		this.myHealth = 10; //10 at start
		this.isDead = false; //Not dead
		this.hasObject = false;
		this.myDamage = 5; //5 at start
	}

	//Constructor for a Monster with an Object
	public Monster(String name, ObjectInterface ob) {
		this.myName = name;
		this.myObj = ob;
		this.myWeapon = null; //No Weapon
		this.hasWeapon = false;
		this.myHealth = 10;
		this.isDead = false; 
		this.hasObject = true;
		this.myDamage = 5;
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
		this.myDamage += this.myWeapon.getWeaponDamage(); //Add the weapon damage
		this.hasWeapon = true;
	}
	
	//No Weapon 
	public void setHasNoWeapon() {
		if(hasWeapon) { //If it even has a Weapon in the first place...
			this.myDamage -= this.myWeapon.getWeaponDamage(); //Subtract the damage from the Weapon
			this.myWeapon = null;
			this.hasWeapon = false;	
		}		
	}
	
	//Give the Monster a new Object.
	public void setObject(ObjectInterface ob) {
		myObj = ob;
		hasObject = true;
	}
	
	//Does the Monster have an Object?
	public boolean hasObject() {
		return hasObject;
	}
	
	//Get the Health of the Monster
	public int getHealth() {
		return myHealth;
	}
	
	//If we want to make Monsters stronger
	public void setHealth(int newHealth) {
		this.myHealth = newHealth;
	}
	
	//Take away health
	public void subtractHealth(int health) {
		this.myHealth -= health;
		if(this.myHealth <= 0) {
			this.isDead = true;
		}
	}
	
	//Set the damage of the Monster
	public void setDamage(int damage) {
		this.myDamage = damage;
	}
	
	//Acting is just fighting.
	public void act(Player p) {
		p.subtractHealth(myDamage); //Attack the Player!
	}
	
	//Is the Monster dead?
	public boolean isDead() {
		return isDead;
	}
}

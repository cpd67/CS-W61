package edu.calvin.csw61.finalproject;

import edu.calvin.csw61.weapons.Weapon;

/**
 * Monster is a subclass of Character and can fight a Player.
 * It implements the ActionBehavior Interface so that it can perform an 
 *  Action on the Player.
 * (In this case, fight the Player).
 */
public class Monster extends Character implements ActionBehavior {
	
	//Check if the Monster has a Weapon
	private boolean hasWeapon; 
	//The Weapon that it currently has
	private Weapon myWeapon;
	//Check if the Monster has an ObjectInterface
	private boolean hasObject;
	//Health of the Monster
	private int myHealth;  
	//Check if the Monster is still alive
	private boolean isDead;
	//How much damage can the Monster inflict (without a Weapon)
	private int myDamage;
	
	/**
	 * Constructor for the Monster class.
	 * @param: name, a String representing the name of the Monster.
	 */
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

	/**
	 * Constructor for a Monster with an ObjectInterface.
	 * @param: name, a String representing the name of the Monster.
	 * @param: ob, the ObjectInterface that the Monster should hold.
	 */
	public Monster(String name, ObjectInterface ob) {
		this.myName = name;
		this.myObj = ob;  //Has an ObjectInterface now
		this.myWeapon = null; //No Weapon still
		this.hasWeapon = false;
		this.myHealth = 10;
		this.isDead = false; 
		this.hasObject = true;
		this.myDamage = 5;
	}
	
	/**
	 * Checks if the Monster has a Weapon.
	 * @return: a boolean indicating whether or not the Monster has a Weapon.
	 */
	public boolean hasWeapon() {
		return hasWeapon;
	}
	
	/**
	 * Accessor for the Weapon of the Monster.
	 * @return: myWeapon, the Weapon object that the Monster has.
	 */
	public Weapon getWeapon() {
		return myWeapon;
	}
	
	/**
	 * Mutator for the Weapon of a Monster.
	 * @param: w, the new Weapon that the Monster should now carry. 
	 */
	public void setWeapon(Weapon w) {
		this.myWeapon = w;
		//Add the weapon damage to the Monster's damage
		this.myDamage += this.myWeapon.getWeaponDamage(); 
		this.hasWeapon = true;
	}
	
	/**
	 * Mutator for taking away the Weapon of a Monster.
	 * (Only called when the Monster dies).
	 */
	public void setHasNoWeapon() {
		//If it even has a Weapon in the first place...
		if(this.hasWeapon) { 
			//Subtract the damage from the Weapon
			this.myDamage -= this.myWeapon.getWeaponDamage(); 
			this.myWeapon = null;
			this.hasWeapon = false;	
		}		
	}
	
	/**
	 * Mutator for the ObjectInterface that the Monster should hold.
	 * @param: ob, the new ObjectInterface that the Monster should hold.
	 */
	public void setObject(ObjectInterface ob) {
		this.myObj = ob;
		this.hasObject = true; //Set it to true just in case the Monster never held an ObjectInterface 
	}                    //in the first place
	
	/**
	 * Check if the Monster has an ObjectInterface.
	 * @return: a boolean indicating whether or not the Monster has an ObjectInterface.
	 */
	public boolean hasObject() {
		return hasObject;
	}
	
	/**
	 * Accessor for the health of the Monster.
	 * @return: myHealth, an int representing the health of the Monster.
	 */
	public int getHealth() {
		return myHealth;
	}
	
	/**
	 * Mutator for the health of a Monster.
	 * @param: newHealth, the new Health that the Monster should have.
	 */
	public void setHealth(int newHealth) {
		this.myHealth = newHealth;
	}
	
	/**
	 * subtractHealth() takes away health from the Monster when it incurs damage
	 * from the Player.
	 * @param: health, an int representing how much health the Monster should lose.
	 */
	public void subtractHealth(int health) {
		this.myHealth -= health;
		if(this.myHealth <= 0) {  //If the Monster has no health...
			this.isDead = true;  
		}
	}
	
	/**
	 * Mutator for the damage that the Monster can deal to a Player.
	 * @param: damage, an int representing the new damage that the Monster can deal.
	 */
	public void setDamage(int damage) {
		this.myDamage = damage;
	}
	
	/**
	 * Accessor for the damage of the Monster.
	 * @return: myDamage, an int representing the damage of the Monster.
	 */
	public int getDamage() {
		return myDamage;
	}
	
	/**
	 * act() allows a Monster to attack a Player.
	 * @param: p, a handle to the Player.
	 */
	public void act(Player p) {
		p.subtractHealth(this.getDamage()); //Attack the Player!
	}
	
	/**
	 * Check if the Monster is still alive.
	 * @return: a boolean indicating whether or not the Monster is still alive.
	 */
	public boolean isDead() {
		return isDead;
	}
}
package edu.calvin.csw61.finalproject.playercommands;

import edu.calvin.csw61.finalproject.Monster;
import edu.calvin.csw61.finalproject.Player;

/**
 * Fight allows a Player to fight a Monster.
 * (Implements the Command Interface).
 */
public class Fight implements Command {
	
	private String result; //result string
	private Player myPlayer; //Handle to Player
	private Monster myOpponent; //Opponent
	
	/**
	 * Constructor for the Fight command object.
     * @param: m, a Monster that the Player will fight.
	 * @param: p, a handle to the Player.
	 */
	public Fight(Monster m, Player p) {
		this.myPlayer = p;
		this.myOpponent = m;
		this.result = "";
	}
	
	/**
	 * execute() executes the act of fight a Monster in the game.
	 */
	@Override
	public void execute() {
		//Player goes first
		myPlayer.dealDamage(myOpponent);
		
		//Did you kill the Monster?
		if(myOpponent.isDead()) {
			//Get the weapon/object from the Monster
			if(myOpponent.hasWeapon() || myOpponent.hasObject()) {
				//If the Weapon is already equipped by the Player...
				if(myPlayer.hasWeapon() && myPlayer.getWeapon().getWeaponName().equals(myOpponent.getWeapon().getWeaponName().toLowerCase())) {
					myOpponent.setHasNoWeapon();  //Don't drop the Weapon
				}
				//Add the Object that the Monster was carrying (and Weapon if 
				//it isn't already equipped by the Player)
				myPlayer.getRoom().addMonsterItem();  
			}
			//Take away the Monster
			myPlayer.getRoom().removeMonster();
			result = myOpponent.getName() + " is slain!\n";
			result += "You killed " + myOpponent.getName() + "!\n";
		} else {
			//Monster is still alive
			result = myOpponent.getName() + " has " + myOpponent.getHealth() + " hit points remaning\n";
			System.out.println(myOpponent.getName() + "'s turn to attack");
			//Monster's turn to fight now
			myOpponent.act(myPlayer);
		}
		//Print out the Player's health if he/she hasn't died yet...
		result += "You have " + myPlayer.getHealth() + " hit points remaining. \n";	
	}
	
	/**
	 * Accessor for the result of executing the command.
	 */
	public String getResult() {
		return result;
	}
}
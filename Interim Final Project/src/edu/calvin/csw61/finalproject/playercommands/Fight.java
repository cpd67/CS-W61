package edu.calvin.csw61.finalproject.playercommands;

import edu.calvin.csw61.finalproject.Monster;
import edu.calvin.csw61.finalproject.Player;

public class Fight implements Command {
	
	private String result; //result string
	Player myPlayer; //Handle to Player
	Monster myOpponent; //Opponent
	
	public Fight(Monster m, Player p) {
		myPlayer = p;
		myOpponent = m;
		result = "";
	}
	
	@Override
	public void execute() {
		//Player goes first
		myPlayer.dealDamage(myOpponent);
		
		//Did you kill the Monster?
		if(myOpponent.isDead()) {
			//Get the weapon/object from the Monster
			if(myOpponent.hasWeapon() || myOpponent.hasObject()) {
				//If the Weapon is already equipped by the Player...
				if(myPlayer.getWeapon().getWeaponName().equals(myOpponent.getWeapon().getWeaponName().toLowerCase())) {
					myOpponent.setHasNoWeapon();
				}
				myPlayer.getRoom().addMonsterItem();
			}
			myPlayer.getRoom().removeMonster();
			result += myOpponent.getName() + " is slain!\n";
			result += "You killed " + myOpponent.getName() + "!\n";
		} else {
			result += myOpponent.getName() + " has " + myOpponent.getHealth() + " hit points remaning\n";
			System.out.println(myOpponent.getName() + "'s turn to attack");
			//Monster's turn now
			myOpponent.act(myPlayer);
		}
		
		//Print out the Player's health if he/she hasn't died yet...
		result += "You have " + myPlayer.getHealth() + " hit points remaining. \n";	
	
	}
	
	public String getResult() {
		return result;
	}
}

package edu.calvin.csw61.finalproject.playercommands;

import edu.calvin.csw61.finalproject.Character;
import edu.calvin.csw61.finalproject.NPC;
import edu.calvin.csw61.finalproject.Player;

public class Fight implements Command {
	
	Player myPlayer; //Handle to Player
	Character myOpponent; //Opponent
	
	public Fight(Character m, Player p) {
		myPlayer = p;
		myOpponent = m;
	}
	
	@Override
	public void execute() {
		if(myOpponent instanceof NPC) {
			System.out.println("You can't fight " + myOpponent.getName());
		} else {
			//Fight the Monster
			System.out.println("You fought ");
		}
	}
}

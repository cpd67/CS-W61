package edu.calvin.csw61.finalproject.playercommands;

import edu.calvin.csw61.finalproject.Character;
import edu.calvin.csw61.finalproject.NPC;
import edu.calvin.csw61.finalproject.Player;

public class Fight implements Command {
	
	private String result; //result string
	Player myPlayer; //Handle to Player
	Character myOpponent; //Opponent
	
	public Fight(Character m, Player p) {
		myPlayer = p;
		myOpponent = m;
		result = "";
	}
	
	@Override
	public void execute() {
		if(myOpponent instanceof NPC) {
			result = "You can't fight " + myOpponent.getName() + "\n";
		} else {
			//Fight the Monster
			result = "You fought \n";
		}
	}
	
	public String getResult() {
		return result;
	}
}

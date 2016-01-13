package edu.calvin.csw61.finalproject;

public class Fight implements CommandBehavior {
	
	Player myPlayer; //Handle to Player
	Character myOpponent; //Opponent
	
	public Fight(Player p, Character m) {
		myPlayer = p;
		myOpponent = m;
	}
	
	public void execute() {
		if(myOpponent instanceof NPC) {
			System.out.println("You can't fight " + myOpponent.getName());
		} else {
			//Fight the Monster
		}
	}
}

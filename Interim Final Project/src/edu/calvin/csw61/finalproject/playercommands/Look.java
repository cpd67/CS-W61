package edu.calvin.csw61.finalproject.playercommands;

import edu.calvin.csw61.finalproject.Player;

public class Look implements Command {
	Player myPlayer;
	private String result;

	public Look(Player p) {
		this.myPlayer = p;
		result = "";
	}
	@Override
	public void execute() {
		// needs to show the room the player is in.
		result = myPlayer.getRoom().getDescriptor();
	}
	
	public String getResult() {
		return result;
	}
	

}

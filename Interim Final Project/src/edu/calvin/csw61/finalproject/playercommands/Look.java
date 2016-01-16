package edu.calvin.csw61.finalproject.playercommands;

import edu.calvin.csw61.finalproject.Player;

public class Look implements Command {
	Player myPlayer;

	public Look(Player p) {
		this.myPlayer = p;
	}
	@Override
	public void execute() {
		// needs to show the room the player is in.
		System.out.println(myPlayer.getRoom().getDescriptor());

	}

}

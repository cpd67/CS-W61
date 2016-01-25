package edu.calvin.csw61.finalproject.playercommands;

/**
 * Command is the Interface that all executable Commands will implement.
 * (These are the Commands that the Player can type, like "eat" and "walk").
 * (Implements the Command pattern).
 */
public interface Command {
	public void execute();  //Execute the command behavior	
	public String getResult(); //result of executing the Command 
}
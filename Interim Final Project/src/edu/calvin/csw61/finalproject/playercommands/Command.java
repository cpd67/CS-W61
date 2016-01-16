package edu.calvin.csw61.finalproject.playercommands;

public interface Command {
	public void execute();  //Execute the command behavior	
	public String getResult(); //the string to be printed out
}

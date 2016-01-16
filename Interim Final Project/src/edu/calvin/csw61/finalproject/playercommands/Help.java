package edu.calvin.csw61.finalproject.playercommands;

import edu.calvin.csw61.finalproject.ReadFile;

public class Help implements Command {
	ReadFile readFile;
	private String result;

	@Override
	public void execute() {
		readFile = new ReadFile("help.txt"); //new readFile with the filename help.txt
		readFile.read();
		result = readFile.getContents();
	}
	
	public String getResult() {
		return result;
	}

}

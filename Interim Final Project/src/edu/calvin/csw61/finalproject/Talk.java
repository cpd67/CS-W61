package edu.calvin.csw61.finalproject;

public class Talk implements Command {
	
	private Player myPlayer;
	private ObjectInterface myConversationPartner;
	
	public Talk(ObjectInterface myObject) {
		myConversationPartner = myObject;
	}
	
	public void execute() {
		if(myConversationPartner instanceof NPC) {
			//Spit out the lines for the NPC
		} else {  //Can't talk to anyone else.
			System.out.println("There's no one to talk to.");
		}
	}
	
}

package edu.calvin.csw61.finalproject;

public class Key implements ObjectInterface {
	String myName;
	String instruction, objectToActOn;
	
	//Don't have to set a different name for a Key.
	public Key(String name) {
		myName = "Key";
		instruction = "";
		objectToActOn = name;
	}
	
	public String getName() {
		return myName;
	}

	@Override
	public void handleCommand(String cmd) {
		if(cmd.equals("lock")){
			switch(objectToActOn){
			case "door": //and there is a door to lock and has key? This will need to be changed.
				instruction = "The door is locked.";
				break;
			case "": //if only the eat command is given
				instruction = "Lock what?";
				break;
			default: instruction = "You can't lock this."; //if an unknown food is given
			}
		}
		else if(cmd.equals("unlock")){
			switch(objectToActOn){
			case "door": //and there is a door to unlock
				instruction = "The door is unlocked.";
				break;
			case "": //if only the eat command is given
				instruction = "Unlock what?";
				break;
			default: instruction = "You can't unlock this."; //if an unknown food is given
			}
		} else {
			instruction = "Something bad happened. You should probably not type that again.";
		}

	}

	@Override
	public String getInstruction() {
		
		return instruction;
	}
	
}

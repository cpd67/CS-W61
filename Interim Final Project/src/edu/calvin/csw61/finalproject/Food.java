package edu.calvin.csw61.finalproject;

public class Food implements ObjectInterface {
	String myName, instruction;
	
	//Different food items = different names
	public Food(String name) {
		myName = name;
		instruction = "";
	}
	
	public String getName() {
		return myName;
	}
	
	public void handleCommand(String cmd){
		switch(myName){
		case "pizza": //and player.hasItem(pizza)
			instruction = "You ate the pizza";
			break;
		case "": //if only the eat command is given
			instruction = "Eat what?";
			break;
		default: instruction = "I don't know this food."; //if an unknown food is given
		}
	}
	
	public String getInstruction(){
		return instruction;
	}
	
}

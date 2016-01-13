package edu.calvin.csw61.finalproject;

public class QuestItem implements ObjectInterface {
	String myName;
	
	//Can be different items.
	public QuestItem(String name) {
		myName = name;
	}
	
	public String getName() {
		return myName;
	}

}

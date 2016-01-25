package edu.calvin.csw61.finalproject;

/**
 * QuestItem implements the ObjectInterface and is used to distinguish
 * between a normal ObjectInterface and an ObjecInterfcae needed for a Quest.
 */
public class QuestItem implements ObjectInterface {
	private String myName; //The name of the QuestItem
	
	/**
	 * Constructor for the QuestItem class.
	 * @param: name, a String representing the name of the QuestItem.
	 */
	public QuestItem(String name) {
		this.myName = name.toLowerCase();
	}
	
	/**
	 * Accessor for the name of the QuestItem.
	 * @return: myName, the String representing the name of the QuestItem.
	 */
	public String getName() {
		return myName;
	}
}
package edu.calvin.csw61.finalproject;

/**
 * QuestState is the Interface that all existing and new States must implement.
 * It keeps track of a Player's state during a Quest.
 * (Implements the State pattern).
 */
public interface QuestState {
	//Set the Player's Quest
	public void setQuest(Quest q);
	//Check if the Player has the necessary ObjectInterface for the Quest
	public boolean hasItem();
	//Get the name of the Quest
	public String getQuestName();
	//Get the id number of the Quest
	public int getId();
}

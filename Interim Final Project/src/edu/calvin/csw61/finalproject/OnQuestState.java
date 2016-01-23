package edu.calvin.csw61.finalproject;

/**
 * OnQuestState implements the QuestState Interface and is the next State for a Player
 * when he/she gains a Quest.
 */
public class OnQuestState implements QuestState {
	//Handle to the Player
	Player myPlayer;
	
	/**
	 * Constructor for the OnQuestState class.
	 * @param p, a handle to the Player.
	 */
	public OnQuestState(Player p) {
		myPlayer = p;
	}
	
	/**
	 * Mutator for the Quest of the Player.
	 * In this case, a Player can't get a new Quest if they are already on one.
	 */
	public void setQuest(Quest q) {
		System.out.println("Already on a Quest!");
		System.out.println("Complete the one you are on in order to get the next one!");
	}
	
	/**
	 * hasItem() checks if the Player has the QuestItem for a Quest.
	 * In this case, he/she may have it.
	 * @return: a boolean indicating whether the Player has the QuestItem or not.
	 */
	public boolean hasItem() {
		Quest q = myPlayer.getActualQuest(); //Get the Quest
		if(myPlayer.hasItem(q.getItem().toLowerCase())) { //If the Player has the necessary item...
			myPlayer.setQuestState(myPlayer.getHasQuestItemState()); //Change States
			return true; 
		} //Else, they don't
		return false;
	}
	
	/**
	 * Accessor for the name of the Quest.
	 * @return: a String indicating the name of the Quest that the Player is on.
	 */
	public String getQuestName() {
		Quest q = myPlayer.getActualQuest();
		return q.getName();
	}
	
	/**
	 * Accessor for the id of the Quest.
	 * @return: the int indicating the id number of the Quest that the Player is on.
	 */
	public int getId() {
		Quest q = myPlayer.getActualQuest();
		return q.getId();
	}
}

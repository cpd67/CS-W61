package edu.calvin.csw61.finalproject;

/**
 * HasQuestItemState implements the QuestState Interface and is the next State for a Player
 * when he/she find the necessary QuestItem for his/her Quest.
 * (NOTE: The State gets set back to NoQuestState as soon as the Player gives the QuestItem
 *  away to it's rightful owner. This is set in Talk or Give.).
 */
public class HasQuestItemState implements QuestState {
	//Handle to the Player
	Player myPlayer;
	
	/**
	 * Constructor for the HasQuestItemState class.
	 * @param p, a handle to the Player.
	 */
	public HasQuestItemState(Player p) {
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
	 * In this case, he/she already has it.
	 * @return: a boolean indicating the Player has the QuestItem.
	 */
	public boolean hasItem() {
		return true;
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
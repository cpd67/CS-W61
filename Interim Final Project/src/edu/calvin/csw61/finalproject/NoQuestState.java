package edu.calvin.csw61.finalproject;

/**
 * NoQuestState implements the QuestState Interface and is the default State for a Player
 * when he/she is not on a Quest.
 */
public class NoQuestState implements QuestState {
	//Handle to the Player
	Player myPlayer;
	
	/**
	 * Constructor for the NoQuestState class.
	 * @param p, a handle to the Player.
	 */
	public NoQuestState(Player p) {
		myPlayer = p;
	}
	
	/**
	 * Mutator for the Quest of the Player.
	 */
	public void setQuest(Quest q) {
		System.out.println("Quest added! - " + q.getName());
		myPlayer.setActualQuest(q);  //Set the Quest in the Player
		myPlayer.setQuestState(myPlayer.getOnQuestState());  //Change the state
	}
	
	/**
	 * hasItem() checks if the Player has the QuestItem for a Quest.
	 * In this case, he/she won't.
	 * @return: false, a boolean indicating that the Player doesn't have a QuestItem.
	 */
	public boolean hasItem() {
		return false;
	}
	
	/**
	 * Accessor for the name of the Quest.
	 * In this case, the Player is not on a Quest.
	 * @return: a String indicating that the Player isn't on a Quest yet.
	 */
	public String getQuestName() {
		return "You are not on a Quest!";
	}
	
	/**
	 * Accessor for the id of the Quest.
	 * In this case, the Player isn't on a Quest.
	 * @return: -1, indicating the Player doesn't have a Quest.
	 */
	public int getId() {
		return -1; 
	}	
}
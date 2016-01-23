package edu.calvin.csw61.finalproject;

/**
 * ActionBehavior is the Interface that NPCs and Monsters will implement in order to
 * perform a certain action.
 */
public interface ActionBehavior {
	//Act on the Player
	public void act(Player p); 
}

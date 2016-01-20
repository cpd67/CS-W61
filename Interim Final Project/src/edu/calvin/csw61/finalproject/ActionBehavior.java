package edu.calvin.csw61.finalproject;

public interface ActionBehavior {
	public void act(Player p);  //NPC and Monsters do this instead of CommandBehavior
						//Fight can have this and CommandBehavior
}

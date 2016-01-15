package edu.calvin.csw61.finalproject;

public class Door implements ApertureBehavior {
	private boolean isBreakable;
	
	public Door(){
		isBreakable = false;
	}
	
	public Door(Room room1, Room room2, boolean breakable){
		if(breakable){
			setBreakable();
		}
	}
	
	@Override
	public void setBreakable() {
		isBreakable = true;
	}

	@Override
	public boolean isBreakable() {
		return isBreakable;
	}
}

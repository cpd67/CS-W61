package edu.calvin.csw61.finalproject;

public class DoorBehavior implements ApertureBehavior {
	private boolean isBreakable;
	
	public DoorBehavior(){
		isBreakable = false;
	}
	
	public DoorBehavior(Room room1, Room room2, boolean breakable){
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

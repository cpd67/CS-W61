package edu.calvin.csw61.finalproject;

public class WallBehavior implements ApertureBehavior {
	
	String myName;
	boolean isBreakable;
	String myDirection;
	
	public WallBehavior(String name, String dir) {
		myName = name;
		myDirection = dir.toLowerCase();
		isBreakable = false;
	}
	
	public boolean isBreakable() {
		return isBreakable;
	}
	
	public String getDir() {
		return myDirection;
	}
	
	@Override
	public String getName() {
		return myName;
	}
	
	@Override
	public void setBreakable() {
		isBreakable = true;
	}
}

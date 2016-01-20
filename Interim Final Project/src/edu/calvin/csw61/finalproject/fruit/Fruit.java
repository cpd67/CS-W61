package edu.calvin.csw61.fruit;
import edu.calvin.csw61.finalproject.*;

public abstract class Fruit implements ObjectInterface {
	
	String myName;
	int myHealth;
	
	public int getHealth() {
		return myHealth;
	}
	
	@Override
	public String getName() {
		return myName;
	}
}

package edu.calvin.csw61.finalproject;
import edu.calvin.csw61.fruit.*;

public class ConcreteFruitFactory extends FruitFactory {
	
	public Fruit createFruit(String fruitName) {
		if(fruitName.equals("apple")) {
			return new Apple();
		} else if(fruitName.equals("blueberry")) {
			return new Blueberry();
		} else if(fruitName.equals("orange")) {
			return new Orange();
		} else return null;
	}
	
}

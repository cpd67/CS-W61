package edu.calvin.csw61.finalproject;

import edu.calvin.csw61.food.*;

public class ConcreteFoodFactory extends FoodFactory {
	
	public Food createFood(String foodName) {
		if(foodName.equals("cupcake")) {
			return new Cupcake();
		} else if(foodName.equals("pizza")) {
			return new Pizza();
		} else if(foodName.equals("spinach")) {
			return new Spinach();
		} else return null;
	}
}

package edu.calvin.csw61.finalproject;

import edu.calvin.csw61.food.*;

public class ConcreteFoodFactory extends FoodFactory {
	
	public Food createFood(String foodName) {
		if(foodName.equals("bagel")) {
			return new Bagel();
		} else if(foodName.equals("cupcake")) {
			return new Cupcake();
		} else if(foodName.equals("pizza")) {
			return new Pizza();
		} else if(foodName.equals("spinach")) {
			return new Spinach();
		} else if(foodName.equals("taco")) {
			return new Taco();
		} else return null;
	}
}

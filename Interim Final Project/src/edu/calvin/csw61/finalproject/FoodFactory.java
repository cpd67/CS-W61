package edu.calvin.csw61.finalproject;

import edu.calvin.csw61.food.Food;

/**
 * FoodFactory is the abstract superclass of ConcreteFoodFactory.
 */
public abstract class FoodFactory {
	//Create a Food item
	abstract Food createFood(String foodName);
}

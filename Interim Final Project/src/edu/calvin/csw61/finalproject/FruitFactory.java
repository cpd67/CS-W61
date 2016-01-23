package edu.calvin.csw61.finalproject;

import edu.calvin.csw61.fruit.Fruit;

/**
 * FruitFactory is the abstract superclass for the ConcreteFruitFactory class.
 */
public abstract class FruitFactory {
	//Create a Fruit object
	public abstract Fruit createFruit(String fruitName);
}

package edu.calvin.csw61.finalproject;
import edu.calvin.csw61.fruit.*;

/**
 * ConcreteFruitFactory is a subclass of the abstract FruitFactory class.
 * It allows the creation of different Fruit objects.
 * (Implements the Factory pattern).
 */
public class ConcreteFruitFactory extends FruitFactory {

	/**
	 * createFruit() creates a Fruit object specified via parameter.
	 * @param: fruitName, a String representing the name of the Fruit object to create.
	 * @return: The Fruit object specified by the fruitName parameter, 
	 *          or null if not a valid parameter.
	 */
	public Fruit createFruit(String fruitName) {
		if(fruitName.equals("apple")) {  //Apple, 30 hit points
			return new Apple();
		} else if(fruitName.equals("blueberry")) {  //Blueberry, 35 hit points
			return new Blueberry();
		} else if(fruitName.equals("orange")) {  //Orange, 40 hit points
			return new Orange();
		} else if(fruitName.equals("papaya")) {  //Papaya, 45 hit points
			return new Papaya();
		} else if(fruitName.equals("watermelon")) {  //Watermelon, 50 hit points
			return new Watermelon();
		} else return null;  //Invalid parameter name
	}
}

package edu.calvin.csw61.finalproject;

//Imports
import edu.calvin.csw61.food.Bagel;
import edu.calvin.csw61.food.Food;
import edu.calvin.csw61.food.Cupcake;
import edu.calvin.csw61.food.Pizza;
import edu.calvin.csw61.food.Spinach;
import edu.calvin.csw61.food.Taco;

/**
 * ConcreteFoodFactory is a subclass of the abstract FoodFactory class.
 * It allows the creation of different Food objects.
 * (Implements the Factory pattern).
 */
public class ConcreteFoodFactory extends FoodFactory {
	
	/**
	 * createFood() creates a Food object specified via parameter.
	 * @param: foodName, a String representing the name of the Food object to create.
	 * @return: The Food object specified by the foodName parameter, 
	 *          or null if not a valid parameter.
	 */
	public Food createFood(String foodName) {
		if(foodName.equals("bagel")) {  //Bagel, 5 hit points
			return new Bagel();
		} else if(foodName.equals("cupcake")) {  //Cupcake, 10 hit points
			return new Cupcake();
		} else if(foodName.equals("pizza")) {  //Pizza, 15 hit points
			return new Pizza();
		} else if(foodName.equals("spinach")) { //Spinach, 20 hit points
			return new Spinach();
		} else if(foodName.equals("taco")) { //Taco, 25 hit points
			return new Taco();
		} else return null;  //Invalid parameter name
	}
}

package edu.calvin.csw61.finalproject;

import java.util.Scanner;

public class Driver {

	public static void main(String[] args) {
		Player p = new Player();
		System.out.println("Welcome to Lost in Knightdale!");
		
		//Test 1: Can an NPC have an Object? (Works)
		ObjectInterface ob = new Key();
		
		NPC bob = new NPC("Bob", ob);
		
		System.out.println("Hello! My name is " + bob.getName());
		System.out.println("I have a " + bob.getObject().getName());
		
		//Test 2: Can Players have multiple Objects? (Yup)
		ObjectInterface ob2 = new Treasure("Bracelet");
		ObjectInterface ob3 = new QuestItem("Lever");
		ObjectInterface ob4 = new Food("Apple");
		ObjectInterface ob5 = new Key();
		ObjectInterface ob6 = new Treasure("Necklace");
		ObjectInterface ob7 = new QuestItem("Bucket");
		ObjectInterface ob8 = new Food("Cupcake");
		ObjectInterface ob9 = new Treasure("Ring");
		ObjectInterface ob10 = new QuestItem("Chicken");
		ObjectInterface ob11 = new Treasure("Ring");
		
		//Add the Objects...
		p.addObject(ob2);
		p.addObject(ob3);
		p.addObject(ob4);
		p.addObject(ob5);
		p.addObject(ob6);
		p.addObject(ob7);
		p.addObject(ob8);
		p.addObject(ob9);
		p.addObject(ob10);
		p.addObject(ob11);
		
		//Print the Backpack
		p.printBackpack();
		
		//This should not be added because the Backpack is full
		p.addObject(ob3);
		
		//Check to make sure
		p.printBackpack();
		
		//Removing Objects may be a bit tricky...
		
		//infinite loop
		while(true){
			//new scanner to get user input
			Scanner scanner = new Scanner(System.in);
			String command = scanner.nextLine();
			
			//execute the command
			p.executeCommand(command);
		}
	}

}

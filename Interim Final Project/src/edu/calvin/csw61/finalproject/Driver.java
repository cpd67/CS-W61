package edu.calvin.csw61.finalproject;

import java.util.Scanner;

public class Driver {
	
	public static void main(String[] args) {
		String verb, noun;
		Player p = new Player();
		System.out.println("Welcome to Lost in Knightdale!");
		verb = "";
		noun = "";

		//infinite loop
		while(true){
			//new scanner to get user input
			Scanner scanner = new Scanner(System.in);

			//read in the line
			String string = scanner.nextLine();
			
			//get the number of words in the string
			int wordCount = wordCount(string);
			
			if(wordCount >= 3){ //if the count is 3 or more
				System.out.println("Too many words"); //too many words
			} else if(wordCount == 1){ //else if the count is one,
				verb = string;			//set the verb to the one word
				noun = "";			//and make the noun null
				p.executeCommand(verb, noun);
			} else { //else split the string and put the first part in the noun and the second part in the verb.
				String[] parts = string.split("\\W+");
				verb = parts[0];
				noun = parts[1];
				p.executeCommand(verb, noun);
			}
			
			//execute the command

		}
	}
	
	/*
	 * This method counts the number of words in a string.
	 */
	public static int wordCount(String s){
		String trim = s.trim();
		if (trim.isEmpty()){
			return 0;
		}
		return trim.split("\\W+").length; // separate string around spaces
	}

}

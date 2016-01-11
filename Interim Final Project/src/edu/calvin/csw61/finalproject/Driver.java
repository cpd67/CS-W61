package edu.calvin.csw61.finalproject;

import java.util.Scanner;

public class Driver {

	public static void main(String[] args) {
		Player p = new Player();
		System.out.println("Welcome to Lost in Knightdale!");
		
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

package edu.calvin.csw61.finalproject;

public class Player extends Character {

	public void executeCommand(String command) {
		switch(command.toLowerCase()) {
		case "walk": case "go":
			System.out.println("you walked");
		break;
		case "eat":
			System.out.println("you ate");
			break;
		case "fight": case "hit":
			System.out.println("you fought");
			break;
		case "take": case "get":
			System.out.println("you have obtained");
			break;
		case "give":
			System.out.println("you gave");
			break;
		case "lock":
			System.out.println("you locked");
			break;
		case "unlock":
			System.out.println("you unlocked");
			break;
		case "break":
			System.out.println("you broke");
			break;
		case "use":
			System.out.println("you used");
			break;
		case "drop":
			System.out.println("you dropped");
			break;
		case "throw":
			System.out.println("you threw");
			break;
		case "dig":
			System.out.println("you dug");
			break;
		default: System.out.println("I don't know what that means.");
		}
	}
	

}

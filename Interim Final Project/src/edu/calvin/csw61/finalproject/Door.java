
package edu.calvin.csw61.finalproject;

public class Door implements ApertureBehavior {
	
	private boolean isBreakable;
	private Room myRoom;
	private Integer myNextRoomNumber;
	private boolean isLocked;
	private String myDirection;  //What direction am I in?
	private String myName;
	
	public Door(){
		isBreakable = false;
		myRoom = null;
		myNextRoomNumber = 0;  //Next Room number
		isLocked = false;
		myDirection = ""; 
		myName = "door";
	}
	
	//Room1 = player's Room, room2 = next room, dir = direction in Room.
	public Door(Room playerRoom, Integer nextRoomNumber, boolean breakable, boolean locked, String dir){
		if(breakable){
			setBreakable();
		}
		myRoom = playerRoom;  //Handle to Player's Room
		myNextRoomNumber = nextRoomNumber;    //Next Room in HashMap
		myDirection = dir.toLowerCase();
		isLocked = locked; //Am I locked?
		myName = "door";
	}
	
	@Override
	public void setBreakable() {
		isBreakable = true;
	}

	@Override
	public boolean isBreakable() {
		return isBreakable;
	}
	
	//What's my Direction in the Room?
	public void setDir(String dir) {	
		myDirection = dir.toLowerCase();
	}
		
	//Get my Direction in the Room
	public String getDir() {
		return myDirection;
	}
	
	//When you want to Lock a Door...
	public void setLocked() {
		isLocked = true;
	}
	
	//When you want to Unlock a Door...
	public void setUnlocked() {
		isLocked = false;	
	}
	
	//Get the name of the Aperture. (door in this case).
	public String getName() {
		return myName;
	}
	
	public void goThrough(Player p) {
		switch(p.getBuilding()) {
			case "Chapel":  //You are in a building (HashMap of Rooms).
				if(myNextRoomNumber == -2) {  //If we are heading Outside...
					p.setBuilding("Outside");  //The Player is now Outside
					TestClass.myCPRooms.get(p.getRoomNum()).setNoPlayer();  //The Player has left the Room.
					TestClass.outsideRoom.hasPlayer();  //The outside Room has the Player
					p.setCurrentRoom(TestClass.outsideRoom); //Handle to Outside.
					p.setRoomNum(myNextRoomNumber);  //Set the current Room number
				} else {
					TestClass.myCPRooms.get(p.getRoomNum()).setNoPlayer();  //The Player has left the Room.
					TestClass.myCPRooms.get(myNextRoomNumber).hasPlayer();  //The Player is now in the next Room...
			
					p.setCurrentRoom(TestClass.myCPRooms.get(myNextRoomNumber));  //And gets a new handle to the next Room
					p.setRoomNum(myNextRoomNumber);  //Set the current Room number
				}
				break;
			case "Commons":
				if(myNextRoomNumber == -2) {  //If we are heading Outside...
					p.setBuilding("Outside");  //The Player is now Outside
					TestClass.myCMRooms.get(p.getRoomNum()).setNoPlayer();  //The Player has left the Room.
					TestClass.outsideRoom.hasPlayer();  //The outside Room has the Player
					p.setCurrentRoom(TestClass.outsideRoom); //Handle to Outside.
					p.setRoomNum(myNextRoomNumber);  //Set the current Room number
				} else {
					TestClass.myCMRooms.get(p.getRoomNum()).setNoPlayer();  //The Player has left the Room.
					TestClass.myCMRooms.get(myNextRoomNumber).hasPlayer();  //The Player is now in the next Room...
					p.setCurrentRoom(TestClass.myCMRooms.get(myNextRoomNumber));  //And gets a new handle to the next Room
					p.setRoomNum(myNextRoomNumber);  //Set the current Room number
				}
				break;
			case "Hiemenga Hall":
				if(myNextRoomNumber == -2) {  //If we are heading Outside...
					p.setBuilding("Outside");  //The Player is now Outside
					TestClass.myHHRooms.get(p.getRoomNum()).setNoPlayer();  //The Player has left the Room.
					TestClass.outsideRoom.hasPlayer();  //The outside Room has the Player
					p.setCurrentRoom(TestClass.outsideRoom); //Handle to Outside.
					p.setRoomNum(myNextRoomNumber);  //Set the current Room number
				} else {
					TestClass.myHHRooms.get(p.getRoomNum()).setNoPlayer();  //The Player has left the Room.
					TestClass.myHHRooms.get(myNextRoomNumber).hasPlayer();  //The Player is now in the next Room...
					p.setCurrentRoom(TestClass.myHHRooms.get(myNextRoomNumber));  //And gets a new handle to the next Room
					p.setRoomNum(myNextRoomNumber);  //Set the current Room number
				}
				break;
			case "Science Building":
				if(myNextRoomNumber == -2) {  //If we are heading Outside...
					p.setBuilding("Outside");  //The Player is now Outside
					TestClass.mySBRooms.get(p.getRoomNum()).setNoPlayer();  //The Player has left the Room.
					TestClass.outsideRoom.hasPlayer();  //The outside Room has the Player
					p.setCurrentRoom(TestClass.outsideRoom); //Handle to Outside.
					p.setRoomNum(myNextRoomNumber);  //Set the current Room number
					break;
				} else {
					TestClass.mySBRooms.get(p.getRoomNum()).setNoPlayer();  //The Player has left the Room.
					TestClass.mySBRooms.get(myNextRoomNumber).hasPlayer();  //The Player is now in the next Room...
			
					p.setCurrentRoom(TestClass.mySBRooms.get(myNextRoomNumber));  //And gets a new handle to the next Room
					p.setRoomNum(myNextRoomNumber);  //Set the current Room number
				}
				break;
			case "Outside":  //If you are Outside...
				switch(p.getDirection()) {
				case "south":
					p.setBuilding("Chapel");
					TestClass.myCPRooms.get(myNextRoomNumber).hasPlayer();  //The Player is now in the next Room...		
					p.setCurrentRoom(TestClass.myCPRooms.get(myNextRoomNumber));  //And gets a new handle to the next Rooms
					p.setRoomNum(myNextRoomNumber);  //Set the current Room number
					break;
				case "north":
					p.setBuilding("Commons");
					TestClass.myCMRooms.get(myNextRoomNumber).hasPlayer();  //The Player is now in the next Room...		
					p.setCurrentRoom(TestClass.myCMRooms.get(myNextRoomNumber));  //And gets a new handle to the next Room
					p.setRoomNum(myNextRoomNumber);  //Set the current Room number
					break;
				case "west":
					p.setBuilding("Science Building");  //(I'll change it so that North Hall is North when I am done testing!
					TestClass.mySBRooms.get(myNextRoomNumber).hasPlayer();  //The Player is now in the next Room...		
					p.setCurrentRoom(TestClass.mySBRooms.get(myNextRoomNumber));  //And gets a new handle to the next Room
					p.setRoomNum(myNextRoomNumber);  //Set the current Room number
					break;
				case "east":
					p.setBuilding("Hiemenga Hall");
					TestClass.myHHRooms.get(myNextRoomNumber).hasPlayer();  //The Player is now in the next Room...
					p.setCurrentRoom(TestClass.myHHRooms.get(myNextRoomNumber));  //And gets a new handle to the next Room
					p.setRoomNum(myNextRoomNumber);  //Set the current Room number
					break;
				default:
					System.out.println("Something went wrong in Walk...");
				}
				break;
			default: 
				System.out.println("Something went wrong...");
				break;
		}
	}
	
	//Am I unlocked?
	public boolean isLocked() {
		return isLocked;
	}
}
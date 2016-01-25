package edu.calvin.csw61.finalproject;

/**
 * Door implements the ApertureBehavior Interface and mimics the behavior of a Door
 * in a Room.
 */
public class Door implements ApertureBehavior {
	
	//Check if we can break the Door
	private boolean isBreakable;
	//The Room that the Door is in
	private Room myRoom;
	//The Room that the Door connects to
	private Integer myNextRoomNumber;
	//Check if the Door is locked
	private boolean isLocked;
	//The direction of the Door in the Room
	private String myDirection;  
	//The name of the ApertureBehavior (Door)
	private String myName;
	
	/**
	 * Constructor for the Door class.
	 */
	public Door(){
		this.isBreakable = false;
		this.myNextRoomNumber = 0;  //Next Room number
		this.isLocked = false;
		this.myDirection = ""; 
		this.myName = "door";
	}
	
	//Room1 = player's Room, room2 = next room, dir = direction in Room.
	public Door(Room playerRoom, Integer nextRoomNumber, boolean breakable, boolean locked, String dir){
		if(breakable){ //Is the Door breakable?
			this.setBreakable();
		}
		this.myRoom = playerRoom;  //Handle to Player's Room
		this.myNextRoomNumber = nextRoomNumber;    //Next Room in HashMap
		this.myDirection = dir.toLowerCase();
		this.isLocked = locked; //Am I locked?
		this.myName = "door";
	}
	
	/**
	 * Mutator that makes a Door breakable.
	 */
	@Override
	public void setBreakable() {
		this.isBreakable = true;
	}

	/**
	 * Check if the Door is breakable.
	 * @return: isBreakable, a boolean indicating whether or not a Door is breakable.
	 */
	@Override
	public boolean isBreakable() {
		return isBreakable;
	}
	
	/**
	 * Mutator that sets the direction of the Door in the Room.
	 * @param: dir, a String representing the new direction of the Door in the Room.
	 */
	public void setDir(String dir) {	
		this.myDirection = dir.toLowerCase();
	}
		
	/**
	 * Accessor for the direction of the Door in the Room.
	 * @return: myDirection, the String representing the direction of the Door in the Room.
	 */
	public String getDir() {
		return myDirection;
	}
	
	/**
	 * Mutator that locks the Door.
	 */
	public void setLocked() {
		this.isLocked = true;
	}
	
	/**
	 * Mutator that unlocks the Door.
	 */
	public void setUnlocked() {
		this.isLocked = false;	
	}

	/**
	 * Accessor for the name of the ApertureBehavior. 
	 * (In this case, "door").
	 * @return: myName, the String representing the name of the ApertureBehavior. 
	 */
	public String getName() {
		return myName;
	}
	
	/**
	 * goThrough() allows a Player to "go through" the Door to another Room.
	 * @param: p, a handle to the Player.
	 */
	public void goThrough(Player p) {
		//Check the building (HashMap of Rooms) that the Player is in. 
		switch(p.getBuilding()) {
			case "Chapel":  //Chapel
				//If we are heading Outside...
				if(myNextRoomNumber == -2) {  
					//The Player is now Outside
					p.setBuilding("Outside"); 
					//The Player has left the Room.
					TestClass.myCPRooms.get(p.getRoomNum()).setNoPlayer();
					//The outside Room has the Player
					TestClass.outsideRoom.hasPlayer();  
					//Set the Player's current handle to a Room
					p.setCurrentRoom(TestClass.outsideRoom);
					//Set the current Room number
					p.setRoomNum(myNextRoomNumber);  
				} else {
					//We are still in the Chapel
					//The Player has left the Room
					TestClass.myCPRooms.get(p.getRoomNum()).setNoPlayer();  
					//The Player is now in the next Room
					TestClass.myCPRooms.get(myNextRoomNumber).hasPlayer();  
					//And gets a new handle to the next Room
					p.setCurrentRoom(TestClass.myCPRooms.get(myNextRoomNumber));
					//Set the current Room number
					p.setRoomNum(myNextRoomNumber);  
				}
				break;
				//The pattern is the same for each "building"
			case "Commons":  //Commons
				if(myNextRoomNumber == -2) {  
					p.setBuilding("Outside"); 
					TestClass.myCMRooms.get(p.getRoomNum()).setNoPlayer();  
					TestClass.outsideRoom.hasPlayer();  
					p.setCurrentRoom(TestClass.outsideRoom); 
					p.setRoomNum(myNextRoomNumber); 
				} else {
					TestClass.myCMRooms.get(p.getRoomNum()).setNoPlayer();  
					TestClass.myCMRooms.get(myNextRoomNumber).hasPlayer();  
					p.setCurrentRoom(TestClass.myCMRooms.get(myNextRoomNumber));  
					p.setRoomNum(myNextRoomNumber);
				}
				break;
			case "Hiemenga Hall": //Hiemenga Hall
				if(myNextRoomNumber == -2) {  
					p.setBuilding("Outside");  
					TestClass.myHHRooms.get(p.getRoomNum()).setNoPlayer(); 
					TestClass.outsideRoom.hasPlayer(); 
					p.setCurrentRoom(TestClass.outsideRoom);
					p.setRoomNum(myNextRoomNumber);  
				} else {
					TestClass.myHHRooms.get(p.getRoomNum()).setNoPlayer();  
					TestClass.myHHRooms.get(myNextRoomNumber).hasPlayer();  
					p.setCurrentRoom(TestClass.myHHRooms.get(myNextRoomNumber)); 
					p.setRoomNum(myNextRoomNumber);  
				}
				break;
			case "Science Building": //Science Building
				if(myNextRoomNumber == -2) {  
					p.setBuilding("Outside");  
					TestClass.mySBRooms.get(p.getRoomNum()).setNoPlayer();  
					TestClass.outsideRoom.hasPlayer();
					p.setCurrentRoom(TestClass.outsideRoom); 
					p.setRoomNum(myNextRoomNumber);  
					break;
				} else {
					TestClass.mySBRooms.get(p.getRoomNum()).setNoPlayer();  
					TestClass.mySBRooms.get(myNextRoomNumber).hasPlayer();  
					p.setCurrentRoom(TestClass.mySBRooms.get(myNextRoomNumber)); 
					p.setRoomNum(myNextRoomNumber);
				}
				break;
				//However, if you are Outside 
				//and are going from the Outside to a "building"...
			case "Outside":
				//Check the direction of the Player
				switch(p.getDirection()) {
				case "south":  //South = Chapel
					//Set the Player's "building" to the Chapel
					p.setBuilding("Chapel");  
					//The Player is now in the "building"
					TestClass.myCPRooms.get(myNextRoomNumber).hasPlayer();  	
					//And gets a handle to the first Room of the "building"
					p.setCurrentRoom(TestClass.myCPRooms.get(myNextRoomNumber));
					//As well as the next Room number
					p.setRoomNum(myNextRoomNumber);  
					break;
					//The pattern is the same for the other three directions
				case "north":  //North = Commons
					p.setBuilding("Commons");
					TestClass.myCMRooms.get(myNextRoomNumber).hasPlayer(); 		
					p.setCurrentRoom(TestClass.myCMRooms.get(myNextRoomNumber)); 
					p.setRoomNum(myNextRoomNumber);
					break;
				case "west": //West = Science Building
					p.setBuilding("Science Building"); 
					TestClass.mySBRooms.get(myNextRoomNumber).hasPlayer(); 		
					p.setCurrentRoom(TestClass.mySBRooms.get(myNextRoomNumber)); 
					p.setRoomNum(myNextRoomNumber); 
					break;
				case "east":  //East = Hiemenga Hall
					p.setBuilding("Hiemenga Hall");
					TestClass.myHHRooms.get(myNextRoomNumber).hasPlayer(); 
					p.setCurrentRoom(TestClass.myHHRooms.get(myNextRoomNumber));
					p.setRoomNum(myNextRoomNumber);  
					break;
					//Something went wrong...
				default:
					System.out.println("Something went wrong in Walk...");
				}
				break;
				//Something went wrong...
			default: 
				System.out.println("Something went wrong...");
				break;
		}
	}
	
	/**
	 * Checks if the Door is locked.
	 * @return: a boolean indicating whether or not the Door is locked.
	 */
	public boolean isLocked() {
		return isLocked;
	}
}
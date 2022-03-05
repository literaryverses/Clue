package clueGame;

public enum DoorDirection {
	LEFT, UP, DOWN, RIGHT;

	private String location;
	
	DoorDirection (String aLocation) {
		location = aLocation;
	}
	
	public String toString() {
		return location;
	}

}
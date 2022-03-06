// Jun Lee
// Matthew Brause

package clueGame;
import java.util.*;

public class BoardCell {
	private boolean isDoor;
	private boolean isOccupied;
	private DoorDirection doorDirection;
	private char initial;
	private boolean isLabel;
	private boolean isCenter;
	private char secretPassage;
	private Set<BoardCell> adjList = new HashSet<BoardCell>();
	
	public BoardCell(int row, int column) {

	}
	
	public DoorDirection getDoorDirection() {
		return doorDirection;
	}
	
	public void setIsDoor(boolean isDoor) {
		this.isDoor = isDoor;
	}
	
	public boolean isDoorway() {
		return this.isDoor;
	}

	
	public char getInitial() {
		return initial;
	}

	public void setInitial(char initial) {
		this.initial = initial;
	}
	
	public boolean getOccupied() {
		return isOccupied;
	}

	public void setOccupied(boolean isOccupied) {
		this.isOccupied = isOccupied;
	}
	
	public boolean isLabel() {
		return isLabel;
	}
	
	public void setIsLabel(boolean isLabel) {
		this.isLabel = isLabel;
	}

	public boolean isRoomCenter() {
		return isCenter;
	}

	public void setIsCenter(boolean isCenter) {
		this.isCenter = isCenter;
	}

	public char getSecretPassage() {
		return secretPassage;
	}

	public void setSecretPassage(char secretPassage) {
		this.secretPassage = secretPassage;
	}
	
	public void addAdjList(BoardCell cell) {		
		this.adjList.add(cell);
	}
}

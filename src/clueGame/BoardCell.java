// Jun Lee
// Matthew Brause

package clueGame;
import java.util.*;

import experiment.TestBoardCell;

public class BoardCell {
	private int row, col;
	private boolean isDoor;
	private boolean isOccupied;
	private DoorDirection doorDirection;
	private char initial;
	private boolean isLabel;
	private boolean isCenter;
	private char secretPassage;
	private Set<BoardCell> adjList = new HashSet<BoardCell>();
	
	public BoardCell(int row, int column) {
		this.row = row;
		this.col = column;
		this.adjList = new HashSet<BoardCell>();
		this.doorDirection = DoorDirection.NONE;
		this.isOccupied = false;
		this.isDoor = false;
		this.isLabel = false;
		this.isCenter = false;
	} 
	
	public DoorDirection getDoorDirection() {
		return doorDirection;
	}
	
	public void setDoorDirection(char w) {
		if (w=='>') {
			this.doorDirection = doorDirection.RIGHT;			
		}
		else if (w=='<') {
			this.doorDirection = doorDirection.LEFT;			
		}
		else if (w=='v') {
			this.doorDirection = doorDirection.DOWN;			
		}
		else if (w=='^'){
			this.doorDirection = doorDirection.UP;
		}
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

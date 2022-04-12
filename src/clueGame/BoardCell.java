// Jun Lee
// Matthew Brause

package clueGame;
import java.awt.Color;
import java.awt.Graphics;
import java.util.*;

public class BoardCell {
	private int row, col;
	private boolean isDoor;
	private boolean isOccupied;
	private boolean isLabel;
	private boolean isCenter;
	private boolean isUsed;
	private char initial;
	private char secretPassage;
	private DoorDirection doorDirection;
	private Set<BoardCell> adjList = new HashSet<BoardCell>();
	
	/*
	 * BoardCell constructor
	 * @param int row, int column
	 */
	public BoardCell(int row, int column) {
		this.row = row;
		this.col = column;
		this.adjList = new HashSet<BoardCell>();
		this.doorDirection = DoorDirection.NONE;
		this.isOccupied = false;
		this.isDoor = false;
		this.isLabel = false;
		this.isCenter = false;
		this.isUsed = false;
	} 
	
	/*
	 * draws boardCell
	 */
	public void draw(Graphics g, int panelWidth, int panelHeight, int cellSize) {
		
    	int xOffset = ((panelWidth-col*cellSize)/2);
    	int yOffset = ((panelHeight-row*cellSize)/2);
    	
		g.drawRect(xOffset, yOffset, cellSize, cellSize); // draw cell
		if (initial == 'W') {
			g.setColor(Color.WHITE); // white for walkways
		}
		else if (initial == 'X') { // black for unused
			g.setColor(Color.BLACK);
		}
		else {
			g.setColor(Color.GRAY); // gray for rooms
		}
		
		if (isLabel == true) {
			
		}
	}
	
	/*
	 * returns if a cell is marked as usable
	 */
	public boolean getIsUsed() {
		return isUsed;
	}

	/*
	 * sets a cell as usable or not
	 */
	public void setIsUsed(boolean isUsed) {
		this.isUsed = isUsed;
	}

	/*
	 * returns door direction of doorway
	 */
	public DoorDirection getDoorDirection() {
		return doorDirection;
	}
	
	/*
	 * sets door direction based on given symbol read on file
	 * @param char w
	 */
	public void setDoorDirection(char direction) {
		switch (direction) {
		case '>':
			this.doorDirection = doorDirection.RIGHT;
			break;
		case '<':
			this.doorDirection = doorDirection.LEFT;
			break;	
		case 'v':
			this.doorDirection = doorDirection.DOWN;
			break;	
		case '^':
			this.doorDirection = doorDirection.UP;
			break;	
		}
	}

	/*
	 * sets that cell is doorway
	 * @param boolean isDoor
	 */
	public void setIsDoor(boolean isDoor) {
		this.isDoor = isDoor;
	}
	
	/*
	 * states if cell is doorway 
	 */
	public boolean isDoorway() {
		return this.isDoor;
	}

	/*
	 * returns room of cell
	 */
	public char getInitial() {
		return initial;
	}

	/*
	 * sets room of cell based on char
	 * @param char initial
	 */
	public void setInitial(char initial) {
		this.initial = initial;
	}
	
	/*
	 * returns if cell is occupied
	 */
	public boolean getOccupied() {
		return isOccupied;
	}

	/*
	 * sets cell as occupied
	 * @param boolean isOccupied
	 */
	public void setOccupied(boolean isOccupied) {
		this.isOccupied = isOccupied;
	}
	
	/*
	 * returns if cell is room label
	 */
	public boolean isLabel() {
		return isLabel;
	}
	
	/*
	 * sets if cell is room label
	 * @param boolean isLabel
	 */
	public void setIsLabel(boolean isLabel) {
		this.isLabel = isLabel;
	}

	/*
	 * returns if cell is center of the room
	 */
	public boolean isRoomCenter() {
		return isCenter;
	}

	/*
	 * Sets the cell as center of the room
	 * @param boolean isCenter
	 */
	public void setIsCenter(boolean isCenter) {
		this.isCenter = isCenter;
	}

	/*
	 * returns the next room of secret passageway
	 */
	public char getSecretPassage() {
		return secretPassage;
	}
	
	/*
	 * sets the next room of secret passageway in cell
	 * @param char secretPassage
	 */
	public void setSecretPassage(char secretPassage) {
		this.secretPassage = secretPassage;
	}
	
	/*
	 * adds cell to adjacency list
	 * @param BoardCell cell
	 */
	public void addAdjList(BoardCell cell) {
		if (cell.isUsed && this.isUsed) {
			this.adjList.add(cell);
		}
	}
	
	/*
	 * returns adjacency list of cell
	 */
	public Set<BoardCell> getAdjList() {
		return adjList;
	}
	
	/*
	 * gets row of cell
	 */
	public int getRow() {
		return this.row;
	}
	
	/*
	 * gets column of cell
	 */
	public int getColumn() {
		return this.col;
	}
	
	@Override
	public String toString() {
		return "(" + this.row + "," + this.col + ")";
	}
	
}

// Jun Lee
// Matthew Brause

package clueGame;
import java.awt.Color;
import java.awt.Font;
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
	public void draw(Graphics g, int cellWidth, int cellHeight) {
		
    	int xPos = col*cellWidth;
    	int yPos = row*cellHeight;
    	
		if (initial == 'W') {
			g.setColor(Color.BLACK);
			g.drawRect(xPos, yPos, cellWidth, cellHeight); // black outline for walkways
			g.setColor(Color.YELLOW); // yellow fill for walkways
		}
		else if (initial == 'X') { // black fill for unused
			g.setColor(Color.BLACK);
		}
		else {
			g.setColor(Color.LIGHT_GRAY); // gray fill for rooms
		}
		g.fillRect(xPos, yPos, cellWidth, cellHeight); // draw cell
	}
	
	/*
	 * draws doors and room labels
	 */
	public void drawLabels(Graphics g, int cellWidth, int cellHeight) {
		
    	int xPos = col*cellWidth;
    	int yPos = row*cellHeight;
    	
		if (isLabel) { // if label exists
			String name = Board.getInstance().getRoom(initial).getName();
			g.setFont(new Font("", Font.BOLD, (cellWidth+cellHeight)/4));
			g.setColor(Color.blue);
			g.drawString(name, xPos, yPos);
		}
		
		g.setColor(Color.blue); // draw doors
		if (isDoor) {
			switch (doorDirection) {
			case LEFT:
				cellWidth = cellWidth/4;
				xPos -= cellWidth;
				break;
			case RIGHT:
				xPos += cellWidth;
				cellWidth = cellWidth/4;
				break;
			case UP:
				cellHeight = cellHeight/4;
				yPos -= cellHeight;
				break;
			case DOWN:
				yPos += cellHeight;
				cellHeight = cellHeight/4;
				break;
			}
			g.fillRect(xPos, yPos, cellWidth, cellHeight);
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

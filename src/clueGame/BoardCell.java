// Jun Lee
// Matthew Brause

package clueGame;
import java.util.*;
import experiment.TestBoard;


public class BoardCell {
	private int col;
	private int row;
	private Set<BoardCell> adjList;
	private boolean isDoor;
	private boolean isOccupied;
	private DoorDirection doorDirection;
	private char initial;
	private boolean roomLabel;
	private boolean roomCenter;
	private char secretPassage;
	
	public BoardCell(int column, int row) {
		this.col = column;
		this.row = row;
		this.adjList = new HashSet<BoardCell>();
	}
	
	public DoorDirection getDoorDirection() {
		return doorDirection;
	}

	public void addAdjList(BoardCell cell) {		
		this.adjList.add(cell);
	}
	
	public Set<BoardCell> getAdjList() {
		return adjList;
	}
	
	public void setIsDoor(boolean isDoor) {
		this.isDoor = isDoor;
	}
	
	public boolean getIsDoor() {
		return this.isDoor;
	}

	public void setOccupied(boolean isOccupied) {
		this.isOccupied = isOccupied;
	}
	
	public boolean getOccupied() {
		return isOccupied;
	}
	
}

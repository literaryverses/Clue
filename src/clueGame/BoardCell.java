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
	
	public BoardCell(int row, int column) {
		this.col = column;
		this.row = row;
		this.adjList = new HashSet<BoardCell>();
	}
	
	public void getDoorDirection() {
		
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
	
	public boolean isDoorway() {
		return this.isDoor;
	}

	public void setOccupied(boolean isOccupied) {
		this.isOccupied = isOccupied;
	}
	
	public boolean getOccupied() {
		return isOccupied;
	}
	
    @Override
    public String toString() {
        return this.row + " + " + this.col;
    }
	
}

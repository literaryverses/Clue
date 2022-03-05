// Jun Lee
// Matthew Brause

package clueGame;
import java.util.*;
import experiment.TestBoard;


public class BoardCell {
	private int row;
	private int col;
	private Set<BoardCell> adjList;
	private boolean isDoor;
	private boolean isOccupied;
	final static int COLS = 4;
	final static int ROWS = 4;
	
	public BoardCell(int column, int row) {
		this.row = row;
		this.col = column;
		this.adjList = new HashSet<BoardCell>();
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
	
    @Override
    public String toString() {
        return this.col + " + " + this.row;
    }
	
}

// Jun Lee
// Matthew Brause

package experiment;
import java.util.*;
import experiment.TestBoard;


public class TestBoardCell {
	private int row;
	private int col;
	private Set<TestBoardCell> adjList;
	private boolean isRoom;
	private boolean isOccupied;
	final static int COLS = 4;
	final static int ROWS = 4;
	
	
	public TestBoardCell(int column, int row) {
		super();
		this.row = row;
		this.col = column;
		this.adjList = new HashSet<TestBoardCell>();
	}

	public void addAdjList(TestBoardCell cell) {		
		this.adjList.add(cell);
	}
	
	public Set<TestBoardCell> getAdjList() {
		return adjList;
	}
	
	public void setIsRoom(boolean inRoom) {
		this.isRoom = inRoom;
	}
	
	public boolean getIsRoom() {
		return this.isRoom;
	}

	public void setOccupied(boolean isOccupied) {
		this.isOccupied = isOccupied;
	}
	
	public boolean getOccupied() {
		return isOccupied;
	}
}

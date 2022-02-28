package experiment;
import java.util.*;


public class TestBoardCell {
	private int row;
	private int col;
	private Set<TestBoardCell> adjList;
	private boolean inRoom;
	private boolean isOccupied;
	
	
	public TestBoardCell(int row, int column) {
		super();
		this.row = row;
		this.col = column;
	}

	public void addAdjList(TestBoardCell cell) {
		this.adjList.add(cell);
	}
	
	public Set<TestBoardCell> getAdjList() {
		return adjList;
	}
	
	public void setRoom(boolean inRoom) {
		this.inRoom = inRoom;
	}
	
	public boolean isRoom() {
		return this.inRoom;
	}

	public void setOccupied(boolean isOccupied) {
		this.isOccupied = isOccupied;
	}
	
	public boolean getOccupied() {
		return isOccupied;
	}
}

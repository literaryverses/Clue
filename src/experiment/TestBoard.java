// Jun Lee
// Matthew Brause

package experiment;
import java.util.*;

public class TestBoard {
	private TestBoardCell[][] grid;
	private Set<TestBoardCell> targets;
	private Set<TestBoardCell> visited;
	final static int COLS = 4;
	final static int ROWS = 4;
	
	public TestBoard() {
		visited = new HashSet<TestBoardCell>();
		grid = new TestBoardCell[COLS][ROWS];
	}
	
	public void calcTargets(TestBoardCell startCell, int pathlength) {
		
	}
	
	public Set<TestBoardCell> getTargets() {
		return new HashSet<TestBoardCell>();
	}
	
	public TestBoardCell getCell( int col, int row ) {
		return grid[col][row];
	}
}

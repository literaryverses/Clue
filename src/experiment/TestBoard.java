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
		targets = new HashSet<TestBoardCell>();
		grid = new TestBoardCell[COLS][ROWS];
		for (int i =0; i < COLS; i++) {
			for (int j=0; j < ROWS; j++) {
				grid[i][j] = new TestBoardCell(i,j);
			}
		}
	}
	
	public void calcTargets(TestBoardCell startCell, int pathLength) {
		this.visited.clear();
		this.targets.clear();
		this.visited.add(startCell);
		findAllTargets(startCell, pathLength);
	}
	
	public void findAllTargets(TestBoardCell thisCell, int numSteps) {
		for (TestBoardCell adjCell: thisCell.getAdjList()) {
			if (visited.contains(adjCell)) {
				continue;
			}
			visited.add(adjCell);
			if (numSteps == 1) {
				targets.add(adjCell);
			}
			else {
				findAllTargets(adjCell, numSteps-1);
			}
			visited.remove(adjCell);
		}
	}
	
	public Set<TestBoardCell> getTargets() {
		return targets;
	}
	
	public void setAdjList( int col, int row ) {
		TestBoardCell newCell = grid[col][row];

		if (row-1 >= 0) {
			newCell.addAdjList(grid[col][row-1]);
		} 
		if (row+1 < ROWS) {
			newCell.addAdjList(grid[col][row+1]);
		} 
		if (col-1 >= 0) {
			newCell.addAdjList(grid[col-1][row]);
		} 
		if (col+1 < COLS) {
			newCell.addAdjList(grid[col+1][row]);
		} 
		 
		
	}
	
	public TestBoardCell getCell( int col, int row ) {
		TestBoardCell cell = grid[col][row];
			
		return cell;
	}
}

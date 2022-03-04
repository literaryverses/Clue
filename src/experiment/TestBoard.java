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
		for (int i =0; i < ROWS; i++) {
			for (int j=0; j < COLS; j++) {
				grid[i][j] = new TestBoardCell(i,j);
			}
		}
	}
	
	public void calcTargets(TestBoardCell startCell, int pathLength) {
		this.visited.add(startCell);
		if (pathLength == 1) {
			
		}
		
		for (TestBoardCell adjCell : startCell.getAdjList()) {
			
		}
		
		// visited list is used to avoid backtracking.  Set to empty list
		// targets will ultimately contain the list of cells. Set to an empty list.
		// Add the start location to the visited list (so no cycle through this cell)
		// Call recursive function - name findAllTargets
			// startCell and pathLength as parameters
	}
	
	/*
	 * findALlTargets psuedo
	 * param: thisCell, numSteps
	 * 
	 * for each adjCell in adjacentCells
	 * if already visited, skip
	 * 
	 * add adjCell to visited list
	 * if numSteps==1, add adjCell to Targets
	 * else call findAllTargets with adjCell, numSteps-1
	 * remove adjCell from visited List
	 */
	
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
		if (cell.getAdjList().isEmpty()) {
			setAdjList(col, row);
		}
			
		return cell;
	}
}

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
		// base case
		// visited list is used to avoid backtracking.  Set to empty list
		// targets will ultimately contain the list of cells. Set to an empty list.
		// Add the start location to the visited list (so no cycle through this cell)
		//Call recursive function - name findAllTargets
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
	
	public Set<TestBoardCell> getTargets() {
		return targets;
	}
	
	public TestBoardCell getCell( int col, int row ) {
		return grid[col][row];
	}
}

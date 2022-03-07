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
		
		grid = new TestBoardCell[ROWS][COLS];
		for (int i =0; i < COLS; i++) {
			for (int j=0; j < ROWS; j++) {
				grid[j][i] = new TestBoardCell(j,i);
			}
		}
		makeAdjs();
	}
	
    public void calcTargets(TestBoardCell startCell, int pathLength) {
        this.visited.add(startCell);
        
        for (TestBoardCell adjCell : startCell.getAdjList()) {
            if (!(visited.contains(adjCell)) && !(adjCell.getOccupied())) { 
            	//doesn't look at this cell if its visited or occupied 
                if (pathLength == 1 || adjCell.getIsRoom()) {
                	//adds cell if it is a room entrance or last step of path
                    this.targets.add(adjCell);
                } else {
                    calcTargets(adjCell,pathLength-1); 
                }
            }
        }
        //after doing all pathing from this cell, removes it from visited so other paths can still use cell
        this.visited.remove(startCell);
    }
	public Set<TestBoardCell> getTargets() {
		return targets;
	}
	
	public void setAdjList( int row, int col ) {
		TestBoardCell newCell = grid[row][col];
		
		//adds cells to the adjacent cell list if they are in the boundaries
		if (row-1 >= 0) {
			newCell.addAdjList(grid[row-1][col]);
		} 
		if (row+1 < ROWS) {
			newCell.addAdjList(grid[row+1][col]);
		} 
		if (col-1 >= 0) {
			newCell.addAdjList(grid[row][col-1]);
		} 
		if (col+1 < COLS) {
			newCell.addAdjList(grid[row][col+1]);
		} 
		 
	}
	
	public void makeAdjs() {
		//creates the adjacent cell list for all the cells in the grid
		for (int i = 0; i < COLS; i++) {
			for (int j = 0; j < ROWS; j++) {
				setAdjList(j,i);
			}
		}
	}
	
	public TestBoardCell getCell( int row, int col ) {
		TestBoardCell cell = grid[row][col];
		return cell;
	}
}

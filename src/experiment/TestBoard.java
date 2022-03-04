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
		makeAdjs();
	}
	
    public void calcTargets(TestBoardCell startCell, int pathLength) {
        this.visited.add(startCell);
        for (TestBoardCell adjCell : startCell.getAdjList()) {
            if (!(visited.contains(adjCell)) && !(adjCell.getOccupied())) {
                if (pathLength == 1 || adjCell.getIsRoom()) {
                    this.targets.add(adjCell);
                    System.out.println(adjCell);
                } else {
                    calcTargets(adjCell,pathLength-1);
                }
            }
        }
        this.visited.remove(startCell);
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
	
	public void makeAdjs() {
		for (int i = 0; i < COLS; i++) {
			for (int j = 0; j < ROWS; j++) {
				setAdjList(i,j);
			}
		}
	}
	
	public TestBoardCell getCell( int col, int row ) {
		TestBoardCell cell = grid[col][row];

		return cell;
	}
}

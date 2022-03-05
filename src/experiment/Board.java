// Jun Lee
// Matthew Brause

package experiment;
import java.util.*;

public class Board {
	private TestBoardCell[][] grid;
	private Set<BoardCell> targets;
	private Set<BoardCell> visited;
	final static int COLS = 4;
	final static int ROWS = 4;
	
	public Board() {
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
	
    public void calcTargets(BoardCell startCell, int pathLength) {
        this.visited.add(startCell);
        
        for (BoardCell adjCell : startCell.getAdjList()) {
        	
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
	public Set<BoardCell> getTargets() {
		return targets;
	}
	
	public void setAdjList( int col, int row ) {
		BoardCell newCell = grid[col][row];
		
		//adds cells to the adjacent cell list if they are in the boundaries
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
		//creates the adjacent cell list for all the cells in the grid
		
		for (int i = 0; i < COLS; i++) {
			for (int j = 0; j < ROWS; j++) {
				setAdjList(i,j);
			}
		}
	}
	
	public BoardCell getCell( int col, int row ) {
		BoardCell cell = grid[col][row];

		return cell;
	}
}

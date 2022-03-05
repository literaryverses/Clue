// Jun Lee
// Matthew Brause

package clueGame;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class Board {
	private BoardCell[][] grid;
	private Set<BoardCell> targets;
	private Set<BoardCell> visited;
	final static int COLS = 21;
	final static int ROWS = 25;
	private String layoutFileName;
	private String setupFileName;
	private Map<Character,Room> roomMap;
	private String[][] cellTags;
	
	private static Board theInstance = new Board();
    // constructor is private to ensure only one can be created
    private Board() {
        super();
        cellTags = new String[ROWS][COLS];
        visited = new HashSet<BoardCell>();
   		targets = new HashSet<BoardCell>();
   		grid = new BoardCell[ROWS][COLS];   		
   		
   		for (int i =0; i < ROWS; i++) {
   			for (int j=0; j < COLS; j++) {
   				grid[i][j] = new BoardCell(i,j);
   			}
   		}
   		makeAdjs();
    }
    // this method returns the only Board
    public static Board getInstance() {
           return theInstance;
    }
    /*
     * initialize the board (since we are using singleton pattern)
     */
    public void initialize()
    {
    }
	
    public void calcTargets(BoardCell startCell, int pathLength) {
        this.visited.add(startCell);
        
        for (BoardCell adjCell : startCell.getAdjList()) {
        	
            if (!(visited.contains(adjCell)) && !(adjCell.getOccupied())) { 
            	//doesn't look at this cell if its visited or occupied 
                if (pathLength == 1 || adjCell.getDoor()) {
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
	
	public void setAdjList( int row, int col ) {
		BoardCell newCell = grid[row][col];
		
		//adds cells to the adjacent cell list if they are in the boundaries
		if (col-1 >= 0) {
			newCell.addAdjList(grid[row][col-1]);
		} 
		if (col+1 < COLS) {
			newCell.addAdjList(grid[row][col+1]);
		} 
		if (row-1 >= 0) {
			newCell.addAdjList(grid[row-1][col]);
		} 
		if (row+1 < ROWS) {
			newCell.addAdjList(grid[row+1][col]);
		} 
		 
	}
	
	public void makeAdjs() {
		//creates the adjacent cell list for all the cells in the grid
		
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				setAdjList(i,j);
			}
		}
	}
	
	public BoardCell getCell( int row, int col ) {
		BoardCell cell = grid[row][col];

		return cell;
	}
	
public void loadDataFile(String fileName) throws FileNotFoundException {
		String[] lineTags;
		String line;
		int count = 0;
		// open file for reading
		FileReader fileReader = new FileReader(fileName);
		Scanner scan = new Scanner(fileReader);
		
		// loop over lines and add to array as a Student
		while (scan.hasNextLine()) {
			
			// grab the data as strings
			line = scan.nextLine();
			lineTags = line.split(",");
			for (int i = 0; i < lineTags.length; i++) {
				cellTags[count][i] = lineTags[i];
			}
			count++;
			
		}

	};
	
}
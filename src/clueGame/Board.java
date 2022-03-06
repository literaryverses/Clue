// Jun Lee
// Matthew Brause

package clueGame;
import java.util.*;

public class Board {
	private BoardCell[][] grid;
	private Set<BoardCell> targets;
	private Set<BoardCell> visited;
	private static int COLS;
	private static int ROWS;
	private String layoutConfigFile;
	private String setupConfigFileName;
	private Map<Character,Room> roomMap = new HashMap<Character,Room>();
	
	private static Board theInstance = new Board();
    // constructor is private to ensure only one can be created
    private Board() {
        super();
    }
    // this method returns the only Board
    public static Board getInstance() {
           return theInstance;
    }
    /*
     * initialize the board (since we are using singleton pattern)
     */
    public void initialize() {
    	
    	grid = new BoardCell[COLS][ROWS];
		for (int i =0; i < COLS; i++) {
			for (int j=0; j < ROWS; j++) {
				grid[i][j] = new BoardCell(i,j);
			}
		}
		makeAdjs();
	}
    
	public int getNumColumns() {
		return COLS;
	}
	public int getNumRows() {
		return ROWS;
	}
    
    public void setConfigFiles(String layoutCSV, String setupTXT) {
    	this.layoutConfigFile = layoutCSV;
    	this.setupConfigFileName = setupTXT;
    	
    }
    
    public void loadSetupConfig() throws BadConfigFormatException {
    	
    }
    
    public void loadLayoutConfig() throws BadConfigFormatException {
    	
    }

	
	public Room getRoom(BoardCell cell) {
		return roomMap.get(cell.getInitial());
	}
	
	public Room getRoom(char x) {
		return roomMap.get(x);
	}
	
	public BoardCell getCell( int row, int col ) {
		BoardCell cell = grid[col][row];
		return cell;
	}
	
	public void makeAdjs() {
		//creates the adjacent cell list for all the cells in the grid
		for (int i = 0; i < COLS; i++) {
			for (int j = 0; j < ROWS; j++) {
				setAdjList(i,j);
			}
		}
	}
	
	public void setAdjList( int row, int col ) {
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
}
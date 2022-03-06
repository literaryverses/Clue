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
	private int COLS;
	private int ROWS;
	private String layoutConfigFile;
	private String setupConfigFileName;
	private Map<Character,Room> roomMap = new HashMap<Character,Room>();
	private String[][] cellTags;
	
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
    public void initialize() throws FileNotFoundException {
    	layoutConfigFile = "data/ClueLayout306.csv";
    	loadDataFile(layoutConfigFile);
    	//printBoard();
    	
    	grid = new BoardCell[ROWS][COLS];
		for (int i =0; i < ROWS; i++) {
			for (int j=0; j < COLS; j++) {
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
    
    public void setConfigFiles(String layoutConfigFile, String setupConfigFileName) throws BadConfigFormatException {

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
	
	public void loadDataFile(String fileName) throws FileNotFoundException {
        String[] lineTags;
        String line;
        ArrayList<String> raws = new ArrayList<String>();

        // open file for reading
        FileReader fileReader = new FileReader(fileName);
        Scanner scan = new Scanner(fileReader);
        
        // loop over lines and add to array
        while (scan.hasNextLine()) {
        	raws.add(scan.nextLine());
        }
        scan.close();
        
        ROWS = raws.size();
        lineTags = raws.get(0).split(",");
        COLS = lineTags.length;
        cellTags = new String[ROWS][COLS];
        
        for (int i = 0; i < ROWS; i++) {
            
            // grab the data as strings
            line = raws.get(i);
            lineTags = line.split(",");
            for (int j = 0; j < lineTags.length; j++) {
                cellTags[i][j] = lineTags[j];
            }          
        }

    };
    
    public void printBoard() {
    	System.out.println(ROWS + " , " + COLS);
    	for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                System.out.print(cellTags[i][j] + " ");
            }
            System.out.println(" ");
        }
    }
	
}
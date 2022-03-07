// Jun Lee
// Matthew Brause

package clueGame;
import java.io.*;
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
    	try {
    		loadSetupConfig();
    		loadLayoutConfig();
    	} catch (Exception e) {
    		System.out.println(e.getMessage());
    	}
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
    
    public void loadSetupConfig() throws BadConfigFormatException, FileNotFoundException {
		// open file
    	FileReader fileReader = new FileReader(setupConfigFileName);
		Scanner scan = new Scanner(fileReader);
		
		// read each line
		while (scan.hasNextLine()) {
			String line = scan.nextLine();
			String[] lines = line.split(", "); // split line by commas
			if (lines[0]!="Room" || lines[0]!="Space") {
				continue;
			}
			else {
				Room r = new Room();
				r.setName(lines[1]); // extract room
				Character c = lines[2].charAt(0); // extract initial
				roomMap.put(c, r);
			}
		}
		scan.close();
    }
    
    public void loadLayoutConfig() throws BadConfigFormatException, FileNotFoundException {
    	String[] line;
        ArrayList<String> raws = new ArrayList<String>();

        // open file for reading
        FileReader fileReader = new FileReader(layoutConfigFile);
        Scanner scan = new Scanner(fileReader);
        
        // loop over lines and add to array
        while (scan.hasNextLine()) {
        	raws.add(scan.nextLine());
        }
        scan.close();
        
        // determine dimensions
        ROWS = raws.size()+1;
        line = raws.get(0).split(",");
        COLS = line.length;

        // create grid from dimensions
        // extract data and save by cell
    	grid = new BoardCell[COLS][ROWS];
		for (int i =0; i < ROWS; i++) {
			line = raws.get(i).split(",");
			for (int j=0; j < COLS; j++) {
				BoardCell newCell = new BoardCell(i,j); // create new cell
				String cell = line[j]; // extract cell from 
        		
        		char first = line[j].charAt(0); // extract 1st char
        		char second = ' ';
        		newCell.setInitial(first);
        		
        		
        		if (line.length == 2) {
        			second = line[j].charAt(1); // extract 2nd char
        		}
        		if (second == '*' ) {
        			newCell.setIsCenter(true);
        			Character c = second;
        			Room room = roomMap.get(c);
        			room.setCenterCell(newCell);
        		}
        		if (second == '#') {
        			newCell.setIsLabel(true);
        			Character c = second;
        			Room room = roomMap.get(c);
        			room.setLabelCell(newCell);
        		}
        		if (second == '^' || second == 'v' || second == '>' || second == '<') {
        			newCell.setIsDoor(true);
        			newCell.setDoorDirection(second);
        		}
        		if (second < 91 && second > 64) {
        			newCell.setSecretPassage(second);
        		}
        		
				grid[i][j] = newCell; // insert cell into grid
			}
		}
		//creates the adjacent cell list for all the cells in the grid
		for (int i = 0; i < COLS; i++) {
			for (int j = 0; j < ROWS; j++) {
				setAdjList(i,j);
			}
		}
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
// Jun Lee
// Matthew Brause

package clueGame;
import java.io.*;
import java.util.*;

public class Board {
	private BoardCell[][] grid;
	private Set<BoardCell> targets = new HashSet<BoardCell>();
	private Set<BoardCell> visited = new HashSet<BoardCell>();
	private int ROWS;
	private int COLS;
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
    
    /*
     * get columns of grid
     */
	public int getNumColumns() {
		return COLS;
	}
	
	/*
	 * get rows of grid
	 */
	public int getNumRows() {
		return ROWS;
	}
    
	/*
	 * Sets up files to read 
	 * @param String layoutCSV, setupTXT
	 */
    public void setConfigFiles(String layoutCSV, String setupTXT) {
    	this.layoutConfigFile = "data/"+layoutCSV;
    	this.setupConfigFileName = "data/"+setupTXT;
    }
    
    /*
     * loads setup file to read in legend
     */
    public void loadSetupConfig() throws BadConfigFormatException, FileNotFoundException {
		// open file
    	FileReader fileReader = new FileReader(setupConfigFileName);
		Scanner scan = new Scanner(fileReader);
		
		// read each line
		while (scan.hasNextLine()) {
			String line = scan.nextLine();
			String[] lines = line.split(", "); // split line by commas
			if (lines[0].equals("Room") || lines[0].equals("Space")) {
				Room r = new Room();
				//System.out.println("here");
				r.setName(lines[1]); // extract room
				Character c = lines[2].charAt(0); // extract initial
				roomMap.put(c, r);
			}
			else if (Character.isLetter(lines[0].charAt(0))) {
				throw new BadConfigFormatException();
			}
			else {
				continue;
			}
		}
		scan.close();
    }
    
    /*
     * loads layout file to read in map
     */
    public void loadLayoutConfig() throws BadConfigFormatException, FileNotFoundException {
    	String[] line;
    	
    	// extract data from file
		ArrayList<String> raws = extractLayoutFile();
        
        // determine dimensions
        ROWS = raws.size();
        line = raws.get(0).split(",");
        COLS = line.length;

        // create grid from dimensions
    	createGrid(raws);
		makeAdjList();
	}
    
    /*
     * creates a grid based on info extracted from layout file
     * @param ArrayList<String> raws
     */
	public void createGrid(ArrayList<String> raws) throws BadConfigFormatException {
		String[] line;
		grid = new BoardCell[ROWS][COLS];
		
		for (int i =0; i < ROWS; i++) {
			
			line = raws.get(i).split(",");
			
			if (line.length < COLS) {
				throw new BadConfigFormatException("Dimensions are inconsistent");
			}
			
			for (int j=0; j < COLS; j++) {
				BoardCell newCell = new BoardCell(i,j); // create new cell
				String cell = line[j]; // extract cell from 
        		
        		char first = line[j].charAt(0); // extract 1st char
    			Character c = first;
        		if (roomMap.containsKey(c) == false) {
    				throw new BadConfigFormatException("Unidentified room located");
    			}
        		char second = ' ';
        		newCell.setInitial(first);
        		
        		if (cell.length() == 2) {
        			second = line[j].charAt(1); // extract 2nd char
        		}
        		editSpecialCells(newCell, first, second);
        		
				grid[i][j] = newCell; // insert cell into grid
			}
		}
	}
	
	/*
	 * Sets the variables for special cells
	 * @param BoardCell newCell, char first, second 
	 */
	public void editSpecialCells(BoardCell newCell, char first, char second) throws BadConfigFormatException {
		if (second == '*' ) { // Room center
			newCell.setIsCenter(true);
			Character c = first;
			Room room = roomMap.get(c);
			room.setCenterCell(newCell);
		}
		if (second == '#') { // Room Label
			newCell.setIsLabel(true);
			Character c = first;
			Room room = roomMap.get(c);
			room.setLabelCell(newCell);
		}
		if (second == '^' || second == 'v' || second == '>' || second == '<') {
			newCell.setIsDoor(true); // Doorway
			newCell.setDoorDirection(second);
		}
		if (second < 91 && second > 64) { // checks if second char is letter
			newCell.setSecretPassage(second);
		}
	}
    
	/*
	 * returns data extracted from read layout file
	 */
	public ArrayList<String> extractLayoutFile() throws FileNotFoundException {
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
		return raws;
	}
	
	/*
	 * creates an adjacency list for every cell in teh grid
	 */
	public void makeAdjList() {
		//creates the adjacent cell list for all the cells in the grid
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				if (j==21) {
					//delete
				}
				setAdjList(i,j);
			}
		}
	}

	/*
	 * gets room of given cell
	 * @param BoardCell cell
	 */
	public Room getRoom(BoardCell cell) {
		return roomMap.get(cell.getInitial());
	}
	
	/*
	 * gets room based on legend key
	 * @param char x
	 */
	public Room getRoom(char x) {
		return roomMap.get(x);
	}
	
	/*
	 * gets cell from grid based on coordinates
	 * @param int row, int col
	 */
	public BoardCell getCell( int row, int col ) {
		BoardCell cell = grid[row][col];
		return cell;
	}
	
	/*
	 * checks for valid cells to add to adjacency list of a given cell
	 *  based on cell's coordinates
	 * @param int row, int col
	 */
	public void setAdjList( int row, int col ) {
		BoardCell newCell = grid[row][col];
		BoardCell other;
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
	
	/*
	 * Calculates player's available targets based on how many steps
	 *  are needed and the starting cell
	 * @param BoardCell startCell, int pathLength
	 */
	public void calcTargets(BoardCell startCell, int pathLength) {
        this.visited.add(startCell);
        
        for (BoardCell adjCell : startCell.getAdjList()) {
            if (!(visited.contains(adjCell)) && !(adjCell.getOccupied())) { 
            	//doesn't look at this cell if its visited or occupied 
                if (pathLength == 1 || adjCell.isRoomCenter()) { // FIXME
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
	
	/*
	 * returns the target list
	 */
	public Set<BoardCell> getTargets() {
		return targets;
	}
	
	/*
	 * returns the adjacency list
	 */
	public Set<BoardCell> getAdjList(int row, int col) {
		return grid[row][col].getAdjList();
	}
}
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
				Room room = new Room();
				//System.out.println("here");
				room.setName(lines[1]); // extract room
				Character label = lines[2].charAt(0); // extract initial
				roomMap.put(label, room);
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
		ArrayList<String> raws = this.extractLayoutFile();
        
        // determine dimensions
        ROWS = raws.size();
        COLS = raws.get(0).split(",").length;
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
		switch (second) {
		case '*' :
			newCell.setIsCenter(true);
			Room room = roomMap.get(first);
			room.setCenterCell(newCell);
			newCell.setIsUsed(true);
			break;
		case '#':
			newCell.setIsLabel(true);
			Room room1 = roomMap.get(first);
			room1.setLabelCell(newCell);
			break;
		case '^':
		case 'v':
		case '<':
		case '>':
			newCell.setIsDoor(true); // Doorway
			newCell.setDoorDirection(second);
			newCell.setIsUsed(true);
			break;
		}
		if (second < 91 && second > 64) { // checks if second char is letter
			newCell.setSecretPassage(second);
			newCell.setIsUsed(true);
		}
		if (first == 'W' ) {
			newCell.setIsUsed(true);
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
		
		if (newCell.isDoorway()) {
			connectDoor(newCell,row,col);
		}
		else if (newCell.getSecretPassage() > 64) {
			Character c = newCell.getInitial();
			Room room = roomMap.get(c);
			BoardCell centerCell = room.getCenterCell();
			c = newCell.getSecretPassage();
			connectPassage(centerCell,c);
		}
		 
	}
	
	public void connectDoor(BoardCell cell, int row, int col) {
		char c = 0;
		switch (cell.getDoorDirection()) {
		case UP: 
			c = grid[row-1][col].getInitial();
			break;
		case DOWN: 
			c = grid[row+1][col].getInitial();
			break;
		case LEFT: 
			c = grid[row][col-1].getInitial();
			break;
		case RIGHT: 
			c = grid[row][col+1].getInitial();
		default:
		}
		Room room = roomMap.get(c);
		BoardCell roomCenter = room.getCenterCell();
		cell.addAdjList(roomCenter);
		roomCenter.addAdjList(cell);
	}
	
	public void connectPassage(BoardCell centerCell, Character c) {
		Room room = roomMap.get(c);
		BoardCell otherCenter = room.getCenterCell();
		centerCell.addAdjList(otherCenter);
		otherCenter.addAdjList(centerCell);
	}
	
	/*
	 * Initiates adding available targets based on how many steps
	 *  are needed from the starting cell
	 * @param BoardCell startCell, int pathLength
	 */
	public void calcTargets(BoardCell startCell, int pathLength) {
		visited.clear();
		targets.clear();
		visited.add(startCell);
		findAllTargets(startCell, pathLength);
	}
	
	/*
	 * Recursively finds available targets based on how many steps
	 *  are needed from the starting cell 
	 * @param BoardCell startCell, int numSteps
	 */
	public void findAllTargets(BoardCell startCell, int numSteps) {
        for (BoardCell adjCell : startCell.getAdjList()) {
        	if ( !visited.contains(adjCell) && (!adjCell.getOccupied() || adjCell.isRoomCenter()) ) {
        		visited.add(adjCell);
        		if (numSteps==1 || adjCell.isRoomCenter()) {
        			targets.add(adjCell);
        		}
        		else { //TODO probably need to make sure that this doenst allow travel through rooms
            		findAllTargets(adjCell, numSteps-1);
            	}
        		visited.remove(adjCell);
        	}
        }
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
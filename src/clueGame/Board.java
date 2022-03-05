// Jun Lee
// Matthew Brause

package clueGame;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

import experiment.TestBoardCell;

public class Board {
	private BoardCell[][] grid;
	private Set<BoardCell> targets;
	private Set<BoardCell> visited;
	static int COLS;
	static int ROWS;
	private String layoutConfigFile;
	private String setupConfigFileName;
	private Map<Character,Room> roomMap;
	
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
    	
    }
    
	public static int getCOLS() {
		return COLS;
	}
	public static int getROWS() {
		return ROWS;
	}
    
    public void setupConfigFiles(String layoutConfigFile, String setupConfigFileName) throws BadConfigFormatException {
    	
    }

	
	public Room getRoom(BoardCell cell) {
		return roomMap.get(cell.getInitial());
	}
	
	public BoardCell getCell( int col, int row ) {
		BoardCell cell = grid[col][row];
		return cell;
	}
}
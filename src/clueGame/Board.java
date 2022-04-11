// Jun Lee
// Matthew Brause

package clueGame;
import java.awt.*;
import java.io.*;
import java.util.*;
import javax.swing.*;

public class Board extends JPanel {
	private int rows;
	private int cols;
	private String layoutConfigFile;
	private String setupConfigFileName;
	private BoardCell[][] grid;
	private Set<BoardCell> targets = new HashSet<BoardCell>();
	private Set<BoardCell> visited = new HashSet<BoardCell>();
	private Map<Character,Room> roomMap = new HashMap<Character,Room>();
	private ArrayList<Card> deck = new ArrayList<Card>();
	private ArrayList<Player> players = new ArrayList<Player>();
	private Solution theAnswer = new Solution();
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
    		deal();
    	} catch (Exception e) {
    		//System.out.println("Error");
    		System.out.println(e.getMessage());
    	}
	}
    
    /*
     * draws the board and players
     */
    public void paintComponent(Graphics g) {
    	super.paintComponent(g);
    }
    
    /*
     * checks to see if the accusation is true or false
     * @param Solution
     */
    public boolean checkAccusation(Solution accusation)  {
    	if (!theAnswer.getPerson().equals(accusation.getPerson())) {
    		return false;
    	}
    	if (!theAnswer.getWeapon().equals(accusation.getWeapon())) {
    		return false;
    	}
    	if (!theAnswer.getRoom().equals(accusation.getRoom())) {
    		return false;
    	}
    	return true;
    }
    
    /*
     * moves the computer player
     * @param player
     */
    public BoardCell ComputerPlayerMove(Player player) {
    	BoardCell newCell = player.selectTarget(targets, roomMap, deck);
    	player.setPlace(newCell.getRow(), newCell.getColumn());
    	player.setRoomName(roomMap, grid); // set new room name for player
    	return newCell;
    }
    
    /*
     * checks to see if a given suggestion can be disproven by a player's card
     * @param Solution
     */
    public Card handleSuggestion(Solution suggestion) {
    	for (Player player : players) {
    		Card card = player.disproveSuggestion(suggestion);
    		if (card!=null) {
    			return card;
    		}
    	}
    	return null;
    }
    
    public ArrayList<Player> getPlayers() {
    	return this.players;
    }
    
    public ArrayList<Card> getDeck() {
    	return this.deck;
    }
    
    public Solution getAnswer() {
    	return theAnswer;
    }
    
    /*
     * hands out three cards to each player
     */
    public void deal() {
    	Collections.shuffle(deck);
		int playerIndex = 0;
		int cardCount = 0;
    	for (Card card : deck) { 		
    		 //Checks to see if the solution has that card type already and isn't full
    		if (!theAnswer.hasCardType(card) && !theAnswer.isFull()) {
    			setAnswer(card);
    		}
    		else {
    			Player player = players.get(playerIndex);
    			player.updateHand(card);
    			cardCount++;
    			if (cardCount == 3) {
    				playerIndex++;
    				cardCount = 0;
    			}
    		}
    	}
    }
    
    /*
     * set theAnswer with cards
     */
	public void setAnswer(Card c) {
		theAnswer.add(c);
	}
    
    /*
     * get columns of grid
     */
	public int getNumColumns() {
		return cols;
	}
	
	/*
	 * get rows of grid
	 */
	public int getNumRows() {
		return rows;
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
			
			// create cards from file and adds it to deck
			if (lines[0].equals("Room")||lines[0].equals("Person")||lines[0].equals("Weapon")) {
				Card card = new Card(lines[1]);
				card.setType(lines[0].toUpperCase());
				deck.add(card);
			}
			else if (Character.isLetter(lines[0].charAt(0)) && !lines[0].equals("Space")) {
				throw new BadConfigFormatException();
			}
			
			if (lines[0].equals("Room") || lines[0].equals("Space")) { // if line is room
				Room room = new Room();
				room.setName(lines[1]); // extract room
				Character label = lines[2].charAt(0); // extract initial
				roomMap.put(label, room);
			}
			else if (lines[0].equals("Person")) { // else if line is person
				Player player;
				if (lines[1].equals("Player")) {
					player = new HumanPlayer(lines[1]);
				}
				else {
					player = new ComputerPlayer(lines[1]);
				}
				players.add(player);
				for (Card card : deck) { // adds card of player to player's seen list
		    		if (card.getType()==CardType.PERSON && card.getName().equals(player.getName())) {
		    				player.seeCard(card);
		    		}
				}
			}
		}
    }
    
    /*
     * loads layout file to read in map
     */
    public void loadLayoutConfig() throws BadConfigFormatException, FileNotFoundException {
    	String[] line;
    	
    	// extract data from file
		ArrayList<String> raws = this.extractLayoutFile();
        // determine dimensions
        rows = raws.size();
        cols = raws.get(0).split(",").length;
        // create grid from dimensions
    	createGrid(raws);
    	placePlayers();
    	//System.out.println("here7");
		makeAdjList();
	}
    
    /*
     * set player locations on board
     */
    public void placePlayers() {
    	int[] rowLocs = {7, 19, 23, 17, 6, 0};
    	int[] colLocs = {19, 17, 7, 0, 1, 12};
    	int index = 0;
    	//System.out.println("right here");
    	//System.out.println(players.size());
    	for (Player player : players) {
    		player.setPlace(rowLocs[index], colLocs[index]); // set players in Room
    		index++;
    		if (player instanceof ComputerPlayer) {
    			player.setRoomName(roomMap, grid);
    		}
    	}
    }
    
    /*
     * creates a grid based on info extracted from layout file
     * @param ArrayList<String> raws
     */
	public void createGrid(ArrayList<String> raws) throws BadConfigFormatException {
		String[] line;
		grid = new BoardCell[rows][cols];
		
		for (int i =0; i < rows; i++) {
			
			line = raws.get(i).split(",");
			
			if (line.length < cols) {
				throw new BadConfigFormatException("Dimensions are inconsistent");
			}
			
			for (int j=0; j < cols; j++) {
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
		if (Character.isLetter(second)) { // checks if second char is letter
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
	 * creates an adjacency list for every cell in the grid
	 */
	public void makeAdjList() {
		//creates the adjacent cell list for all the cells in the grid
		for (int i = 0; i < rows; i++) { // iterates each row
			for (int j = 0; j < cols; j++) { // iterates each column
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
	 * gets room Map
	 */
	public Map<Character,Room> getRoomMap() {
		return roomMap;
	}
	
	/*
	 * gets board grid
	 */
	public BoardCell[][] getGrid() {
		return grid;
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
		if (row+1 < rows) {
			newCell.addAdjList(grid[row+1][col]);
		} 
		if (col-1 >= 0) {
			newCell.addAdjList(grid[row][col-1]);
		} 
		if (col+1 < cols) {
			newCell.addAdjList(grid[row][col+1]);
		} 
		
		if (newCell.isDoorway()) {
			connectDoor(newCell,row,col);
		}
		else if (newCell.getSecretPassage() > 64) {
			Character character = newCell.getInitial();
			Room room = roomMap.get(character);
			BoardCell centerCell = room.getCenterCell();
			character = newCell.getSecretPassage();
			connectPassage(centerCell,character);
		}
		 
	}
	
	/*
	 * This adds adjacent rooms to doors
	 * @param BoardCell cell, int row, int col
	 */
	public void connectDoor(BoardCell cell, int row, int col) {
		char roomInitial = 0;
		switch (cell.getDoorDirection()) {
		case UP: 
			roomInitial = grid[row-1][col].getInitial();
			break;
		case DOWN: 
			roomInitial = grid[row+1][col].getInitial();
			break;
		case LEFT: 
			roomInitial = grid[row][col-1].getInitial();
			break;
		case RIGHT: 
			roomInitial = grid[row][col+1].getInitial();
		default:
		}
		Room room = roomMap.get(roomInitial);
		BoardCell roomCenter = room.getCenterCell();
		cell.addAdjList(roomCenter);
		roomCenter.addAdjList(cell);
	}
	
	/*
	 * This adds adjacent rooms to secret passages
	 * @param BoardCell centerCell, Character c
	 */
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
		System.out.println("InFind");
        for (BoardCell adjCell : startCell.getAdjList()) {
        	System.out.println("InFindFor");
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
		System.out.println(targets.size());
		return targets;
	}

	/*
	 * returns the adjacency list
	 */
	public Set<BoardCell> getAdjList(int row, int col) {
		return grid[row][col].getAdjList();
	}
	
}
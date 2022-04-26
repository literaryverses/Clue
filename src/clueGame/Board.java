// Jun Lee
// Matthew Brause

package clueGame;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.Timer;

public class Board extends JPanel implements MouseListener {
	private int rows;
	private int cols;
	private int panelWidth;
	private int panelHeight;
	private int cellWidth;
	private int cellHeight;
	private int turn;
	private String layoutConfigFile;
	private String setupConfigFileName;
	private BoardCell[][] grid;
	private Set<BoardCell> targets = new HashSet<BoardCell>();
	private Set<BoardCell> visited = new HashSet<BoardCell>();
	private Map<Character,Room> roomMap = new HashMap<Character,Room>();
	private ArrayList<Card> deck = new ArrayList<Card>();
	private ArrayList<Player> players = new ArrayList<Player>();
	private Solution theAnswer = new Solution();
	private Solution theAccusation;
	private static Board theInstance = new Board();
	private boolean turnOver;
	
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
    		setupGame();
    	} catch (Exception e) {
    		System.err.println();
    	}
	}
    
    public void updateDrawing(int x, int y) {
    	
    	Timer t = new Timer(1000, new TimerListener());
    	t.start();
    }
	
	/*public JDialog suggestOrAccuse(String inRoom, boolean isAccusation) {
		JDialog suggestOptions = new JDialog();
		suggestOptions.setLayout(new GridLayout(0,2));
		
		JLabel roomLabel = new JLabel("Current room");
		JLabel personLabel = new JLabel("Person");
		JLabel weaponLabel = new JLabel("Weapon");
		JComboBox<String> roomsCombo = new JComboBox<String>();
		JComboBox<String> playersCombo = new JComboBox<String>();
		JComboBox<String> weaponsCombo = new JComboBox<String>();
		for (Card card : deck) {
			switch (card.getType()) {
			case ROOM:
				roomsCombo.addItem(card.getName());
				break;
			case PERSON:
				playersCombo.addItem(card.getName());
				break;
			case WEAPON:
				weaponsCombo.addItem(card.getName());
				break;
			}
		}
		JButton submitButton = new JButton("Submit");
		JButton cancelButton = new JButton("Cancel");
		
		suggestOptions.add(roomLabel);
		if (isAccusation) {
			suggestOptions.setTitle("Make Accusation");
			suggestOptions.add(roomsCombo);

		} else {
			suggestOptions.setTitle("Make Suggestion");
			suggestOptions.add(new JTextField(inRoom));
		}
		suggestOptions.add(personLabel);
		suggestOptions.add(playersCombo);		
		suggestOptions.add(weaponLabel);
		suggestOptions.add(weaponsCombo);
		
		return suggestOptions;
	}
	*/
    

    /*
     * timer to update players moving
     */
    private class TimerListener implements ActionListener {
    	public void actionPerformed(ActionEvent e) {
    		//players.get(turn).translate(xPos, yPos);
    	}
    }
    
    /*
     * sets up the game by getting the players and player turn
     */
    public void setupGame() {
        turnOver = false;
    	for (Player player: players) {
    		if (player instanceof HumanPlayer) {
    			turn = players.indexOf(player); // get index
    			int roll = new Random().nextInt(7);
    	    	calcTargets(getCell(player.getRow(), player.getCol()), roll);
    	    	GameControlPanel.setTurnDisplay(player, roll);
    		}
    	}
    	addMouseListener(this);
    }
    
    /*
     * updates the turn of next player
     */
    public void updateTurn() {
    	if (!turnOver) {
    		JOptionPane.showMessageDialog(this, "You haven't finished your turn");
    		return;
    	}
    	setTurn();
    	Player player = players.get(turn);
    	int roll = new Random().nextInt(6) + 1;
    	calcTargets(getCell(player.getRow(), player.getCol()), roll);
    	GameControlPanel.setTurnDisplay(player, roll);
    	if (player instanceof HumanPlayer) { // human player moves
    		turnOver = false;
    	}
    	else { // computer moves
    		computerMove(player);
    		targets.clear();
    	}
		repaint();
    }
    
    /*
     * determine player turn
     */
    public void setTurn() {
    	turn++;
    	if (turn == players.size()) {
    		turn = 0;
    	}
    }
    
    /*
     * AI on how computer player moves
     * @param Player player
     */
    public void computerMove(Player player) {
    	if (theAccusation!=null) {
    		// Make accusation FIXME
    	}
    	
    	BoardCell movedCell = computerPlayerMove(player);
    	
    	if (movedCell.isRoomCenter()) { // make suggestion
    		Solution suggestion = player.createSuggestion(getDeck());
    		
    		for (Player accused: players) { // move player into room
    			if (accused.getName().equals(suggestion.getPerson().getName())) {
    				for (var entry: roomMap.entrySet()) {
    					Room room = entry.getValue();
    					if (room.getName().equals(suggestion.getRoom().getName())) {
    						BoardCell roomCell = room.getCenterCell();
    						grid[player.getRow()][accused.getCol()].setOccupied(false);
    						accused.setPlace(roomCell.getRow(), roomCell.getColumn());
    						break;
    					}
    				}
    				break;
       			}
    		}
    		
    		GameControlPanel.setGuess(suggestion.toString()); // set suggestion to display
    		Card card = handleSuggestion(suggestion);
    		if (card==null) { // if a suggestion is not disproven
    			GameControlPanel.setGuessResult("Suggestion not disproven", Color.WHITE);
    			this.theAccusation = suggestion;
    		}
    	}
    }
    
    /*
     * draws the board and players
     */
    public void paintComponent(Graphics g) {
    	super.paintComponent(g);
    	panelWidth = getWidth();
    	panelHeight = getHeight();
    	
    	cellWidth = panelWidth / cols;
    	cellHeight = panelHeight / rows;
    	
    	paintCells(g);
    	
    	paintRoomLabels(g);
    	
    	paintTargets(g);
    	
    	ArrayList<int[]> playersPoses = paintPlayerShifts(g);
    	playersPoses.clear();
    	
    }

	public void paintCells(Graphics g) {
		//paints all the cells
    	for (int i=0;i<rows;i++) {
    		for (int j=0;j<cols;j++) {
    			grid[i][j].draw(g, cellWidth, cellHeight);
    		}
    	}
	}

	public void paintRoomLabels(Graphics g) {
		//paints the room labels
    	for (int x = 0; x < rows; x++) {
			for (int y = 0; y < cols; y++) {
				grid[x][y].drawLabels(g, cellWidth, cellHeight);
			}
		}
	}

	public void paintTargets(Graphics g) {
		//when there are targets to choose from, it draws them
    	if (!targets.isEmpty()) {
			for (BoardCell cell : targets) {
				cell.drawTarget(g, cellWidth, cellHeight);
			}
		}
	}

	public ArrayList<int[]> paintPlayerShifts(Graphics g) {
		//draws the players and shifts over for multiple people on a tile
    	ArrayList<int[]> playersPoses = new ArrayList<int[]>();
    	for (Player player: players) {
    		int dupe = 0;
    		dupe = dupeCount(player.getPos(), playersPoses);
    		playersPoses.add(player.getPos());
    		player.draw(g, cellWidth, cellHeight, dupe);
    	}
		return playersPoses;
	}
    
	@Override
	public void mouseClicked(MouseEvent e) {
		Player player = players.get(turn);
		//clicking on the board should only do things if the player is human and in the middle of the turn
		if (player instanceof HumanPlayer && !turnOver) {
			BoardCell cell = null;
			for (BoardCell target: targets) {
				//checks to see if the clicked place is a target cell
				if (target.containsClick(e.getX(), e.getY(), cellWidth, cellHeight)) {
					cell = target;
					break;
				}
			}
			//If there was a clicked target, the player gets moved there
			if ( cell != null) {
				grid[player.getRow()][player.getCol()].setOccupied(false);
				player.setPlace(cell.getRow(), cell.getColumn());
				if (cell.isRoomCenter()) {
					Room room = roomMap.get(cell.getInitial());
					doSuggestion(room);
				} else {
					cell.setOccupied(true); 
				}
				turnOver = true; 
				targets.clear();
			} else {
	    		JOptionPane.showMessageDialog(this, "Not a valid target");
			}
			
			//FIXME make suggestion if player in room
		}
		repaint();
	}
	
    public void doAccusation() {
    	if (turn != 5) {
    		JOptionPane.showMessageDialog(this, "It's not your turn");
    	} 
    	AccuSuggest accuse = new AccuSuggest(ClueGame.getInstance(), "Make an Accussation", null);
    	ArrayList<Card> accusationCards = accuse.getCards();
    	
    	if (accusationCards == null) {
    		return;
    	}
    	
    	Solution guess = new Solution();
    	for (Card card : accusationCards) {
    		guess.add(card);
    	}
    	
    	if (checkAccusation(guess)) {
    		ClueGame.getInstance().handleEndgame(1);
    	} else {
    		ClueGame.getInstance().handleEndgame(2);
    	}
    	return;
    }
    
	
	public void doSuggestion(Room room) {
		AccuSuggest suggest = new AccuSuggest(ClueGame.getInstance(), "Make an Suggestion", room);
    	ArrayList<Card> suggestionCards = suggest.getCards();
    	
    	if (suggestionCards == null) {
    		return;
    	}
    	
    	Solution guess = new Solution();
    	for (Card card : suggestionCards) {
    		guess.add(card);
    	}
    	
    	Card disprovedCard = handleSuggestion(guess);
    	ArrayList<Card> seenCards = players.get(5).getSeen();
    	if (!(seenCards.contains(disprovedCard) || players.get(5).getHand().contains(disprovedCard))) {
    		players.get(5).seeCard(disprovedCard);
    		for (Player player : players) {
    			if (player.getHand().contains(disprovedCard)) {
    				GameCardsPanel.setSeen(player,disprovedCard);
    			}
    		}
    	}
    	ClueGame.redraw();
    	return;
	}
    
    
    /*
     * Checks how many times the players position is in the list of all players positions
     * This works because only once it checks to see if playerPosition is in there, it adds it to the list of players
     */
    public int dupeCount(int[] playerPos, ArrayList<int[]> playersPoses) {
    	int count = 0;
    	
    	for (int[] pos : playersPoses) {
    		if (pos[0] == playerPos[0] && pos[1] == playerPos[1]) {
    			count++;
    		}
    	}
    	
    	return count;
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
    public BoardCell computerPlayerMove(Player player) {
    	BoardCell newCell = player.selectTarget(targets, roomMap, deck);
    	grid[player.getRow()][player.getCol()].setOccupied(false);
    	player.setPlace(newCell.getRow(), newCell.getColumn());
    	newCell.setOccupied(true);
    	player.setRoomPlayerIn(roomMap, grid); // set new room name for player
    	return newCell;
    }
    
    /*
     * checks to see if a given suggestion can be disproved by a player's card
     * @param Solution
     */
    public Card handleSuggestion(Solution suggestion) {
    	for (Player player : players) {
    		Card card = player.disproveSuggestion(suggestion);
    		if (card!=null) {
    			if (turnOver) {
        			GameControlPanel.setGuessResult("Suggestion is disproved", player.getColor());
    			} else {
        			GameControlPanel.setGuessResult(card.getName(), player.getColor());    				
    			}
    			return card;
    		}
    	}
    	return null;
    }

    /*
     * hands out three cards to each player
     */
    public void deal() {
    	int cardsPerPlayer = (deck.size()-3)/players.size(); // determine how many cards allocated per player
    	
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
    			if (cardCount == cardsPerPlayer) {
    				playerIndex++;
    				cardCount = 0;
    			}
    		}
    	}
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
		makeAdjList();
	}
    
    /*
     * set player locations on board
     */
    public void placePlayers() {
    	int[] rowLocs = {7, 19, 23, 17, 6, 0};
    	int[] colLocs = {19, 17, 7, 0, 1, 12};
    	int index = 0;
    	for (Player player : players) {
    		player.setPlace(rowLocs[index], colLocs[index]); // set players in Room
    		index++;
    		if (player instanceof ComputerPlayer) {
    			player.setRoomPlayerIn(roomMap, grid);
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
        for (BoardCell adjCell : startCell.getAdjList()) {
        	if ( !visited.contains(adjCell) && (!adjCell.getOccupied() || adjCell.isRoomCenter()) ) {
        		visited.add(adjCell);
        		if (numSteps==1 || adjCell.isRoomCenter()) {
        			targets.add(adjCell);
        		}
        		else {
            		findAllTargets(adjCell, numSteps-1);
            	}
        		visited.remove(adjCell);
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
	
    public ArrayList<Player> getPlayers() {
    	return this.players;
    }
    
    public ArrayList<Card> getDeck() {
    	return this.deck;
    }
    
    public Solution getAnswer() {
    	return theAnswer;
    }
	
    public String getAnswerText() {
    	String text = theAnswer.getPerson().getName() + " killed with a " + theAnswer.getWeapon().getName() + " in the " + theAnswer.getRoom().getName();
    	return text;
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

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
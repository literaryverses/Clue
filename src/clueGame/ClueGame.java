package clueGame;

import java.awt.BorderLayout;
import java.util.*;
import javax.swing.*;

public class ClueGame extends JFrame {

	private static ClueGame game = new ClueGame();
    private static Board board;
    private GameControlPanel controlPanel;
    private GameCardsPanel cardsPanel;
    private boolean gameOver = false;
    private int turn;
    private ArrayList<Player> players = new ArrayList<Player>();
        
    /*
     * constructor that sets up game
     */
    private ClueGame() {
        super("Clue Game - CSCI306");
        JPanel panel = new JPanel(new BorderLayout());
        setSize(1000, 1000);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        board = Board.getInstance();
        board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");    
        board.initialize();
        panel.add(board, BorderLayout.CENTER);
        
        controlPanel = new GameControlPanel();
        cardsPanel = new GameCardsPanel();
        addHand();
        panel.add(cardsPanel, BorderLayout.EAST);        
        panel.add(controlPanel, BorderLayout.SOUTH);
        add(panel);
        
        setupGame();        
    }
    
    /*
     * human player choices movement
     */
    public void humanMove(Player player) {
    	
    }
    
    
    /*
     * AI on how computer player moves
     * @param Player player
     */
    public void computerMove(Player player) {
    	// TODO accusation here
    	
    	BoardCell movedCell = board.ComputerPlayerMove(player);
    	
    	if (movedCell.isRoomCenter()) { // make suggestion
    		Solution suggestion = player.createSuggestion(board.getDeck());
    		//TODO update suggestion on game
    	}
    }
    
    /*
     * updates the turn of next player
     */
    public void updateTurn() {
    	turn++;
    	Player player = players.get(turn);
    	int roll = new Random().nextInt(7);
    	board.calcTargets(board.getCell(player.getRow(), player.getCol()), roll);
    	controlPanel.setTurn(player, roll);
    	if (player instanceof HumanPlayer) { // human player moves
    		humanMove(player);
    	}
    	else { // computer moves
    		computerMove(player);
    	}
    }

    /*
     * sets up the game by getting the players and player turn
     */
	public void setupGame() {
		players = board.getPlayers(); // get players
        
        String playerName = new String();
    	for (Player player: players) {
    		if (player instanceof HumanPlayer) {
    			playerName = player.getName(); // get HumanPlayer name
    			turn = players.indexOf(player) - 1; // get index 
    		}
    	}
        splashScreen("You are "+playerName+"\nCan you find the solution\nbefore the computer players?");
        updateTurn();
	}
    
    
    
    /*
     * returns instance of ClueGame
     */
    public static ClueGame getInstance() {
        return game;
    }
    
    /*
     * creates splashScreen
     */
    public void splashScreen(String message) { 	
    	JOptionPane pane = new JOptionPane();
    	JOptionPane.showMessageDialog(pane, message);
    }
    
    /*
     * adds hand to panel
     */
    public void addHand() {
        for (Player player : board.getPlayers()) {
            if (player instanceof HumanPlayer) {
                GameCardsPanel.setHand(player);
            }
            
        }
    }
    
    /*
     * redraws game
     */
    public static void redraw() {
        game.revalidate();
    }
    
    /*
     * main
     */
    public static void main(String[] args) {
        game.setVisible(true);
        
    }
}
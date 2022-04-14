package clueGame;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.*;

public class ClueGame extends JFrame {

	private static ClueGame game = new ClueGame();
    private static Board board;
        
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

        GameCardsPanel cards = new GameCardsPanel();
        addHand();
        panel.add(cards, BorderLayout.EAST);
        
        
        GameControlPanel control = new GameControlPanel();
        panel.add(control, BorderLayout.SOUTH);
        
        add(panel);

        splashScreen();        
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
    public void splashScreen() {
    	String name = new String();
    	for (Player player: board.getPlayers()) { // obtain HumanPlayer name
    		if (player instanceof HumanPlayer) {
    			name = player.getName();
    		}
    	}
    	
    	JOptionPane pane = new JOptionPane();
    	JOptionPane.showMessageDialog(pane, "You are "+name+"\nCan you find the solution"
    			+ "\nbefore the computer players?");
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
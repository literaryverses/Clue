package clueGame;

import java.awt.BorderLayout;
import java.util.*;
import javax.swing.*;

public class ClueGame extends JFrame {

	private static ClueGame game = new ClueGame();
    private static Board board;
    private static GameControlPanel controlPanel;
    private static GameCardsPanel cardsPanel;
        
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
        
        String playerName = new String();
    	for (Player player: board.getPlayers()) {
    		if (player instanceof HumanPlayer) {
    			playerName = player.getName(); // get HumanPlayer name
    		}
    	}
    	controlPanel.optionsScreen( "You are "+playerName+"\nCan you find the solution\nbefore the computer players?");
        
    }
    
    /*
     * returns instance of ClueGame
     */
    public static ClueGame getInstance() {
        return game;
    }
    
    /*
     * adds hand to panel
     */
    public void addHand() {
        for (Player player : board.getPlayers()) {
            if (player instanceof HumanPlayer) {
                cardsPanel.setHand(player);
            }
        }
    }
    
    /*
     * redraws game
     */
    public static void redraw() {
    	System.out.println("drawing!!");
        game.revalidate();
    }
    
    /*
     * main
     */
    
    public void handleEndgame(int endgame) {
    	String answerInfo = board.getAnswerText();
    	switch (endgame) {
    	case 1:
    		JOptionPane.showMessageDialog(board, "You were right! " + answerInfo);
    		break;
    	case 2: 
    		JOptionPane.showMessageDialog(board, "You were wrong! " + answerInfo);
    		break;
    	case 3:
    		JOptionPane.showMessageDialog(board, "The Computers found the solution! " + answerInfo);
    		break;
    	}
    	System.exit(0);
    }
    
    public static void main(String[] args) {
        game.setVisible(true);
    }
}
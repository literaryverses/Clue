package clueGame;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.*;

public class ClueGame extends JFrame {
    
private static ClueGame game = new ClueGame();
    private static Board board;
    
    //private static ArrayList<Player> players;
    
    private ClueGame() {
        super("Clue Game - CSCI306");
        JPanel panel = new JPanel(new BorderLayout());
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        board = Board.getInstance();
        board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");    
        board.initialize();
        panel.add(board, BorderLayout.CENTER);

        GameCardsPanel cards = new GameCardsPanel();
        createHand();
        panel.add(cards, BorderLayout.EAST);
        
        
        GameControlPanel control = new GameControlPanel();
        panel.add(control, BorderLayout.SOUTH);
        
        add(panel);    
        
    }
    
    public static ClueGame getInstance() {
        return game;
    }
    
    public void createHand() {
        for (Player player : board.getPlayers()) {
            if (player instanceof HumanPlayer) {
                System.out.println("Here");
                GameCardsPanel.setHand(player);
            }
            
        }
    }
    
    public static void redraw() {
        game.revalidate();
    }
    
    public static void main(String[] args) {
        game.setVisible(true);
        game.redraw();
    }
}
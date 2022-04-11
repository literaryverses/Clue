package clueGame;

import java.awt.BorderLayout;

import javax.swing.*;

public class ClueGame extends JFrame {
	
private static ClueGame game = new ClueGame();
	
	private static Board board;
	
	private ClueGame() {
		super("Clue Game - CSCI306");
		JPanel panel = new JPanel(new BorderLayout());
		setSize(700, 700);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");	
		board.initialize();
		panel.add(board, BorderLayout.CENTER);

		GameCardsPanel cards = new GameCardsPanel();
		panel.add(cards, BorderLayout.EAST);
		
		GameControlPanel control = new GameControlPanel();
		panel.add(control, BorderLayout.SOUTH);
		
		add(panel);
		
	}
	
	public static ClueGame getInstance() {
		return game;
	}
	
	public static void redraw() {
		game.revalidate();
	}
	
	public static void main(String[] args) {
		game.setVisible(true);
	}
}



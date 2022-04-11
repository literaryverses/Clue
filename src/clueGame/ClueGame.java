package clueGame;

import java.awt.BorderLayout;

import javax.swing.*;

public class ClueGame extends JFrame {
	private static Board boardPanel = Board.getInstance();
	private GameCardsPanel cardsPanel = new GameCardsPanel();
	private GameControlPanel controlPanel = new GameControlPanel();
	
	public ClueGame() {
		setTitle("Clue Game CSCI306");
		setContentPane(createLayout()); // put the panel in the frame
		setSize(700, 700);  // size the frame
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		setVisible(true); // make it visible
	}
	
	/*
	 * Adds all the display panels to JFrame
	 */
	public JPanel createLayout() {
		JPanel panel = new JPanel();
		panel.add(boardPanel, BorderLayout.CENTER);
		panel.add(cardsPanel, BorderLayout.EAST);
		panel.add(controlPanel, BorderLayout.SOUTH);
		return panel;
	}
	
	public static void main(String[] args) {
		
	}
}



package clueGame;


import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class GameControlPanel extends JPanel{
	/**
	 * Constructor for the panel, it does 90% of the work
	 */
	public GameControlPanel()  {
		JButton accuseButton = new JButton("Make Accusation");
		add(accuseButton, BorderLayout.EAST);
		JButton nextButton = new JButton("NEXT!");
		add(nextButton, BorderLayout.EAST);
	}

	/**
	 * Main to test the panel
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		GameControlPanel panel = new GameControlPanel();  // create the panel
		JFrame frame = new JFrame();  // create the frame 
		frame.setContentPane(panel); // put the panel in the frame
		frame.setSize(750, 180);  // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true); // make it visible
	}
}

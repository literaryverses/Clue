package clueGame;


import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class GameControlPanel extends JPanel{
	private JTextField playerName = new JTextField();
	private JTextField rollNum = new JTextField();
	private JTextField guessText = new JTextField();
	private JTextField guessResult = new JTextField();

	
	
	/**
	 * Constructor for the panel, it does 90% of the work
	 */
	public GameControlPanel()  {
		JPanel gameControlPanel = new JPanel();
		gameControlPanel.setLayout(new GridLayout(2,0));
		
		JPanel turnOptionsPanel = new JPanel();
		turnOptionsPanel.setLayout(new GridLayout(1,4));
		gameControlPanel.add(turnOptionsPanel);
		
		JPanel whoseTurnPanel = new JPanel();
		whoseTurnPanel.setLayout(new GridLayout(1,0));
		JLabel whoseTurn = new JLabel("Whose Turn?");
		
		JPanel rollPanel = new JPanel();
		JLabel rollLabel = new JLabel("Roll:");
		
		JButton accuseButton = new JButton("Make Accusation");
		turnOptionsPanel.add(accuseButton);
		JButton nextButton = new JButton("NEXT!");
		turnOptionsPanel.add(nextButton);
		
		JPanel guessPanel = new JPanel();
		guessPanel.setLayout(new GridLayout(0,2));
		gameControlPanel.add(guessPanel);
		
		JPanel playerGuessPanel = new JPanel();
		JPanel guessResultPanel = new JPanel();
	}

	public void setPlayerName(String playerName) {
		this.playerName.setText(playerName);
	}

	public void setRollNum(String rollNum) {
		this.rollNum.setText(rollNum);
	}

	public void setGuessText(String guessText) {
		this.guessText.setText(guessText);
	}

	public void setGuessResult(String guessResult) {
		this.guessResult.setText(guessResult);
	}

	/**
	 * Main to test the panel
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		GameControlPanel panel = new GameControlPanel();  // create the panel
		JFrame frame = new JFrame();  // create the frame 
		frame.setTitle("Game Control Panel");
		frame.setContentPane(panel); // put the panel in the frame
		frame.setSize(750, 180);  // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true); // make it visible
		

	}
}

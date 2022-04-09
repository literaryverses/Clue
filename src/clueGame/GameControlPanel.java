package clueGame;


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

public class GameControlPanel extends JPanel{
	private JTextField playerTurn = new JTextField(10);
	private JTextField rollNum = new JTextField(3);
	private JTextField guessText = new JTextField(20);
	private JTextField guessResult = new JTextField(20);

	
	
	/**
	 * Constructor for the panel, it does 90% of the work
	 */
	public GameControlPanel()  {
		setLayout(new GridLayout(2,0)); // set up Layout of control panel
		
		JPanel turnOptionsPanel = new JPanel(); // add upper turn options panel
		turnOptionsPanel.setLayout(new GridLayout(1,4));
		
		JPanel whoseTurnPanel = new JPanel();
		JLabel whoseTurn = new JLabel("Whose Turn?");
		whoseTurnPanel.add(whoseTurn);
		whoseTurnPanel.add(playerTurn);
		turnOptionsPanel.add(whoseTurnPanel);
		
		JPanel rollPanel = new JPanel();
		JLabel rollLabel = new JLabel("Roll:");
		rollPanel.add(rollLabel);
		rollPanel.add(rollNum);
		turnOptionsPanel.add(rollPanel);
		
		JButton accuseButton = new JButton("Make Accusation");
		turnOptionsPanel.add(accuseButton);
		JButton nextButton = new JButton("NEXT!");
		turnOptionsPanel.add(nextButton);
		add(turnOptionsPanel);
		
		JPanel guessPanel = new JPanel(); // add lower guess panel
		guessPanel.setLayout(new GridLayout(0,2));
		
		JPanel playerGuessPanel = new JPanel();
		playerGuessPanel.setBorder(new TitledBorder (new EtchedBorder(), "Guess"));
		playerGuessPanel.add(guessText);
		JPanel guessResultPanel = new JPanel();
		guessResultPanel.setBorder(new TitledBorder (new EtchedBorder(), "Guess Result"));
		guessResultPanel.add(guessResult);
		guessPanel.add(playerGuessPanel);
		guessPanel.add(guessResultPanel);
		add(guessPanel);

	}

	/*
	 * Sets the turn display
	 * @param Player, int
	 */
	public void setTurn(Player player, int roll) {
		this.playerTurn.setText(player.getName());
		this.rollNum.setText(Integer.toString(roll));
	}

	/*
	 * Sets the guess display
	 * @param String
	 */
	public void setGuess(String guessText) {
		this.guessText.setText(guessText);
	}

	/*
	 * Sets the guess result display
	 * @param String
	 */
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
		
		// test filling in the data
		panel.setTurn(new ComputerPlayer("Col. Mustard"), 5);
		panel.setGuess("I have no guess!");
		panel.setGuessResult("So you have nothing?");

	}
}

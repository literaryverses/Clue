package clueGame;


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

public class GameControlPanel extends JPanel{
	private JTextField playerName = new JTextField();
	private JTextField rollNum = new JTextField();
	private JTextField guessText = new JTextField();
	private JTextField guessResult = new JTextField();

	
	
	/**
	 * Constructor for the panel, it does 90% of the work
	 */
	public GameControlPanel()  {
		setLayout(new GridLayout(2,0));
		
		JPanel turnOptionsPanel = new JPanel();
		turnOptionsPanel.setLayout(new GridLayout(1,4));
		
		JPanel whoseTurnPanel = new JPanel();
		whoseTurnPanel.setLayout(new GridLayout(1,0));
		JLabel whoseTurn = new JLabel("Whose Turn?");
		whoseTurnPanel.add(whoseTurn);
		whoseTurnPanel.add(playerName);
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
		
		JPanel guessPanel = new JPanel();
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

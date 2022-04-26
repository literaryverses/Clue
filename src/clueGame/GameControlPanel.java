package clueGame;


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

public class GameControlPanel extends JPanel{
	private static JTextField playerTurn = new JTextField(10);
	private static JTextField rollNum = new JTextField(3);
	private static JTextField guessText = new JTextField(20);
	private static JTextField guessResult = new JTextField(20);
	JButton accuseButton;
	JButton nextButton;
	/**
	 * Constructor for the panel, it does 90% of the work
	 */
	public GameControlPanel()  {
		setLayout(new GridLayout(2,0)); // set up Layout of control panel
		
		JPanel turnOptionsPanel = new JPanel(); // add upper turn options panel
		turnOptionsPanel.setLayout(new GridLayout(1,4));
		
		JPanel whoseTurnPanel = createWhoseTurnPanel();
		turnOptionsPanel.add(whoseTurnPanel);
		
		JPanel rollPanel = createRollPanel();
		turnOptionsPanel.add(rollPanel);
		
		createButtons(turnOptionsPanel);
		add(turnOptionsPanel);

		
		JPanel guessPanel = createGuessPanel(); // add lower guess panel
		add(guessPanel);
	}
	
	/*
	 * create guess panel
	 */
	public JPanel createGuessPanel() {
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
		return guessPanel;
	}
	
	/*
	 * create buttons for turn options panel
	 */
	public void createButtons(JPanel turnOptionsPanel) {
		accuseButton = new JButton("Make Accusation");
		turnOptionsPanel.add(accuseButton);
		nextButton = new JButton("NEXT!");
		turnOptionsPanel.add(nextButton);
		nextButton.addActionListener(new NextListener());
	}

	/*
	 * create panel that shows rolls
	 */
	public JPanel createRollPanel() {
		JPanel rollPanel = new JPanel();
		JLabel rollLabel = new JLabel("Roll:");
		rollPanel.add(rollLabel);
		rollPanel.add(rollNum);
		return rollPanel;
	}

	/*
	 * create panel saying whose turn it is
	 */
	public JPanel createWhoseTurnPanel() {
		JPanel whoseTurnPanel = new JPanel();
		JLabel whoseTurn = new JLabel("Whose Turn?");
		whoseTurnPanel.add(whoseTurn);
		whoseTurnPanel.add(playerTurn);
		return whoseTurnPanel;
	}
	
	/*
	 * Implements events for pressing next button
	 */
	private class NextListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == nextButton) {
				Board.getInstance().updateTurn();
			} else if (e.getSource() == accuseButton) {
				Board.getInstance().doAccusation();
			}
		}
	}

	/*
	 * Sets the turn display
	 * @param Player, int
	 */
	public static void setTurnDisplay(Player player, int roll) {
		playerTurn.setText(player.getName());
		playerTurn.setBackground(player.getColor());
		rollNum.setText(Integer.toString(roll));
		playerTurn.setEditable(false);
		rollNum.setEditable(false);
	}
	
	/*
     * creates splashScreen
     */
    public void optionsScreen(String message) { 
    	JOptionPane pane = new JOptionPane();
    	JOptionPane.showMessageDialog(pane,message);
    }

	/*
	 * Sets the guess display
	 * @param String
	 */
	public static void setGuess(String guess) {
		guessText.setText(guess);
		guessText.setEditable(false);
	}

	/*
	 * Sets the guess result display
	 * @param boolean
	 */
	public static void setGuessResult(String result, Color color) {
		guessResult.setText(result);
		guessResult.setEditable(false);
		guessResult.setBackground(color);
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
		panel.setTurnDisplay(new ComputerPlayer("Col. Mustard"), 5);
		panel.setGuess("I have no guess!");
		panel.setGuessResult("So you have nothing?", Color.WHITE);
	}
}

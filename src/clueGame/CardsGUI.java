package clueGame;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;

public class CardsGUI extends JPanel{
	private Set<JTextField> inHand = new HashSet<JTextField>(20);
	private Set<JTextField> seen = new HashSet<JTextField>(20);
	/**
	 * Constructor for the panel, it does 90% of the work
	 */
	public CardsGUI() {
		setLayout(new GridLayout(0,3)); // set up Layout of control panel
	}

	public static void main(String[] args) {
		CardsGUI panel = new CardsGUI();  // create the panel
		JFrame frame = new JFrame();  // create the frame 
		frame.setTitle("Game Control Panel");
		frame.setContentPane(panel); // put the panel in the frame
		frame.setSize(180, 750);  // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true); // make it visible
		
		// test panel
	}
}

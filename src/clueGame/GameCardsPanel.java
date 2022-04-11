package clueGame;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;

public class GameCardsPanel extends JPanel{
	private ArrayList<JTextField> weapons = new ArrayList<JTextField>();
	private ArrayList<JTextField> players = new ArrayList<JTextField>();
	private ArrayList<JTextField> rooms = new ArrayList<JTextField>();
	private JPanel playersPanel;
	private JPanel roomPanel;
	private JPanel weaponPanel;
	/**
	 * Constructor for the panel, it does 90% of the work
	 */
	public GameCardsPanel() {
		setLayout(new GridLayout(3,0)); // set up Layout of control panel
		setBorder(new TitledBorder (new EtchedBorder(), "Known Cards"));
		
		// create panels
		playersPanel = createPanel("People");
		roomPanel = createPanel("Rooms");		
		weaponPanel = createPanel("Weapons");
		
		// add all panels to Cards GUI panel
		add(playersPanel);
		add(roomPanel);
		add(weaponPanel);
	}
	
	/*
	 * Creates a panel with standardized settings
	 * @param String
	 */
	public JPanel createPanel(String typeName) {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBorder(new TitledBorder (new EtchedBorder(), typeName));
		
		JLabel hand = new JLabel("In Hand:");
		JTextField fieldHand = empty();
		
		JLabel seen = new JLabel("Seen:");
		JTextField fieldSeen = empty();
		
		panel.add(hand);
		panel.add(fieldHand);
		panel.add(seen);
		panel.add(fieldSeen);
		
		return panel;
	}
	
	/*
	 * adds the seen cards from computer players
	 */
	public void setSeen(Player player, Card card) {
		JTextField field = new JTextField(card.getName());
		field.setName("Seen");
		field.setBackground(player.getColor());
		field.setEditable(false);
		
		switch (card.getType().name()) { // add card to array by type
		case "PERSON":
			players.add(field);
			break;
		case "ROOM":
			rooms.add(field);
			break;
		case "WEAPON":
			weapons.add(field);
			break;
		}
	}
	
	/*
	 * adds the deck from human player
	 * @param HumanPlayer player
	 */
	public void setHand(HumanPlayer player) {
		ArrayList<Card> hand = player.getHand();
		for (Card card : hand) {
			JTextField field = new JTextField(card.getName());
			field.setName("Hand");
			field.setBackground(player.getColor());
			field.setEditable(false);
			
			switch (card.getType().name()) { // add card to array by type
			case "PERSON":
				players.add(field);
				break;
			case "ROOM":
				rooms.add(field);
				break;
			case "WEAPON":
				weapons.add(field);
				break;
			}
		}
	}
	
	/*
	 * creates an empty JTextField
	 */
	public JTextField empty() {
		JTextField empty = new JTextField(20);
		empty.setEditable(false);
		empty.setText("None");
		return empty;
	}
	
	/*
	 * updates all the panels
	 */
	public void updateAll() {
		updatePanel(playersPanel, players);
		updatePanel(roomPanel, rooms);
		updatePanel(weaponPanel, weapons);
	}
	
	/*
	 * updates a given panel
	 * @param JPanel, ArrayList<JTextField>
	 */
	public void updatePanel(JPanel panel, ArrayList<JTextField> Typelist) {
		panel.removeAll();
		
		ArrayList<JTextField> seenList = new ArrayList<JTextField>();
		ArrayList<JTextField> handList = new ArrayList<JTextField>();
		
		// iterate through list to separate out hand and seen cards
		for(int i = Typelist.size() - 1; i >= 0; --i) {// running a for loop backwards to remove hand cards
			if (Typelist.get(i).getName().equals("Seen")) {
				seenList.add(Typelist.get(i));
			}
			else if (Typelist.get(i).getName().equals("Hand")) {
				handList.add(Typelist.get(i));
				Typelist.remove(i); // remove hand cards for next round
			}
		}
		
		JLabel hand = new JLabel("In Hand:");
		panel.add(hand);
		if (handList.size() == 0) {
			panel.add(empty());
		} else {
			for (JTextField field : handList) { // create text fields from hand list
				panel.add(field);
			}
		}
		
		
		JLabel seen = new JLabel("Seen:");
		panel.add(seen);
		if (seenList.size() == 0) {
			panel.add(empty());
		} else {
			for (JTextField field : seenList) { // create text fields from seen list
				panel.add(field);
			}
		}
	}

	public static void main(String[] args) {
		GameCardsPanel panel = new GameCardsPanel();  // create the panel
		JFrame frame = new JFrame();  // create the frame 
		frame.setTitle("Game Control Panel");
		frame.setContentPane(panel); // put the panel in the frame
		frame.setSize(180, 750);  // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true); // make it visible

		// test panels below
		HumanPlayer player = new HumanPlayer("Player");
		ComputerPlayer comp1 = new ComputerPlayer("One");
		ComputerPlayer comp2 = new ComputerPlayer("Two");
		ComputerPlayer comp3 = new ComputerPlayer("Three");
		Card weapon1 = new Card("Hammer",CardType.WEAPON);
		Card weapon2 = new Card("Sickle",CardType.WEAPON);
		Card room1 = new Card("Incubator",CardType.ROOM);
		Card room2 = new Card("Dungeon",CardType.ROOM);
		Card person1 = new Card("Karl",CardType.PERSON);
		Card person2 = new Card("Marx",CardType.PERSON);
				
		player.updateHand(room1);
		player.updateHand(weapon2);
		panel.setHand(player);
		
		panel.setSeen(comp1, person1);
		panel.setSeen(comp1, person2);
		panel.setSeen(comp2, weapon1);
		panel.setSeen(comp3, room2);
		panel.updateAll();
		frame.revalidate();
	}
}

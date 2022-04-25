package clueGame;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AccuSuggest extends JDialog implements ActionListener {
	
	private JComboBox<String> room;
	private JComboBox<String> player;
	private JComboBox<String> weapon;
	private JButton cancelButton;
	private JButton submitButton;
	
	private static Board board;
	private ArrayList<Card> cards = new ArrayList<Card>();

	public AccuSuggest(JFrame parent, String title, Room currRoom) {
		super(parent, title, true);
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(4, 2));
		setSize(400, 200);
		
		board = Board.getInstance();
		room = new JComboBox<String>();
		player = new JComboBox<String>();
		weapon = new JComboBox<String>();
		
		for(Card card : board.getDeck()) {
			if(card.getType() == CardType.ROOM)   { 
				room.addItem(card.getName());   
			} else if (card.getType() == CardType.PERSON) { 
				player.addItem(card.getName()); 
			} else if(card.getType() == CardType.WEAPON) { 
				weapon.addItem(card.getName()); 
			}
		}
		
		if (currRoom != null) {
			room.removeAllItems();
			room.addItem(currRoom.getName());
		}
		
		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(this);
		submitButton = new JButton("Submit");
		submitButton.addActionListener(this);

		panel.add(new JLabel("Room"));
		panel.add(room);
		
		panel.add(new JLabel("Person"));
		panel.add(player);
		
		panel.add(new JLabel("Weapon"));
		panel.add(weapon);
		
		panel.add(submitButton);
		panel.add(cancelButton);
		
		getContentPane().add(panel);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == cancelButton) {
			cards = null;
		}
		if (e.getSource() == submitButton) {
			Card roomCard = new Card(null);
			Card personCard = new Card(null);
			Card weaponCard = new Card(null);
			
			// We do this so we can add the cards to the array in the right order
			// It needs to be room player weapon
			for (Card card : board.getDeck()) {
				if (card.getName().equals(room.getSelectedItem().toString())) {
					roomCard = card;
				} else if (card.getName().equals(player.getSelectedItem().toString())) {
					personCard = card;
				} else if (card.getName().equals(weapon.getSelectedItem().toString())) {
					weaponCard= card;
				}
			}	
			cards.add(roomCard);
			cards.add(personCard);
			cards.add(weaponCard);
			
		}
		dispose();
		
	}
	
	public ArrayList<Card> run() {
		this.setVisible(true);
		return cards;
	}

}

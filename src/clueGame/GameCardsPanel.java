package clueGame;


import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class GameCardsPanel extends JPanel{

	private JPanel playersPanel;
	private JPanel roomsPanel;
	private JPanel weaponsPanel;
	private ArrayList<JTextField> playersHand = new ArrayList<JTextField>();
	private ArrayList<JTextField> playersSeen = new ArrayList<JTextField>();
	private ArrayList<JTextField> roomsHand = new ArrayList<JTextField>();
	private ArrayList<JTextField> roomsSeen = new ArrayList<JTextField>();
	private ArrayList<JTextField> weaponsHand = new ArrayList<JTextField>();
	private ArrayList<JTextField> weaponsSeen = new ArrayList<JTextField>();

	public GameCardsPanel() {
		super();
		setLayout(new GridLayout(3,0));
		setBorder(new TitledBorder (new EtchedBorder(), "Known Cards"));
		playersPanel = cardPanel();
		playersPanel.setBorder(new TitledBorder (new EtchedBorder(), "Players"));
		add(playersPanel);
		roomsPanel = cardPanel();
		roomsPanel.setBorder(new TitledBorder (new EtchedBorder(), "Rooms"));
		add(roomsPanel);
		weaponsPanel = cardPanel();
		weaponsPanel.setBorder(new TitledBorder (new EtchedBorder(), "Weapons"));
		add(weaponsPanel);
	}
	
	private JPanel cardPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		JLabel hand = new JLabel("In Hand:");
		JTextField fieldHand = new JTextField(20);
		fieldHand.setEditable(false);
		fieldHand.setText("None");
		
		JLabel seen = new JLabel("Seen:");
		JTextField fieldSeen = new JTextField(20);
		fieldSeen.setEditable(false);
		fieldSeen.setText("None");
		
		panel.add(hand);
		panel.add(fieldHand);
		panel.add(seen);
		panel.add(fieldSeen);
		
		return panel;
	}
	
	public void setPlayerHand(String[] playerHand) {
		playersHand.clear();
		for (String cardName : playerHand) {
			JTextField field = new JTextField(cardName);
			field.setEditable(false);
			playersHand.add(field);
		}
	}
	
	public void setPlayerSeen(String[] playerSeen) {
		playersSeen.clear();
		for (String cardName : playerSeen) {
			JTextField field = new JTextField(cardName);
			field.setEditable(false);
			playersSeen.add(field);
		}
	}
	
	
	public void updatePlayerPanel() {
		playersPanel.removeAll();
		
		
		JLabel hand = new JLabel("In Hand:");
		playersPanel.add(hand);
		if (playersHand.size() == 0) {
			playersPanel.add(empty());
		} else {
			for (JTextField field : playersHand) {
				playersPanel.add(field);
			}
		}
		
		
		JLabel seen = new JLabel("Seen:");
		playersPanel.add(seen);
		if (playersSeen.size() == 0) {
			playersPanel.add(empty());
		} else {
			for (JTextField field : playersSeen) {
				playersPanel.add(field);
			}
		}
	}
	
	
	public void setRoomHand(String[] roomHand) {
		roomsHand.clear();
		for (String cardName : roomHand) {
			JTextField field = new JTextField(cardName);
			field.setEditable(false);
			roomsHand.add(field);
		}
	}
	
	public void setRoomSeen(String[] roomSeen) {
		roomsSeen.clear();
		for (String cardName : roomSeen) {
			JTextField field = new JTextField(cardName);
			field.setEditable(false);
			roomsSeen.add(field);
		}
	}
	
	
	public void updateRoomPanel() {
		roomsPanel.removeAll();
		
		JLabel hand = new JLabel("In Hand:");
		roomsPanel.add(hand);
		if (roomsHand.size() == 0) {
			roomsPanel.add(empty());
		} else {
			for (JTextField field : roomsHand) {
				roomsPanel.add(field);
			}
		}
		
		
		JLabel seen = new JLabel("Seen:");
		roomsPanel.add(seen);
		if (roomsSeen.size() == 0) {
			roomsPanel.add(empty());
		} else {
			for (JTextField field : roomsSeen) {
				roomsPanel.add(field);
			}
		}
	}
	
	
	public void setWeaponHand(String[] weaponHand) {
		weaponsHand.clear();
		for (String cardName : weaponHand) {
			JTextField field = new JTextField(cardName);
			field.setEditable(false);
			weaponsHand.add(field);
		}
	}
	
	public void setWeaponSeen(String[] weaponSeen) {
		weaponsSeen.clear();
		for (String cardName : weaponSeen) {
			JTextField field = new JTextField(cardName);
			field.setEditable(false);
			weaponsSeen.add(field);
		}
	}
	
	
	public void updateWeaponPanel() {
		weaponsPanel.removeAll();
		
		JLabel hand = new JLabel("In Hand:");
		weaponsPanel.add(hand);
		if (weaponsHand.size() == 0) {
			weaponsPanel.add(empty());
		} else {
			for (JTextField field : weaponsHand) {
				weaponsPanel.add(field);
			}
		}
		
		
		JLabel seen = new JLabel("Seen:");
		weaponsPanel.add(seen);
		if (weaponsSeen.size() == 0) {
			weaponsPanel.add(empty());
		} else {
			for (JTextField field : weaponsSeen) {
				weaponsPanel.add(field);
			}
		}
	}
	
	public void updateAll() {
		updatePlayerPanel();
		updateRoomPanel();
		updateWeaponPanel();
	}
	
	public JTextField empty() {
		JTextField empty = new JTextField(20);
		empty.setEditable(false);
		empty.setText("None");
		return empty;
	}

	public static void main(String[] args) {
		GameCardsPanel panel = new GameCardsPanel();
		JFrame frame = new JFrame("Game Card Panel");
		frame.setContentPane(panel);
		frame.setSize(300, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		
		//Tests
		String[] playerHand = {"Lil Wayne", "Eminem"};
		String[] playersSeen = {"Kanye West"};
		String[] roomHand = {"Kitchen"};
		String[] roomsSeen = {"Bathroom", "Library", "Dining Room", "Hall", "Study", "Music Room"};
		String[] weaponHand = {};
		String[] weaponsSeen = {"Knife"};
		panel.setPlayerHand(playerHand);
		panel.setPlayerSeen(playersSeen);
		panel.setRoomHand(roomHand);
		panel.setRoomSeen(roomsSeen);
		panel.setWeaponHand(weaponHand);
		panel.setWeaponSeen(weaponsSeen);
		panel.updateAll();
		frame.revalidate();

	}

}

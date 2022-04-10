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
		
		JLabel label1 = new JLabel("In Hand:");
		JTextField field1 = new JTextField(20);
		field1.setEditable(false);
		field1.setText("None");
		
		JLabel label2 = new JLabel("Seen:");
		JTextField field2 = new JTextField(20);
		field2.setEditable(false);
		field2.setText("None");
		
		panel.add(label1);
		panel.add(field1);
		panel.add(label2);
		panel.add(field2);
		
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
		playersHand.clear();
		for (String cardName : playerSeen) {
			JTextField field = new JTextField(cardName);
			field.setEditable(false);
			playersSeen.add(field);
		}
	}
	
	
	
	public void updatePlayerPanel() {
		playersPanel.removeAll();
		JLabel label1 = new JLabel("In Hand:");
		playersPanel.add(label1);
		for (JTextField field : playersHand) {
			playersPanel.add(field);
		}
		JLabel label2 = new JLabel("Seen:");
		playersPanel.add(label2);
		for (JTextField field : playersSeen) {
			playersPanel.add(field);
		}
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
		panel.setPlayerHand(playerHand);
		panel.updatePlayerPanel();
		frame.revalidate();

	}

}

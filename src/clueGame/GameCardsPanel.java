package clueGame;


import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class GameCardsPanel extends JPanel{
	private JPanel peoplePanel;
	private JPanel roomsPanel;
	private JPanel weaponsPanel;
	private ArrayList<JTextField> playerHandField = new ArrayList<JTextField>();
	private ArrayList<JTextField> playerSeenField = new ArrayList<JTextField>();
	private ArrayList<JTextField> roomHandField = new ArrayList<JTextField>();
	private ArrayList<JTextField> roomSeenField = new ArrayList<JTextField>();
	private ArrayList<JTextField> weaponHandField = new ArrayList<JTextField>();
	private ArrayList<JTextField> weaponSeenField = new ArrayList<JTextField>();

	public GameCardsPanel() {
		super();
		setLayout(new GridLayout(3,0));
		setBorder(new TitledBorder (new EtchedBorder(), "Known Cards"));
		peoplePanel = cardPanel();
		peoplePanel.setBorder(new TitledBorder (new EtchedBorder(), "People"));
		add(peoplePanel);
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

	public static void main(String[] args) {
		GameCardsPanel panel = new GameCardsPanel();
		JFrame frame = new JFrame("Game Card Panel - CSCI306");
		frame.setContentPane(panel);
		frame.setSize(200, 750);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

	}

}

package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import clueGame.Player;
import clueGame.Board;
import clueGame.Card;
import clueGame.ComputerPlayer;
import clueGame.HumanPlayer;

class PlayersTests {
	
	private HumanPlayer man;
	private ComputerPlayer comp;
	private ComputerPlayer comp2;
	private static Board board;
	private ArrayList<Player> players;
	
	@BeforeEach
	void setUp() throws Exception {
		
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");		
		// Initialize will load config files 
		board.initialize();
		
		players = board.getPlayers();
		
		man = new HumanPlayer("You");
		comp = new ComputerPlayer("Harry");
		comp2 = new ComputerPlayer("Dick");
		
		
		
	}

	@Test
	void testDifferentColors() {
		for (Player player : players) {
			for (Player other : players) {
				if (player != other) {
					assertTrue(player.getColor() != other.getColor());
				}
			}
		}
	}
	
	@Test
	void testDifferentHands() {
		ArrayList<Card> hand;
		for (Player player : players) {
			hand = player.getHand();
			for (Card card : hand) {
				for (Player other : players) {
					if (player != other) {
						assertTrue(!(other.getHand().contains(card)));
					}
				}
			}
		}
	}

}

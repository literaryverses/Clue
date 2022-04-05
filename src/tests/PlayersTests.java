package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import clueGame.Player;
import clueGame.Solution;
import clueGame.Board;
import clueGame.Card;
import clueGame.ComputerPlayer;
import clueGame.HumanPlayer;

class PlayersTests {
	
	private HumanPlayer man;
	private ComputerPlayer comp;
	private ComputerPlayer comp2;
	private static Board board;
	private static ArrayList<Player> players;
	
	@BeforeAll
	static void setUp() throws Exception {
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");		
		// Initialize will load config files 
		board.initialize();
		
		players = board.getPlayers();
	}
	
	@Test
	void testSetup() {
		//makes sure we have the right number of players
		assertTrue(players.size() == 6);
		
		//makes sure we have the human player and all the computer players
		assertTrue(players.get(5) instanceof HumanPlayer);
		for (int i = 0; i < 5; i++) {
			assertTrue(players.get(i) instanceof ComputerPlayer);
		}
		
		//checks to see deck size is correct
		assertTrue(board.getDeck().size() == 21);
		
		//makes sure we have a solution
		Solution answer = board.getAnswer();
		assertTrue(answer.getPerson() != null);
		assertTrue(answer.getRoom() != null);
		assertTrue(answer.getWeapon() != null);
		
		//makes sure the players have cards
		for (Player player : players) {
			assertTrue(player.getHand().size() == 3);
		}
	}

	@Test
	void testDifferentColors() {
		//makes sure the players all have different colors
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
		//makes sure no one card in a players hand is in and other players hands
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

package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Player;
import clueGame.Solution;
import clueGame.Board;
import clueGame.Card;
import clueGame.ComputerPlayer;
import clueGame.HumanPlayer;

class GameSolutionTest {

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
	void testAccusation() {
		Solution tempAnswer = board.getAnswer();
		board.setAnswer("Eminem", "Knife", "Bathroom");
		Solution accusation = new Solution(board.pickCard("Eminem"), board.pickCard("Knife"), board.pickCard("Bathroom"));
		assertTrue(board.checkAccusation(accusation));
		accusation = new Solution(board.pickCard("Player"), board.pickCard("Knife"), board.pickCard("Bathroom"));
		assertTrue(!(board.checkAccusation(accusation)));
		accusation = new Solution(board.pickCard("Eminem"), board.pickCard("Ax"), board.pickCard("Bathroom"));
		assertTrue(!(board.checkAccusation(accusation)));
		accusation = new Solution(board.pickCard("Eminem"), board.pickCard("Knife"), board.pickCard("Kitchen"));
		assertTrue(!(board.checkAccusation(accusation)));
		board.setAnswer(tempAnswer.getPerson().getName(), tempAnswer.getWeapon().getName(), tempAnswer.getRoom().getName());
	}
	
	@Test
	void testDisprove() {
		String name = "Eminem";
		board.getPlayer(name).overrideHand(board.pickCard("Knife"), board.pickCard("Ax"), board.pickCard("Kitchen"));
		Solution suggestion = new Solution(board.pickCard("Eminem"), board.pickCard("Knife"), board.pickCard("Bathroom"));
		assertTrue(board.pickCard("Knife").equals(board.getPlayer("Eminem").disproveSuggestion(suggestion)));
	}
	
	@Test
	void testHandle() {
		
	}

}

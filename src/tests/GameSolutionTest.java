package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.*;

class GameSolutionTest {
	private static Board board;
	private static Card a = new Card("Eminem");
	private static Card b = new Card("Knife");
	private static Card c = new Card("Bathroom");
	private static Card d = new Card("Finn");
	private static Card e = new Card("Nuke");
	private static Card f = new Card("Cat realm");
	private Player p = new ComputerPlayer("Steve");


	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");		
		// Initialize will load config files 
		board.loadSetupConfig();
		board.loadLayoutConfig();
		b.setType("WEAPON");
		c.setType("ROOM");
		a.setType("PERSON");
		d.setType("PERSON");
		e.setType("WEAPON");
		f.setType("ROOM");
		board.setAnswer(a);
		board.setAnswer(b);
		board.setAnswer(c);
	}

	@Test
	void testAccusation() {
		//right accusation
		Solution right = new Solution();
		right.add(a);
		right.add(b);
		right.add(c);
		assertTrue(board.checkAccusation(right));
		
		//wrong person
		Solution wrongP = new Solution();
		wrongP.add(d);
		wrongP.add(b);
		wrongP.add(c);
		assertFalse(board.checkAccusation(wrongP));
		
		//wrong weapon
		Solution wrongW = new Solution();
		wrongW.add(a);
		wrongW.add(e);
		wrongW.add(c);
		assertFalse(board.checkAccusation(wrongW));
		
		//wrong room
		Solution wrongR = new Solution();
		wrongR.add(a);
		wrongR.add(b);
		wrongR.add(f);
		assertFalse(board.checkAccusation(wrongR));
	}
	
	@Test
	void testDisprove() {		
		p.updateHand(a);
		p.updateHand(b);
		p.updateHand(c);
		
		// nothing matches
		Solution suggestion1 = new Solution();
		suggestion1.add(d);
		suggestion1.add(e);
		suggestion1.add(f);
		assertTrue(p.disproveSuggestion(suggestion1)==null);
		
		
		// one matches
		Solution suggestion2 = new Solution();
		suggestion2.add(a);
		suggestion2.add(e);
		suggestion2.add(f);
		assertTrue(p.disproveSuggestion(suggestion2).equals(a));
		
		
		// two matches
		Solution suggestion3 = new Solution();
		suggestion3.add(a);
		suggestion3.add(b);
		suggestion3.add(f);
		Card z = p.disproveSuggestion(suggestion3);
		assertTrue(z.equals(a)||z.equals(b));
	}
	
	@Test
	void testHandle() {
		Solution suggest = new Solution();
		suggest.add(a);
		suggest.add(b);
		suggest.add(c);
		
		ArrayList<Card> tempDeck = board.getDeck();
		tempDeck.remove(10);
		tempDeck.remove(5);
		tempDeck.remove(15);
		board.deal();
		
		// assertion no one can dispute
		assertTrue(null == board.handleSuggestion(suggest));

		
		// assertion only accusing player can dispute
		ArrayList<Player> players = board.getPlayers();
		for (Player p : players) {
			if (p.getName().equals("Eminem")) {
				p.updateHand(a);
			}
		}
		assertTrue(null == board.handleSuggestion(suggest));
		
		// assertion human can dispute
		for (Player p: players) {
			if (p.getName().equals("Player")) {
				p.updateHand(b);
			}
		}
		assertTrue(b.equals(board.handleSuggestion(suggest)));
		
		// assertion two players can dispute
		for (Player p: players) {
			if (p.getName().equals("MF Doom")) {
				p.updateHand(c);
			}
		}
		// MF Doom comes before player
		assertTrue(c.equals(board.handleSuggestion(suggest)));
	}

}

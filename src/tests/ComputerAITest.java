package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import clueGame.*;

class ComputerAITest {
	private static Board board;
	private static ComputerPlayer cp = new ComputerPlayer("Steve");
	private static Card a = new Card("Steve");
	private static Card b = new Card("Knife");
	private static Card c = new Card("Gaming Room");
	private static Card d = new Card("Finn");
	private static Card e = new Card("Nuke");
	private static Card f = new Card("Library");
	private static Card g = new Card("Dante");
	private static Card h = new Card("Bazooka");
	private static Card i = new Card("Bathroom");
	private static ArrayList<Card> deck = new ArrayList<Card>();
	private static Map<Character,Room> roomMap;
	private static BoardCell[][] grid;


	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		board.loadSetupConfig();
		board.loadLayoutConfig();
		a.setType("PERSON"); b.setType("WEAPON"); c.setType("ROOM");
		d.setType("PERSON"); e.setType("WEAPON"); f.setType("ROOM");
		g.setType("PERSON"); h.setType("WEAPON"); i.setType("ROOM");
		deck.add(a); deck.add(b); deck.add(c);
		deck.add(d); deck.add(e); deck.add(f);
		deck.add(g); deck.add(h); deck.add(i);
		roomMap = board.getRoomMap();
		grid = board.getGrid();
	}

	@Test
	void testComputerSuggest() {
		cp.seeCard(a);
		cp.seeCard(b);
		
		Solution suggest1 = cp.createSuggestion(deck);
		String weapon = suggest1.getWeapon().getName();
		String person = suggest1.getPerson().getName();
		
		// If multiple weapons not seen, one of them is randomly selected
		assertTrue(person.equals("Finn")||person.equals("Dante"));
		// If multiple persons not seen, one of them is randomly selected
		assertTrue(weapon.equals("Nuke")||weapon.equals("Bazooka"));
		
		// Room matches current location
		cp.setPlace(11, 14); // in gaming room
		cp.setRoomPlayerIn(roomMap, grid);
		Solution suggest2 = cp.createSuggestion(deck);
		assertTrue(suggest2.getRoom().getName().equals("Gaming Room"));
				
		cp.updateHand(c);
		cp.updateHand(d);
		cp.updateHand(e);
		
		Solution suggest3 = cp.createSuggestion(deck);
		// If only one person not seen, it's selected  
		assertTrue(suggest3.getPerson().getName().equals("Dante"));
		// If only one weapon not seen, it's selected
		assertTrue(suggest3.getWeapon().getName().equals("Bazooka"));
	}
	
	@Test
	void testSelectTargets() {
		cp.seeCard(a);
		cp.seeCard(b);
		cp.updateHand(c);
		cp.updateHand(d);
		cp.updateHand(e);
		
		Set<BoardCell> t = new HashSet<BoardCell>();
		t.add(board.getCell(6, 2));
		t.add(board.getCell(16, 0));
		t.add(board.getCell(17, 3));
		t.add(board.getCell(10, 12));
		
		// if no rooms in list, select randomly
		BoardCell bc = cp.selectTarget(t, roomMap, deck);
		assertTrue(bc.getInitial()=='W'); //FIXME
		
		t.add(board.getCell(11, 14)); // gaming room
		
		// if room in list that has been seen, each target (including room) selected randomly		
		bc = cp.selectTarget(t, roomMap, deck);
		assertTrue(t.contains(bc)); // FIXME
		
		// if room in list that has not been seen, select it
		t.add(board.getCell(2, 16)); // library
		bc = cp.selectTarget(t, roomMap, deck);
		assertTrue(bc.getInitial()=='L');
	}
}

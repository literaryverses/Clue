package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.BoardCell;

class BoardAdjTargetTest {
	
	private static Board board;

	@BeforeEach
	void setUp() throws Exception {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");		
		// Initialize will load config files 
		board.initialize();
	}

	@Test
	void testWalkwayAdjacency() {
		//tests a walkway that is connected on all sides
		Set<BoardCell> testList = board.getAdjList(9, 6);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(10, 6)));
		assertTrue(testList.contains(board.getCell(8, 6)));
		assertTrue(testList.contains(board.getCell(9, 7)));
		assertTrue(testList.contains(board.getCell(9, 5)));
		
		//tests a walkway in a corner of room and edge
		testList = board.getAdjList(7, 20);
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCell(6, 20)));
		assertTrue(testList.contains(board.getCell(7, 19)));
		
		//tests a walkway at a door
		testList = board.getAdjList(4, 12);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(5, 12)));
		assertTrue(testList.contains(board.getCell(3, 12)));
		assertTrue(testList.contains(board.getCell(4, 11)));
		assertTrue(testList.contains(board.getCell(2, 16)));
	}
	
	@Test
	void testRoomAdjacency() {
		//tests room center
		Set<BoardCell> testList = board.getAdjList(21, 12);
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCell(23, 8)));
		assertTrue(testList.contains(board.getCell(19, 16)));
		
		//tests normal room
		testList = board.getAdjList(5, 5);
		assertEquals(0, testList.size());
		
		//tests secret passage
		testList = board.getAdjList(2, 2);
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCell(6, 5)));
		assertTrue(testList.contains(board.getCell(10, 20)));
		
	}
	
	
	@Test
	public void testTargetsInWalkways() {
		// test a roll of 1
		board.calcTargets(board.getCell(15, 12), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCell(15, 11)));
		assertTrue(targets.contains(board.getCell(16, 12)));	
		assertTrue(targets.contains(board.getCell(14, 12)));	
		
		// test a roll of 2
		board.calcTargets(board.getCell(15, 12), 2);
		targets= board.getTargets();
		assertEquals(5, targets.size());
		assertTrue(targets.contains(board.getCell(17, 12)));	
		assertTrue(targets.contains(board.getCell(16, 13)));	
		assertTrue(targets.contains(board.getCell(13, 12)));	
		assertTrue(targets.contains(board.getCell(14, 11)));	
		assertTrue(targets.contains(board.getCell(16, 11)));	
		
		// test a roll of 4
		board.calcTargets(board.getCell(15, 12), 4);
		targets= board.getTargets();
		assertEquals(12, targets.size());
		assertTrue(targets.contains(board.getCell(17, 12)));	
		assertTrue(targets.contains(board.getCell(16, 13)));	
		assertTrue(targets.contains(board.getCell(13, 12)));	
		assertTrue(targets.contains(board.getCell(14, 11)));	
		assertTrue(targets.contains(board.getCell(16, 11)));	
		assertTrue(targets.contains(board.getCell(11, 14)));	
		assertTrue(targets.contains(board.getCell(12, 11)));	
		assertTrue(targets.contains(board.getCell(11, 12)));	
		assertTrue(targets.contains(board.getCell(16, 9)));	
		assertTrue(targets.contains(board.getCell(16, 15)));	
		assertTrue(targets.contains(board.getCell(17, 10)));	
		assertTrue(targets.contains(board.getCell(17, 14)));	
	}
	
	@Test
	public void testTargetInDiningRoom() {
		board.calcTargets(board.getCell(21, 12), 1);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCell(23, 8)));	
		assertTrue(targets.contains(board.getCell(19, 16)));
		
		board.calcTargets(board.getCell(21, 12), 3);
		targets = board.getTargets();
		assertEquals(5, targets.size());
		assertTrue(targets.contains(board.getCell(21, 8)));
		assertTrue(targets.contains(board.getCell(22, 7)));
		assertTrue(targets.contains(board.getCell(17, 16)));
		assertTrue(targets.contains(board.getCell(18, 17)));
		assertTrue(targets.contains(board.getCell(19, 18)));
		
		board.calcTargets(board.getCell(21, 12), 5);
		targets = board.getTargets();
		assertEquals(13, targets.size());
		assertTrue(targets.contains(board.getCell(21, 8)));
		assertTrue(targets.contains(board.getCell(19, 8)));
		assertTrue(targets.contains(board.getCell(22, 7)));
		assertTrue(targets.contains(board.getCell(21, 3)));
		assertTrue(targets.contains(board.getCell(17, 16)));
		assertTrue(targets.contains(board.getCell(17, 14)));
		assertTrue(targets.contains(board.getCell(16, 19)));
		assertTrue(targets.contains(board.getCell(18, 17)));
		assertTrue(targets.contains(board.getCell(19, 18)));
	}
	
	@Test
	public void testTargetInKitchen() {
		board.calcTargets(board.getCell(21, 3), 1);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCell(21, 7)));	
		assertTrue(targets.contains(board.getCell(2, 16)));
		
		board.calcTargets(board.getCell(21, 3), 2);
		targets = board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCell(2, 16)));
		assertTrue(targets.contains(board.getCell(22, 7)));	
		assertTrue(targets.contains(board.getCell(21, 8)));	
		assertTrue(targets.contains(board.getCell(20, 7)));	
	}
	
	@Test
	public void testOccupied() {
		//test occupied walkway
		board.getCell(6, 19).setOccupied(true);
		board.calcTargets(board.getCell(6, 20), 2);
		board.getCell(6, 19).setOccupied(false);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(1, targets.size());
		assertTrue(targets.contains(board.getCell(7, 19)));	
		
		//test occupied room
		board.getCell(10, 20).setOccupied(true);
		board.calcTargets(board.getCell(10, 18), 1);
		board.getCell(10, 20).setOccupied(false);
		targets = board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCell(10, 20)));
		assertTrue(targets.contains(board.getCell(10, 17)));	
		assertTrue(targets.contains(board.getCell(9, 18)));	
		assertTrue(targets.contains(board.getCell(11, 18)));	
		
		//test occupied doorway
		board.getCell(23, 8).setOccupied(true);
		board.calcTargets(board.getCell(21, 12), 2);
		board.getCell(23, 8).setOccupied(false);
		targets = board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCell(18, 16)));
		assertTrue(targets.contains(board.getCell(19, 17)));	
	}
	

}

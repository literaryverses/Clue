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
	void testWalkways() {
		//tests a walkway that is connected on all sides
		Set<BoardCell> testList = board.getAdjList(9, 6);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(10, 6)));
		assertTrue(testList.contains(board.getCell(8, 6)));
		assertTrue(testList.contains(board.getCell(9, 7)));
		assertTrue(testList.contains(board.getCell(9, 5)));
		
		//tests a walkway in a corner
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

}

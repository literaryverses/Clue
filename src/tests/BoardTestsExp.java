// Jun Lee
// Matthew Brause

package tests;


import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import experiment.TestBoard;
import experiment.TestBoardCell;

public class BoardTestsExp {
	TestBoard board;
	
	@BeforeEach
	public void setUp() throws Exception {
		board = new TestBoard();
	}

	@Test
	public void testAdjacency() {
		// Top left corner
		TestBoardCell cell = board.getCell(0, 0);
		Set<TestBoardCell> testList = cell.getAdjList();
		Assert.assertTrue(testList.contains(board.getCell(1, 0)));
		Assert.assertTrue(testList.contains(board.getCell(0, 1)));
		Assert.assertEquals(2,  testList.size());
		
		// Bottom right corner
		cell = board.getCell(3, 3);
		testList = cell.getAdjList();
		Assert.assertTrue(testList.contains(board.getCell(3, 2)));
		Assert.assertTrue(testList.contains(board.getCell(2, 3)));
		Assert.assertEquals(2, testList.size());
		
		// Right edge
		cell = board.getCell(1, 3);
		testList = cell.getAdjList();
		Assert.assertTrue(testList.contains(board.getCell(0, 3)));
		Assert.assertTrue(testList.contains(board.getCell(2, 3)));
		Assert.assertTrue(testList.contains(board.getCell(1, 2)));
		Assert.assertEquals(3, testList.size());
		
		// Left edge
		cell = board.getCell(3, 0);
		testList = cell.getAdjList();
		Assert.assertTrue(testList.contains(board.getCell(2, 0)));
		Assert.assertTrue(testList.contains(board.getCell(3, 1)));
		Assert.assertEquals(2, testList.size());
	}
	
	
	@Test
	public void testTargetCorner() {
		TestBoardCell cell = board.getCell(0, 0);
		Set<TestBoardCell> targets = board.getTargets();
		board.calcTargets(cell, 3);
		Assert.assertEquals(6, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(3, 0)));
		Assert.assertTrue(targets.contains(board.getCell(2, 1)));
		Assert.assertTrue(targets.contains(board.getCell(0, 1)));
		Assert.assertTrue(targets.contains(board.getCell(1, 2)));
		Assert.assertTrue(targets.contains(board.getCell(0, 3)));
		Assert.assertTrue(targets.contains(board.getCell(1, 0)));
		}
	
	@Test
	public void testTargetCenter() {
		TestBoardCell cell = board.getCell(1, 1);
		Set<TestBoardCell> targets = board.getTargets();
		board.calcTargets(cell, 2);
		Assert.assertEquals(4, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(0, 1)));
		Assert.assertTrue(targets.contains(board.getCell(2, 0)));
		Assert.assertTrue(targets.contains(board.getCell(2, 2)));
		Assert.assertTrue(targets.contains(board.getCell(0, 2)));
		}
	
	@Test
	public void testRoomSurround() {
		// Set up occupied cells
		board.getCell(2, 1).setIsRoom(true);
		board.getCell(2, 3).setIsRoom(true);
		board.getCell(1, 3).setIsRoom(true);
		board.getCell(3, 3).setIsRoom(true);
		TestBoardCell cell = board.getCell(2, 2);
		board.calcTargets(cell, 2);
		Set<TestBoardCell> targets = board.getTargets();
		Assert.assertEquals(4, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(2, 1)));
		Assert.assertTrue(targets.contains(board.getCell(2, 3)));
		Assert.assertTrue(targets.contains(board.getCell(1, 3)));
		Assert.assertTrue(targets.contains(board.getCell(3, 3)));
		}
	
	@Test
	public void testTargetWall() {
		// Set up occupied cells
		board.getCell(0, 1).setIsRoom(true);
		board.getCell(1, 1).setIsRoom(true);
		board.getCell(2, 1).setIsRoom(true);
		board.getCell(3, 1).setIsRoom(true);
		TestBoardCell cell = board.getCell(3, 3);
		board.calcTargets(cell, 4);
		Set<TestBoardCell> targets = board.getTargets();
		Assert.assertEquals(3, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(0, 2)));
		Assert.assertTrue(targets.contains(board.getCell(2, 2)));
		Assert.assertTrue(targets.contains(board.getCell(1, 3)));
		}
	
	@Test
	public void testTargetMixed() {
		// Set up occupied cells
		board.getCell(0, 2).setOccupied(true);
		board.getCell(1, 2).setIsRoom(true);
		TestBoardCell cell = board.getCell(0, 3);
		board.calcTargets(cell, 3);
		Set<TestBoardCell> targets = board.getTargets();

		Assert.assertEquals(3, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(1, 2)));
		Assert.assertTrue(targets.contains(board.getCell(2, 3)));
		Assert.assertTrue(targets.contains(board.getCell(3, 3)));
		}
	
	
	
	

}

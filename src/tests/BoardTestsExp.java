package tests;


import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import experiment.TestBoard;
import experiment.TestBoardCell;

public class BoardTestsExp {
	TestBoard boardExp;
	
	@BeforeEach
	public void setUp() throws Exception {
		boardExp = new TestBoard();
	}

	@Test
	public void testAdjacency() {
		// Top left corner
		TestBoardCell cell = boardExp.getCell(0, 0);
		Set<TestBoardCell> testList = cell.getAdjList();
		Assert.assertTrue(testList.contains(boardExp.getCell(1, 0)));
		Assert.assertTrue(testList.contains(boardExp.getCell(0, 1)));
		Assert.assertEquals(2,  testList.size());
		
		// Bottom right corner
		cell = boardExp.getCell(3, 3);
		testList = cell.getAdjList();
		Assert.assertTrue(testList.contains(boardExp.getCell(3, 2)));
		Assert.assertTrue(testList.contains(boardExp.getCell(2, 3)));
		Assert.assertEquals(2, testList.size());
		
		// Right edge
		cell = boardExp.getCell(1, 3);
		testList = cell.getAdjList();
		Assert.assertTrue(testList.contains(boardExp.getCell(0, 3)));
		Assert.assertTrue(testList.contains(boardExp.getCell(2, 3)));
		Assert.assertTrue(testList.contains(boardExp.getCell(1, 2)));
		Assert.assertEquals(3, testList.size());
		
		// Left edge
		cell = boardExp.getCell(3, 0);
		testList = cell.getAdjList();
		Assert.assertTrue(testList.contains(boardExp.getCell(2, 0)));
		Assert.assertTrue(testList.contains(boardExp.getCell(3, 1)));
		Assert.assertEquals(2, testList.size());
	}
	
	
	@Test
	public void testTargetCorner() {
		TestBoardCell cell = boardExp.getCell(0, 0);
		Set<TestBoardCell> targets = boardExp.getTargets();
		boardExp.calcTargets(cell, 3);
		Assert.assertEquals(6, targets.size());
		Assert.assertTrue(targets.contains(boardExp.getCell(3, 0)));
		Assert.assertTrue(targets.contains(boardExp.getCell(2, 1)));
		Assert.assertTrue(targets.contains(boardExp.getCell(0, 1)));
		Assert.assertTrue(targets.contains(boardExp.getCell(1, 2)));
		Assert.assertTrue(targets.contains(boardExp.getCell(0, 3)));
		Assert.assertTrue(targets.contains(boardExp.getCell(1, 0)));
		}
	
	@Test
	public void testTargetCenter() {
		TestBoardCell cell = boardExp.getCell(1, 1);
		Set<TestBoardCell> targets = boardExp.getTargets();
		boardExp.calcTargets(cell, 2);
		Assert.assertEquals(4, targets.size());
		Assert.assertTrue(targets.contains(boardExp.getCell(0, 1)));
		Assert.assertTrue(targets.contains(boardExp.getCell(2, 0)));
		Assert.assertTrue(targets.contains(boardExp.getCell(2, 2)));
		Assert.assertTrue(targets.contains(boardExp.getCell(0, 2)));
		}
	
	@Test
	public void testRoomSurround() {
		// Set up occupied cells
		boardExp.getCell(2, 1).setIsRoom(true);
		boardExp.getCell(2, 3).setIsRoom(true);
		boardExp.getCell(1, 3).setIsRoom(true);
		boardExp.getCell(3, 3).setIsRoom(true);
		TestBoardCell cell = boardExp.getCell(2, 2);
		boardExp.calcTargets(cell, 2);
		Set<TestBoardCell> targets = boardExp.getTargets();
		Assert.assertEquals(4, targets.size());
		Assert.assertTrue(targets.contains(boardExp.getCell(2, 1)));
		Assert.assertTrue(targets.contains(boardExp.getCell(2, 3)));
		Assert.assertTrue(targets.contains(boardExp.getCell(1, 3)));
		Assert.assertTrue(targets.contains(boardExp.getCell(3, 3)));
		}
	
	@Test
	public void testTargetWall() {
		// Set up occupied cells
		boardExp.getCell(0, 1).setIsRoom(true);
		boardExp.getCell(1, 1).setIsRoom(true);
		boardExp.getCell(2, 1).setIsRoom(true);
		boardExp.getCell(3, 1).setIsRoom(true);
		TestBoardCell cell = boardExp.getCell(3, 3);
		boardExp.calcTargets(cell, 4);
		Set<TestBoardCell> targets = boardExp.getTargets();
		Assert.assertEquals(3, targets.size());
		Assert.assertTrue(targets.contains(boardExp.getCell(0, 2)));
		Assert.assertTrue(targets.contains(boardExp.getCell(2, 2)));
		Assert.assertTrue(targets.contains(boardExp.getCell(1, 3)));
		}
	
	@Test
	public void testTargetMixed() {
		// Set up occupied cells
		boardExp.getCell(0, 2).setOccupied(true);
		boardExp.getCell(1, 2).setIsRoom(true);
		TestBoardCell cell = boardExp.getCell(0, 3);
		boardExp.calcTargets(cell, 3);
		Set<TestBoardCell> targets = boardExp.getTargets();

		Assert.assertEquals(3, targets.size());
		Assert.assertTrue(targets.contains(boardExp.getCell(1, 2)));
		Assert.assertTrue(targets.contains(boardExp.getCell(2, 3)));
		Assert.assertTrue(targets.contains(boardExp.getCell(3, 3)));
		}
	
	
	
	

}

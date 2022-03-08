// Matt Brause
// Jun Lee

package tests;

/*
 * This program tests that config files are loaded properly.
 */

// Doing a static import allows me to write assertEquals rather than
// Assert.assertEquals
import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.BoardCell;
import clueGame.DoorDirection;
import clueGame.Room;

public class FileInitTests {
	// Constants that I will use to test whether the file was loaded correctly
	public static final int NUM_ROWS = 25;
	public static final int NUM_COLUMNS = 21;

	// NOTE: I made Board static because I only want to set it up one
	// time (using @BeforeAll), no need to do setup before each test.
	private static Board board;

	@BeforeAll
	public static void setUp() throws BadConfigFormatException {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		// Initialize will load BOTH config files
		board.initialize();
	}

	@Test
	public void testRoomLabels() {
		// To ensure data is correctly loaded, test retrieving all the rooms
		assertEquals("Bedroom", board.getRoom('B').getName() );
		assertEquals("Library", board.getRoom('L').getName() );
		assertEquals("Music Room", board.getRoom('M').getName() );
		assertEquals("Dining Room", board.getRoom('D').getName() );
		assertEquals("Walkway", board.getRoom('W').getName() );
	}

	// Tests if we have the same number of rows and columns
	@Test
	public void testBoardDimensions() {
		assertEquals(NUM_ROWS, board.getNumRows());
		assertEquals(NUM_COLUMNS, board.getNumColumns());
	}

	// Test a doorway in each direction (RIGHT/LEFT/UP/DOWN), plus cells from other rooms that are not doorways
	@Test
	public void fourDoorDirections() {
		BoardCell cell = board.getCell(8, 11);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.LEFT, cell.getDoorDirection());
		cell = board.getCell(6, 5);
		assertTrue(cell.isDoorway());
		//System.out.println(cell.isDoorway());
		//System.out.println(cell.getDoorDirection());
		assertEquals(DoorDirection.UP, cell.getDoorDirection());
		cell = board.getCell(1, 6);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.RIGHT, cell.getDoorDirection());
		cell = board.getCell(7, 0);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.DOWN, cell.getDoorDirection());
		// Test cells that are not doors
		cell = board.getCell(4, 11); //walkway
		assertFalse(cell.isDoorway());
		cell = board.getCell(4, 2); //bedroom
		assertFalse(cell.isDoorway());
		cell = board.getCell(13, 3); //music room		
		assertFalse(cell.isDoorway());
		cell = board.getCell(22, 4); //kitchen		
		assertFalse(cell.isDoorway());
		cell = board.getCell(5, 8); //hall		
		assertFalse(cell.isDoorway());
		cell = board.getCell(2, 14); //library		
		assertFalse(cell.isDoorway());
		cell = board.getCell(13, 14); //gaming room		
		assertFalse(cell.isDoorway());
		cell = board.getCell(11, 20); //bathrom		
		assertFalse(cell.isDoorway());
		cell = board.getCell(22, 4); //kitchen		
		assertFalse(cell.isDoorway());
		cell = board.getCell(16, 19); //study		
		assertFalse(cell.isDoorway());
		cell = board.getCell(15, 8); //unused		
		assertFalse(cell.isDoorway());
	}
	

	// Test that we have the correct number of doors
	@Test
	public void testNumberOfDoorways() {
		int numDoors = 0;
		for (int row = 0; row < board.getNumRows(); row++)
			for (int col = 0; col < board.getNumColumns(); col++) {
				BoardCell cell = board.getCell(row, col);
				if (cell.isDoorway())
					numDoors++;
			}
		Assert.assertEquals(14, numDoors);
	}

	// Test a few room cells to ensure the room labels are correct.
	@Test
	public void testRooms() {
		// just test a standard room location
		BoardCell cell = board.getCell(2, 3);
		Room room = board.getRoom( cell ) ;
		assertTrue( room != null );
		assertEquals( room.getName(), "Bedroom" ) ;
		assertFalse( cell.isLabel() );
		assertFalse( cell.isRoomCenter() ) ;
		assertFalse( cell.isDoorway()) ;

		// this is a label cell to test
		cell = board.getCell(11, 2);
		room = board.getRoom( cell ) ;
		assertTrue( room != null );
		assertEquals( room.getName(), "Music Room" ) ;
		assertTrue( cell.isLabel() );
		assertTrue( room.getLabelCell() == cell );
		
		// this is a room center cell to test
		cell = board.getCell(21, 3);
		room = board.getRoom( cell ) ;
		assertTrue( room != null );
		assertEquals( room.getName(), "Kitchen" ) ;
		assertTrue( cell.isRoomCenter() );
		assertTrue( room.getCenterCell() == cell );
		
		// this is a secret passage test
		cell = board.getCell(5, 0);
		room = board.getRoom( cell ) ;
		assertTrue( room != null );
		assertEquals( room.getName(), "Bedroom" ) ;
		assertTrue( cell.getSecretPassage() == 'P' );
		
		// test a walkway
		cell = board.getCell(8, 11);
		room = board.getRoom( cell ) ;
		assertTrue( room != null );
		assertEquals( room.getName(), "Walkway" ) ;
		assertFalse( cell.isRoomCenter() );
		assertFalse( cell.isLabel() );
		
		// test a closet
		cell = board.getCell(12, 8);
		room = board.getRoom( cell ) ;
		assertTrue( room != null );
		assertEquals( room.getName(), "Unused" ) ;
		assertFalse( cell.isRoomCenter() );
		assertFalse( cell.isLabel() );
		
	}

}

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
	public static final int NUM_ROWS = 24;
	public static final int NUM_COLUMNS = 20;

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
		BoardCell cell = new BoardCell(0,0);
		cell.setInitial('D');
		assertEquals("Dining Room", board.getRoom(cell) );
		cell.setInitial('B');
		assertEquals("Bedroom", board.getRoom(cell).getName() );
		cell.setInitial('L');
		assertEquals("Library", board.getRoom(cell).getName() );
		cell.setInitial('M');
		assertEquals("Music Room", board.getRoom(cell).getName() );
		cell.setInitial('W');
		assertEquals("Walkway", board.getRoom(cell).getName() );
		cell.setInitial('K');
		assertEquals("Kitchen", board.getRoom(cell).getName() );
		cell.setInitial('P');
		assertEquals("Bathroom", board.getRoom(cell).getName() );
		cell.setInitial('G');
		assertEquals("Gaming Room", board.getRoom(cell).getName() );
		cell.setInitial('S');
		assertEquals("Study", board.getRoom(cell).getName() );
		cell.setInitial('H');
		assertEquals("Hall", board.getRoom(cell).getName() );
		cell.setInitial('X');
		assertEquals("Unused", board.getRoom(cell).getName() );
	}

	// Tests if we have the same number of rows and columns
	@Test
	public void testBoardDimensions() {
		assertEquals(NUM_ROWS, board.getROWS());
		assertEquals(NUM_COLUMNS, board.getCOLS());
	}

	// Test a doorway in each direction (RIGHT/LEFT/UP/DOWN), plus cells from other rooms that are not doorways
	@Test
	public void fourDoorDirections() {
		BoardCell cell = board.getCell(11, 8);
		assertTrue(cell.getIsDoor());
		assertEquals(DoorDirection.LEFT, cell.getDoorDirection());
		cell = board.getCell(5, 8);
		assertTrue(cell.getIsDoor());
		assertEquals(DoorDirection.UP, cell.getDoorDirection());
		cell = board.getCell(6, 1);
		assertTrue(cell.getIsDoor());
		assertEquals(DoorDirection.RIGHT, cell.getDoorDirection());
		cell = board.getCell(0, 7);
		assertTrue(cell.getIsDoor());
		assertEquals(DoorDirection.DOWN, cell.getDoorDirection());
		// Test cells that are not doors
		cell = board.getCell(11, 4); //walkway
		assertFalse(cell.getIsDoor());
		cell = board.getCell(2, 4); //bedroom
		assertFalse(cell.getIsDoor());
		cell = board.getCell(3, 13); //music room		
		assertFalse(cell.getIsDoor());
		cell = board.getCell(4, 22); //kitchen		
		assertFalse(cell.getIsDoor());
		cell = board.getCell(8, 5); //hall		
		assertFalse(cell.getIsDoor());
		cell = board.getCell(14, 2); //library		
		assertFalse(cell.getIsDoor());
		cell = board.getCell(14, 13); //gaming room		
		assertFalse(cell.getIsDoor());
		cell = board.getCell(20, 11); //bathrom		
		assertFalse(cell.getIsDoor());
		cell = board.getCell(4, 22); //kitchen		
		assertFalse(cell.getIsDoor());
		cell = board.getCell(19, 16); //study		
		assertFalse(cell.getIsDoor());
		cell = board.getCell(8, 15); //unused		
		assertFalse(cell.getIsDoor());
	}
	

	// Test that we have the correct number of doors
	@Test
	public void testNumberOfDoorways() {
		int numDoors = 0;
		for (int row = 0; row < board.getROWS(); row++)
			for (int col = 0; col < board.getCOLS(); col++) {
				BoardCell cell = board.getCell(col, row);
				if (cell.getIsDoor())
					numDoors++;
			}
		Assert.assertEquals(14, numDoors);
	}

	// Test a few room cells to ensure the room labels are correct.
	@Test
	public void testRooms() {
		// just test a standard room location
		BoardCell cell = board.getCell( 3, 2);
		Room room = board.getRoom( cell ) ;
		assertTrue( room != null );
		assertEquals( room.getName(), "Bedroom" ) ;
		assertFalse( cell.getIsLabel() );
		assertFalse( cell.getIsCenter() ) ;
		assertFalse( cell.getIsDoor()) ;

		// this is a label cell to test
		cell = board.getCell(2, 11);
		room = board.getRoom( cell ) ;
		assertTrue( room != null );
		assertEquals( room.getName(), "Music room" ) ;
		assertTrue( cell.getIsLabel() );
		assertTrue( room.getLabelCell() == cell );
		
		// this is a room center cell to test
		cell = board.getCell(3, 21);
		room = board.getRoom( cell ) ;
		assertTrue( room != null );
		assertEquals( room.getName(), "Kitchen" ) ;
		assertTrue( cell.getIsCenter() );
		assertTrue( room.getCenterCell() == cell );
		
		// this is a secret passage test
		cell = board.getCell(0, 5);
		room = board.getRoom( cell ) ;
		assertTrue( room != null );
		assertEquals( room.getName(), "Bedroom" ) ;
		assertTrue( cell.getSecretPassage() == 'P' );
		
		// test a walkway
		cell = board.getCell(10, 8);
		room = board.getRoom( cell ) ;
		assertTrue( room != null );
		assertEquals( room.getName(), "Walkway" ) ;
		assertFalse( cell.getIsCenter() );
		assertFalse( cell.getIsLabel() );
		
		// test a closet
		cell = board.getCell(8, 12);
		room = board.getRoom( cell ) ;
		assertTrue( room != null );
		assertEquals( room.getName(), "Unused" ) ;
		assertFalse( cell.getIsCenter() );
		assertFalse( cell.getIsLabel() );
		
	}

}

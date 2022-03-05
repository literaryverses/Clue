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

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.DoorDirection;
import clueGame.Room;

public class FileInitTests {
	// Constants that I will use to test whether the file was loaded correctly
	public static final int LEGEND_SIZE = 11;
	public static final int NUM_ROWS = 20;
	public static final int NUM_COLUMNS = 24;

	// NOTE: I made Board static because I only want to set it up one
	// time (using @BeforeAll), no need to do setup before each test.
	private static Board board;

	@BeforeAll
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		// Initialize will load BOTH config files
		board.initialize();
	}

	@Test
	public void testRoomLabels() {
		// To ensure data is correctly loaded, test retrieving a few rooms
		// from the hash, including the first and last in the file and a few others
		assertEquals("Dining Room", board.getRoom('D').getName() );
		assertEquals("Bedroom", board.getRoom('B').getName() );
		assertEquals("Library", board.getRoom('L').getName() );
		assertEquals("Music Room", board.getRoom('M').getName() );
		assertEquals("Walkway", board.getRoom('W').getName() );
		assertEquals("Kitchen", board.getRoom('K').getName() );
		assertEquals("Bathroom", board.getRoom('P').getName() );
		assertEquals("Gaming Room", board.getRoom('G').getName() );
		assertEquals("Study", board.getRoom('S').getName() );
		assertEquals("Hall", board.getRoom('H').getName() );
		assertEquals("Unused", board.getRoom('X').getName() );
	}

	@Test
	public void testBoardDimensions() {
		// Ensure we have the proper number of rows and columns
		assertEquals(NUM_ROWS, board.getROWS());
		assertEquals(NUM_COLUMNS, board.getCOLS());
	}

	// Test a doorway in each direction (RIGHT/LEFT/UP/DOWN), plus
	// two cells that are not a doorway.
	// These cells are white on the planning spreadsheet
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
		for (int row = 0; row < board.getNumRows(); row++)
			for (int col = 0; col < board.getNumColumns(); col++) {
				BoardCell cell = board.getCell(row, col);
				if (cell.isDoorway())
					numDoors++;
			}
		Assert.assertEquals(17, numDoors);
	}

	// Test a few room cells to ensure the room initial is correct.
	@Test
	public void testRooms() {
		// just test a standard room location
		BoardCell cell = board.getCell( 23, 23);
		Room room = board.getRoom( cell ) ;
		assertTrue( room != null );
		assertEquals( room.getName(), "Kitchen" ) ;
		assertFalse( cell.isLabel() );
		assertFalse( cell.isRoomCenter() ) ;
		assertFalse( cell.isDoorway()) ;

		// this is a label cell to test
		cell = board.getCell(2, 19);
		room = board.getRoom( cell ) ;
		assertTrue( room != null );
		assertEquals( room.getName(), "Lounge" ) ;
		assertTrue( cell.isLabel() );
		assertTrue( room.getLabelCell() == cell );
		
		// this is a room center cell to test
		cell = board.getCell(20, 11);
		room = board.getRoom( cell ) ;
		assertTrue( room != null );
		assertEquals( room.getName(), "Ballroom" ) ;
		assertTrue( cell.isRoomCenter() );
		assertTrue( room.getCenterCell() == cell );
		
		// this is a secret passage test
		cell = board.getCell(3, 0);
		room = board.getRoom( cell ) ;
		assertTrue( room != null );
		assertEquals( room.getName(), "Study" ) ;
		assertTrue( cell.getSecretPassage() == 'K' );
		
		// test a walkway
		cell = board.getCell(5, 0);
		room = board.getRoom( cell ) ;
		// Note for our purposes, walkways and closets are rooms
		assertTrue( room != null );
		assertEquals( room.getName(), "Walkway" ) ;
		assertFalse( cell.isRoomCenter() );
		assertFalse( cell.isLabel() );
		
		// test a closet
		cell = board.getCell(24, 18);
		room = board.getRoom( cell ) ;
		assertTrue( room != null );
		assertEquals( room.getName(), "Unused" ) ;
		assertFalse( cell.isRoomCenter() );
		assertFalse( cell.isLabel() );
		
	}

}

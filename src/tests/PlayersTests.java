package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import clueGame.Player;
import clueGame.ComputerPlayer;
import clueGame.HumanPlayer;

class PlayersTests {
	
	private HumanPlayer man;
	private ComputerPlayer comp;
	private ComputerPlayer comp2;
	
	@BeforeEach
	void setUp() throws Exception {
		man = new HumanPlayer();
		comp = new ComputerPlayer();
		comp2 = new ComputerPlayer();
	}

	@Test
	void testDifferentColors() {
		assertTrue(man.getColor() != comp.getColor());
		assertTrue(man.getColor() != comp2.getColor());
		assertTrue(comp2.getColor() != comp.getColor());
	}

}

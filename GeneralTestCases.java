import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class GeneralTestCases {

	@Test
	void test() {
		BoardModel board = new BoardModel();
		Player player = new Player("Ethan");
		Location location = new Location();
		Move move = new Move(player, board, location);
		
		//Player can move if she hasn't moved yet
		assertTrue(move.isValid());
		
		//After a player moves, she shouldn't be able to move again
		move.excecute();
		assertFalse(move.isValid());
		
		assertEquals(location, player.getLocation());
		
		//Other actions should not interfere with Movement Validity.
		player.clearActions();
		player.addAction(new Upgrade(player, 3));
		assertTrue(move.isValid());
	}

}

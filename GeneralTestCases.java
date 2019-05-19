import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

class GeneralTestCases {

	@Test
	void moveTest() {
		BoardModel board = new BoardModel();
		Player player = new Player("Ethan");
		ArrayList<Player> players = new ArrayList<Player>();
		players.add(player);
		board.setPlayers(players);
		player.setArea(board.getAreaByName("Trailer"));
		
		Area area = board.getAreaByName("Main Street");
		Move move = new Move(player, board, area);
		
		//Player can move if she hasn't moved yet
		assertTrue(move.isValid());
		
		//After a player moves, she shouldn't be able to move again
		move.excecute();
		assertFalse(move.isValid());
		
		// Player should be in the right place.
		assertEquals(area, player.getArea());
		
		//Other actions should not interfere with Movement Validity.
		player.clearActions();
		player.addAction(new Upgrade(player, 3, "dollar"));
		player.setArea(board.getAreaByName("Trailer"));
		assertTrue(move.isValid());
	}
	
	@Test
	void upgradeTest() {
		//TODO write tests for the Upgrade action
		fail("not yet implemented");
	}
	
	@Test
	void takeRoleTest() {
		//TODO write tests for the TakeRole action
		fail("not yet implemented");
	}
	
	@Test
	void actTest() {
		//TODO write tests for the Act action
		fail("not yet implemented");
	}
	
	@Test
	void rehearseTest() {
		//TODO write tests for rehearsal. Make sure the player can't rehearse if they aren't on a role
		fail("not yet implemented");
	}

	@Test
	void hasAreaByNameTest(){
		BoardModel board = new BoardModel();
		assertTrue(board.hasAreaByName("Secret Hideout"));
	}

	@Test
	void getAreaByNameTest() {
		BoardModel board = new BoardModel();
		assertNotNull(board.getAreaByName("Secret Hideout"));
	}

}

import java.util.Arrays;
import java.util.stream.Stream;

public class TakeRole implements Action {

	private Player player;
	private BoardModel board;
	private Role role;
	
	public TakeRole(Player player, BoardModel board, Role role) {
		this.player = player;
		this.board = board;
		this.role = role;
	}

	@Override
	public boolean isValid() {
		
		//Steps TODO:
		// 1. Make sure the player is in the same area
		// 2. Check if the role is open
		// 3. Double-check that this set has shots left
		// 4. Check if the player's rank is high enough
		
		
		return roleOpen();
	}

	@Override
	public void excecute() {
		player.setRole(role);

	}
	
	// Checks if a role is open
	public boolean roleOpen() {
		for (Player p : board.getPlayers()){
			if (p.getRole() == role) return false;
		}
		return true;
	}
	
	//Double-checks that the player is in the same area as the role, and if the role is free
	// NOTE: This method isn't designed in a good way. NEEDS WORK
	public boolean sameArea() {
		Area a = board.getAreaByName(player.getArea().getName());
		if (a instanceof Set) {
			Set s = (Set) a;
			return Arrays.stream(s.getFreeRoles()).anyMatch(r -> r == role) //checks roles on set
					||
					Arrays.stream(s.getCard().getRemainingCRoles()).anyMatch(r -> r == role); //checks roles on card
		}
		else return false;
	}

}

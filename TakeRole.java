import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

public class TakeRole implements Action {

	private Player player;
	private String roleName;
	private Role role;
	
	public TakeRole(Player player, String roleName) {
		this.player = player;
		this.roleName = roleName;
	}

	@Override
	public boolean isValid() {
		Area a = player.getArea();
		if (a instanceof Set) {
			Set s = (Set) a;
			Optional<Role> optRole = s.getFreeRoles().stream().filter(r -> r.getName().equalsIgnoreCase(roleName)).findAny();
			if (optRole.isPresent()) {
				role = optRole.get();
				return (s.getShotsRemaining() > 0 && player.getRank() >= role.getRank());
			}
		}
		return false;
		// 1. Make sure the player is in the same area
		// 2. Check if the role is open
		// 3. Double-check that this set has shots left
		// 4. Check if the player's rank is high enough	
	}

	@Override
	public void excecute() {
		Set set = (Set) player.getArea();
		if (set.isRolePresent(role)) set.setRoleUnavailable(role);
		else if (set.getCard().isRolePresent(role)) set.getCard().setRoleUnavailable(role);
		else System.out.println("ERROR: the player took a role in an area they weren't in!!!?");

		player.addAction(this);
		player.setRole(role);
	}
}

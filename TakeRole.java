import java.util.Optional;

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
		boolean hasNoRole = player.getRole() == null;
		boolean hasNotActed = !player.hasPerformedAction(Act.class);

		Area a = player.getArea();
		if (a instanceof Set) {
			Set s = (Set) a;
			// get off-card roles
			Optional<Role> optRole = s.getFreeRoles().stream().filter(r -> r.getName().equalsIgnoreCase(roleName)).findAny();
			if (!optRole.isPresent()) {
				// get on-card roles
				optRole = s.getCard().getFreeCRoles().stream().filter(r -> r.getName().equalsIgnoreCase(roleName)).findAny();
				System.out.println("In TakeRole.isValid(), searched for a role on the card. Is it present? " + String.valueOf(optRole.isPresent()));
			}
			if(optRole.isPresent()){
				role = optRole.get();
				
				//Messages to check why the card roles are sometimes rejected...
				System.out.println(role.getStateString());
				System.out.println("Player rank: " + String.valueOf(player.getRank()));
				System.out.println("Role rank: " + String.valueOf(role.getRank()));
				System.out.println("Has no role: " + String.valueOf(hasNoRole));
				System.out.println("Has not acted: " + String.valueOf(hasNotActed));
				
				return (s.getShotsRemaining() > 0) && (player.getRank() >= role.getRank()) && hasNoRole && hasNotActed;
			}
		}
		return false;
		// 1. Make sure the player is in the same area
		// 2. Check if the role is open
		// 3. Double-check that this set has shots left
		// 4. Check if the player's rank is high enough
		// 5. Double-check that the player doesn't have a role
		// 6. Double-check that the player did not act this turn
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

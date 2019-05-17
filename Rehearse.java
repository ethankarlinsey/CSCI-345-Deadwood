
public class Rehearse implements Action {

	private Player player;
	
	public Rehearse(Player player) {
		this.player = player;
	}

	@Override
	public boolean isValid() {
		// NOTE: Assuming rehearse is one of the action choices, it is always valid.
		if (player.getRole() != null) {
			return ((Set) player.getArea()).getCard().getBudget() > player.getRehearsalCount() + 1; //Player can't rehearse if success is guarenteed
		}
		return false;
	}

	@Override
	public void excecute() {
		player.addRehearsal();
	}

}

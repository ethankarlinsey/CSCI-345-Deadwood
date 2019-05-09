
public class Rehearse implements Action {

	private Player player;
	
	public Rehearse(Player player) {
		this.player = player;
	}

	@Override
	public boolean isValid() {
		// NOTE: Assuming rehearse is one of the action choices, it is always valid.
		return player.getRole() != null;
	}

	@Override
	public void excecute() {
		player.addRehearsal();
	}

}

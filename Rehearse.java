
public class Rehearse implements Action {

	private Player player;
	
	public Rehearse(Player player) {
		this.player = player;
	}

	@Override
	public boolean isValid() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void excecute() {
		player.addRehearsal();
	}

}

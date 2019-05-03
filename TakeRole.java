
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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void excecute() {
		player.setRole(role);

	}

}

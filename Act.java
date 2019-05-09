
public class Act implements Action {

	private Player player;
	private BoardModel board;
	
	public Act(Player player, BoardModel board) {
		this.player = player;
		this.board = board;
	}

	@Override
	public boolean isValid() {
		// As long as the player has a role, she can act
		return player.getRole() != null;
	}

	@Override
	public void excecute() {
		// TODO Auto-generated method stub

	}
	
	private int calculatePay() {
		return 0;
	}

}

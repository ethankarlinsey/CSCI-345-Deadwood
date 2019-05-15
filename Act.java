
public class Act implements Action {

	private Player player;
	private BoardModel board;
	
	public Act(Player player, BoardModel board) {
		this.player = player;
		this.board = board;
	}

	@Override
	public boolean isValid() {
		// As long as the player has a role and hasn't acted yet, she can act
		return player.getRole() != null && !player.hasPerformedAction(Act.class);
	}

	@Override
	public void excecute() {
		// TODO Auto-generated method stub

	}
	
	private int calculatePay() {
		return 0;
	}

}


public class Act implements Action {

	private Player player;
	private BoardModel board;
	
	public Act(Player player, BoardModel board) {
		this.player = player;
		this.board = board;
	}

	@Override
	public boolean isValid() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void excecute() {
		// TODO Auto-generated method stub

	}
	
	private int calculatePay() {
		return 0;
	}

}

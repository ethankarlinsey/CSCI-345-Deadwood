
public class Move implements Action {

	private Player player;
	private BoardModel board;
	private Point newLocation;
	
	public Move(Player player, BoardModel board, Point newLocation) {
		this.player = player;
		this.board = board;
		this.newLocation = newLocation;
	}

	@Override
	public boolean isValid() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void excecute() {
		player.moveTo(newLocation);
	}

}

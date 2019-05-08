
public class Move implements Action {

	private Player player;
	private BoardModel board;
	private Location newLocation;
	
	public Move(Player player, BoardModel board, Location newLocation) {
		this.player = player;
		this.board = board;
		this.newLocation = newLocation;
	}

	@Override
	public boolean isValid() {
		boolean adjascent = player.getLocation().isAdjLocation(newLocation);
		boolean didNotMoveYet = !player.hasPerformedAction(Move.class);
		return adjascent && didNotMoveYet;
	}

	@Override
	public void excecute() {
		player.setLocation(newLocation);
		player.addAction((Action)this);
	}

}

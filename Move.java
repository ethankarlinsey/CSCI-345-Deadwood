
public class Move implements Action {

	private Player player;
	private BoardModel board;
	private Area area;
	
	public Move(Player player, BoardModel board, Area area) {
		this.player = player;
		this.board = board;
		this.area = area;
	}

	@Override
	public boolean isValid() {
		boolean adjacent = player.getArea().isAdjacent(area);
		boolean didNotMoveYet = !player.hasPerformedAction(Move.class);
		boolean didNotActYet = !player.hasPerformedAction(Act.class); // don't let players move after receiving payout
		boolean noRole = player.getRole() == null;
		return adjacent && didNotMoveYet && noRole && didNotActYet;
	}

	@Override
	public void excecute() {
		player.setArea(area);
		player.addAction((Action)this);
		if (area instanceof Set) ((Set) area).setCardVisible();
	}

}

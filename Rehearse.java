
public class Rehearse implements Action {

	private Player player;
	
	public Rehearse(Player player) {
		this.player = player;
	}

	@Override
	public boolean isValid() {
		if (player.getRole() != null) {
			boolean guaranteedSuccess = (((Set) player.getArea()).getCard().getBudget() > player.getRehearsalCount() + 1); //Player can't rehearse if success is guaranteed
			boolean gotRoleThisTurn = player.hasPerformedAction(TakeRole.class); // Player can't rehearse if they just got the role
			boolean alreadyActed = player.hasPerformedAction(Act.class); // Player can't rehearse if they've already acted this turn
			boolean alreadyRehearsed = player.hasPerformedAction(Rehearse.class); // Player can't rehearse if they've already rehearsed this turn
			return guaranteedSuccess && !gotRoleThisTurn && !alreadyActed && !alreadyRehearsed;
		}
		return false;
	}

	@Override
	public void excecute() {
		player.addAction(this);
		player.addRehearsal();
	}

}

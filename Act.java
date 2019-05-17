import java.util.Random;

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
		Random rand = new Random();
		int roll = rand.nextInt(6) + 1 + player.getRehearsalCount(); //Rolls the die and adds the number of rehearsals
		int budget = ((Set) player.getArea()).getCard().getBudget();
		
		calculatePay(roll >= budget); //Calculate pay based on outcome of the roll
	}
	
	private void calculatePay(boolean sucess) {
		if (((Set) player.getArea()).isRolePresent(player.getRole())) offCardPay(sucess);
		else if (((Set) player.getArea()).getCard().isRolePresent(player.getRole())) onCardPay(sucess);
		else System.out.println("ERROR: The player somehow acted without a role in their area.");
	}
	
	private void onCardPay(boolean sucess) {
		if (sucess) {
			player.addCredits(2);
			((Set) player.getArea()).removeShot();
		}
	}
	
	private void offCardPay(boolean sucess) {
		if (sucess) {
			player.addDollars(1);
			player.addCredits(1);
			((Set) player.getArea()).removeShot();
		}
		else {
			player.addDollars(1);
		}
	}

}

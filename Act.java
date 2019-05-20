import java.util.Random;

public class Act implements Action {

	private Player player;
	private BoardModel board;
	private int diceRoll = 0;
	private boolean actingSuccess = false;
	private int[] payment = {0,0}; // first index is credits, second index is dollars.

	
	public Act(Player player, BoardModel board) {
		this.player = player;
		this.board = board;
	}

	@Override
	public boolean isValid() {
		// As long as the player has a role, hasn't simply taken the role this turn, hasn't rehearsed in the same turn, and hasn't acted yet, she can act
		return player.getRole() != null && !player.hasPerformedAction(Act.class) && !player.hasPerformedAction(TakeRole.class) && !player.hasPerformedAction(Rehearse.class);
	}

	@Override
	public void excecute() {
		player.addAction(this);
		Random rand = new Random();
		this.diceRoll = rand.nextInt(6) + 1 + player.getRehearsalCount(); //Rolls the die and adds the number of rehearsals
		int budget = ((Set) player.getArea()).getCard().getBudget();
		this.actingSuccess = this.diceRoll >= budget;

		calculatePay(this.actingSuccess); //Calculate pay based on outcome of the roll
	}
	
	private void calculatePay(boolean success) {
		if (((Set) player.getArea()).isRolePresent(player.getRole())) offCardPay(success);
		else if (((Set) player.getArea()).getCard().isRolePresent(player.getRole())) onCardPay(success);
		else System.out.println("ERROR: The player somehow acted without a role in their area.");
	}
	
	private void onCardPay(boolean success) {
		if (success) {
			this.payment[0] = 2;
			this.payment[1] = 0;
			player.addCredits(2);
			((Set) player.getArea()).removeShot();
		}
	}
	
	private void offCardPay(boolean success) {
		if (success) {
			this.payment[0] = 1;
			this.payment[1] = 1;
			player.addDollars(1);
			player.addCredits(1);
			((Set) player.getArea()).removeShot();
		}
		else {
			this.payment[0] = 0;
			this.payment[1] = 1;
			player.addDollars(1);
		}
	}

	public boolean getActingSuccess(){
		return this.actingSuccess;
	}

	public int getDiceRoll(){
		return this.diceRoll;
	}

	public int[] getPayment(){
		return this.payment;
	}
}

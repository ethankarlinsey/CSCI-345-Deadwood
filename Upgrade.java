
public class Upgrade implements Action {

	private Player player;
	private int newRank;
	private String currency;
	
	// For the arrays of costs, the index is the rank, and the value is the cost
	private static final int[] dollarCosts = {0, 0, 4, 10, 18, 28, 40};
	private static final int[] creditCosts = {0, 0, 5, 10, 15, 20, 25};
	
	public Upgrade(Player player, int newRank, String currency) {
		this.player = player;
		this.newRank = newRank;
		
		// currency is a string of value "dollar" or "credit" that denotes which payment to use
		this.currency = currency;
	}

	@Override
	public boolean isValid() {
		
		// NOTE: This assumes the player is already in the Casting Office. TODO: check if player is in casting office
		
		//player cannot 'downgrade' her rank
		int currentRank = player.getRank();
		if (newRank <= currentRank) return false;
		
		//Check if the player has enough currency (credit or dollar)
		if (currency == "dollar") {
			int dollarCount = player.getDollarCount();
			if (dollarCount >= dollarCosts[newRank]) return true;
			else return false;
		}
		else if (currency == "credit") {
			int creditCount = player.getCreditCount();
			if (creditCount >= creditCosts[newRank]) return true;
			else return false;
		}
		else return false; //defaults to false if an invalid currency is used
	}

	@Override
	public void excecute() {
		player.addAction(this);
		if (currency == "dollar") {
			player.removeDollars(dollarCosts[newRank]); // deduct dollars
			player.upgradeRank(newRank);				// upgrade player rank
		}
		else if (currency == "credit") {
			player.removeCredits(creditCosts[newRank]);	// deduct credits
			player.upgradeRank(newRank);				// upgrade player rank
		}
	}

}

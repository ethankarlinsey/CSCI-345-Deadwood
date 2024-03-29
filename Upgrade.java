
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
		
		// currency is a string of value "dollars" or "credits" that denotes which payment to use
		this.currency = currency;
	}

	@Override
	public boolean isValid() {
		

		if(!this.player.getArea().getName().equals("Casting Office")){
			return false;
		}
		
		//player cannot 'downgrade' her rank
		int currentRank = player.getRank();
		if (newRank <= currentRank) return false;
		
		//Check if the player has enough currency (credit or dollar)
		if (currency.equals("dollars")) {
			int dollarCount = player.getDollarCount();
			if (dollarCount >= dollarCosts[newRank]) return true;
			else return false;
		}
		else if (currency.equals("credits")) {
			int creditCount = player.getCreditCount();
			if (creditCount >= creditCosts[newRank]) return true;
			else return false;
		}
		else return false; //defaults to false if an invalid currency is used
	}

	@Override
	public void excecute() {
		player.addAction(this);
		if (currency.equals("dollars")) {
			player.removeDollars(dollarCosts[newRank]); // deduct dollars
			player.upgradeRank(newRank);				// upgrade player rank
		}
		else if (currency.equals("credits")) {
			player.removeCredits(creditCosts[newRank]);	// deduct credits
			player.upgradeRank(newRank);				// upgrade player rank
		}
	}

}

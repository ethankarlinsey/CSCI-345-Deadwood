
public class Upgrade implements Action {

	private Player player;
	private int newRank;
	
	public Upgrade(Player player, int newRank) {
		this.player = player;
		this.newRank = newRank;
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
	
	private void setPlayerRank() {
		player.upgradeRank(newRank);
	}

}

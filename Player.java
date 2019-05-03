
public class Player {

	private String name;
	private int rank, dollarCount, creditCount, rehearsalCount;
	private Role role;
	private Point location;
	
	
	public Player(String name) {
		this.name = name;
		rank = 1;
		dollarCount = 0;
		creditCount = 0;
		rehearsalCount = 0;
		role = null;
		location = new Point(0, 0); //trailer location
	}
	
	
	
	public Role getRole() {return role;}
	public void setRole(Role role) {this.role = role;}
	
	public String getName() {return name;}
	
	public int getRank() {return rank;}
	
	public int getDollarCount() {return dollarCount;}
	
	public int getCreditCount() {return creditCount;}
	
	public int getRehearsalCount() {return rehearsalCount;}
	
	public Point getLocation() {return location;}
	
	

	public void addDollars(int dollars) {
		dollarCount += dollars;
	}
	
	public void addCredits(int credits) {
		creditCount += credits;
	}
	
	public void addRehearsal() {
		rehearsalCount++;
	}
	
	public void upgradeRank(int newRank) {
		rank = newRank;
	}
	
	public void moveTo(Point newLocation) {
		location = newLocation;
	}
	
	public void removeDollars(int cost) {
		dollarCount -= cost;
	}
	
	public void removeCredits(int cost) {
		creditCount -= cost;
	}

}

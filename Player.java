import java.util.ArrayList;

public class Player {

	private String name;
	private int rank, dollarCount, creditCount, rehearsalCount;
	private Role role;
	private Location location;
	private ArrayList<Action> actionsThisTurn;
	
	
	public Player(String name) {
		this.name = name;
		rank = 1;
		dollarCount = 0;
		creditCount = 0;
		rehearsalCount = 0;
		role = null;
		location = new Location(); //trailer location
		actionsThisTurn = new ArrayList<Action>();
	}
	
	
//--- Getters and Setters ------------------------------------------------------------------
//------------------------------------------------------------------------------------------
	
	public Role getRole() {return role;}
	public void setRole(Role role) {this.role = role;}
	
	public String getName() {return name;}
	
	public int getRank() {return rank;}
	
	public int getDollarCount() {return dollarCount;}
	
	public int getCreditCount() {return creditCount;}
	
	public int getRehearsalCount() {return rehearsalCount;}
	
	public Location getLocation() {return location;}
	public void setLocation(Location location) {this.location = location;}
	
	
//--- Public Methods ----------------------------------------------------------------------	
//-----------------------------------------------------------------------------------------	
	
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
	
	public void removeDollars(int cost) {
		dollarCount -= cost;
	}
	
	public void removeCredits(int cost) {
		creditCount -= cost;
	}
	
	public void addAction(Action a) {
		actionsThisTurn.add(a);
	}
	
	public void clearActions() {
		actionsThisTurn.clear();
	}
	
	public boolean hasPerformedAction(Class c) {
		return actionsThisTurn.stream()
						.anyMatch(a -> c.isInstance(a) );
	}
}

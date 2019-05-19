import java.util.ArrayList;

public class Player {

	private String name;
	private int rank, dollarCount, creditCount, rehearsalCount;
	private Role role;
	private Area area;
	private ArrayList<Action> actionsThisTurn;
	
	
	public Player(String name) {
		this.name = name;
		rank = 1;
		dollarCount = 0;
		creditCount = 0;
		rehearsalCount = 0;
		role = null;
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
	
	public Area getArea() {return area;}
	public void setArea(Area area) {this.area = area;}
	
	
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
	
	public void resetRehearsals() {
		rehearsalCount = 0;
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

	public String getStateString() {
		// get: Rank, Credits, Dollars, Role, Rehearsal tokens, Room
		String state = "Player: " + this.name +
				"\n\t" + "Rank: " + this.rank +
				"\n\t" + "Credits: " + this.creditCount +
				"\n\t" + "Dollars: " + this.dollarCount;
		if(this.role != null){
			state += "\n\t" + this.role.getStateString();
			state += "\n\tRehearsal Count: " + this.rehearsalCount;
		}
		state += "\n\tArea: " + this.area.getName();

		return state;
	}
}

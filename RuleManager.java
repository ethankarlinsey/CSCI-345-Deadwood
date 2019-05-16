import java.util.ArrayList;
import java.util.Collections;

public class RuleManager {

	private ArrayList<Player> players;
	private Player activePlayer;
	private BoardModel board;
	
	private int currentDay = 0;
	private final int lastDay = 4;	
	private int playerTurnIndex = 0;
	
	public RuleManager() {
		// TODO Auto-generated constructor stub
	}
	
	//TODO this method needs work
	public void startGame() {
		activePlayer = players.get(0);
		newDay(); //currentDay increments from 0 to 1
	}
	
	/*
	 * Randomizes player order and shuffles
	 */
	public void initializePlayers(ArrayList<String> names) {
		players = new ArrayList<Player>();
		Collections.shuffle(names);
		names.stream().forEach(name -> players.add(new Player(name)));
	}
	
	public boolean initializeBoard(String layout) {
		return true; //returns whether the layout string is actually an option
	}
	
	public ArrayList<Class> getValidActions(){ //builds a list of action types the player can generally do (as classes)
		
		ArrayList<Class> actions = new ArrayList<Class>();
		
		//If the player has a role and hasn't acted yet, she can act
		if (activePlayer.getRole() != null && !activePlayer.hasPerformedAction(Act.class))
			actions.add(Act.class);
		
		//If the player does not have a role and also hasn't moved yet, she can move
		if (activePlayer.getRole() == null && !activePlayer.hasPerformedAction(Move.class))
			actions.add(Move.class);
		
		//If the player has a role and has not rehearsed yet, she can rehearse
		if (activePlayer.getRole() != null && !activePlayer.hasPerformedAction(Rehearse.class))
			actions.add(Rehearse.class);
		
		//If the player does not have a role and is on a set with open roles, she can take a role.
		//Does not check whether the player's rank is high enough for the roles
		if (activePlayer.getRole() == null) {
			if (activePlayer.getArea() instanceof Set) {
				Set set = (Set) activePlayer.getArea();
				if (set.getFreeRoles().length > 0)
					actions.add(TakeRole.class);
			}
		}
		
		//If the player is in the CastingOffice and hasn't upgraded yet, she can upgrade
		if (activePlayer.getArea() instanceof CastingOffice && !activePlayer.hasPerformedAction(Upgrade.class))
			actions.add(Upgrade.class);
		
		return actions;
	}
	
	public void newDay() {
		currentDay++;
		board.nextDayReset(); //anything else not handled by the BoardModel?
		
		// TODO: should we check if currentDay > lastDay?
	}
	
	public boolean daysLeft() {
		return currentDay <= lastDay;
	}
	
	public boolean scenesLeft() { // checks whether there are scenes left to continue the day
		return board.getSceneCount() > 1;
	}
	
	public boolean excecuteAction(Action action) {
		return true;
	}
	
	public String tryAct() {
		Act act = new Act(activePlayer, board);
		
		if (act.isValid()) {
			act.excecute();
			return "Nice acting!"; //TODO: display how much they earned
		}
		return "You cannot act right now.";
	}
	
	public String tryMove(String areaName) {
		Area area = board.getAreaByName(areaName);
		Move move = new Move(activePlayer, board, area);
		
		if (move.isValid()) {
			move.excecute();
			return activePlayer.getName() + " moved to " + areaName;
		}
		return "Bruh. you can't move there";
	}
	
	public String tryRehearse() {
		Rehearse rehearse = new Rehearse(activePlayer);
		
		if (rehearse.isValid()) {
			rehearse.excecute();
			return "Nice practice. You're getting better!";
		}
		return "You can't rehearse if you don't have a roll silly!";
	}
	
	public String tryTakeRole(String roleName) {
		Role role = board.getRoleByName(roleName);
		TakeRole takeRole = new TakeRole(activePlayer,board, role);
		
		if (takeRole.isValid()) {
			takeRole.excecute();
			return activePlayer + " is now acting as " + roleName;
		}
		return "You can't take this role.";
	}
	
	public String tryUpgrade(int rank, String currency) {
		Upgrade upgrade = new Upgrade(activePlayer, rank, currency);
		
		if (upgrade.isValid()) {
			upgrade.excecute();
			return activePlayer + " upgraded to rank " + rank;
		}
		return "That upgrade isn't going to work out buddy.";
	}

	public void payout() {
		
	}
	
	public void setNextPlayerActive() {
		//Clear the player's actions at the end of each turn
		activePlayer.clearActions();
		
		//cycle to the next turn
		if (playerTurnIndex < players.size()) playerTurnIndex++;
		else playerTurnIndex = 0;		
		activePlayer = players.get(playerTurnIndex);
	}
	
	public void endGame() {
		
	}
	
	public void declareWinner() {
		
	}
	
}

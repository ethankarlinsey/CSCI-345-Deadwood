import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

public class RuleManager {

	private ArrayList<Player> players;
	private Player activePlayer;
	private BoardModel board;
	
	private int currentDay = 0;
	private int lastDay = 4;
	private int playerTurnIndex = 0;
	
	public RuleManager() {
		// TODO Auto-generated constructor stub
	}
	
	//TODO this method needs work
	public void startGame() {
		board.setPlayers(players);

		setGameParameters();

		activePlayer = players.get(0);
		newDay(); //currentDay increments from 0 to 1
	}
	
	// Randomizes player order and shuffles
	public void initializePlayers(ArrayList<String> names) {
		players = new ArrayList<Player>();
		Collections.shuffle(names);
		names.stream().forEach(name -> players.add(new Player(name)));
	}
	
	public boolean initializeBoard() {
		board = new BoardModel();
		board.setPlayers(players);
		return true;
	}
	
	public ArrayList<Class> getValidActions(){ //builds a list of action types the player can generally do (as classes)
		
		ArrayList<Class> actions = new ArrayList<Class>();
		
		//If the player has a role, hasn't acted yet, and hasn't moved, rehearsed, or taken a role this turn, she can act
		if (activePlayer.getRole() != null 
							&& !activePlayer.hasPerformedAction(Act.class) 
							&& !activePlayer.hasPerformedAction(Move.class)
							&& !activePlayer.hasPerformedAction(TakeRole.class)
							&& !activePlayer.hasPerformedAction(Rehearse.class))
			actions.add(Act.class);
		
		//If the player does not have a role and also hasn't taken a role, acted, moved, or rehearsed yet, she can move
		if (activePlayer.getRole() == null 
							&& !activePlayer.hasPerformedAction(TakeRole.class)
							&& !activePlayer.hasPerformedAction(Act.class)
							&& !activePlayer.hasPerformedAction(Rehearse.class)
							&& !activePlayer.hasPerformedAction(Move.class))
			actions.add(Move.class);
		
		//If the player has a role and has not taken a role, rehearsed, or acted yet, she can rehearse
		if (activePlayer.getRole() != null 
							&& !activePlayer.hasPerformedAction(TakeRole.class)
							&& !activePlayer.hasPerformedAction(Rehearse.class)
							&& !activePlayer.hasPerformedAction(Act.class))
			actions.add(Rehearse.class);
		
		//If the player does not have a role and is on a set with open roles, she can take a role.
		//Does not check whether the player's rank is high enough for the roles
		if (activePlayer.getRole() == null) {
			if (activePlayer.getArea() instanceof Set) {
				Set set = (Set) activePlayer.getArea();
				if (set.getFreeRoles().size() > 0)
					actions.add(TakeRole.class);
			}
		}
		
		//If the player is in the CastingOffice and hasn't upgraded yet, she can upgrade
		if (activePlayer.getArea() instanceof CastingOffice && !activePlayer.hasPerformedAction(Upgrade.class))
			actions.add(Upgrade.class);
		
		return actions;
	}
	
	public String getBoardStateAsString() { //building a string line by line might not be the best idea...
		String state = "";
		for (Area a : board.getAreas()) {
			state += a.getName();
			ArrayList<Player> areaPlayers = board.getPlayersByArea(a);
			if (!areaPlayers.isEmpty()) {
				state += " is occupied by:";
				for (Player p : areaPlayers) state += " " + p.getName();
			}
			else state += " is unoccupied.";
			state += "\n";
		}
		return state;
	}
	
	public String getAreaStateString(String areaName) {
		String state;
		if (board.hasAreaByName(areaName)) {
			Area area = board.getAreaByName(areaName);
			state = area.getStateString();
			ArrayList<Player> areaPlayers = board.getPlayersByArea(area);
			if (!areaPlayers.isEmpty()) {
				state += "Occupied by:";
				for (Player p : areaPlayers) state += " " + p.getName();
			}
			else state += "Unoccupied.";
			state += "\n";
		}
		else state = areaName + " does not exist.\n";
		return state;
	}
	
	// displays the number of days and scenes left.
	public String getGameStateString() {
		String state = "Game state:\n";
		
		state += "The current day is " 
				+ String.valueOf(currentDay) 
				+ " out of " 
				+ String.valueOf(lastDay) 
				+ "\n";
		
		state += "There are " 
				+ board.getSceneCount() 
				+ " scenes left\n";
		
		for (Player p : players) {
			state += p.getName() + " has " + String.valueOf(p.getScore()) + " points\n";
		}
		
		return state;
	}
	
	public String getPlayerStateString(String playerName) {
		String state = "";

		if(board.hasPlayer(playerName)){
			Player player = board.getPlayer(playerName);
			state = player.getStateString();
			if (player == activePlayer) 
				state += "\n\n It is " + playerName + "'s turn.";
			else 
				state += "\n\n It is not " + playerName + "'s turn.";
			
		} else {
			state = "Player " + playerName + " does not exist. Please pick another action.";
		}

		return state;
	}
	
	public void newDay() {
		currentDay++;
		if(daysLeft()) {
			board.nextDayReset(); //anything else not handled by the BoardModel?
		}
		// daysleft and scenesleft called by controller
	}
	
	public boolean daysLeft() {
		return currentDay < lastDay;
	}
	
	public boolean scenesLeft() { // checks whether there are scenes left to continue the day
		return board.getSceneCount() > 1;
	}
	
	public Player getActivePlayer() {
		return activePlayer;
	}
	
	public String tryAct() {
		Act act = new Act(activePlayer, board);
		String state = "";

		if (act.isValid()) {
			act.excecute();
			state += "You have acted. " +
					"\n\tYou rolled (roll + rehearsal bonus): " + act.getDiceRoll() + " using a rehearsal bonus of " + activePlayer.getRehearsalCount() +
					"\n\tThe film's budget is: " + ((Set) this.activePlayer.getArea()).getCard().getBudget() +
					"\n\tDid your performance pass muster? : " + act.getActingSuccess() +
					"\n\tYou receive a payment of [credits, dollars]: " + Arrays.toString(act.getPayment());
			String potentialBonus = checkSetShots((Set) activePlayer.getArea());
			state += potentialBonus;
			return state;
		}
		return null;
	}
	
	public String checkSetShots(Set set) {
		if (set.getShotsRemaining() <= 0) {
			return wrapScene(set); //Payout should also reset player rehearsals and roles
			// countRemainingScenes(); NOTE: controller counts how many scenes are left at the end of each turn
		}
		return "";
	}
	
	public boolean tryMove(String areaName) {
		Area area = board.getAreaByName(areaName);
		if(area != null) {
			Move move = new Move(activePlayer, board, area);

			if (move.isValid()) {
				move.excecute();
				return true;
			}
		}
		return false;
	}

	public String tryRehearse() {
		Rehearse rehearse = new Rehearse(activePlayer);
		
		if (rehearse.isValid()) {
			rehearse.excecute();
			return "Nice practice. You're getting better!";
		}
		return "You can't rehearse right now.";
	}
	
	public boolean tryTakeRole(String roleName) {
		TakeRole takeRole = new TakeRole(activePlayer, roleName);
		
		if (takeRole.isValid()) {
			takeRole.excecute();
			return true;
		}
		return false;
	}
	
	public String tryUpgrade(int rank, String currency) {
		Upgrade upgrade = new Upgrade(activePlayer, rank, currency);
		
		if (upgrade.isValid()) {
			upgrade.excecute();
			return activePlayer.getName() + " upgraded to rank " + rank;
		}
		return "That upgrade isn't going to work out buddy.";
	}

	public String cheatMove(String playerName, String areaName) {
		Area area = board.getAreaByName(areaName);
		Optional<Player> optPlayer = players.stream().filter(p -> p.getName().equalsIgnoreCase(playerName)).findAny();
		if(area != null && optPlayer.isPresent()) {
			optPlayer.get().setArea(area);
			return "Successfully entered the matrix!!! (cheat code worked)";
		}
		return "Invalid area or player name";
	}
	
	public String cheatSetInactive() {
		String message = "All areas except Main Street and Hotel set to inactive?";
		
		board.getAreas().stream()
						.filter(a -> a instanceof Set)
						.map(a -> (Set) a)
						.filter(s -> !(s.getName().equals("Main Street") || s.getName().equals("Hotel")))
						.forEach(s -> s.setInactiveOverride());
		
		return message;
	}
	
	//The controller calls newDay, so no need to worry about counting scenes
	public String wrapScene(Set set) {//pays the players when a scene wraps. Also resets their roles and rehearsals
		String payoutDetails = payout(set);
		//release the players
		ArrayList<Player> playersOnSet = board.getPlayersByArea(set);
		for (Player p : playersOnSet) {
			p.setRole(null);
			p.resetRehearsals();
		}
		set.setInactive(); //empties the list of available roles, removes the card - must be the last item

		return "\nThis Scene is wrapped.\n" + payoutDetails;
	}
	
	public String payout(Set set) {
		
		ArrayList<Player> playersOnSet = board.getPlayersByArea(set);
		String state = "";
		
		// 1. check if someone was on the card. If no one was on the card, there is no payout.
		ArrayList<Player> playersOnCard = (ArrayList<Player>) playersOnSet.stream()
																.filter(p -> set.getCard().getCardRoles().contains(p.getRole()))
																.collect(Collectors.toList());	
		
		if (playersOnCard.size() > 0) {
			
			// 2. roll same number of dice as budget, pay to players on card by rank.
			//setup the bonuses
			int budget = set.getCard().getBudget();
			int[] bonuses = new int[budget];
			Random rand = new Random();
			for (int i = 0; i < budget; i++) {
				bonuses[i] = rand.nextInt(6) + 1; //rolls the dice and adds its value to the list
			}
			Arrays.sort(bonuses);
			
			// pay the players on the card with the bonuses.
			//Sort players on card by rank in descending order
			Comparator<Player> compareByRoleRank = new Comparator<Player>() {
				public int compare(Player p1, Player p2) {
					return p1.getRole().getRank() - p2.getRole().getRank();					
				}
			};			
			playersOnCard = (ArrayList<Player>) playersOnCard.stream().sorted(compareByRoleRank).collect(Collectors.toList());
			
			//distribute bonuses
			int playerIndex = 0;
			for (int i = bonuses.length - 1; i > -1; i--) {
				if (playerIndex >= playersOnCard.size()) playerIndex = 0; //distribution of bonuses loops back if there are more bonuses than players.
				playersOnCard.get(playerIndex).addDollars(bonuses[i]);
				state += "\n\tPlayer " + playersOnCard.get(playerIndex).getName() +
						" who acted in an on-card role earns a bonus of " + bonuses[i] + " dollars.";
				playerIndex++;
			}
			
			// 3. Pay extras the same dollar value as the rank of their roles
			playersOnSet.stream()
						.filter(p -> set.getRoles().contains(p.getRole()))
						.forEach(p -> p.addDollars(p.getRole().getRank()));

			for (Player p: playersOnSet
				 ) {
				if(set.getRoles().contains(p.getRole())) {
					state += "\n\tPlayer " + p.getName() +
							" who acted in an off-card role earns a bonus of " + p.getRole().getRank() + " dollars.";
				}
			}
		}
		// if playersOnCard.size() == 0, there is no payout.
		return state;
	}
	
	public void setNextPlayerActive() {
		//Clear the player's actions at the end of each turn
		activePlayer.clearActions();
		
		//cycle to the next turn
		if (playerTurnIndex < players.size() - 1) playerTurnIndex++;
		else playerTurnIndex = 0;		
		activePlayer = players.get(playerTurnIndex);
	}
	
	public String getEndStateString() {
		String state = "The game is over! Let's see how you did:\n";
		
		for (Player p : players) {
			state += p.getName() 
					+ " scored " 
					+ String.valueOf(p.getScore()) 
					+ " points.\n";
		}
		
		state += getWinner().getName() + " is the winner!";
		
		return state;
	}
	
	private Player getWinner() {
		Player leadPlayer = null;
		for (Player p: players) {
			if (leadPlayer == null) leadPlayer = p;
			else {
				if (p.getScore() > leadPlayer.getScore()) leadPlayer = p; // does not check for ties
			}
		}
		return leadPlayer;
	}

	private void setGameParameters(){
		// change game parameters based on number of players
		if(players.size() < 4){
			this.lastDay = 3;
		} else if(players.size() == 5){
			players.stream()
					.forEach(p -> p.addCredits(2));
		} else if(players.size() == 6){
			players.stream()
					.forEach(p -> p.addCredits(3));
		} else {
			players.stream()
					.forEach(p -> p.upgradeRank(2));
		}

	}

	public ArrayList<Area> getAreas() {
		return board.getAreas();
	}

	public ArrayList<Card> getCards() {
		return board.getCards();
	}
}

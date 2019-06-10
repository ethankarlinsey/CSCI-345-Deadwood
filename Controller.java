import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Controller {

	//private static View view;
	private static RuleManager manager;
	private static MainWindow view;

	private static HashMap<Class, String> actionTypeToString = new HashMap<Class, String>();

	private static String defaultErrorString = "Incorrect Syntax. Type 'help' to see valid commands";

	private static String[] commandDescriptions = {
			"help - displays available commands",
			"exit - exits the game",
			"end turn - ends the current player's turn",
			"view player [playerName] - displays the stats of [playername]",
			"view area [areaName] - displays the information for [areaName]",
			"view board - displays the state of the board",
			"view gamestate - displays the state of the game",
			"act - if valid, the player will act",
			"move to [areaName] - if valid, the player will move to [areaName]",
			"rehearse - if valid, the player will rehearse",
			"take role [roleName] - if valid, the player takes the [roleName] role",
			"upgrade to [rankNumber] with [currencyType] - if valid, the player's rank will be upgraded to [rankNumber]. [currencyType] can be 'dollars' or 'credits'"
	};


	public static void initialize() {//initializes maps and Scanner

		actionTypeToString = new HashMap<>();
		//initialize the actionTypeToString map
		actionTypeToString.put(Act.class, "Act");
		actionTypeToString.put(Move.class, "Move");
		actionTypeToString.put(Rehearse.class, "Rehearse");
		actionTypeToString.put(TakeRole.class, "Take Role");
		actionTypeToString.put(Upgrade.class, "Upgrade");
	}
	
	/*
	 * Prompts for layout and tells manager to initialize the board
	 * Prompts for playercount and player names then tells manager to initialize players
	 * Tells manager to start the game
	 */

	public static void start() {

		// Begin initializing the game
		manager = new RuleManager();

		//First prompts - board layout, player count and player names
		System.out.println("Welcome to Deadwood! The cheapass game of acting badly!");

		//Initialize the board
		manager.initializeBoard();

		//INITIALIZE WINDOW
		view = MainWindow.getInstance();
		view.buildAreas(buildAreaViews());
		view.buildCards(buildCardViews());

		int playerCount = view.getNumPlayers();

		ArrayList<String> names = new ArrayList<String>();

		for (int i = 0; i < playerCount; i++) { //prompts for player names and adds to list.
			while(true) {
				String name = view.getPlayerName(i+1);
				if(name != null) {
					if (!names.contains(name) && name.length() < 4 && name.length() > 2) {        //If a name has already been added, the user is re-prompted
						names.add(name.substring(0, 3));
						break;
					}
				}
			}
		}

		//Initialize the players
		manager.initializePlayers(names);

		//Prompt game start
		System.out.println("Let the acting begin!");
		manager.startGame();

		updateAreaCards();
		view.setPlayers(names);
		turnUpdate();
		view.sendPlayersToTrailers(names);
	}

	private static void dayUpdate() {
		if (!manager.daysLeft()) end();
		manager.newDay();
		
		//TODO: start a new day in the view
		view.sendPlayersToTrailers(manager.getPlayerNames());
		//TODO: reset shots
		resetViewShots();
		updateAreaCards();
		view.setActivePlayer(manager.getActivePlayer().getName());
		view.displayNewDayMessage(manager.getCurrentDay(), manager.getLastDay());
	}

	// Called when the player ends their turn.
	public static void turnUpdate() {
		manager.setNextPlayerActive();
		view.setActivePlayer(manager.getActivePlayer().getName());
		//TODO: reflect this in the view
		if (!manager.scenesLeft()) dayUpdate();
		updateViewValidActions();
	}
	
	public static void resetViewShots() {
		ArrayList<Area> modelAreas = manager.getAreas();
		ArrayList<Set> modelSets = (ArrayList<Set>) modelAreas.stream().filter(a -> a instanceof Set).map(a -> (Set) a).collect(Collectors.toList());
		modelSets.stream().forEach(s -> view.getAreaByName(s.getName()).setShotsLeft(s.getInitialShots()));
	}

	private static void cheatMove(Scanner reader) { // cheat format: sendto [playername] [areaname]
		String playerName = reader.next();
		String areaName = reader.nextLine().trim();

		System.out.println(manager.cheatMove(playerName, areaName));
	}

	private static void cheatSetInactive() {
		System.out.println(manager.cheatSetInactive());
	}

	private static void help() {
		Arrays.stream(commandDescriptions).forEach(str -> System.out.println(str));
	}

	public static void tryAct(){
		String areaName = manager.getActivePlayer().getArea().getName();
		ArrayList<Role> heldRoles = new ArrayList<>();
		ArrayList<String> playersInArea = new ArrayList<>();
		manager.getPlayersByArea(manager.getActivePlayer().getArea()).forEach(p -> playersInArea.add(p.getName()));
		manager.getPlayersByArea(manager.getActivePlayer().getArea()).forEach(p -> heldRoles.add(p.getRole()));

		String act = manager.tryAct();
		if(act != null) {
			view.actStatus(act);
			view.setShot(areaName, ((Set) manager.getActivePlayer().getArea()).getShotsRemaining());

			// check if the scene wrapped
			if (manager.getActivePlayer().getRole() == null) {
				// update the view for all players who were on the card/ area
				for (int i = 0; i < playersInArea.size(); i++) {
					if (heldRoles.get(i) != null) {
						view.removeFromRole(areaName, heldRoles.get(i).getName());
						view.updatePlayerInfo(playersInArea.get(i));
					}
				}
			}
		} else view.displayActError();

		updateViewValidActions();
	}

	
	public static void tryMove(String areaName) {
		String oldAreaName = manager.getActivePlayer().getArea().getName();
		boolean moveSuccessful = manager.tryMove(areaName);
		if(moveSuccessful){
			view.movePlayer(manager.getActivePlayer().getName(), oldAreaName, manager.getActivePlayer().getArea().getName());
		}
		else view.displayMoveError(areaName);
		updateViewValidActions();
	}

	public static void tryRehearse(){
		boolean rehearse = manager.tryRehearse();
		if(!rehearse) view.displayRehearseError();
		updateViewValidActions();
	}

	public static void tryTakeRole(String roleName){
		boolean takeRoleSuccessful = manager.tryTakeRole(roleName);
		if(takeRoleSuccessful){
			view.addToRole(manager.getActivePlayer().getName(), manager.getActivePlayer().getArea().getName(), roleName);
		}
		else view.displayTakeRoleError(roleName);
		updateViewValidActions();
	}

	public static void tryUpgrade(int rank, String currency){
		boolean upgrade = manager.tryUpgrade(rank, currency);
		if(upgrade){
			view.updatePlayerInfo(manager.getActivePlayer().getName());
		} else {
			view.displayUpgradeError();
		}
		updateViewValidActions();
	}

	public static void displayBoardState() {
		System.out.println("\nBoard state:\n");
		System.out.println(manager.getBoardStateAsString());
	}

	// displays the number of days left, maybe number of scenes left in the day and who is in the lead?
	public static void displayGameState() {
		System.out.println(manager.getGameStateString());
	}

	// displays the area info including occupants and neighbors
	// if set, display roles (open or taken by ___) budget info shot info, and card role info.
	public static void displayAreaState(String areaName) {
		String message = areaName + " state: \n" + manager.getAreaStateString(areaName);
		System.out.println(message);
		view.updateGeneralInfo(message);
	}

	public static String displayPlayerState(String playerName) {
		String message = playerName + " state: \n";
		message += manager.getPlayerStateString(playerName);
		System.out.println(message);
		return message;
	}

	public static void displayRoleState(String roleName) {
		System.out.println("Role state " + roleName);
		// TODO: Make better message
		view.updateGeneralInfo(roleName);
	}

	public static void displayCardState(String cardTitle) {
		// TODO Auto-generated method stub
		view.updateGeneralInfo(cardTitle);
	}

	public static void end() {
		System.out.println(manager.getEndStateString());
		view.displayWinner(manager.getEndStateString());
		// TODO: Should we exit here?
	}


	// View builder methods
	public static ArrayList<AreaView> buildAreaViews() {
		ArrayList<Area> modelAreas = manager.getAreas();
		ArrayList<AreaView> viewAreas = new ArrayList<AreaView>();

		for (Area a: modelAreas) {
			viewAreas.add(buildAreaView(a));
		}

		return viewAreas;
	}

	public static ArrayList<RoleView> buildRoleViews(Set modelSet) {
		ArrayList<Role> modelRoles = modelSet.getRoles();
		ArrayList<RoleView> viewRoles = new ArrayList<RoleView>();

		for (Role r: modelRoles) {
			viewRoles.add(buildRoleView(r));
		}

		return viewRoles;
	}

	public static ArrayList<RoleView> buildRoleViews(Card modelCard){
		ArrayList<Role> modelRoles = modelCard.getCardRoles();
		ArrayList<RoleView> viewRoles = new ArrayList<RoleView>();

		for (Role r: modelRoles) {
			viewRoles.add(buildRoleView(r));
		}

		return viewRoles;
	}

	public static ArrayList<CardView> buildCardViews(){
		ArrayList<Card> modelCards = manager.getCards();
		ArrayList<CardView> viewCards = new ArrayList<CardView>();

		for (Card c: modelCards) {
			viewCards.add(buildCardView(c));
		}

		return viewCards;
	}

	public static AreaView buildAreaView(Area modelArea) {
		AreaView viewArea = new AreaView(modelArea.getName(), view);
		//viewArea.setPlayers(null);

		if (modelArea instanceof Set) {
			viewArea.setShotsLeft(((Set) modelArea).getShotsRemaining());
			viewArea.setRoles(buildRoleViews((Set) modelArea));
		}

		return viewArea;
	}

	public static RoleView buildRoleView(Role modelRole) {
		RoleView viewRole = new RoleView(modelRole.getName(), view);
		viewRole.setLine(modelRole.getLine());
		viewRole.setPlayer(null);
		viewRole.setRank(modelRole.getRank());

		System.out.println(modelRole.getName() + "\n" + viewRole.getName());

		return viewRole;
	}

	public static CardView buildCardView(Card modelCard) {
		CardView viewCard = new CardView(modelCard.getTitle(), view);
		viewCard.setBudget(modelCard.getBudget());
		viewCard.setDescription(modelCard.getDescription());
		System.out.println("CARD DESCRIPTION------ " + modelCard.getDescription());
		viewCard.setRoles(buildRoleViews(modelCard));

		return viewCard;
	}

	public static void updateViewValidActions() {
		String temp;
		ArrayList<Class> actions = manager.getValidActions();
		ArrayList<String> validActionNames = new ArrayList<>();

		for(Class c : actions){
			temp = c.getName();
			if(temp.equals("Act")){
				validActionNames.add("Act");
			} else if(temp.equals("Move")){
				validActionNames.add("Move");
			} else if(temp.equals("Rehearse")){
				validActionNames.add("Rehearse");
			} else if(temp.equals("TakeRole")){
				validActionNames.add("Take Role");
			} else if(temp.equals("Upgrade")){
				validActionNames.add("Upgrade");
			}
		}

		validActionNames.add("End Turn");

		view.updateEnabledButtons(validActionNames);
		view.updatePlayerInfo(manager.getActivePlayer().getName());
	}

	public static void updateAreaCards() {
		ArrayList<Area> modelAreas = manager.getAreas();

		for (Area modelArea: modelAreas) {
			if (modelArea instanceof Set) {
				Set modelSet = (Set) modelArea;
				String areaName = modelSet.getName();
				String cardDescription = modelSet.getCard().getDescription();

				AreaView viewArea = view.getAreaByName(areaName);
				CardView viewCard = view.getCardByDescription(cardDescription);
				viewArea.replaceCard(viewCard);
			}
		}
	}

	public static void main(String[] args) {

		initialize();

		start();
	}

}

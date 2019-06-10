import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

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
	}

	private static void dayUpdate() {
		if (!manager.daysLeft()) end();
		manager.newDay();
		//view.newDay();
		//TODO: start a new day in the view
	}

	// Called when the player ends their turn.
	public static void turnUpdate() {
		manager.setNextPlayerActive();
		view.setActivePlayer(manager.getActivePlayer().getName());
		//TODO: reflect this in the view
		if (!manager.scenesLeft()) dayUpdate();
		updateViewValidActions();
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

	private static boolean tryEndTurn(Scanner reader) { // if the command was "end turn" return false to end the turn.
		if (reader.next().toLowerCase().equals("turn")) return false;
		System.out.println("Error ending turn");
		System.out.println(defaultErrorString);
		return true;
	}

//	private static void tryAct(Scanner reader) { // verifies command syntax and prompts manager to try the action
//		if (reader.nextLine().trim().length() > 0) {
//			System.out.println("Error acting");
//			System.out.println(defaultErrorString); // if the command has more elements than "act", write an error and return.
//			return;
//		}
//
//		String message = manager.tryAct();
//		System.out.println(message);
//	}

	public static void tryAct(){
		String areaName = manager.getActivePlayer().getArea().getName();
		ArrayList<Role> heldRoles = new ArrayList<>();
		ArrayList<String> playersInArea = new ArrayList<>();
		manager.getPlayersByArea(manager.getActivePlayer().getArea()).forEach(p -> playersInArea.add(p.getName()));
		manager.getPlayersByArea(manager.getActivePlayer().getArea()).forEach(p -> heldRoles.add(p.getRole()));

		String act = manager.tryAct();
		if(act != null){
			view.actStatus(act);
			view.decrementShot(areaName);

			// check if the scene wrapped
			if(manager.getActivePlayer().getRole() == null){
				// update the view for all players who were on the card/ area
				for(int i = 0; i < playersInArea.size(); i++){
					if(heldRoles.get(i) != null){
						view.removeFromRole(areaName, heldRoles.get(i).getName());
						view.updatePlayerInfo(playersInArea.get(i));
					}
				}
			}

			updateViewValidActions();
		}
	}

//	private static void tryView(Scanner reader) {
//		try {
//			String viewType = reader.next();
//			if (viewType.equalsIgnoreCase("player")) {
//				displayPlayerState(reader.nextLine().trim());
//			}
//			else if (viewType.equalsIgnoreCase("area")) {
//				displayAreaState(reader.nextLine().trim());
//			}
//			else if (viewType.equalsIgnoreCase("board")) {
//				displayBoardState();
//			}
//			else if (viewType.equalsIgnoreCase("gamestate")) {
//				displayGameState();
//			}
//			else System.out.println("that is an invalid view request.");
//		}
//		catch (Exception e) {
//			System.out.println("Error viewing");
//			System.out.println(e.getMessage());
//			e.printStackTrace(System.out);
//			System.out.println(defaultErrorString);
//		}
//	}
	
	public static void tryMove(String areaName) {
		String oldAreaName = manager.getActivePlayer().getArea().getName();
		boolean moveSuccessful = manager.tryMove(areaName);
		if(moveSuccessful){
			view.movePlayer(manager.getActivePlayer().getName(), oldAreaName, manager.getActivePlayer().getArea().getName());
			updateViewValidActions();
		}
		else view.displayMoveError(areaName);
	}

//	private static void tryRehearse(Scanner reader) { // verifies command syntax and prompts manager to try the action
//		if (reader.nextLine().trim().length() > 0) {
//			System.out.println("Error rehearsing - too many arguments");
//			System.out.println(defaultErrorString);
//			return;
//		}
//
//		String message = manager.tryRehearse();
//		System.out.println(message);
//	}

	public static void tryRehearse(){
		boolean rehearse = manager.tryRehearse();
		if(rehearse){
			updateViewValidActions();
		}
		else view.displayRehearseError();
	}

	public static void tryTakeRole(String roleName){
		boolean takeRoleSuccessful = manager.tryTakeRole(roleName);
		if(takeRoleSuccessful){
			view.addToRole(manager.getActivePlayer().getName(), manager.getActivePlayer().getArea().getName(), roleName);
			updateViewValidActions();
		}
		else view.displayTakeRoleError(roleName);
	}

//	private static void tryUpgrade(Scanner reader) { // verifies command syntax and prompts manager to try the action
//		try {
//			if (reader.next().toLowerCase().equals("to")) {
//				int rank = reader.nextInt();
//				if (reader.next().toLowerCase().equals("with")) {
//					String currency = reader.nextLine().toLowerCase().trim();
//					String message = manager.tryUpgrade(rank, currency);
//					System.out.println(message);
//					return;
//				}
//			}
//		}
//		catch (Exception e) {
//			System.out.println("Error upgrading - enough to throw an error... try matching the format listed under help");
//			return;
//		}
//		System.out.println("Error upgrading, but not enough to throw an error... try getting your words right");
//	}

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
		
	}

//	private static void displayValidActions(ArrayList<Class> actions) {
//		System.out.println();
//		System.out.println("It is " + manager.getActivePlayer().getName() + "'s turn. Right now you can");
//		actions.stream().map(c -> actionTypeToString.get((Class) c)).forEach(str -> System.out.println(str));
//		System.out.println("End turn");
//	}

	public static void end() {
		System.out.println(manager.getEndStateString());
		
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
				String cardTitle = modelSet.getCard().getTitle();

				AreaView viewArea = view.getAreaByName(areaName);
				CardView viewCard = view.getCardByTitle(cardTitle);
				viewArea.replaceCard(viewCard);
			}
		}
	}

	public static void main(String[] args) {

		initialize();

		start();
	}

}

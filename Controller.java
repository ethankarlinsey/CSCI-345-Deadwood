import java.util.HashMap;
import java.util.InputMismatchException;
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
	
	
	public static Scanner initialize() {//initializes maps and Scanner

		actionTypeToString = new HashMap<>();
		//initialize the actionTypeToString map
		actionTypeToString.put(Act.class, "Act");
		actionTypeToString.put(Move.class, "Move");
		actionTypeToString.put(Rehearse.class, "Rehearse");
		actionTypeToString.put(TakeRole.class, "Take Role");
		actionTypeToString.put(Upgrade.class, "Upgrade");

		return new Scanner(System.in);
	}
	
	/*
	 * Prompts for layout and tells manager to initialize the board
	 * Prompts for playercount and player names then tells manager to initialize players
	 * Tells manager to start the game
	 */
	public static void start(Scanner reader) {
		
		// Begin initializing the game
		manager = new RuleManager();
		
		//First prompts - board layout, player count and player names
		System.out.println("Welcome to Deadwood! The cheapass game of acting badly!");
		
		int playerCount = -1;
		
		while (true) {
			System.out.println("How many players are there? (There can be 2 to 8.)");
			try {
				playerCount = reader.nextInt();
			}
			catch (InputMismatchException e) {
				reader.next();
			}
			if (playerCount > 1 && playerCount < 9) break;
			
			System.out.println("That's an invalid number of players!");
		}
		ArrayList<String> names = new ArrayList<String>();
		
		for (int i = 0; i < playerCount; i++) { //prompts for player names and adds to list.
			while(true) {
				Integer playerNum = i + 1;
				System.out.println("What is player " + playerNum.toString() + "'s name? (three characters)");
				String name = reader.next();
				if (!names.contains(name) && name.length() < 4 && name.length() > 2) {		//If a name has already been added, the user is re-prompted
					names.add(name.substring(0, 3));
					break;
				}
				System.out.println("That's an invalid name!");
			}
		}
		names.stream().forEach(s -> System.out.println(s));
		
		//Initialize the players
		manager.initializePlayers(names);

		// Initialize the board
		System.out.println("What board layout will you use? (default)");
		manager.initializeBoard(reader.next());
		
		//INITIALIZE WINDOW
		view = new MainWindow();
		view.buildAreas(buildAreaViews());
		view.buildCards(buildCardViews());
		
		
		//Prompt game start
		System.out.println("Let the acting begin!");
		manager.startGame();
		
		updateAreaCards();
	}

	public static void dayUpdate(Scanner reader) {
		
		while (manager.scenesLeft()) { // while there are scenes left in the day, keep doing turns
			turnUpdate(reader);
		}

		manager.newDay(); // start a new day whenever we only have one scene left
		System.out.println("Start of a new day!\n" +
						"Everyone is returned to their trailers, all unfinished scenes are cancelled, and 10 fabulous new scenes await you!");
		if(manager.daysLeft()){
			System.out.println("Please note that this is not the final day: you will have at least one more day after this to show your stuff on set!");
		} else {
			System.out.println("Please note that this IS the final day. Time to make it count!");
		}
	}
	
	/*
	 * Handles turn-by-turn operations
	 */
	private static void turnUpdate(Scanner reader) {
		

		// Display the updated board before each turn
		displayBoardState();
		
		boolean continueTurn = true;
		while (continueTurn) {
			continueTurn = actionUpdate(reader);
		} // continue prompting player for actions until she decides to end her turn
		
		manager.setNextPlayerActive(); // move to the next player at the end of each turn
	}
	
	private static boolean actionUpdate(Scanner reader) {
		

		// clear the reader
		System.out.println("Press enter to continue.");
		reader.nextLine();
		
		// Display valid actions, which always includes ending the turn
		ArrayList<Class> actions = manager.getValidActions();	
		displayValidActions(actions);
		
		// Prompt user for command and split the input into words
		System.out.println("What would you like to do?");
		String firstWord = reader.next().toLowerCase(); // set commands to lowercase
		
		switch (firstWord) { // Parse the first argument of the command and try to execute the corresponding action
		case "exit":
			reader.close();
			System.exit(0);
			break;
		case "end":
			return tryEndTurn(reader);
		case "view":
			tryView(reader);
			break;
		case "help":
			help();
			break;
		case "act":
			tryAct(reader);
			break;
		case "move":
			tryMove(reader);
			break;
		case "rehearse":
			tryRehearse(reader);
			break;
		case "take":
			tryTakeRole(reader);
			break;
		case "upgrade":
			tryUpgrade(reader);
			break;
			
			// ------------------ Cheat codes for debugging are below this line --------------------------
			
		case "sendto":	// cheat code sends active player to specified area
			cheatMove(reader);
			break;
		case "newday":
			manager.newDay();
			break;
		case "endgame":
			end(reader);
			break;
		case "setinactive":
			cheatSetInactive();
			break;
		default:
			System.out.println(defaultErrorString);
			break;
		}
		return true;
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

	private static void tryAct(Scanner reader) { // verifies command syntax and prompts manager to try the action
		if (reader.nextLine().trim().length() > 0) {
			System.out.println("Error acting");
			System.out.println(defaultErrorString); // if the command has more elements than "act", write an error and return.
			return;
		}
		
		String message = manager.tryAct();
		System.out.println(message);
	}

	private static void tryView(Scanner reader) {
		try {
			String viewType = reader.next();
			if (viewType.equalsIgnoreCase("player")) {
				displayPlayerState(reader.nextLine().trim());
			}
			else if (viewType.equalsIgnoreCase("area")) {
				displayAreaState(reader.nextLine().trim());
			}
			else if (viewType.equalsIgnoreCase("board")) {
				displayBoardState();
			}
			else if (viewType.equalsIgnoreCase("gamestate")) {
				displayGameState();
			}
			else System.out.println("that is an invalid view request.");
		}
		catch (Exception e) {
			System.out.println("Error viewing");
			System.out.println(e.getMessage());
			e.printStackTrace(System.out);
			System.out.println(defaultErrorString);
		}
	}
	
	private static void tryMove(Scanner reader) { // verifies command syntax and prompts manager to try the action
		try {
			if (reader.next().toLowerCase().equals("to")){
				String areaName = reader.nextLine().trim();
				String message = manager.tryMove(areaName);
				System.out.println(message);
				return;
			}
		}
		catch (Exception e) {
			System.out.println("Error moving");
			System.out.println(e.getMessage());
			e.printStackTrace(System.out);
			System.out.println(defaultErrorString);
		}
	}
	
	public static void tryMove(String areaName) {
		String message = manager.tryMove(areaName);
		System.out.println(message);
	}
	
	private static void tryRehearse(Scanner reader) { // verifies command syntax and prompts manager to try the action
		if (reader.nextLine().trim().length() > 0) {
			System.out.println("Error rehearsing - too many arguments");
			System.out.println(defaultErrorString);
			return;
		}
		
		String message = manager.tryRehearse();
		System.out.println(message);
	}

	private static void tryTakeRole(Scanner reader) { // verifies command syntax and prompts manager to try the action
		try {
			if (reader.next().toLowerCase().equals("role")) {
				String roleName = reader.nextLine().trim();
				String message = manager.tryTakeRole(roleName);
				System.out.println(message);
			}
		}
		catch (Exception e) {
			System.out.println("Error taking role.");
			System.out.println(e.getMessage());
			e.printStackTrace();
			System.out.println(defaultErrorString);
			return;
		}
	}

	private static void tryUpgrade(Scanner reader) { // verifies command syntax and prompts manager to try the action
		try {
			if (reader.next().toLowerCase().equals("to")) {
				int rank = reader.nextInt();
				if (reader.next().toLowerCase().equals("with")) {
					String currency = reader.nextLine().toLowerCase().trim();
					String message = manager.tryUpgrade(rank, currency);
					System.out.println(message);
					return;
				}
			}
		}
		catch (Exception e) {
			System.out.println("Error upgrading - enough to throw an error... try matching the format listed under help");
			return;
		}
		System.out.println("Error upgrading, but not enough to throw an error... try getting your words right");
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
		System.out.println("\n" + areaName + " state: \n");
		System.out.println(manager.getAreaStateString(areaName));
	}
	
	public static void displayPlayerState(String playerName) {
		System.out.println("\n" + playerName + " state: \n");
		System.out.println(manager.getPlayerStateString(playerName));
	}
	
	public static void displayRoleState(String roleName) {
		System.out.println("Role statae " + roleName);
	}
	
	private static void displayValidActions(ArrayList<Class> actions) {
		System.out.println();
		System.out.println("It is " + manager.getActivePlayer().getName() + "'s turn. Right now you can");
		actions.stream().map(c -> actionTypeToString.get((Class) c)).forEach(str -> System.out.println(str));
		System.out.println("End turn");
	}
	
	public static void end(Scanner reader) {
		System.out.println(manager.getEndStateString());
		reader.nextLine();
		reader.close();
		System.exit(0);
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
		viewArea.setPlayers(null);
		
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
		viewCard.setDesctription(modelCard.getDescription());
		viewCard.setRoles(buildRoleViews(modelCard));
		
		return viewCard;
	}
	
	public static void updateAreaCards() {
		// TODO: update the AreaView with new CardViews.
	}
	
	public static void main(String[] args) {
		
		Scanner reader = initialize();
		
		start(reader);
		
		// cycle through days as long as there are days left
		while (manager.daysLeft()) {
			dayUpdate(reader);
		}
		
		end(reader);
	}

}

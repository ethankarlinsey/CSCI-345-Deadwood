import java.util.HashMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Controller {

	//private static View view;
	private static RuleManager manager;

	private static HashMap<Class, String> actionTypeToString;

	private static String defaultErrorString = "Incorrect Syntax. Type 'help' to see valid commands";
	
	private static String[] commandDescriptions = {
			"help - displays available commands",
			"exit - exits the game",
			"end turn - ends the current player's turn",
			"view [playerName] - displays the stats of [playername]",
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
	 * Tells manager to start the game TODO: implement RuleManager.StartGame()
	 */
	public static void start(Scanner reader) {
		
		// Begin initializing the game
		manager = new RuleManager();
		
		//First prompts - board layout, player count and player names
		System.out.println("Welcome to Deadwood! The cheapass game of acting badly!");
		
		// Initialize the board
		System.out.println("What board layout will you use? (default)");
		manager.initializeBoard(reader.next()); //TODO implement layout validity check
		
		System.out.println("How many players are there? (There can be 2 to 8.)");
		int playerCount = reader.nextInt();
		ArrayList<String> names = new ArrayList<String>();
		
		for (int i = 0; i < playerCount; i++) { //prompts for player names and adds to list.
			while(true) {
				Integer playerNum = i + 1;
				System.out.println("What is player " + playerNum.toString() + "'s name? (three characters)");
				String name = reader.next();
				if (!names.contains(name)) {		//If a name has already been added, the user is re-prompted
					names.add(name.substring(0, 3));
					break;
				}
				System.out.println("That name is already being used!");
			}
		}
		names.stream().forEach(s -> System.out.println(s));
		
		//Initialize the players
		manager.initializePlayers(names);
		
		//Prompt game start
		System.out.println("Let the acting begin!");
		manager.startGame(); //not sure if this method will be useful
	}

	public static void dayUpdate(Scanner reader) {
		
		while (manager.scenesLeft()) { // while there are scenes left in the day, keep doing turns
			turnUpdate(reader);
		}
		
		manager.newDay(); // start a new day whenever we run out of scenes		
	}
	
	/*
	 * Handles turn-by-turn operations
	 */
	private static void turnUpdate(Scanner reader) {
		
		while (actionUpdate(reader)) {} // continue prompting player for actions until she decides to end her turn
		
		manager.setNextPlayerActive(); // move to the next player at the end of each turn
	}
	
	private static boolean actionUpdate(Scanner reader) {
		
		// Display the updated board before each action
		displayBoardState();
		
		// Display valid actions, which always includes ending the turn
		ArrayList<Class> actions = manager.getValidActions();	
		displayValidActions(actions);
		
		// Prompt user for command and split the input into words
		System.out.println("What would you like to do?");
		String[] command = reader.next().toLowerCase().split(" "); // set commands to lowercase
		
		switch (command[0]) { // Parse the first argument of the command and try to execute the corresponding action
		case "exit":
			System.exit(0);
		case "end":
			return tryEndTurn(command);
		case "help":
			help();
			break;
		case "act":
			tryAct(command);
			break;
		case "move":
			tryMove(command);
			break;
		case "rehearse":
			tryRehearse(command);
			break;
		case "take":
			tryTakeRole(command);
			break;
		case "upgrade":
			tryUpgrade(command);
			break;
		default:
			System.out.println(defaultErrorString);
		}
		
		return true;
	}
	
	private static void help() {
		Arrays.stream(commandDescriptions).forEach(str -> System.out.println(str));
	}
	
	private static boolean tryEndTurn(String[] command) { // if the command was "end turn" return false to end the turn.
		if (command[1].equals("turn")) return false;
		System.out.println(defaultErrorString);
		return true;
	}
	
	private static void tryAct(String[] command) { // verifies command syntax and prompts manager to try the action
		if (command.length != 1) {
			System.out.println(defaultErrorString); // if the command has more elements than "act", write an error and return.
			return;
		}
		
		String message = manager.tryAct();
		System.out.println(message);
	}
	
	private static void tryMove(String[] command) { // verifies command syntax and prompts manager to try the action
		if (command.length != 3) { 
			System.out.println(defaultErrorString);
			return;
		}
		if (!command[1].equals("to")) {
			System.out.println(defaultErrorString);
			return;
		}
		
		String message = manager.tryMove(command[2]);
		System.out.println(message);
	}
	
	private static void tryRehearse(String[] command) { // verifies command syntax and prompts manager to try the action
		if (command.length != 1) {
			System.out.println(defaultErrorString);
			return;
		}
		
		String message = manager.tryRehearse();
		System.out.println(message);
	}
	
	private static void tryTakeRole(String[] command) { // verifies command syntax and prompts manager to try the action
		if (command.length != 3) { 
			System.out.println(defaultErrorString);
			return;
		}
		if (!command[1].equals("role")) {
			System.out.println(defaultErrorString);
			return;
		}
		
		String message = manager.tryTakeRole(command[3]);
		System.out.println(message);
	}
	
	private static void tryUpgrade(String[] command) { // verifies command syntax and prompts manager to try the action
		if (command.length != 5) {
			System.out.println(defaultErrorString);
			return;
		}
		if (!command[1].equals("to")) {
			System.out.println(defaultErrorString);
			return;
		}
		if (!command[3].equals("with")) {
			System.out.println(defaultErrorString);
			return;
		}
		
		int rank;
		
		try {
			rank = Integer.getInteger(command[2]);
		}
		catch (Exception e) {
			System.out.println(defaultErrorString);
			return;
		}
		
		String message = manager.tryUpgrade(rank, command[4]);
		System.out.println(message);
	}
	
	private static void displayBoardState() {
		// TODO: Fill this in.
	}

	private static void displayRoomState() {
		// TODO: Fill this in. If set, display role info, budget info, shot info, then card role info.
	}

	private static void displayAdjacentRooms() {
		// TODO: Fill this in.
	}
	
	private static void displayValidActions(ArrayList<Class> actions) {
		System.out.println("Right now you can");
		actions.stream().map(c -> actionTypeToString.get((Class) c)).forEach(str -> System.out.println(str));
		System.out.println("End turn");
	}
	
	public static void end(Scanner reader) {
		//TODO: display the game ending
		reader.close();
	}
	
	public static void main(String[] args) {
		
		Scanner reader = initialize();

		reader.useDelimiter("\n");
		
		start(reader);
		
		// cycle through days as long as there are days left
		while (manager.daysLeft()) {
			dayUpdate(reader);
		}
		
		end(reader);
	}

}

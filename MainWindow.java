import java.awt.EventQueue;

import javax.swing.*;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.border.LineBorder;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

import java.awt.Choice;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class MainWindow {

	private static MainWindow instance;
	private JFrame frmDeadwood;
	//private Controller controller;
	private JLayeredPane boardView;
	private JLayeredPane menuBar;
	private JLayeredPane playerInfoPane, generalInfoPane, controlPane;
	private JComboBox<String> comboBox;
	private JTextPane txtpnPlayerInfo, txtpnGeneralInfo;
	private ArrayList<AreaView> areas;
	private ArrayList<CardView> cards;
	private HashMap<String, JButton> buttons = new HashMap<String, JButton>();
	
	private HashMap<String, int[]> areaBounds = new HashMap<String, int[]>();
	
	private int selectionState;
	private final int normalState = 0;
	private final int moveState = 1;
	private final int roleState = 2;
	private boolean changingComboBox = false;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frmDeadwood.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	private MainWindow() {
		initialize();
		try {
			frmDeadwood.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static MainWindow getInstance() {
		if (instance == null) instance = new MainWindow();
		return instance; 
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		// Set the selection state
		selectionState = normalState;
		
		// Initialize bounds of the areas
		areaBounds.put("Train Station", new int[] {0, 0, 225, 450});
		areaBounds.put("Jail", new int[] {225, 0, 450, 225});
		areaBounds.put("General Store", new int[] {225, 225, 450, 225});
		areaBounds.put("Main Street", new int[] {675, 0, 675, 225});
		areaBounds.put("Saloon", new int[] {675, 225, 450, 225});
		areaBounds.put("Hotel", new int[] {1125, 450, 225, 450});
		areaBounds.put("Bank", new int[] {675, 450, 450, 225});
		areaBounds.put("Church", new int[] {675, 675, 450, 225});
		areaBounds.put("Ranch", new int[] {225, 450, 450, 225});
		areaBounds.put("Secret Hideout", new int[] {0, 675, 675, 225});
		areaBounds.put("Casting Office", new int[] {0, 450, 225, 225});
		areaBounds.put("Trailer", new int[] {1125, 225, 225, 225});
		
		frmDeadwood = new JFrame();
		frmDeadwood.getContentPane().setBackground(new Color(250, 235, 215));
		frmDeadwood.setResizable(false);
		frmDeadwood.setFont(null);
		frmDeadwood.setForeground(Color.BLACK);
		frmDeadwood.setBackground(Color.BLACK);
		frmDeadwood.setTitle("Deadwood");
		frmDeadwood.setBounds(50, 10, 1620, 935);
		frmDeadwood.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmDeadwood.getContentPane().setLayout(null);
		
		boardView = new JLayeredPane();
		boardView.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		boardView.setBounds(0, 0, 1350, 900);
		frmDeadwood.getContentPane().add(boardView);
		
		
		//-------------------------------Initialize the menu area-------------------------------------------
		menuBar = new JLayeredPane();
		menuBar.setBorder(new LineBorder( new Color(0, 0, 0), 2));
		menuBar.setBounds(1350, 0, 264, 900);
		frmDeadwood.getContentPane().add(menuBar);
		
		playerInfoPane = new JLayeredPane();
		playerInfoPane.setBorder(new LineBorder(new Color(244, 164, 96), 3));
		playerInfoPane.setBounds(0, 0, 264, 225);
		menuBar.add(playerInfoPane);
		
		comboBox = new JComboBox();
		comboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if (!changingComboBox)
					updatePlayerInfo(String.valueOf(comboBox.getSelectedItem()));
			}
		});
		comboBox.setBounds(12, 13, 240, 22);
		playerInfoPane.add(comboBox);
		
		txtpnPlayerInfo = new JTextPane();
		txtpnPlayerInfo.setEditable(false);
		txtpnPlayerInfo.setText("Player info");
		txtpnPlayerInfo.setBounds(12, 48, 240, 164);
		playerInfoPane.add(txtpnPlayerInfo);
		
		generalInfoPane = new JLayeredPane();
		generalInfoPane.setBorder(new LineBorder(new Color(244, 164, 96), 3));
		generalInfoPane.setBounds(0, 225, 264, 396);
		menuBar.add(generalInfoPane);
		
		txtpnGeneralInfo = new JTextPane();
		txtpnGeneralInfo.setEditable(false);
		txtpnGeneralInfo.setText("General Info");
		txtpnGeneralInfo.setBounds(12, 13, 240, 370);
		generalInfoPane.add(txtpnGeneralInfo);
		
		controlPane = new JLayeredPane();
		controlPane.setBorder(new LineBorder(new Color(244, 164, 96), 3));
		controlPane.setBounds(0, 621, 264, 279);
		menuBar.add(controlPane);
		
		JButton btnMove = new JButton("Move");
		btnMove.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(btnMove.isEnabled()) {
					moveClicked();
				}
			}
		});
		btnMove.setBounds(12, 13, 240, 25);
		controlPane.add(btnMove);
		buttons.put("Move", btnMove);
		
		JButton btnTakeRole = new JButton("Take Role");
		btnTakeRole.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(btnTakeRole.isEnabled()) {
					takeRoleClicked();
				}
			}
		});
		btnTakeRole.setBounds(12, 51, 240, 25);
		controlPane.add(btnTakeRole);
		buttons.put("Take Role", btnTakeRole);
		
		JButton btnAct = new JButton("Act");
		btnAct.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(btnAct.isEnabled()) {
					actClicked();
				}
			}
		});
		btnAct.setBounds(12, 89, 240, 25);
		controlPane.add(btnAct);
		buttons.put("Act", btnAct);
		
		JButton btnRehearse = new JButton("Rehearse");
		btnRehearse.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(btnRehearse.isEnabled()) {
					rehearseClicked();
				}
			}
		});
		btnRehearse.setBounds(12, 127, 240, 25);
		controlPane.add(btnRehearse);
		buttons.put("Rehearse", btnRehearse);
		
		JButton btnUpgrade = new JButton("Upgrade");
		btnUpgrade.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(btnUpgrade.isEnabled()) {
					upgradeClicked();
				}
			}
		});
		btnUpgrade.setBounds(12, 165, 240, 25);
		controlPane.add(btnUpgrade);
		buttons.put("Upgrade", btnUpgrade);
		
		JButton btnEndTurn = new JButton("End Turn");
		btnEndTurn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(btnEndTurn.isEnabled()) {
					endTurnClicked();
				}
			}
		});
		btnEndTurn.setBounds(12, 203, 240, 25);
		controlPane.add(btnEndTurn);
		buttons.put("End Turn", btnEndTurn);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(btnCancel.isEnabled()) {
					cancelClicked();
				}
			}
		});
		btnCancel.setBounds(12, 241, 240, 25);
		controlPane.add(btnCancel);
		buttons.put("Cancel", btnCancel);
	}

	public int getNumPlayers(){
		Integer[] possiblePlayerNum = {2, 3, 4, 5, 6, 7, 8};
		return (Integer) JOptionPane.showInputDialog(null, "Please choose number of players:", "Input", JOptionPane.INFORMATION_MESSAGE, null, possiblePlayerNum, possiblePlayerNum[0]);
	}

	public String getPlayerName(int playerNum){ // called by Controller
		
		String[] options = {"OK"};
		JPanel panel = new JPanel();
		JLabel lbl = new JLabel("Please enter a 3 character name for player number " + playerNum + ":");
		JTextField txt = new JTextField(3);
		panel.add(lbl);
		panel.add(txt);
		txt.requestFocus();
		int selectedOption = JOptionPane.showOptionDialog(null, panel, "Let's get to know each other", JOptionPane.NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options , options[0]);

		if(selectedOption == 0)
		{
		    return txt.getText();
		}
		
		return null;
	}
	
	public void displayWinner(String message) {

		String[] options = {"Exit"};
		JPanel panel = new JPanel();
		JLabel lbl = new JLabel(message);
		panel.add(lbl);
		int selectedOption = JOptionPane.showOptionDialog(null, panel, "There are no more yees left to haw.", JOptionPane.NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options , options[0]);

		if(selectedOption == 0)
		{
		    frmDeadwood.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		    System.exit(0);
		}
	}
	
	public void buildAreas(ArrayList<AreaView> areas) { // called by Controller
		
		this.areas = areas;

		for (AreaView area: this.areas) {
			System.out.println(area.getAreaName());
			area.buildAreaView(areaBounds.get(area.getAreaName()));
			boardView.add(area.getAreaPane());
		}
	}
	
	public void buildCards(ArrayList<CardView> cards) {
		this.cards = cards;
		
		for (CardView card: this.cards) {
			System.out.println(card.getTitle());
			card.build();
		}
		System.out.println("CARDS WERE BUILT!!!! --------------------------");
	}
	
	public void setActivePlayer(String playerName) {
		updatePlayerInfo(playerName);
		JOptionPane.showMessageDialog(frmDeadwood, "It is " + playerName + "'s turn!");
	}
	
	private void moveClicked() {
		// TODO: set selectionState to moveState and disable all buttons except cancel.
		this.selectionState = this.moveState;
		this.disableButtons();
	}
	
	private void takeRoleClicked() {
		// TODO: set selectionState to roleState and disable all buttons except cancel.
		this.selectionState = this.roleState;
		this.disableButtons();
	}
	
	private void actClicked() {
		Controller.tryAct();
	}

	public void actStatus(String performance){
		JOptionPane.showMessageDialog(null, performance);
	}
	
	private void rehearseClicked() {
		Controller.tryRehearse();
	}
	
	private void upgradeClicked() {
		Integer[] possibleRanks = {2, 3, 4, 5, 6, 7, 8};
		String[] possibleCurrencies = {"dollars", "credits"};
		int rank = (Integer) JOptionPane.showInputDialog(null, "Please select rank to upgrade to:", "Input", JOptionPane.INFORMATION_MESSAGE, null, possibleRanks, possibleRanks[0]);
		String currency = (String) JOptionPane.showInputDialog(null, "Please select currency to use for upgrade:", "Input", JOptionPane.INFORMATION_MESSAGE, null, possibleCurrencies, possibleCurrencies[0]);
		Controller.tryUpgrade(rank, currency);
	}

	public void displayUpgradeError() {
		JOptionPane.showMessageDialog(null, "Error: Upgrade unsuccessful.");
	}
	
	private void endTurnClicked() {
		selectionState = normalState;
		Controller.turnUpdate();
	}

	private void cancelClicked() {
		// TODO: enable appropriate buttons and set selectionState to normal.
		selectionState = normalState;
		askForEnabledButtons();
	}
	
	public void areaClicked(String areaName) {
		switch (selectionState) {
		case normalState:
			Controller.displayAreaState(areaName);
			break;
		case moveState:
			Controller.tryMove(areaName);
			break;
		case roleState:
			break;
		}
	}
	
	public void roleClicked(String roleName) {
		switch (selectionState) {
		case normalState:
			Controller.displayRoleState(roleName);
			break;
		case moveState:
			break;
		case roleState:
			Controller.tryTakeRole(roleName);
			break;
		}
	}
	
	public void cardClicked(String cardTitle) {
		switch (selectionState) {
		case normalState:
			Controller.displayCardState(cardTitle);
			break;
		case moveState:
			break;
		case roleState:
			break;
		}
	}
	
	// Called by Controller
	public void updateEnabledButtons(ArrayList<String> validActions) {
		this.selectionState = normalState;
		// TODO: update which buttons are enabled based on the general validity check in ruleManager
		for(String action : buttons.keySet()){
			if(validActions.contains(action)){
				buttons.get(action).setEnabled(true);
			} else {
				buttons.get(action).setEnabled(false);
			}
		}
		buttons.get("Cancel").setEnabled(true);
	}
	
	private void askForEnabledButtons() {
		Controller.updateViewValidActions();
	}
	
	public void disableButtons() {
		// TODO: disable all buttons except cancel.
		for (String key : buttons.keySet()) {
			if (!key.equalsIgnoreCase("Cancel")) buttons.get(key).setEnabled(false);
		}
	}
	
	public void updatePlayerInfo(String name) {
		for (int i = 0; i < comboBox.getItemCount(); i++) {
			if (comboBox.getItemAt(i).toString().equals(name)) {
				changingComboBox = true;
				comboBox.setSelectedIndex(i);
				String message = Controller.displayPlayerState(comboBox.getSelectedItem().toString());
				txtpnPlayerInfo.setText(message);
				changingComboBox = false;
				//System.out.println("------------------------ Updated " + name);
			}
		}
	}
	
	public void updateGeneralInfo(String message) {
		txtpnGeneralInfo.setText(message);
	}
	
	public void sendPlayersToTrailers(ArrayList<String> playerNames) {
		//Remove players from areas
		areas.stream().forEach(a -> a.clearPlayers());
		//Remove players from roles
		areas.parallelStream().forEach(a -> a.clearRoles());
		//Send players to trailers
		playerNames.stream().forEach(p -> addPlayerToArea(p, "Trailer"));
	}
	
	public void movePlayer(String playerName, String oldArea, String newArea) {
		removePlayerFromArea(playerName, oldArea);
		addPlayerToArea(playerName, newArea);
	}

	private void addPlayerToArea(String playerName, String areaName) {
		Optional<AreaView> area = areas.stream().filter(a -> a.getAreaName().equals(areaName)).findFirst();
		if (area.isPresent()) {
			area.get().addPlayer(playerName);
		}
		else System.out.println(areaName + " was not found in MainWindow.addPlayerToArea");
	}

	private void removePlayerFromArea(String playerName, String areaName) {
		Optional<AreaView> area = areas.stream().filter(a -> a.getAreaName().equals(areaName)).findFirst();
		if (area.isPresent()) {
			area.get().removePlayer(playerName);
		}
		else System.out.println(areaName + " was not found in MainWindow.removePlayerFromArea");
	}
	
	public void addToRole(String playerName, String areaName, String roleName) {
		Optional<AreaView> area = areas.stream().filter(a -> a.getAreaName().equals(areaName)).findFirst();
		if (area.isPresent()){
			// check if role is on the area or the card
			ArrayList<RoleView> roles = area.get().getRoles();
			Optional<RoleView> role = roles.stream().filter(r -> r.getName().equals(roleName)).findFirst();
			if (role.isPresent()){
				role.get().setPlayer(playerName);
			}
			else {
				roles = area.get().getCard().getRoles();
				role = roles.stream().filter(r -> r.getName().equals(roleName)).findFirst();
				if (role.isPresent()){
					role.get().setPlayer(playerName);
				}
				else{
					System.out.println(roleName + " in " + areaName + " was not found in MainWindow.addToRole, either on area or on card");
				}
			}
		}
		else System.out.println(areaName + " was not found in MainWindow.addToRole");
	}
	
	public void removeFromRole(String areaName, String roleName) {
		Optional<AreaView> area = areas.stream().filter(a -> a.getAreaName().equals(areaName)).findFirst();
		if (area.isPresent()){
			// check if role is on the area or the card
			ArrayList<RoleView> roles = area.get().getRoles();
			Optional<RoleView> role = roles.stream().filter(r -> r.getName().equals(roleName)).findFirst();
			if (role.isPresent()){
				role.get().setPlayer(null);
			}
			else {
				roles = area.get().getCard().getRoles();
				role = roles.stream().filter(r -> r.getName().equals(roleName)).findFirst();
				if (role.isPresent()){
					role.get().setPlayer(null);
				}
				else{
					System.out.println(roleName + " in " + areaName + " was not found in MainWindow.removeFromRole, either on area or on card");
				}
			}
		}
		else System.out.println(areaName + " was not found in MainWindow.removeFromRole");
	}

	public void decrementShot(String areaName) {
		Optional<AreaView> area = areas.stream().filter(a -> a.getAreaName().equals(areaName)).findFirst();
		if (area.isPresent()) {
			int shots = area.get().getShotsLeft();
			area.get().setShotsLeft(shots - 1);
		}
	}

	public ArrayList<AreaView> getAreas(){
		return areas;
	}
	
	public AreaView getAreaByName(String areaName) {
		Optional<AreaView> area = areas.stream().filter(a -> a.getAreaName().equalsIgnoreCase(areaName)).findFirst();
		if (area.isPresent()) return area.get();
		System.out.println("tried getting the areaView but it didn't exist: " + areaName);
		return null;
	}
	
	public ArrayList<CardView> getCards(){
		return cards;
	}
	
	public CardView getCardByTitle(String cardTitle) {
		Optional<CardView> card = cards.stream().filter(c -> c.getTitle().equalsIgnoreCase(cardTitle)).findFirst();
		if (card.isPresent()) return card.get();
		System.out.println("tried getting the cardView but it didn't exist: "+ cardTitle);
		return null;
	}
	
	public void setPlayers(ArrayList<String> names) {
		for (String name: names) {
			comboBox.addItem(name);
		}
	}

	public void displayMoveError(String areaName) {

		String[] options = {"OK"};
		JPanel panel = new JPanel();
		JLabel lbl = new JLabel("You are currently unable to move to " + areaName + ".");
		panel.add(lbl);
		int selectedOption = JOptionPane.showOptionDialog(null, panel, "Uh oh...", JOptionPane.NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options , options[0]);
	}

	public void displayRehearseError() {
		String[] options = {"OK"};
		JPanel panel = new JPanel();
		JLabel lbl = new JLabel("Their ain't no time to rehearse right now!");
		panel.add(lbl);
		int selectedOption = JOptionPane.showOptionDialog(null, panel, "Uh oh...", JOptionPane.NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options , options[0]);
	}

	public void displayTakeRoleError(String roleName) {
		String[] options = {"OK"};
		JPanel panel = new JPanel();
		JLabel lbl = new JLabel("You aren't allowed to take the role " + roleName + " right now.");
		panel.add(lbl);
		int selectedOption = JOptionPane.showOptionDialog(null, panel, "Uh oh...", JOptionPane.NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options , options[0]);	
	}

	public void displayActError() {
		String[] options = {"OK"};
		JPanel panel = new JPanel();
		JLabel lbl = new JLabel("Hold yer horses. You can't act right now.");
		panel.add(lbl);
		int selectedOption = JOptionPane.showOptionDialog(null, panel, "Uh oh...", JOptionPane.NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options , options[0]);	
	
	}

	public void displayNewDayMessage(int currentDay, int lastDay) {
		String[] options = {"OK"};
		JPanel panel = new JPanel();
		JLabel message1 = new JLabel("It's time for a new day! Today is day " + String.valueOf(currentDay)
									+ " out of " + String.valueOf(lastDay) + ".");
		JLabel message2 = new JLabel("All players have been moved to the trailer");
		panel.add(message1);
		panel.add(message2);
		int selectedOption = JOptionPane.showOptionDialog(null, panel, "Uh oh...", JOptionPane.NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options , options[0]);	
	
	}
	
}

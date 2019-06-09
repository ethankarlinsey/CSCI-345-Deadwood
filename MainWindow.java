import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.border.LineBorder;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

import javax.swing.JSpinner;
import javax.swing.JTree;
import java.awt.Choice;
import javax.swing.JComboBox;
import javax.swing.JTextPane;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class MainWindow {

	private JFrame frmDeadwood;
	//private Controller controller;
	private JLayeredPane boardView;
	private JLayeredPane menuBar;
	private JLayeredPane playerInfoPane, generalInfoPane, controlPane;
	private JComboBox comboBox;
	private ArrayList<AreaView> areas;
	private ArrayList<CardView> cards;
	private HashMap<String, JButton> buttons = new HashMap<String, JButton>();
	
	private HashMap<String, int[]> areaBounds = new HashMap<String, int[]>();
	
	private int selectionState;
	private final int normalState = 0;
	private final int moveState = 1;
	private final int roleState = 2;
	private JPanel testCard_1;

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
	public MainWindow() {
		initialize();
		try {
			frmDeadwood.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
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
				updatePlayerInfo(String.valueOf(comboBox.getSelectedItem()));
			}
		});
		comboBox.setBounds(12, 13, 240, 22);
		playerInfoPane.add(comboBox);
		
		JTextPane txtpnPlayerInfo = new JTextPane();
		txtpnPlayerInfo.setEditable(false);
		txtpnPlayerInfo.setText("Player info");
		txtpnPlayerInfo.setBounds(12, 48, 240, 164);
		playerInfoPane.add(txtpnPlayerInfo);
		
		generalInfoPane = new JLayeredPane();
		generalInfoPane.setBorder(new LineBorder(new Color(244, 164, 96), 3));
		generalInfoPane.setBounds(0, 225, 264, 396);
		menuBar.add(generalInfoPane);
		
		JTextPane txtpnGeneralInfo = new JTextPane();
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
				moveClicked();
			}
		});
		btnMove.setBounds(12, 13, 240, 25);
		controlPane.add(btnMove);
		buttons.put("Move", btnMove);
		
		JButton btnTakeRole = new JButton("Take Role");
		btnTakeRole.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				takeRoleClicked();
			}
		});
		btnTakeRole.setBounds(12, 51, 240, 25);
		controlPane.add(btnTakeRole);
		buttons.put("Take Role", btnTakeRole);
		
		JButton btnAct = new JButton("Act");
		btnAct.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				actClicked();
			}
		});
		btnAct.setBounds(12, 89, 240, 25);
		controlPane.add(btnAct);
		buttons.put("Act", btnAct);
		
		JButton btnRehearse = new JButton("Rehearse");
		btnRehearse.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				rehearseClicked();
			}
		});
		btnRehearse.setBounds(12, 127, 240, 25);
		controlPane.add(btnRehearse);
		buttons.put("Rehearse", btnRehearse);
		
		JButton btnUpgrade = new JButton("Upgrade");
		btnUpgrade.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				upgradeClicked();
			}
		});
		btnUpgrade.setBounds(12, 165, 240, 25);
		controlPane.add(btnUpgrade);
		buttons.put("Upgrade", btnUpgrade);
		
		JButton btnEndTurn = new JButton("End Turn");
		btnEndTurn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				endTurnClicked();
			}
		});
		btnEndTurn.setBounds(12, 203, 240, 25);
		controlPane.add(btnEndTurn);
		buttons.put("End Turn", btnEndTurn);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				cancelClicked();
			}
		});
		btnCancel.setBounds(12, 241, 240, 25);
		controlPane.add(btnCancel);
		buttons.put("Cancel", btnCancel);
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
	
	private void moveClicked() {
		// TODO: set selectionState to moveState and disable all buttons except cancel.
	}
	
	private void takeRoleClicked() {
		// TODO: set selectionState to roleState and disable all buttons except cancel.
	}
	
	private void actClicked() {
		
	}
	
	private void rehearseClicked() {
		
	}
	
	private void upgradeClicked() {
		
	}
	
	private void endTurnClicked() {
		
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
			break;
		case moveState:
			break;
		case roleState:
			break;
		}
	}
	
	// Called by Controller
	public void updateEnabledButtons(ArrayList<String> validActions) {
		// TODO: update which buttons are enabled based on the general validity check in ruleManager
	}
	
	private void askForEnabledButtons() {
		// TODO: call a getValidActions method in Controller.
	}
	
	public void disableButtons() {
		// TODO: disable all buttons except cancel.
		for (String key : buttons.keySet()) {
			if (!key.equalsIgnoreCase("Cancel")) buttons.get(key).setEnabled(false);
		}
	}
	
	public void updatePlayerInfo(String name) {
		comboBox.setSelectedItem(name);
		//TODO: get playerInfoMessage and display it in the text box.
	}
	
	public void updateGeneralInfo(String message) {
		
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
		else System.out.println(areaName + " was not found in MainWindow.addPlayerToArea");
	}
	
	public void addToRole(String playerName, String areaName, String roleName) {
		
	}
	
	public void removeFromRole(String playerName, String areaName, String roleName) {
		
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
}

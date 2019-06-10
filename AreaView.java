import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

public class AreaView {

	private int shotsLeft;
	private JLayeredPane areaPane = new JLayeredPane();
	private JLabel labelTitle = new JLabel();
	private JLabel labelShots = new JLabel();
	private JLabel labelOccupiedBy = new JLabel();
	private CardView card;
	private ArrayList<RoleView> roles = new ArrayList<RoleView>();
	ArrayList<String> players = new ArrayList<String>();
	private JPanel cardPanel = new JPanel();
	
	private MainWindow view;
	
	private int[][] verticalRoleBounds = {
			{12, 309, 95, 58},
			{117, 308, 95, 58},
			{12, 379, 95, 58},
			{117, 379, 95, 58}
			};
	private int[][] horizontalRoleBounds = {
			{236, 84, 95, 58},
			{343, 84, 95, 58},
			{236, 155, 95, 58},
			{343, 155, 95, 58}
			};
	private int[][] roleBounds;
	
	public JLayeredPane getPane() {
		return areaPane;
	}
	
	public AreaView(String areaName, MainWindow view) {
		this.view = view;
		this.setAreaName(areaName);
		labelOccupiedBy.setText("Occupied by: ");
	}
	
	public void buildAreaView(int[] bounds) {
		
		areaPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				view.areaClicked(getAreaName());
			}
		});
		areaPane.setBorder(new LineBorder(new Color(244, 164, 96), 3));
		areaPane.setBounds(bounds[0], bounds[1], bounds[2], bounds[3]); //TODO: add panel to the main window in MainWindow
		
		//Determine construction from the length and width
		if (bounds[2] == 225 && bounds[3] == 450) buildVerticalPane(areaPane);
		else if (bounds[2] == 450 && bounds[3] == 225) buildHorizontalPane(areaPane);
		else if (bounds[2] == 675 && bounds[3] == 225) buildLongPane(areaPane);
		else buildNonSetAreas(areaPane);
		
		areaPane.add(cardPanel);
	}
	
	//TODO: implement construction of non-set areas
	public void buildNonSetAreas(JLayeredPane pane) {
		
		labelTitle.setLabelFor(pane);
		labelTitle.setFont(new Font("Tahoma", Font.BOLD, 13));
		labelTitle.setBounds(12, 13, 200, 16);
		pane.add(labelTitle);
		
		labelOccupiedBy.setLabelFor(pane);
		labelOccupiedBy.setBounds(12, 40, 200, 16);
		pane.add(labelOccupiedBy);
		
		JTextPane messageBox = new JTextPane();
		messageBox.setEditable(false);
		messageBox.setText("General Info");
		messageBox.setBounds(12, 68, 200, 145);
		pane.add(messageBox);
		
		if (getAreaName().equalsIgnoreCase("Trailer")) {
			messageBox.setText("Start here every day\n");
		}
		if (getAreaName().equalsIgnoreCase("Casting Office" )) {
			String message = "Pay dollars OR credits to upgrade\n"
					+ "Rank" + "\t" + "Dollars" + "\t" + "Credits\n"
					+ "2" + "\t" + "4" + "\t" + "5" + "\n"
					+ "3" + "\t" + "10" + "\t" + "10" + "\n"
					+ "4" + "\t" + "18" + "\t" + "15" + "\n"
					+ "5" + "\t" + "28" + "\t" + "25" + "\n";
			messageBox.setText(message);
		}
		else System.out.println("In AreaView - buildNonSetAreas - the area name was wrong");
	}
	
	//TODO: integrate pane builders with the controller and boardModel
	public void buildVerticalPane(JLayeredPane pane) {
		
		roleBounds = verticalRoleBounds;
		
		//Panel Labels
		labelTitle.setLabelFor(pane);
		labelTitle.setFont(new Font("Tahoma", Font.BOLD, 13));
		labelTitle.setBounds(12, 233, 200, 16);
		pane.add(labelTitle);
		
		labelShots.setLabelFor(pane);
		labelShots.setBounds(12, 262, 200, 16);
		pane.add(labelShots);
		
		labelOccupiedBy.setLabelFor(pane);
		labelOccupiedBy.setBounds(12, 280, 200, 16);
		pane.add(labelOccupiedBy);
		
		buildRoles();
	}
	
	public void buildHorizontalPane(JLayeredPane pane) {
		
		roleBounds = horizontalRoleBounds;
		
		//begin building subcomponents
		labelTitle.setBounds(238, 13, 200, 16);
		pane.add(labelTitle);
		labelTitle.setFont(new Font("Tahoma", Font.BOLD, 13));
		
		labelShots.setBounds(238, 42, 200, 16);
		pane.add(labelShots);
		
		labelOccupiedBy.setBounds(238, 56, 200, 16);
		pane.add(labelOccupiedBy);
		
		buildRoles();
	}
	
	public void buildLongPane(JLayeredPane pane) {
		buildHorizontalPane(pane);
	}
	
	public void buildRoles() {
		for (int i = 0; i < roles.size(); i++) {
			roles.get(i).buildRoleView(roleBounds[i]);
		}
	}
	
	public void replaceCard(CardView card) {
		areaPane.remove(cardPanel);
		this.card = card;
		// this.cardPanel = this.card.getPanelInvisible(); // Should set card invisible by default.
		this.cardPanel = this.card.getPanelVisible(); // setting to visible for testing purposes.
		System.out.println("CARDS WERE REPLACED -------- " + card.getTitle());
		areaPane.add(cardPanel);
	}

	public String getAreaName() {
		return labelTitle.getText();
	}

	public void setAreaName(String areaName) {
		this.labelTitle.setText(areaName);
	}

	public ArrayList<RoleView> getRoles() {
		return roles;
	}

	public void setRoles(ArrayList<RoleView> roles) {
		this.roles = roles;
		for (RoleView r: roles) {
			areaPane.add(r.getPanel());
		}
	}
	public void addRole(RoleView role) {
		this.roles.add(role);
		areaPane.add(role.getPanel());
	}
	public RoleView getRole(String roleName) {
		return null; //TODO: implement this
	}

	public ArrayList<String> getPlayers() {
		return players;
	}

	public void setPlayers(ArrayList<String> players) {
		this.players = players;
		
		if (players == null) {
			this.labelOccupiedBy.setText("Occupied by: empty");
			return;
		}
		
		String occupancy = "Occupied by:";
		for (String p : this.players) {
			occupancy += " " + p;
		}
		
		this.labelOccupiedBy.setText(occupancy);
	}
	
	public void addPlayer(String name) {
		players.add(name);
		setPlayers(players);
		System.out.println("added " + name);
	}
	
	public void removePlayer(String name) {
		if (players.remove(name)) System.out.println("sucessfully removed " + name);
		else System.out.println("could not remove " + name + " probably because is doesn't exist");
		setPlayers(players);
	}

	public JLayeredPane getAreaPane() {
		return areaPane;
	}
	public int getShotsLeft() {
		return shotsLeft;
	}

	public void setShotsLeft(int shotsLeft) {
		this.shotsLeft = shotsLeft;
		this.labelShots.setText("Shots left: " + String.valueOf(this.shotsLeft));
	}
	
}

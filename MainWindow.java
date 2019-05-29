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
import java.util.HashMap;

public class MainWindow {

	private JFrame frmDeadwood;
	
	private HashMap<String, int[]> areaBounds = new HashMap<String, int[]>();

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
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
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
		
		JLayeredPane panelBoard = new JLayeredPane();
		panelBoard.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panelBoard.setBounds(0, 0, 1350, 900);
		frmDeadwood.getContentPane().add(panelBoard);
		
		buildAreas(panelBoard);
		
		//-------------------------------Initialize the menu area-------------------------------------------
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(1350, 0, 270, 900);
		frmDeadwood.getContentPane().add(tabbedPane);
	}
	
	public void buildAreas(JLayeredPane panelBoard) {
		
		//Initialize train station
		areaBounds.put("Train Station", new int[] {0, 0, 225, 450});
		buildAreaPanel(panelBoard, "Train Station");

		//Initialize Jail
		areaBounds.put("Jail", new int[] {225, 0, 450, 225});
		buildAreaPanel(panelBoard, "Jail");
		
		//Initialize General Store
		areaBounds.put("General Store", new int[] {225, 225, 450, 225});
		buildAreaPanel(panelBoard, "General Store");
		
		//Initialize Main Street
		areaBounds.put("Main Street", new int[] {675, 0, 675, 225});
		buildAreaPanel(panelBoard, "Main Street");
		
		//Initialize Saloon
		areaBounds.put("Saloon", new int[] {675, 225, 450, 225});
		buildAreaPanel(panelBoard, "Saloon");
		
		//Initialize Hotel
		areaBounds.put("Hotel", new int[] {1125, 450, 225, 450});
		buildAreaPanel(panelBoard, "Hotel");
		
		//Initialize Bank
		areaBounds.put("Bank", new int[] {675, 450, 450, 225});
		buildAreaPanel(panelBoard, "Bank");
		
		//Initialize Church
		areaBounds.put("Church", new int[] {675, 675, 450, 225});
		buildAreaPanel(panelBoard, "Church");
		
		//Initialize Ranch
		areaBounds.put("Ranch", new int[] {225, 450, 450, 225});
		buildAreaPanel(panelBoard, "Ranch");
		
		//Initialize Secret Hideout
		areaBounds.put("Secret Hideout", new int[] {0, 675, 675, 225});
		buildAreaPanel(panelBoard, "Secret Hideout");
		
		
		//Initialize non-set areas
		
		//Initialize Casting Office
		areaBounds.put("Casting Office", new int[] {0, 450, 225, 225});
		buildAreaPanel(panelBoard, "Casting Office");
		
		//Initialize Trailers
		areaBounds.put("Trailers", new int[] {1125, 225, 225, 225});
		buildAreaPanel(panelBoard, "Trailers");
		
		//TODO: add trailer builder?
		
	}
	
	public void buildAreaPanel(JLayeredPane basePanel, String areaName) {
		int[] bounds = areaBounds.get(areaName);
		JLayeredPane areaPanel = new JLayeredPane();
		areaPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JOptionPane.showMessageDialog(null, "Clicked on " + areaName); // TODO: add listener functionality
			}
		});
		areaPanel.setBorder(new LineBorder(new Color(244, 164, 96), 3));
		areaPanel.setBounds(bounds[0], bounds[1], bounds[2], bounds[3]);
		basePanel.add(areaPanel);
		
		//Determine construction from the length and width
		if (bounds[2] == 225 && bounds[3] == 450) buildVerticalPane(areaPanel, areaName);
		else if (bounds[2] == 450 && bounds[3] == 225) buildHorizontalPane(areaPanel, areaName);
		else if (bounds[2] == 675 && bounds[3] == 225) buildLongPane(areaPanel, areaName);
		else buildNonSetAreas(areaPanel);
	}
	
	public void buildNonSetAreas(JLayeredPane pane) {
		
	}
	
	//TODO: integrate pane builders with the controller and boardModel
	public void buildVerticalPane(JLayeredPane pane, String areaName) {
		
		//Panel Labels
		JLabel labelTitle = new JLabel(areaName);
		labelTitle.setLabelFor(pane);
		labelTitle.setFont(new Font("Tahoma", Font.BOLD, 13));
		labelTitle.setBounds(12, 233, 200, 16);
		pane.add(labelTitle);
		
		JLabel labelShots = new JLabel("Shots left: ");
		labelShots.setLabelFor(pane);
		labelShots.setBounds(12, 262, 200, 16);
		pane.add(labelShots);
		
		JLabel labelOccupiedBy = new JLabel("Occupied by:");
		labelOccupiedBy.setLabelFor(pane);
		labelOccupiedBy.setBounds(12, 280, 200, 16);
		pane.add(labelOccupiedBy);
		
		//Panel Card TODO: add buildCard() method
		JPanel panelCard = new JPanel();
		panelCard.setBounds(12, 13, 200, 200);
		pane.add(panelCard);
		panelCard.setBackground(new Color(139, 69, 19));
		
		
		//Build the 1st role
		JPanel panelRole1 = new JPanel();
		panelRole1.setBounds(12, 309, 95, 58);	// NOTE: location setting is kept outside the buildRolePanel() method.
		pane.add(panelRole1);
		
		buildRolePanel(panelRole1);
		
		//Build the 2nd role
		JPanel panelRole2 = new JPanel();
		panelRole2.setBounds(117, 308, 95, 58);
		pane.add(panelRole2);
		
		buildRolePanel(panelRole2);
		
		
		//Build the 3rd role (if necessary)
		JPanel panelRole3 = new JPanel();
		panelRole3.setBounds(12, 379, 95, 58);
		pane.add(panelRole3);
		
		buildRolePanel(panelRole3);
		
		//Build the 4th role (if necessary)
		JPanel panelRole4 = new JPanel();
		panelRole4.setBounds(117, 379, 95, 58);
		pane.add(panelRole4);
		
		buildRolePanel(panelRole4);
		
	}
	
	public void buildHorizontalPane(JLayeredPane pane, String areaName) {
		
		//begin building subcomponents
		JLabel labelTitle = new JLabel(areaName);
		labelTitle.setBounds(238, 13, 200, 16);
		pane.add(labelTitle);
		labelTitle.setFont(new Font("Tahoma", Font.BOLD, 13));
		
		JLabel labelShots = new JLabel("Shots left: ");
		labelShots.setBounds(238, 42, 200, 16);
		pane.add(labelShots);
		
		JLabel labelOccupiedBy = new JLabel("Occupied by:");
		labelOccupiedBy.setBounds(238, 56, 200, 16);
		pane.add(labelOccupiedBy);
		
		JPanel card = new JPanel();
		card.setBackground(new Color(139, 69, 19));
		card.setBounds(12, 13, 200, 200);
		pane.add(card);
		
		JPanel panelRole1 = new JPanel();
		panelRole1.setBounds(236, 84, 95, 58);
		pane.add(panelRole1);
		
		buildRolePanel(panelRole1);
		
		JPanel panelRole2 = new JPanel();
		panelRole2.setBounds(343, 84, 95, 58);
		pane.add(panelRole2);
		
		buildRolePanel(panelRole2);
		
		JPanel panelRole3 = new JPanel();
		panelRole3.setBounds(236, 155, 95, 58);
		pane.add(panelRole3);
		
		buildRolePanel(panelRole3);
		
		JPanel panelRole4 = new JPanel();
		panelRole4.setBounds(343, 155, 95, 58);
		pane.add(panelRole4);
		
		buildRolePanel(panelRole4);
		
	}
	
	public void buildLongPane(JLayeredPane pane, String areaName) {
		buildHorizontalPane(pane, areaName);
	}
	
	//TODO: add roleName as arg
	public void buildRolePanel(JPanel panel) {
		
		panel.setLayout(null);
		
		panel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//TODO: add funcionality to add roles
				
				JOptionPane.showMessageDialog(null, "Clicked on the role");
			}
		});
		
		JLabel labelName = new JLabel("RoleName");
		labelName.setLabelFor(panel);
		labelName.setHorizontalAlignment(SwingConstants.CENTER);
		labelName.setBounds(0, 0, 95, 16);
		panel.add(labelName);
		
		JLabel labelRank = new JLabel("Rank:");
		labelRank.setLabelFor(panel);
		labelRank.setHorizontalAlignment(SwingConstants.CENTER);
		labelRank.setBounds(0, 29, 95, 16);
		panel.add(labelRank);
		
		JLabel labelLine = new JLabel("Line");
		labelLine.setLabelFor(panel);
		labelLine.setHorizontalAlignment(SwingConstants.CENTER);
		labelLine.setBounds(0, 13, 95, 16);
		panel.add(labelLine);
		
		JLabel labelActor = new JLabel("Actor:");
		labelActor.setLabelFor(panel);
		labelActor.setHorizontalAlignment(SwingConstants.CENTER);
		labelActor.setBounds(5, 42, 85, 16);
		panel.add(labelActor);
		
	}
}

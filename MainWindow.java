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

public class MainWindow {

	private JFrame frmDeadwood;

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
		JLayeredPane panelTrainStation = new JLayeredPane();
		panelTrainStation.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JOptionPane.showMessageDialog(null, "Clicked on Train Station"); // TODO: add listener functionality
			}
		});
		panelTrainStation.setBorder(new LineBorder(new Color(244, 164, 96), 3));
		panelTrainStation.setBounds(0, 0, 225, 450);
		panelBoard.add(panelTrainStation);
		panelTrainStation.setLayout(null);

		buildVerticalPane(panelTrainStation);

		//Initialize Jail
		JLayeredPane panelJail = new JLayeredPane();
		panelJail.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JOptionPane.showMessageDialog(null, "Clicked on Jail"); // TODO: add listener functionality
			}
		});
		panelJail.setBorder(new LineBorder(new Color(244, 164, 96), 3));
		panelJail.setBounds(225, 0, 450, 225);
		panelBoard.add(panelJail);
		
		buildHorizontalPane(panelJail);
		
		//Initialize General Store
		JLayeredPane panelGeneralStore = new JLayeredPane();
		panelGeneralStore.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JOptionPane.showMessageDialog(null, "Clicked on General Store"); // TODO: add listener functionality
			}
		});
		panelGeneralStore.setBorder(new LineBorder(new Color(244, 164, 96), 3));
		panelGeneralStore.setBounds(225, 225, 450, 225);
		panelBoard.add(panelGeneralStore);
		
		buildHorizontalPane(panelGeneralStore);
		
		//Initialize Main Street
		JLayeredPane panelMainStreet = new JLayeredPane();
		panelMainStreet.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JOptionPane.showMessageDialog(null, "Clicked on Main Street"); // TODO: add listener functionality
			}
		});
		panelMainStreet.setBorder(new LineBorder(new Color(244, 164, 96), 3));
		panelMainStreet.setBounds(675, 0, 675, 225);
		panelBoard.add(panelMainStreet);
		
		buildLongPane(panelMainStreet);
		
		//Initialize Saloon
		JLayeredPane panelSaloon = new JLayeredPane();
		panelSaloon.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JOptionPane.showMessageDialog(null, "Clicked on Saloon"); // TODO: add listener functionality
			}
		});
		panelSaloon.setBorder(new LineBorder(new Color(244, 164, 96), 3));
		panelSaloon.setBounds(675, 225, 450, 225);
		panelBoard.add(panelSaloon);
		
		buildHorizontalPane(panelSaloon);
		
		//Initialize Hotel
		JLayeredPane panelHotel = new JLayeredPane();
		panelHotel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JOptionPane.showMessageDialog(null, "Clicked on Hotel"); // TODO: add listener functionality
			}
		});
		panelHotel.setBorder(new LineBorder(new Color(244, 164, 96), 3));
		panelHotel.setBounds(1125, 450, 225, 450);
		panelBoard.add(panelHotel);
		
		buildVerticalPane(panelHotel);
		
		//Initialize Bank
		JLayeredPane panelBank = new JLayeredPane();
		panelBank.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JOptionPane.showMessageDialog(null, "Clicked on Bank"); // TODO: add listener functionality
			}
		});
		panelBank.setBorder(new LineBorder(new Color(244, 164, 96), 3));
		panelBank.setBounds(675, 450, 450, 225);
		panelBoard.add(panelBank);
		
		buildHorizontalPane(panelBank);
		
		//Initialize Church
		JLayeredPane panelChurch = new JLayeredPane();
		panelChurch.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JOptionPane.showMessageDialog(null, "Clicked on Church"); // TODO: add listener functionality
			}
		});
		panelChurch.setBorder(new LineBorder(new Color(244, 164, 96), 3));
		panelChurch.setBounds(675, 675, 450, 225);
		panelBoard.add(panelChurch);
		
		buildHorizontalPane(panelChurch);
		
		//Initialize Ranch
		JLayeredPane panelRanch = new JLayeredPane();
		panelRanch.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JOptionPane.showMessageDialog(null, "Clicked on Ranch"); // TODO: add listener functionality
			}
		});
		panelRanch.setBorder(new LineBorder(new Color(244, 164, 96), 3));
		panelRanch.setBounds(225, 450, 450, 225);
		panelBoard.add(panelRanch);
		
		buildHorizontalPane(panelRanch);
		
		//Initialize Secret Hideout
		JLayeredPane panelSecretHideout = new JLayeredPane();
		panelSecretHideout.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JOptionPane.showMessageDialog(null, "Clicked on Secret Hideout"); // TODO: add listener functionality
			}
		});
		panelSecretHideout.setBorder(new LineBorder(new Color(244, 164, 96), 3));
		panelSecretHideout.setBounds(0, 675, 675, 225);
		panelBoard.add(panelSecretHideout);
		
		buildLongPane(panelSecretHideout);
		
		
		//Initialize non-set areas
		
		//Initialize Casting Office
		JLayeredPane panelCastingOffice = new JLayeredPane();
		panelCastingOffice.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JOptionPane.showMessageDialog(null, "Clicked on Casting Office"); // TODO: add listener functionality
			}
		});
		panelCastingOffice.setBorder(new LineBorder(new Color(244, 164, 96), 3));
		panelCastingOffice.setBounds(0, 450, 225, 225);
		panelBoard.add(panelCastingOffice);
		
		//Initialize Trailers
		JLayeredPane panelTrailers = new JLayeredPane();
		panelTrailers.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JOptionPane.showMessageDialog(null, "Clicked on Trailers"); // TODO: add listener functionality
			}
		});
		panelTrailers.setBorder(new LineBorder(new Color(244, 164, 96), 3));
		panelTrailers.setBounds(1125, 225, 225, 225);
		panelBoard.add(panelTrailers);
		
		//TODO: add trailer builder?
		
	}
	
	//TODO: integrate pane builders with the controller and boardModel
	public void buildVerticalPane(JLayeredPane pane) {
		
		//Panel Labels
		JLabel labelTitle = new JLabel("Title:");
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
	
	public void buildHorizontalPane(JLayeredPane pane) {
		
		//begin building subcomponents
		JLabel labelTitle = new JLabel("Title:");
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
	
	public void buildLongPane(JLayeredPane pane) {
		buildHorizontalPane(pane);
	}
	
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

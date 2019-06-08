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
import javax.swing.JSpinner;
import javax.swing.JTree;
import java.awt.Choice;
import javax.swing.JComboBox;
import javax.swing.JTextPane;

public class MainWindow {

	private JFrame frmDeadwood;
	//private Controller controller;
	private JLayeredPane boardView;
	private JLayeredPane menuBar;
	private JLayeredPane playerInfoPane, generalInfoPane, controlPane;
	
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
		//this.controller = controller;
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
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(12, 13, 240, 22);
		playerInfoPane.add(comboBox);
		
		JTextPane txtpnPlayerInfo = new JTextPane();
		txtpnPlayerInfo.setEditable(false);
		txtpnPlayerInfo.setText("Player info");
		txtpnPlayerInfo.setBounds(12, 48, 240, 164);
		playerInfoPane.add(txtpnPlayerInfo);
		
		generalInfoPane = new JLayeredPane();
		generalInfoPane.setBorder(new LineBorder(new Color(244, 164, 96), 3));
		generalInfoPane.setBounds(0, 225, 264, 474);
		menuBar.add(generalInfoPane);
		
		JTextPane txtpnGeneralInfo = new JTextPane();
		txtpnGeneralInfo.setEditable(false);
		txtpnGeneralInfo.setText("General Info");
		txtpnGeneralInfo.setBounds(12, 13, 240, 448);
		generalInfoPane.add(txtpnGeneralInfo);
		
		controlPane = new JLayeredPane();
		controlPane.setBorder(new LineBorder(new Color(244, 164, 96), 3));
		controlPane.setBounds(0, 698, 264, 202);
		menuBar.add(controlPane);
		
		JButton btnMove = new JButton("Move");
		btnMove.setBounds(12, 13, 240, 25);
		controlPane.add(btnMove);
		
		JButton btnTakeRole = new JButton("Take Role");
		btnTakeRole.setBounds(12, 51, 240, 25);
		controlPane.add(btnTakeRole);
		
		JButton btnAct = new JButton("Act");
		btnAct.setBounds(12, 89, 240, 25);
		controlPane.add(btnAct);
		
		JButton btnRehearse = new JButton("Rehearse");
		btnRehearse.setBounds(12, 127, 240, 25);
		controlPane.add(btnRehearse);
		
		JButton btnUpgrade = new JButton("Upgrade");
		btnUpgrade.setBounds(12, 165, 240, 25);
		controlPane.add(btnUpgrade);
	}
	
	public void buildAreas(ArrayList<AreaView> areas) { // called by Controller

		for (AreaView area: areas) {
			System.out.println(area.getAreaName());
			area.buildAreaView(areaBounds.get(area.getAreaName()));
			boardView.add(area.getAreaPane());
		}
	}
}

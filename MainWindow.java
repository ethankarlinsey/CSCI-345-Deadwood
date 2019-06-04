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

public class MainWindow {

	private JFrame frmDeadwood;
	//private Controller controller;
	private JLayeredPane boardView;
	
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
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(1350, 0, 270, 900);
		frmDeadwood.getContentPane().add(tabbedPane);
	}
	
	public void buildAreas(ArrayList<AreaView> areas) { // called by Controller

		for (AreaView area: areas) {
			System.out.println(area.getAreaName());
			area.buildAreaView(areaBounds.get(area.getAreaName()));
			boardView.add(area.getAreaPane());
		}
	}

}

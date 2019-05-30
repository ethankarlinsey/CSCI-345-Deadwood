import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

public class AreaView {

	String areaName;
	private JLayeredPane areaPane;
	private JLabel labelTitle;
	private JLabel labelOccupiedBy;
	private JPanel panelCard;
	private JPanel[] panelRoles;
	
	public JLayeredPane getPane() {
		return areaPane;
	}
	
	public AreaView(String areaName, int[] bounds) {
		
		this.areaName = areaName;
		
		areaPane = new JLayeredPane();
		areaPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JOptionPane.showMessageDialog(null, "Clicked on " + areaName); // TODO: add listener functionality
			}
		});
		areaPane.setBorder(new LineBorder(new Color(244, 164, 96), 3));
		areaPane.setBounds(bounds[0], bounds[1], bounds[2], bounds[3]); //TODO: add panel to the main window in MainWindow
		
		//Determine construction from the length and width
		if (bounds[2] == 225 && bounds[3] == 450) buildVerticalPane(areaPane);
		else if (bounds[2] == 450 && bounds[3] == 225) buildHorizontalPane(areaPane);
		else if (bounds[2] == 675 && bounds[3] == 225) buildLongPane(areaPane);
		else buildNonSetAreas(areaPane);
	}
	
	//TODO: implement construction of non-set areas
	public void buildNonSetAreas(JLayeredPane pane) {
		
	}
	
	//TODO: integrate pane builders with the controller and boardModel
	public void buildVerticalPane(JLayeredPane pane) {
		
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
	
	public void buildHorizontalPane(JLayeredPane pane) {
		
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
	
	public void buildLongPane(JLayeredPane pane) {
		buildHorizontalPane(pane);
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

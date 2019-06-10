import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class RoleView {

	JPanel panel = new JPanel();
	JLabel nameLabel = new JLabel();
	int rank;
	JLabel rankLabel = new JLabel();
	String line;
	JLabel lineLabel = new JLabel();
	String player;
	JLabel actorLabel = new JLabel();
	MainWindow view;
	
	public RoleView(String roleName, MainWindow view) {
		this.view = view;
		this.setName(roleName);
	}
	
	public void buildRoleView(int[] bounds) {

		panel.setBounds(bounds[0], bounds[1], bounds[2], bounds[3]); //TODO: add getPanel
		
		panel.setLayout(null);
		
		panel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				view.roleClicked(getName());
			}
		});
		
		nameLabel.setLabelFor(panel);
		nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		nameLabel.setBounds(0, 0, 95, 16);
		panel.add(nameLabel);
		
		rankLabel.setLabelFor(panel);
		rankLabel.setHorizontalAlignment(SwingConstants.CENTER);
		rankLabel.setBounds(0, 29, 95, 16);
		panel.add(rankLabel);
		
		lineLabel.setLabelFor(panel);
		lineLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lineLabel.setBounds(0, 13, 95, 16);
		panel.add(lineLabel);
		
		actorLabel.setLabelFor(panel);
		actorLabel.setHorizontalAlignment(SwingConstants.CENTER);
		actorLabel.setBounds(5, 42, 85, 16);
		panel.add(actorLabel);
		
	}
	
	public JPanel getPanel() {
		return panel;
	}

	public String getName() {
		return nameLabel.getText();
	}

	public void setName(String name) {
		nameLabel.setText(name);
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
		rankLabel.setText("Rank: " + String.valueOf(rank));
	}

	public String getLine() {
		return lineLabel.getText();
	}

	public void setLine(String line) {
		lineLabel.setText(line);
	}

	public String getPlayer() {
		return player;
	}

	public void setPlayer(String player) {
		this.player = player;
		if(player == null){
			actorLabel.setText("No Actor");
		} else {
			actorLabel.setText("Actor: " + player);
		}
	}
	
}

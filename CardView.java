import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CardView {

	private JPanel panelVisible;
	private JPanel panelInvisible;
	private MainWindow view;
	private JLabel lblTitle = new JLabel();
	private JLabel lblDescription = new JLabel();
	private int budget;
	private JLabel lblBudget = new JLabel();
	private ArrayList<RoleView> roles;
	
	private int[][] roleBounds = {
			{3, 75, 95, 58},
			{102, 75, 95, 58},
			{3, 137, 95, 58},
			{102, 137, 95, 58}
			};
	
	public CardView(String title, MainWindow view) {
		// TODO Auto-generated constructor stub
		setTitle(title);
		this.view = view;
	}
	
	public void build() {
		panelInvisible = new JPanel();
		panelInvisible.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				view.cardClicked(getInvisibleStateString());
			}
		});
		panelInvisible.setBackground(new Color(139, 69, 19));
		panelInvisible.setBounds(12, 13, 200, 200);
		
		panelVisible = new JPanel();
		panelVisible.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				view.cardClicked(getVisibleStateString());
			}
		});
		panelVisible.setBackground(new Color(139, 69, 19));
		panelVisible.setBounds(12, 13, 200, 200);
		panelVisible.setLayout(null);
		
		lblTitle.setBounds(12, 13, 150, 16);
		panelVisible.add(lblTitle);
		
		lblDescription.setBounds(12, 42, 176, 16);
		panelVisible.add(lblDescription);
		
		lblBudget.setBounds(160, 13, 20, 16);
		panelVisible.add(lblBudget);
		
		buildRoles();
	}
	
	private void buildRoles() {
		for (int i = 0; i < roles.size(); i++) {
			roles.get(i).buildRoleView(roleBounds[i]);
			panelVisible.add(roles.get(i).getPanel());
		}
	}
	

	private String getVisibleStateString() {
		return this.getTitle() + "\n" 
					+ this.getDescription();
	}
	
	private String getInvisibleStateString() {
		return "The card is face down";
	}
	
	public JPanel getPanelVisible() {
		return panelVisible;
	}
	
	public JPanel getPanelInvisible() {
		return panelInvisible;
	}

	public String getTitle() {
		return lblTitle.getText();
	}

	public void setTitle(String title) {
		this.lblTitle.setText(title);
	}

	public String getDescription() {
		return lblDescription.getText();
	}

	public void setDescription(String description) {
		this.lblDescription.setText(description);
	}

	public int getBudget() {
		return budget;
	}

	public void setBudget(int budget) {
		this.budget = budget;
		lblBudget.setText("$" + String.valueOf(this.budget));
	}
	
	public void setRoles(ArrayList<RoleView> roles) {
		this.roles = roles;
	}
	
	public ArrayList<RoleView> getRoles() {
		return roles;
	}

	public void clearRoles() {
		roles.stream().forEach(r -> r.setPlayer(null));
	}
}

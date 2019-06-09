import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;

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
			{12, 84, 95, 58},
			{117, 84, 95, 58},
			{12, 155, 95, 58},
			{117, 155, 95, 58}
			};
	
	public CardView(String title, MainWindow view) {
		// TODO Auto-generated constructor stub
		setTitle(title);
		this.view = view;
	}
	
	public void build() {
		panelInvisible = new JPanel();
		panelInvisible.setBackground(new Color(139, 69, 19));
		panelInvisible.setBounds(12, 13, 200, 200);
		
		panelVisible = new JPanel();
		panelVisible.setBackground(new Color(139, 69, 19));
		panelVisible.setBounds(12, 13, 200, 200);
		panelVisible.setLayout(null);
		
		lblTitle.setBounds(12, 13, 108, 16);
		panelVisible.add(lblTitle);
		
		lblDescription.setBounds(12, 42, 176, 16);
		panelVisible.add(lblDescription);
		
		lblBudget.setBounds(149, 13, 39, 16);
		panelVisible.add(lblBudget);
		
		buildRoles();
	}
	
	private void buildRoles() {
		for (int i = 0; i < roles.size(); i++) {
			roles.get(i).buildRoleView(roleBounds[i]);
		}
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

	public String getDesctription() {
		return lblDescription.getText();
	}

	public void setDesctription(String description) {
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
}

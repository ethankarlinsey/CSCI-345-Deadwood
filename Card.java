import java.util.ArrayList;

public class Card {

    private ArrayList<Role> cardRoles;
    private ArrayList<Role> remainingCRoles;
    private String title;
    private String description;
    private int budget;

    public Card(){

    }

    public Card(ArrayList<Role> roles, String filmTitle, String filmDescription, int filmBudget){
        this.cardRoles = roles;
        this.remainingCRoles = roles;
        this.title = filmTitle;
        this.description = filmDescription;
        this.budget = filmBudget;
    }
    
    public boolean isRolePresent(Role role) {
    	return cardRoles.contains(role);
    }

    public ArrayList<Role> getCardRoles(){
        return this.cardRoles;
    }

    public ArrayList<Role> getRemainingCRoles(){
        return this.remainingCRoles;
    }
    
    public int getBudget() {
    	return budget;
    }

    // how do we want this to relate to player?
    public void takeRole(Role toTake, Player player){
    	player.setRole(toTake);
        cardRoles.remove(toTake);
    }

    public void resetRoles(){
        this.remainingCRoles = this.cardRoles;
    }
    
    public void setInactive() {
    	remainingCRoles.clear();
    }

}
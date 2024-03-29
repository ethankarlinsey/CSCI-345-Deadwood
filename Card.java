import java.util.ArrayList;

public class Card {

    private ArrayList<Role> cardRoles;
    private ArrayList<Role> freeCRoles;
    private String title;
    private String description;
    private int budget;

    public Card(){

    }

    public Card(ArrayList<Role> roles, String filmTitle, String filmDescription, int filmBudget){
        this.cardRoles = roles;
        this.freeCRoles = new ArrayList<Role>(roles);
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

    public ArrayList<Role> getFreeCRoles(){
        return this.freeCRoles;
    }
    
    public int getBudget() {
    	return budget;
    }

    // how do we want this to relate to player?
    public void setRoleUnavailable(Role role){
        freeCRoles.remove(role);
    }

    public void resetRoles(){
        this.freeCRoles = new ArrayList<>(this.cardRoles);
    }
    
    public void setInactive() {
    	freeCRoles.clear();
    }

    public String getStateString() {
        String state = "";
        for (Role r : this.cardRoles) {
            state += r.getStateString();

            state += "\n\tStatus: ";
            if(this.freeCRoles.contains(r)){
                state += "free\n";
            } else {
                state += "taken\n";
            }
        }

        return state;
    }

    public String getTitle() {
    	return title;
    }
    
    public String getDescription() {
    	return description;
    }
}
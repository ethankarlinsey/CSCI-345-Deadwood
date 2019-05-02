public class Card {

    private Role[] cardRoles;
    private Role[] remainingCRoles;
    private String title;
    private String description;
    private int budget;

    public Card(){

    }

    public Card(Role[] roles, String filmTitle, String filmDescription, int filmBudget){
        this.cardRoles = roles;
        this.remainingCRoles = roles;
        this.title = filmTitle;
        this.description = filmDescription;
        this.budget = filmBudget;
    }

    public Role[] getCardRoles(){
        return this.cardRoles;
    }

    public Role[] getRemainingCRoles(){
        return this.remainingCRoles;
    }

    // how do we want this to relate to player?
    public void takeRole(Role toTake){
        for(int i = 0; i < this.remainingCRoles.length; i++){
            if(this.remainingCRoles[i] == toTake){
                this.remainingCRoles[i] = null;
                break;
            }
        }
    }

    public void resetRoles(){
        this.remainingCRoles = this.cardRoles;
    }
}
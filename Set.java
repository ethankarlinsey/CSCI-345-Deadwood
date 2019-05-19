import java.util.ArrayList;
import java.util.Optional;

class Set extends Area {

    private Card card;
    private ArrayList<Role> setRoles;
    private ArrayList<Role> freeSRoles;
    private int shots;
    private int shotsRemaining;
    private boolean cardVisible = false;

    public Set(){

    }

    public Set(String title, ArrayList<Role> roles, int cardShots){
        this.name = title;
        this.setRoles = roles;
        this.freeSRoles = new ArrayList<Role>(roles);
        this.shots = cardShots;
        this.shotsRemaining = cardShots;
    }


    public Set(String title, ArrayList<Role> roles, int cardShots, String[] adjacents){
        this.name = title;
        this.setRoles = roles;
        this.freeSRoles = new ArrayList<Role>(roles);
        this.shots = cardShots;
        this.shotsRemaining = cardShots;
        this.adjacentAreas = adjacents;
    }

    public void resetShots(){
        this.shotsRemaining = this.shots;
    }

    public void removeShot(){
        this.shotsRemaining--;
    }

    public void replaceCard(Card newCard){
        this.card = newCard;
    }
    
    public String getStateString() {
    	String state = "";
		state += "Neighboors:";
		for (String s : this.adjacentAreas) state += " " + s;
		state += "\n";
		
		state += "This scene started with " 
				+ String.valueOf(shots)
				+ " shots and has " 
				+ String.valueOf(shotsRemaining)
				+ " shots remaining.\n";

		state += "This scene has a budget of "
                + this.card.getBudget() + "\n";
		
		state += "Off-card roles:\n";
		for (Role r : this.setRoles) {
		    state += r.getStateString();

            state += "\n\tStatus: ";
            if(this.freeSRoles.contains(r)){
		        state += "free\n";
            } else {
		        state += "taken\n";
            }
        }

        state += "On-card roles:\n";
		state += this.card.getStateString();

        return state;
    }

    public ArrayList<Role> getRoles(){
        return this.setRoles;
    }

    public ArrayList<Role> getFreeRoles(){
        return this.freeSRoles;
    }

    public Role getRoleByName(String roleName) {
    	Optional<Role> role = setRoles.parallelStream().filter(r -> r.getName().equalsIgnoreCase(roleName)).findFirst();
    	if (role.isPresent()) return role.get();
    	return null;
    }
    
    public int getShotsRemaining() {
    	return shotsRemaining;
    }
    
    public boolean isRolePresent(Role role) {
    	return setRoles.contains(role);
    }

    // how do we want this to relate to player?
    public void setRoleUnavailable(Role role){
        freeSRoles.remove(role);
    }
    
    // removes all available roles once the shots are gone
    public void setInactive() {
    	if (shotsRemaining <= 0) {
    		freeSRoles.clear();
    		card.setInactive();
    		card = null;
    	}
    }

    public void resetRoles(){
        this.freeSRoles = new ArrayList<>(this.setRoles);
    }
    
    //TODO added by Ethan, accessing the card and its roles
    public Card getCard() {
    	return card;
    }
    
    public void setCardVisible() {
    	cardVisible = true;
    }
}
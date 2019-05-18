import java.util.ArrayList;
import java.util.Optional;

class Set extends Area {

    private Card card;
    private ArrayList<Role> setRoles;
    private ArrayList<Role> remainingSRoles;
    private int shots;
    private int shotsRemaining;
    private boolean cardVisible = false;

    public Set(){

    }

    public Set(String title, ArrayList<Role> roles, int cardShots){
        this.name = title;
        this.setRoles = roles;
        this.remainingSRoles = roles;
        this.shots = cardShots;
        this.shotsRemaining = cardShots;
    }


    public Set(String title, ArrayList<Role> roles, int cardShots, String[] adjacents){
        this.name = title;
        this.setRoles = roles;
        this.remainingSRoles = roles;
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

    public ArrayList<Role> getRoles(){
        return this.setRoles;
    }

    public ArrayList<Role> getFreeRoles(){
        return this.remainingSRoles;
    }

    public Role getRoleByName(String roleName) {
    	Optional<Role> role = setRoles.parallelStream().filter(r -> r.getName().equals(roleName)).findFirst();
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
        setRoles.remove(role);
    }
    
    // removes all available roles once the shots are gone
    public void setInactive() {
    	if (shotsRemaining <= 0) {
    		remainingSRoles.clear();
    		card.setInactive();
    		card = null;
    	}
    }

    public void resetRoles(){
        this.remainingSRoles = this.setRoles;
    }
    
    //TODO added by Ethan, accessing the card and its roles
    public Card getCard() {
    	return card;
    }
    
    public void setCardVisible() {
    	cardVisible = true;
    }
}
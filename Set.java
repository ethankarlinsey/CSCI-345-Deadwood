class Set extends Area {

    private Card card;
    private Role[] setRoles;
    private Role[] remainingSRoles;
    private int shots;
    private int shotsRemaining;
    private boolean cardVisible;

    public Set(){

    }

    public Set(String title, Role[] roles, int cardShots){
        this.name = title;
        this.setRoles = roles;
        this.remainingSRoles = roles;
        this.shots = cardShots;
        this.shotsRemaining = cardShots;
    }


    public Set(String title, Role[] roles, int cardShots, String[] adjacents){
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

    public Role[] getRoles(){
        return this.setRoles;
    }

    public Role[] getFreeRoles(){
        return this.remainingSRoles;
    }

    public void getRoleByName() {} // TODO: Change return to Role, implement.

    // how do we want this to relate to player?
    public void takeRole(Role toTake){
        for(int i = 0; i < this.remainingSRoles.length; i++){
            if(this.remainingSRoles[i] == toTake){
                this.remainingSRoles[i] = null;
                break;
            }
        }
    }

    public void resetRoles(){
        this.remainingSRoles = this.setRoles;
    }
    
    //TODO added by Ethan, accessing the card and its roles
    public Card getCard() {
    	return card;
    }
}
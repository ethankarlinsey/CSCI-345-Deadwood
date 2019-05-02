class Set extends Area {

    private Card card;
    private Role[] setRoles;
    private Role[] remainingSRoles;
    private int shots;
    private int shotsRemaining;
    private boolean cardVisible;

    public Set(){

    }

    public Set(String title, Role[] roles, int cardShots, int cardShotsRemaining){
        this.name = title;
        this.setRoles = roles;
        this.remainingSRoles = roles;
        this.shots = cardShots;
        this.shotsRemaining = cardShotsRemaining;
    }


    public Set(String title, Role[] roles, int cardShots, int cardShotsRemaining, Location locale, Location[] adjacents){
        this.name = title;
        this.setRoles = roles;
        this.remainingSRoles = roles;
        this.shots = cardShots;
        this.shotsRemaining = cardShotsRemaining;
        this.location = locale;
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
}
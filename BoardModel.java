public class BoardModel {
    private Area[] areas;
    private Player[] players;
    private Card[] cards;

    public BoardModel(){

    }

    public void setUpAreaLocations(){

    }

    public void movePlayer(Location loc, Player player){

    }

    public void nextDayReset(){

    }

    private void dealNewCards(){

    }

    private void sendPlayersToTrailers(){
        // find trailer Area
        Area trailer = null;
        String trailerName = "Trailer";

        for (Area a: this.areas) {
            if(trailerName.equals(a.getName())){
                trailer = a;
                break;
            }
        }

        // move all players
        for (Player p: this.players) {
            p.setLocation(trailer.getLocation());
        }
    }
    
    public Player[] getPlayers() {
    	return players;
    }
    
    //TODO Ethan added this method but isn't quite sure how to implement it
    public Area getAreaFromLocation(Location loc) {
    	// find the area that has this location
    	return new Set();
    }
}
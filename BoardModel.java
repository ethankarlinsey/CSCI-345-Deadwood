import java.util.Arrays;

public class BoardModel {
    private static final String XMLBoardName = "board.xml";
    private static final String XMLCardsName = "cards.xml";
    private Area[] areas;
    private Player[] players;
    private Card[] cards; //TODO: when setting up cards, remember to shuffle
    
    private int cardIndex = 0;

    public BoardModel(){
    	
    }

    public void setUpAreaLocations(){

    }
    
    public Area getAreaByName(String name) {
    	return null;
    }

    public void movePlayer(Player player){ // TODO: update

    }

    public void nextDayReset(){
    	dealNewCards();
    	sendPlayersToTrailers();
    	replaceShotCounters();
    	
    	//Return players to the trailers (include resetting role and rehearsalCount)
    	//Remove the last scene card from the board
    	//Deal ten new scene cards onto the board, face-down
    	//Replace all shot counters
    	
    }

    //deals the next 10 cards
    private void dealNewCards(){
    	int endIndex = cardIndex + 10;
    	for(int i = cardIndex; i < endIndex; cardIndex++) {
    		
    	}
    	cardIndex = endIndex;
    }
    
    private void replaceShotCounters() {
    	Arrays.stream(areas).filter(area -> area instanceof Set)
    			.map(area -> (Set) area)
    			.forEach(set -> set.resetShots());
    }
    
    public int getSceneCount() {//returns the number of scenes left
    	return 2;
    	
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

        // move all players, reset roles and rehearsals
        for (Player p: this.players) {
            p.setArea(trailer);
            p.setRole(null);
            p.resetRehearsals();
        }
    }
    
    public Player[] getPlayers() {
    	return players;
    }
}
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class BoardModel {
    private static final String XMLBoardName = "board.xml";
    private static final String XMLCardsName = "cards.xml";
    private Area[] areas;
    private Player[] players; // is this needed?
    private Card[] cards; //TODO: when setting up cards, remember to shuffle
    
    private int cardIndex = 0;

    public BoardModel(){
    	setUpCards();
    	setUpAreaLocations();
    }

    private void setUpCards(){
        ParseGamePiecesXML cardParser = new ParseGamePiecesXML();
        Card[] temp_cards = cardParser.initCards(XMLCardsName);
        ArrayList toShuffle = new ArrayList<>(Arrays.asList(temp_cards));
        Collections.shuffle(toShuffle);
        this.cards = temp_cards;
    }

    private void setUpAreaLocations(){
        ParseGamePiecesXML areaParser = new ParseGamePiecesXML();
        this.areas = areaParser.initAreas(XMLBoardName);
    }
    
    public Role getRoleByName(String name) {
    	return null;
    }

    public boolean hasAreaByName(String name) {
        return Areas.hasAreaByName(this.areas, name);
    }

    public Area getAreaByName(String name) {
        return Areas.getAreaByName(this.areas, name);
    }


    public void nextDayReset(){
    	dealNewCards();
    	sendPlayersToTrailers();
    	replaceShotCounters();
    	
    	//Return players to the trailers (include resetting role, and rehearsalCount)
    	//Remove the last scene card from the board
    	//Deal ten new scene cards onto the board, face-down
    	//Replace all shot counters
    	
    }

    //deals the next 10 cards
    private void dealNewCards(){ // TODO: Implement this so we don't always do the same process
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
    
    public ArrayList<Player> getPlayersByArea(Area area) { //returns a list of players in an area
    	return (ArrayList<Player>) Arrays.stream(players).filter(p -> p.getArea() == area).collect(Collectors.toList());
    }
}
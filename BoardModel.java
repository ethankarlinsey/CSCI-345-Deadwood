import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.stream.Collectors;

public class BoardModel {
    private static final String XMLBoardName = "board.xml";
    private static final String XMLCardsName = "cards.xml";
    private ArrayList<Area> areas;
    private ArrayList<Player> players; // is this needed?
    private ArrayList<Card> cards;

    Iterator<Card> cardIterator;

    public BoardModel(){
    	setUpAreaLocations();
        setUpCards();
    }
    
    public void setPlayers(ArrayList<Player> players) {
    	this.players = players;
    }

    private void setUpCards(){
        ParseGamePiecesXML cardParser = new ParseGamePiecesXML();
        ArrayList<Card> cards = cardParser.initCards(XMLCardsName);
        Collections.shuffle(cards);
        this.cards = cards;
        cardIterator = cards.iterator();
        dealNewCards();
    }

    private void setUpAreaLocations(){
        ParseGamePiecesXML areaParser = new ParseGamePiecesXML();
        this.areas = areaParser.initAreas(XMLBoardName);
    }
    
    public Role getRoleByName(String name) {
    	return null;
    }

    public boolean hasAreaByName(String name) {
        return areas.stream().anyMatch(a -> a.getName().equals(name));
    }

    public Area getAreaByName(String name) { // returns an area with the same name. Does not account for multiple areas with same name.
        return areas.stream().filter(a -> a.getName().contentEquals(name)).collect(Collectors.toList()).get(0);
    }


    public void nextDayReset(){
    	dealNewCards();
    	resetSetRoles();
    	sendPlayersToTrailers();
    	replaceShotCounters();
    	
    	//Return players to the trailers (include resetting role, and rehearsalCount)
    	//Remove the last scene card from the board
    	//Deal ten new scene cards onto the board, face-down
    	//Replace all shot counters
    	
    }

    //deals the next 10 cards
    private void dealNewCards(){
    	areas.stream().filter(area -> area instanceof Set)
                .map(area -> (Set) area)
                .forEach(set -> set.replaceCard(cardIterator.next()));

    	// testing
        areas.stream().filter(area -> area instanceof Set)
                .map(area -> (Set) area)
                .forEach(set -> System.out.println(set.getCard().getCardRoles().get(0).getName()));
    }

    private void resetSetRoles(){
        areas.stream().filter(area -> area instanceof Set)
                .map(area -> (Set) area)
                .forEach(set -> set.resetRoles());
    }
    
    private void replaceShotCounters() {
    	areas.stream().filter(area -> area instanceof Set)
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
    
    public ArrayList<Player> getPlayers() {
    	return players;
    }
    
    public ArrayList<Player> getPlayersByArea(Area area) { //returns a list of players in an area
    	return (ArrayList<Player>) players.stream().filter(p -> p.getArea() == area).collect(Collectors.toList());
    }
}
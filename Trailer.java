class Trailer extends Area {

    public Trailer(){
        this.name = "Trailer";
    }

    public Trailer(String[] adjacents){
        this.name = "Trailer";
        this.adjacentAreas = adjacents;
    }
    
    public String getStateString() {
    	String state = "";
		state += "Neighboors:";
		for (String s : this.adjacentAreas) state += " - " + s;
		state += "\n";
		
		state += "Start here every day\n";
		
		return state;
    }
}
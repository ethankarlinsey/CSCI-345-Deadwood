class CastingOffice extends Area {

    public CastingOffice(){
        this.name = "Casting Office";
    }


    public CastingOffice(String[] adjacents){
        this.name = "Casting Office";
        this.adjacentAreas = adjacents;
    }
    
    public String getStateString() {
    	String state = "";
		state += "Neighbors:";
		for (String s : this.adjacentAreas) state += " - " + s;
		state += "\n";
		
		state += "Pay dollars OR credits to upgrade\n"
				+ "Rank" + "\t" + "Dollars" + "\t" + "Credits\n"
				+ "2" + "\t" + "4" + "\t" + "5" + "\n"
				+ "3" + "\t" + "10" + "\t" + "10" + "\n"
				+ "4" + "\t" + "18" + "\t" + "15" + "\n"
				+ "5" + "\t" + "28" + "\t" + "20" + "\n"
				+ "6" + "\t" + "40" + "\t" + "25" + "\n";
		
		return state;
    }
}
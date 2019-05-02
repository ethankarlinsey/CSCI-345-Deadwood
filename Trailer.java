class Trailer extends Area {

    public Trailer(){
        this.name = "Trailer";
    }

    public Trailer(Location locale, Location[] adjacents){
        this.name = "Trailer";
        this.location = locale;
        this.adjacentAreas = adjacents;
    }
}
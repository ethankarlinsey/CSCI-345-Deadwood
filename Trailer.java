class Trailer extends Area {

    public Trailer(){
        this.name = "Trailer";
    }

    public Trailer(String[] adjacents){
        this.name = "Trailer";
        this.adjacentAreas = adjacents;
    }
}
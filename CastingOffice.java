class CastingOffice extends Area {

    public CastingOffice(){
        this.name = "Casting Office";
    }


    public CastingOffice(String[] adjacents){
        this.name = "Casting Office";
        this.adjacentAreas = adjacents;
    }
}
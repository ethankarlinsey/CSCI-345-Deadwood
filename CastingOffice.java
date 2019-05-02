class CastingOffice extends Area {

    public CastingOffice(){
        this.name = "Casting Office";
    }


    public CastingOffice(Location locale, Location[] adjacents){
        this.name = "Casting Office";
        this.location = locale;
        this.adjacentAreas = adjacents;
    }
}
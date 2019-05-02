public abstract class Area {
    protected String name;
    protected Location location;
    protected Location[] adjacentAreas;

    public Area(){

    }

    public Area(String title){
        this.name = title;
    }

    public Area(String title, Location locale, Location[] adjacents){
        this.name = title;
        this.location = locale;
        this.adjacentAreas = adjacents;
    }

    final Location getLocation(){
        return this.location;
    }

    // added to aid in containing Area's functionality to one class
    final boolean isAdjacent(Area a) {
        boolean isAdj = false;
        if (a.getLocation().isAdjLocation(this.location)) {
            isAdj = true;
        }

        return isAdj;
    }

    final String getName(){
        return this.name;
    }

    final Location[] getAdjacentAreas(){
        return this.adjacentAreas;
    }

    final void setLocation(Location loc){
        this.location = loc;
    }

    final void setAdjacentAreas(Location[] areas){
        this.adjacentAreas = areas;
    }
}
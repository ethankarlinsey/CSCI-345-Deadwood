public abstract class Area {
    protected String name;
    protected String[] adjacentAreas;

    public Area(){

    }

    public Area(String title){
        this.name = title;
    }

    public Area(String title, String[] adjacents){
        this.name = title;
        this.adjacentAreas = adjacents;
    }
    
    public abstract String getStateString();

    // added to aid in containing Area's functionality to one class
    final boolean isAdjacent(Area a) {
        boolean isAdj = false;
        String areaStr = a.getName();

        for (String name : this.adjacentAreas) {
            if (areaStr.equals(name)) {
                isAdj = true;
                break;
            }
        }

        return isAdj;
    }

    final String getName(){
        return this.name;
    }

    final String[] getAdjacentAreas(){
        return this.adjacentAreas;
    }
}
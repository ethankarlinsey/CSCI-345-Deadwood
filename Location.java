public class Location {

    private int[] boardIndices;

    Location() {

    }

    Location(int index){
        this.boardIndices[0] = index;
        this.boardIndices[1] = -1;
    }

    Location(int index1, int index2){
        this.boardIndices[0] = index1;
        this.boardIndices[1] = index2;
    }

    public boolean isAdjLocation(Location room){
        boolean adj = false;
        int[] indices = room.getIndices();
        // incomplete
        return adj;
    }

    // checks whether the location given is the same as this location
    public boolean isSameLoc(Location locale){
        boolean same = false;
        int[] localeIndices = locale.getIndices();
        if(localeIndices[0] == this.boardIndices[0] && localeIndices[1] == this.boardIndices[1]){
            same = true;
        }

        return same;
    }

    public int[] getIndices(){
        return this.boardIndices;
    }
}


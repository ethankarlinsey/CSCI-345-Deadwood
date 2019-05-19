// to be used as a data object
public class Role {
    private int rank;
    private String name;
    private String line;

    public Role(){

    }

    public Role(int roleRank, String roleName, String roleLine){
        this.rank = roleRank;
        this.name = roleName;
        this.line = roleLine;
    }

    public int getRank(){
        return this.rank;
    }

    public String getName(){
        return this.name;
    }

    public String getLine(){
        return this.line;
    }

    public String getStateString() {
        String state = "";
        state += "\tRole Name: ";
        state += this.name;
        state += "\n\tRole Rank: ";
        state += this.rank;
        state += "\n\tRole Line: ";
        state += this.line;

        return state;
    };
}
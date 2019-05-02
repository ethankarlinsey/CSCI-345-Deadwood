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
}
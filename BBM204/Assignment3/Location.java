import java.util.ArrayList;

public class Location {
    public String name;
    public int id;
    public ArrayList<Trail> adj = new ArrayList<>();

    public Location(String name, int id) {
        this.name = name;
        this.id = id;
    }

    void addAdj(Trail v){
        adj.add(v);
    }
}

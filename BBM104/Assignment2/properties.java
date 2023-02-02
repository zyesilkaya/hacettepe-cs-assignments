


public abstract class properties extends Board {
    
    protected int cost;
    protected String name;    
    protected Players owner = null;
    
    public Players getOwner() {
        return owner;
    }

    public void setOwner(Players owner) {
        this.owner = owner;
    }

    public int getCost() {
        return cost;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public properties(int cost, String name) {
        this.cost = cost;
        this.name = name;
    }

}

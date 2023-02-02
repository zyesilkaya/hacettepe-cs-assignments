

public class Railroads extends properties {


    public Railroads(int cost, String name) {
        super(cost, name);
    }

    @Override
    public int getCost() {
        return cost;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Players getOwner() {
        return owner;
    }

    @Override
    public void setOwner(Players owner) {
        this.owner = owner;
    }

}

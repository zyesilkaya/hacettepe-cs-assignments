

public class Land extends properties {

    public Land(int cost, String name) {
        super(cost, name);
    }

    @Override
    public void setName(String name) {
        super.setName(name); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getName() {
        return super.getName(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setOwner(Players owner) {
        super.setOwner(owner); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Players getOwner() {
        return super.getOwner(); //To change body of generated methods, choose Tools | Templates.        
    }
}

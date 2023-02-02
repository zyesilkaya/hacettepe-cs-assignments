public class Seat extends Cinema{
    private int rowIndex;
    private int colIndex;
    private boolean disable;
    private User owner;
    private Hall hall;
    private int cost;

    public Seat(int rowIndex, int colIndex, boolean disable, User owner, Hall hall, int cost) {
        this.rowIndex = rowIndex;
        this.colIndex = colIndex;
        this.disable = disable;
        this.owner = owner;
        this.hall = hall;
        this.cost = cost;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }

    public int getColIndex() {
        return colIndex;
    }

    public void setColIndex(int colIndex) {
        this.colIndex = colIndex;
    }

    public boolean isDisable() {
        return disable;
    }

    public void setDisable(boolean disable) {
        this.disable = disable;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Hall getHall() {
        return hall;
    }

    public void setHall(Hall hall) {
        this.hall = hall;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        if (getOwner().isClubMember()){
            this.cost=cost*(100- FileReaderForBackup.discount)/100;
        }
        else {
            this.cost = cost;
        }

    }
}

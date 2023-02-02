import java.util.ArrayList;
import java.util.HashMap;

public class Hall extends Cinema{
    private int row;
    private int col;
    private String name;
    private int pricePerSeat;
    private String shownFilm;
    public static HashMap<String,Hall> hallMap = new HashMap();
    ArrayList<Seat> seatArraylist = new ArrayList<>();

    public Hall(int row, int col, String name, int pricePerSeat, String shownFilm) {
        this.row = row;
        this.col = col;
        this.name = name;
        this.pricePerSeat = pricePerSeat;
        this.shownFilm = shownFilm;
    }

    public ArrayList<Seat> getSeatArraylist() {
        return seatArraylist;
    }

    public void setSeatArraylist(ArrayList<Seat> seatArraylist) {
        this.seatArraylist = seatArraylist;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPricePerSeat() {
        return pricePerSeat;
    }

    public void setPricePerSeat(int pricePerSeat) {
        this.pricePerSeat = pricePerSeat;
    }

    public String getShownFilm() {
        return shownFilm;
    }

    public void setShownFilm(String shownFilm) {
        this.shownFilm = shownFilm;
    }
}

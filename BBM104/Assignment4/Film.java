import java.util.ArrayList;
import java.util.HashMap;

public class Film extends Cinema{
    private String name;
    private int duration;
    private String address;
    public static HashMap<String, Film> filmMap = new HashMap();
   HashMap<String,Hall> hallList = new HashMap();
   HashMap<String,Comment> commentForFilm = new HashMap<>();

    public Film(String name, int duration, String address) {
        this.name = name;
        this.duration = duration;
        this.address = address;
    }

    public HashMap<String, Hall> getHallList() {
        return hallList;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

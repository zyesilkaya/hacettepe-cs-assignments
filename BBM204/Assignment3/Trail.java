public class Trail {
    public Location source;
    public Location destination;
    public int danger;

    public Trail(Location source, Location destination, int danger) {
        this.source = source;
        this.destination = destination;
        this.danger = danger;
    }

    int either(){
        return source.id;
    }

    int other(int v){
        if (v == source.id) return destination.id;
        else return source.id;
    }

}

public class Player implements Comparable<Player> {
    // in this class I use comparable interface to rank the players by their scores

    private int score;
    private String name;

    public Player(int score, String name) {
        this.score = score;
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public String getName() {
        return name;
    }

    @Override
    public int compareTo(Player o) {
        if(this.score>o.getScore()){ return -1;}
        if(this.score<o.getScore()){ return 1;}
        return 0;
    }
}

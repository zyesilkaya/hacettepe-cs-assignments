import java.util.HashMap;

public class Comment {
    private String comment;
    private int stars;
    private String username;
    private Film film;
    static HashMap<String,Comment> commentMap = new HashMap<>();

    public Comment(String comment, int stars, String username, Film film) {
        this.comment = comment;
        this.stars = stars;
        this.username = username;
        this.film = film;
    }

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

import java.io.IOException;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneController {

    public static Scene previousScene;

    public void switchToLogIn(Stage primaryStage,Scene scene) throws IOException {
        Login log = new Login();
        log.start(primaryStage);
        previousScene = scene;
    }

    public void switchToSignUP(Stage primaryStage,Scene scene) throws IOException {
        SignUp dm = new SignUp();
        dm.start(primaryStage);
        previousScene = scene;
    }

    public void switchToselectFilm(Stage primaryStage,Scene scene) throws IOException {
        SelectFilmStage slFilm = new SelectFilmStage();
        slFilm.start(primaryStage);
        previousScene = scene;
    }
    public void switchToRemoveFilm(Stage primaryStage,Scene scene) throws Exception {
        RemoveFilm rmFilm = new RemoveFilm();
        rmFilm.start(primaryStage);
        previousScene = scene;
    }

    public void switchToAddFilm(Stage primaryStage,Scene scene) throws Exception {
        AddFilm aFilm = new AddFilm();
        aFilm.start(primaryStage);
        previousScene = scene;
    }

    public void switchToFilmStage(Stage primaryStage,Scene scene) throws IOException {
        FilmStage film = new FilmStage();
        film.start(primaryStage);
        previousScene = scene;
    }

    public void switchToSeatStage(Stage primaryStage,Scene scene) throws Exception {
        SeatStage seats = new SeatStage();
        seats.start(primaryStage);
        previousScene = scene;
    }

    public void switchToBack(Stage primaryStage, Scene scene) throws Exception {
        primaryStage.setScene(previousScene);
        primaryStage.show();
        previousScene = scene;
    }

    public void switchToEditUsers(Stage primaryStage,Scene scene) throws Exception {
        EditUsers editUsers = new EditUsers();
        editUsers.start(primaryStage);
        previousScene = scene;
    }

    public void switchToRemoveHall(Stage primaryStage,Scene scene) throws Exception {
        RemoveHall rmHall = new RemoveHall();
        rmHall.start(primaryStage);
        previousScene = scene;
    }

    public void switchToAddHall(Stage primaryStage,Scene scene) throws Exception {
        AddHall aHall = new AddHall();
        aHall.start(primaryStage);
        previousScene = scene;
    }

    public void switchToComments(Stage primaryStage,Scene scene) throws Exception {
        CommentStage com = new CommentStage();
        com.start(primaryStage);
        previousScene = scene;
    }
}
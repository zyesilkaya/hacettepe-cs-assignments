import javafx.application.Application;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Main extends Application {
    public static void main(String[] args) throws IOException {
        FileReaderForBackup.readAllFile();
        launch(args);
        Writer writer = new Writer();
        writer.writer();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        new MenuStage().start(primaryStage);
    }
}
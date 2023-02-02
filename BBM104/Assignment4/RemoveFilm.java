import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileNotFoundException;

public class RemoveFilm extends Application {
    Scene scene;

    @Override
    public void start(Stage primaryStage) throws Exception {

        scene = new Scene(getPane(primaryStage),400,200);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setTitle(FileReaderForBackup.title);
        primaryStage.getIcons().add(FileReaderForBackup.logo);
        primaryStage.show();

    }

    /**
     * this method is for creating remove film page
     * @return VBox
     */

    VBox getPane(Stage primaryStage){
        Text instructor = new Text("Select the hall that you desire to remove and then click OK");

        ChoiceBox<String> filmBox = new ChoiceBox<>();
        filmBox.setPrefWidth(300);

        for(Film film:Film.filmMap.values()){
            filmBox.getItems().add(film.getName());
            filmBox.setValue(film.getName());
        }

        Button backButton = new Button("\u25C0 BACK");
        backButton.setOnAction(event -> {
            SceneController sc = new SceneController();
            try {
                sc.switchToselectFilm(primaryStage,scene);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        Button OKButton = new Button("OK");

        OKButton.setOnAction(event -> {
            try{
                String selected = filmBox.getSelectionModel().getSelectedItem();
                Film.filmMap.remove(selected);
                filmBox.getItems().remove(selected);
                Film.filmMap.forEach((k, v) ->{
                    filmBox.setValue(v.getName());
                });
            }
            catch (Exception e){
            }
        });

        HBox buttonBox = new HBox(backButton,OKButton);
        buttonBox.setSpacing(10);
        buttonBox.setAlignment(Pos.CENTER);

        VBox pane = new VBox(instructor,filmBox,buttonBox);
        pane.setSpacing(20);
        pane.setAlignment(Pos.CENTER);
        return pane;

    }
}

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

public class RemoveHall extends Application {
    Scene scene;
    ChoiceBox<String> hallBox;
    @Override
    public void start(Stage primaryStage) throws Exception {

        scene = new Scene(getPane(primaryStage),500,200);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setTitle(FileReaderForBackup.title);
        primaryStage.getIcons().add(FileReaderForBackup.logo);
        primaryStage.show();
    }

    /**
     * this function creates all labels and fields for remove hall scene
     * @return VBox for scene
     */
    VBox getPane(Stage primaryStage){

        Text instructor = new Text("Select the hall that you desire to remove from"+" and then click OK");

        hallBox = new ChoiceBox<>();
        hallBox.setPrefWidth(300);

        Hall.hallMap.forEach((k, v) ->{
            if(v.getShownFilm().equals(SelectFilmStage.selectedFilm.getName())){
                hallBox.getItems().add(k);
                hallBox.setValue(v.getName());
            }
            System.out.println("key: "+k+" value:"+v); });

        Button backButton = new Button("\u25C0 BACK");
        backButton.setOnAction(event -> backButtonHandler(primaryStage));
        Button OKButton = new Button("OK");
        OKButton.setOnAction(event -> OKButtonHandler(primaryStage) );
        HBox buttonBox = new HBox(backButton,OKButton);
        buttonBox.setSpacing(10);
        buttonBox.setAlignment(Pos.CENTER);

        VBox pane = new VBox(instructor,hallBox,buttonBox);
        pane.setSpacing(20);
        pane.setAlignment(Pos.CENTER);
        return pane;
    }

    void OKButtonHandler(Stage primaryStage){
        String hall = hallBox.getValue();
        hallBox.getItems().remove(hall);
        Hall.hallMap.remove(hall);
        SelectFilmStage.selectedFilm.getHallList().remove(hall);
        Hall.hallMap.forEach((k, v) ->{
            hallBox.setValue(v.getName());
        });
    }

    void backButtonHandler(Stage primaryStage){
        SceneController sc = new SceneController();
        try {
            sc.switchToFilmStage(primaryStage,scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

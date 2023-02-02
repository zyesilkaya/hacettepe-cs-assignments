import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.TextField;

import java.io.File;

public class AddFilm extends Application {
    Scene scene;
    TextField nameField;
    TextField durationField;
    TextField pathField;
    Label errorLabel;
    @Override
    public void start(Stage primaryStage) throws Exception {

        scene = new Scene(getPane(primaryStage),570,300);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setTitle(FileReaderForBackup.title);
        primaryStage.getIcons().add(FileReaderForBackup.logo);
        primaryStage.show();
    }

    /**
     * this method is for creating Hall page
     * @return VBox
     */

    VBox getPane(Stage primaryStage){

        GridPane pane = new GridPane();
        pane.setHgap(10);
        pane.setVgap(10);

        Text instruction = new Text("Please give name,relative path of the trailer and duration time of the film.");

        Label nameLabel = new Label("Name:");
        nameField = new TextField();
        nameField.setPromptText("Name");
        pane.add(nameLabel,0,0);
        pane.add(nameField,1,0);

        Label trailerLabel = new Label("Trailer (Path):");
        pathField = new TextField();
        pathField.setPromptText("Path");
        pane.add(trailerLabel,0,1);
        pane.add(pathField,1,1);

        Label durationLabel = new Label("Duration (m):");
        durationField = new TextField();
        durationField.setPromptText("Duration");
        pane.add(durationLabel,0,2);
        pane.add(durationField,1,2);

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

        OKButton.setOnAction(event -> buttonActive());

        HBox buttonBox = new HBox(OKButton);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);

        pane.add(backButton,0,3);
        pane.add(buttonBox,1,3);

        pane.setAlignment(Pos.CENTER);

        errorLabel = new Label();
        HBox errorLabelBox = new HBox(errorLabel);
        errorLabelBox.setAlignment(Pos.CENTER);

        VBox vBox = new VBox(instruction,pane,errorLabelBox);
        vBox.setSpacing(20);
        vBox.setPadding(new Insets(20,20,20,20));
        return vBox;
    }
    /**
     * this method is for calling check method and if it is true adding the film
     */
    public void buttonActive()  {
        if(!isAccepted()){
            reset();
        }
        else {
            Film.filmMap.put(nameField.getText(),new Film(nameField.getText(),Integer.parseInt(durationField.getText()),pathField.getText()));
            errorLabel.setText("SUCCESS: Film added successfully!");
            nameField.setText("");
            durationField.setText("");
            pathField.setText("");
        }
    }
    /**
     * is for checking password is valid or not and checking username is valid or not
     * @return boolean
     */
    public boolean isAccepted(){
        try{
            if(nameField.getText().equals("")){
            errorLabel.setText("ERROR! Film name could not be empty");
            return false;
            }

            else if(Film.filmMap.containsKey(nameField.getText())){
                errorLabel.setText("ERROR! This film name already exists ");
                return false;
            }

            else if (durationField.getText().equals("")){
                errorLabel.setText("ERROR! Duration could not be empty");
                return false;
            }

            else if (Integer.parseInt(durationField.getText())<0){
                errorLabel.setText("ERROR! Duration must be positive integer");
                return false;
            }

            else if (pathField.getText().equals("")){
                errorLabel.setText("ERROR! Path could not be empty");
                return false;
            }

            try{
                String media = new File("assets\\trailers\\"+pathField.getText()).toURI().toString();
                Media mediaPlayer = new Media(media);
            }
            catch (Exception e){
                errorLabel.setText("ERROR! There is no such a trailer");
                return false;
            }
            return true;
        }

        catch (Exception e){
            errorLabel.setText("ERROR! Duration must be positive integer");
            return false;
        }
    }

    /**
     * this method is for cleaning the fields and playing error sound
     */
    private void reset(){
        String uriString = new File("assets\\effects\\error.mp3").toURI().toString();
        MediaPlayer Player = new MediaPlayer(new Media(uriString));
        Player.play();
        nameField.setText("");
        durationField.setText("");
        pathField.setText("");
    }
}

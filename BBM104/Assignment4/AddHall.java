import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;

public class AddHall extends Application {
    Scene scene;
    Button backButton;
    ChoiceBox<Integer> rowBox;
    ChoiceBox<Integer> colBox;
    TextField priceField;
    TextField nameField;
    private Label errorLabel;
    @Override
    public void start(Stage primaryStage) throws Exception {

        scene = new Scene(getPane(primaryStage),400,300);
        primaryStage.setScene(scene);
        primaryStage.setTitle(FileReaderForBackup.title);
        primaryStage.getIcons().add(FileReaderForBackup.logo);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    /**
     * this method is for creating add hall page
     * @param primaryStage It is used to switch to another page when the back button is pressed.
     * @return VBox
     */
    VBox getPane(Stage primaryStage){
        VBox vBox = new VBox(15);
        vBox.setPadding(new Insets(20,20,20,20));

        GridPane pane = new GridPane();
        pane.setHgap(10);
        pane.setVgap(10);

        Label rowLabel = new Label("Row:");
        rowBox = new ChoiceBox();
        Integer[] numbers = {3,4,5,6,7,8,9,10};
        rowBox.getItems().addAll(numbers);
        rowBox.setValue(3);
        pane.add(rowLabel,0,0);
        pane.add(rowBox,1,0);

        Label colLabel = new Label("Column:");
        colBox = new ChoiceBox();
        colBox.getItems().addAll(numbers);
        colBox.setValue(3);
        pane.add(colLabel,0,2);
        pane.add(colBox,1,2);

        Label nameLabel = new Label("Name:");
        nameField = new TextField();
        nameField.setPromptText("Name");
        pane.add(nameLabel,0,3);
        pane.add(nameField,1,3);

        Label priceLabel = new Label("Price:");
        priceField = new TextField();
        priceField.setPromptText("Price");
        pane.add(priceLabel,0,4);
        pane.add(priceField,1,4);

        errorLabel = new Label();
        HBox errorLabelBox = new HBox(errorLabel);
        errorLabelBox.setAlignment(Pos.CENTER);

        backButton = new Button("\u25C0 BACK");
        backButton.setOnAction(event -> backButtonAction(primaryStage) );

        Button OKButton = new Button("OK");
        OKButton.setOnAction(event -> buttonActive());

        pane.setHalignment(OKButton, HPos.RIGHT);

        pane.setAlignment(Pos.CENTER);

        pane.add(backButton,0,5);
        pane.add(OKButton,1,5);

        vBox.getChildren().addAll(pane,errorLabelBox);
        return vBox;
    }
    /**
     * this method is for calling check method and if it returns true adding new hall
     */
    public void buttonActive()  {
        if(!isAccepted()){
            reset();
        }
        else {
            Hall hall =new Hall(rowBox.getValue(),colBox.getValue(),nameField.getText(),Integer.parseInt(priceField.getText()), SelectFilmStage.selectedFilm.getName());
            errorLabel.setText("SUCCESS! Hall added successfully");
            for(int i = 0;i<hall.getRow();i++){
                for(int j = 0;j<hall.getCol();j++){
                    hall.seatArraylist.add(new Seat(j,i,false,null,hall,0));
                }
            }
            Hall.hallMap.put(nameField.getText(),hall);
            SelectFilmStage.selectedFilm.getHallList().put(hall.getName(), hall);
        }
    }
    /**
     * this method is for checking password is valid or not and for checking username is valid or not
     * @return boolean
     */
    public boolean isAccepted(){
        try{
            if(nameField.getText().equals("")){
                errorLabel.setText("ERROR! Hall name could not be empty");
                return false;
            }
            if(Hall.hallMap.containsKey(nameField.getText())){
                errorLabel.setText("ERROR! This hall name already exits");
                return false;
            }
            else if (priceField.getText().equals("")){
                errorLabel.setText("ERROR! Price could not be empty");
                return false;
            }
            else if (Integer.parseInt(priceField.getText())<=0){
                errorLabel.setText("ERROR! Price has to be positive integer");
                return false;
            }
        }
        catch (Exception ignored){
            errorLabel.setText("ERROR! Price has to be positive integer");
            return false;
        }
        return true;
    }

    /**
     * this method is for cleaning the fields and playing error sound
     */
    private void reset(){
        String uriString = new File("assets\\effects\\error.mp3").toURI().toString();
        MediaPlayer Player = new MediaPlayer(new Media(uriString));
        Player.play();
        nameField.setText("");
        priceField.setText("");
        rowBox.setValue(3);
        colBox.setValue(3);
    }

    /**
     * @param primaryStage It is used to switch to hall page when pressed back button.
     */
    void backButtonAction(Stage primaryStage){
        SceneController sc = new SceneController();
        try {
            sc.switchToFilmStage(primaryStage,scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

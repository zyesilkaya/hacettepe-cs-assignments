import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.*;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;

public class SelectFilmStage extends Application {
    String text;
    Text topText;
    HBox topBox;
    HBox buttonBox;
    HBox buttonBoxAdmin = new HBox();
    HBox choiceHBox;
    Scene scene;
    VBox vBox;
    ChoiceBox<String> choiceBox;
    Button okButton;

    public static Film selectedFilm;

    public void start(Stage primaryStage){

        scene = new Scene(getPane(primaryStage),470,300);
        primaryStage.setTitle(FileReaderForBackup.title);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(FileReaderForBackup.logo);
        primaryStage.show();

    }
    /**
     * this method is for creating welcome page
     * @return Pane
     */
    Pane getPane(Stage primaryStage){
        topText = new Text(getText());
        topText.setTextAlignment(TextAlignment.CENTER);

        Button addFilmButton = new Button("Add Film");
        Button removeFilmButton = new Button("Remove Film");
        Button editUsersButton = new Button("Edit Users");

        choiceBox = new ChoiceBox<>();
        choiceBox.setPrefWidth(340);

        for(Film film:Film.filmMap.values()){
            choiceBox.getItems().add(film.getName());
            choiceBox.setValue(film.getName());
        }

        if(Login.loggedUser.isAdmin()){
            buttonBoxAdmin.getChildren().addAll(addFilmButton, removeFilmButton, editUsersButton);
            buttonBoxAdmin.setSpacing(10);
            buttonBoxAdmin.setAlignment(Pos.CENTER);
        }

        addFilmButton.setOnAction(event -> {
            SceneController sc = new SceneController();
            try {
                sc.switchToAddFilm(primaryStage, scene);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } );

        removeFilmButton.setOnAction(event -> {
            SceneController sc = new SceneController();
            try {
                sc.switchToRemoveFilm(primaryStage, scene);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } );

        editUsersButton.setOnAction(event -> {
            SceneController sc = new SceneController();
            try {
                sc.switchToEditUsers(primaryStage, scene);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        topBox = new HBox(topText);
        topBox.setPadding(new Insets(20,20,20,20));

        Button logoutButton = new Button("LOG OUT");
        buttonBox = new HBox(logoutButton);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);
        buttonBox.setPadding(new Insets(20,20,40,100));

        okButton = new Button("OK");

        okButton.setOnAction(event -> OKbutton(primaryStage));

        logoutButton.setOnAction(event -> logoutButtonAction(primaryStage));

        choiceHBox = new HBox(choiceBox,okButton);
        choiceHBox.setPadding(new Insets(20,20,20,20));
        choiceHBox.setSpacing(20);
        choiceHBox.setAlignment(Pos.CENTER);

        topBox.setAlignment(Pos.CENTER);
        vBox = new VBox();
        vBox.getChildren().addAll(topBox, choiceHBox,buttonBoxAdmin,buttonBox);
        vBox.setSpacing(10);
        vBox.setAlignment(Pos.CENTER);

        Pane pane = new Pane(vBox);

        return pane;
    }

    /**
     * this method is for creating top text
     * @return String
     */
    public String getText(){
        text = "Welcome " + Login.loggedUser.getUsername();
        if(Login.loggedUser.isAdmin() && Login.loggedUser.isClubMember()){
            text += " (Admin - Club Member)!\nYou can either select film below or do edits";
        }

        else if(Login.loggedUser.isAdmin()){
            text += " (Admin)!\nYou can either select film below or do edits";
        }

        else if(Login.loggedUser.isClubMember()){
            text += " (Club Member)!\nSelect a film and then click OK to continue";
        }

        else {
            text += " Select a film and then click OK to continue";
        }
        return text;
    }

    void OKbutton(Stage primaryStage){
        SceneController sc = new SceneController();
        try {
            selectedFilm = Film.filmMap.get(choiceBox.getValue());
            sc.switchToFilmStage(primaryStage, scene);
        } catch (Exception ignored) {
        }
    }

    void logoutButtonAction(Stage primaryStage){
        SceneController sc = new SceneController();
        Login.loggedUser = null;
        try {
            sc.switchToLogIn(primaryStage, scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
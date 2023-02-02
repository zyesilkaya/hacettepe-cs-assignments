import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuStage extends Application {

    Scene scene;
    SceneController sc = new SceneController();

    @Override
    public void start(Stage primaryStage) throws Exception {

        scene = new Scene(getPane(primaryStage),700,300);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setTitle(FileReaderForBackup.title);
        primaryStage.getIcons().add(FileReaderForBackup.logo);
        primaryStage.show();
    }

    /**
     * this method is for creating menu page
     * @param primaryStage It is used to switch to another page when the back button is pressed.
     * @return VBox
     */
    VBox getPane(Stage primaryStage){
        VBox pane = new VBox(50);
        HBox topBox = new HBox(100);
        topBox.setPadding(new Insets(20,0,0,0));

        Label text = new Label("WELCOME TO HUCS CINEMA\n     RESERVATION SYSTEM");
        text.setAlignment(Pos.CENTER);
        text.setFont(Font.font("-fx-font-family: Comic Sans MS;", FontWeight.BOLD, FontPosture.REGULAR,30));
        text.setStyle("-fx-text-fill: SlateBlue;");
        HBox textBox = new HBox(text);
        textBox.setAlignment(Pos.CENTER);

        Button loginButton = new Button("LOGIN");
        loginButton.setOnAction(event -> loginButtonAction(primaryStage));
        Button signUpButton = new Button("SIGN UP");
        signUpButton.setOnAction(event -> signupButtonAction(primaryStage));

        HBox buttonBox = new HBox(10);
        buttonBox.getChildren().addAll(loginButton,signUpButton);

        Menu communicationMenu = new Menu("COMMUNICATION");
        Menu subMenu = new Menu("E-mail");
        MenuItem email1 = new MenuItem("b2210356048@gmail.com");
        MenuItem email2 = new MenuItem("HUCScinemasystem@gmail.com");
        subMenu.getItems().addAll(email1,email2);
        Menu phoneNumber = new Menu("Phone");
        MenuItem phone1 = new MenuItem("+90 555 555 55 55");
        MenuItem phone2 = new MenuItem("+90 312 010 10 01");
        phoneNumber.getItems().addAll(phone1,phone2);
        communicationMenu.getItems().addAll(subMenu,phoneNumber);

        Menu filmMenu = new Menu("FILMS");
        Menu hallMenu = new Menu("HALLS");

        for(Film f: Film.filmMap.values()){
            filmMenu.getItems().add(new MenuItem(f.getName()));
        }
        for(Hall h: Hall.hallMap.values()){
            hallMenu.getItems().add(new MenuItem(h.getName()));
        }

        Menu menu = new Menu("Menu ");

        // create a menubar
        MenuBar mb = new MenuBar();

        // add menu to menubar
        mb.getMenus().addAll(menu,filmMenu,hallMenu,communicationMenu);

        // create a VBox
        topBox.getChildren().addAll(mb,buttonBox);
        pane.getChildren().addAll(topBox,textBox);
        return pane;
    }

    /**
     * this method is for action to login button
     * @param primaryStage It is used to switch to login page when pressed login button.
     */
    void loginButtonAction(Stage primaryStage){
        try {
            sc.switchToLogIn(primaryStage, scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * this method is for action to sign up button
     * @param primaryStage It is used to switch to sign up page when pressed sign up button.
     */
    void signupButtonAction(Stage primaryStage){
        try {
            sc.switchToSignUP(primaryStage,scene);;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

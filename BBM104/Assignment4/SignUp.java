import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class SignUp extends Application {

    private TextField passwordText2 = new TextField();
    private TextField passwordText = new TextField();
    private PasswordField passwordField;
    private PasswordField passwordField2;
    private TextField principalTextField;
    private Button LoginButton;
    private Button SignIn;
    private Label errorLabel;
    private CheckBox showButton;
    private HBox passwBox = new HBox(10);
    HBox passwBox2;
    private String password;

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage){
        Scene sc = new Scene(getPane(),450,400);
        //button active için bi fonks aç event handler
        SignIn.setOnAction(event -> buttonActive());
        LoginButton.setOnAction(event -> {
            try {
                showStage(primaryStage, sc);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        primaryStage.setScene(sc);
        primaryStage.setResizable(false);
        primaryStage.setTitle(FileReaderForBackup.title);
        primaryStage.getIcons().add(FileReaderForBackup.logo);
        primaryStage.show();

    }

    /**
     * this method is for creating sign up page
     * @return GridPane
     */
     GridPane getPane(){
        GridPane pane = new GridPane();
        Text instr = new Text(50,40,"Welcome to the HUCS cinema reservation system!" +
                "\nFill the form bellow to create a new account" +
                "\nYou can go to login page by clicking LOG IN button");

        instr.setTextAlignment(TextAlignment.CENTER);
        HBox topBox = new HBox(instr);
        topBox.setPadding(new Insets(20,0,20,20));
        pane.getChildren().add(topBox);

        Label NameLabel = new Label("Username: ");
        NameLabel.setPrefWidth(100);
        principalTextField = new TextField();
        NameLabel.setTextAlignment(TextAlignment.RIGHT);
        principalTextField.setPrefColumnCount(10);
        principalTextField.setPromptText("Username: ");
        HBox LogBox = new HBox(NameLabel, principalTextField);
        LogBox.setAlignment(Pos.CENTER);

        Label Passwordlabel = new Label("Password");
        Passwordlabel.setPrefWidth(100);
        passwordField = new PasswordField();
        Passwordlabel.setTextAlignment(TextAlignment.RIGHT);
        passwordField.setPrefColumnCount(10);
        passwordField.setPromptText("Password");
        passwBox = new HBox(Passwordlabel, passwordField);
        passwBox.setAlignment(Pos.CENTER);

        Label Passwordlabel2 = new Label("Password");
        Passwordlabel2.setPrefWidth(100);
        passwordField2 = new PasswordField();
        Passwordlabel2.setTextAlignment(TextAlignment.RIGHT);
        passwordField2.setPrefColumnCount(10);
        passwordField2.setPromptText("Password");
        passwBox2 = new HBox(Passwordlabel2, passwordField2);
        passwBox2.setAlignment(Pos.CENTER);

        LoginButton = new Button("LOG IN");
        SignIn = new Button("SIGN UP");
        showButton = new CheckBox("Show");
        showButton.setOnAction(event -> showButtonActive());
        HBox showBox = new HBox(showButton);
        showBox.setAlignment(Pos.CENTER_RIGHT);

        HBox buttonBox = new HBox(LoginButton,SignIn);
        buttonBox.setSpacing(100);
        buttonBox.setPadding(new Insets(10,0,0,0));
        buttonBox.setAlignment(Pos.CENTER);

        errorLabel = new Label();
        HBox errorLabelBox = new HBox(errorLabel);
        errorLabelBox.setAlignment(Pos.CENTER);

        VBox vBox = new VBox(topBox, LogBox, passwBox,passwBox2,showBox, buttonBox,errorLabelBox);
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(10, 20, 10, 20));

        pane.getChildren().add(vBox);

        return pane;
    }

    /**
     * this method is for action to login button
     * @param scene It is used to switch page without refreshing
     * @param primaryStage It is used to switch to login page when pressed login button.
     */
    public void showStage(Stage primaryStage, Scene scene) throws IOException {
        SceneController sc = new SceneController();
        sc.switchToLogIn(primaryStage, scene);
    }

    /**
     * this method is for calling check method
     */
    public void buttonActive(){

        if(!isAcceptedNumber(passwordField, passwordField2,principalTextField) ){
            reset();
        }
    }

    /**
     * @param password1 is for checking password is valid or not
     * @param username is for checking username is valid or not
     * @return boolean
     */
    private boolean isAcceptedNumber(TextField password1,TextField password2,TextField username){

            if(username.getText().equals("")){
                errorLabel.setText("ERROR! Username cannot be empty");
                return false;
            }

            else if(password1.getText().equals("") && passwordText2.getText().equals("")){
                errorLabel.setText("ERROR! Password cannot be empty");
                return false;
            }

            else if(password2.getText().equals("") && passwordText.getText().equals("")){
                errorLabel.setText("ERROR! Password cannot be empty");
                return false;
            }
            else if(!password1.getText().equals(password2.getText())){
                errorLabel.setText("ERROR! Passwords do not match");
                return false;
            }

            else if(User.userMap.containsKey(username.getText())){
                errorLabel.setText("ERROR! This username already exists");
                return false;
            }

            else {
                password = "";
                if(showButton.isSelected()){
                    password = passwordText.getText();
                }
                else {
                    password = passwordField.getText();
                }
                errorLabel.setText("SUCCESS! You have successfully registered.");
                User.userMap.put(username.getText(),new User(username.getText(),User.hashPassword(password),false,false));
                passwordField.setText("");
                principalTextField.setText("");
                passwordField2.setText("");
                passwordText.setText("");
                passwordText2.setText("");
                return true;
            }
    }

    /**
     * this method is for cleaning the fields and playing error sound
     */
    private void reset(){
        String uriString = new File("assets\\effects\\error.mp3").toURI().toString();
        MediaPlayer Player = new MediaPlayer(new Media(uriString));
        Player.play();
        passwordField.setText("");
        principalTextField.setText("");
        passwordField2.setText("");
        passwordText.setText("");
        passwordText2.setText("");

    }

    /**
     *this method is for showing the password appears if it is hidden, and it is hidden if it is visible.
     */
    void showButtonActive(){
        if(showButton.isSelected()){
            passwordText2.setPrefColumnCount(10);
            passwordText2.setPromptText("Password");
            passwordText2.setText(passwordField2.getText());

            passwordText.setPrefColumnCount(10);
            passwordText.setPromptText("Password");
            passwordText.setText(passwordField.getText());

            passwBox.getChildren().remove(passwordField);
            passwBox.getChildren().add(1,passwordText);

            passwBox2.getChildren().remove(passwordField2);
            passwBox2.getChildren().add(1,passwordText2);
        }
        else {
            passwBox.getChildren().remove(passwordText);
            passwBox.getChildren().add(1, passwordField);

            passwBox2.getChildren().remove(passwordText2);
            passwBox2.getChildren().add(1, passwordField2);
        }
    }
}
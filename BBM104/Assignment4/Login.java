import javafx.application.Application;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class Login extends Application{
    SceneController sc = new SceneController();

    private static int count;
    static int interval = FileReaderForBackup.blockTime;

    static boolean isBlocked=false;
    private PasswordField passwText;
    private TextField passwordText = new TextField();
    private TextField usernameTextField;
    private Button LoginButton;
    private Button SignIn;
    private Label errorLabel;
    public static User loggedUser;
    private Scene scene;
    private CheckBox showButton;
    private HBox passwBox = new HBox(10);
    private Label passwordlabel;

    private String password;


    public void start(Stage primaryStage){

        scene = new Scene(getPane(),500,340);
        LoginButton.setOnAction(event -> {
            try {
                buttonActive(primaryStage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        SignIn.setOnAction(event -> {
            try {
                sc.switchToSignUP(primaryStage,scene);;
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setTitle(FileReaderForBackup.title);
        primaryStage.getIcons().add(FileReaderForBackup.logo);
        primaryStage.show();

    }

    /**
     * this function creates all labels and fields for login scene
     * @return GridPane for scene
     */

    GridPane getPane(){
        GridPane pane = new GridPane();
        Text instr = new Text(40,40,"Welcome to the HUCS Cinema Reservation System!" +
                "\nPlease enter your credentials below and click LOGIN." +
                "\nYou can create a new account by clicking SIGN UP button.");
        instr.setTextAlignment(TextAlignment.CENTER);
        HBox topBox = new HBox(instr);
        topBox.setPadding(new Insets(20,20,20,20));
        pane.getChildren().add(topBox);

        Label NameLabel = new Label("Username");
        NameLabel.setPrefWidth(160);
        usernameTextField = new TextField();
        NameLabel.setTextAlignment(TextAlignment.RIGHT);
        usernameTextField.setPrefColumnCount(10);
        usernameTextField.setPromptText("Username");
        HBox LogBox = new HBox(NameLabel, usernameTextField);
        LogBox.setAlignment(Pos.CENTER);

        passwordlabel = new Label("Password");
        passwordlabel.setPrefWidth(160);

        showButton = new CheckBox("Show");
        showButton.setOnAction(event -> showButtonActive());
        HBox showBox = new HBox(showButton);
        showBox.setAlignment(Pos.CENTER_RIGHT);
        passwText = new PasswordField();
        passwordlabel.setTextAlignment(TextAlignment.RIGHT);
        passwText.setPrefColumnCount(10);
        passwText.setPromptText("Password");
        passwBox = new HBox(passwordlabel, passwText);
        passwBox.setAlignment(Pos.CENTER);

        LoginButton = new Button("LOG IN");
        SignIn = new Button("SIGN UP");

        HBox buttonBox = new HBox(SignIn,LoginButton);
        buttonBox.setSpacing(100);
        buttonBox.setPadding(new Insets(10,0,0,0));
        buttonBox.setAlignment(Pos.CENTER);

        errorLabel = new Label();
        HBox errorLabelBox = new HBox(errorLabel);
        errorLabelBox.setAlignment(Pos.CENTER);

        VBox vBox = new VBox(topBox, LogBox, passwBox, showBox,buttonBox,errorLabelBox);
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(10, 20, 10, 20));

        pane.getChildren().add(vBox);
        return pane;

    }

    /**
     *this method is for showing the password appears if it is hidden, and it is hidden if it is visible.
     */

    void showButtonActive(){
        if(showButton.isSelected()){
            passwordText.setPrefColumnCount(10);
            passwordText.setPromptText("Password");
            passwordText.setText(passwText.getText());
            passwBox.getChildren().remove(passwText);
            passwBox.getChildren().add(1,passwordText);
        }
        else {
            passwText.setText(passwordText.getText());
            passwBox.getChildren().remove(passwordText);
            passwBox.getChildren().add(1,passwText);
        }
    }

    /**
     * this method is for action to login button
     * @param primaryStage It is used to switch to welcome page when pressed login button.
     */
    public void buttonActive(Stage primaryStage) throws IOException {
        password = "";
        if(showButton.isSelected()){
            password= passwordText.getText();
        }
        else {
            password = passwText.getText();
        }
        if (count== FileReaderForBackup.maxError-1){
            if(!User.userMap.containsKey(usernameTextField.getText()) || !User.userMap.get(usernameTextField.getText()).getPassword().equals(User.hashPassword(password))){
            errorLabel.setText("ERROR! Please wait for "+ FileReaderForBackup.blockTime +" seconds to make new operation.");
            isBlocked=true;
            count=0;
            setTimer();
            }
            else {
                count=0;
                loggedUser = User.userMap.get(usernameTextField.getText());
                SceneController sc = new SceneController();
                sc.switchToselectFilm(primaryStage,scene);
            }
        }

        else if(isBlocked){
            errorLabel.setText("ERROR! Please wait until end of the "+FileReaderForBackup.blockTime +" seconds to login.");
            reset();
        }

        else if(!isAcceptedNumber(password, usernameTextField)){
            count++;
            reset();
        }
        else {
            count=0;
            SceneController sc = new SceneController();
            sc.switchToselectFilm(primaryStage,scene);
        }
    }

    /**
     * @param password is for checking password is valid or not
     * @param username is for checking username is valid or not
     * @return boolean
     */
    private boolean isAcceptedNumber(String password,TextField username){

            if(username.getText().equals("")){
                errorLabel.setText("ERROR! Enter a username.");
                return false;
            }

            else if(password.equals("")){
                errorLabel.setText("ERROR! Enter a password.");
                return false;
            }

            else if(!User.userMap.containsKey(username.getText())){
                errorLabel.setText("ERROR! There is no such user.");
                return false;
            }

            else if(!User.userMap.get(username.getText()).getPassword().equals(User.hashPassword(password))){
                errorLabel.setText("ERROR! Password is wrong.");
                return false;
            }

            else {
                loggedUser = User.userMap.get(username.getText());
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
        passwText.setText("");
        usernameTextField.setText("");

    }

    /**
     * this method is for count down from blocktime
     */

    public void setTimer() {
        Timer timer = new Timer();
        interval = FileReaderForBackup.blockTime;
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                if(interval > 0) {
                    interval--;
                }
                else{
                    timer.cancel();
                    isBlocked=false;
                }
            }
        }, 0,1000);
    }
}

import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.geometry.Pos;
import javafx.util.Duration;

import java.io.File;
import java.io.FileNotFoundException;

public class FilmStage extends Application {
    Scene scene;
    Scene scen;
    public static Hall selectedHall;
    HBox buttonBox;
    HBox miniScreenBox;
    Button playButton;
    Button playButton2 = new Button("\u25B6");
    Button windButton;
    Button goBackButton;
    Button rewindButton;
    Button goForwardButton;
    Button removeHallButton;
    Button OKButton;
    Button addHallButton;
    Button commentButton;
    Button backButton;
    Button newScene;
    ChoiceBox<String> hallBox;
    MediaPlayer mediaPlayer;
    Slider timeSlider= new Slider();;

    public void start(Stage primaryStage){

        String media = new File("assets\\trailers\\"+ SelectFilmStage.selectedFilm.getAddress()).toURI().toString();
        mediaPlayer = new MediaPlayer(new Media(media));
        MediaView mediaView2 = new MediaView(mediaPlayer);
        StackPane pane1 = new StackPane();
        miniScreenBox = new HBox(playButton2);
        miniScreenBox.setAlignment(Pos.BOTTOM_CENTER);
        pane1.getChildren().add(mediaView2);
        pane1.getChildren().add(miniScreenBox);
        scen = new Scene(pane1);
        pane1.widthProperty().addListener((obs, oldVal, newVal) -> {mediaView2.setFitWidth(newVal.doubleValue());});
        pane1.heightProperty().addListener((obs, oldVal, newVal) -> {mediaView2.setFitHeight(newVal.doubleValue());});

        // Create a scene and place it in the stage
        scene = new Scene(getPane(primaryStage), 800, 550);
        primaryStage.setTitle(FileReaderForBackup.title); // Set the stage title
        primaryStage.getIcons().add(FileReaderForBackup.logo);
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show(); // Display the stage
    }

    /**
     * this method is for creating Hall page
     * @return BorderPane
     */
    BorderPane getPane(Stage primaryStage){

        String media2 = new File("extras\\ok.png").toURI().toString();
        ImageView imgView = new ImageView(new Image(media2));
        imgView.setFitWidth(24);
        imgView.setFitHeight(24);
        newScene = new Button("");
        newScene.setGraphic(imgView);
        newScene.setPrefWidth(24);

        Text filmNameText = new Text(SelectFilmStage.selectedFilm.getName()+" ("+ SelectFilmStage.selectedFilm.getDuration()+")");
        HBox topBox = new HBox(filmNameText);
        topBox.setAlignment(Pos.CENTER);

        hallBox = new ChoiceBox<>();
        hallBox.setPrefWidth(200);

        Hall.hallMap.forEach((k, v) ->{
            if(v.getShownFilm().equals(SelectFilmStage.selectedFilm.getName())){
                hallBox.getItems().add(k);
                hallBox.setValue(k);
            }
        });

        commentButton = new Button("Comments");
        commentButton.setOnAction(event -> commentButtonAction(primaryStage));
        backButton = new Button("\u25C0 BACK");
        backButton.setOnAction(event -> backButtonAction(primaryStage));
        OKButton= new Button("OK");
        removeHallButton= new Button("Remove Hall");
        removeHallButton.setOnAction(event -> removeHallButtonAction(primaryStage));
        addHallButton = new Button("Add Hall");
        addHallButton.setOnAction(event -> addHallButtonAction(primaryStage));

        if(Login.loggedUser.isAdmin()){
            buttonBox = new HBox(backButton,addHallButton,removeHallButton,hallBox,commentButton,OKButton);
        }
        else {
            buttonBox = new HBox(backButton,hallBox,commentButton,OKButton);
        }

        buttonBox.setSpacing(10);
        buttonBox.setAlignment(Pos.CENTER);

        MediaView mediaView = new MediaView(mediaPlayer);

        mediaView.setFitHeight(400);
        mediaView.setFitWidth(600);

        playButton2.setOnAction(e -> pausePlayButtonAction(playButton2));
        playButton2.setPrefSize(70,60);


        playButton = new Button("\u25B6");
        playButton.setOnAction(e -> pausePlayButtonAction(playButton));
        playButton.setPrefWidth(45);

        goForwardButton = new Button("\u23E9");
        goForwardButton.setPrefWidth(45);
        goForwardButton.setOnAction(event ->forwardButtonAction());

        goBackButton= new Button("\u23EA");
        goBackButton.setPrefWidth(45);
        goBackButton.setOnAction(event ->backwardButtonAction());

        rewindButton = new Button("\u23EE");
        rewindButton.setOnAction(e -> rewindButtonAction());
        rewindButton.setPrefWidth(50);

        windButton = new Button("\u23ED");
        windButton.setOnAction(e ->windButtonAction());
        windButton.setPrefWidth(50);

        Slider slVolume = new Slider();
        slVolume.setValue(50);
        slVolume.setOrientation(Orientation.VERTICAL);
        mediaPlayer.setVolume(slVolume.getValue()/100);

        slVolume.valueProperty().addListener(new InvalidationListener(){
            public void invalidated(Observable ov) {
                if (slVolume.isValueChanging()) {
                    // multiply duration by percentage calculated by slider position
                    //slVolume.setValue(slVolume.getValue() +1);
                    mediaPlayer.setVolume((slVolume.getValue() / 100.0));
                }
            }} );

        mediaPlayer.currentTimeProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                int currentTime = (int) mediaPlayer.getCurrentTime().toSeconds();
                timeSlider.setValue(currentTime/mediaPlayer.getTotalDuration().toSeconds()*100);

            }
        });

        // Add time slider
        HBox timeBox = new HBox(15);
        HBox.setHgrow(timeSlider,Priority.ALWAYS);
        timeSlider.setPrefWidth(570);
        timeBox.getChildren().add(timeSlider);

        timeSlider.valueProperty().addListener(new InvalidationListener() {
            public void invalidated(Observable ov) {
                if (timeSlider.isValueChanging()) {
                    mediaPlayer.seek(mediaPlayer.getTotalDuration().multiply(timeSlider.getValue() / 100.0));
                }
            }
        });

        HBox hBox = new HBox(15);
        hBox.getChildren().addAll(buttonBox);
        hBox.setAlignment(Pos.TOP_CENTER);

        VBox vBox = new VBox(10);
        vBox.setPadding(new Insets(0,50,0,0));
        vBox.getChildren().addAll(playButton,goForwardButton, goBackButton,rewindButton,windButton,slVolume,newScene);
        vBox.setAlignment(Pos.CENTER);

        VBox leftBox = new VBox(10);
        leftBox.setPadding(new Insets(50,0,0,50));
        leftBox.getChildren().addAll(topBox,mediaView,timeBox,hBox);

        BorderPane pane = new BorderPane();
        pane.setLeft(leftBox);
        pane.setRight(vBox);

        OKButton.setOnAction(event -> okButtonAction(primaryStage));
        newScene.setOnAction(event -> newSceneAction());
        return pane;
    }

    /**
     * this method takes forward media player 5 seconds.
     */
    void forwardButtonAction(){
        Duration time;
        time = (Duration.seconds((mediaPlayer.getCurrentTime().toSeconds()+5)));
        timeSlider.setValue((mediaPlayer.getCurrentTime().toSeconds()+5)/mediaPlayer.getTotalDuration().toSeconds()*100);
        mediaPlayer.seek(time);
    }

    /**
     * this method rewinds media player 5 seconds.
     */
    void backwardButtonAction(){
        Duration time;
        time = (Duration.seconds((mediaPlayer.getCurrentTime().toSeconds()-5)));
        timeSlider.setValue((mediaPlayer.getCurrentTime().toSeconds()-5)/mediaPlayer.getTotalDuration().toSeconds()*100);
        mediaPlayer.seek(time);

    }

    /**
     * this method stops or starts the media player.
     * @param playButton is for play button
     */
    void pausePlayButtonAction(Button playButton){

        if (playButton.getText().equals("\u25B6")) {
            mediaPlayer.play();
            playButton.setText("\u23F8");
        } else {
            mediaPlayer.pause();
            playButton.setText("\u25B6");
        }
    }

    /**
     *this method wraps the media player to the very end.
     */
    void windButtonAction(){
        mediaPlayer.seek(Duration.INDEFINITE);
        timeSlider.setValue(100);
    }

    /**
     * this method wraps the media player at the very beginning.
     */
    void rewindButtonAction(){
        mediaPlayer.seek(Duration.ZERO);
        timeSlider.setValue(0);
    }

    /**
     * @param primaryStage It is used to switch to welcome page when pressed back button.
     */
    void backButtonAction(Stage primaryStage){
        mediaPlayer.pause();
        SceneController sc = new SceneController();
        try {
            sc.switchToselectFilm(primaryStage,scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * this method determines the selected hall and changes the scene.
     * @param primaryStage It is used to switch to seat page when pressed OK button.
     */
    void okButtonAction(Stage primaryStage){
        try {
            selectedHall = Hall.hallMap.get(hallBox.getSelectionModel().getSelectedItem());
            mediaPlayer.pause();
            SceneController sc = new SceneController();
            sc.switchToSeatStage(primaryStage, scene);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * @param primaryStage It is used to switch to hall page when pressed remove hall button.
     */
    void removeHallButtonAction(Stage primaryStage){
        mediaPlayer.pause();
        SceneController sc = new SceneController();
        try {
            sc.switchToRemoveHall(primaryStage,scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * @param primaryStage It is used to switch to hall page when pressed add hall button.
     */
    void addHallButtonAction(Stage primaryStage){
        mediaPlayer.pause();
        SceneController sc = new SceneController();
        try {
            sc.switchToAddHall(primaryStage,scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * @param primaryStage It is used to switch to comment page when pressed comment button.
     */
    void commentButtonAction(Stage primaryStage){
        mediaPlayer.pause();
        SceneController sc = new SceneController();
        try {
            sc.switchToComments(primaryStage,scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void newSceneAction(){
        Stage stage = new Stage();
        stage.setScene(scen);
        stage.show();
    }
}

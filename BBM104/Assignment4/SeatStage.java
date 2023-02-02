import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.File;
import java.util.HashMap;

public class SeatStage extends Application {
    Text filmText;
    VBox textBox = new VBox();
    Text seatText = new Text();
    Text boughtText = new Text();
    Scene scene;

    ChoiceBox<String> userBox = new ChoiceBox<>();
    HashMap<RadioButton,Seat> seatButtons = new HashMap();
    @Override
    public void start(Stage primaryStage) throws Exception {
        scene = new Scene(getPane(primaryStage),80*FilmStage.selectedHall.getCol()+170,80*FilmStage.selectedHall.getRow()+185);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(FileReaderForBackup.logo);
        primaryStage.setTitle(FileReaderForBackup.title);
        primaryStage.show();
    }

    /**
     * this method creates all labels and fields for seat scene
     * @return VBox for scene
     */
    VBox getPane(Stage primaryStage){

        GridPane pane = new GridPane();
        pane.setVgap(10);
        pane.setHgap(10);

        VBox vBox = new VBox();

        filmText = new Text(SelectFilmStage.selectedFilm.getName()+" ("+ SelectFilmStage.selectedFilm.getDuration()+" minutes) Hall "+ FilmStage.selectedHall.getName());

        String image2 = new File("assets\\icons\\reserved_seat.png").toURI().toString();
        String image = new File("assets\\icons\\empty_seat.png").toURI().toString();

        for(Seat seat: FilmStage.selectedHall.seatArraylist){

            ImageView emptySeat = new ImageView(image);
            emptySeat.setFitHeight(50);
            emptySeat.setFitWidth(50);

            ImageView reservedSeat = new ImageView(image2);
            reservedSeat.setFitHeight(50);
            reservedSeat.setFitWidth(50);

            RadioButton seatButton = new RadioButton();
            seatButton.getStyleClass().remove("radio-button");
            seatButton.getStyleClass().add("toggle-button");
            seatButton.setGraphic(emptySeat);

            if (seat.isDisable()){
                seatButton.setGraphic(reservedSeat);
                seatButton.setSelected(true);
                if(!(seat.getOwner() == Login.loggedUser) && !(Login.loggedUser.isAdmin())){
                    seatButton.setDisable(true);
                }
            }

            seatButtons.put(seatButton,seat);
            HBox seatBox = new HBox(seatButton);
            pane.add(seatBox,seat.getColIndex()+1,seat.getRowIndex()+1);

            if(Login.loggedUser.isAdmin()){
                seatButton.addEventHandler(MouseEvent.MOUSE_ENTERED_TARGET,(MouseEvent key)-> mouseEnterHandler(seatButton));
                seatButton.addEventHandler(MouseEvent.MOUSE_EXITED,(MouseEvent key)-> {
                    seatText.setText("");
                });
            }
            seatButton.setOnMouseClicked(event -> seatButtonHandler(seatButton, emptySeat, reservedSeat));
        }

        User.userMap.forEach((k, v) ->{
            userBox.getItems().add(k);
        });
        userBox.setValue(Login.loggedUser.getUsername());

        Button backButton = new Button("\u25C0 BACK");
        HBox buttonBox = new HBox(backButton);
        buttonBox.setAlignment(Pos.CENTER_LEFT);
        buttonBox.setPadding(new Insets(0,0,0,10* FilmStage.selectedHall.getRow()));

        backButton.setOnAction(event -> backButtonAction(primaryStage));

        if (Login.loggedUser.isAdmin()){
            textBox.getChildren().add(seatText);
        }
        textBox.getChildren().add(boughtText);
        textBox.setAlignment(Pos.CENTER);
        textBox.setSpacing(5);
        pane.setAlignment(Pos.CENTER);

        vBox.setSpacing(10);
        vBox.setAlignment(Pos.CENTER);
        if(Login.loggedUser.isAdmin()){
            vBox.getChildren().addAll(filmText,pane,userBox, textBox,buttonBox);
        }
        else {
            vBox.getChildren().addAll(filmText,pane, textBox,buttonBox);
        }
        return vBox;
    }

    public void mouseEnterHandler(RadioButton seatButton){
        Seat selectedSeat = seatButtons.get(seatButton);
        if(!seatButton.isSelected() ){
            seatText.setText("Not bought yet!");
        }

        else {
            seatText.setText("Bought by "+selectedSeat.getOwner().getUsername()+" for "+selectedSeat.getCost()+" TL");
        }
    }

    /**
     * This method performs operations on the clicked button.
     * @param seatButton is for clicked button
     * @param emptySeat is for empty seat imageview
     * @param reservedSeat is for reserved seat imageview
     */
    public void seatButtonHandler(RadioButton seatButton, ImageView emptySeat, ImageView reservedSeat){
        Seat selectedSeat = seatButtons.get(seatButton);
        if(seatButton.isSelected()){
            selectedSeat.setOwner(getUser());
            selectedSeat.setCost(FilmStage.selectedHall.getPricePerSeat());
            selectedSeat.setDisable(true);
            boughtText.setText(getText(true,seatButton));
            seatButton.setGraphic(reservedSeat);
        }

        else {
            selectedSeat.setCost(0);
            selectedSeat.setOwner(null);
            selectedSeat.setDisable(false);
            seatButton.setGraphic(emptySeat);
            boughtText.setText(getText(false,seatButton));
            seatText.setText("");
        }
    }

    /**
     * This method is for getting user which is selected if logged user admin otherwise returns current user.
     * @return User
     */
    public User getUser(){
        if(Login.loggedUser.isAdmin()){
            String username = userBox.getValue();
            User user = User.userMap.get(username);
            return user;
        }
        return Login.loggedUser;
    }

    /**
     *This method is for getting text for seat actions
     * @param selected is indicates whether the seat has been selected.
     * @param seatButton is the button which is clicked
     * @return String
     */
    public String getText(boolean selected,RadioButton seatButton){
        Seat selectedSeat = seatButtons.get(seatButton);
        if(Login.loggedUser.isAdmin()&&selected){
           return "Seat at "+(selectedSeat.getRowIndex()+1)+"-"+(selectedSeat.getColIndex()+1)+" is bought for "+getUser().getUsername()+" for "+selectedSeat.getCost()+" TL successfully";
        }
        else if(Login.loggedUser.isAdmin()&&!selected){
            return "Seat at "+(selectedSeat.getRowIndex()+1)+"-"+(selectedSeat.getColIndex()+1)+" refunded successfully";
        }
        else if(!Login.loggedUser.isAdmin()&&selected){
            return "Seat at "+(selectedSeat.getRowIndex()+1)+"-"+(selectedSeat.getColIndex()+1)+" is bought for "+selectedSeat.getCost()+" TL successfully";
        }
        else {
            return "Seat at "+(selectedSeat.getRowIndex()+1)+"-"+(selectedSeat.getColIndex()+1)+" refunded successfully";
        }
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
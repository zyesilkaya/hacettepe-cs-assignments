import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.FileNotFoundException;

public class EditUsers extends Application {

    Scene scene;
    @Override
    public void start(Stage primaryStage) throws Exception {

        scene = new Scene(getPane(primaryStage),650,500);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setTitle(FileReaderForBackup.title);
        primaryStage.getIcons().add(FileReaderForBackup.logo);
        primaryStage.show();
    }

    /**
     * this method is for creating Edit Users page
     * @return VBox
     */
    VBox getPane(Stage primaryStage){

        VBox pane = new VBox();
        pane.setPadding(new Insets(20,20,20,20));
        pane.setSpacing(15);

        Button backButton = new Button("\u25C0 BACK");
        backButton.setOnAction(event -> backButtonAction(primaryStage));

        Button clubMemberButton = new Button("Promote/Demote Club Member");
        Button adminButton = new Button("Promote/Demote Admin");
        HBox buttonBox = new HBox(backButton,clubMemberButton,adminButton);
        buttonBox.setSpacing(20);
        buttonBox.setAlignment(Pos.CENTER);

        TableView tableView = new TableView();

        tableView.setPlaceholder(new Label("No user available in the database"));

        TableColumn<User, String> firstNameColumn = new TableColumn<>("First Name");
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        firstNameColumn.setMinWidth(200);

        TableColumn<User, String> clubMember = new TableColumn<>("Club Member");
        clubMember.setCellValueFactory(new PropertyValueFactory<>("clubMember"));
        clubMember.setMinWidth(120);

        TableColumn<User, String> Admin = new TableColumn<>("Admin");
        Admin.setCellValueFactory(new PropertyValueFactory<>("admin"));
        Admin.setMinWidth(100);

        tableView.getColumns().addAll(firstNameColumn,clubMember,Admin);

        //Admin kendisni görmicek
        ObservableList<User> list = FXCollections.observableArrayList();
        User.userMap.forEach((k, v) ->{
            if(v != Login.loggedUser){
                list.add(v);
            }
        });

        tableView.setItems(list);
        tableView.getSelectionModel().selectFirst();

        clubMemberButton.setOnAction(event1 -> {
            TablePosition position = (TablePosition) tableView.getSelectionModel().getSelectedCells().get(0);
            User.userMap.get(list.get(position.getRow()).getUsername()).setClubMember(!list.get(position.getRow()).isClubMember());
            tableView.refresh();
        });

        adminButton.setOnAction(event1 -> {
            TablePosition position = (TablePosition) tableView.getSelectionModel().getSelectedCells().get(0);
            User.userMap.get(list.get(position.getRow()).getUsername()).setAdmin(!list.get(position.getRow()).isAdmin());
            tableView.refresh();
        });

        pane.getChildren().add(tableView);
        pane.getChildren().add(buttonBox);
        return pane;
    }

    /**
     *
     * @param primaryStage It is used to switch to welcome page when pressed back button.
     */
    void backButtonAction(Stage primaryStage){
        SceneController sc = new SceneController();
        try {
            sc.switchToselectFilm(primaryStage,scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

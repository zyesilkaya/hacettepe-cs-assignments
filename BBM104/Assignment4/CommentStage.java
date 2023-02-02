import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.scene.control.ScrollPane;

import java.io.File;
import java.util.HashMap;


public class CommentStage extends Application {
    Stage stage;
    Scene scene;
    String starCreator;
    String commentText;
    String totalString;
    Label NameLabel;
    Label commentLabel;
    Button addComment;
    TextField usernameTextField;
    TextField commentField;
    TextArea commentArea;
    Button OKbutton;
    HashMap<Integer,Button> starButton = new HashMap<>();
    GridPane starBox;
    VBox total;
    SceneController sc = new SceneController();
    int commentNum=0;
    int clickIndex =0;
    int index =0;
    boolean selected;

    @Override
    public void start(Stage primaryStage) throws Exception {

        stage= primaryStage;
        scene = new Scene(getPane(primaryStage),750,getHeight());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle(FileReaderForBackup.title);
        primaryStage.getIcons().add(FileReaderForBackup.logo);
        stage.show();
    }
    /**
     * this method is for creating comment page
     * @return ScrollPane
     */
    ScrollPane getPane(Stage primaryStage){
        ScrollPane scrollPane = new ScrollPane();

        VBox vBox = new VBox(15);
        vBox.setAlignment(Pos.CENTER);
        vBox.setStyle("-fx-background-color: LightPink;");

        SelectFilmStage.selectedFilm.commentForFilm.forEach((k,c)->{
            commentNum++;
            starCreator="";
            Text bosluk = new Text("\n--------------------------------------------------------------------------------------------------------------\n");

            VBox commentBox = new VBox();
            String cssLayout = "-fx-border-color: DarkGray;\n" +
                    "-fx-border-width: 3;\n" +
                    "-fx-background-color: LavenderBlush;";

            commentBox.setStyle(cssLayout);
            commentBox.setPadding(new Insets(0,0,0,100));


            if(c.getComment().length()/90>0){
                totalString = "" ;
                for(int i=1;i<=c.getComment().length()/90;i++){
                    totalString += c.getComment().substring((i-1)*90,i*90)+"\n";
                }
                totalString+=c.getComment().substring(c.getComment().length()/90*90);
            }
            else {
                totalString=c.getComment();
            }

            Text comment = new Text(totalString);

            Text user = new Text(c.getUsername());

            for(int i=0;i<c.getStars();i++){
                starCreator+="\u2B50";
            }

            Text star = new Text(starCreator);
            commentBox.getChildren().addAll(user,star,comment,bosluk);
            vBox.getChildren().addAll(commentBox);
        });

        Button backButton = new Button("\u25C0 BACK");
        backButton.setOnAction(event -> backButtonAction(primaryStage));

        addComment = new Button("Add Comment");

        HBox buttonBox = new HBox(40);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(backButton,addComment);
        vBox.getChildren().add(buttonBox);

        addComment.setOnAction(event -> {
            vBox.getChildren().add(addCommentBox());
            addComment.setDisable(true);
        });
        scrollPane.setContent(vBox);
        return scrollPane;
    }

    int getHeight(){
        if(commentNum*190>850){
            return  850;
        }
        else {
            return commentNum*190;
        }
    }
    /**
     * this method is for adding comment box below the comments
     * @return Vbox
     */
    public VBox addCommentBox(){
        String emptyStar = new File("extras\\yildizbos.png").toURI().toString();
        String filledStar = new File("extras\\yildiz.png").toURI().toString();

        total = new VBox(15);

        OKbutton = new Button("OK");
        OKbutton.setPrefWidth(50);
        OKbutton.setOnAction(event -> {
            commentText=commentArea.getText().replace("\n"," ");
            Comment comment = new Comment(commentText, clickIndex+1, usernameTextField.getText(),SelectFilmStage.selectedFilm);
            Comment.commentMap.put(usernameTextField.getText(),comment);
            SelectFilmStage.selectedFilm.commentForFilm.put(usernameTextField.getText(),comment);
            total.getChildren().clear();
            try {
                sc.switchToComments(stage,scene);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        NameLabel = new Label("Username:");
        NameLabel.setPrefWidth(100);
        usernameTextField = new TextField();
        NameLabel.setTextAlignment(TextAlignment.RIGHT);
        usernameTextField.setPrefColumnCount(10);
        usernameTextField.setPromptText("Username");
        HBox nameBox = new HBox(NameLabel, usernameTextField);
        nameBox.setPadding(new Insets(0,0,0,30));

        commentLabel = new Label("Comment:");
        commentLabel.setPrefWidth(100);
        commentField = new TextField();
        commentArea = new TextArea();
        commentLabel.setTextAlignment(TextAlignment.RIGHT);
        commentArea.setPrefColumnCount(20);
        commentArea.setPrefRowCount(6);
        commentArea.setPromptText("Write your comment here");
        commentField.setPrefColumnCount(300);
        commentField.setPrefWidth(300);
        commentField.setPrefHeight(200);
        commentField.setPromptText("Write your comment here");
        HBox commentBox = new HBox(commentLabel, commentArea);
        commentBox.setPrefWidth(100);
        commentBox.setFillHeight(true);
        commentBox.setPadding(new Insets(0,0,0,30));

        starBox = new GridPane();
        starBox.setHgap(4);
        starBox.setPadding(new Insets(0,0,0,30));
        for(int i=1;i<11;i++){

            ImageView emptyView = new ImageView(emptyStar);
            emptyView.setFitHeight(30);
            emptyView.setFitWidth(30);

            ImageView imageView2 = new ImageView(filledStar);
            imageView2.setFitHeight(30);
            imageView2.setFitWidth(30);

            Button stars = new Button();
            stars.setGraphic(emptyView);
            starButton.put(i,stars);
            starBox.add(stars,i,0);
            stars.addEventHandler(MouseEvent.MOUSE_ENTERED_TARGET,(MouseEvent key)-> mouseEnterHandler(stars));
            stars.addEventHandler(MouseEvent.MOUSE_EXITED,(MouseEvent key)-> {

                if(findIndex(stars)> clickIndex){
                    int temp = clickIndex;
                    while (temp<findIndex(stars)){
                        ImageView emptyView2 = new ImageView(emptyStar);
                        emptyView2.setFitHeight(30);
                        emptyView2.setFitWidth(30);
                        temp++;
                        Button selectedButton = (Button) starBox.getChildren().get(temp);
                        selectedButton.setGraphic(emptyView2);

                    }
                }

                if(!selected){
                    starButton.forEach((k,v)->{
                        ImageView emptyView2 = new ImageView(emptyStar);
                        emptyView2.setFitHeight(30);
                        emptyView2.setFitWidth(30);
                        v.setGraphic(emptyView2);
                    });
                }
            });
            stars.setOnMouseClicked(event -> mouseClickHandler(stars));
        }
        total.getChildren().addAll(nameBox,starBox,commentBox,OKbutton);
        total.setAlignment(Pos.CENTER);
        addComment.setDisable(false);
        return total;
    }

    /**
     * this method allows the stars to be painted yellow when they hover over the stars.
     * @param stars is for selected star button.
     */
    public void mouseEnterHandler(Button stars){

        String image2 = new File("extras\\yildiz.png").toURI().toString();
        ImageView imageView = new ImageView(image2);
        imageView.setFitHeight(30);
        imageView.setFitWidth(30);
        stars.setGraphic(imageView);
        index =0;
        while (starBox.getChildren().get(index) != stars){
            ImageView imageView2 = new ImageView(image2);
            imageView2.setFitHeight(30);
            imageView2.setFitWidth(30);
            Button selectedButton = (Button) starBox.getChildren().get(index);
            selectedButton.setGraphic(imageView2);
            index++;
        }
    }

    /**
     * this method allows stars up to their index to be painted yellow when you click on the stars.
     * @param stars is for selected star button.
     */
    public void mouseClickHandler(Button stars){

        String emptyStar = new File("extras\\yildizbos.png").toURI().toString();
        String image2 = new File("extras\\yildiz.png").toURI().toString();
        ImageView imageView = new ImageView(image2);
        imageView.setFitHeight(30);
        imageView.setFitWidth(30);
        int tempIndex=clickIndex;
        clickIndex =0;
        selected = true;
        while (starBox.getChildren().get(clickIndex) != stars){
            ImageView imageView2 = new ImageView(image2);
            imageView2.setFitHeight(30);
            imageView2.setFitWidth(30);
            Button selectedButton = (Button) starBox.getChildren().get(clickIndex);
            selectedButton.setGraphic(imageView2);
            clickIndex++;
        }

        while (clickIndex<tempIndex){
            ImageView imageView2 = new ImageView(emptyStar);
            imageView2.setFitHeight(30);
            imageView2.setFitWidth(30);
            Button selectedButton = (Button) starBox.getChildren().get(tempIndex);
            selectedButton.setGraphic(imageView2);
            tempIndex--;
        }
    }

    /**
     * this method finds the index of the selected buttons.
     * @param stars is for selected star button.
     * @return int
     */
    int findIndex(Button stars){
        int tempIndex=0;
        while (starBox.getChildren().get(tempIndex) != stars){
            tempIndex++;
        }
        return tempIndex;
    }

    /**
     * @param primaryStage It is used to switch to hall page when pressed back button.
     */
    void backButtonAction(Stage primaryStage){

        try {
            sc.switchToFilmStage(primaryStage,scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

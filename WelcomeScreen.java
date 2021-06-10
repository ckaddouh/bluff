
// A welcome screen that has a play, instructions, and settings button
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.FileInputStream;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

// Create a class that extends BorderPane
public class WelcomeScreen extends BorderPane {

    private MainApp mainApp;
    public static String file_name;

    public WelcomeScreen(MainApp app) {
        super();
        this.mainApp = app;

        // Create a welcome label and format it
        Effect glow = new Glow(100.0);
        Label label = new Label();
        label.setText(" Welcome to CS BS! ");
        label.setEffect(glow);
        label.setTextFill(Color.web("#ff0c94"));
        label.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 48));
        label.setFont(Font.loadFont("file:PlayfairDisplay-Bold.ttf", 70));
        label.setStyle("text-decoration: underline overline; -fx-background-color: black");

        setCenter(label);
        setAlignment(label, Pos.CENTER);

        // Set the background of the screen
        setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        // format thigns vertically
        GridPane.setValignment(label, VPos.CENTER);

        // Create an instructions button and format it
        Button instructionButton = new Button("Instructions");
        instructionButton.setTextFill(Color.web("#dcfc5c"));
        instructionButton.setOnAction(e -> handleInstructionButton());
        DropShadow dropshadow = new DropShadow();
        dropshadow.setColor(Color.web("#dcfc5c"));
        instructionButton.setEffect(dropshadow);
        instructionButton.setStyle("-fx-background-color: #000000; ");
        // instructionButton.setStyle("-fx-effect: dropshadow(three-pass-box,
        // rgba(0,0,0,0.7), 5, 0.0, 0, 1)");
        instructionButton.setStyle(
                "-fx-font: 22 serif; -fx-background-color: #000000, linear-gradient(#000000 0%, #000000 20%, #000000 100%), linear-gradient(#000000, #000000), radial-gradient(center 50% 0%, radius 100%, #000000, #000000)");
        // instructionButton.setFont(Font.loadFont("CaviarDreams.ttf", 22));

        // Create a play button and format it
        Button play = new Button("Start");
        play.setTextFill(Color.web("#ea9dff"));
        play.setOnAction(e -> handleButtonStart());
        DropShadow drop = new DropShadow();
        drop.setColor(Color.web("#ea9dff"));
        play.setEffect(drop);
        // play.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.7), 5,
        // 0.0, 0, 1)");
        play.setStyle(
                "-fx-font: 22 serif; -fx-background-color: #000000, linear-gradient(#000000 0%, #000000 20%, #000000 100%), linear-gradient(#000000, #000000), radial-gradient(center 50% 0%, radius 100%, #000000, #000000)");

        // // Create a settings button and format it
        // Button settingsBT = new Button("Settings");
        // settingsBT.setOnAction(e -> handleButtonSettings());
        // settingsBT.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.7),
        // 5, 0.0, 0, 1)");
        // settingsBT.setStyle("-fx-font: 22 fantasy; -fx-background-color: #0072ab,
        // linear-gradient(#2a5880 0%, #1f2429 20%, #191d22 100%),
        // linear-gradient(#007be0, #3275c7), radial-gradient(center 50% 0%, radius
        // 100%, #64a5f5, #9ddbfa)");

        // Put these buttons into a GridPane at the bottom of the screen and set its
        // spacing
        GridPane bottom = new GridPane();
        bottom.addRow(0, play, instructionButton);

        bottom.setHgap(20);
        bottom.setVgap(10);
        bottom.setPadding(new Insets(10));

        // Align the GridPane and do more formatting
        setAlignment(bottom, Pos.CENTER);
        setBottom(bottom);
        bottom.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));

        // try {
        //     Image image = new Image(new FileInputStream("all_players_line.jpg"));
        //     ImageView imageView = new ImageView(image); 
        //     imageView.setX(400); 
        //     imageView.setY(10); 

        //     imageView.setFitHeight(200); 
        //     imageView.setFitWidth(40); 

        //     imageView.setPreserveRatio(true); 
        // } catch (FileNotFoundException e1) {
        //     // TODO Auto-generated catch block
        //     e1.printStackTrace();
        // } 




    }

    // Define methods to handle each button
    private void handleInstructionButton() {
        mainApp.showInstructionScreen();
    }

    public void handleButtonStart() {
        mainApp.showWaitScreen();
    }

    // public void handleButtonSettings() {
    // mainApp.showSettingsScreen();
    // }
}
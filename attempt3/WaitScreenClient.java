package attempt3;

// A welcome screen that has a play, instructions, and settings button
import java.io.FileNotFoundException;

import javafx.application.Application;
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

// Create a class that extends BorderPane
public class WaitScreenClient extends BorderPane {

    private Application mainApp;
    public static String file_name;

    public WaitScreenClient(Application app) {
        super();
        this.mainApp = app;

        // Create a welcome label and format it
        Effect glow = new Glow(100.0);
        Label label = new Label();
        label.setText(" Waiting for others to join... ");
        label.setEffect(glow);
        label.setTextFill(Color.web("#ff0c94"));
        label.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 48));
        label.setFont(Font.loadFont("file:PlayfairDisplay-Bold.ttf", 70));
        label.setStyle("text-decoration: underline overline; -fx-background-color: black");

        setCenter(label);
        setAlignment(label, Pos.CENTER);

        // Set the background of the screen
        setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        // format things vertically
        GridPane.setValignment(label, VPos.CENTER);

    }

    // Define methods to handle each button

    public void startGame() {
        ((ClientJavaFX)mainApp).showGameScreen();
    }

    // public void handleButtonSettings() {
    // mainApp.showSettingsScreen();
    // }
}
package attempt3;
// An EndScreen that displays the winner and gives an option to go to menu or play again.

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Glow;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

// Create a class that extends Border Pane
public class EndScreen extends BorderPane {

    private Application mainApp;

    public EndScreen(Application app) {
        super();
        this.mainApp = app;

        // Create a title and format it
        Text text = new Text();
        text.setText("\nGame Ended");
        text.setFill(Color.web("ff0c94"));
        text.setFont(Font.loadFont("file:PlayfairDisplay-Bold.ttf", 35));
        Effect glow = new Glow(100.0);
        text.setEffect(glow);
        // Place the title at the top center of the screen
        setTop(text);
        setAlignment(text, Pos.CENTER);

        // Create 2 buttons: back and play
        Button changeMenuScreenButton = new Button("Menu");
        changeMenuScreenButton.setTextFill(Color.web("#dcfc5c"));
        changeMenuScreenButton.setOnAction(e -> handleButtonMenu());
        DropShadow drop1 = new DropShadow();
        drop1.setColor(Color.web("#dcfc5c"));
        changeMenuScreenButton.setEffect(drop1);
        changeMenuScreenButton.setStyle("-fx-font: 22 serif; -fx-background-color: #000000, linear-gradient(#000000 0%, #000000 20%, #000000 100%), linear-gradient(#000000, #000000), radial-gradient(center 50% 0%, radius 100%, #000000, #000000)");

        
        Button replayGameButton = new Button("Replay");
        replayGameButton.setTextFill(Color.web("#ea9dff"));
        replayGameButton.setOnAction(e -> handleButtonReplay());
        DropShadow drop2 = new DropShadow();
        drop2.setColor(Color.web("#ea9dff"));
        replayGameButton.setEffect(drop2);
        replayGameButton.setStyle("-fx-font: 22 serif; -fx-background-color: #000000, linear-gradient(#000000 0%, #000000 20%, #000000 100%), linear-gradient(#000000, #000000), radial-gradient(center 50% 0%, radius 100%, #000000, #000000)");
        

        // Make the bottom of the BorderPane a GridPane of the buttons
        GridPane bottom = new GridPane();
        bottom.addRow(0, changeMenuScreenButton, replayGameButton);

        // Set the sapcing and position of the GridPane
        bottom.setHgap(10);
        bottom.setVgap(10);
        bottom.setPadding(new Insets(10));

        setAlignment(bottom, Pos.BOTTOM_RIGHT);
        setBottom(bottom);

        // Create text for the actual instructions in the center
        Text winner = new Text("Player " + GameScreen.winner + " has won the game!");
        winner.setFill(Color.web("#ff0c94"));
        // Format the text
        winner.setFont(Font.font("verdana", FontPosture.REGULAR, 25));
        setCenter(winner);
        setAlignment(winner, Pos.CENTER);

        // Set the background color of the screen
        setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));

    }

    // Define methods to handle screen changes for each button

    private void handleButtonMenu() {
        //mainApp.showWelcomeScreen();
    }

    private void handleButtonReplay() {
        //mainApp.showGameScreen();
    }
}
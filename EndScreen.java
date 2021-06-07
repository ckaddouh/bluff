// An EndScreen that displays the winner and gives an option to go to menu or play again.

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
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

    private MainApp mainApp;

    public EndScreen(MainApp app) {
        super();
        this.mainApp = app;

        // Create a title and format it
        Text text = new Text();
        text.setText("\nInstructions"); 
        text.setFill(Color.DODGERBLUE);
        text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 35));
        // Place the title at the top center of the screen
        setTop(text);
        setAlignment(text, Pos.CENTER);

        // Create 2 buttons: back and play
        Button changeMenuScreenButton = new Button("Menu");
        changeMenuScreenButton.setOnAction(e -> handleButtonMenu());
        
        Button replayGameButton = new Button("Replay");
        replayGameButton.setOnAction(e -> handleButtonReplay());

        // Set the styles of the buttons
        replayGameButton.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.7), 5, 0.0, 0, 1)");
        replayGameButton.setStyle("-fx-font: 22 fantasy; -fx-background-color: #ff0c94, linear-gradient(#2a5880 0%, #1f2429 20%, #191d22 100%), linear-gradient(#007be0, #3275c7), radial-gradient(center 50% 0%, radius 100%, #64a5f5, #9ddbfa)");

        replayGameButton.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.7), 5, 0.0, 0, 1)");
        replayGameButton.setStyle("-fx-font: 22 fantasy; -fx-background-color: #ff0c94, linear-gradient(#2a5880 0%, #1f2429 20%, #191d22 100%), linear-gradient(#007be0, #3275c7), radial-gradient(center 50% 0%, radius 100%, #64a5f5, #9ddbfa)");

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
        // Format the text
        winner.setFont(Font.font("verdana", FontPosture.REGULAR, 25));
        setAlignment(winner, Pos.CENTER);
        setCenter(winner);

        // Set the background color of the screen
        setBackground( new Background( new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));

    }

    // Define methods to handle screen changes for each button

    private void handleButtonMenu(){
        mainApp.showWelcomeScreen();
    }
    
    private void handleButtonReplay() {
        mainApp.showGameScreen();
    }
}

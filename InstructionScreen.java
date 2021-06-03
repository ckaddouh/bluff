// An instruction screen from which users can go back to the Welcome or play

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
public class InstructionScreen extends BorderPane {

    private MainApp mainApp;

    public InstructionScreen(MainApp app) {
        super();
        this.mainApp = app;

        // Create a title and format it
        Text text = new Text();
        text.setText("\nInstructions");
        text.setFill(Color.web("ff0c94"));
        text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 35));
        // Place the title at the top center of the screen
        setTop(text);
        setAlignment(text, Pos.CENTER);

        // Create 2 buttons: back and play
        Button changeScreenBack = new Button("Back");
        changeScreenBack.setOnAction(e -> handleButtonBack());

        Button changeScreenPlay = new Button("Play");
        changeScreenPlay.setOnAction(e -> handleButtonPlay());

        // Set the styles of the buttons
        changeScreenBack.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.7), 5, 0.0, 0, 1)");
        changeScreenBack.setStyle(
                "-fx-font: 22 fantasy; -fx-background-color: #b30768, linear-gradient(#9c2f6c 0%, #291f29 20%, #221921 100%), linear-gradient(#e000c6, #c732c2), radial-gradient(center 50% 0%, radius 100%, #fc6af0, #ff00d4)");

        changeScreenPlay.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.7), 5, 0.0, 0, 1)");
        changeScreenPlay.setStyle(
                "-fx-font: 22 fantasy; -fx-background-color: #b30768, linear-gradient(#9c2f6c 0%, #291f29 20%, #221921 100%), linear-gradient(#e000c6, #c732c2), radial-gradient(center 50% 0%, radius 100%, #fc6af0, #ff00d4)");

        // Make the bottom of the BorderPane a GridPane of the buttons
        GridPane bottom = new GridPane();
        bottom.addRow(0, changeScreenBack, changeScreenPlay);

        // Set the sapcing and position of the GridPane
        bottom.setHgap(10);
        bottom.setVgap(10);
        bottom.setPadding(new Insets(10));

        setAlignment(bottom, Pos.BOTTOM_RIGHT);
        setBottom(bottom);

        // Create text for the actual instructions in the center
        Text inst = new Text(
                "                                  Welcome to CS BS!\nThe game is simple: every player must put at least one card face down in \nthe center when it is their turn. You must put a card down that comes sequentially \nin order starting with an ace at the beginning of the game. \n If you do not have a card with the necessary number, you can bluff\n and put other cards. However, if someone catches your bluff, \n they can press the 'BS' button, and you will \nhave to take the entire pile into your hand. \n If someone incorrectly calls 'BS' on another player, they take the pile. The \n person to finish their cards first wins. To begin, press play!");
        // Format the text
        inst.setFont(Font.font("verdana", FontPosture.REGULAR, 25));
        inst.setFill(Color.web("#ff0c94"));
        setAlignment(inst, Pos.CENTER);
        setCenter(inst);

        // Set the background color of the screen
        setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));

    }

    // Define methods to handle screen changes for each button

    private void handleButtonBack() {
        mainApp.showWelcomeScreen();
    }

    private void handleButtonPlay() {
        mainApp.showGameScreen();
    }
}

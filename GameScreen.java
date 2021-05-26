// A welcome screen that has a play, instructions, and settings button
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

// Create a class that extends BorderPane
public class GameScreen extends BorderPane {

    private MainApp mainApp;
    public static String file_name;
    private int numPlayers;
    private ArrayList<Card> deck = new ArrayList<>();
    private ArrayList<ArrayList<Card>> hands = new ArrayList<ArrayList<Card>>();
    private String[] suits = {"H", "D", "S", "C"};
    private ArrayList<Card> pile = new ArrayList<>();
    private int playerTurn = 0;
    private int currentVal = 1;

    public GameScreen(MainApp app) throws FileNotFoundException {
        super();
        this.mainApp = app;
        numPlayers = 4;

        for (int i = 1; i <= 13; i++) {
            for (int s = 0; s < 4; s++) {
                deck.add(new Card(i, suits[s]));
            }
        }
        for (int i = 0; i < numPlayers; i++) {
            ArrayList<Card> temp = new ArrayList<Card>();
            for (int j = 0; j < 52/numPlayers; j++) {
                temp.add(deck.remove((int)(Math.random()*deck.size())));
            }
            hands.add(temp);
        }
        for (int i = 0; i < deck.size(); i++) {
            hands.get(numPlayers-1).add(deck.remove(i));
        }

        Card startingAce = new Card(1, "S");
        for (int j = 0; j < hands.size(); j++) {
            for (int i = 0; i < hands.get(j).size(); i++) {
                if (hands.get(j).get(i).equals(startingAce)) {
                    pile.add(hands.get(j).remove(i));
                    currentVal++;
                    playerTurn = (j+1) % numPlayers;
                }
            }
        }
        

        // Create a welcome label and format it
        Label label = new Label();
        label.setText(" Game screen ");
        label.setTextFill(Color.WHITE);
        label.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 48));
        label.setStyle("text-decoration: underline overline; -fx-background-color: dodgerblue");

        setTop(label);
        setAlignment(label, Pos.CENTER);

        // Set the background of the screen
        setBackground( new Background( new BackgroundFill(Color.LIGHTCYAN, CornerRadii.EMPTY, Insets.EMPTY)));
        // format thigns vertically
        GridPane.setValignment(label, VPos.CENTER);

        BorderPane screen = new BorderPane();
        setCenter(screen);
        FileInputStream imageStream = new FileInputStream("./cards/blue_back.png");
        ImageView p = new ImageView(new Image(imageStream));
        p.setFitWidth(130);
        p.setFitHeight(130);
        screen.setCenter(p);

        // // Create an instructions button and format it
        // Button changeScreenButton = new Button("Instructions");
        // changeScreenButton.setOnAction(e -> handleButton());
        // changeScreenButton.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.7), 5, 0.0, 0, 1)");
        // changeScreenButton.setStyle("-fx-font: 22 fantasy; -fx-background-color: #0072ab, linear-gradient(#2a5880 0%, #1f2429 20%, #191d22 100%), linear-gradient(#007be0, #3275c7), radial-gradient(center 50% 0%, radius 100%, #64a5f5, #9ddbfa)");
        
        // // Create a play button and format it
        // Button play = new Button("Start");
        // play.setOnAction(e -> handleButtonStart());
        // play.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.7), 5, 0.0, 0, 1)");
        // play.setStyle("-fx-font: 22 fantasy; -fx-background-color: #0072ab, linear-gradient(#2a5880 0%, #1f2429 20%, #191d22 100%), linear-gradient(#007be0, #3275c7), radial-gradient(center 50% 0%, radius 100%, #64a5f5, #9ddbfa)");

        // // Create a settings button and format it
        // Button settingsBT = new Button("Settings");
        // settingsBT.setOnAction(e -> handleButtonSettings());
        // settingsBT.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.7), 5, 0.0, 0, 1)");
        // settingsBT.setStyle("-fx-font: 22 fantasy; -fx-background-color: #0072ab, linear-gradient(#2a5880 0%, #1f2429 20%, #191d22 100%), linear-gradient(#007be0, #3275c7), radial-gradient(center 50% 0%, radius 100%, #64a5f5, #9ddbfa)");
        
        // // Put these buttons into a GridPane at the bottom of the screen and set its spacing
        // GridPane bottom = new GridPane();
        // bottom.addRow(0, changeScreenButton, play, settingsBT);

        // bottom.setHgap(20);
        // bottom.setVgap(10);
        // bottom.setPadding(new Insets(10));

        // // Align the GridPane and do more formatting
        // setAlignment(bottom, Pos.CENTER);
        // setBottom(bottom);
        // bottom.setBackground( new Background( new BackgroundFill(Color.LIGHTCYAN, CornerRadii.EMPTY, Insets.EMPTY)));

        

    }


    // public void handleButtonSettings() {
    //     mainApp.showSettingsScreen();
    // }
}
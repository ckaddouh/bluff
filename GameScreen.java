// A welcome screen that has a play, instructions, and settings button
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

import attempt3.Card;
import attempt3.Hand;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;

// Create a class that extends BorderPane
public class GameScreen extends Application {

    private ClientApp mainApp;
    public static String file_name;
    public static int numPlayers;
    private ArrayList<Card> deck = new ArrayList<>();
    private ArrayList<Hand> hands = new ArrayList<>();
    private String[] suits = { "H", "D", "S", "C" };
    private ArrayList<Card> pile = new ArrayList<>();
    public static int playerTurn = 0;
    private int currentVal = 1;
    private Button sortButton;
    private BorderPane screen;
    private HBox hbox = new HBox();
    private int numCardsAdded = 0;
    private ArrayList<ArrayList<Integer>> removeIndexes;
    public static int winner = 0;
    public Button BSButton;
    public static int playersLeft = 4;
    private PrintWriter out;
    private Scene scene;
    private BorderPane pane;


    public GameScreen(PrintWriter o, Scene s, BorderPane p) throws FileNotFoundException {
        out = o;
        scene = s;
        pane = p;
        
        
        //super();
        //this.mainApp = app;
        //numPlayers = 4;

      
    }

    public void showHand() {
        hbox.getChildren().clear();

        for (Card c : hands.get(0).getHand()) {
            hbox.getChildren().add(c.faceUp);
        }
    }

    public void handleSortButton() {
        hands.set(0, new Hand(hands.get(playerTurn).mergeSort(hands.get(playerTurn).getHand())));
        showHand();
        screen.setBottom(hbox);
    }

    public void handleBSButton() {
        for (int i = 0; i < numCardsAdded; i++) {
            if (pile.get(pile.size() - 1).getValue() != currentVal)
                hands.get(playerTurn--).getHand().addAll(pile);
            else
                hands.get(playerTurn).getHand().addAll(pile);
        }
    }

    public void resizeHand() {
        for (Card c : hands.get(playerTurn).getHand()) {
            c.resize(1150 / (hands.get(playerTurn).getHand().size() + 2), 100);
        }
    }

    public void handlePileClicked() {
        Hand copy = new Hand(hands.get(0));

        for (int i = 0; i < removeIndexes.get(0).size(); i++) {
            Card c = copy.getHand().get(removeIndexes.get(0).get(i));
            pile.add(c);
            hands.get(0).getHand().remove(c);
        }
        showHand();
        removeIndexes.clear();

    }

    @Override
    public void start(Stage arg0) throws Exception {
        for (int i = 1; i <= 13; i++) {
            for (int s = 0; s < 4; s++) {
                deck.add(new Card(i, suits[s]));
            }
        }
        for (int i = 0; i < numPlayers; i++) {
            ArrayList<Card> temp = new ArrayList<Card>();
            for (int j = 0; j < 52 / numPlayers; j++) {
                temp.add(deck.remove((int) (Math.random() * deck.size())));
            }
            hands.add(new Hand(temp));
        }
        for (int i = 0; i < deck.size(); i++) {
            hands.get(numPlayers - 1).getHand().add(deck.remove(i));
        }

        Card startingAce = new Card(1, "S");
        for (int j = 0; j < hands.size(); j++) {
            for (int i = 0; i < hands.get(j).getHand().size(); i++) {
                if (hands.get(j).getHand().get(i).equals(startingAce)) {
                    pile.add(hands.get(j).getHand().remove(i));
                    currentVal++;
                    playerTurn = (j + 1) % numPlayers;
                }
            }
        }

        // Create a welcome label and format it
        Text label = new Text();
        label.setText("Game screen");
        label.setFill(Color.WHITE);
        label.setFont(Font.loadFont("file:PlayfairDisplay-Bold.ttf", 48));
        // label.setStyle("text-decoration: underline overline; -fx-background-color:
        // dodgerblue");

        Effect glow = new Glow(100.0);
        label.setEffect(glow);


        pane.setTop(label);
        pane.setAlignment(label, Pos.CENTER);

        // Set the background of the screen
        pane.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        // format thigns vertically
        GridPane.setValignment(label, VPos.CENTER);

        screen = new BorderPane();

        FileInputStream imageStream = new FileInputStream("./cards/custom_back.png");
        ImageView p = new ImageView(new Image(imageStream));
        p.setPickOnBounds(true);
        p.setOnMouseClicked((MouseEvent e) -> {
            System.out.println("Turn over!"); // change functionality
            playerTurn = (playerTurn + 1) % numPlayers;
            handlePileClicked();
        });

        p.setFitWidth(100);
        p.setFitHeight(125);
        screen.setCenter(p);

        sortButton = new Button("Sort");
        sortButton.setOnAction(e -> handleSortButton());
        sortButton.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.7), 5, 0.0, 0, 1)");
        sortButton.setStyle(
                "-fx-font: 22 fantasy; -fx-background-color: #0f723c, linear-gradient(#2a5880 0%, #1f2429 20%, #191d22 100%), linear-gradient(#007be0, #3275c7), radial-gradient(center 50% 0%, radius 100%, #64a5f5, #9ddbfa)");

        hbox.setPadding(new Insets(15, 12, 15, 12));
        hbox.setSpacing(10);
        hbox.setStyle("-fx-background-color: black;");
        hbox.setPrefWidth(1200);
        hbox.setPrefHeight(130);
        pane.setAlignment(hbox, Pos.CENTER);
        hbox.setAlignment(Pos.CENTER);

        
        BSButton = new Button("BS");
        BSButton.setOnAction(e -> handleBSButton());
        DropShadow drop = new DropShadow();
        drop.setColor(Color.web("#ea9dff"));
        BSButton.setEffect(drop);
        BSButton.setStyle(
                "-fx-font: 22 serif; -fx-background-color: #000000, linear-gradient(#000000 0%, #000000 20%, #000000 100%), linear-gradient(#000000, #000000), radial-gradient(center 50% 0%, radius 100%, #000000, #000000)");

        pane.setCenter(screen);

        showHand();
        hbox.getChildren().add(BSButton);
        screen.setBottom(hbox);

        removeIndexes = new ArrayList<ArrayList<Integer>>();
        for (int i = 0; i < numPlayers; i++) {
            removeIndexes.add(new ArrayList<Integer>());
        }

        System.out.println(playerTurn);
        for (int i = 0; i < hands.get(0).getHand().size(); i++) {
            Card c = hands.get(0).getHand().get(i);
            final int INDEX = i;
            c.faceUp.setPickOnBounds(true);
            c.faceUp.setOnMouseClicked((MouseEvent e) -> {
                System.out.println("before method");
                removeIndexes.get(0).add(INDEX);
            });
        }
    
        
    }

}
package attempt3;

// A welcome screen that has a play, instructions, and settings button
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javafx.application.Application;
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
import javafx.scene.input.MouseEvent;
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
import javafx.scene.layout.HBox;

// Create a class that extends BorderPane
public class GameScreenClient extends GameScreen {

    public static String file_name;
    private String[] suits = {"H", "D", "S", "C"};
    private Button sortButton;
    private BorderPane screen;
    private HBox hbox = new HBox();
    private int numCardsAdded = 0;
    private ArrayList<ArrayList<Integer>> removeIndexes;
    public static int winner = 0;
    public static Button BSButton;
    private int playerNum = ServerJavaFX.connectionList.size();

    public GameScreenClient(Application app) throws FileNotFoundException {
        super(app);
        

        // Create a welcome label and format it
        Label label = new Label();
        label.setText(" Game screen Client ");
        label.setTextFill(Color.WHITE);
        label.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 48));
        label.setStyle("text-decoration: underline overline; -fx-background-color: dodgerblue");

        setTop(label);
        setAlignment(label, Pos.CENTER);

        // Set the background of the screen
        setBackground( new Background( new BackgroundFill(Color.LIGHTCYAN, CornerRadii.EMPTY, Insets.EMPTY)));
        // format thigns vertically
        GridPane.setValignment(label, VPos.CENTER);

        screen = new BorderPane();
        
        FileInputStream imageStream = new FileInputStream("./cards/blue_back.png");
        ImageView p = new ImageView(new Image(imageStream));
        p.setPickOnBounds(true);
        p.setOnMouseClicked((MouseEvent e) -> {
            System.out.println("Turn over!"); // change functionality
            playerTurn = (playerTurn + 1) % numPlayers;   
            handlePileClicked();         
        });

        p.setFitWidth(100);
        p.setFitHeight(100);
        screen.setCenter(p);

        sortButton = new Button("Sort");
        sortButton.setOnAction(e -> handleSortButton());
        sortButton.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.7), 5, 0.0, 0, 1)");
        sortButton.setStyle("-fx-font: 22 fantasy; -fx-background-color: #0072ab, linear-gradient(#2a5880 0%, #1f2429 20%, #191d22 100%), linear-gradient(#007be0, #3275c7), radial-gradient(center 50% 0%, radius 100%, #64a5f5, #9ddbfa)");
        
        hbox.setPadding(new Insets(15, 12, 15, 12));
        hbox.setSpacing(10);
        hbox.setStyle("-fx-background-color: #336699;");
        hbox.setPrefWidth(1200);
        hbox.setPrefHeight(130);
        setAlignment(hbox, Pos.CENTER);
        hbox.setAlignment(Pos.CENTER);

        
        BSButton = new Button("BS");
        BSButton.setOnAction(e -> handleBSButton());
        BSButton.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.7), 5, 0.0, 0, 1)");
        BSButton.setStyle("-fx-font: 22 fantasy; -fx-background-color: #0072ab, linear-gradient(#2a5880 0%, #1f2429 20%, #191d22 100%), linear-gradient(#007be0, #3275c7), radial-gradient(center 50% 0%, radius 100%, #64a5f5, #9ddbfa)");
        

        setCenter(screen);
      
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
        System.out.println("testing");
        // for (int i = 0; i < numCardsAdded; i++) {
        //     if (pile.get(pile.size()-1).getValue() != currentVal)
        //         hands.get(playerTurn--).getHand().addAll(pile);
        //     else
        //         hands.get(playerTurn).getHand().addAll(pile);
        // }
    }

    public void resizeHand() {
        for (Card c : hands.get(playerTurn).getHand()) {
            c.resize(1150/(hands.get(playerTurn).getHand().size() + 2), 100);
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


}
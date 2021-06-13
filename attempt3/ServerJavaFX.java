/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attempt3;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
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
import javafx.stage.Stage;


public class ServerJavaFX extends Application {
    public TextArea txtAreaDisplay;
    public static List<TaskClientConnection> connectionList = new ArrayList<TaskClientConnection>();
    //public static List<Application> clientList = new ArrayList<>();
    public static String file_name;
    public static int numPlayers;
    private ArrayList<Card> deck = new ArrayList<>();
    private ArrayList<Hand> hands = new ArrayList<>();
    private String[] suits = { "H", "D", "S", "C" };
    private ArrayList<Card> pile = new ArrayList<>();
    private int playerTurn = 0;
    private int currentVal = 1;
    private Button sortButton;
    private BorderPane screen;
    private HBox hbox = new HBox();
    private int numCardsAdded = 0;
    private ArrayList<ArrayList<Integer>> removeIndexes;
    public static int winner = 0;
    public static Button BSButton;

    private Stage stage;
    private WelcomeScreen welcomeScreen;
    private InstructionScreen instructionScreen;
    private WaitScreen waitScreen;
    private GameScreen gameScreen;
    private EndScreen endScreen;

    @Override // Override the start method in the Application class
    public void start(Stage primaryStage) throws FileNotFoundException {
        stage = primaryStage;
        welcomeScreen = new WelcomeScreen(this);
        instructionScreen = new InstructionScreen(this);
        waitScreen = new WaitScreen(this);
        gameScreen = new GameScreen(this);
        endScreen = new EndScreen(this);

        // Create a scene and place it in the stage
        Scene scene = new Scene(gameScreen, 1200, 500);
        stage.setTitle("CS BS!"); // Set the stage title
        stage.setScene(scene); // Place the scene in the stage
        stage.show(); // Display the stage

        // create a new thread
        new Thread(() -> {
            try {
                // Create a server socket
                ServerSocket serverSocket = new ServerSocket(ConnectionUtil.port);

                // append message of the Text Area of UI (GUI Thread)
                Platform.runLater(() -> System.out.println("New server started at " + new Date() + '\n'));

                // continous loop
                while (true) {
                    // Listen for a connection request, add new connection to the list
                    Socket socket = serverSocket.accept();
                    TaskClientConnection connection = new TaskClientConnection(socket, this);
                    connectionList.add(connection);

                    // create a new thread
                    Thread thread = new Thread(connection);
                    thread.start();

                }

            } catch (IOException ex) {
                //txtAreaDisplay.appendText(ex.toString() + '\n');
            }
        }).start();

    }

    public void showWelcomeScreen() {
        Scene scene = stage.getScene();
        scene.setRoot(welcomeScreen);
    }

    public void showInstructionScreen() {
        Scene scene = stage.getScene();
        scene.setRoot(instructionScreen);
    }

    public void showWaitScreen() {
        Scene scene = stage.getScene();
        scene.setRoot(waitScreen);
    }

    public void showGameScreen() {
        Scene scene = stage.getScene();
        scene.setRoot(gameScreen);
    }

    public void showEndScreen() {
        Scene scene = stage.getScene();
        scene.setRoot(endScreen);
    }

    /**
     * The main method is only needed for the IDE with limited JavaFX support. Not
     * needed for running from the command line.
     */
    public static void main(String[] args) {
        launch(args);
    }

    // send message to all connected clients
    public void broadcast(String message) {
        // for (TaskClientConnection clientConnection : this.connectionList) {
        //     clientConnection.(message);
        // }
    }

}

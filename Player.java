import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

import attempt3.InstructionScreen;
import attempt3.WaitScreen;
import attempt3.WelcomeScreen;

import java.io.FileNotFoundException;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class Player extends Client {
    private Socket socket;
    private ObjectOutputStream socketOut;
    private ObjectInputStream socketIn;

    static Stage primaryStage;
    public static Stage stage2;

    // Define all of the screens as Pane variables
    Pane welcomeScreen;
    Pane waitScreen;
    Pane gameScreen;
    Pane instructionScreen;

    public static Scene scene2;

    public Player(String ip, int port) throws Exception {
        socket = new Socket(ip, port);
        socketOut = new ObjectOutputStream(socket.getOutputStream());
        socketIn = new ObjectInputStream(socket.getInputStream());
    }

    
    @Override
    public void start(Stage stage) throws Exception {
        // Create 2 stages, one for the main game, and one for the timer
        primaryStage = stage;

        stage2 = new Stage();
        stage2.setX(1100);
        stage2.setY(150);

        // // Create the second scene for the timer and set its properties
        // Scene scene2 = new Scene(new TimerScreen(this), 200, 200);
        // stage2.setTitle("Pictionary");
        // stage2.setScene(scene2);

        // Define each of the Pane variables to a new screen passing it the mainApp
        welcomeScreen = new WelcomeScreen(this);
        waitScreen = new WaitScreen(this);
        gameScreen = new GameScreen(this);
        instructionScreen = new InstructionScreen(this);


        // Make sure the users cannot resize the windows
        primaryStage.setResizable(false);
        stage2.setResizable(false);

        // Set the primary screen to the WelcomeScreen and determine its properties
        Scene scene = new Scene(welcomeScreen, 1200, 500);
        primaryStage.setTitle("CS BS!");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    // Define a method that sets the root of the scene to welcomeScreen
    public void showWelcomeScreen() {
        Scene scene = primaryStage.getScene();
        // setStageSize(800, 500);
        scene.setRoot(welcomeScreen);

    }

    public void showWaitScreen() {
        Scene scene = primaryStage.getScene();
        scene.setRoot(waitScreen);
    }

    public void showGameScreen() {
        Scene scene = primaryStage.getScene();
        // setStageSize(800, 500);
        scene.setRoot(gameScreen);

    }

    public void showInstructionScreen() {
        Scene scene = primaryStage.getScene();
        // setStageSize(800, 500);
        scene.setRoot(instructionScreen);

    }

    // Define a function to set the stage size
    public static void setStageSize(double width, double height) {
        primaryStage.setWidth(width);
        primaryStage.setHeight(height);
    }

    // start a thread to listen for messages from the server
    private void startListener() {
        new Thread(new ChatClientSocketListener(socketIn)).start();
    }

    private void sendMessage(Message m) throws Exception {
        socketOut.writeObject(m);
        // socketOut.flush();
    }

    private void mainLoop(Scanner in) throws Exception {
        System.out.print("Chat sessions has started - enter a user name: ");
        String name = in.nextLine().trim();

        sendMessage(new MessageCtoS_Join(name));

        String line = in.nextLine().trim();
        //while (!line.toLowerCase().startsWith("/quit")) {
            
        // if (line.toLowerCase().startsWith("/dm_")) {
        //     int idNum = Integer.parseInt(line.substring(4, 5));
        //     String message = line.substring(7);
        //     sendMessage(new MessageCtoS_Private(message, idNum));
        // }
        
        while (GameScreen.playersLeft > 2) {
            try {
                sendMessage(new MessageCtoS_Chat(line));
                
                line = in.nextLine().trim();
            }
            catch (Exception e) {
                System.out.println("Exiting");
            }
        }
        //}
        //sendMessage(new MessageCtoS_Quit());

    }

    private void closeSockets() throws Exception {
        socketIn.close();
        socketOut.close();
        socket.close();
    }

    public static void main(String[] args) throws Exception {
        Scanner userInput = new Scanner(System.in);
        System.out.println("What's the server IP? ");
        String serverip = userInput.nextLine();

        System.out.println("What's the server port? ");
        int port = userInput.nextInt();
        userInput.nextLine();

        Player p = new Player(serverip, port);

        p.startListener();
        p.mainLoop(userInput);

        userInput.close();
        p.closeSockets();

        launch(args);

    }


}

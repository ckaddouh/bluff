import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;
import java.io.FileNotFoundException;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public abstract class Client extends Application {
    protected Socket socket;
    protected ObjectOutputStream socketOut;
    protected ObjectInputStream socketIn;

    static Stage primaryStage;
    public static Stage stage2;

    // Define all of the screens as Pane variables
    Pane welcomeScreen;
    Pane waitScreen;
    Pane gameScreen;
    Pane instructionScreen;

    public static Scene scene2;

    // public Client(String ip, int port) throws Exception {
    //     socket = new Socket(ip, port);
    //     socketOut = new ObjectOutputStream(socket.getOutputStream());
    //     socketIn = new ObjectInputStream(socket.getInputStream());
    // }

    
    @Override
    public abstract void start(Stage stage);

    // Define a method that sets the root of the scene to welcomeScreen
    protected void showWelcomeScreen() {
        Scene scene = primaryStage.getScene();
        // setStageSize(800, 500);
        scene.setRoot(welcomeScreen);

    }

    protected void showWaitScreen() {
        Scene scene = primaryStage.getScene();
        scene.setRoot(waitScreen);
    }

    protected void showGameScreen() {
        Scene scene = primaryStage.getScene();
        // setStageSize(800, 500);
        scene.setRoot(gameScreen);

    }

    protected void showInstructionScreen() {
        Scene scene = primaryStage.getScene();
        // setStageSize(800, 500);
        scene.setRoot(instructionScreen);

    }

    // Define a function to set the stage size
    protected static void setStageSize(double width, double height) {
        primaryStage.setWidth(width);
        primaryStage.setHeight(height);
    }

    // start a thread to listen for messages from the server
    protected abstract void startListener();

    // protected void sendMessage(Message m) throws Exception {
    //     socketOut.writeObject(m);
    //     // socketOut.flush();
    // }

    protected void mainLoop(Scanner in) throws Exception {
        System.out.print("Chat sessions has started - enter a user name: ");
        //String name = in.nextLine().trim();

        // sendMessage(new MessageCtoS_Join(name));

        //String line = in.nextLine().trim();
        //while (!line.toLowerCase().startsWith("/quit")) {
            
        // if (line.toLowerCase().startsWith("/dm_")) {
        //     int idNum = Integer.parseInt(line.substring(4, 5));
        //     String message = line.substring(7);
        //     sendMessage(new MessageCtoS_Private(message, idNum));
        // }
        
        while (GameScreen.playersLeft > 2) {
            try {
               // sendMessage(new MessageCtoS_Chat(line));
                
                //line = in.nextLine().trim();
            }
            catch (Exception e) {
                System.out.println("Exiting");
            }
        }
        //}
        //sendMessage(new MessageCtoS_Quit());

    }

    protected void closeSockets() throws Exception {
        socketIn.close();
        socketOut.close();
        socket.close();
    }


}

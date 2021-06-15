package files;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Client extends Application {
    private Socket socket;
    private ObjectOutputStream socketOut;
    private ObjectInputStream socketIn;

    private Stage stage;
    private WelcomeScreenClient welcomeScreen;
    private InstructionScreenClient instructionScreen;
    private WaitScreenClient waitScreen;
    private GameScreen gameScreen;
    private EndScreen endScreen;
    private static Scanner userInput;
    private static Client cc;

    public Client(String ip, int port) throws Exception {
        System.out.println("client starting");
        socket = new Socket("192.168.56.1", 54323);

        socketOut = new ObjectOutputStream(socket.getOutputStream());
        socketIn = new ObjectInputStream(socket.getInputStream());

    }
    public static void main(String[] args) throws Exception {
        userInput = new Scanner(System.in);
        // System.out.println("What's the server IP? ");
        // String serverip = userInput.nextLine();

        // System.out.println("What's the server port? ");
        // int port = userInput.nextInt();
        // userInput.nextLine();

        // cc = new Client("192.168.56.1", 54323);

        launch(args);
    }

   

    // start a thread to listen for messages from the server
    private void startListener() {
        new Thread(new ClientSocketListener(socketIn)).start();
    }

    private void sendMessage(Message m) throws Exception {
        socketOut.writeObject(m);
//        socketOut.flush();
    }

    private void mainLoop(Scanner in) throws Exception {
        System.out.print("Chat sessions has started - enter a user name: ");
        String name = in.nextLine().trim();

        sendMessage(new MessageCtoS(name));

        String line = in.nextLine().trim();
        while (!line.toLowerCase().startsWith("/quit")) {
            
            // if (line.toLowerCase().startsWith("/list")) {
            //     sendMessage(new MessageCtoS_ListRequest());
            // }
            // else if (line.toLowerCase().startsWith("/dm_")) {
            //     int idNum = Integer.parseInt(line.substring(4, 5));
            //     String message = line.substring(7);
            //     sendMessage(new MessageCtoS_Private(message, idNum));
            //}
            //else {
                sendMessage(new MessageCtoS(line));
            //}
            line = in.nextLine().trim();
        }
        //sendMessage(new MessageCtoS_Quit());

    }

    private void closeSockets() throws Exception {
        socketIn.close();
        socketOut.close();
        socket.close();
    }



    @Override
    public void start(Stage primaryStage) throws Exception {
        System.out.println("Hello");
   
        stage = primaryStage;
        welcomeScreen = new WelcomeScreenClient(this);
        instructionScreen = new InstructionScreenClient(this);
        waitScreen = new WaitScreenClient(this);
        gameScreen = new GameScreen(this);
        endScreen = new EndScreen(this);

        //create a scene and display
        Scene scene = new Scene(gameScreen, 1200, 500);
        stage.setTitle("Client: CS BS!");
        stage.setScene(scene);
        stage.show();


        // new Thread(() -> {
            try {

              
                cc.startListener();
                cc.mainLoop(userInput);

                userInput.close();
                cc.closeSockets();

            } catch (Exception ex) {
                //txtAreaDisplay.appendText(ex.toString() + '\n');
            }
        //}).start();
    }

    

}

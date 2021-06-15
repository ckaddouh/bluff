package files;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Server extends Application {
    public static final int PORT = 54323;
    private static final ArrayList<ClientConnectionData> clientArrayList = new ArrayList<>();
    private static final List<ClientConnectionData> clientList = Collections.synchronizedList(clientArrayList);

    private Stage stage;
    private WelcomeScreen welcomeScreen;
    private InstructionScreen instructionScreen;
    private WaitScreen waitScreen;
    private GameScreen gameScreen;
    private EndScreen endScreen;


    public static void main(String[] args) throws Exception {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {

        stage = primaryStage;
        welcomeScreen = new WelcomeScreen(this);
        instructionScreen = new InstructionScreen(this);
        waitScreen = new WaitScreen(this);
        gameScreen = new GameScreen(this);
        endScreen = new EndScreen(this);

        //create a scene and display
        Scene scene = new Scene(gameScreen, 1200, 500);
        stage.setTitle("Server: CS BS!");
        stage.setScene(scene);
        stage.show();
        new Thread(() -> {
            ExecutorService pool = Executors.newFixedThreadPool(100);

            try (ServerSocket serverSocket = new ServerSocket(PORT)) {
                System.out.println("Chat Server started.");
                System.out.println("Local IP: " + Inet4Address.getLocalHost().getHostAddress());
                System.out.println("Local Port: " + serverSocket.getLocalPort());
    
                while (true) {
                    try {
                        Socket socket = serverSocket.accept();
                        System.out.printf("Connected to %s:%d on local port %d\n", socket.getInetAddress(),
                                socket.getPort(), socket.getLocalPort());
    
                        // This code should really be done in the separate thread
                        ObjectOutputStream socketOut = new ObjectOutputStream(socket.getOutputStream());
                        ObjectInputStream socketIn = new ObjectInputStream(socket.getInputStream());
                        String name = socket.getInetAddress().getHostName();
    
                        ClientConnectionData client = new ClientConnectionData(socket, socketIn, socketOut, name);
                        clientList.add(client);
    
                        System.out.println("added client " + name);
    
                        // handle client business in another thread
                        pool.execute(new ServerSocketListener(client, clientList));
                    } 
                    
                    // prevent exceptions from causing server from exiting.
                    catch (Exception ex) {
                        System.out.println(ex.getMessage());
                    }
    
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
           
        }).start();        
    }
}

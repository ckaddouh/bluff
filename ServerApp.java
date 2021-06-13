

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import attempt3.InstructionScreen;
import attempt3.WaitScreen;
import attempt3.WelcomeScreen;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.io.*;
import java.net.*;
import java.util.*;


public class ServerApp {
    public static final int PORT = 54323;
    private static final ArrayList<ClientConnectionData> clientArrayList = new ArrayList<>();
    private static final List<ClientConnectionData> clientList = Collections.synchronizedList(clientArrayList);
    private static int pNum = 0;

    public static void main(String[] args) throws Exception {
        ExecutorService pool = Executors.newFixedThreadPool(100);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server started");
            System.out.println("Local IP: " + Inet4Address.getLocalHost().getHostAddress());
            System.out.println("Port: " + serverSocket.getLocalPort());
    
            while (true) {
                Socket socket = serverSocket.accept();

                System.out.printf("Connected to %s:%d on local port %d\n", socket.getInetAddress(), socket.getPort(), socket.getLocalPort());

                BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream());

       
                String name = socket.getInetAddress().getHostAddress();
                ClientConnectionData cd = new ClientConnectionData(socket, input, out, name, pNum);

                clientList.add(cd);

                pool.execute(new ClientHandler(cd));
            }
        }
    }

    public void start(Stage primaryStage) throws Exception {
        welcomeScreen = new WelcomeScreen(this);
        instructionScreen = new InstructionScreen(this);
        gameScreen = new GameScreen(this);
        waitScreen = new WaitScreen(this);
        
        this.primaryStage = primaryStage;
        stage2 = new Stage();
        stage2.setX(1100);
        stage2.setY(150);

        primaryStage.setResizable(false);
        stage2.setResizable(false);
        
        // Set the primary screen to the WelcomeScreen and determine its properties
        Scene scene = new Scene(welcomeScreen, 1200, 500);
        primaryStage.setTitle("CS BS!");
        primaryStage.setScene(scene);
        primaryStage.show();  

        //new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(PORT)) {
                System.out.println("Chat Server started.");
                System.out.println("Local IP: " + Inet4Address.getLocalHost().getHostAddress());
                System.out.println("Local Port: " + serverSocket.getLocalPort());


                System.out.println("show");
                while (true) {
                    try {
                        System.out.println("running");
                        Socket socket = serverSocket.accept();
                        System.out.println("accepted:");
                        System.out.printf("Connected to %s:%d on local port %d\n", socket.getInetAddress(),
                                socket.getPort(), socket.getLocalPort());

                        // This code should really be done in the separate thread
                        ObjectOutputStream socketOut = new ObjectOutputStream(socket.getOutputStream());
                        ObjectInputStream socketIn = new ObjectInputStream(socket.getInputStream());
                        String name = socket.getInetAddress().getHostName();

                        ClientConnectionData client = new ClientConnectionData(socket, socketIn, socketOut, name, pNum++);
                        pNum %= 4;
                        clientList.add(client);

                        System.out.println("added client " + name);

                        // handle client business in another thread
                        pool.execute(new ServerSocketListener(client, clientList));
                    } 
                
                // prevent exceptions from causing server from exiting.
                catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }

            }
        }

    class ClientHandler implements Runnable {
        ClientConnectionData cd;

        public ClientHandler(ClientConnectionData cd) {
            this.cd = cd;
        }

        public void broadcast(String msg) {
            try {
                System.out.println("Broadcast -- " + msg);
                for (ClientConnnectionData c: clientList) {
                    c.getOut().println(msg);
                }
            }
            catch (Exception ex) {
                System.out.println("broadcast - caught exception: " + ex);
                ex.printStackTrace();
            }
        }

        public void run() {
            try {
                BufferedReader in = cd.getInput();
                String userName = in.readLine().trim();
                cd.setUserName(userName);
                broadcast(String.format("WELCOME %s", cd.getUserName()));
            
                String incoming = "";

                while (incoming = in.readLine() != null) {
                    if (incoming.startsWith("CHAT")) {
                        String chat = incoming.substring(4).trim();
                            if (chat.length() > 0) {
                                broadcast(String.format("CHAT %s %s", cd.getUserName(), chat));
                            }
                    }
                    else if (incoming.startsWith("QUIT")) {
                        break;
                    }
                }
            }
            catch (Exception e) {
                if (e instanceof SocketException) {
                   System.out.println("Caught socket ex for " + cd.getName()); 
                }
                else {
                    System.out.println("caught exception: " + e);
                    ex.printStackTrace();
                }
            }
            finally {
                clientList.remove(cd);

                System.out.println(cd.getName() + " has left");
                broadcast(String.format("EXIT %s", cd.getUserName()));

                try {
                    cd.getSocket().close();
                }
                catch (Exception ex) {

                }
            }
        }
    }
}


public class ServerApp extends ClientApp {
    public static final int PORT = 54323;
    private static final ArrayList<ClientConnectionData> clientArrayList = new ArrayList<>();
    private static final List<ClientConnectionData> clientList = Collections.synchronizedList(clientArrayList);
    private static int playerNum = 0;
    private static Stage primaryStage;
    private Stage stage2;
    
    private WelcomeScreen welcomeScreen;
    private InstructionScreen instructionScreen;
    private GameScreen gameScreen;
    private WaitScreen waitScreen;
    private static ExecutorService pool;
    

    public static void main(String[] args) throws Exception {
        pool = Executors.newFixedThreadPool(100);


        BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream());
        
        ClientConnectionData cd = new ClientConnectionData(socket, input, out, name);

        Thread client = new Thread(new ClientListener());

        Application.launch(args);

        
        
    }

    public void start(Stage primaryStage) throws Exception {
        welcomeScreen = new WelcomeScreen(this);
        instructionScreen = new InstructionScreen(this);
        gameScreen = new GameScreen(this);
        waitScreen = new WaitScreen(this);
        
        this.primaryStage = primaryStage;
        stage2 = new Stage();
        stage2.setX(1100);
        stage2.setY(150);

        primaryStage.setResizable(false);
        stage2.setResizable(false);
        
        // Set the primary screen to the WelcomeScreen and determine its properties
        Scene scene = new Scene(welcomeScreen, 1200, 500);
        primaryStage.setTitle("CS BS!");
        primaryStage.setScene(scene);
        primaryStage.show();  

        //new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(PORT)) {
                System.out.println("Chat Server started.");
                System.out.println("Local IP: " + Inet4Address.getLocalHost().getHostAddress());
                System.out.println("Local Port: " + serverSocket.getLocalPort());


                System.out.println("show");
                while (true) {
                    try {
                        System.out.println("running");
                        Socket socket = serverSocket.accept();
                        System.out.println("accepted:");
                        System.out.printf("Connected to %s:%d on local port %d\n", socket.getInetAddress(),
                                socket.getPort(), socket.getLocalPort());

                        // This code should really be done in the separate thread
                        ObjectOutputStream socketOut = new ObjectOutputStream(socket.getOutputStream());
                        ObjectInputStream socketIn = new ObjectInputStream(socket.getInputStream());
                        String name = socket.getInetAddress().getHostName();

                        ClientConnectionData client = new ClientConnectionData(socket, socketIn, socketOut, name, playerNum++);
                        playerNum %= 4;
                        clientList.add(client);

                        System.out.println("added client " + name);

                        // handle client business in another thread
                        pool.execute(new ServerSocketListener(client, clientList));
                    } 
                
                // prevent exceptions from causing server from exiting.
                catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }

            }
        }
        //}).start();

    }

      // Define a method that sets the root of the scene to welcomeScreen
      public void showWelcomeScreen(){
        Scene scene = primaryStage.getScene();
        // setStageSize(800, 500);
        scene.setRoot(welcomeScreen);
    }

    public void showGameScreen(){
        Scene scene = primaryStage.getScene();
        // setStageSize(800, 500);
        scene.setRoot(gameScreen);

    }

    public void showInstructionScreen(){
        Scene scene = primaryStage.getScene();
        // setStageSize(800, 500);
        scene.setRoot(instructionScreen);

    }

    public void showWaitScreen(){
        Scene scene = primaryStage.getScene();
        // setStageSize(800, 500);
        scene.setRoot(waitScreen);

    }

    // Define a function to set the stage size
    public static void setStageSize(double width, double height){
        primaryStage.setWidth(width);
        primaryStage.setHeight(height);
    }


    class ClientHandler implements Runnable {
        ClientConnectionData cd;

        public ClientHandler(ClientConnectionData cd) {
            this.cd = cd;
        }

        public void broadcast(String msg) {
            try {
                System.out.println("Broadcast -- " + msg);
                for (ClientConnnectionData c: clientList) {
                    c.getOut().println(msg);
                }
            }
            catch (Exception ex) {
                System.out.println("broadcast - caught exception: " + ex);
                ex.printStackTrace();
            }
        }

        public void run() {
            try {
                BufferedReader in = cd.getInput();
                String userName = in.readLine().trim();
                cd.setUserName(userName);
                broadcast(String.format("WELCOME %s", cd.getUserName()));
            
                String incoming = "";

                while (incoming = in.readLine() != null) {
                    if (incoming.startsWith("CHAT")) {
                        String chat = incoming.substring(4).trim();
                            if (chat.length() > 0) {
                                broadcast(String.format("CHAT %s %s", cd.getUserName(), chat));
                            }
                    }
                    else if (incoming.startsWith("QUIT")) {
                        break;
                    }
                }
            }
            catch (Exception e) {
                if (e instanceof SocketException) {
                   System.out.println("Caught socket ex for " + cd.getName()); 
                }
                else {
                    System.out.println("caught exception: " + e);
                    ex.printStackTrace();
                }
            }
            finally {
                clientList.remove(cd);

                System.out.println(cd.getName() + " has left");
                broadcast(String.format("EXIT %s", cd.getUserName()));

                try {
                    cd.getSocket().close();
                }
                catch (Exception ex) {

                }
            }
        }
    }
}





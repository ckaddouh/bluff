

import java.util.Scanner;

import attempt3.InstructionScreen;
import attempt3.WaitScreenClient;
import attempt3.WelcomeScreen;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.io.*;
import java.util.*;
import java.net.*;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientApp extends Application {
    private static Socket socket;
    private static BufferedReader socketIn;
    private static PrintWriter out;

    private static WelcomeScreen welcomeScreen;
    private static InstructionScreen instructionScreen;
    private static GameScreen gameScreen;
    private static WaitScreenClient waitScreen;

    static Stage primaryStage;
    public static Scene scene;
    private static BorderPane pane;
    private Stage stage2;

    public static void main(String[] args) throws Exception {
        Scanner userInput = new Scanner (System.in);
        System.out.println("Server IP?");
        String ip = userInput.nextLine();

        System.out.println("Port?");
        int port = userInput.nextInt();
        userInput.nextLine();

        socket = new Socket(ip, port);

        socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);

         
       
        

        ServerListener listener = new ServerListener();
        Thread t = new Thread(listener);
        t.start();

        System.out.println("Enter your username.");
        String userName = userInput.nextLine();
        out.println(userName);
        out.flush();

        String line = userInput.nextLine().trim();
        while (!line.equals("/quit")) {
            String msg = String.format("CHAT %s", line);
            out.println(msg);
            out.flush();
            line = userInput.nextLine().trim();
        }

        out.println("QUIT");
        out.flush();
        out.close();
        userInput.close();
        socketIn.close();
        socket.close();

        launch(args);
    }


    static class ServerListener implements Runnable {

        public void run() {
            try {
                String incoming = "";

                while ((incoming = socketIn.readLine()) != null) {
                    System.out.println(incoming);
                }
            }
            catch (Exception ex) {
                System.out.println("Exception caught in listener - " + ex);
            }
            finally {
                System.out.println("Listener exiting");
            }
        }

    }


    @Override
    public void start(Stage arg0) throws Exception {
        pane = new BorderPane();
        gameScreen = new GameScreen(out, scene, pane);
        waitScreen = new WaitScreenClient(out, scene, pane);
        instructionScreen = new InstructionScreen(out, scene, pane);
        welcomeScreen = new WelcomeScreen(out, scene, pane);

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


        try {
            String incoming = "";
            while ((incoming = socketIn.readLine()) != null) {
                System.out.println(incoming);
            }
        }
        catch (Exception ex) {
            System.out.println("Exception caught in listener - " + ex);
        }
        finally {
            System.out.println("Listener exiting");
        }
        
    }



}

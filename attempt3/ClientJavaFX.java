package attempt3;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Socket;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author topman garbuja, 
 *
 * This is the client which passes and get message to and from server and
 * further to multiple clients
 *
 * It also uses TaskReadThread.java file to be used in a new thread in order to
 * get simultaneous input from server
 */
public class ClientJavaFX extends Application {

    //controls

    TextField txtName;
    TextField txtInput;
    ScrollPane scrollPane;
    public TextArea txtAreaDisplay;

    // IO streams
    DataOutputStream output = null;


    private Stage stage;
    private WelcomeScreenClient welcomeScreen;
    private InstructionScreenClient instructionScreen;
    private WaitScreen waitScreen;
    private GameScreen gameScreen;
    private EndScreen endScreen;


    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {

        stage = primaryStage;
        welcomeScreen = new WelcomeScreenClient(this);
        instructionScreen = new InstructionScreenClient(this);
        waitScreen = new WaitScreen(this);
        gameScreen = new GameScreen(this);
        endScreen = new EndScreen(this);
        //pane to hold scroll pane and HBox
        // VBox vBox = new VBox();

        // scrollPane = new ScrollPane();   //pane to display text messages
        // HBox hBox = new HBox(); //pane to hold input textfield and send button

        // txtAreaDisplay = new TextArea();
        // txtAreaDisplay.setEditable(false);
        // scrollPane.setContent(txtAreaDisplay);
        // scrollPane.setFitToHeight(true);
        // scrollPane.setFitToWidth(true);

        // //define textfield and button and add to hBox
        // txtName = new TextField();
        // txtName.setPromptText("Name");
        // txtName.setTooltip(new Tooltip("Write your name. "));
        // txtInput = new TextField();
        // txtInput.setPromptText("New message");
        // txtInput.setTooltip(new Tooltip("Write your message. "));
        // Button btnSend = new Button("Send");
        // btnSend.setOnAction(new ButtonListener());

        // hBox.getChildren().addAll(txtName, txtInput, btnSend);
        // hBox.setHgrow(txtInput, Priority.ALWAYS);  //set textfield to grow as window size grows

        // //set center and bottom of the borderPane with scrollPane and hBox
        // vBox.getChildren().addAll(scrollPane, hBox);
        // vBox.setVgrow(scrollPane, Priority.ALWAYS);

        //create a scene and display
        Scene scene = new Scene(welcomeScreen, 1200, 500);
        stage.setTitle("Client: CS BS!");
        stage.setScene(scene);
        stage.show();

        try {
            // Create a socket to connect to the server
            Socket socket = new Socket(ConnectionUtil.host, ConnectionUtil.port);

            //Connection successful
            //txtAreaDisplay.appendText("Connected. \n");
          
            // Create an output stream to send data to the server
            output = new DataOutputStream(socket.getOutputStream());

            //create a thread in order to read message from server continuously
            TaskReadThread task = new TaskReadThread(socket, this);
            Thread thread = new Thread(task);
            thread.start();

            System.out.println("successfully connected");
        } catch (IOException ex) {
            
            //txtAreaDisplay.appendText(ex.toString() + '\n');
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Handle button action
     */
    private class ButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent e) {
            try {
                //get username and message
                String username = txtName.getText().trim();
                String message = txtInput.getText().trim();

                //if username is empty set it to 'Unknown' 
                if (username.length() == 0) {
                    username = "Unknown";
                }
                //if message is empty, just return : don't send the message
                if (message.length() == 0) {
                    return;
                }

                //send message to server
                output.writeUTF("[" + username + "]: " + message + "");
                output.flush();

                //clear the textfield
                txtInput.clear();
            } catch (IOException ex) {
                System.err.println(ex);
            }

        }
    }


    public void showWelcomeScreen () {
        Scene scene = stage.getScene();
        scene.setRoot(welcomeScreen);
    }
    
    public void showInstructionScreen () {
        Scene scene = stage.getScene();
        scene.setRoot(instructionScreen);
    }

    public void showWaitScreen () {
        Scene scene = stage.getScene();
        scene.setRoot(waitScreen);
    }

    public void showGameScreen () {
        Scene scene = stage.getScene();
        scene.setRoot(gameScreen);
    }

    public void showEndScreen () {
        Scene scene = stage.getScene();
        scene.setRoot(endScreen);
    }

}

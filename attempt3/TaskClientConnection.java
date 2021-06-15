/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attempt3;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Socket;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author topman garbuja,It represents each new connection
 */
public  class TaskClientConnection extends Application implements Runnable {


    Socket socket;
    ServerJavaFX server;
    // Create data input and output streams
    DataInputStream input;
    DataOutputStream output;

    private Stage stage;
    private WelcomeScreenClient welcomeScreen;
    private InstructionScreenClient instructionScreen;
    private WaitScreenClient waitScreen;
    private GameScreen gameScreen;
    private EndScreen endScreen;
    private ClientJavaFX client;



    public TaskClientConnection(Socket socket, ServerJavaFX server) throws FileNotFoundException {
        this.socket = socket;
        this.server = server;

    }


    @Override
    public void run() {

        try {
            // Create data input and output streams
            input = new DataInputStream(
                    socket.getInputStream());
            output = new DataOutputStream(
                    socket.getOutputStream());

            while (true) {
                // Get message from the client
                String message = input.readUTF();

                //send message via server broadcast
                server.broadcast(message);
            
                
                //append message of the Text Area of UI (GUI Thread)
                Platform.runLater(() -> {                    
                    //server.txtAreaDisplay.appendText(message + "\n");
                });
            }
            
            

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }

    }

    //send message back to client
    public void sendMessage(String message) {
          try {
            output.writeUTF(message);
            output.flush();

        } catch (IOException ex) {
            ex.printStackTrace();
        } 
       
    }


    @Override
    public void start(Stage arg0) throws Exception {
        // TODO Auto-generated method stub
        
    }

}

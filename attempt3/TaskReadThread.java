/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attempt3;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import javafx.application.Platform;

/**
 *
 * @author topman garbuja,
 * 
 * It is used to get input from server simultaneously
 */
public class TaskReadThread implements Runnable {
    //private variables
    Socket socket;
    ClientJavaFX client;
    DataInputStream input;

    //constructor
    public TaskReadThread(Socket socket, ClientJavaFX client) {
        this.socket = socket;
        this.client = client;
    }

    @Override 
    public void run() {
        //continuously loop it
        while (true) {
            try {
                //Create data input stream
                input = new DataInputStream(socket.getInputStream());

                //get input from the client
                String message = input.readUTF();

                //append message of the Text Area of UI (GUI Thread)
                Platform.runLater(() -> {
                    //display the message in the textarea
                    client.txtAreaDisplay.appendText(message + "\n");
                });
            } catch (IOException ex) {
                System.out.println("Error reading from server: " + ex.getMessage());
                ex.printStackTrace();
                break;
            }
        }
    }
}

package videoAttempt;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

import Message;

public class Client {
    private Socket socket;
    private ObjectOutputStream socketOut;
    private ObjectInputStream socketIn;

    public Client(String ip, int port) throws Exception {
        socket = new Socket(ip, port);
        socketOut = new ObjectOutputStream(socket.getOutputStream());
        socketIn = new ObjectInputStream(socket.getInputStream());
    }

    // start a thread to listen for messages from the server
    protected void startListener() {
        new Thread(new ChatClientSocketListener(socketIn)).start();
    }

    protected void sendMessage(Message m) throws Exception {
        socketOut.writeObject(m);
//        socketOut.flush();
    }

    protected void mainLoop(Scanner in) throws Exception {
        System.out.print("Chat sessions has started - enter a user name: ");
        String name = in.nextLine().trim();

        //sendMessage(new MessageCtoS_Join(name));

        String line = in.nextLine().trim();
        while (!line.toLowerCase().startsWith("/quit")) {
            
            if (line.toLowerCase().startsWith("/list")) {
                //sendMessage(new MessageCtoS_ListRequest());
            }
            else if (line.toLowerCase().startsWith("/dm_")) {
                int idNum = Integer.parseInt(line.substring(4, 5));
                String message = line.substring(7);
                //sendMessage(new MessageCtoS_Private(message, idNum));
            }
            else {
                //sendMessage(new MessageCtoS_Chat(line));
            }
            line = in.nextLine().trim();
        }
       //sendMessage(new MessageCtoS_Quit());

    }

    protected void closeSockets() throws Exception {
        socketIn.close();
        socketOut.close();
        socket.close();
    }

}

package videoAttempt;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import ClientConnectionData;
import Message;
import MessageStoC;

public class ServerSocketListener  implements Runnable {
    private ClientConnectionData client;
    private List<ClientConnectionData> clientList;

    public ServerSocketListener(ClientConnectionData client, List<ClientConnectionData> clientList) {
        this.client = client;
        this.clientList = clientList;
    }

    // private void processButtonClick(MessageStoC m) {
    //     System.out.println("Chat received from " + client.getUserName() + " - broadcasting");
    //     broadcast(new MessageStoC("button clicked"), client);
    // }

    /**
     * Broadcasts a message to all clients connected to the server.
     */
    public void broadcast(MessageStoC m, ClientConnectionData skipClient) {
        try {
            System.out.println("broadcasting: " + m);
            for (ClientConnectionData c : clientList){
                // if c equals skipClient, then c.
                // or if c hasn't set a userName yet (still joining the server)
                if ((c != skipClient) && (c.getUserName()!= null)){
                    c.getOut().writeObject(m);
                }
            }
        } catch (Exception ex) {
            System.out.println("broadcast caught exception: " + ex);
            ex.printStackTrace();
        }        
    }

    public void broadcastFromServer(Message m) {
        try {
            System.out.println("broadcasting: " + m);
            for (ClientConnectionData c : clientList){
                // or if c hasn't set a userName yet (still joining the server)
                if (c.getUserName()!= null){
                    c.getOut().writeObject(m);
                }
            }
        } catch (Exception ex) {
            System.out.println("broadcast caught exception: " + ex);
            ex.printStackTrace();
        } 
    }

    private void sendMessage(Message msg, ClientConnectionData client) {
        try {
            client.getOut().writeObject(msg);
        } catch (IOException e) {
            System.out.println("Error sending list response.");
            e.printStackTrace();
        }
    }


    @Override
    public void run() {
        try {
            ObjectInputStream in = client.getInput();

            //MessageCtoS_Join joinMessage = (MessageCtoS_Join)in.readObject();
            client.setUserName("username");
            broadcast(new MessageStoC("testing"), client);
            // Example: /dm_0\ hello! will send the user with ID 0 "hello!"
            
            while (true) {
                Message msg = (Message) in.readObject(); // listen for button click
                // if (msg instanceof MessageCtoS_Quit) {
                //     break;
                // }
               
               if (msg instanceof MessageStoC) {
                    //processButtonClick((MessageStoC) msg);
                }
                else {
                    System.out.println("Unhandled message type: " + msg.getClass());
                }
            }
        } catch (Exception ex) {
            if (ex instanceof SocketException) {
                System.out.println("Caught socket ex for " + 
                    client.getName());
            } else {
                System.out.println(ex);
                ex.printStackTrace();
            }
        } finally {
            //Remove client from clientList
            clientList.remove(client); 

            // Notify everyone that the user left.
            //broadcast(new MessageStoC_Exit(client.getUserName()), client);

            try {
                client.getSocket().close();
            } catch (IOException ex) {}
        }
    }
        
}

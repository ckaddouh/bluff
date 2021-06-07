import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class ChatServerSocketListener  implements Runnable {
    private ClientConnectionData client;
    private List<ClientConnectionData> clientList;

    public ChatServerSocketListener(ClientConnectionData client, List<ClientConnectionData> clientList) {
        this.client = client;
        this.clientList = clientList;
    }

    private void processChatMessage(MessageCtoS_Chat m) {
        System.out.println("Chat received from " + client.getUserName() + " - broadcasting");
        broadcast(new MessageStoC_Chat(client.getUserName(), m.msg), client);
    }

    private void processListRequest(MessageCtoS_ListRequest m) {
        ArrayList<String> clients = new ArrayList<>();
        for (ClientConnectionData c : clientList) {
            clients.add(c.getUserName());
        }
        sendMessage((new MessageStoC_ListResponse(clients)), client);
    }

    private void processPrivateMessage(MessageCtoS_Private m) {
        try {
            sendMessage(new MessageStoC_Private(client.getUserName(), m.msg), clientList.get(m.idNum));
        } catch (Exception ex) {
            System.out.println("Client selected invalid user.");
            sendMessage(new MessageStoC("Invalid user."), client);
        }
    }

    /**
     * Broadcasts a message to all clients connected to the server.
     */
    public void broadcast(Message m, ClientConnectionData skipClient) {
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

            MessageCtoS_Join joinMessage = (MessageCtoS_Join)in.readObject();
            client.setUserName(joinMessage.userName);
            broadcast(new MessageStoC_Welcome(joinMessage.userName), client);
            // Example: /dm_0\ hello! will send the user with ID 0 "hello!"
            sendMessage(new MessageStoC("Type /dm_[userID]\\ [message] to send a private dm."), client);
            String str = "Updated id list: ";
            for (int i = 0; i < clientList.size(); i++) {
                str = str + clientList.get(i).getUserName() + ": " + i + ", ";
            }
            broadcastFromServer(new MessageStoC(str));

            while (true) {
                Message msg = (Message) in.readObject();
                if (msg instanceof MessageCtoS_Quit) {
                    break;
                }
                else if (msg instanceof MessageCtoS_Chat) {
                    processChatMessage((MessageCtoS_Chat) msg);
                }
                else if (msg instanceof MessageCtoS_ListRequest) {
                    processListRequest((MessageCtoS_ListRequest) msg);
                }
                else if (msg instanceof MessageCtoS_Private) {
                    processPrivateMessage((MessageCtoS_Private) msg);
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
            broadcast(new MessageStoC_Exit(client.getUserName()), client);

            String str = "Updated id list: ";
            for (int i = 0; i < clientList.size(); i++) {
                str = str + clientList.get(i).getUserName() + ": " + i + ", ";
            }
            broadcastFromServer(new MessageStoC(str));

            try {
                client.getSocket().close();
            } catch (IOException ex) {}
        }
    }
        
}

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChatServer {
    public static final int PORT = 54323;
    private static final ArrayList<ClientConnectionData> clientArrayList = new ArrayList<>();
    private static final List<ClientConnectionData> clientList = Collections.synchronizedList(clientArrayList);

    private static int pNum = 0;

    public static void main(String[] args) throws Exception {
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

                    ClientConnectionData client = new ClientConnectionData(socket, socketIn, socketOut, name, pNum++);
                    pNum %= 4;
                    clientList.add(client);

                    System.out.println("added client " + name);

                    // handle client business in another thread
                    pool.execute(new ChatServerSocketListener(client, clientList));
                } 
                
                // prevent exceptions from causing server from exiting.
                catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }

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

package videoAttempt;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.function.Consumer;

public abstract class NetworkConnection {
    private Consumer<Serializable> onReceiveCallback;
    public static ArrayList<ConnectionThread> clientList = new ArrayList<>();
    
    public NetworkConnection(Consumer<Serializable> onReceiveCallback) {
        this.onReceiveCallback = onReceiveCallback;
        for (int i = 0; i < GameScreenClient.numPlayers; i++) {
            ConnectionThread connThread = new ConnectionThread();
            connThread.setDaemon(true);
            clientList.add(connThread);
        }
    }

    public void startConnection() throws Exception {
        for (ConnectionThread connThread : clientList) {
            connThread.start();
        }
    }

    public void send(Serializable data) throws Exception {
        for (ConnectionThread connThread : clientList) {
            connThread.out.writeObject(data);
        }
    }

    public void closeConnection() throws Exception {
        for (ConnectionThread connThread : clientList) {
            connThread.socket.close();
        }
    }

    protected abstract boolean isServer();
    protected abstract String getIP();
    protected abstract int getPort();

    private class ConnectionThread extends Thread {
        private Socket socket;
        private ObjectOutputStream out;

        public void run() {
            try (ServerSocket server = isServer() ? new ServerSocket(getPort()) : null;
                Socket socket = isServer() ? server.accept() : new Socket(getIP(), getPort());
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {
                
                this.socket = socket;
                this.out = out;
                socket.setTcpNoDelay(true);

                while (true) {
                    Serializable data = (Serializable) in.readObject();
                    onReceiveCallback.accept(data);

                }
                    
            }
            catch (Exception e) {
                onReceiveCallback.accept("Connection closed");
            }
        }
    }
}


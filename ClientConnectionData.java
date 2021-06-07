import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientConnectionData {
    private Socket socket;
    private ObjectInputStream input;
    private ObjectOutputStream out;
    private String name;
    private String userName;
    private int playerNum;

    public ClientConnectionData(Socket socket, ObjectInputStream input, ObjectOutputStream out, String name, int playerNum) {
        this.socket = socket;
        this.input = input;
        this.out = out;
        this.name = name;
        this.playerNum = playerNum;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public ObjectInputStream getInput() {
        return input;
    }

    public void setInput(ObjectInputStream input) {
        this.input = input;
    }

    public ObjectOutputStream getOut() {
        return out;
    }

    public void setOut(ObjectOutputStream out) {
        this.out = out;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getPlayerNum() {
        return playerNum;
    }

    
}

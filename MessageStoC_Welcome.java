public class MessageStoC_Welcome extends Message {
    public String userName;

    public MessageStoC_Welcome(String userName) {
        this.userName = userName;
    }

    public String toString() {
        return "Welcome: " + userName;
    }

}
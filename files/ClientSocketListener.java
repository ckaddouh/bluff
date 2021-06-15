package files;

import java.io.ObjectInputStream;

public class ClientSocketListener implements Runnable {
    private ObjectInputStream socketIn;

    public ClientSocketListener(ObjectInputStream socketIn) {
        this.socketIn = socketIn;
    }

    private void processChatMessage(MessageStoC_Chat m) {
        System.out.println(m.userName + ": " + m.msg);
    }


    private void processWelcomeMessage(MessageStoC_Welcome m) {
        System.out.println(m.userName + " joined the server!");
    }

    private void processExitMessage(MessageStoC_Exit m) {
        System.out.println(m.userName + " left the server!");
    }

    private void processListResponse(MessageStoC_ListResponse m){
        System.out.println(m);
    }

    private void processInstructionResponse(MessageStoC m){
        System.out.println(m);
    }

    private void processPrivateMessage(MessageStoC_Private m) {
        System.out.println(m);
    }

    @Override
    public void run() {
        try {
            while (true) {
                Message msg = (Message) socketIn.readObject();

                if (msg instanceof MessageStoC_Welcome) {
                    processWelcomeMessage((MessageStoC_Welcome) msg);
                }
                else if (msg instanceof MessageStoC_Chat) {
                    processChatMessage((MessageStoC_Chat) msg);
                }
                else if (msg instanceof MessageStoC_Exit) {
                    processExitMessage((MessageStoC_Exit) msg);
                }
                else if (msg instanceof MessageStoC_ListResponse) {
                    processListResponse((MessageStoC_ListResponse) msg);
                }
                else if (msg instanceof MessageStoC) {
                    processInstructionResponse((MessageStoC) msg);
                }
                else if (msg instanceof MessageStoC_Private) {
                    processPrivateMessage((MessageStoC_Private) msg);
                }
                else {
                    System.out.println("Unhandled message type: " + msg.getClass());
                }
            }
        } catch (Exception ex) {
            System.out.println("Exception caught in listener - " + ex);
        } finally{
            System.out.println("Client Listener exiting");
        }
    }
}
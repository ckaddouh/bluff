import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class ServerApp extends Application {

    private boolean isServer = true; // change for either server or client

    private TextArea messages = new TextArea();
    private NetworkConnection connection = createServer();

    private Parent createContent() {
        messages.setPrefHeight(550);
        TextField input = new TextField();
        input.setOnAction(event -> {
            String message = "Server: ";
            message += input.getText();
            input.clear();
            
            messages.appendText(message + "\n");

            try {
                connection.send(message);
            } catch (Exception e) {
                messages.appendText("Failed to send\n");
            }
        });

        VBox root = new VBox(20, messages, input);
        root.setPrefSize(600, 600);
        return root;
    }

    public void init() throws Exception {
        connection.startConnection();
    }

    public void start(Stage primaryStage) throws Exception {
        primaryStage.setScene(new Scene(createContent()));
        primaryStage.show();
    }
    
    public void stop() throws Exception {
        connection.closeConnection();
    }

    private Server createServer() {
        return new Server(55555, data -> {
            Platform.runLater(() -> {
                messages.appendText(data.toString() + "\n");
            });
        });
    }

    private Client createClient() {
        return new Client("127.0.0.1", 55555, data -> {
            Platform.runLater(() -> {
                messages.appendText(data.toString() + "\n");
            });
        });
    }
    public static void main(String[] args) {
        launch(args);
    }
}

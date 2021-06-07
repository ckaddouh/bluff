import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class ClientApp extends Application {

    private boolean isServer = false; // change for either server or client

    private TextArea messages = new TextArea();
    private NetworkConnection connection = isServer ? createServer() : createClient();

    private WelcomeScreen welcomeScreen;
    private InstructionScreen instructionScreen;
    private GameScreenClient gameScreen;

    static Stage primaryStage;
    public static Stage stage2;
    public static Scene scene;


    private Parent createContent() {
        messages.setPrefHeight(550);
        TextField input = new TextField();
        input.setOnAction(event -> {
            String message = isServer ? "Server: " : "Client: ";
            message += "said something"; // input.getText();
            input.clear();
            
            messages.appendText(message + "\n");

            try {
                connection.send(message);
            } catch (Exception e) {
                messages.appendText("Failed to send\n");
            }
        });

        
        return gameScreen;
    }

    public void init() throws Exception {
        connection.startConnection();
    }

    public void start(Stage primaryStage) throws Exception {
        welcomeScreen = new WelcomeScreen(this);
        instructionScreen = new InstructionScreen(this);
        gameScreen = new GameScreenClient(this);
        
        this.primaryStage = primaryStage;
        stage2 = new Stage();
        stage2.setX(1100);
        stage2.setY(150);

        primaryStage.setResizable(false);
        stage2.setResizable(false);
        
        // Set the primary screen to the WelcomeScreen and determine its properties
        Scene scene = new Scene(welcomeScreen, 1200, 500);
        primaryStage.setTitle("CS BS!");
        primaryStage.setScene(scene);
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


    // Define a method that sets the root of the scene to welcomeScreen
    public void showWelcomeScreen(){
        Scene scene = primaryStage.getScene();
        // setStageSize(800, 500);
        scene.setRoot(welcomeScreen);
    }

    public void showGameScreen(){
        Scene scene = primaryStage.getScene();
        // setStageSize(800, 500);
        scene.setRoot(gameScreen);

    }

    public void showInstructionScreen(){
        Scene scene = primaryStage.getScene();
        // setStageSize(800, 500);
        scene.setRoot(instructionScreen);

    }

    // Define a function to set the stage size
    public static void setStageSize(double width, double height){
        primaryStage.setWidth(width);
        primaryStage.setHeight(height);
    }

}

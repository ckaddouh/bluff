package video2;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Player extends Application {
    private int width;
    private int height; 
    private GridPane gridPane;
    private TextField message;
    private Button b1;
    private Button b2;
    private Stage primaryStage;
    private Scene scene;
    private Pane welcomeScreen;

    
    public Player(int w, int h) {
        width = w;
        height = h;
        gridPane = new GridPane();
        message = new TextField();
        b1 = new Button("1");
        b2 = new Button("2");
        
    }

    public void setUpGUI() {
        gridPane.add(message, 0, 1);
        message.setText("blah blah");
        message.setEditable(false);
        gridPane.add(b1, 0, 1);
        gridPane.add(b2, 0, 2);
    }

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        welcomeScreen = new WelcomeScreen(this);
        scene = new Scene(welcomeScreen, 1200, 500);

        primaryStage.setResizable(false);

        // Set the primary screen to the WelcomeScreen and determine its properties
        Scene scene = new Scene(welcomeScreen, 1200, 500);
        primaryStage.setTitle("CS BS!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        Player p = new Player(500, 100);
        p.setUpGUI();
        launch(args);

    }
}

// 6/11
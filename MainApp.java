// The main screen manager that creates and controls other screens

import java.io.FileNotFoundException;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

// Create a class that extends Application
public class MainApp extends Application {

    static Stage primaryStage;
    public static Stage stage2;

    // Define all of the screens as Pane variables
    Pane welcomeScreen;


    public static Scene scene2;

    @Override
    public void start(Stage stage) throws Exception {
        // Create 2 stages, one for the main game, and one for the timer
        primaryStage = stage;

        stage2 = new Stage();
        stage2.setX(1100);
        stage2.setY(150);

        // // Create the second scene for the timer and set its properties
        // Scene scene2 = new Scene(new TimerScreen(this), 200, 200);
        // stage2.setTitle("Pictionary");
        // stage2.setScene(scene2);

        // // Define each of the Pane variables to a new screen passing it the mainApp
        welcomeScreen = new WelcomeScreen(this);
        // instructionScreen = new InstructionScreen(this);
        // drawingScreen = new DrawingScreen(this);
        // settingsScreen = new SettingsScreen(this);
        // resultsScreen = new ResultsScreen(this);
        // finalScreen = new FinalScreen(this);
        // wordScreen = new WordScreen(this);
        // reminder = new Reminder(this);
 
        // Make sure the users cannot resize the windows
        primaryStage.setResizable(false);
        stage2.setResizable(false);
        
        // Set the primary screen to the WelcomeScreen and determine its properties
        Scene scene = new Scene(welcomeScreen, 900, 500);
        primaryStage.setTitle("CS BS!");
        primaryStage.setScene(scene);
        primaryStage.show();  

    }

    // Define a method that sets the root of the scene to welcomeScreen
    public void showWelcomeScreen(){
        Scene scene = primaryStage.getScene();
        // setStageSize(800, 500);
        scene.setRoot(welcomeScreen);

    }

    // Define a function to set the stage size
    public static void setStageSize(double width, double height){
        primaryStage.setWidth(width);
        primaryStage.setHeight(height);
    }

    // Define a method that actually runs the program
    public static void main(String[] args){
        launch(args);
    }
}
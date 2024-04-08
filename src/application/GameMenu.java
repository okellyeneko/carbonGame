package application;


import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GameMenu {
    private Stage stage;
    static TransportGame game;
    private Scene mainScene;
    
    // Constructor receiving the Stage object from the main application class
    public GameMenu(Stage stage) {
        this.stage = stage;
    }

    // Method to initialize and return the game menu scene
    public Scene getGameMenuScene() {
        // Create a VBox as the root layout
        VBox layout = new VBox(10); // Spacing between nodes
        layout.setPrefSize(Main.WIDTH, Main.HEIGHT);
        // Create a "Play Game" button
        Button playGameButton = new Button("Play Game");
        playGameButton.setOnAction(e -> {
            BorderPane gameRoot = new BorderPane(); // Or retrieve an existing one
            Scene gameScene = new Scene(gameRoot, 800, 600); // Or use an existing scene
            TransportGame game = new TransportGame(gameRoot, gameScene);
            game.startGame();
            stage.setScene(gameScene); // Assuming 'stage' is the primary Stage
        });

        // Add the button to the VBox
        layout.getChildren().add(playGameButton);

        // Create and return the scene
        return new Scene(layout); // Adjust the size as needed
    }

    
}

package application;


import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Alert;
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
        // Create a VBox as the root layout with spacing and alignment
        VBox layout = new VBox(10); // Spacing between nodes
        layout.setPrefSize(Main.WIDTH, Main.HEIGHT);
        layout.setStyle("-fx-background-color: #333; -fx-padding: 20; -fx-alignment: center;"); // Example styling

        // Create a "Play Game" button with styling
        Button playGameButton = new Button("Play Game");
        playGameButton.setStyle("-fx-font-size: 16px; -fx-background-color: #64DD17;");
        playGameButton.setOnAction(e -> {
            BorderPane gameRoot = new BorderPane();
            Scene gameScene = new Scene(gameRoot, 800, 600);
            TransportGame game = new TransportGame(gameRoot, gameScene);
            game.startGame();
            stage.setScene(gameScene);
        });

        // Create an "Instructions" button with styling
        Button instructionsButton = new Button("Instructions");
        instructionsButton.setStyle("-fx-font-size: 16px; -fx-background-color: #FFD600;");
        instructionsButton.setOnAction(e -> {
        	Alert instructionsAlert = new Alert(Alert.AlertType.INFORMATION);
            instructionsAlert.setTitle("Game Instructions");
            instructionsAlert.setHeaderText("Welcome to our transport game!");

            String instructionsContent = 
                    "Objective: Collect as many gems as possible by navigating the map using Buses, Bikes, and the Luas. Manage your Stamina, Money, and Carbon Footprint wisely.\n\n" +
                    "- Stamina decreases with physical activity. Cycling is nice until you can't breath, it's game over.\n" +
                    "- Money is needed for transport fares.\n" +
                    "- Keep your Carbon Footprint low. Exceeding the limit ends the game.\n\n" +
                    "Tips: Balance your resources, plan your routes, and explore for hidden gems. The right strategy will lead to more gems!";

            instructionsAlert.setContentText(instructionsContent);
            instructionsAlert.showAndWait();
        });

        // Add the buttons to the VBox
        layout.getChildren().addAll(playGameButton, instructionsButton);

        // Create and return the scene with potential styling
        Scene scene = new Scene(layout);
        scene.getStylesheets().add(getClass().getResource("/application/style.css").toExternalForm());
        //scene.getStylesheets().add("style.css"); // Assuming an external stylesheet (optional)
        return scene;
    }


    
}

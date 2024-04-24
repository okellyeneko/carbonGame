package application;

import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.geometry.Pos;

public class GameMenu {
    private Stage stage;

    public GameMenu(Stage stage) {
        this.stage = stage;
    }

    public Scene getGameMenuScene() {
        // Root pane for the scene that layers everything
        StackPane root = new StackPane();

        // Background setup
        Image backgroundImage = new Image(getClass().getResourceAsStream("map.png"));
        ImageView backgroundImageView = new ImageView(backgroundImage);
        backgroundImageView.setPreserveRatio(false);
        backgroundImageView.setOpacity(0.7);
        backgroundImageView.setFitWidth(Main.WIDTH);
        backgroundImageView.setFitHeight(Main.HEIGHT);
        StackPane imagePane = new StackPane();
        imagePane.setStyle("-fx-background-color: #ADD8E6;"); // Light blue color
        imagePane.getChildren().add(backgroundImageView);

        // Logo setup
        Image logoImage = new Image(getClass().getResourceAsStream("carbon_logo.png"));
        ImageView logoImageView = new ImageView(logoImage);
        logoImageView.setFitWidth(500);
        logoImageView.setPreserveRatio(true);
        StackPane logoPane = new StackPane(logoImageView);
        logoPane.setAlignment(Pos.TOP_CENTER); // Center the logo at the top

        // Buttons setup
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 20; -fx-background-color: transparent;");
        Button playGameButton = new Button("Play Game");
        Button instructionsButton = new Button("Instructions");
        layout.getChildren().addAll(playGameButton, instructionsButton);

        // Layering everything
        root.getChildren().addAll(imagePane, logoPane, layout);

        return new Scene(root, Main.WIDTH, Main.HEIGHT);
    }
}
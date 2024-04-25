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
import javafx.geometry.Insets;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.animation.ScaleTransition;
import javafx.util.Duration;
import javafx.scene.control.DialogPane;  
import javafx.scene.Cursor;

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
        backgroundImageView.setOpacity(1);

        // Bind background size to scene size for full coverage
        root.widthProperty().addListener((obs, oldVal, newVal) -> {
            backgroundImageView.setFitWidth(newVal.doubleValue());
        });
        root.heightProperty().addListener((obs, oldVal, newVal) -> {
            backgroundImageView.setFitHeight(newVal.doubleValue());
        });

        StackPane imagePane = new StackPane();
        imagePane.getChildren().add(backgroundImageView);

        // Logo setup
        Image logoImage = new Image(getClass().getResourceAsStream("logo.png"));
        ImageView logoImageView = new ImageView(logoImage);
        logoImageView.setFitWidth(250);
        logoImageView.setPreserveRatio(true);
        
        DropShadow logoShadow = new DropShadow();
        logoShadow.setRadius(10.0);
        logoShadow.setOffsetX(5.0);
        logoShadow.setOffsetY(5.0);
        logoShadow.setColor(Color.BLACK);
        
        logoImageView.setEffect(logoShadow);
        
        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(1), logoImageView);
        scaleTransition.setFromX(1);
        scaleTransition.setFromY(1);
        scaleTransition.setToX(1.1);  // Increase size by 10%
        scaleTransition.setToY(1.1);  // Increase size by 10%
        scaleTransition.setAutoReverse(true);
        scaleTransition.setCycleCount(ScaleTransition.INDEFINITE);
        StackPane logoPane = new StackPane(logoImageView);
        
        logoPane.setAlignment(Pos.TOP_CENTER);
        logoPane.setPadding(new Insets(50, 0, 0, 0)); // Set padding to lower the logo from the top

        
        // Buttons setup
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 20; -fx-background-color: transparent;");
        Button playGameButton = new Button("Play Game");
        playGameButton.setOnAction(e -> {
            BorderPane gameRoot = new BorderPane();
            Scene gameScene = new Scene(gameRoot);
            TransportGame game = new TransportGame(gameRoot, gameScene);
            game.startGame();
            stage.setScene(gameScene);
        });
        
        Button instructionsButton = new Button("Instructions");
        instructionsButton.setOnAction(e -> {
            Alert instructionsAlert = new Alert(Alert.AlertType.INFORMATION);
            instructionsAlert.setTitle("Game Instructions");
            instructionsAlert.setHeaderText("Welcome to our transport game!");

            String instructionsContent = 
                    "Objective: Collect as many gems as possible by navigating the map using Buses, Bikes, and the Luas. Manage your Stamina, Money, and Carbon Footprint wisely.\n\n" +
                    "- Stamina decreases with physical activity. Cycling is nice until you can't breathe, it's game over.\n" +
                    "- Money is needed for transport fares.\n" +
                    "- Keep your Carbon Footprint low. Exceeding the limit ends the game.\n\n" +
                    "Tips: Balance your resources, plan your routes, and explore for hidden gems. The right strategy will lead to more gems!";

            instructionsAlert.setContentText(instructionsContent);

            // Apply CSS style
            DialogPane dialogPane = instructionsAlert.getDialogPane();
            dialogPane.getStylesheets().add(getClass().getResource("instructions.css").toExternalForm());
            dialogPane.getStyleClass().add("alert");

            instructionsAlert.showAndWait();
        });
        
        playGameButton.getStyleClass().add("button-play");
        instructionsButton.getStyleClass().add("button-instructions");
        VBox.setMargin(playGameButton, new Insets(50, 0, 0, 0)); // Top margin of 20 for 'Play Game' button
       
        
        layout.getChildren().addAll(playGameButton, instructionsButton);
        
        DropShadow shadow = new DropShadow(20, Color.BLACK);
        addHoverEffect(playGameButton, shadow);
        addHoverEffect(instructionsButton, shadow);


        // Layering everything
        root.getChildren().addAll(imagePane, logoPane, layout);
        
        Scene scene = new Scene(root);
        scaleTransition.play();
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        
        stage.fullScreenProperty().addListener((obs, wasFullScreen, isNowFullScreen) -> {
            if (isNowFullScreen) {
                logoImageView.setFitWidth(400);  // Larger size when full screen
            } else {
                logoImageView.setFitWidth(250);  // Original size when not full screen
            }
        });

        return scene;


       
    }

    // Method to add hover effects to buttons
    private void addHoverEffect(Button button, DropShadow shadow) {
        button.setOnMouseEntered(e -> {
            button.setEffect(shadow);
            button.getScene().setCursor(Cursor.HAND); // Change cursor to hand cursor
        });
        button.setOnMouseExited(e -> {
            button.setEffect(null);
            button.getScene().setCursor(Cursor.DEFAULT); // Change cursor to default cursor
        });
    }

}
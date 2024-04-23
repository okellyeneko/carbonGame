package application;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import java.util.Optional;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.DropShadow;
import javafx.scene.text.Font;
import javafx.scene.shape.Circle;
import javafx.scene.layout.BorderPane;
import java.util.HashMap;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DialogPane;
import javafx.scene.text.FontWeight;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Font;








public class TransportGame {
    
    private BorderPane  root; 
    private Player player;
    private MapGraph mapGrap;
    private int currentLevel = 1;
    private int currentRound = 1;
    private int gemsCollected = 0;
    private int gemsToCollect = 4; // Initial value for level 1
    private int currentLocation = 1; 
    HighScoreManager highScoreManager = new HighScoreManager();
    private int  highScore;
    private Map<Integer, Point> pointsMap;
    private List<Integer> availableGems;
    double scaleFactor = 1; // You can adjust this factor as needed
    double scaleX = 100.0 * scaleFactor;
    double scaleY = 100.0 * scaleFactor;
    double offsetX = 0.0 * scaleFactor; 
    double offsetY = 0.0 * scaleFactor;
    double playerOffsetX = -20.0; 
    double playerOffsetY = -20.0;
    private Scene gameScene;
    private Pane mainGameArea;
    private VBox leftPanel;
    private VBox routeOptions;
    private VBox budgetsArea;
    private ProgressBar carbonProgress;
    private double progressCarbon;
    private ProgressBar timeProgress;
    private double progressTime;
    private ProgressBar costProgress;
    private double progressCost;
    private Label carbonBudgetLabel;
    private Label timeBudgetLabel;
    private Label costBudgetLabel;
    private Label scoreLabel;
    private Label budgetsHeading;
    private Label routeHeading;
    private HBox carbonHbox;
    private HBox timeHbox;
    private HBox costHbox;
    
    
    
    public TransportGame(BorderPane root, Scene gameScene) {
        this.root = root;
        this.gameScene = gameScene;
        initializeGame();
    }
    
    private void initializeGame() {

        leftPanel = new VBox(10);
        routeOptions = new VBox(5);
        budgetsArea = new VBox(5);
        this.root.setLeft(leftPanel);        
        budgetsHeading = new Label("Budget Overview");
        routeHeading = new Label("Route Details");
        carbonBudgetLabel = new Label("Carbon Budget: ");
        timeBudgetLabel = new Label("Time Budget: ");
        costBudgetLabel = new Label("Cost Budget: ");
        scoreLabel = new Label("High Score:" + System.lineSeparator() + "Current Score");  
        
        
        carbonProgress = new ProgressBar();
        carbonProgress.setStyle("-fx-accent: green;");
        carbonProgress.setProgress(1.0);
        timeProgress = new ProgressBar();
        timeProgress.setStyle("-fx-accent: green;");
        timeProgress.setProgress(1.0);
        costProgress = new ProgressBar();
        costProgress.setStyle("-fx-accent: green;");
        costProgress.setProgress(1.0);
        carbonBudgetLabel = new Label("Carbon Budget: ");
        timeBudgetLabel = new Label("Time Budget:   ");
        costBudgetLabel = new Label("Cost Budget:   ");

        carbonHbox = new HBox();
        timeHbox = new HBox();
        costHbox = new HBox();
        
        carbonHbox.setSpacing(10);
        timeHbox.setSpacing(10);
        costHbox.setSpacing(10);
        
        carbonHbox.getChildren().addAll(carbonBudgetLabel, carbonProgress);
        timeHbox.getChildren().addAll(timeBudgetLabel, timeProgress);
        costHbox.getChildren().addAll(costBudgetLabel, costProgress);
        
        budgetsArea.getChildren().add(budgetsHeading);
        budgetsArea.getChildren().addAll(carbonBudgetLabel, carbonHbox, timeBudgetLabel, timeHbox, costBudgetLabel, costHbox);
        //leftPanel.getChildren().add(highScoreLabel);
        leftPanel.getChildren().add(scoreLabel);        
        leftPanel.getChildren().add(budgetsArea);
        leftPanel.getChildren().add(routeOptions);
        routeOptions.getChildren().add(routeHeading);
        mainGameArea = new Pane();
        root.setCenter(mainGameArea);        
        highScore = highScoreManager.readHighScore();
        // Styling in CSS
        leftPanel.getStyleClass().add("left-panel"); 
        scoreLabel.getStyleClass().add("score-label");
        budgetsHeading.getStyleClass().add("heading-label");
        routeHeading.getStyleClass().add("route-label");
        routeOptions.getStyleClass().add("route-options");       
        budgetsArea.getStyleClass().add("budgets-area");
        carbonBudgetLabel.getStyleClass().add("label-budget");
        timeBudgetLabel.getStyleClass().add("label-budget");
        costBudgetLabel.getStyleClass().add("label-budget");
        gameScene.getStylesheets().add(getClass().getResource("/application/style.css").toExternalForm());
        
        
    }
  

    private void initializePoints() {
    	
    	//Volcano island
    	
        pointsMap.put(1, new Point(1, "Inferno Heart", 1.775, 3.05));
        pointsMap.put(2, new Point(2, "Emberfall Coast", 1.775, 1.2));
        pointsMap.put(3, new Point(3, "Lava's Edge", 3, 3.05));
        pointsMap.put(4, new Point(4, "Pyroclast Tombs", 2.5, 2.2));
        pointsMap.put(5, new Point(5, "Nidhogg's Nest", 2, 3.82));
        
        //Lighthouse - Forgotten Shores
        pointsMap.put(6, new Point(6, "Sentinel Point", 5.4, 4.55));
        pointsMap.put(7, new Point(7, "Forgotten Shores", 8.43, 4.45));
        
        //Snowy mountains
        pointsMap.put(8, new Point(8, "Sailor's Sanctuary", 6.35, 2.77));
        pointsMap.put(9, new Point(9, "Frostbound Slopes", 7.5, 3.1));
        pointsMap.put(10, new Point(10, "Frozen Shores", 8.32, 2.5));
        pointsMap.put(11, new Point(11, "Alpine Crossroads", 8.2, 0.8));
        pointsMap.put(12, new Point(12, "Pinecrest forests", 6.5, 0.37));
        pointsMap.put(13, new Point(13, "Snowglimmer Span", 8.92, -0.65));
        pointsMap.put(14, new Point(14, "Flimmer Span", 7.95, -0.35));
        
        
        pointsMap.put(15, new Point(15, "Whispering Woods", 7.3, -0.8));
        pointsMap.put(16, new Point(16, "Evergreen Cross", 6.53, -1.17));
        pointsMap.put(17, new Point(17, "Hares' Haven", 6.95, -1.55));
        pointsMap.put(18, new Point(18, "Greenridge Pastures", 8.45, -2.82));
        pointsMap.put(19, new Point(19, "Homestead Horizon", 8.95, -1.85));
        pointsMap.put(20, new Point(20, "Rivercross Glaciergate", 9.15, -1.37));
        pointsMap.put(21, new Point(21, "Snowdrift Domain", 8.27, 1.71));
        
        pointsMap.put(22, new Point(22, "Silo Crossroads", 6.9, -3.87));
        pointsMap.put(23, new Point(23, "Forgefront Curve", 5.87, -3.87));
        pointsMap.put(24, new Point(24, "Gearwork Grove", 5.87, -3.18));
        pointsMap.put(25, new Point(25, "Warehouse Wharf", 4.05, -2.75));
        pointsMap.put(26, new Point(26, "Bolt & Barrel Borough", 3.1, -3.63));
        
        
        pointsMap.put(27, new Point(27, "Assembly fields", 0.45, -3.63));
        pointsMap.put(28, new Point(28, "Forgefield Commons", 1.9, -2.75));
        pointsMap.put(29, new Point(29, "Meadowmere Fortress", 1.1, -1.86));
        pointsMap.put(30, new Point(30, "Baron's Expanse", 2.5, -1.7));
        pointsMap.put(31, new Point(31, "Crownlands Acres", 1.34, -0.7));
        
        
        pointsMap.put(32, new Point(32, "Cobblestone Capital", 3.5, -1.25));
        pointsMap.put(33, new Point(33, "Harborsky Port", 2.6, -0.6));
        pointsMap.put(34, new Point(34, "Cabana Cove", 4.52, 0.1));
        pointsMap.put(35, new Point(35, "Mainstreet Commons", 5.35, -1.7));
        pointsMap.put(36, new Point(36, "Townhall Terrace", 4.5, -1.5));
        
        
        
    }
    
    private void initializeMapGraph() {
    	
    	//Inferno Heart vertex 
    	mapGrap.addLink(new Link(1, 2, Transport.CYCLE, 5, 2, 10));
       	mapGrap.addLink(new Link(1, 3, Transport.CYCLE, 5, 2, 10));
       	mapGrap.addLink(new Link(1, 4, Transport.CYCLE, 5, 2, 10));
       	mapGrap.addLink(new Link(1, 5, Transport.CYCLE, 5, 2, 10));
       	
    	//Emberfall Coast
    	mapGrap.addLink(new Link(2, 1, Transport.CYCLE, 5, 2, 10));
       	mapGrap.addLink(new Link(2, 4, Transport.CYCLE, 5, 2, 10));
       	mapGrap.addLink(new Link(2, 31, Transport.BOAT, 5, 2, 10));
       	mapGrap.addLink(new Link(2, 33, Transport.BOAT, 5, 2, 10));
       	
       	mapGrap.addLink(new Link(2, 31, Transport.AIRPLANE, 5, 2, 10));
       	mapGrap.addLink(new Link(2, 33, Transport.AIRPLANE, 5, 2, 10));
       	
       	//Lava's Edge"
    	mapGrap.addLink(new Link(3, 1, Transport.CYCLE, 5, 2, 10));
      	mapGrap.addLink(new Link(3, 4, Transport.CYCLE, 5, 2, 10));
      	mapGrap.addLink(new Link(3, 8, Transport.BOAT, 5, 2, 10));
      	mapGrap.addLink(new Link(3, 12, Transport.BOAT, 5, 2, 10));
      	mapGrap.addLink(new Link(3, 34, Transport.BOAT, 5, 2, 10));
      	
      	//Pyroclast Tombs
    	mapGrap.addLink(new Link(4, 2, Transport.CYCLE, 5, 2, 10));
      	mapGrap.addLink(new Link(4, 3, Transport.CYCLE, 5, 2, 10));
      	
      	
      	//Nidhogg's Nest
       	mapGrap.addLink(new Link(5, 1, Transport.CYCLE, 5, 2, 10));
       	mapGrap.addLink(new Link(5, 6, Transport.BOAT, 5, 2, 10));
       	
       	//Sentinel Point
       	mapGrap.addLink(new Link(6, 5, Transport.BOAT, 5, 2, 10));
       	mapGrap.addLink(new Link(6, 7, Transport.BOAT, 5, 2, 10));
       	
       	//Forgotten Shores
       	mapGrap.addLink(new Link(7, 6, Transport.BOAT, 5, 2, 10));
       	mapGrap.addLink(new Link(7, 10, Transport.BOAT, 5, 2, 10));
       	
       	//Sailor's Sanctuary
       	mapGrap.addLink(new Link(8, 3, Transport.BOAT, 5, 2, 10));
       	mapGrap.addLink(new Link(8, 9, Transport.CYCLE, 5, 2, 10));
       	mapGrap.addLink(new Link(8, 11, Transport.CYCLE, 5, 2, 10));
       	
       	//Frostbound Slopes
       	mapGrap.addLink(new Link(9, 8, Transport.CYCLE, 5, 2, 10));
       	mapGrap.addLink(new Link(9, 10, Transport.CYCLE, 5, 2, 10));
       	
       	//Frozen Shores
    	mapGrap.addLink(new Link(10, 7, Transport.BOAT, 5, 2, 10));
    	mapGrap.addLink(new Link(10, 9, Transport.CYCLE, 5, 2, 10));
    	mapGrap.addLink(new Link(10, 21, Transport.CYCLE, 5, 2, 10));
       	
    	//Alpine Crossroads
    	mapGrap.addLink(new Link(11, 8, Transport.CYCLE, 5, 2, 10));
    	mapGrap.addLink(new Link(11, 21, Transport.CYCLE, 5, 2, 10));
    	mapGrap.addLink(new Link(11, 12, Transport.CYCLE, 5, 2, 10));
    	mapGrap.addLink(new Link(11, 14, Transport.CYCLE, 5, 2, 10));
       	
    	//Pinecrest forests
    	mapGrap.addLink(new Link(12, 3, Transport.BOAT, 5, 2, 10));
    	mapGrap.addLink(new Link(12, 11, Transport.CYCLE, 5, 2, 10));
    	
    	//Snowglimmer Span
    	mapGrap.addLink(new Link(13, 14, Transport.CYCLE, 5, 2, 10));
    	mapGrap.addLink(new Link(13, 21, Transport.CYCLE, 5, 2, 10));
      	mapGrap.addLink(new Link(13, 20, Transport.CYCLE, 5, 2, 10));
    	
    	//Snowdrift Domain
    	mapGrap.addLink(new Link(21, 10, Transport.CYCLE, 5, 2, 10));
    	mapGrap.addLink(new Link(21, 11, Transport.CYCLE, 5, 2, 10));
     	mapGrap.addLink(new Link(21, 13, Transport.CYCLE, 5, 2, 10));
    	
    	//Flimmer Span
    	mapGrap.addLink(new Link(14, 11, Transport.CYCLE, 5, 2, 10));
    	mapGrap.addLink(new Link(14, 13, Transport.CYCLE, 5, 2, 10));
    	mapGrap.addLink(new Link(14, 15, Transport.CYCLE, 5, 2, 10));
    	
    	//Whispering Woods
    	mapGrap.addLink(new Link(15, 14, Transport.CYCLE, 5, 2, 10));
    	mapGrap.addLink(new Link(15, 16, Transport.CYCLE, 5, 2, 10));
    
       	//Evergreen Cross
    	mapGrap.addLink(new Link(16, 15, Transport.CYCLE, 5, 2, 10));
    	mapGrap.addLink(new Link(16, 17, Transport.CYCLE, 5, 2, 10));
    	mapGrap.addLink(new Link(16, 35, Transport.CYCLE, 5, 2, 10));
    	
       	//Hares' Haven
    	mapGrap.addLink(new Link(17, 16, Transport.CYCLE, 5, 2, 10));
    	mapGrap.addLink(new Link(17, 18, Transport.CYCLE, 5, 2, 10));
    	mapGrap.addLink(new Link(17, 24, Transport.CYCLE, 5, 2, 10));
    	
       	//Greenridge Pastures
    	mapGrap.addLink(new Link(18, 17, Transport.CYCLE, 5, 2, 10));
    	mapGrap.addLink(new Link(18, 19, Transport.CYCLE, 5, 2, 10));
    	mapGrap.addLink(new Link(18, 22, Transport.CYCLE, 5, 2, 10));
    	
       	//Homestead Horizon
    	mapGrap.addLink(new Link(19, 18, Transport.CYCLE, 5, 2, 10));
    	mapGrap.addLink(new Link(19, 20, Transport.BOAT, 5, 2, 10));
    	
       	//Rivercross Glaciergate
    	mapGrap.addLink(new Link(20, 19, Transport.BOAT, 5, 2, 10));
    	mapGrap.addLink(new Link(20, 21, Transport.CYCLE, 5, 2, 10));
    	
      	//Silo Crossroads
    	mapGrap.addLink(new Link(22, 18, Transport.CYCLE, 5, 2, 10));
    	mapGrap.addLink(new Link(22, 23, Transport.CYCLE, 5, 2, 10));
    	
      	//Forgefront Curve
    	mapGrap.addLink(new Link(23, 22, Transport.CYCLE, 5, 2, 10));
    	mapGrap.addLink(new Link(23, 24, Transport.CYCLE, 5, 2, 10));
    	
    	//Gearwork Grove
    	mapGrap.addLink(new Link(24, 23, Transport.CYCLE, 5, 2, 10));
    	mapGrap.addLink(new Link(24, 17, Transport.CYCLE, 5, 2, 10));
    	mapGrap.addLink(new Link(24, 25, Transport.CYCLE, 5, 2, 10));
    	
    	//Warehouse Wharf
    	mapGrap.addLink(new Link(25, 24, Transport.CYCLE, 5, 2, 10));
    	mapGrap.addLink(new Link(25, 26, Transport.CYCLE, 5, 2, 10));
     	mapGrap.addLink(new Link(25, 28, Transport.CYCLE, 5, 2, 10));
     	mapGrap.addLink(new Link(25, 36, Transport.CYCLE, 5, 2, 10));
    	
    	//Bolt & Barrel Borough
    	mapGrap.addLink(new Link(26, 25, Transport.CYCLE, 5, 2, 10));
    	mapGrap.addLink(new Link(26, 27, Transport.CYCLE, 5, 2, 10));
    	
    	//Assembly fields
    	mapGrap.addLink(new Link(27, 26, Transport.CYCLE, 5, 2, 10));
    	mapGrap.addLink(new Link(27, 27, Transport.CYCLE, 5, 2, 10));
    	mapGrap.addLink(new Link(27, 29, Transport.CYCLE, 5, 2, 10));
    	
      	//Forgefield Commons
    	mapGrap.addLink(new Link(28, 25, Transport.CYCLE, 5, 2, 10));
    	mapGrap.addLink(new Link(28, 29, Transport.CYCLE, 5, 2, 10));
    	
      	//Meadowmere Fortress
    	mapGrap.addLink(new Link(29, 27, Transport.CYCLE, 5, 2, 10));
    	mapGrap.addLink(new Link(29, 28, Transport.CYCLE, 5, 2, 10));
    	mapGrap.addLink(new Link(29, 30, Transport.CYCLE, 5, 2, 10));
    	mapGrap.addLink(new Link(29, 31, Transport.CYCLE, 5, 2, 10));
    	
      	//Baron's Expanse
    	mapGrap.addLink(new Link(30, 29, Transport.CYCLE, 5, 2, 10));
    	mapGrap.addLink(new Link(30, 32, Transport.CYCLE, 5, 2, 10));
    	
      	//Crownlands Acres
    	mapGrap.addLink(new Link(31, 29, Transport.CYCLE, 5, 2, 10));
    	mapGrap.addLink(new Link(31, 2, Transport.BOAT, 5, 2, 10));
    	
      	//Cobblestone Capital
    	mapGrap.addLink(new Link(32, 33, Transport.CYCLE, 5, 2, 10));
    	mapGrap.addLink(new Link(32, 30, Transport.CYCLE, 5, 2, 10));
     	mapGrap.addLink(new Link(32, 34, Transport.CYCLE, 5, 2, 10));
     	mapGrap.addLink(new Link(32, 36, Transport.CYCLE, 5, 2, 10));
     	
      	//Harborsky Port
    	mapGrap.addLink(new Link(33, 2, Transport.BOAT, 5, 2, 10));
    	mapGrap.addLink(new Link(33, 32, Transport.CYCLE, 5, 2, 10));
    	
      	//Cabana Cove
    	mapGrap.addLink(new Link(34, 32, Transport.CYCLE, 5, 2, 10));
    	mapGrap.addLink(new Link(34, 3, Transport.BOAT, 5, 2, 10));
    	
      	//Mainstreet Commons
    	mapGrap.addLink(new Link(35, 16, Transport.CYCLE, 5, 2, 10));
    	mapGrap.addLink(new Link(35, 36, Transport.CYCLE, 5, 2, 10));
    	
      	//Townhall Terrace
    	mapGrap.addLink(new Link(36, 35, Transport.CYCLE, 5, 2, 10));
    	mapGrap.addLink(new Link(36, 32, Transport.CYCLE, 5, 2, 10));
    	mapGrap.addLink(new Link(36, 25, Transport.CYCLE, 5, 2, 10));
    }


    public void startGame() {
        currentLevel = 1;
        currentRound = 1;
        gemsCollected = 0;
        gemsToCollect = currentLevel * 4;
        player = new Player(1);
        pointsMap = new HashMap<>();
        mapGrap = new MapGraph();
        initializeMapGraph();
        initializePoints();
        
        // Clear previous game settings if any
        mainGameArea.getChildren().clear();
        
        startRound();
    }


    private void startRound() {
        if (currentRound <= 4) { // Check if the current level still has rounds to play
        	mainGameArea.getChildren().clear(); // Clear the GUI for the new round
            availableGems = placeGemsForRound(); // Place new gems for the round
            displayGems(); // Display the gems for the player to collect
        } else {
            // All rounds for the current level are complete, proceed to the next level
            currentLevel++;
            currentRound = 1; // Reset the round count for the new level
            gemsCollected = 0; // Reset gems collected for the new level
            gemsToCollect = currentLevel * 4; // Recalculate gems to collect for the new level, if applicable
            startRound(); // Start the first round of the new level
        }
    }
    
    private List<Integer> placeGemsForRound() {
        List<Integer> gemLocations = new ArrayList<>(); // To hold the locations of the gems for this round
        Random rand = new Random();

        for (int i = 0; i < currentLevel; i++) { // Number of gems based on current level
            int randomPointId = 1 + rand.nextInt(pointsMap.size());
            if (!gemLocations.contains(randomPointId)) {
                gemLocations.add(randomPointId);
            } else {
                i--; // Ensuring unique gem placements
            }
        }
        return gemLocations; // Return the list of gem locations
    }
    
    
    private void displayGems() {
        mainGameArea.getChildren().clear(); // Clear existing content
        updatePlayerStatus();
        // Create an AnchorPane to hold the map
        AnchorPane anchorPane = new AnchorPane();
        mainGameArea.getChildren().add(anchorPane); // Add anchorPane to the main game area

        // Load the map image
        Image mapImage = new Image(getClass().getResourceAsStream("map.png"));
        ImageView mapView = new ImageView(mapImage);
        anchorPane.getChildren().add(mapView); // Add the map to the container

        // Increase the size of the map
        double scaleFactor2 = 0.363;
        mapView.setFitWidth(mapImage.getWidth() * scaleFactor2);
        mapView.setFitHeight(mapImage.getHeight() * scaleFactor2);

        // Display station names and redraw circles for stations
        for (Point point : pointsMap.values()) {
            // Display station name label with background, modified text, and shadow effect
            Label stationLabel = new Label(point.getName());
            stationLabel.setLayoutX(point.getLongitude() * scaleX);
            stationLabel.setLayoutY(point.getLatitude() * scaleY);
            stationLabel.setStyle("-fx-background-color: white; -fx-text-fill: black; -fx-padding: 1.5;");
            stationLabel.setFont(new Font("Arial", 12)); // Set font size and family
            stationLabel.setWrapText(true); // Allow text wrapping
            stationLabel.setMaxWidth(80); // Set a max width for text wrapping and alignment

            // Create and apply the DropShadow effect
            DropShadow dropShadow = new DropShadow();
            dropShadow.setRadius(3.0);
            dropShadow.setOffsetX(2.0);
            dropShadow.setOffsetY(2.0);
            dropShadow.setColor(Color.color(0.2, 0.2, 0.2)); // Slightly darker shadow

            stationLabel.setEffect(dropShadow); // Apply the shadow effect to the label

            anchorPane.getChildren().add(stationLabel);

            // Redraw circle for station
            Circle stationCircle = new Circle(point.getLongitude() * scaleX, point.getLatitude() * scaleY, 5, Color.BLUE);
            anchorPane.getChildren().add(stationCircle);
        }

        // Place player sprite on the map
        Image playerSprite = new Image(getClass().getResourceAsStream("player.png"));
        ImageView playerImageView = new ImageView(playerSprite);
        double playerX = pointsMap.get(player.getLocation()).getLongitude() * scaleX + playerOffsetX; // Example scaling
        double playerY = pointsMap.get(player.getLocation()).getLatitude() * scaleY + playerOffsetY; // Example scaling

        // Assuming the player's sprite image is too big, let's scale it down
        playerImageView.setFitWidth(40); // Set width to 20px, adjust as necessary
        playerImageView.setFitHeight(40); // Set height to 20px, adjust as necessary
        playerImageView.setX(playerX - 10); // Center the player image
        playerImageView.setY(playerY - 10);

        anchorPane.getChildren().add(playerImageView);

        // Display gem sprites instead of buttons
        for (Integer gemLocation : availableGems) {
            Image gemSprite = new Image(getClass().getResourceAsStream("gem.png"));
            ImageView gemImageView = new ImageView(gemSprite);

            // Set the size of the gem sprite
            double gemSize = 25; // Example size, adjust as necessary
            gemImageView.setFitWidth(gemSize); // Set width to 30px, adjust as necessary
            gemImageView.setFitHeight(gemSize); // Set height to 30px, adjust as necessary

            // Calculate the position so the center of the gem is at the location point
            double gemX = pointsMap.get(gemLocation).getLongitude() * scaleX + offsetX - gemSize / 2;
            double gemY = pointsMap.get(gemLocation).getLatitude() * scaleY + offsetY - gemSize / 2;
            gemImageView.setX(gemX);
            gemImageView.setY(gemY);

            // Add click event to gem sprite to invoke the link options display
            gemImageView.setOnMouseClicked(e -> {
                // Trigger the display of link options for the selected gem location
                displayLinkOptions(player.getLocation(), new Route(), gemLocation);
            });

            anchorPane.getChildren().add(gemImageView);
        }
    }



    private void displayLinkOptions(int point, Route route, int gemLocation) {
    	mainGameArea.getChildren().removeIf(node -> "iconTag".equals(node.getUserData()));
    	mainGameArea.getChildren().removeIf(node -> node instanceof Line);
    	
        for (Link link : mapGrap.getAllLinks()) {
        	Line line = drawLine(link);
        	
            if(point == gemLocation) {
            	if(route.containsLink(link)) {
            		line.setStrokeWidth(8);
            	}
            	
            } else if (link.getStartPoint() == point) {

                line.setStrokeWidth(8);
                line.setOpacity(1);
                
                ImageView  imageView = placeIcon(link, route,  gemLocation);
	       	     
                mainGameArea.getChildren().add(imageView);
                
            }
            
            if(link.getEndPoint() != point) {
            	mainGameArea.getChildren().add(line);
            }
            
        }
        
        //Add Collect Gem Button
        if(point == gemLocation) {
        	
            Button collectGemButton = new Button("Collect Gem");
            collectGemButton.setOnAction(r -> collectGem(gemLocation, route));

            routeOptions.getChildren().add(collectGemButton);
        }
    }
    
    private ImageView placeIcon(Link link, Route currentRoute, int gemLocation) {
    	
        Point startPoint = pointsMap.get(link.getStartPoint());
        Point endPoint = pointsMap.get(link.getEndPoint());
        
        boolean startPointIsLower = link.getStartPoint() < link.getEndPoint();
        double x1 = startPointIsLower ? startPoint.getLongitude() : endPoint.getLongitude();
        double y1 = startPointIsLower ? startPoint.getLatitude() : endPoint.getLatitude();
        double x2 = startPointIsLower ? endPoint.getLongitude() : startPoint.getLongitude();
        double y2 = startPointIsLower ? endPoint.getLatitude() : startPoint.getLatitude();
        
        double midX = (x1 + x2) / 2;
        double midY = (y1 + y2) / 2;
        
        double dx = x2 - x1;
        double dy = y2 - y1;
        double length = Math.sqrt(dx * dx + dy * dy);
        
        Transport transportType = link.getTransport(); // Assuming link is an object that knows the transport type

	     // Determine the image and offset based on the transport type
	     Image image;
	     double offsetX, offsetY;// Assuming dx and dy are defined as the difference in x and y coordinates
	
	     switch (transportType) {
	         case BUS:
	             image = new Image(getClass().getResourceAsStream("bus.png"));
	             offsetX = (dy / length) * 20; 
	             offsetY = (-dx / length) * 20;
	             break;
	         case CYCLE:
	             image = new Image(getClass().getResourceAsStream("bike.png")); 
	             offsetX = (dy / length) * 10; 
	             offsetY = (-dx / length) * 10;
	             break;
	         case AIRPLANE:
	             image = new Image(getClass().getResourceAsStream("plane.png")); 
	             offsetX = (dy / length) * 15; 
	             offsetY = (-dx / length) * 15;
	             break;
	         case BOAT:
	             image = new Image(getClass().getResourceAsStream("boat.png")); 
	             offsetX = (dy / length) * -15;
	             offsetY = (-dx / length) * -15;
	             break;
	         default:
	             // Default case
	             image = new Image(getClass().getResourceAsStream("bike.png")); 
	             offsetX = offsetY = 0; // No offset
	             break;
	     }

	     // Create the ImageView for the transport icon as before
	     ImageView imageView = new ImageView(image);
	     imageView.setFitHeight(30); // Adjust as necessary
	     imageView.setFitWidth(30);
	     imageView.setX((midX * scaleX - imageView.getFitWidth() / 2) + offsetX);
	     imageView.setY((midY * scaleY - imageView.getFitHeight() / 2) + offsetY);
	     
	     // Event handler for imageView clicks
	     imageView.setOnMouseClicked(e -> {
	         // Add the link to the current route instead of creating a new one
	    	 
	         currentRoute.addLink(link);
	         addRouteDetails(currentRoute); // Update route details with the current route
	         displayLinkOptions(link.getEndPoint(), currentRoute, gemLocation); // Use the updated route for further actions
	         showClearButton(currentRoute, gemLocation);
	         
            String soundFileName = TransportSoundMapper.getSoundFileNameForTransport(link.getTransport());
            if (soundFileName != null) {
                SoundEffectsPlayer.playSound(soundFileName);
            }
	         
	     });
	     
	     String tooltipText = String.format("To: %s\nTransport: %s\nTime: %d\nCost: $%d\nCarbon: %d",
                 pointsMap.get(link.getEndPoint()).getName(), 
                 link.getTransport().toString(),
                 link.getTime(),
                 link.getCost(),
                 link.getCarbonFootprint());
		Tooltip tooltip = new Tooltip(tooltipText);
		Tooltip.install(imageView, tooltip);
		tooltip.setShowDelay(javafx.util.Duration.millis(0));
	     
	     imageView.setUserData("iconTag"); 
	     return imageView;
    	
    }
    
    private void showClearButton(Route route, int gemLocation) {
        Button clearButton = new Button("Clear");
        clearButton.setOnAction(event -> {
            // Reset the route 
            routeOptions.getChildren().clear(); 
            Route newRoute = new Route(); 
            displayLinkOptions(player.getLocation(), newRoute, gemLocation);
        });

        // Add the clearButton to routeOptions
        
        if (!routeOptions.getChildren().contains(clearButton)) {
            routeOptions.getChildren().add(clearButton);
        }
    }

    
    private Line drawLine(Link link) {
    	Point startPoint = pointsMap.get(link.getStartPoint());
        Point endPoint = pointsMap.get(link.getEndPoint());
        
        Line line = new Line(startPoint.getLongitude() * scaleX,
                             startPoint.getLatitude() * scaleY,
                             endPoint.getLongitude() * scaleX,
                             endPoint.getLatitude() * scaleY);
        line.setStrokeWidth(4);
        line.setOpacity(0.3);
        
       
        switch (link.getTransport()) {
        case BUS:
            line.setStroke(Color.YELLOW);
            break;
        case TRAIN:
            line.setStroke(Color.GREEN);
            break;
        case CYCLE:
            line.setStroke(Color.ORANGE);
            break;
        default:
            line.setStroke(Color.BLACK); // Default color if none of the cases match
            break;
        }

        
        String tooltipText = String.format("Transport: %s\nCost: %d\nTime: %d\nCarbon: %d",
                link.getTransport(),
                link.getCost(),
                link.getTime(),
                link.getCarbonFootprint());
				Tooltip tooltip = new Tooltip(tooltipText);
				Tooltip.install(line, tooltip);
				tooltip.setShowDelay(javafx.util.Duration.millis(100)); // Use the fully qualified name

				
    	    // Enhance hover effect
		final Color originalStrokeColor = (Color) line.getStroke();
		line.setOnMouseEntered(e -> {
			line.setStrokeWidth(line.getStrokeWidth() * 1.2); // Increase stroke width on hover
			line.setStroke(originalStrokeColor.brighter()); // Brighten the color on hover
		});
		line.setOnMouseExited(e -> {
			line.setStrokeWidth(6); // Revert to original stroke width
			line.setStroke(originalStrokeColor); // Revert to original color
	    });	
		return line;
    }
   
    
    private void addRouteDetails(Route route) {
    	routeOptions.getChildren().clear();
    	routeOptions.getChildren().add(routeHeading); // Title for the route details section
        if (route.getLinks().isEmpty()) return;
        System.out.println("Cost: " + route.getTotalCost());
        System.out.println("Time: " + route.getTotalTime());
        System.out.println("Carbon: " + route.getTotalCarbonFootprint());
        Link previousLink = route.getLinks().get(0);
        int startSegment = previousLink.getStartPoint();
        int endSegment = previousLink.getEndPoint();
        Transport transportType = previousLink.getTransport();
       
        int segmentCost = previousLink.getCost();
        int segmentCarbon = previousLink.getCarbonFootprint();
        int segmentTime = previousLink.getTime();

        for (int i = 1; i < route.getLinks().size(); i++) {
            Link currentLink = route.getLinks().get(i);
            if (currentLink.getTransport() == transportType) {
                // Extend the segment and accumulate cost, carbon, and time
                endSegment = currentLink.getEndPoint();
                segmentCost += currentLink.getCost();
                segmentCarbon += currentLink.getCarbonFootprint();
                segmentTime += currentLink.getTime();
            } else {
                // Transport type changed, print the segment and reset accumulators
                printSegment(routeOptions, startSegment, endSegment, transportType, segmentCost, segmentCarbon, segmentTime);

                // Start a new segment with the current link
                startSegment = currentLink.getStartPoint();
                endSegment = currentLink.getEndPoint();
                transportType = currentLink.getTransport();
                segmentCost = currentLink.getCost();
                segmentCarbon = currentLink.getCarbonFootprint();
                segmentTime = currentLink.getTime();
            }
        }

        // Ensure the last segment is printed
        printSegment(routeOptions, startSegment, endSegment, transportType, segmentCost, segmentCarbon, segmentTime);
        
        String routeSummary = String.format("Cost: %d Carbon: %d Time: %d", route.getTotalCost(), route.getTotalCarbonFootprint(), route.getTotalTime());
        routeOptions.getChildren().add(new Label(routeSummary));
    }

    private void printSegment(VBox vbox, int startSegment, int endSegment, Transport transportType,
                              int cost, int carbon, int time) {
        String detailText = String.format("%s -> %s (%s) \nCost: %d \nCarbon: %d \nTime: %d",
        		pointsMap.get(startSegment).getName(),  pointsMap.get(endSegment).getName(), transportType, cost, carbon, time);
        System.out.println(detailText);
        vbox.getChildren().add(new Label(detailText));
    }
    
    private void collectGem(int gemLocation, Route chosenRoute) {
        System.out.println("Collecting gem at " + gemLocation + " via chosen route.");
        routeOptions.getChildren().clear();
        compareRoutes(chosenRoute, gemLocation);
        // Update the player's location to the route's end point
        player.setLocation(gemLocation);
        SoundEffectsPlayer.playSound("/soundEffects/gem.mp3");

        // Update the player's budgets based on the selected route
        boolean canContinue = player.updateBudgets(chosenRoute.getTotalTime(), chosenRoute.getTotalCost(), chosenRoute.getTotalCarbonFootprint());
        System.out.println("Cash: " + player.getCostBudget() + " Time: " + player.getTimeBudget() + " Carbon : " + player.getCarbonBudget());
        // Mark the gem as collected by removing it from the list of available gems
        if(canContinue) {
        	availableGems.remove(Integer.valueOf(gemLocation));
            player.collectGem();
            
            //new high score
            if(player.getGemsCollected() >= highScore) {
            	System.out.println("New High Score");
            	highScore = player.getGemsCollected();
            	highScoreManager.writeHighScore(player.getGemsCollected());
            }

            // Check if all gems for the round have been collected to possibly proceed to the next round
            if (availableGems.isEmpty()) {
                System.out.println("All gems collected for this round.");
                currentRound++;
                startRound();
            }else {
            	displayGems();
            }
        }else {

        	showGameOverPopup("Game OVER!!!");
        	
        }
        
    }
    
    private void compareRoutes(Route selectedRoute, int gemLocation) {
        Route fastestRoute = mapGrap.findRoute(player.getLocation(), gemLocation, "time");
        Route cheapestRoute = mapGrap.findRoute(player.getLocation(), gemLocation, "cost");
        Route lowestCarbonRoute = mapGrap.findRoute(player.getLocation(), gemLocation, "carbon");

        boolean isFastest = selectedRoute.getTotalTime() <= fastestRoute.getTotalTime();
        boolean isCheapest = selectedRoute.getTotalCost() <= cheapestRoute.getTotalCost();
        boolean isLowestCarbon = selectedRoute.getTotalCarbonFootprint() <= lowestCarbonRoute.getTotalCarbonFootprint();

        StringBuilder message = new StringBuilder();

        if (isLowestCarbon) {
            message.append("Congratulations! You've chosen the route with the lowest carbon footprint. Thank you for being environmentally conscious!");
        } else {
            message.append("You've chosen a good route, but here's how you could do even better:\n");
            if (!isFastest) {
                message.append(String.format("Faster route: Saves you %d minutes.\n", fastestRoute.getTotalTime() - selectedRoute.getTotalTime()));
            }
            if (!isCheapest) {
                message.append(String.format("Cheaper route: Saves you $%d.\n", cheapestRoute.getTotalCost() - selectedRoute.getTotalCost()));
            }
            if (!isLowestCarbon) {
                message.append(String.format("Lower carbon route: Reduces carbon emissions by %d units.\n", lowestCarbonRoute.getTotalCarbonFootprint() - selectedRoute.getTotalCarbonFootprint()));
            }
            message.append("Consider these options next time to optimize your travel impact!");
        }

        Platform.runLater(() -> {
            Stage primaryStage = Main.getPrimaryStage(); // Use your static method to get the primary stage
            boolean wasFullScreen = primaryStage.isFullScreen();

            // Temporarily exit full screen to ensure alert is visible
            if (wasFullScreen) {
                primaryStage.setFullScreen(false);
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Route Comparison");
            alert.setHeaderText(null); // No header text
            alert.setContentText(message.toString());
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: blue; -fx-border-width: 2;");
            dialogPane.lookup(".content.label").setStyle("-fx-font-size: 16px; -fx-text-fill: #333333;");
            alert.showAndWait();

            // Return to full-screen mode if it was originally set
            if (wasFullScreen) {
                primaryStage.setFullScreen(true);
            }
        });
    }


    
    public void updatePlayerStatus() {
    	progressCarbon = player.getCarbonBudget();
        progressCarbon = progressCarbon/200;
        progressTime = player.getTimeBudget();
        progressTime = progressTime/100;
        progressCost = player.getCostBudget();
        progressCost = progressCost/50;
        
        if (progressCarbon < 0) {
        	progressCarbon = 0.0;
        }
        if (progressTime < 0) {
        	progressTime = 0.0;
        }
        if (progressCost < 0) {
        	progressCost = 0.0;
        }
                
        if (progressCarbon < 0.25) {
        	carbonProgress.setStyle("-fx-accent: red;");
        }
        else {
        	carbonProgress.setStyle("-fx-accent: limegreen;");
        }
        if (progressTime < 0.25) {
        	timeProgress.setStyle("-fx-accent: red;");
        }
        else {
        	timeProgress.setStyle("-fx-accent: limegreen;");
        }
        if (progressCost < 0.25) {
        	costProgress.setStyle("-fx-accent: red;");
        }
        else {
        	costProgress.setStyle("-fx-accent: limegreen;");
        }
    	carbonProgress.setProgress(progressCarbon);
        timeProgress.setProgress(progressTime);
        costProgress.setProgress(progressCost);
        carbonBudgetLabel.setText("Carbon Budget: " + player.getCarbonBudget());
        timeBudgetLabel.setText("Time Budget: " + player.getTimeBudget());
        costBudgetLabel.setText("Cost Budget: " + player.getCostBudget());
        scoreLabel.setText(String.format("High Score: %d%nCurrent Score: %d", highScore, player.getGemsCollected()));

    
    }
    
    public void showGameOverPopup(String message) {
        // Create a new Alert of type INFORMATION
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Game Over"); 
        alert.setHeaderText(null); 
        alert.setContentText(message + "Gems Collected : " + player.getGemsCollected()); 

        // Show the alert and wait for the user to close it
        alert.showAndWait();
        Stage primaryStage = Main.getPrimaryStage();
        GameMenu gameMenu = new GameMenu(primaryStage);
        Scene menuScene = gameMenu.getGameMenuScene();

        
        primaryStage.setScene(menuScene);
       	SoundEffectsPlayer.playSound("/soundEffects/gameOver.wav");
        primaryStage.show();
    }

}

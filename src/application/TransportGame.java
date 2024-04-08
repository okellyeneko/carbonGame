package application;

import javafx.application.Application;
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
import javafx.scene.text.Font;
import javafx.scene.shape.Circle;
import javafx.scene.layout.BorderPane;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;



public class TransportGame {
    
    private BorderPane  root; // Using BorderPane  for simplicity, replace with your actual game layout
    private Player player;
    private MapGraph mapGrap;
    private int currentLevel = 1;
    private int currentRound = 1;
    private int gemsCollected = 0;
    private int gemsToCollect = 4; // Initial value for level 1
    private int currentLocation = 1; // Assuming the game starts at point 1
    HighScoreManager highScoreManager = new HighScoreManager();
    private int  highScore;
    private Map<Integer, Point> pointsMap;
    private List<Integer> availableGems;
    double scaleFactor = 0.5; // You can adjust this factor as needed
    double scaleX = 100.0 * scaleFactor;
    double scaleY = 100.0 * scaleFactor;
    double offsetX = 0.0 * scaleFactor; // Example offset if needed
    double offsetY = 0.0 * scaleFactor;
    private Scene gameScene;
    private Pane mainGameArea;
    private VBox leftPanel;
    private VBox routeOptions;
    private VBox budgetsArea;
    private Label carbonBudgetLabel;
    private Label timeBudgetLabel;
    private Label costBudgetLabel;
    
    
    public TransportGame(BorderPane root, Scene gameScene) {
        this.root = root;
        this.gameScene = gameScene;
        initializeGame();
    }
    
    private void initializeGame() {
        // Here, initialize your game components. For example:
        leftPanel = new VBox(10);
        routeOptions = new VBox(5);
        budgetsArea = new VBox(5);
        leftPanel.setStyle("-fx-background-color: #778899;");
        leftPanel.setPrefWidth(400);
        this.root.setLeft(leftPanel);
        carbonBudgetLabel = new Label("Carbon Budget: ");
        timeBudgetLabel = new Label("Time Budget: ");
        costBudgetLabel = new Label("Cost Budget: ");
        budgetsArea.getChildren().addAll(carbonBudgetLabel, timeBudgetLabel, costBudgetLabel);
        leftPanel.getChildren().add(routeOptions);
        leftPanel.getChildren().add(budgetsArea);
        mainGameArea = new Pane();
        root.setCenter(mainGameArea);
        highScore = highScoreManager.readHighScore();
        // You might need to adjust how the scene or stage is handled based on your game's requirements.
        // This method should prepare the game's UI within the provided BorderPane.
    }
  

    private void initializePoints() {
        pointsMap.put(1, new Point(1, "Dundrum", 2.46, -0.05));
        pointsMap.put(2, new Point(2, "Miltown", 0.82, 1.55));
        pointsMap.put(3, new Point(3, "Ranelagh", 0.26, 1.85));
        pointsMap.put(4, new Point(4, "Stephens Green", 0.82, 2.26));
        pointsMap.put(5, new Point(5, "Connolly Station", 1.06, 3.87));
        pointsMap.put(6, new Point(6, "Grand Canal Dock", 2.46, 3.46));
        pointsMap.put(7, new Point(7, "Sandymount", 0.26, 4.05));
        pointsMap.put(8, new Point(8, "Booterstown", 4.05, 1.5));
        pointsMap.put(9, new Point(9, "Blackrock", 3.85, 0.17));
        pointsMap.put(10, new Point(10, "Mount Merrion", 3.95, 2.94));
        pointsMap.put(11, new Point(11, "UCD", 4.05, 2.18));
        pointsMap.put(12, new Point(12, "Donnybrook", 0.53, -0.05));
        pointsMap.put(13, new Point(13, "A", 3.51, 3.465));
        pointsMap.put(14, new Point(14, "B", 5.28, 3.48));
        pointsMap.put(15, new Point(15, "C", 4.53, 3.9));
        pointsMap.put(16, new Point(16, "D", 4.09, 4.4));
        pointsMap.put(17, new Point(17, "E", 4.45, 4.85));
        pointsMap.put(18, new Point(18, "F", 3.4, 4.85));
        pointsMap.put(19, new Point(19, "G", 2.95, 4.75));
        pointsMap.put(20, new Point(20, "H", 2.5, 4.57));
        pointsMap.put(21, new Point(21, "I", 5.28, 1.99));
        pointsMap.put(22, new Point(22, "J", 5.29, 2.94));
        pointsMap.put(23, new Point(23, "K", 2.89, 3.05));
        pointsMap.put(24, new Point(24, "L", 1.89, 3.17));
        pointsMap.put(25, new Point(25, "M", 1.89, 3.17));
        pointsMap.put(26, new Point(26, "N", 4.1, 4.85));
        pointsMap.put(27, new Point(27, "O", 2.7, 2.9));
        pointsMap.put(28, new Point(28, "P", 3.45, 2.2));
        pointsMap.put(29, new Point(29, "Q", 5.1, 4.0));
        pointsMap.put(30, new Point(30, "R", 4.96, 2.2));
        pointsMap.put(31, new Point(31, "S", 4.96, 2.7));
        pointsMap.put(32, new Point(32, "T", 5.29, 2.7));
        pointsMap.put(33, new Point(33, "U", 1.05, 2.7));
        pointsMap.put(34, new Point(34, "V", 2.25, 4.1));
    }
    
    private void initializeMapGraph() {
        // Initialise your mapGraph with Links, similar to previous examples
    	mapGrap.addLink(new Link(2, 4, Transport.LUAS, 5, 2, 10));
    	mapGrap.addLink(new Link(2, 12, Transport.LUAS, 5, 2, 10));
    	mapGrap.addLink(new Link(2, 3, Transport.LUAS, 5, 2, 10));
    	
    	mapGrap.addLink(new Link(3, 2, Transport.LUAS, 5, 2, 10));
    	mapGrap.addLink(new Link(3, 7, Transport.LUAS, 5, 2, 10));
    	
    	mapGrap.addLink(new Link(4, 2, Transport.LUAS, 5, 2, 10));
    	mapGrap.addLink(new Link(4, 33, Transport.LUAS, 5, 2, 10));
    	
    	mapGrap.addLink(new Link(6, 13, Transport.LUAS, 5, 2, 10));
    	mapGrap.addLink(new Link(6, 23, Transport.LUAS, 5, 2, 10));
    	mapGrap.addLink(new Link(6, 25, Transport.LUAS, 5, 2, 10));
    	
    	mapGrap.addLink(new Link(25, 6, Transport.LUAS, 5, 2, 10));
    	mapGrap.addLink(new Link(25, 27, Transport.LUAS, 5, 2, 10));
    	
    	mapGrap.addLink(new Link(27, 25, Transport.LUAS, 5, 2, 10));
    	mapGrap.addLink(new Link(27, 23, Transport.LUAS, 5, 2, 10));
    	mapGrap.addLink(new Link(27, 28, Transport.LUAS, 5, 2, 10));
    	
    	mapGrap.addLink(new Link(28, 27, Transport.LUAS, 5, 2, 10));
    	mapGrap.addLink(new Link(28, 11, Transport.LUAS, 5, 2, 10));
    	
    	mapGrap.addLink(new Link(33, 4, Transport.LUAS, 5, 2, 10));
    	mapGrap.addLink(new Link(33, 5, Transport.LUAS, 5, 2, 10));
    	
    	mapGrap.addLink(new Link(12, 1, Transport.LUAS, 5, 2, 10));
    	mapGrap.addLink(new Link(12, 2, Transport.LUAS, 5, 2, 10));
    	
    	mapGrap.addLink(new Link(1, 12, Transport.LUAS, 5, 2, 10));
    	mapGrap.addLink(new Link(1, 9, Transport.LUAS, 5, 2, 10));
    	
    	mapGrap.addLink(new Link(7, 3, Transport.LUAS, 5, 2, 10));
    	
      	mapGrap.addLink(new Link(9, 1, Transport.LUAS, 5, 2, 10));
    	mapGrap.addLink(new Link(9, 8, Transport.LUAS, 5, 2, 10));
    	
    	mapGrap.addLink(new Link(8, 9, Transport.LUAS, 5, 2, 10));
    	mapGrap.addLink(new Link(8, 11, Transport.LUAS, 5, 2, 10));
    	
    	mapGrap.addLink(new Link(11, 28, Transport.LUAS, 5, 2, 10));
    	mapGrap.addLink(new Link(11, 30, Transport.LUAS, 5, 2, 10));
    	mapGrap.addLink(new Link(11, 8, Transport.LUAS, 5, 2, 10));


    	mapGrap.addLink(new Link(10, 13, Transport.LUAS, 5, 2, 10));
    	mapGrap.addLink(new Link(10, 22, Transport.LUAS, 5, 2, 10));
    	
     	mapGrap.addLink(new Link(13, 6, Transport.LUAS, 5, 2, 10));
    	mapGrap.addLink(new Link(13, 10, Transport.LUAS, 5, 2, 10));
     	mapGrap.addLink(new Link(13, 16, Transport.LUAS, 5, 2, 10));
     	mapGrap.addLink(new Link(13, 14, Transport.LUAS, 5, 2, 10));
     	
     	mapGrap.addLink(new Link(15, 14, Transport.LUAS, 5, 2, 10));
     	mapGrap.addLink(new Link(15, 16, Transport.LUAS, 5, 2, 10));
     	mapGrap.addLink(new Link(15, 29, Transport.LUAS, 5, 2, 10));
    	
     	mapGrap.addLink(new Link(14, 13, Transport.LUAS, 5, 2, 10));
     	mapGrap.addLink(new Link(14, 22, Transport.LUAS, 5, 2, 10));
     	mapGrap.addLink(new Link(14, 29, Transport.LUAS, 5, 2, 10));
     	mapGrap.addLink(new Link(14, 15, Transport.LUAS, 5, 2, 10));
     	
    	mapGrap.addLink(new Link(16, 26, Transport.LUAS, 5, 2, 10));
     	mapGrap.addLink(new Link(16, 19, Transport.LUAS, 5, 2, 10));
     	mapGrap.addLink(new Link(16, 29, Transport.LUAS, 5, 2, 10));
     	mapGrap.addLink(new Link(16, 13, Transport.LUAS, 5, 2, 10));
     	mapGrap.addLink(new Link(16, 15, Transport.LUAS, 5, 2, 10));
     	
    	mapGrap.addLink(new Link(19, 16, Transport.LUAS, 5, 2, 10));
     	mapGrap.addLink(new Link(19, 20, Transport.LUAS, 5, 2, 10));
     	
    	mapGrap.addLink(new Link(21, 32, Transport.LUAS, 5, 2, 10));
     
    	mapGrap.addLink(new Link(23, 27, Transport.LUAS, 5, 2, 10));
     	mapGrap.addLink(new Link(23, 6, Transport.LUAS, 5, 2, 10));
     	
    	mapGrap.addLink(new Link(26, 16, Transport.LUAS, 5, 2, 10));
     	mapGrap.addLink(new Link(26, 17, Transport.LUAS, 5, 2, 10));
     	mapGrap.addLink(new Link(26, 18, Transport.LUAS, 5, 2, 10));
     	
     	mapGrap.addLink(new Link(17, 26, Transport.LUAS, 5, 2, 10));
     	
     	mapGrap.addLink(new Link(18, 26, Transport.LUAS, 5, 2, 10));
     	
    	mapGrap.addLink(new Link(29, 14, Transport.LUAS, 5, 2, 10));
     	mapGrap.addLink(new Link(29, 15, Transport.LUAS, 5, 2, 10));
     	mapGrap.addLink(new Link(29, 16, Transport.LUAS, 5, 2, 10));
     	
    	mapGrap.addLink(new Link(5, 34, Transport.LUAS, 5, 2, 10));
    	mapGrap.addLink(new Link(5, 33, Transport.LUAS, 5, 2, 10));
    	
    	mapGrap.addLink(new Link(30, 31, Transport.LUAS, 5, 2, 10));
    	mapGrap.addLink(new Link(30, 11, Transport.LUAS, 5, 2, 10));
    	
      	mapGrap.addLink(new Link(31, 30, Transport.LUAS, 5, 2, 10));
    	mapGrap.addLink(new Link(31, 32, Transport.LUAS, 5, 2, 10));
    	
     	mapGrap.addLink(new Link(32, 31, Transport.LUAS, 5, 2, 10));
     	mapGrap.addLink(new Link(32, 21, Transport.LUAS, 5, 2, 10));
     	mapGrap.addLink(new Link(32, 22, Transport.LUAS, 5, 2, 10));
     	
     	mapGrap.addLink(new Link(22, 32, Transport.LUAS, 5, 2, 10));
     	mapGrap.addLink(new Link(22, 10, Transport.LUAS, 5, 2, 10));
     	mapGrap.addLink(new Link(22, 14, Transport.LUAS, 5, 2, 10));
    	
    	mapGrap.addLink(new Link(34, 5, Transport.LUAS, 5, 2, 10));
    	mapGrap.addLink(new Link(34, 20, Transport.LUAS, 5, 2, 10));
    	
    	mapGrap.addLink(new Link(20, 34, Transport.LUAS, 5, 2, 10));
    	mapGrap.addLink(new Link(20, 19, Transport.LUAS, 5, 2, 10));
    	
    	// BUS routes addition
    	mapGrap.addLink(new Link(2, 4, Transport.BUS, 10, 1, 15));
    	mapGrap.addLink(new Link(2, 12, Transport.BUS, 10, 1, 15));
    	mapGrap.addLink(new Link(2, 3, Transport.BUS, 10, 1, 15));
    	
    	mapGrap.addLink(new Link(3, 2, Transport.BUS, 10, 1, 15));
    	mapGrap.addLink(new Link(3, 7, Transport.BUS, 10, 1, 15));
    	
    	mapGrap.addLink(new Link(4, 2, Transport.BUS, 10, 1, 15));
    	mapGrap.addLink(new Link(4, 33, Transport.BUS, 10, 1, 15));
    	
    	mapGrap.addLink(new Link(6, 13, Transport.BUS, 10, 1, 15));
    	mapGrap.addLink(new Link(6, 23, Transport.BUS, 10, 1, 15));
    	mapGrap.addLink(new Link(6, 25, Transport.BUS, 10, 1, 15));
    	
    	mapGrap.addLink(new Link(25, 6, Transport.BUS, 10, 1, 15));
    	mapGrap.addLink(new Link(25, 27, Transport.BUS, 10, 1, 15));
    	
    	mapGrap.addLink(new Link(27, 25, Transport.BUS, 10, 1, 15));
    	mapGrap.addLink(new Link(27, 23, Transport.BUS, 10, 1, 15));
    	mapGrap.addLink(new Link(27, 28, Transport.BUS, 10, 1, 15));
    	
    	mapGrap.addLink(new Link(28, 27, Transport.BUS, 10, 1, 15));
    	mapGrap.addLink(new Link(28, 11, Transport.BUS, 10, 1, 15));
    	
    	mapGrap.addLink(new Link(33, 4, Transport.BUS, 10, 1, 15));
    	mapGrap.addLink(new Link(33, 5, Transport.BUS, 10, 1, 15));
    	
    	mapGrap.addLink(new Link(12, 1, Transport.BUS, 10, 1, 15));
    	mapGrap.addLink(new Link(12, 2,Transport.BUS, 10, 1, 15));
    	
    	mapGrap.addLink(new Link(1, 12, Transport.BUS, 10, 1, 15));
    	mapGrap.addLink(new Link(1, 9, Transport.BUS, 10, 1, 15));
    	
    	mapGrap.addLink(new Link(7, 3, Transport.BUS, 10, 1, 15));
    	
      	mapGrap.addLink(new Link(9, 1, Transport.BUS, 10, 1, 15));
    	mapGrap.addLink(new Link(9, 8, Transport.BUS, 10, 1, 15));
    	
    	mapGrap.addLink(new Link(8, 9, Transport.BUS, 10, 1, 15));
    	mapGrap.addLink(new Link(8, 11, Transport.BUS, 10, 1, 15));
    	
    	mapGrap.addLink(new Link(11, 28, Transport.BUS, 10, 1, 15));
    	mapGrap.addLink(new Link(11, 30, Transport.BUS, 10, 1, 15));
    	mapGrap.addLink(new Link(11, 8, Transport.BUS, 10, 1, 15));


    	mapGrap.addLink(new Link(10, 13, Transport.BUS, 10, 1, 15));
    	mapGrap.addLink(new Link(10, 22, Transport.BUS, 10, 1, 15));
    	
     	mapGrap.addLink(new Link(13, 6, Transport.BUS, 10, 1, 15));
    	mapGrap.addLink(new Link(13, 10, Transport.BUS, 10, 1, 15));
     	mapGrap.addLink(new Link(13, 16, Transport.BUS, 10, 1, 15));
     	mapGrap.addLink(new Link(13, 14, Transport.BUS, 10, 1, 15));
     	
     	mapGrap.addLink(new Link(15, 14, Transport.BUS, 10, 1, 15));
     	mapGrap.addLink(new Link(15, 16, Transport.BUS, 10, 1, 15));
     	mapGrap.addLink(new Link(15, 29, Transport.BUS, 10, 1, 15));
    	
     	mapGrap.addLink(new Link(14, 13, Transport.BUS, 10, 1, 15));
     	mapGrap.addLink(new Link(14, 22, Transport.BUS, 10, 1, 15));
     	mapGrap.addLink(new Link(14, 29, Transport.BUS, 10, 1, 15));
     	mapGrap.addLink(new Link(14, 15, Transport.BUS, 10, 1, 15));
     	
    	mapGrap.addLink(new Link(16, 26, Transport.BUS, 10, 1, 15));
     	mapGrap.addLink(new Link(16, 19, Transport.BUS, 10, 1, 15));
     	mapGrap.addLink(new Link(16, 29, Transport.BUS, 10, 1, 15));
     	mapGrap.addLink(new Link(16, 13, Transport.BUS, 10, 1, 15));
     	mapGrap.addLink(new Link(16, 15, Transport.BUS, 10, 1, 15));
     	
    	mapGrap.addLink(new Link(19, 16, Transport.BUS, 10, 1, 15));
     	mapGrap.addLink(new Link(19, 20, Transport.BUS, 10, 1, 15));
     	
    	mapGrap.addLink(new Link(21, 32, Transport.BUS, 10, 1, 15));
     
    	mapGrap.addLink(new Link(23, 27, Transport.BUS, 10, 1, 15));
     	mapGrap.addLink(new Link(23, 6, Transport.BUS, 10, 1, 15));
     	
    	mapGrap.addLink(new Link(26, 16, Transport.BUS, 10, 1, 15));
     	mapGrap.addLink(new Link(26, 17, Transport.BUS, 10, 1, 15));
     	mapGrap.addLink(new Link(26, 18, Transport.BUS, 10, 1, 15));
     	
     	mapGrap.addLink(new Link(17, 26, Transport.BUS, 10, 1, 15));
     	
     	mapGrap.addLink(new Link(18, 26, Transport.BUS, 10, 1, 15));
     	
    	mapGrap.addLink(new Link(29, 14, Transport.BUS, 10, 1, 15));
     	mapGrap.addLink(new Link(29, 15, Transport.BUS, 10, 1, 15));
     	mapGrap.addLink(new Link(29, 16, Transport.BUS, 10, 1, 15));
     	
    	mapGrap.addLink(new Link(5, 34, Transport.BUS, 10, 1, 15));
    	mapGrap.addLink(new Link(5, 33, Transport.BUS, 10, 1, 15));
    	
    	mapGrap.addLink(new Link(30, 31, Transport.BUS, 10, 1, 15));
    	mapGrap.addLink(new Link(30, 11, Transport.BUS, 10, 1, 15));
    	
      	mapGrap.addLink(new Link(31, 30, Transport.BUS, 10, 1, 15));
    	mapGrap.addLink(new Link(31, 32, Transport.BUS, 10, 1, 15));
    	
     	mapGrap.addLink(new Link(32, 31, Transport.BUS, 10, 1, 15));
     	mapGrap.addLink(new Link(32, 21, Transport.BUS, 10, 1, 15));
     	mapGrap.addLink(new Link(32, 22, Transport.BUS, 10, 1, 15));
     	
     	mapGrap.addLink(new Link(22, 32, Transport.BUS, 10, 1, 15));
     	mapGrap.addLink(new Link(22, 10, Transport.BUS, 10, 1, 15));
     	mapGrap.addLink(new Link(22, 14, Transport.BUS, 10, 1, 15));
    	
    	mapGrap.addLink(new Link(34, 5, Transport.BUS, 10, 1, 15));
    	mapGrap.addLink(new Link(34, 20, Transport.BUS, 10, 1, 15));
    	
    	mapGrap.addLink(new Link(20, 34, Transport.BUS, 10, 1, 15));
    	mapGrap.addLink(new Link(20, 19, Transport.BUS, 10, 1, 15));


   
//        mapGrap.addLink(new Link(2, 3, Transport.LUAS, 5, 2, 10));
//        mapGrap.addLink(new Link(3, 4, Transport.LUAS, 5, 2, 10));
//        mapGrap.addLink(new Link(4, 5, Transport.LUAS, 5, 2, 10));
//        mapGrap.addLink(new Link(5, 6, Transport.DART, 5, 2, 10));
//        mapGrap.addLink(new Link(6, 7, Transport.DART, 5, 2, 10));
//        mapGrap.addLink(new Link(7, 8, Transport.DART, 5, 2, 10));
//        mapGrap.addLink(new Link(8, 9, Transport.DART, 5, 2, 10));
//        
//        mapGrap.addLink(new Link(9, 10, Transport.BUS, 5, 1, 15));
//        mapGrap.addLink(new Link(10, 11, Transport.BUS, 5, 1, 15));
//        mapGrap.addLink(new Link(11, 12, Transport.BUS, 5, 1, 15));
//        mapGrap.addLink(new Link(12, 4, Transport.BUS, 5, 1, 15));
//        
//        mapGrap.addLink(new Link(1, 2, Transport.CYCLE, 15, 1, 0));
//        mapGrap.addLink(new Link(2, 3, Transport.CYCLE, 15, 1, 0));
//        mapGrap.addLink(new Link(3, 4, Transport.CYCLE, 15, 1, 0));
//        mapGrap.addLink(new Link(4, 5, Transport.CYCLE, 15, 1, 0));
//        mapGrap.addLink(new Link(2, 1, Transport.CYCLE, 15, 1, 0));
//        mapGrap.addLink(new Link(3, 2, Transport.CYCLE, 15, 1, 0));
//        mapGrap.addLink(new Link(4, 3, Transport.CYCLE, 15, 1, 0));
//        mapGrap.addLink(new Link(5, 4, Transport.CYCLE, 15, 1, 0));
//        
//        
//        mapGrap.addLink(new Link(1, 11, Transport.BUS, 10, 1, 15));
//        mapGrap.addLink(new Link(11, 3, Transport.BUS, 10, 1, 15));
//        mapGrap.addLink(new Link(3, 12, Transport.BUS, 5, 1, 15));
//        mapGrap.addLink(new Link(12, 7, Transport.BUS, 10, 1, 15));
//        
//        mapGrap.addLink(new Link(8, 11, Transport.CYCLE, 30, 1, 0));
//        mapGrap.addLink(new Link(4, 7, Transport.CYCLE, 30, 1, 0));
//        
//        mapGrap.addLink(new Link(2, 1, Transport.LUAS, 5, 2, 10));
//        mapGrap.addLink(new Link(3, 2, Transport.LUAS, 5, 2, 10));
//        mapGrap.addLink(new Link(4, 3, Transport.LUAS, 5, 2, 10));
//        mapGrap.addLink(new Link(5, 4, Transport.LUAS, 5, 2, 10));
//        mapGrap.addLink(new Link(6, 5, Transport.DART, 5, 2, 10));
//        mapGrap.addLink(new Link(7, 6, Transport.DART, 5, 2, 10));
//        mapGrap.addLink(new Link(8, 7, Transport.DART, 5, 2, 10));
//        mapGrap.addLink(new Link(9, 8, Transport.DART, 5, 2, 10));
//
//        mapGrap.addLink(new Link(10, 9, Transport.BUS, 5, 1, 15));
//        mapGrap.addLink(new Link(11, 10, Transport.BUS, 5, 1, 15));
//        mapGrap.addLink(new Link(12, 11, Transport.BUS, 5, 1, 15));
//        mapGrap.addLink(new Link(4, 12, Transport.BUS, 5, 1, 15));
//
//        mapGrap.addLink(new Link(11, 1, Transport.BUS, 10, 1, 15));
//        mapGrap.addLink(new Link(3, 11, Transport.BUS, 10, 1, 15));
//        mapGrap.addLink(new Link(12, 3, Transport.BUS, 5, 1, 15));
//        mapGrap.addLink(new Link(7, 12, Transport.BUS, 10, 1, 15));
//
//        mapGrap.addLink(new Link(11, 8, Transport.CYCLE, 30, 1, 0));
//        mapGrap.addLink(new Link(7, 4, Transport.CYCLE, 30, 1, 0));

        
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
        Image mapImage = new Image(getClass().getResourceAsStream("map.jpeg"));
        ImageView mapView = new ImageView(mapImage);
        anchorPane.getChildren().add(mapView); // Add the map to the container

        // Increase the size of the map
        double scaleFactor2 = 0.4;
        mapView.setFitWidth(mapImage.getWidth() * scaleFactor2);
        mapView.setFitHeight(mapImage.getHeight() * scaleFactor2);

        // Display station names and redraw circles for stations
        for (Point point : pointsMap.values()) {
            // Display station name label
            Label stationLabel = new Label(point.getName());
            stationLabel.setLayoutX(point.getLongitude() * scaleX + offsetX);
            stationLabel.setLayoutY(point.getLatitude() * scaleY + offsetY);
            anchorPane.getChildren().add(stationLabel);

            // Redraw circle for station
            Circle stationCircle = new Circle(point.getLongitude() * scaleX + offsetX, point.getLatitude() * scaleY + offsetY, 5, Color.BLUE);
            anchorPane.getChildren().add(stationCircle);
        }

        // Place player sprite on the map
        Image playerSprite = new Image(getClass().getResourceAsStream("player.png"));
        ImageView playerImageView = new ImageView(playerSprite);
        double playerX = pointsMap.get(player.getLocation()).getLongitude() * scaleX + offsetX; // Example scaling
        double playerY = pointsMap.get(player.getLocation()).getLatitude() * scaleY + offsetY; // Example scaling

        // Assuming the player's sprite image is too big, let's scale it down
        playerImageView.setFitWidth(30); // Set width to 20px, adjust as necessary
        playerImageView.setFitHeight(30); // Set height to 20px, adjust as necessary
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

                line.setOnMouseClicked(e -> {
                	
                    route.addLink(link);
                    addRouteDetails(route);
                    Button clearButton = new Button("Clear");
                    clearButton.setOnAction(r -> {
                    	routeOptions.getChildren().clear();
                    	displayLinkOptions(player.getLocation(), new Route(),gemLocation);
                    });
                    
                    // Add the routeOptions and cancelButton to the main layout
                    routeOptions.getChildren().addAll(clearButton);
                    
                    //Dont really need this if statement
                    if (link.getEndPoint() == gemLocation) {
                        // End point reached, possibly end or reset the game here
                        System.out.println("Gem location reached. Route completed.");
                        displayLinkOptions(link.getEndPoint(), route, gemLocation);
                    } else {
                        // Recursive call to allow further link selection
                        displayLinkOptions(link.getEndPoint(), route, gemLocation);
                    }
                });
            }
            //Dont add the line if it is ending at the current point as it makes it harder to click the lines we want to travel
            if(link.getEndPoint() != point) {
            	mainGameArea.getChildren().add(line);
            }
            
        }
        
        //Add Collect Gem Button
        if(point == gemLocation) {
        	Button clearButton = new Button("Clear");
            clearButton.setOnAction(r -> {
            	routeOptions.getChildren().clear();
            	displayLinkOptions(player.getLocation(), new Route(),gemLocation);
            });
            Button collectGemButton = new Button("Collect Gem");
            collectGemButton.setOnAction(r -> collectGem(gemLocation, route));
            // Add the routeOptions and cancelButton to the main layout
            routeOptions.getChildren().addAll(clearButton, collectGemButton);
        }
        
        
        
    }
    
    private Line drawLine(Link link) {
    	Point startPoint = pointsMap.get(link.getStartPoint());
        Point endPoint = pointsMap.get(link.getEndPoint());
        
        boolean startPointIsLower = link.getStartPoint() < link.getEndPoint();
        double baseX = startPointIsLower ? startPoint.getLongitude() : endPoint.getLongitude();
        double baseY = startPointIsLower ? startPoint.getLatitude() : endPoint.getLatitude();
        double targetX = startPointIsLower ? endPoint.getLongitude() : startPoint.getLongitude();
        double targetY = startPointIsLower ? endPoint.getLatitude() : startPoint.getLatitude();

        
     // Calculate direction vector (base to target) and normalize
        double dx = targetX - baseX;
        double dy = targetY - baseY;
        double length = Math.sqrt(dx * dx + dy * dy);
        double offsetX = (dy / length) * (link.getTransport() == Transport.BUS ? 4 : 0); // Example: only offset cycles
        double offsetY = (-dx / length) * (link.getTransport() == Transport.BUS ? 4 : 0);

        Line line = new Line(startPoint.getLongitude() * scaleX + offsetX + this.offsetX,
                             startPoint.getLatitude() * scaleY + offsetY + this.offsetY,
                             endPoint.getLongitude() * scaleX + offsetX + this.offsetX,
                             endPoint.getLatitude() * scaleY + offsetY + this.offsetY);
        line.setStrokeWidth(4);
        line.setOpacity(0.3);
        
        

        //Line line = new Line(startPoint.getLongitude() * scaleX + offsetX, startPoint.getLatitude() * scaleY + offsetY, endPoint.getLongitude() * scaleX + offsetX, endPoint.getLatitude() * scaleY + offsetY);
        
        
        switch (link.getTransport()) {
        case BUS:
            line.setStroke(Color.YELLOW);
            break;
        case LUAS:
            line.setStroke(Color.GREEN);
            break;
        case CYCLE:
            line.setStroke(Color.ORANGE);
            break;
        case DART:
            line.setStroke(Color.RED);
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
		
		
				
		return line;
    }
    
  /*
    
    
    private void displayRouteOptions(int gemLocation, AnchorPane anchorPane) {
        // Clear previous content but keep the background
        anchorPane.getChildren().clear();
        ImageView mapView = new ImageView(new Image(getClass().getResourceAsStream("map_background.jpg")));
        anchorPane.getChildren().add(mapView);

        // Fetch routes with different criteria
        Route fastestRoute = mapGrap.findRoute(player.getLocation(), gemLocation, "time");
        Route cheapestRoute = mapGrap.findRoute(player.getLocation(), gemLocation, "cost");
        Route lowestCarbonRoute = mapGrap.findRoute(player.getLocation(), gemLocation, "carbon");

        

        // Add buttons for selecting a route
        addRouteSelectionButtons(anchorPane, gemLocation, fastestRoute, cheapestRoute, lowestCarbonRoute);
    }

    private List<Line> drawRoute(AnchorPane anchorPane, Route route) {
        List<Line> routeLines = new ArrayList<>();

        for (Link link : route.getLinks()) {
            Point start = pointsMap.get(link.getStartPoint());
            Point end = pointsMap.get(link.getEndPoint());
            Line line = new Line(start.getLongitude() * scaleX + offsetX, start.getLatitude() * scaleY + offsetY,
                                 end.getLongitude() * scaleX + offsetX, end.getLatitude() * scaleY + offsetY);

            // Determine the color based on the method of transport
            switch (link.getTransport()) {
                case BUS:
                    line.setStroke(Color.YELLOW);
                    break;
                case LUAS:
                    line.setStroke(Color.GREEN);
                    break;
                case CYCLE:
                    line.setStroke(Color.ORANGE);
                    break;
                case DART:
                    line.setStroke(Color.RED);
                    break;
                default:
                    line.setStroke(Color.BLACK); // Default color if none of the cases match
                    break;
            }

            line.setStrokeWidth(4); // Default stroke width
            Tooltip.install(line, new Tooltip(link.getTransport().toString())); // Tooltip showing the route name and transport method
            anchorPane.getChildren().add(line);
            routeLines.add(line);
        }

        return routeLines;
    }


    private void addRouteSelectionButtons(AnchorPane anchorPane, int gemLocation,
        Route fastestRoute, Route cheapestRoute, Route lowestCarbonRoute) {
		List<Line> fastestRouteLines = drawRoute(anchorPane, fastestRoute);
		List<Line> cheapestRouteLines = drawRoute(anchorPane, cheapestRoute);
		List<Line> lowestCarbonRouteLines = drawRoute(anchorPane, lowestCarbonRoute);
		
		routeOptions.getChildren().clear(); // Clear existing content in the routeOptions

		
		Button btnFastest = new Button("Fastest Route");
		Button btnCheapest = new Button("Cheapest Route");
		Button btnLowestCarbon = new Button("Lowest Carbon Route");
		
		setupButtonWithRouteLines(btnFastest, fastestRouteLines, gemLocation, fastestRoute);
		setupButtonWithRouteLines(btnCheapest, cheapestRouteLines, gemLocation, cheapestRoute);
		setupButtonWithRouteLines(btnLowestCarbon, lowestCarbonRouteLines, gemLocation, lowestCarbonRoute);
		// Example positioning, adjust as necessary
		

		routeOptions.getChildren().addAll(btnFastest, btnCheapest, btnLowestCarbon);
		
		VBox fastestBox = new VBox(2);
	    VBox cheapestBox = new VBox(2);
	    VBox lowestCarbonBox = new VBox(2);

	    // Adjust these as necessary for layout
	    fastestBox.setLayoutX(50); fastestBox.setLayoutY(400);
	    cheapestBox.setLayoutX(250); cheapestBox.setLayoutY(400);
	    lowestCarbonBox.setLayoutX(450); lowestCarbonBox.setLayoutY(400);


	    // Add labels for each link in the route
	    addRouteDetails(fastestBox, fastestRoute);
	    addRouteDetails(cheapestBox, cheapestRoute);
	    addRouteDetails(lowestCarbonBox, lowestCarbonRoute);

	    
	    pointsMap.values().forEach(point -> {
            // Scale or adjust these values as necessary to fit your AnchorPane
            double x = point.getLongitude() * scaleX + offsetX; // Example scaling
            double y = point.getLatitude() * scaleY + offsetY; // Example scaling

            Circle circle = new Circle(x, y, 5, Color.BLUE);
            Label label = new Label(point.getName());
            label.setLayoutX(x + 5); // Offset the label a bit from the circle
            label.setLayoutY(y);

            anchorPane.getChildren().addAll(circle, label);
        });
	    
	    // Add everything to the anchorPane
	    routeOptions.getChildren().addAll(fastestBox, cheapestBox, lowestCarbonBox);
	    
	    
			
	}
    
    
   
    


    
    
    private void setupButtonWithRouteLines(Button button, List<Line> routeLines, int gemLocation, Route route) {
        button.setLayoutY(550); // Common Y for simplicity
        button.setOnMouseEntered(e -> routeLines.forEach(line -> line.setStrokeWidth(10))); // Thicker on hover
        button.setOnMouseExited(e -> routeLines.forEach(line -> line.setStrokeWidth(5))); // Back to normal
        button.setOnAction(e -> collectGem(gemLocation, route));

        // Tooltip for additional route info (optional)
        Tooltip.install(button, new Tooltip("TEST")); // Assuming route.toString() gives meaningful info
    }
    */
    
    private void addRouteDetails(Route route) {
    	routeOptions.getChildren().clear();
    	routeOptions.getChildren().add(new Label("Route details:")); // Title for the route details section
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
        
        String routeSummary = String.format("Carbon: %d Cost: %d  Time: %d", route.getTotalCarbonFootprint(), route.getTotalCost(), route.getTotalTime());
        routeOptions.getChildren().add(new Label(routeSummary));
    }

    private void printSegment(VBox vbox, int startSegment, int endSegment, Transport transportType,
                              int cost, int carbon, int time) {
        String detailText = String.format("From %s to %s via %s - Cost: %d, Carbon: %d, Time: %d",
        		pointsMap.get(startSegment).getName(),  pointsMap.get(endSegment).getName(), transportType, cost, carbon, time);
        System.out.println(detailText);
        vbox.getChildren().add(new Label(detailText));
    }
    
    private void collectGem(int gemLocation, Route chosenRoute) {
        System.out.println("Collecting gem at " + gemLocation + " via chosen route.");
        routeOptions.getChildren().clear();
        
        // Update the player's location to the route's end point
        player.setLocation(gemLocation);

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
            // Optionally, update the GUI here to reflect the gem's collection and the route's effects

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
    
    public void updatePlayerStatus() {
        carbonBudgetLabel.setText("Carbon Budget: " + player.getCarbonBudget());
        timeBudgetLabel.setText("Time Budget: " + player.getTimeBudget());
        costBudgetLabel.setText("Cost Budget: " + player.getCostBudget());
    }
    
    public void showGameOverPopup(String message) {
        // Create a new Alert of type INFORMATION
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Game Over"); // Set the title of the popup window
        alert.setHeaderText(null); // Set the header text. Null means no header.
        alert.setContentText(message + "Gems Collected : " + player.getGemsCollected()); // Set the actual message to display

        // Show the alert and wait for the user to close it
        alert.showAndWait();
        Stage primaryStage = Main.getPrimaryStage();
        GameMenu gameMenu = new GameMenu(primaryStage);
        Scene menuScene = gameMenu.getGameMenuScene();

        
        primaryStage.setScene(menuScene);
        primaryStage.show();
    }
}

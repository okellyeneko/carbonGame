package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class Main extends Application {
	public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    private static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
    	Main.primaryStage = primaryStage;
        GameMenu gameMenu = new GameMenu(primaryStage);
        Scene menuScene = gameMenu.getGameMenuScene();

        primaryStage.setTitle("Game Menu");
        primaryStage.setScene(menuScene);
        primaryStage.show();
    }
    
    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}

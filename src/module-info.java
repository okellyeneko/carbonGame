module carbonGame {
    requires javafx.controls;
    requires javafx.media;  // Add this line to include the javafx.media module

    // Open your application package to the necessary JavaFX modules
    opens application to javafx.graphics, javafx.fxml;
}

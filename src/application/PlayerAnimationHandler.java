package application;

import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.application.Platform;
import javafx.scene.image.ImageView;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

public class PlayerAnimationHandler {
    private ImageView playerImageView;
    private double scaleX;
    private double scaleY;
    private Player player;
    private Runnable onFinished;
    private PathTransition currentTransition; // Store the current animation

    public PlayerAnimationHandler(ImageView playerImageView, double scaleX, double scaleY, Player player, Runnable onFinished) {
        this.playerImageView = playerImageView;
        this.scaleX = scaleX;
        this.scaleY = scaleY;
        this.player = player;
        this.onFinished = onFinished;
    }

    public void animatePlayerMovement(Point start, Point end) {
        stopCurrentAnimation(); // Ensure no previous animations are running

        Path path = new Path();
        path.getElements().add(new MoveTo(start.getLongitude() * scaleX, start.getLatitude() * scaleY));
        path.getElements().add(new LineTo(end.getLongitude() * scaleX, end.getLatitude() * scaleY));

        currentTransition = new PathTransition(Duration.seconds(1), path, playerImageView);
        currentTransition.setInterpolator(Interpolator.LINEAR);
        currentTransition.setOnFinished(event -> {
            if (onFinished != null) {
                Platform.runLater(onFinished); // Execute any additional specified tasks after the animation
            }
        });

        currentTransition.play();
    }

    public void stopCurrentAnimation() {
        if (currentTransition != null) {
            currentTransition.stop(); // Stop the current animation
            currentTransition.setOnFinished(null); // Clear any onFinish actions to avoid state inconsistencies
            currentTransition = null; // Clear the reference to the transition
        }
    }
    
    public void updateScale(double newScaleX, double newScaleY) {
        this.scaleX = newScaleX;
        this.scaleY = newScaleY;
    }
}

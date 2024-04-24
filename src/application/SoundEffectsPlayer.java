package application;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.net.URL;

public class SoundEffectsPlayer {

    private static MediaPlayer backgroundPlayer;  // Singleton instance of background media player

    public static void playSound(String soundFileName) {
        try {
            URL resource = SoundEffectsPlayer.class.getResource(soundFileName);
            if (resource == null) {
                System.err.println("Cannot find file: " + soundFileName);
                return;
            }
            Media sound = new Media(resource.toString());
            MediaPlayer mediaPlayer = new MediaPlayer(sound);
            mediaPlayer.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void playBackgroundMusic(String musicFileName, double volume) {
        try {
            URL resource = SoundEffectsPlayer.class.getResource(musicFileName);
            if (resource == null) {
                System.err.println("Cannot find file: " + musicFileName);
                return;
            }
            Media music = new Media(resource.toString());
            if (backgroundPlayer != null) {
                backgroundPlayer.stop();  // Stop currently playing music if any
            }
            backgroundPlayer = new MediaPlayer(music);
            backgroundPlayer.setVolume(volume);  // Set the volume (0.0 to 1.0)
            backgroundPlayer.setCycleCount(MediaPlayer.INDEFINITE);  // Loop indefinitely
            backgroundPlayer.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
}

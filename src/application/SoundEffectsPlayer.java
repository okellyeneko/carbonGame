package application;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.net.URL;

public class SoundEffectsPlayer {

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
}

package application;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class HighScoreManager {
    private String filePath = "highscore.txt"; // Path to the file where the high score will be saved

    
    public void writeHighScore(int score) {
        try (FileWriter fileWriter = new FileWriter(filePath, false)) { // false to overwrite the file
            fileWriter.write(String.valueOf(score));
            fileWriter.flush();
            
        } catch (IOException e) {
            System.out.println("An error occurred while writing the high score to the file.");
            e.printStackTrace();
        }
    }

    
    public int readHighScore() {
        try (Scanner scanner = new Scanner(new File(filePath))) {
            if (scanner.hasNextInt()) {
                return scanner.nextInt();
            }
        } catch (FileNotFoundException e) {
            System.out.println("The high score file was not found.");
            e.printStackTrace();
        }
        return 0; // Return 0 if there's no score or an error occurred
    }
    
}
package com.spaceblaster.controller;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import com.spaceblaster.util.ScoreManager;
import com.spaceblaster.util.ScoreManager.HighScoreEntry;
import java.util.List;

/**
 * Controller class responsible for displaying the high scores screen.
 * Loads and displays the top scores from the ScoreManager.
 * 
 * @author Space Blaster Team
 * @version 1.0
 */
public class HighScoreController {
    
    /** The primary stage (window) where the screen is displayed. */
    private Stage primaryStage;
    
    /** The width of the screen. */
    private int width;
    
    /** The height of the screen. */
    private int height;
    
    /** Callback to execute when returning to the main menu. */
    private Runnable onBack;

    /**
     * Constructs a new HighScoreController with the specified parameters.
     * 
     * @param primaryStage The primary stage (window)
     * @param width The width of the screen
     * @param height The height of the screen
     */
    public HighScoreController(Stage primaryStage, int width, int height) {
        this.primaryStage = primaryStage;
        this.width = width;
        this.height = height;
    }

    /**
     * Displays the high scores screen with a ranked list of top scores.
     * 
     * @param onBack A Runnable to execute when the back button is pressed
     */
    public void showHighScores(Runnable onBack) {
        this.onBack = onBack;
        
        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: black;");
        
        Text title = new Text("HIGH SCORES");
        title.setFont(Font.font("Monospace", 40));
        title.setStyle("-fx-fill: cyan;");
        
        VBox scoresBox = new VBox(10);
        scoresBox.setAlignment(Pos.CENTER);
        
        List<HighScoreEntry> scores = ScoreManager.loadHighScores();
        
        if (scores.isEmpty()) {
            Text emptyText = new Text("No scores yet! Play the game to set a record.");
            emptyText.setFont(Font.font("Monospace", 20));
            emptyText.setStyle("-fx-fill: gray;");
            scoresBox.getChildren().add(emptyText);
        } else {
            int rank = 1;
            for (HighScoreEntry entry : scores) {
                Text scoreText = new Text(String.format("%d. %s - %d", rank++, entry.getName(), entry.getScore()));
                scoreText.setFont(Font.font("Monospace", 20));
                scoreText.setStyle("-fx-fill: white;");
                scoresBox.getChildren().add(scoreText);
            }
        }
        
        Button backButton = new Button("BACK TO MENU");
        backButton.setStyle("-fx-font-size: 20px; -fx-padding: 10px 30px;");
        backButton.setOnAction(e -> onBack.run());
        
        root.getChildren().addAll(title, scoresBox, backButton);
        
        Scene scene = new Scene(root, width, height);
        primaryStage.setScene(scene);
    }
}
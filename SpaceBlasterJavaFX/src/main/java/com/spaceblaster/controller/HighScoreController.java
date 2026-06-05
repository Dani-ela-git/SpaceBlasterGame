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

public class HighScoreController {
    private Stage primaryStage;
    private int width;
    private int height;
    private Runnable onBack;
    
    public HighScoreController(Stage primaryStage, int width, int height) {
        this.primaryStage = primaryStage;
        this.width = width;
        this.height = height;
    }
    
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
        
        int rank = 1;
        for (HighScoreEntry entry : ScoreManager.loadHighScores()) {
            Text scoreText = new Text(String.format("%d. %s - %d", rank++, entry.getName(), entry.getScore()));
            scoreText.setFont(Font.font("Monospace", 20));
            scoreText.setStyle("-fx-fill: white;");
            scoresBox.getChildren().add(scoreText);
        }
        
        Button backButton = new Button("BACK TO MENU");
        backButton.setStyle("-fx-font-size: 20px; -fx-padding: 10px 30px;");
        backButton.setOnAction(e -> onBack.run());
        
        root.getChildren().addAll(title, scoresBox, backButton);
        
        Scene scene = new Scene(root, width, height);
        primaryStage.setScene(scene);
    }
}
package com.spaceblaster.controller;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MenuController {
    private Stage primaryStage;
    private int width;
    private int height;
    
    public MenuController(Stage primaryStage, int width, int height) {
        this.primaryStage = primaryStage;
        this.width = width;
        this.height = height;
    }
    
    public void showMainMenu() {
        VBox root = new VBox(30);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: black;");
        
        Text title = new Text("SPACE BLASTER");
        title.setFont(Font.font("Monospace", 60));
        title.setStyle("-fx-fill: cyan; -fx-font-weight: bold;");
        
        Button startButton = new Button("START GAME");
        startButton.setStyle("-fx-font-size: 24px; -fx-padding: 10px 40px; -fx-background-color: transparent; -fx-text-fill: white; -fx-border-color: cyan; -fx-border-width: 2px;");
        startButton.setOnMouseEntered(e -> startButton.setStyle("-fx-font-size: 24px; -fx-padding: 10px 40px; -fx-background-color: rgba(0,255,255,0.2); -fx-text-fill: cyan; -fx-border-color: cyan; -fx-border-width: 2px;"));
        startButton.setOnMouseExited(e -> startButton.setStyle("-fx-font-size: 24px; -fx-padding: 10px 40px; -fx-background-color: transparent; -fx-text-fill: white; -fx-border-color: cyan; -fx-border-width: 2px;"));
        startButton.setOnAction(e -> showInstructions());
        
        Button highScoresButton = new Button("HIGH SCORES");
        highScoresButton.setStyle("-fx-font-size: 24px; -fx-padding: 10px 40px; -fx-background-color: transparent; -fx-text-fill: white; -fx-border-color: cyan; -fx-border-width: 2px;");
        highScoresButton.setOnMouseEntered(e -> highScoresButton.setStyle("-fx-font-size: 24px; -fx-padding: 10px 40px; -fx-background-color: rgba(0,255,255,0.2); -fx-text-fill: cyan; -fx-border-color: cyan; -fx-border-width: 2px;"));
        highScoresButton.setOnMouseExited(e -> highScoresButton.setStyle("-fx-font-size: 24px; -fx-padding: 10px 40px; -fx-background-color: transparent; -fx-text-fill: white; -fx-border-color: cyan; -fx-border-width: 2px;"));
        highScoresButton.setOnAction(e -> showHighScores());
        
        Button exitButton = new Button("EXIT");
        exitButton.setStyle("-fx-font-size: 24px; -fx-padding: 10px 40px; -fx-background-color: transparent; -fx-text-fill: white; -fx-border-color: cyan; -fx-border-width: 2px;");
        exitButton.setOnMouseEntered(e -> exitButton.setStyle("-fx-font-size: 24px; -fx-padding: 10px 40px; -fx-background-color: rgba(0,255,255,0.2); -fx-text-fill: cyan; -fx-border-color: cyan; -fx-border-width: 2px;"));
        exitButton.setOnMouseExited(e -> exitButton.setStyle("-fx-font-size: 24px; -fx-padding: 10px 40px; -fx-background-color: transparent; -fx-text-fill: white; -fx-border-color: cyan; -fx-border-width: 2px;"));
        exitButton.setOnAction(e -> System.exit(0));
        
        root.getChildren().addAll(title, startButton, highScoresButton, exitButton);
        
        Scene scene = new Scene(root, width, height);
        primaryStage.setScene(scene);
    }
    
    private void showInstructions() {
        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: black;");
        
        Text title = new Text("INSTRUCTIONS");
        title.setFont(Font.font("Monospace", 40));
        title.setStyle("-fx-fill: cyan;");
        
        Text controls = new Text(
            "CONTROLS:\n" +
            "↑ ↓ ← → - Move your ship\n" +
            "SPACE - Shoot\n\n" +
            "GAMEPLAY:\n" +
            "Destroy asteroids and enemies to earn points\n" +
            "Avoid enemy fire and collisions\n" +
            "You have 3 lives\n" +
            "Complete all 4 levels to win!\n\n" +
            "Press SPACE to start"
        );
        controls.setFont(Font.font("Monospace", 18));
        controls.setStyle("-fx-fill: white;");
        
        root.getChildren().addAll(title, controls);
        
        Scene scene = new Scene(root, width, height);
        scene.setOnKeyPressed(e -> {
            switch(e.getCode()) {
                case SPACE -> startGame();
            }
        });
        
        primaryStage.setScene(scene);
    }
    
    private void startGame() {
        GameController gameController = new GameController(primaryStage, width, height);
        gameController.startGame();
    }
    
    private void showHighScores() {
        HighScoreController highScoreController = new HighScoreController(primaryStage, width, height);
        highScoreController.showHighScores(this::showMainMenu);
    }
}
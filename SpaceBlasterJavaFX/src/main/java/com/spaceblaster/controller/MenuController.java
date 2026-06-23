package com.spaceblaster.controller;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Controller class responsible for managing all menu screens of the game.
 * Handles navigation between the main menu, instructions, and high score screens.
 * 
 * @author Space Blaster Team
 * @version 1.0
 */
public class MenuController {
    
    /** The primary stage (window) where scenes are displayed. */
    private Stage primaryStage;
    
    /** The width of the game window. */
    private int width;
    
    /** The height of the game window. */
    private int height;
    
    /**
     * Constructs a new MenuController with the specified stage and dimensions.
     * 
     * @param primaryStage The primary stage (window) for the application
     * @param width The width of the game window
     * @param height The height of the game window
     */
    public MenuController(Stage primaryStage, int width, int height) {
        this.primaryStage = primaryStage;
        this.width = width;
        this.height = height;
    }
    
    /**
     * Displays the main menu screen with title, start button, high scores button,
     * and exit button. Applies cyberpunk-style visual styling.
     */
    public void showMainMenu() {
        VBox root = new VBox(30);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: black;");
        
        Text title = new Text("SPACE BLASTER");
        title.setFont(Font.font("Monospace", 60));
        title.setStyle("-fx-fill: cyan; -fx-font-weight: bold;");
        
        Button startButton = createStyledButton("START GAME");
        startButton.setOnAction(e -> showInstructions());
        
        Button highScoresButton = createStyledButton("HIGH SCORES");
        highScoresButton.setOnAction(e -> showHighScores());
        
        Button exitButton = createStyledButton("EXIT");
        exitButton.setOnAction(e -> System.exit(0));
        
        root.getChildren().addAll(title, startButton, highScoresButton, exitButton);
        
        Scene scene = new Scene(root, width, height);
        primaryStage.setScene(scene);
    }
    
    /**
     * Creates a styled button with hover effects for the menu.
     * 
     * @param text The text to display on the button
     * @return A styled Button instance
     */
    private Button createStyledButton(String text) {
        Button button = new Button(text);
        String baseStyle = "-fx-font-size: 24px; -fx-padding: 10px 40px; "
                         + "-fx-background-color: transparent; -fx-text-fill: white; "
                         + "-fx-border-color: cyan; -fx-border-width: 2px;";
        String hoverStyle = "-fx-font-size: 24px; -fx-padding: 10px 40px; "
                          + "-fx-background-color: rgba(0,255,255,0.2); "
                          + "-fx-text-fill: cyan; -fx-border-color: cyan; "
                          + "-fx-border-width: 2px;";
        
        button.setStyle(baseStyle);
        button.setOnMouseEntered(e -> button.setStyle(hoverStyle));
        button.setOnMouseExited(e -> button.setStyle(baseStyle));
        
        return button;
    }
    
    /**
     * Displays the game instructions screen with controls and gameplay information.
     * Pressing SPACE starts the game.
     */
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
            "Enemies start shooting from Level 3\n" +
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
                default -> { /* Ignore other keys */ }
            }
        });
        
        primaryStage.setScene(scene);
    }
    
    /**
     * Starts the game by creating a new GameController instance.
     */
    private void startGame() {
        GameController gameController = new GameController(primaryStage, width, height);
        gameController.startGame();
    }
    
    /**
     * Displays the high scores screen by delegating to HighScoreController.
     */
    private void showHighScores() {
        HighScoreController highScoreController = new HighScoreController(primaryStage, width, height);
        highScoreController.showHighScores(this::showMainMenu);
    }
}
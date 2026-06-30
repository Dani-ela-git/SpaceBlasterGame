package com.spaceblaster;

import javafx.application.Application;
import javafx.stage.Stage;
import com.spaceblaster.controller.MenuController;

/**
 * The main entry point for the Space Blaster game application.
 * This class extends JavaFX Application and initializes the game window.
 * 
 * @author Space Blaster Team
 * @version 1.0
 * @see Application
 * @see MenuController
 */

public class Main extends Application {
    
    /** The title displayed in the game window. */
    private static final String TITLE = "Space Blaster";
    
    /** The fixed width of the game window in pixels. */
    private static final int WIDTH = 1024;
    
    /** The fixed height of the game window in pixels. */
    private static final int HEIGHT = 768;
    
    /** The controller responsible for managing the main menu. */
    private MenuController menuController;
    
    /**
     * Starts the JavaFX application by initializing the primary stage,
     * setting up the window properties, and displaying the main menu.
     * 
     * @param primaryStage The primary stage (window) provided by JavaFX
     */
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle(TITLE);
        primaryStage.setResizable(false);
        
        menuController = new MenuController(primaryStage, WIDTH, HEIGHT);
        menuController.showMainMenu();
        
        primaryStage.show();
    }
    
    /**
     * The main method that launches the JavaFX application.
     * 
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    /**
     * Gets the fixed width of the game window.
     * 
     * @return The game width in pixels (1024)
     */
    public static int getGameWidth() {
        return WIDTH;
    }
    
    /**
     * Gets the fixed height of the game window.
     * 
     * @return The game height in pixels (768)
     */
    public static int getGameHeight() {
        return HEIGHT;
    }
}
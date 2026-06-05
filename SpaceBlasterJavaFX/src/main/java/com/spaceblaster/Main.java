package com.spaceblaster;

import javafx.application.Application;
import javafx.stage.Stage;
import com.spaceblaster.controller.MenuController;

public class Main extends Application {
    private static final String TITLE = "Space Blaster";
    private static final int WIDTH = 1024;
    private static final int HEIGHT = 768;
    
    private MenuController menuController;
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle(TITLE);
        primaryStage.setResizable(false);
        
        menuController = new MenuController(primaryStage, WIDTH, HEIGHT);
        menuController.showMainMenu();
        
        primaryStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
    public static int getGameWidth() {
        return WIDTH;
    }
    
    public static int getGameHeight() {
        return HEIGHT;
    }
}
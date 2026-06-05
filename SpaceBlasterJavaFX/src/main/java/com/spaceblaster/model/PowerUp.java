package com.spaceblaster.model;

import javafx.scene.shape.Rectangle;

public class PowerUp {
    private double x, y;
    private double width = 20;
    private double height = 20;
    private double speedY = 3;
    private PowerUpType type;
    
    public enum PowerUpType {
        RAPID_FIRE,
        SHIELD,
        SCORE_MULTIPLIER,
        EXTRA_LIFE
    }
    
    public PowerUp(double x, double y, PowerUpType type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }
    
    public void update() {
        y += speedY;
    }
    
    public boolean isOffScreen(double screenHeight) {
        return y > screenHeight;
    }
    
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
    
    // Getters
    public double getX() { return x; }
    public double getY() { return y; }
    public double getWidth() { return width; }
    public double getHeight() { return height; }
    public PowerUpType getType() { return type; }
}
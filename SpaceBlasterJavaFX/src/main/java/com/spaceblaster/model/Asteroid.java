package com.spaceblaster.model;

import javafx.scene.shape.Rectangle;

public class Asteroid {
    private double x, y;
    private double width = 30;
    private double height = 30;
    private double speedY;
    private int points = 100;
    
    public Asteroid(double x, double y, double speedY) {
        this.x = x;
        this.y = y;
        this.speedY = speedY;
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
    public int getPoints() { return points; }
}
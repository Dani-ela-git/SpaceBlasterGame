package com.spaceblaster.model;

import javafx.scene.shape.Rectangle;

public class Bullet {
    private double x, y;
    private double width = 4;
    private double height = 10;
    private double speedY = -8; // Para cima
    private boolean fromPlayer;
    
    public Bullet(double x, double y, boolean fromPlayer) {
        this.x = x;
        this.y = y;
        this.fromPlayer = fromPlayer;
    }
    
    public Bullet(double x, double y, double speedY, boolean fromPlayer) {
        this.x = x;
        this.y = y;
        this.speedY = speedY;
        this.fromPlayer = fromPlayer;
    }
    
    public void update() {
        y += speedY;
    }
    
    public boolean isOffScreen(double screenHeight) {
        return y < 0 || y > screenHeight;
    }
    
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
    
    // Getters
    public double getX() { return x; }
    public double getY() { return y; }
    public double getWidth() { return width; }
    public double getHeight() { return height; }
    public boolean isFromPlayer() { return fromPlayer; }
}
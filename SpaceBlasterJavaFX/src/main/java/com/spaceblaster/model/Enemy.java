package com.spaceblaster.model;

import javafx.scene.shape.Rectangle;

public class Enemy {
    private double x, y;
    private double width = 35;
    private double height = 35;
    private double speedX, speedY;
    private int points = 150;
    private long lastShotTime;
    private static final long SHOT_DELAY = 1500;
    
    public Enemy(double x, double y, double speedX, double speedY) {
        this.x = x;
        this.y = y;
        this.speedX = speedX;
        this.speedY = speedY;
        this.lastShotTime = System.currentTimeMillis();
    }
    
    public void update() {
        x += speedX;
        y += speedY;
    }
    
    public boolean canShoot() {
        long now = System.currentTimeMillis();
        if (now - lastShotTime > SHOT_DELAY) {
            lastShotTime = now;
            return true;
        }
        return false;
    }
    
    public boolean isOffScreen(double screenWidth, double screenHeight) {
        return x < -50 || x > screenWidth + 50 || y > screenHeight + 50;
    }
    
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public int getEnemiesSpawned() {
    return 0; // Placeholder - não usado diretamente
    }
    
    // Getters
    public double getX() { return x; }
    public double getY() { return y; }
    public double getWidth() { return width; }
    public double getHeight() { return height; }
    public int getPoints() { return points; }
    public double getBulletX() { return x + width/2; }
    public double getBulletY() { return y + height; }
}
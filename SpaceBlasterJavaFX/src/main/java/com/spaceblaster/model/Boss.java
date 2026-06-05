package com.spaceblaster.model;

import javafx.scene.shape.Rectangle;

public class Boss {
    private double x, y;
    private double width = 80;
    private double height = 80;
    private int hitPoints = 10;
    private int maxHitPoints = 10;
    private double speedX = 2;
    private long lastShotTime;
    private static final long SHOT_DELAY = 800;
    
    public Boss(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    public void update(double screenWidth) {
        x += speedX;
        if (x < 50 || x > screenWidth - width - 50) {
            speedX *= -1;
        }
    }
    
    public boolean canShoot() {
        long now = System.currentTimeMillis();
        if (now - lastShotTime > SHOT_DELAY) {
            lastShotTime = now;
            return true;
        }
        return false;
    }
    
    public void hit() {
        hitPoints--;
    }
    
    public boolean isDefeated() {
        return hitPoints <= 0;
    }
    
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
    
    // Getters
    public double getX() { return x; }
    public double getY() { return y; }
    public double getWidth() { return width; }
    public double getHeight() { return height; }
    public int getHitPoints() { return hitPoints; }
    public int getMaxHitPoints() { return maxHitPoints; }
    public double getBulletX() { return x + width/2; }
    public double getBulletY() { return y + height; }
}
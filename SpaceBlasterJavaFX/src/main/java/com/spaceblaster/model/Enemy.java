package com.spaceblaster.model;

import javafx.scene.shape.Rectangle;

/**
 * Represents an enemy spaceship in the game.
 * Enemies move downward and can shoot bullets at the player (from Level 3 onwards).
 * 
 * @author Space Blaster Team
 * @version 1.0
 */
public class Enemy {
    
    /** The x-coordinate of the enemy. */
    private double x;
    
    /** The y-coordinate of the enemy. */
    private double y;
    
    /** The width of the enemy in pixels. */
    private double width = 35;
    
    /** The height of the enemy in pixels. */
    private double height = 35;
    
    /** The horizontal movement speed. */
    private double speedX;
    
    /** The vertical movement speed. */
    private double speedY;
    
    /** The points awarded for destroying this enemy. */
    private int points = 150;
    
    /** The timestamp of the last shot fired. */
    private long lastShotTime;
    
    /** The delay between shots in milliseconds. */
    private static final long SHOT_DELAY = 1500;

    /**
     * Constructs a new Enemy at the specified position with the given speeds.
     * 
     * @param x The initial x-coordinate
     * @param y The initial y-coordinate
     * @param speedX The horizontal movement speed
     * @param speedY The vertical movement speed
     */
    public Enemy(double x, double y, double speedX, double speedY) {
        this.x = x;
        this.y = y;
        this.speedX = speedX;
        this.speedY = speedY;
        this.lastShotTime = System.currentTimeMillis();
    }

    /**
     * Updates the enemy's position by moving both horizontally and vertically.
     */
    public void update() {
        x += speedX;
        y += speedY;
    }

    /**
     * Checks if the enemy can shoot based on the cooldown timer.
     * 
     * @return true if the enemy can shoot
     */
    public boolean canShoot() {
        long now = System.currentTimeMillis();
        if (now - lastShotTime > SHOT_DELAY) {
            lastShotTime = now;
            return true;
        }
        return false;
    }

    /**
     * Checks if the enemy has moved off the screen.
     * 
     * @param screenWidth The width of the screen
     * @param screenHeight The height of the screen
     * @return true if the enemy is off screen
     */
    public boolean isOffScreen(double screenWidth, double screenHeight) {
        return x < -50 || x > screenWidth + 50 || y > screenHeight + 50;
    }

    /**
     * Gets the bounding rectangle of the enemy for collision detection.
     * 
     * @return A Rectangle representing the enemy's bounds
     */
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    /**
     * Gets the x-coordinate of the enemy.
     * 
     * @return The x-coordinate
     */
    public double getX() { 
        return x; 
    }

    /**
     * Gets the y-coordinate of the enemy.
     * 
     * @return The y-coordinate
     */
    public double getY() { 
        return y; 
    }

    /**
     * Gets the width of the enemy.
     * 
     * @return The width in pixels
     */
    public double getWidth() { 
        return width; 
    }

    /**
     * Gets the height of the enemy.
     * 
     * @return The height in pixels
     */
    public double getHeight() { 
        return height; 
    }

    /**
     * Gets the points awarded for destroying this enemy.
     * 
     * @return The point value
     */
    public int getPoints() { 
        return points; 
    }

    /**
     * Gets the x-coordinate where the enemy's bullet should spawn.
     * 
     * @return The bullet x-coordinate
     */
    public double getBulletX() { 
        return x + width / 2; 
    }

    /**
     * Gets the y-coordinate where the enemy's bullet should spawn.
     * 
     * @return The bullet y-coordinate
     */
    public double getBulletY() { 
        return y + height; 
    }
}
package com.spaceblaster.model;

import javafx.scene.shape.Rectangle;

/**
 * Represents a bullet or projectile in the game.
 * Bullets can be fired by the player or by enemies/boss.
 * 
 * @author Space Blaster Team
 * @version 1.0
 */
public class Bullet {
    
    /** The x-coordinate of the bullet. */
    private double x;
    
    /** The y-coordinate of the bullet. */
    private double y;
    
    /** The width of the bullet in pixels. */
    private double width = 4;
    
    /** The height of the bullet in pixels. */
    private double height = 10;
    
    /** The vertical speed of the bullet (negative for upward, positive for downward). */
    private double speedY = -8;
    
    /** Whether this bullet was fired by the player. */
    private boolean fromPlayer;

    /**
     * Constructs a new Bullet at the specified position.
     * The default speed is -8 (upward).
     * 
     * @param x The x-coordinate of the bullet
     * @param y The y-coordinate of the bullet
     * @param fromPlayer true if fired by the player, false if fired by enemy
     */
    public Bullet(double x, double y, boolean fromPlayer) {
        this.x = x;
        this.y = y;
        this.fromPlayer = fromPlayer;
    }

    /**
     * Constructs a new Bullet at the specified position with custom speed.
     * 
     * @param x The x-coordinate of the bullet
     * @param y The y-coordinate of the bullet
     * @param speedY The vertical speed of the bullet
     * @param fromPlayer true if fired by the player, false if fired by enemy
     */
    public Bullet(double x, double y, double speedY, boolean fromPlayer) {
        this.x = x;
        this.y = y;
        this.speedY = speedY;
        this.fromPlayer = fromPlayer;
    }

    /**
     * Updates the bullet's position by moving it vertically.
     */
    public void update() {
        y += speedY;
    }

    /**
     * Checks if the bullet has moved off the screen.
     * 
     * @param screenHeight The height of the screen
     * @return true if the bullet is off screen
     */
    public boolean isOffScreen(double screenHeight) {
        return y < 0 || y > screenHeight;
    }

    /**
     * Gets the bounding rectangle of the bullet for collision detection.
     * 
     * @return A Rectangle representing the bullet's bounds
     */
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    /**
     * Gets the x-coordinate of the bullet.
     * 
     * @return The x-coordinate
     */
    public double getX() { 
        return x; 
    }

    /**
     * Gets the y-coordinate of the bullet.
     * 
     * @return The y-coordinate
     */
    public double getY() { 
        return y; 
    }

    /**
     * Gets the width of the bullet.
     * 
     * @return The width in pixels
     */
    public double getWidth() { 
        return width; 
    }

    /**
     * Gets the height of the bullet.
     * 
     * @return The height in pixels
     */
    public double getHeight() { 
        return height; 
    }

    /**
     * Checks if this bullet was fired by the player.
     * 
     * @return true if fired by the player, false if fired by enemy
     */
    public boolean isFromPlayer() { 
        return fromPlayer; 
    }
}
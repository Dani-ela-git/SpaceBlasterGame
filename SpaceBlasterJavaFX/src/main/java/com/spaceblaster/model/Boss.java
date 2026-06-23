package com.spaceblaster.model;

import javafx.scene.shape.Rectangle;

/**
 * Represents the boss enemy that appears in Level 4.
 * The boss has multiple hit points, moves horizontally, and shoots frequently.
 * 
 * @author Space Blaster Team
 * @version 1.0
 */
public class Boss {
    
    /** The x-coordinate of the boss. */
    private double x;
    
    /** The y-coordinate of the boss. */
    private double y;
    
    /** The width of the boss in pixels. */
    private double width = 80;
    
    /** The height of the boss in pixels. */
    private double height = 80;
    
    /** The current hit points of the boss. */
    private int hitPoints = 10;
    
    /** The maximum hit points of the boss. */
    private int maxHitPoints = 10;
    
    /** The horizontal movement speed. */
    private double speedX = 2;
    
    /** The timestamp of the last shot fired. */
    private long lastShotTime;
    
    /** The delay between shots in milliseconds. */
    private static final long SHOT_DELAY = 800;

    /**
     * Constructs a new Boss at the specified position.
     * 
     * @param x The initial x-coordinate
     * @param y The initial y-coordinate
     */
    public Boss(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Updates the boss's position by moving it horizontally.
     * The boss bounces off the screen edges.
     * 
     * @param screenWidth The width of the screen
     */
    public void update(double screenWidth) {
        x += speedX;
        if (x < 50 || x > screenWidth - width - 50) {
            speedX *= -1;
        }
    }

    /**
     * Checks if the boss can shoot based on the cooldown timer.
     * 
     * @return true if the boss can shoot
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
     * Reduces the boss's hit points by 1 when hit.
     */
    public void hit() {
        hitPoints--;
    }

    /**
     * Checks if the boss has been defeated.
     * 
     * @return true if the boss's hit points are 0 or less
     */
    public boolean isDefeated() {
        return hitPoints <= 0;
    }

    /**
     * Gets the bounding rectangle of the boss for collision detection.
     * 
     * @return A Rectangle representing the boss's bounds
     */
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    /**
     * Gets the x-coordinate of the boss.
     * 
     * @return The x-coordinate
     */
    public double getX() { 
        return x; 
    }

    /**
     * Gets the y-coordinate of the boss.
     * 
     * @return The y-coordinate
     */
    public double getY() { 
        return y; 
    }

    /**
     * Gets the width of the boss.
     * 
     * @return The width in pixels
     */
    public double getWidth() { 
        return width; 
    }

    /**
     * Gets the height of the boss.
     * 
     * @return The height in pixels
     */
    public double getHeight() { 
        return height; 
    }

    /**
     * Gets the current hit points of the boss.
     * 
     * @return The current hit points
     */
    public int getHitPoints() { 
        return hitPoints; 
    }

    /**
     * Gets the maximum hit points of the boss.
     * 
     * @return The maximum hit points
     */
    public int getMaxHitPoints() { 
        return maxHitPoints; 
    }

    /**
     * Gets the x-coordinate where the boss's bullet should spawn.
     * 
     * @return The bullet x-coordinate
     */
    public double getBulletX() { 
        return x + width / 2; 
    }

    /**
     * Gets the y-coordinate where the boss's bullet should spawn.
     * 
     * @return The bullet y-coordinate
     */
    public double getBulletY() { 
        return y + height; 
    }
}
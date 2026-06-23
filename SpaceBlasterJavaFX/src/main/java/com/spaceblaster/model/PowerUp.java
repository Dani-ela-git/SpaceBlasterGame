package com.spaceblaster.model;

import javafx.scene.shape.Rectangle;

/**
 * Represents a power-up item that can be collected by the player.
 * Power-ups provide temporary or permanent bonuses to the player.
 * 
 * @author Space Blaster Team
 * @version 1.0
 */
public class PowerUp {
    
    /** Enumeration of available power-up types. */
    public enum PowerUpType {
        /** Increases firing speed for a duration. */
        RAPID_FIRE,
        
        /** Provides temporary invincibility. */
        SHIELD,
        
        /** Adds bonus points immediately. */
        SCORE_MULTIPLIER,
        
        /** Grants an extra life. */
        EXTRA_LIFE
    }
    
    /** The x-coordinate of the power-up. */
    private double x;
    
    /** The y-coordinate of the power-up. */
    private double y;
    
    /** The width of the power-up in pixels. */
    private double width = 20;
    
    /** The height of the power-up in pixels. */
    private double height = 20;
    
    /** The falling speed of the power-up. */
    private double speedY = 3;
    
    /** The type of this power-up. */
    private PowerUpType type;

    /**
     * Constructs a new PowerUp at the specified position with the given type.
     * 
     * @param x The x-coordinate of the power-up
     * @param y The y-coordinate of the power-up
     * @param type The type of power-up
     */
    public PowerUp(double x, double y, PowerUpType type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }

    /**
     * Updates the power-up's position by moving it downward.
     */
    public void update() {
        y += speedY;
    }

    /**
     * Checks if the power-up has moved off the bottom of the screen.
     * 
     * @param screenHeight The height of the screen
     * @return true if the power-up is off screen
     */
    public boolean isOffScreen(double screenHeight) {
        return y > screenHeight;
    }

    /**
     * Gets the bounding rectangle of the power-up for collision detection.
     * 
     * @return A Rectangle representing the power-up's bounds
     */
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    /**
     * Gets the x-coordinate of the power-up.
     * 
     * @return The x-coordinate
     */
    public double getX() { 
        return x; 
    }

    /**
     * Gets the y-coordinate of the power-up.
     * 
     * @return The y-coordinate
     */
    public double getY() { 
        return y; 
    }

    /**
     * Gets the width of the power-up.
     * 
     * @return The width in pixels
     */
    public double getWidth() { 
        return width; 
    }

    /**
     * Gets the height of the power-up.
     * 
     * @return The height in pixels
     */
    public double getHeight() { 
        return height; 
    }

    /**
     * Gets the type of this power-up.
     * 
     * @return The power-up type
     */
    public PowerUpType getType() { 
        return type; 
    }
}
package com.spaceblaster.model;

import javafx.scene.shape.Rectangle;

/**
 * Represents the player's ship in the game.
 * Manages position, movement, lives, invincibility, and power-up states.
 * 
 * @author Space Blaster Team
 * @version 1.0
 */
public class Player {
    
    /** The x-coordinate of the player ship. */
    private double x;
    
    /** The y-coordinate of the player ship. */
    private double y;
    
    /** The width of the player ship in pixels. */
    private double width = 40;
    
    /** The height of the player ship in pixels. */
    private double height = 40;
    
    /** The movement speed of the player ship. */
    private double speed = 5;
    
    /** The number of lives remaining. */
    private int lives = 3;
    
    /** Whether the player is invincible (after being hit). */
    private boolean invincible = false;
    
    /** The timestamp when invincibility expires. */
    private long invincibleEndTime;
    
    /** Whether rapid fire is active. */
    private boolean rapidFireActive = false;
    
    /** The timestamp when rapid fire expires. */
    private long rapidFireEndTime;
    
    /** Whether shield is active. */
    private boolean shieldActive = false;
    
    /** The timestamp when shield expires. */
    private long shieldEndTime;

    /**
     * Constructs a new Player with the specified starting position.
     * 
     * @param x The initial x-coordinate
     * @param y The initial y-coordinate
     */
    public Player(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Moves the player to the left.
     */
    public void moveLeft() {
        x = Math.max(10, x - speed);
    }

    /**
     * Moves the player to the right.
     * 
     * @param screenWidth The width of the screen to constrain movement
     */
    public void moveRight(double screenWidth) {
        x = Math.min(screenWidth - width - 10, x + speed);
    }

    /**
     * Moves the player upward.
     */
    public void moveUp() {
        y = Math.max(10, y - speed);
    }

    /**
     * Moves the player downward.
     * 
     * @param screenHeight The height of the screen to constrain movement
     */
    public void moveDown(double screenHeight) {
        y = Math.min(screenHeight - height - 10, y + speed);
    }

    /**
     * Applies damage to the player if not invincible or shielded.
     * Reduces lives by 1 and activates invincibility for 2 seconds.
     */
    public void hit() {
        if (!invincible && !shieldActive) {
            lives--;
            invincible = true;
            invincibleEndTime = System.currentTimeMillis() + 2000;
        }
    }

    /**
     * Updates the player's state, checking for expired power-ups and invincibility.
     */
    public void update() {
        long now = System.currentTimeMillis();
        if (invincible && now > invincibleEndTime) {
            invincible = false;
        }
        if (rapidFireActive && now > rapidFireEndTime) {
            rapidFireActive = false;
        }
        if (shieldActive && now > shieldEndTime) {
            shieldActive = false;
        }
    }

    /**
     * Activates the rapid fire power-up for the specified duration.
     * 
     * @param duration The duration in milliseconds
     */
    public void activateRapidFire(long duration) {
        this.rapidFireActive = true;
        this.rapidFireEndTime = System.currentTimeMillis() + duration;
    }

    /**
     * Activates the shield power-up for the specified duration.
     * 
     * @param duration The duration in milliseconds
     */
    public void activateShield(long duration) {
        this.shieldActive = true;
        this.shieldEndTime = System.currentTimeMillis() + duration;
    }

    /**
     * Checks if the shield is active.
     * 
     * @return true if shield is active
     */
    public boolean hasShield() {
        return shieldActive;
    }

    /**
     * Checks if rapid fire is active.
     * 
     * @return true if rapid fire is active
     */
    public boolean hasRapidFire() {
        return rapidFireActive;
    }

    /**
     * Gets the bounding rectangle of the player for collision detection.
     * 
     * @return A Rectangle representing the player's bounds
     */
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    /**
     * Sets the number of lives.
     * 
     * @param lives The number of lives to set
     */
    public void setLives(int lives) {
        this.lives = lives;
    }

    /**
     * Gets the x-coordinate of the player.
     * 
     * @return The x-coordinate
     */
    public double getX() {
        return x;
    }

    /**
     * Gets the y-coordinate of the player.
     * 
     * @return The y-coordinate
     */
    public double getY() {
        return y;
    }

    /**
     * Gets the width of the player.
     * 
     * @return The width in pixels
     */
    public double getWidth() {
        return width;
    }

    /**
     * Gets the height of the player.
     * 
     * @return The height in pixels
     */
    public double getHeight() {
        return height;
    }

    /**
     * Gets the number of lives.
     * 
     * @return The number of lives
     */
    public int getLives() {
        return lives;
    }

    /**
     * Checks if the player is invincible.
     * 
     * @return true if invincible
     */
    public boolean isInvincible() {
        return invincible;
    }
}
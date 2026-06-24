package com.spaceblaster.model;

import javafx.scene.shape.Rectangle;

/**
 * Represents an asteroid obstacle in the game.
 * Asteroids fall from the top of the screen and can be destroyed by player bullets.
 * 
 * @author Space Blaster Team
 * @version 1.0
 */
public class Asteroid {
    
    /** The x-coordinate of the asteroid. */
    private double x;
    
    /** The y-coordinate of the asteroid. */
    private double y;
    
    /** The width of the asteroid in pixels. */
    private double width = 30;
    
    /** The height of the asteroid in pixels. */
    private double height = 30;
    
    /** The falling speed of the asteroid. */
    private double speedY;
    
    /** The points awarded for destroying this asteroid. */
    private int points = 100;

    /**
     * Constructs a new Asteroid at the specified position with the given speed.
     * 
     * @param x The initial x-coordinate
     * @param y The initial y-coordinate (usually above the screen)
     * @param speedY The falling speed
     */
    public Asteroid(double x, double y, double speedY) {
        this.x = x;
        this.y = y;
        this.speedY = speedY;
    }

    /**
     * Updates the asteroid's position by moving it downward.
     */
    public void update() {
        y += speedY;
    }

    /**
     * Checks if the asteroid has moved off the bottom of the screen.
     * 
     * @param screenHeight The height of the screen
     * @return true if the asteroid is off screen
     */
    public boolean isOffScreen(double screenHeight) {
        return y > screenHeight;
    }

    /**
     * Gets the bounding rectangle of the asteroid for collision detection.
     * The rectangle just apear if the image does not load, but it is used for collision detection.
     * 
     * @return A Rectangle representing the asteroid's bounds
     */
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    /**
     * Gets the x-coordinate of the asteroid.
     * 
     * @return The x-coordinate
     */
    public double getX() { 
        return x; 
    }

    /**
     * Gets the y-coordinate of the asteroid.
     * 
     * @return The y-coordinate
     */
    public double getY() { 
        return y; 
    }

    /**
     * Gets the width of the asteroid.
     * 
     * @return The width in pixels
     */
    public double getWidth() { 
        return width; 
    }

    /**
     * Gets the height of the asteroid.
     * 
     * @return The height in pixels
     */
    public double getHeight() { 
        return height; 
    }

    /**
     * Gets the points awarded for destroying this asteroid.
     * 
     * @return The point value
     */
    public int getPoints() { 
        return points; 
    }
}
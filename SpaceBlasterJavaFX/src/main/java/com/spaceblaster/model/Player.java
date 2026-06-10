package com.spaceblaster.model;

import javafx.scene.shape.Rectangle;

public class Player {
    private double x, y;
    private double width = 40;
    private double height = 40;
    private double speed = 5;
    private int lives = 3;
    private boolean invincible = false;
    private long invincibleEndTime;
    private boolean rapidFireActive = false;
    private long rapidFireEndTime;
    private boolean shieldActive = false;
    private long shieldEndTime;

    public Player(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void moveLeft() {
        x = Math.max(10, x - speed);
    }

    public void moveRight(double screenWidth) {
        x = Math.min(screenWidth - width - 10, x + speed);
    }

    public void moveUp() {
        y = Math.max(10, y - speed);
    }

    public void moveDown(double screenHeight) {
        y = Math.min(screenHeight - height - 10, y + speed);
    }

    public void hit() {
        if (!invincible && !shieldActive) {
            lives--;
            invincible = true;
            invincibleEndTime = System.currentTimeMillis() + 2000;
        }
    }

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

    public void activateRapidFire(long duration) {
        this.rapidFireActive = true;
        this.rapidFireEndTime = System.currentTimeMillis() + duration;
    }

    public void activateShield(long duration) {
        this.shieldActive = true;
        this.shieldEndTime = System.currentTimeMillis() + duration;
    }

    public boolean hasShield() {
        return shieldActive;
    }

    public boolean hasRapidFire() {
        return rapidFireActive;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public int getLives() {
        return lives;
    }

    public boolean isInvincible() {
        return invincible;
    }
}
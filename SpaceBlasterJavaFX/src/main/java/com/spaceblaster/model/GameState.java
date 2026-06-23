package com.spaceblaster.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Represents the complete state of the game including all entities,
 * scores, levels, and game flags. This class manages the game data
 * and provides methods for updating and accessing game state.
 * 
 * @author Space Blaster Team
 * @version 1.0
 */
public class GameState {

    /** The player ship. */
    private Player player;
    
    /** List of active asteroids. */
    private List<Asteroid> asteroids;
    
    /** List of active enemies. */
    private List<Enemy> enemies;
    
    /** List of active bullets. */
    private List<Bullet> bullets;
    
    /** List of active power-ups. */
    private List<PowerUp> powerUps;
    
    /** The boss enemy (present only in level 4). */
    private Boss boss;

    /** Total game score. */
    private int score;
    
    /** Current level (1-4). */
    private int level;
    
    /** Whether the game is currently running. */
    private boolean gameRunning;
    
    /** Whether the current level is complete. */
    private boolean levelComplete;
    
    /** Whether the boss has been defeated. */
    private boolean bossDefeated;
    
    /** Number of enemies to spawn in the current level. */
    private int enemiesToSpawn;
    
    /** Number of enemies already spawned. */
    private int enemiesSpawned;
    
    /** Points accumulated in the current level. */
    private int pointsInCurrentLevel;

    /** Queue for safely adding asteroids during update. */
    private Queue<Asteroid> asteroidsToAdd;
    
    /** Queue for safely adding enemies during update. */
    private Queue<Enemy> enemiesToAdd;
    
    /** Queue for safely adding bullets during update. */
    private Queue<Bullet> bulletsToAdd;
    
    /** Queue for safely adding power-ups during update. */
    private Queue<PowerUp> powerUpsToAdd;

    /**
     * Constructs a new GameState with default values.
     * Initializes the player at the center-bottom of the screen,
     * starts at level 1, and sets up empty entity lists.
     */
    public GameState() {
        this.player = new Player(512, 650);
        this.asteroids = new ArrayList<>();
        this.enemies = new ArrayList<>();
        this.bullets = new ArrayList<>();
        this.powerUps = new ArrayList<>();

        this.score = 0;
        this.level = 1;
        this.gameRunning = true;
        this.levelComplete = false;
        this.bossDefeated = false;
        this.enemiesToSpawn = 5;
        this.enemiesSpawned = 0;
        this.pointsInCurrentLevel = 0;

        this.asteroidsToAdd = new ConcurrentLinkedQueue<>();
        this.enemiesToAdd = new ConcurrentLinkedQueue<>();
        this.bulletsToAdd = new ConcurrentLinkedQueue<>();
        this.powerUpsToAdd = new ConcurrentLinkedQueue<>();
    }

    /**
     * Determines whether the player should advance to the next level.
     * Requires 400 points in the current level.
     * 
     * @return true if the player has enough points to advance
     */
    public boolean shouldAdvanceLevel() {
        return pointsInCurrentLevel >= 400;
    }

    /**
     * Adds points to the total score and the current level counter.
     * 
     * @param points The number of points to add
     */
    public void addScore(int points) {
        this.score += points;
        this.pointsInCurrentLevel += points;
    }

    /**
     * Resets the game state for the next level.
     * Clears all entities and keeps any extra points beyond the 400 threshold.
     */
    public void resetForNextLevel() {
        int extraPoints = pointsInCurrentLevel - 400;
        
        asteroids.clear();
        enemies.clear();
        bullets.clear();
        powerUps.clear();
        levelComplete = false;
        enemiesSpawned = 0;
        
        this.pointsInCurrentLevel = Math.max(0, extraPoints);

        if (level == 4) {
            bossDefeated = false;
            boss = null;
        }
    }

    /**
     * Completely resets the game to its initial state.
     * Used when restarting the game.
     */
    public void resetGame() {
        this.score = 0;
        this.level = 1;
        this.gameRunning = true;
        this.levelComplete = false;
        this.bossDefeated = false;
        this.enemiesSpawned = 0;
        this.pointsInCurrentLevel = 0;
        this.player = new Player(512, 650);
        asteroids.clear();
        enemies.clear();
        bullets.clear();
        powerUps.clear();
        boss = null;
    }

    /**
     * Updates all entities in the game state.
     * Moves entities, removes off-screen entities, and processes add queues.
     */
    public void update() {
        player.update();

        asteroids.forEach(Asteroid::update);
        enemies.forEach(Enemy::update);
        bullets.forEach(Bullet::update);
        powerUps.forEach(PowerUp::update);

        if (boss != null && !bossDefeated) {
            boss.update(1024);
        }

        asteroids.removeIf(a -> a.isOffScreen(768));
        enemies.removeIf(e -> e.isOffScreen(1024, 768));
        bullets.removeIf(b -> b.isOffScreen(768));
        powerUps.removeIf(p -> p.isOffScreen(768));

        while (!asteroidsToAdd.isEmpty()) {
            asteroids.add(asteroidsToAdd.poll());
        }
        while (!enemiesToAdd.isEmpty()) {
            enemies.add(enemiesToAdd.poll());
        }
        while (!bulletsToAdd.isEmpty()) {
            bullets.add(bulletsToAdd.poll());
        }
        while (!powerUpsToAdd.isEmpty()) {
            powerUps.add(powerUpsToAdd.poll());
        }

        if (level == 4 && boss != null && boss.isDefeated() && !levelComplete) {
            bossDefeated = true;
            levelComplete = true;
        }
    }

    /**
     * Adds an asteroid to the add queue for safe addition.
     * 
     * @param asteroid The asteroid to add
     */
    public void addAsteroid(Asteroid asteroid) {
        asteroidsToAdd.add(asteroid);
    }

    /**
     * Adds an enemy to the add queue for safe addition.
     * 
     * @param enemy The enemy to add
     */
    public void addEnemy(Enemy enemy) {
        enemiesToAdd.add(enemy);
        enemiesSpawned++;
    }

    /**
     * Adds a bullet to the add queue for safe addition.
     * 
     * @param bullet The bullet to add
     */
    public void addBullet(Bullet bullet) {
        bulletsToAdd.add(bullet);
    }

    /**
     * Adds a power-up to the add queue for safe addition.
     * 
     * @param powerUp The power-up to add
     */
    public void addPowerUp(PowerUp powerUp) {
        powerUpsToAdd.add(powerUp);
    }

    /**
     * Gets the player ship.
     * 
     * @return The player object
     */
    public Player getPlayer() { 
        return player; 
    }
    
    /**
     * Gets the list of active asteroids.
     * 
     * @return List of asteroids
     */
    public List<Asteroid> getAsteroids() { 
        return asteroids; 
    }
    
    /**
     * Gets the list of active enemies.
     * 
     * @return List of enemies
     */
    public List<Enemy> getEnemies() { 
        return enemies; 
    }
    
    /**
     * Gets the list of active bullets.
     * 
     * @return List of bullets
     */
    public List<Bullet> getBullets() { 
        return bullets; 
    }
    
    /**
     * Gets the list of active power-ups.
     * 
     * @return List of power-ups
     */
    public List<PowerUp> getPowerUps() { 
        return powerUps; 
    }
    
    /**
     * Gets the boss enemy.
     * 
     * @return The boss, or null if not present
     */
    public Boss getBoss() { 
        return boss; 
    }
    
    /**
     * Sets the boss enemy.
     * 
     * @param boss The boss to set
     */
    public void setBoss(Boss boss) { 
        this.boss = boss; 
    }

    /**
     * Gets the total game score.
     * 
     * @return The total score
     */
    public int getScore() { 
        return score; 
    }
    
    /**
     * Sets the total game score.
     * 
     * @param score The score to set
     */
    public void setScore(int score) { 
        this.score = score; 
    }

    /**
     * Gets the current level.
     * 
     * @return The current level (1-4)
     */
    public int getLevel() { 
        return level; 
    }
    
    /**
     * Sets the current level.
     * 
     * @param level The level to set
     */
    public void setLevel(int level) { 
        this.level = level; 
    }
    
    /**
     * Advances to the next level.
     */
    public void nextLevel() { 
        this.level++; 
        this.levelComplete = false;
    }

    /**
     * Checks if the game is running.
     * 
     * @return true if the game is running
     */
    public boolean isGameRunning() { 
        return gameRunning; 
    }
    
    /**
     * Sets the game running state.
     * 
     * @param running true to run the game, false to stop
     */
    public void setGameRunning(boolean running) { 
        this.gameRunning = running; 
    }

    /**
     * Checks if the current level is complete.
     * 
     * @return true if the level is complete
     */
    public boolean isLevelComplete() { 
        return levelComplete; 
    }
    
    /**
     * Sets the level complete flag.
     * 
     * @param complete true if the level is complete
     */
    public void setLevelComplete(boolean complete) { 
        this.levelComplete = complete; 
    }

    /**
     * Gets the number of enemies to spawn.
     * 
     * @return The enemy spawn count
     */
    public int getEnemiesToSpawn() { 
        return enemiesToSpawn; 
    }
    
    /**
     * Sets the number of enemies to spawn.
     * 
     * @param count The enemy spawn count
     */
    public void setEnemiesToSpawn(int count) { 
        this.enemiesToSpawn = count; 
    }

    /**
     * Gets the number of enemies already spawned.
     * 
     * @return The spawned enemy count
     */
    public int getEnemiesSpawned() { 
        return enemiesSpawned; 
    }
    
    /**
     * Sets the number of enemies already spawned.
     * 
     * @param spawned The spawned enemy count
     */
    public void setEnemiesSpawned(int spawned) { 
        this.enemiesSpawned = spawned; 
    }

    /**
     * Checks if the boss has been defeated.
     * 
     * @return true if the boss is defeated
     */
    public boolean isBossDefeated() { 
        return bossDefeated; 
    }
    
    /**
     * Sets the boss defeated flag.
     * 
     * @param defeated true if the boss is defeated
     */
    public void setBossDefeated(boolean defeated) { 
        this.bossDefeated = defeated; 
    }

    /**
     * Gets the points accumulated in the current level.
     * 
     * @return The points in the current level
     */
    public int getPointsInCurrentLevel() {
        return pointsInCurrentLevel;
    }

    /**
     * Sets the points accumulated in the current level.
     * 
     * @param points The points to set
     */
    public void setPointsInCurrentLevel(int points) {
        this.pointsInCurrentLevel = points;
    }
}
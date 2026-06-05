package com.spaceblaster.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class GameState {

    private Player player;
    private List<Asteroid> asteroids;
    private List<Enemy> enemies;
    private List<Bullet> bullets;
    private List<PowerUp> powerUps;
    private Boss boss;

    private int score;
    private int level;
    private boolean gameRunning;
    private boolean levelComplete;
    private boolean bossDefeated;
    private int enemiesToSpawn;
    private int enemiesSpawned;

    private Queue<Asteroid> asteroidsToAdd;
    private Queue<Enemy> enemiesToAdd;
    private Queue<Bullet> bulletsToAdd;
    private Queue<PowerUp> powerUpsToAdd;

    private int requiredScore; // Pontuação necessária para passar de fase
    private int phase; // Fase atual (1-4)

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
        this.requiredScore = 1000; // Primeira fase precisa de 1000 pontos
        this.phase = 1;

        this.asteroidsToAdd = new ConcurrentLinkedQueue<>();
        this.enemiesToAdd = new ConcurrentLinkedQueue<>();
        this.bulletsToAdd = new ConcurrentLinkedQueue<>();
        this.powerUpsToAdd = new ConcurrentLinkedQueue<>();
    }

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

        if (level < 4 && enemies.isEmpty() && asteroids.isEmpty()
                && enemiesSpawned >= enemiesToSpawn && !levelComplete) {
            levelComplete = true;
        }

        if (level == 4 && boss != null && boss.isDefeated() && !levelComplete) {
            bossDefeated = true;
            levelComplete = true;
        }
    }

    public void addAsteroid(Asteroid asteroid) {
        asteroidsToAdd.add(asteroid);
    }

    public void addEnemy(Enemy enemy) {
        enemiesToAdd.add(enemy);
        enemiesSpawned++;
    }

    public void addBullet(Bullet bullet) {
        bulletsToAdd.add(bullet);
    }

    public void addPowerUp(PowerUp powerUp) {
        powerUpsToAdd.add(powerUp);
    }

    public void resetForNextLevel() {
        asteroids.clear();
        enemies.clear();
        bullets.clear();
        powerUps.clear();
        levelComplete = false;
        enemiesSpawned = 0;

        if (level == 4) {
            bossDefeated = false;
            boss = null;
        }
    }

    // Getters e Setters
    public Player getPlayer() {
        return player;
    }

    public List<Asteroid> getAsteroids() {
        return asteroids;
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public List<Bullet> getBullets() {
        return bullets;
    }

    public List<PowerUp> getPowerUps() {
        return powerUps;
    }

    public Boss getBoss() {
        return boss;
    }

    public void setBoss(Boss boss) {
        this.boss = boss;
    }

    public int getScore() {
        return score;
    }

    public void addScore(int points) {
        this.score += points;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void nextLevel() {
        this.level++;
    }

    public boolean isGameRunning() {
        return gameRunning;
    }

    public void setGameRunning(boolean running) {
        this.gameRunning = running;
    }

    public boolean isLevelComplete() {
        return levelComplete;
    }

    public void setLevelComplete(boolean complete) {
        this.levelComplete = complete;
    }

    public int getEnemiesToSpawn() {
        return enemiesToSpawn;
    }

    public void setEnemiesToSpawn(int count) {
        this.enemiesToSpawn = count;
    }

    public int getEnemiesSpawned() {
        return enemiesSpawned;
    }

    public void setEnemiesSpawned(int spawned) {
        this.enemiesSpawned = spawned;
    }

    public boolean isBossDefeated() {
        return bossDefeated;
    }

    public void setBossDefeated(boolean defeated) {
        this.bossDefeated = defeated;
    }

    public int getRequiredScore() {
        return requiredScore;
    }

    public void setRequiredScore(int requiredScore) {
        this.requiredScore = requiredScore;
    }

    public int getPhase() {
        return phase;
    }

    public void setPhase(int phase) {
        this.phase = phase;
    }

    public void nextPhase() {
        this.phase++;
        // Aumenta a pontuação necessária para cada fase
        this.requiredScore = 1000 + phase; // Fase 2: 2000, Fase 3: 3000, Fase 4: 4000
    }
}

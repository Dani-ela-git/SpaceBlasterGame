package com.spaceblaster.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

import com.spaceblaster.model.Asteroid;
import com.spaceblaster.model.Boss;
import com.spaceblaster.model.Bullet;
import com.spaceblaster.model.Enemy;
import com.spaceblaster.model.GameState;
import com.spaceblaster.model.Player;
import com.spaceblaster.model.PowerUp;
import com.spaceblaster.util.ScoreManager;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class GameController {

    private Stage primaryStage;
    private int width;
    private int height;
    private GameState gameState;
    private AnimationTimer gameLoop;
    private Set<KeyCode> activeKeys;
    private long lastShotTime;
    private long lastEnemySpawnTime;
    private long lastAsteroidSpawnTime;
    private Random random;
    private Canvas canvas;
    private GraphicsContext gc;

    // Imagens carregadas uma vez
    private Image background;
    private Image playerImage;
    private Image asteroidImage;
    private Image enemyImage;
    private Image bossImage;
    private Image bulletImage;
    private Image heartImage;
    private Map<PowerUp.PowerUpType, Image> powerUpImages;

    public GameController(Stage primaryStage, int width, int height) {
        this.primaryStage = primaryStage;
        this.width = width;
        this.height = height;
        this.activeKeys = new HashSet<>();
        this.random = new Random();

        loadImages();
    }

    private void loadImages() {
        try {
            // Usei caminho absoluto para evitar problemas de classpath
            String basePath = "/home/daniiii/Documentos/faculdade/POO/codigos_java/codigos-JAVA/SpaceBlasterJavaFX/src/main/resourses/SpaceShooterRedux/images/";

            background = new Image("file:" + basePath + "darkPurple.png");
            playerImage = new Image("file:" + basePath + "playerShip3_green.png");
            asteroidImage = new Image("file:" + basePath + "meteorBrown_big4.png");
            enemyImage = new Image("file:" + basePath + "enemyBlue4.png");
            bossImage = new Image("file:" + basePath + "ufoYellow.png");
            bulletImage = new Image("file:" + basePath + "laserBlue01.png");
            heartImage = new Image("file:" + basePath + "heart.png");

            powerUpImages = new HashMap<>();
            powerUpImages.put(PowerUp.PowerUpType.RAPID_FIRE,
                    new Image("file:" + basePath + "powerup_rapid.png"));
            powerUpImages.put(PowerUp.PowerUpType.SHIELD,
                    new Image("file:" + basePath + "powerup_shield.png"));
            powerUpImages.put(PowerUp.PowerUpType.EXTRA_LIFE,
                    new Image("file:" + basePath + "powerup_life.png"));
            powerUpImages.put(PowerUp.PowerUpType.SCORE_MULTIPLIER,
                    new Image("file:" + basePath + "powerup_score.png"));

            System.out.println("✅ Imagens carregadas com sucesso!");
        } catch (Exception e) {
            System.err.println("Erro ao carregar imagens: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void startGame() {
        if (gameState == null) {
            gameState = new GameState();
        }
        setupLevel();

        canvas = new Canvas(width, height);
        gc = canvas.getGraphicsContext2D();

        Scene gameScene = new Scene(new StackPane(canvas), width, height);

        gameScene.setOnKeyPressed(e -> {
            activeKeys.add(e.getCode());
            if (e.getCode() == KeyCode.SPACE) {
                shoot();
            }
        });

        gameScene.setOnKeyReleased(e -> activeKeys.remove(e.getCode()));

        primaryStage.setScene(gameScene);

        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
                render(gc);
            }
        };

        gameLoop.start();
    }

    private void setupLevel() {
        int phase = gameState.getPhase();

        switch (phase) {
            case 1:
                gameState.setEnemiesToSpawn(0);
                break;
            case 2:
                gameState.setEnemiesToSpawn(4);
                break;
            case 3:
                gameState.setEnemiesToSpawn(7);
                break;
            case 4:
                gameState.setEnemiesToSpawn(8);
                Boss boss = new Boss(width / 2 - 40, 50);
                gameState.setBoss(boss);
                break;
        }
    }

    private void update() {
        if (!gameState.isGameRunning()) {
            gameOver();
            return;
        }

        // Verifica se atingiu a pontuação necessária para a próxima fase
        if (!gameState.isLevelComplete()
                && gameState.getScore() >= gameState.getRequiredScore()
                && gameState.getPhase() < 4) {
            gameState.setLevelComplete(true);
            return;
        }

        // Verifica se completou a fase 4 (boss)
        if (gameState.getPhase() == 4 && gameState.isBossDefeated() && !gameState.isLevelComplete()) {
            gameState.setLevelComplete(true);
            return;
        }

        if (gameState.isLevelComplete()) {
            nextLevel();
            return;
        }

        Player player = gameState.getPlayer();
        if (activeKeys.contains(KeyCode.LEFT)) {
            player.moveLeft();
        }
        if (activeKeys.contains(KeyCode.RIGHT)) {
            player.moveRight(width);
        }
        if (activeKeys.contains(KeyCode.UP)) {
            player.moveUp();
        }
        if (activeKeys.contains(KeyCode.DOWN)) {
            player.moveDown(height);
        }

        // Spawn de asteroides
        long now = System.currentTimeMillis();
        int spawnDelay = Math.max(800, 2500 - gameState.getLevel() * 150);
        if (now - lastAsteroidSpawnTime > spawnDelay) {
            spawnAsteroid();
            lastAsteroidSpawnTime = now;
        }

        // Spawn de inimigos (a partir da fase 2)
        if (gameState.getPhase() >= 2
                && gameState.getEnemies().size() < 2
                && gameState.getEnemiesSpawned() < (gameState.getPhase() * 2)) { // Mais inimigos por fase
            if (now - lastEnemySpawnTime > (4500 - gameState.getPhase() * 500)) { // Spawn mais rápido
                spawnEnemy();
                lastEnemySpawnTime = now;
            }
        }

        // Inimigos atiram (frequência aumenta com a fase)
        for (Enemy enemy : gameState.getEnemies()) {
            if (enemy.canShoot()) {
                int bulletSpeed = 3 + (gameState.getPhase() / 2); // Balas mais rápidas
                Bullet bullet = new Bullet(enemy.getBulletX(), enemy.getBulletY(), bulletSpeed, false);
                gameState.addBullet(bullet);
            }
        }

        // Boss atira
        if (gameState.getPhase() == 4) {
            Boss boss = gameState.getBoss();
            if (boss != null && !gameState.isBossDefeated() && boss.canShoot()) {
                Bullet bullet = new Bullet(boss.getBulletX(), boss.getBulletY(), 5, false);
                gameState.addBullet(bullet);
            }
        }

        gameState.update();
        checkCollisions();
    }

    private void spawnAsteroid() {
        double x = random.nextDouble() * (width - 50) + 25;
        // Velocidade aumenta com a fase
        double speed = 1 + random.nextDouble() * 2 + (gameState.getPhase() * 0.4);
        Asteroid asteroid = new Asteroid(x, -30, speed);
        gameState.addAsteroid(asteroid);
    }

    private void spawnEnemy() {
        double x = random.nextDouble() * (width - 50) + 25;
        // Movimento horizontal muito suave
        double speedX = (random.nextDouble() - 0.5) * 0.5; // Reduzido de 1 para 0.5
        // Velocidade vertical bem lenta: entre 0.3 e 0.8
        double speedY = 0.3 + random.nextDouble() * 0.5; // Reduzido de 0.8-1.2 para 0.3-0.8
        Enemy enemy = new Enemy(x, -35, speedX, speedY);
        gameState.addEnemy(enemy);
    }

    private void shoot() {
        long now = System.currentTimeMillis();
        long shootDelay = gameState.getPlayer().hasRapidFire() ? 50 : 200;
        if (now - lastShotTime > shootDelay) {
            Player player = gameState.getPlayer();
            Bullet bullet = new Bullet(player.getX() + player.getWidth() / 2 - 2,
                    player.getY() - 10, true);
            gameState.addBullet(bullet);
            lastShotTime = now;
        }
    }

    private void checkCollisions() {
        Player player = gameState.getPlayer();

        for (Iterator<Bullet> bulletIt = gameState.getBullets().iterator(); bulletIt.hasNext();) {
            Bullet bullet = bulletIt.next();
            if (bullet.isFromPlayer()) {
                for (Iterator<Asteroid> astIt = gameState.getAsteroids().iterator(); astIt.hasNext();) {
                    Asteroid asteroid = astIt.next();
                    if (bullet.getBounds().intersects(asteroid.getBounds().getBoundsInParent())) {
                        bulletIt.remove();
                        astIt.remove();
                        gameState.addScore(asteroid.getPoints());
                        maybeSpawnPowerUp(asteroid.getX(), asteroid.getY());
                        break;
                    }
                }

                for (Iterator<Enemy> enemyIt = gameState.getEnemies().iterator(); enemyIt.hasNext();) {
                    Enemy enemy = enemyIt.next();
                    if (bullet.getBounds().intersects(enemy.getBounds().getBoundsInParent())) {
                        bulletIt.remove();
                        enemyIt.remove();
                        gameState.addScore(enemy.getPoints());
                        maybeSpawnPowerUp(enemy.getX(), enemy.getY());
                        break;
                    }
                }

                Boss boss = gameState.getBoss();
                if (boss != null && !gameState.isBossDefeated()
                        && bullet.getBounds().intersects(boss.getBounds().getBoundsInParent())) {
                    bulletIt.remove();
                    boss.hit();
                    gameState.addScore(50);
                    if (boss.isDefeated()) {
                        gameState.addScore(1000);
                        gameState.setBossDefeated(true);
                    }
                }
            }
        }

        for (Asteroid asteroid : gameState.getAsteroids()) {
            if (player.getBounds().intersects(asteroid.getBounds().getBoundsInParent())) {
                player.hit();
                gameState.getAsteroids().remove(asteroid);
                if (player.getLives() <= 0) {
                    gameState.setGameRunning(false);
                }
                break;
            }
        }

        for (Bullet bullet : gameState.getBullets()) {
            if (!bullet.isFromPlayer()
                    && player.getBounds().intersects(bullet.getBounds().getBoundsInParent())) {
                player.hit();
                gameState.getBullets().remove(bullet);
                if (player.getLives() <= 0) {
                    gameState.setGameRunning(false);
                }
                break;
            }
        }

        for (Iterator<PowerUp> it = gameState.getPowerUps().iterator(); it.hasNext();) {
            PowerUp powerUp = it.next();
            if (player.getBounds().intersects(powerUp.getBounds().getBoundsInParent())) {
                applyPowerUp(powerUp);
                it.remove();
            }
        }
    }

    private void maybeSpawnPowerUp(double x, double y) {
        if (random.nextDouble() < 0.1) {
            PowerUp.PowerUpType[] types = PowerUp.PowerUpType.values();
            PowerUp.PowerUpType type = types[random.nextInt(types.length)];
            PowerUp powerUp = new PowerUp(x, y, type);
            gameState.addPowerUp(powerUp);
        }
    }

    private void applyPowerUp(PowerUp powerUp) {
        Player player = gameState.getPlayer();
        switch (powerUp.getType()) {
            case RAPID_FIRE:
                player.activateRapidFire(5000);
                break;
            case SHIELD:
                player.activateShield(5000);
                break;
            case EXTRA_LIFE:
                // Implementar vida extra depois
                break;
            case SCORE_MULTIPLIER:
                gameState.addScore(500);
                break;
        }
    }

    private void nextLevel() {
        gameLoop.stop();

        // Mostra tela de fase completa
        showLevelCompleteScreen();
    }

    private void showLevelCompleteScreen() {
        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: black;");

        int completedPhase = gameState.getPhase();
        String message;

        if (completedPhase == 4) {
            message = "CONGRATULATIONS!\nYou completed the game!\nFinal Score: " + gameState.getScore()
                    + "\n\nPress ENTER to return to menu";
        } else {
            message = "PHASE " + completedPhase + " COMPLETE!\n"
                    + "Score: " + gameState.getScore() + " / " + gameState.getRequiredScore()
                    + "\n\nNext phase requires: " + (1000 + completedPhase * 500) + " points"
                    + "\n\nPress ENTER to continue";
        }

        javafx.scene.control.Label levelLabel = new javafx.scene.control.Label(message);
        levelLabel.setFont(Font.font("Monospace", 24));
        levelLabel.setTextFill(Color.WHITE);
        levelLabel.setTextAlignment(TextAlignment.CENTER);
        levelLabel.setWrapText(true);

        root.getChildren().add(levelLabel);

        Scene scene = new Scene(root, width, height);
        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                if (completedPhase == 4) {
                    MenuController menuController = new MenuController(primaryStage, width, height);
                    menuController.showMainMenu();
                } else {
                    // Avança para próxima fase sem recriar o GameState
                    gameState.nextPhase();
                    gameState.resetForNextLevel();
                    setupLevel();
                    // Volta para o jogo
                    startGame();
                }
            }
        });

        primaryStage.setScene(scene);
    }

    private void gameOver() {
        gameLoop.stop();

        javafx.scene.control.TextInputDialog dialog = new javafx.scene.control.TextInputDialog();
        dialog.setTitle("Game Over");
        dialog.setHeaderText("You reached Phase " + gameState.getPhase()
                + "\nYour score: " + gameState.getScore()
                + "\nTarget for next phase: " + gameState.getRequiredScore());
        dialog.setContentText("Enter your name:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(name -> {
            ScoreManager.saveHighScore(name, gameState.getScore());
        });

        MenuController menuController = new MenuController(primaryStage, width, height);
        menuController.showMainMenu();
    }

    private void render(GraphicsContext gc) {
        // Fundo
        if (background != null) {
            gc.drawImage(background, 0, 0, width, height);
        } else {
            gc.setFill(Color.BLACK);
            gc.fillRect(0, 0, width, height);
        }

        Player player = gameState.getPlayer();

        // Jogador
        if (playerImage != null) {
            if (player.isInvincible() && (System.currentTimeMillis() / 100) % 2 == 0) {
                gc.setGlobalAlpha(0.5);
            }
            gc.drawImage(playerImage, player.getX(), player.getY(), player.getWidth(), player.getHeight());
            gc.setGlobalAlpha(1.0);
        }

        // Asteroides
        if (asteroidImage != null) {
            for (Asteroid a : gameState.getAsteroids()) {
                gc.drawImage(asteroidImage, a.getX(), a.getY(), a.getWidth(), a.getHeight());
            }
        }

        // Inimigos
        if (enemyImage != null) {
            for (Enemy e : gameState.getEnemies()) {
                gc.drawImage(enemyImage, e.getX(), e.getY(), e.getWidth(), e.getHeight());
            }
        }

        // Boss
        if (gameState.getBoss() != null && !gameState.isBossDefeated()) {
            Boss boss = gameState.getBoss();
            if (bossImage != null) {
                gc.drawImage(bossImage, boss.getX(), boss.getY(), boss.getWidth(), boss.getHeight());
            }
            gc.setFill(Color.RED);
            gc.fillRect(boss.getX(), boss.getY() - 20, boss.getWidth(), 10);
            gc.setFill(Color.GREEN);
            double healthPercent = (double) boss.getHitPoints() / boss.getMaxHitPoints();
            gc.fillRect(boss.getX(), boss.getY() - 20, boss.getWidth() * healthPercent, 10);
        }

        // Projéteis
        if (bulletImage != null) {
            for (Bullet b : gameState.getBullets()) {
                gc.drawImage(bulletImage, b.getX(), b.getY(), b.getWidth(), b.getHeight());
            }
        }

        // Power-ups
        if (powerUpImages != null) {
            for (PowerUp p : gameState.getPowerUps()) {
                Image powerImg = powerUpImages.get(p.getType());
                if (powerImg != null) {
                    gc.drawImage(powerImg, p.getX(), p.getY(), p.getWidth(), p.getHeight());
                }
            }
        }

        // HUD
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        gc.fillText("SCORE: " + gameState.getScore(), 20, 40);
        gc.fillText("LEVEL: " + gameState.getLevel(), 20, 80);
        gc.setFont(Font.font("Arial", FontWeight.NORMAL, 18));
        gc.fillText("NEXT PHASE: " + gameState.getRequiredScore() + " points", width - 200, 40);
        gc.fillText("PHASE: " + gameState.getPhase() + "/4", width - 200, 70);

        if (heartImage != null) {
            for (int i = 0; i < gameState.getPlayer().getLives(); i++) {
                gc.drawImage(heartImage, 20 + (i * 35), 100, 30, 30);
            }
        } else {
            gc.fillText("LIVES: " + gameState.getPlayer().getLives(), 20, 120);
        }

        if (gameState.getPlayer().hasShield()) {
            gc.setFill(Color.CYAN);
            gc.setFont(Font.font("Arial", FontWeight.BOLD, 18));
            gc.fillText("SHIELD ACTIVE", width - 150, 40);
        }

        if (gameState.getPlayer().hasRapidFire()) {
            gc.setFill(Color.ORANGE);
            gc.fillText("RAPID FIRE", width - 150, 80);
        }
    }
}

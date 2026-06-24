package com.spaceblaster.util;

import java.io.*;
import java.util.*;

/**
 * Utility class for managing high scores.
 * Handles saving and loading scores from a text file.
 * Scores are stored as name,score pairs, one per line.
 * 
 * @author Space Blaster Team
 * @version 1.0
 */
public class ScoreManager {
    
    /** The name of the high score file. */
    private static final String HIGH_SCORE_FILE = "highscores.txt";
    
    /** The maximum number of scores to keep. */
    private static final int MAX_SCORES = 5;
    
    /**
     * Gets the file path for the high score file in the user's home directory.
     * 
     * @return The full path to the high score file
     */
    private static String getScoreFilePath() {
        String userHome = System.getProperty("user.home");
        return userHome + File.separator + ".spaceblaster" + File.separator + HIGH_SCORE_FILE;
    }
    
    /**
     * Ensures that the directory for the high score file exists.
     * Creates the directory if it does not exist.
     */
    private static void ensureDirectoryExists() {
        try {
            String userHome = System.getProperty("user.home");
            File dir = new File(userHome + File.separator + ".spaceblaster");
            if (!dir.exists()) {
                dir.mkdirs();
                System.out.println("Created directory: " + dir.getAbsolutePath());
            }
        } catch (Exception e) {
            System.err.println("Error creating directory: " + e.getMessage());
        }
    }
    
    /**
     * Saves a high score to the file.
     * If the player name is empty, defaults to "PLAYER".
     * 
     * @param playerName The name of the player
     * @param score The score achieved
     */
    public static void saveHighScore(String playerName, int score) {
        System.out.println("=== SAVING HIGH SCORE ===");
        System.out.println("Player: " + playerName);
        System.out.println("Score: " + score);
        
        if (playerName == null || playerName.trim().isEmpty()) {
            playerName = "PLAYER";
        }
        
        ensureDirectoryExists();
        
        List<HighScoreEntry> scores = loadHighScores();
        scores.add(new HighScoreEntry(playerName, score));
        
        scores.sort((a, b) -> Integer.compare(b.getScore(), a.getScore()));
        if (scores.size() > MAX_SCORES) {
            scores = scores.subList(0, MAX_SCORES);
        }
        
        String filePath = getScoreFilePath();
        File file = new File(filePath);
        
        System.out.println("Saving to: " + file.getAbsolutePath());
        
        try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
            for (HighScoreEntry entry : scores) {
                writer.println(entry.getName() + "," + entry.getScore());
                System.out.println("  Saved: " + entry.getName() + "," + entry.getScore());
            }
            System.out.println("Scores saved successfully!");
        } catch (IOException e) {
            System.err.println("ERROR saving high scores: " + e.getMessage());
            e.printStackTrace();
        }
        System.out.println("=== END SAVING ===");
    }
    
    /**
     * Loads high scores from the file.
     * If the file does not exist, creates default scores.
     * 
     * @return A list of HighScoreEntry objects, sorted by score (highest first)
     */
    public static List<HighScoreEntry> loadHighScores() {
        System.out.println("=== LOADING HIGH SCORES ===");
        List<HighScoreEntry> scores = new ArrayList<>();
        
        ensureDirectoryExists();
        
        String filePath = getScoreFilePath();
        File file = new File(filePath);
        
        System.out.println("Looking for file: " + file.getAbsolutePath());
        System.out.println("File exists: " + file.exists());
        
        if (!file.exists()) {
            System.out.println("File not found. Creating default scores.");
            scores = createDefaultScores();
            
            try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
                for (HighScoreEntry entry : scores) {
                    writer.println(entry.getName() + "," + entry.getScore());
                }
                System.out.println("Default scores saved to: " + file.getAbsolutePath());
            } catch (IOException e) {
                System.err.println("Could not save default scores: " + e.getMessage());
            }
            
            System.out.println("=== END LOADING (defaults) ===");
            return scores;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    try {
                        String name = parts[0].trim();
                        int score = Integer.parseInt(parts[1].trim());
                        scores.add(new HighScoreEntry(name, score));
                        System.out.println("  Loaded: " + name + "," + score);
                    } catch (NumberFormatException e) {
                        System.err.println("Invalid score format: " + line);
                    }
                }
            }
            System.out.println("Loaded " + scores.size() + " scores from file");
        } catch (IOException e) {
            System.err.println("Error loading high scores: " + e.getMessage());
            e.printStackTrace();
            return createDefaultScores();
        }
        
        if (scores.isEmpty()) {
            System.out.println("No scores found, returning defaults");
            return createDefaultScores();
        }
        
        scores.sort((a, b) -> Integer.compare(b.getScore(), a.getScore()));
        
        System.out.println("=== END LOADING ===");
        return scores;
    }
    
    /**
     * Creates a default list of high scores.
     * 
     * @return A list of default HighScoreEntry objects
     */
    private static List<HighScoreEntry> createDefaultScores() {
        List<HighScoreEntry> scores = new ArrayList<>();
        scores.add(new HighScoreEntry("AAA", 5));
        scores.add(new HighScoreEntry("BBB", 4));
        scores.add(new HighScoreEntry("CCC", 3));
        scores.add(new HighScoreEntry("DDD", 2));
        scores.add(new HighScoreEntry("EEE", 1));
        return scores;
    }
    
    /**
     * Inner class representing a single high score entry.
     * Contains a player name and their score.
     */
    public static class HighScoreEntry {
        
        /** The name of the player. */
        private String name;
        
        /** The score achieved. */
        private int score;
        
        /**
         * Constructs a new HighScoreEntry.
         * 
         * @param name The player name
         * @param score The score achieved
         */
        public HighScoreEntry(String name, int score) {
            this.name = name;
            this.score = score;
        }
        
        /**
         * Gets the player name.
         * 
         * @return The player name
         */
        public String getName() { 
            return name; 
        }
        
        /**
         * Gets the score.
         * 
         * @return The score
         */
        public int getScore() { 
            return score; 
        }
    }
}
package com.spaceblaster.util;

import java.io.*;
import java.util.*;

public class ScoreManager {
    private static final String HIGH_SCORE_FILE = "highscores.txt";
    private static final int MAX_SCORES = 5;
    
    public static void saveHighScore(String playerName, int score) {
        List<HighScoreEntry> scores = loadHighScores();
        scores.add(new HighScoreEntry(playerName, score));
        
        scores.sort((a, b) -> Integer.compare(b.getScore(), a.getScore()));
        if (scores.size() > MAX_SCORES) {
            scores = scores.subList(0, MAX_SCORES);
        }
        
        try (PrintWriter writer = new PrintWriter(new FileWriter(HIGH_SCORE_FILE))) {
            for (HighScoreEntry entry : scores) {
                writer.println(entry.getName() + "," + entry.getScore());
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar high score: " + e.getMessage());
        }
    }
    
    public static List<HighScoreEntry> loadHighScores() {
        List<HighScoreEntry> scores = new ArrayList<>();
        File file = new File(HIGH_SCORE_FILE);
        
        if (!file.exists()) {
            // Scores padrão
            scores.add(new HighScoreEntry("AAA", 5000));
            scores.add(new HighScoreEntry("BBB", 4000));
            scores.add(new HighScoreEntry("CCC", 3000));
            scores.add(new HighScoreEntry("DDD", 2000));
            scores.add(new HighScoreEntry("EEE", 1000));
            return scores;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    scores.add(new HighScoreEntry(parts[0], Integer.parseInt(parts[1])));
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao carregar high scores: " + e.getMessage());
        }
        
        return scores;
    }
    
    public static class HighScoreEntry {
        private String name;
        private int score;
        
        public HighScoreEntry(String name, int score) {
            this.name = name;
            this.score = score;
        }
        
        public String getName() { return name; }
        public int getScore() { return score; }
    }
}
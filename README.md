# Space Blaster

A 2D space shooter game developed in Java with JavaFX. Fight against waves of asteroids and enemy ships, collect power-ups, and defeat the final boss!

## 📋 Requirements

- Java 25 or higher
- JavaFX 23 or higher
- Maven (optional, for dependency management)

## 🎮 How to Play

### Controls
- **Arrow Keys** - Move your ship
- **SPACE** - Shoot

### Gameplay
- Destroy asteroids and enemies to earn points
- Avoid enemy fire and collisions
- You have 3 lives
- Complete 4 levels to win!
- Each level requires 1000 points to advance

### Difficulty Progression

| Level | Points Required | Enemies | Difficulty |
|-------|----------------|---------|------------|
| 1 | 1000 | 0 | Normal |
| 2 | 2000 | 5 | Increased speed |
| 3 | 3000 | 8 | Fast spawn rate |
| 4 | 4000 | 10 + Boss | Maximum difficulty |

### Power-ups
- **Rapid Fire** - Shoot faster for 5 seconds
- **Shield** - Become invincible for 5 seconds
- **Score Multiplier** - Earn bonus points
- **Extra Life** - Gain an additional life

## 🚀 How to Run

### Using Maven (Recommended)

```bash
# Clone the repository
git clone https://github.com/yourusername/SpaceBlasterJavaFX.git

# Navigate to the project
cd SpaceBlasterJavaFX

# Compile and run
mvn clean compile
mvn javafx:run

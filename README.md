# Space Blaster

A 2D space shooter game developed in Java with JavaFX. Fight against waves of asteroids and enemy ships, collect power-ups, and defeat the final boss.

## Group Identification

| Name | USP Number |
|------|------------|
| Daniela | 14613625 |
| Bruna Romero | 11913896 |

---

## 1. Requirements

The game implements the following requirements:

### Core Features
- Main menu with "Start Game", "High Scores", and "Exit" options
- Instructions screen displayed before the first level
- Player controls: Arrow keys for movement, Space bar for shooting
- Asteroids spawn at the top of the screen and move downward
- Enemy ships appear from level 2 onward and fire back at the player
- Collision detection: bullets destroy asteroids/enemies; enemy bullets or collisions reduce player lives
- 3 lives system; game ends when all lives are lost

### Levels and Difficulty
- 4 levels with increasing difficulty
- Level progression based on score (1000 points per level)
- Final level includes a boss enemy with larger sprite and 10 hit points
- "Level Complete" screen between levels showing score earned

### Scoring and HUD
- Points awarded for destroying asteroids (100 pts), enemies (150 pts), and boss hits (50 pts)
- Boss defeat awards 1000 bonus points
- Current score, level, and lives always visible on screen
- Progress bar shows points needed for next level

### Graphics and Sound
- Sprite-based graphics for ship, enemies, and bullets
- Simple explosion animation using sprite sheet
- Sound effects for shooting and explosions (optional)

### High Scores
- Top 5 scores saved to file (highscores.txt)
- High scores displayed on dedicated screen accessible from main menu

### Power-ups (Optional Enhancements)
- Rapid Fire: Shoot faster for 5 seconds
- Shield: Become invincible for 5 seconds
- Score Multiplier: Earn 500 bonus points
- Extra Life: Gain an additional life (structure implemented)

---

## 2. Project Description

### Architecture

The project follows the Model-View-Controller (MVC) architectural pattern:

- **Model**: Game entities (Player, Asteroid, Enemy, Boss, Bullet, PowerUp) and game state management
- **View**: Rendering of game elements using JavaFX GraphicsContext
- **Controller**: Game logic, input handling, collision detection, and level progression

### Class Structure
**com.spaceblaster/
├── Main.java # Application entry point
├── controller/
│ ├── GameController.java # Main game logic and loop
│ ├── MenuController.java # Menu and navigation
│ └── HighScoreController.java # High score management
├── model/
│ ├── GameState.java # Central game state
│ ├── Player.java # Player ship with power-ups
│ ├── Asteroid.java # Asteroid entity
│ ├── Enemy.java # Enemy ship
│ ├── Boss.java # Final boss
│ ├── Bullet.java # Projectile
│ └── PowerUp.java # Power-up items
└── util/
├── ScoreManager.java # High score persistence
└── CollisionDetector.java # Collision detection utilities**


### Key Design Decisions

1. **Game Loop**: Uses JavaFX AnimationTimer for smooth 60 FPS gameplay
2. **Collision Detection**: Rectangle-based intersection detection
3. **Score-Based Level Progression**: Each level requires 1000 points (level 1: 0-999, level 2: 1000-1999, etc.)
4. **Concurrent Collections**: Thread-safe queues for entity addition during game loop
5. **Invincibility Frames**: 2 seconds of invincibility after taking damage

### Screenshots

*[Insert screenshots here]*

**Main Menu**
![Main Menu](screenshots/menu.png)

**Gameplay**
![Gameplay](screenshots/gameplay.png)

**Level Complete**
![Level Complete](screenshots/level_complete.png)

**Game Over**
![Game Over](screenshots/game_over.png)

**High Scores**
![High Scores](screenshots/high_scores.png)

---

## 3. Comments About the Code

### Code Organization
- Each class has a single, well-defined responsibility
- Game entities extend no external classes, using composition instead
- All public methods are documented with JavaDoc comments

Known Issues
Images are loaded from absolute path; adjust basePath in loadImages() if images don't appear

Power-up for extra life is not yet fully implemented

4. Test Plan
Manual Test Cases
Test Case	Description	Expected Result
TC-01	Start game from main menu	Game screen appears with player ship at bottom center
TC-02	Move ship with arrow keys	Ship moves smoothly within screen boundaries
TC-03	Shoot with space bar	Bullet appears from ship and moves upward
TC-04	Collide with asteroid	Life decreases, player becomes invincible briefly
TC-05	Destroy asteroid	Score increases by 100, asteroid disappears
TC-06	Reach 1000 points	Level complete screen appears
TC-07	Complete level 4 with boss	Victory screen appears with final score
TC-08	Lose all 3 lives	Game over screen appears with restart options
TC-09	Collect power-up	Corresponding effect activates (rapid fire/shield)
TC-10	Enter name after game over	Score saved to high scores list
Automated Testing
JUnit tests are not implemented due to the real-time nature of the game. Testing was performed manually following the test plan above.

5. Test Results
Test Case	Status	Notes
TC-01	PASS	Menu navigates correctly
TC-02	PASS	Movement is responsive and bounded
TC-03	PASS	Shots fire at 200ms intervals (50ms with rapid fire)
TC-04	PASS	Lives decrease correctly, invincibility works
TC-05	PASS	Score updates correctly
TC-06	PASS	Level complete triggers at 1000, 2000, 3000 points
TC-07	PASS	Boss appears in level 4, requires 10 hits
TC-08	PASS	Game over screen offers restart, menu, and exit
TC-09	PASS	Power-ups activate for 5 seconds
TC-10	PASS	Scores persist to highscores.txt
All core features function as expected. No critical bugs were found.

6. Build Procedures
Prerequisites
Install Java 17 or higher (Ubuntu/Debian):

bash
sudo apt update
sudo apt install openjdk-17-jdk
java -version
Install JavaFX:

bash
sudo apt install openjfx
Install Maven (optional but recommended):

bash
sudo apt install maven
Clone and Build
bash
# Clone the repository
git clone https://github.com/Dani-ela-git/SpaceBlasterGame.git

# Navigate to project directory
cd SpaceBlasterGame/SpaceBlasterJavaFX

# Build with Maven
mvn clean compile

# Run the game
mvn javafx:run
Alternative: Run without Maven
bash
# Set JavaFX path
JAVAFX_PATH="/usr/share/openjfx/lib"

# Compile
javac --module-path $JAVAFX_PATH --add-modules javafx.controls,javafx.fxml,javafx.media -d bin $(find src -name "*.java")

# Run
java --module-path $JAVAFX_PATH --add-modules javafx.controls,javafx.fxml,javafx.media -cp bin com.spaceblaster.Main
Image Assets
The game uses sprites from the Space Shooter Redux asset pack. Images are located at:

text
src/main/resourses/images/

How to Play
Controls
Arrow Keys - Move your ship

SPACE - Shoot

Gameplay
Destroy asteroids and enemies to earn points

Avoid enemy fire and collisions

You have 3 lives

Complete 4 levels to win

Each level requires 1000 points to advance

Difficulty Progression
Level	Points Required	Enemies	Difficulty
1	1000	0	Normal
2	2000	5	Increased speed
3	3000	8	Fast spawn rate
4	4000	10 + Boss	Maximum difficulty
Power-ups
Rapid Fire - Shoot faster for 5 seconds

Shield - Become invincible for 5 seconds

Score Multiplier - Earn bonus points

Extra Life - Gain an additional life


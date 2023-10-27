package com.opcode.spaceinvader2;

import com.opcode.spaceinvader2.Boss.Boss;
import com.opcode.spaceinvader2.Boss.BossBullet;
import com.opcode.spaceinvader2.Boss.LifeBar;
import com.opcode.spaceinvader2.Enemy.EnemyBullet;
import com.opcode.spaceinvader2.Enemy.EnemyTeir2;
import com.opcode.spaceinvader2.Model.Explosion;
import com.opcode.spaceinvader2.Player.PlayerBullet;
import com.opcode.spaceinvader2.Enemy.EnemyShip;
import com.opcode.spaceinvader2.Player.PlayerShip;
import com.opcode.spaceinvader2.Player.PlayerSpecialBullet;
import javafx.animation.AnimationTimer;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class Launcher extends Application {

    public static final Logger logger = LogManager.getLogger(Launcher.class);

    LifeBar lifeBar = new LifeBar();
    // UI Element
    private Text scoreText;
    public static final int PANE_WIDTH = 530;
    public static final int PANE_HEIGHT = 730;

    // Lists
    private List<PlayerBullet> playerBullets = new ArrayList<>();
    private List<PlayerSpecialBullet> playerSpeacialBullets = new ArrayList<>();
    private List<EnemyShip> enemyShips = new ArrayList<>();
    private List<Boss> bosses = new ArrayList<>();
    private List<EnemyTeir2> uncommonEnemyShips = new ArrayList<>();
    private List<EnemyBullet> enemyBullets = new ArrayList<>();
    private List<BossBullet> bossBullets = new ArrayList<>();
    private List<ImageView> heartLives = new ArrayList<>();
    // Hit
    private List<PlayerBullet> PlayerbulletsToRemove = new ArrayList<>();
    private List<PlayerSpecialBullet> playerSpeacialBulletToRemove = new ArrayList<>();
    private List<BossBullet> bossBulletsToRemove = new ArrayList<>();
    private List<Boss> bossesToRemove = new ArrayList<>();
    private List<EnemyShip> enemyShipsToRemove = new ArrayList<>();
    private List<EnemyTeir2> uncommonEnemyToRemove = new ArrayList<>();
    private List<EnemyBullet> enemyTeir2sToShoot = new ArrayList<EnemyBullet>();
    private List<EnemyBullet> enemyBulletsToShoot = new ArrayList<>();
    private List<BossBullet> bossBulletsToShoot = new ArrayList<>();

    // Player action
    private int score = 0;
    private int playerLives = 3;
    private int bossLives = 5;

    // Image
    private Image backgroundImage = new Image(Objects.requireNonNull(Launcher.class.getResource("/com/opcode/spaceinvader2/Pics/bg_02_v.png")).toExternalForm());
    private Image heartImg = new Image(Objects.requireNonNull((Launcher.class.getResource("/com/opcode/spaceinvader2/Pics/Heart.png"))).toExternalForm());

    // Boolean
    private boolean moveLeft = false;
    private boolean moveRight = false;
    private boolean RisPress = false;

    private boolean bossDied = false;
    //Animation Sprite
    private boolean spawnBoss = false;
    // Cooldown variables
    private static final long COOLDOWN_DURATION = 250;
    private long lastHitTime = 0;
    private int bulletHitsPlayerCounter = 0;

    // Boss Life Bar
    private static final int INITIAL_LIFE = 100;
    private int currentLife = INITIAL_LIFE;



    Stage currentStage;

    @Override
    public void start(Stage stage) {
        Pane startPane = new Pane();

        // Load and set the background image
        ImageView backgroundImageView = new ImageView(backgroundImage);
        startPane.getChildren().add(backgroundImageView);  // Add background image to pane

        Scene startScene = new Scene(startPane, PANE_WIDTH, PANE_HEIGHT);

        Image introImage = new Image(Objects.requireNonNull(Launcher.class.getResource("/com/opcode/spaceinvader2/Pics/intro.png")).toExternalForm());
        ImageView introImageView = new ImageView(introImage);
        introImageView.setLayoutX(0);
        introImageView.setLayoutY(70);

        Button startButton = new Button("START");
        startButton.setLayoutX(175);
        startButton.setLayoutY(424);
        startButton.setPrefSize(180, 60);
        startButton.setFont(new Font(20));
        startButton.setTextFill(Color.WHITE);
        startButton.setStyle("-fx-background-color: #AF2492; -fx-background-radius: 25;");

        Button exitButton = new Button("EXIT");
        exitButton.setLayoutX(175);
        exitButton.setLayoutY(510);
        exitButton.setPrefSize(180, 60);
        exitButton.setFont(new Font(20));
        exitButton.setTextFill(Color.WHITE);
        exitButton.setStyle("-fx-background-color: #AF2492; -fx-background-radius: 25;");

        startButton.setOnAction(e -> {
            startGame(stage); // initiate the game when button is clicked
        });

        exitButton.setOnAction(e -> {
            stage.close(); // close the primary stage (window) to exit the program
        });

        startPane.getChildren().addAll(introImageView ,startButton, exitButton);
        stage.setScene(startScene);
        stage.setTitle("OP Space Invader");
        stage.show();
    }

    private void victory() {
        currentStage.close();

        Pane victoryPane = new Pane();
        Stage stage = new Stage();

        // Load and set the background image
        ImageView backgroundImageView = new ImageView(backgroundImage);
        victoryPane.getChildren().add(backgroundImageView);  // Add background image to pane

        Scene victoryScene = new Scene(victoryPane, PANE_WIDTH, PANE_HEIGHT);

        Image youWin = new Image(Objects.requireNonNull(Launcher.class.getResource("/com/opcode/spaceinvader2/Pics/youWIn.png")).toExternalForm());
        ImageView youWinImageView = new ImageView(youWin);
        youWinImageView.setLayoutX(10);
        youWinImageView.setLayoutY(30);

        Text scoreTextVictory = new Text("Score: " + score);
        scoreTextVictory.setFill(Color.WHITE);
        scoreTextVictory.setFont(new Font(50));
        scoreTextVictory.setLayoutX(180);
        scoreTextVictory.setLayoutY(480);  // Adjust the layout position

        Button reStart = new Button("ReStart");
        reStart.setTextFill(Color.WHITE);
        reStart.setStyle("-fx-background-color: #AF2492; -fx-background-radius: 25;");
        reStart.setFont(new Font(20));
        reStart.setPrefSize(180,60);
        reStart.setLayoutX(180);
        reStart.setLayoutY(580);

        reStart.setOnAction(e -> {
            restartGame(stage);
        });

        victoryPane.getChildren().addAll(scoreTextVictory, youWinImageView);

        stage.setScene(victoryScene);
        stage.show();
    }

    private void restartGame(Stage stage) {
        // Reset all game variables and lists
        score = 0;
        playerLives = 3;
        moveLeft = false;
        moveRight = false;
        bossDied = false;
        spawnBoss = false;
        lastHitTime = 0;
        bulletHitsPlayerCounter = 0;

        // Clear all lists
        playerBullets.clear();
        enemyShips.clear();
        bosses.clear();
        uncommonEnemyShips.clear();
        enemyBullets.clear();
        bossBullets.clear();
        heartLives.clear();

        // Clear the platform
        Pane platform = new Pane();
        platform.getChildren().clear();

        // Initialize the game again
        startGame(stage);
    }

    private void GameOver(Stage start) {
        Pane GameOver = new Pane();
        Stage stage = new Stage();

        // Load and set the background image
        ImageView backgroundImageView = new ImageView(backgroundImage);
        GameOver.getChildren().add(backgroundImageView);  // Add background image to pane

        Scene GameOverScene = new Scene(GameOver, PANE_WIDTH, PANE_HEIGHT);

        Image gameOVer = new Image(Objects.requireNonNull(Launcher.class.getResource("/com/opcode/spaceinvader2/Pics/GameOver.png")).toExternalForm());
        ImageView gameOverImage = new ImageView(gameOVer);
        gameOverImage.setLayoutX(10);
        gameOverImage.setLayoutY(30);


        Text scoreText = new Text("Score: " + score);
        scoreText.setFill(Color.WHITE);
        scoreText.setFont(new Font(50));
        scoreText.setLayoutX(180);
        scoreText.setLayoutY(480);  // Adjust the layout position

        Button reStart = new Button("ReStart");
        reStart.setTextFill(Color.WHITE);
        reStart.setStyle("-fx-background-color: #AF2492; -fx-background-radius: 25;");
        reStart.setFont(new Font(20));
        reStart.setPrefSize(180,60);
        reStart.setLayoutX(180);
        reStart.setLayoutY(580);

        reStart.setOnAction(e -> {
            restartGame(stage);
        });

        PauseTransition pauseStart = new PauseTransition(Duration.seconds(0.5));
        pauseStart.setOnFinished(event -> start.close());
        pauseStart.play();


        PauseTransition pausePop = new PauseTransition(Duration.seconds(0.5));
        pausePop.setOnFinished(event -> stage.show());
        pausePop.play();

        GameOver.getChildren().addAll(gameOverImage, scoreText, reStart);

        stage.setScene(GameOverScene);
    }

    // Functions

    private void initializeHeartLife(Pane platform) {
        for (int i = 0; i < playerLives; i++) {
            ImageView heart = new ImageView(heartImg);
            heart.setX(10 + (i * (heartImg.getWidth() + 5)));
            heart.setY(10);
            heartLives.add(heart);
            platform.getChildren().add(heart);
        }
    }

    private void updateHearts() {
        // Remove one heart ImageView
        if (playerLives < heartLives.size()) {
            ImageView heartToRemove = heartLives.remove(heartLives.size() - 1);
            heartToRemove.setImage(null);
        }
    }

    public void startGame(Stage stage) {
        currentStage = stage;

        Pane platform = new Pane();
        backgroundImage = new Image(Objects.requireNonNull(Launcher.class.getResource("/com/opcode/spaceinvader2/Pics/bg_02_v.png")).toExternalForm());
        ImageView backgroundImg = new ImageView(backgroundImage);
        backgroundImg.setFitHeight(backgroundImage.getHeight());
        backgroundImg.setFitWidth(backgroundImage.getWidth());
        PlayerShip playerShip = new PlayerShip();


        Scene scene = new Scene(platform, PANE_WIDTH, PANE_HEIGHT);
        platform.getChildren().addAll(backgroundImg, playerShip);

        stage.setScene(scene);
        stage.setTitle("OP Space Invader");

        Boss bossShip = new Boss(PANE_WIDTH, 200, 60);

        // Detect User Key
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case LEFT:
                    moveLeft = true;
                    break;
                case RIGHT:
                    moveRight = true;
                    break;
                case SPACE:
                    PlayerBullet playerBullet = new PlayerBullet(playerShip.getX() + playerShip.getShipImageView().getFitWidth() / 2 + 10, playerShip.getY());
                    playerBullets.add(playerBullet);
                    platform.getChildren().add(playerBullet.getBulletImagePreview());
                    break;
                case R:
                    RisPress = true;
                    PlayerSpecialBullet playerSpeacialBullet = new PlayerSpecialBullet(playerShip.getX() + playerShip.getShipImageView().getFitWidth()/2 + 10, playerShip.getY());
                    playerSpeacialBullets.add(playerSpeacialBullet);
                    platform.getChildren().add(playerSpeacialBullet.getBulletImagePreview());
                    break;
            }
            logger.info("PLAYER SHIP MOVE X: {}, Y: {}", playerShip.getX(), playerShip.getY());
        });

        // Detect Released
        scene.setOnKeyReleased(event -> {
            switch (event.getCode()) {
                case LEFT:
                    moveLeft = false;
                    break;
                case RIGHT:
                    moveRight = false;
                    break;
                case R:
                    RisPress = false;
                    break;
            }

        });

        //Set Live
        initializeHeartLife(platform);

        // Score Text
        scoreText = new javafx.scene.text.Text(445, 30, "Score: 0");
        scoreText.setFill(Color.WHITE);
        scoreText.setFont(new Font(20));
        platform.getChildren().add(scoreText);

        // Add Common enemy ships
        for (int i = 0; i < 4; i++) { // example, spawn 10 enemies
            EnemyShip enemy = new EnemyShip(randomXPosition(), randomYPosition(), PANE_WIDTH);
            logger.info("ENEMY {} SPAWN X: {}, Y: {}", i+1, this.randomXPosition(), this.randomYPosition());
            enemyShips.add(enemy);
            platform.getChildren().add(enemy.getShipImageView());
            enemy.getShipImageView().setY(randomYPosition());
        }

        // Add Uncommon enemy Ships
        for (int i = 0; i < 4; i++) {
            EnemyTeir2 enemyTeir2 = new EnemyTeir2(randomXPosition(), randomYPosition(), PANE_WIDTH);
            logger.info("UNCOMMON ENEMY {} SPAWN X: {}, Y: {}", i+1, this.randomXPosition(), this.randomYPosition());
            uncommonEnemyShips.add(enemyTeir2);
            platform.getChildren().add(enemyTeir2.getShipImageView());
            enemyTeir2.getShipImageView().setY(randomYPosition() - 20);
        }

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                // Player Movement
                handlePlayerMovement();

                // Handle Player Bullet Action
                handlePlayerBulletAction();

                // EnemyShip Movement
                handleEnemyAction();

                // Uncommon Enemy Movement
                handleEnemyTier2Action();

                // Enemy Collisions
                handleCollisions();

                // Boss Movement and Action
                if (spawnBoss) {
                    handleBossAction();
                }

                // Update player ship animation
                playerShip.tick();

                // Update Boss Animation
                bossShip.tick();

            }

            // Player Movement
            private void handlePlayerMovement() {
                if (moveLeft) {
                    playerShip.moveLeft();
                }
                if (moveRight) {
                    playerShip.moveRight();
                }
                if (RisPress) {
                    playerShip.getShipImageView();
                }
            }

            // Player Bullet Action
            private void handlePlayerBulletAction() {
                playerBullets.forEach(PlayerBullet::moveUp);
                playerSpeacialBullets.forEach(PlayerSpecialBullet::moveUp);
            }

            // EnemyShip Movement
            private void handleEnemyAction() {
                enemyShips.forEach(enemyShip -> {
                    enemyShip.move();
                    if (enemyShip.decideToShoot()) {
                        EnemyBullet enemyBullet = enemyShip.shoot();
                        enemyBulletsToShoot.add(enemyBullet);
                        platform.getChildren().add(enemyBullet.getBulletImagePreview());
                    }
                });
                enemyBullets.addAll(enemyBulletsToShoot );
                enemyBullets.forEach(EnemyBullet::moveDown);
            }

            // Enemy Teir 2 Movement
            private void handleEnemyTier2Action() {
                uncommonEnemyShips.forEach(enemyTeir2 -> {
                    enemyTeir2.move();
                    if (enemyTeir2.decideToShoot()) {
                        EnemyBullet enemyBullet = enemyTeir2.shoot();
                        enemyTeir2sToShoot.add(enemyBullet);
                        platform.getChildren().add(enemyBullet.getBulletImagePreview());
                    }
                });
                enemyBullets.addAll(enemyTeir2sToShoot);
                enemyBullets.forEach(EnemyBullet::moveDown);
            }

            // Boss Action
            private void handleBossAction() {
                bossShip.move();
                if (bossShip.decideToShoot()) {
                    BossBullet bossBullet = bossShip.shoot();
                    bossBulletsToShoot.add(bossBullet);
                    platform.getChildren().add(bossBullet.getBulletImagePreview());
                }
                bossBullets.addAll(bossBulletsToShoot);
                bossBullets.forEach(BossBullet::moveDown);
            }

                private void handleCollisions (){
                    Iterator<PlayerBullet> playerBulletIterator = playerBullets.iterator();
                    while (playerBulletIterator.hasNext()){
                        PlayerBullet playerBullet = playerBulletIterator.next();
                    // Player Bullet Hit Enemy
                        // Use iterator for enemyShips list
                        Iterator<EnemyShip> enemyShipIterator = enemyShips.iterator();
                        while (enemyShipIterator.hasNext()) {
                            EnemyShip enemy = enemyShipIterator.next();

                            if (playerBullet.getHitbox().getBoundsInParent().intersects(enemy.getHitBox().getBoundsInParent())) {
                                // Your collision logic here

                                // Remove Player bullet
                                playerBulletIterator.remove();
                                platform.getChildren().remove(playerBullet.getBulletImagePreview());

                                // Remove Enemy
                                enemyShipIterator.remove(); // Use iterator to remove from the list
                                platform.getChildren().remove(enemy.getShipImageView());

                                score += 1;

                                PlayerbulletsToRemove.add(playerBullet);
                                enemyShipsToRemove.add(enemy);
                            }
                        }

                        uncommonEnemyShips.forEach(enemy -> {
                            if (playerBullet.getHitbox().getBoundsInParent().intersects(enemy.getHitBox().getBoundsInParent())) {
                                // Your collision logic here

                                // Remove Player bullet
                                playerBulletIterator.remove();
                                platform.getChildren().remove(playerBullet.getBulletImagePreview());

                                // Remove Enemy
                                enemyShips.remove(enemy);
                                platform.getChildren().remove(enemy.getShipImageView());

                                playerBullet.stop();

                                score += 2;

                                PlayerbulletsToRemove.add(playerBullet);
                                uncommonEnemyToRemove.add(enemy);
                            }
                        });
                        // Enemy Bullet Hit Player Ship
                        enemyBullets.forEach(enemyBullet -> {
                            if (enemyBullet.getHitbox().getBoundsInParent().intersects(playerShip.getHitbox().getBoundsInParent())) {
                                // Remove enemy bullet
                                enemyBullets.remove(enemyBullet);
                                platform.getChildren().remove(enemyBullet.getBulletImagePreview());
                            }
                        });
                        if (uncommonEnemyShips.isEmpty() && enemyShips.isEmpty() && !spawnBoss) {
                            PauseTransition spawnDelay = new PauseTransition(Duration.seconds(1));
                            spawnDelay.setOnFinished(event -> {
                                if (!platform.getChildren().contains(bossShip.getShipImageView())) {
                                    lifeBar.initializeLifeBar(platform, 225, 10);
                                    platform.getChildren().add(bossShip.getShipImageView());
                                }
                                bossShip.getBossHitBox().setX(bossShip.getShipImageView().getX());
                                bossShip.getBossHitBox().setY(bossShip.getShipImageView().getY());
                                if (!platform.getChildren().contains(bossShip.getBossHitBox())) {
                                    platform.getChildren().add(bossShip.getBossHitBox());
                                }
                                spawnBoss = true;
                            });
                            spawnDelay.play();
                        }
                    }
                    playerSpeacialBullets.forEach(playerSpecialBullet -> {

                        enemyShips.forEach(enemy -> {

                            if (playerSpecialBullet.getHitbox().getBoundsInParent().intersects(enemy.getHitBox().getBoundsInParent())) {

                                Explosion explosion = new Explosion(enemy.getShipImageView().getX(), enemy.getShipImageView().getY());

                                platform.getChildren().add(explosion.getExplosionImageView());

                                PauseTransition delay = new PauseTransition(Duration.seconds(0.5));

                                delay.setOnFinished(event -> platform.getChildren().remove(explosion.getExplosionImageView()));

                                delay.play();

                                // Remove Player bullet
                                playerSpeacialBullets.remove(playerSpecialBullet);
                                platform.getChildren().remove(playerSpecialBullet.getBulletImagePreview());

                                // Remove Enemy
                                enemyShips.remove(enemy);
                                platform.getChildren().remove(enemy.getShipImageView());

                                score += 1;

                                playerSpeacialBulletToRemove.add(playerSpecialBullet);

                                enemyShipsToRemove.add(enemy);

                            }

                        });

                        uncommonEnemyShips.forEach(enemy -> {

                            if (playerSpecialBullet.getHitbox().getBoundsInParent().intersects(enemy.getHitBox().getBoundsInParent())) {

                                Explosion explosion = new Explosion(enemy.getShipImageView().getX(), enemy.getShipImageView().getY());

                                platform.getChildren().add(explosion.getExplosionImageView());

                                PauseTransition delay = new PauseTransition(Duration.seconds(0.5));

                                delay.setOnFinished(event -> platform.getChildren().remove(explosion.getExplosionImageView()));

                                delay.play();

                                // Remove Player bullet
                                playerSpeacialBullets.remove(playerSpecialBullet);
                                platform.getChildren().remove(playerSpecialBullet.getBulletImagePreview());

                                // Remove Enemy
                                enemyShips.remove(enemy);
                                platform.getChildren().remove(enemy.getShipImageView());

                                score += 2;

                                playerSpeacialBulletToRemove.add(playerSpecialBullet);

                                uncommonEnemyToRemove.add(enemy);

                            }

                        });
                        // Enemy Bullet Hit Player Ship

                        enemyBullets.forEach(enemyBullet -> {

                            if (enemyBullet.getHitbox().getBoundsInParent().intersects(playerShip.getHitbox().getBoundsInParent())) {

                                // Remove enemy bullet
                                enemyBullets.remove(enemyBullet);
                                platform.getChildren().remove(enemyBullet.getBulletImagePreview());

                            }

                        });
                        if (uncommonEnemyShips.isEmpty() && enemyShips.isEmpty() && !spawnBoss) {

                            PauseTransition spawnDelay = new PauseTransition(Duration.seconds(1));

                            spawnDelay.setOnFinished(event -> {

                                if (!platform.getChildren().contains(bossShip.getShipImageView())) {

                                    platform.getChildren().add(bossShip.getShipImageView());

                                }

                                bossShip.getBossHitBox().setX(bossShip.getShipImageView().getX());

                                bossShip.getBossHitBox().setY(bossShip.getShipImageView().getY());

                                if (!platform.getChildren().contains(bossShip.getBossHitBox())) {

                                    platform.getChildren().add(bossShip.getBossHitBox());

                                }
                                spawnBoss = true;
                            });
                            spawnDelay.play();
                        }
                    });
                    // Inside handleCollisions method where player is hit by enemy bullet:
                    for (EnemyBullet enemyBullet : enemyBullets) {
                        if (enemyBullet.getHitbox().getBoundsInParent().intersects(playerShip.getHitbox().getBoundsInParent())) {
                            long currentTime = System.currentTimeMillis();
                            if (currentTime - lastHitTime >= COOLDOWN_DURATION) {
                                bulletHitsPlayerCounter++; // Increment the counter

                                platform.getChildren().removeAll(enemyBullet.getBulletImagePreview());

                                // Decrement Player's Life
                                playerLives--;

                                // Update the displayed lives
                                updateHearts();

                                lastHitTime = currentTime;

                                logger.info("PLAYER HIT, LIVE REMAIN: {}", playerLives);
                                break;
                            }
                        }
                        if (playerLives <= 0) {
                            this.stop();
                            logger.info("PLAYER DIE");
                            GameOver(stage);
                            return;
                        }
                    }

                    // Inside handleCollisions method where playerBullet hits bossShip:
                    if (spawnBoss) {
                        for (PlayerBullet playerBullet : playerBullets) {
                            // Check if playerBullet hits bossShip
                            if (playerBullet.getHitbox().getBoundsInParent().intersects(bossShip.getBossHitBox().getBoundsInParent())) {
                                long currentTime = System.currentTimeMillis();
                                // Handle collision logic
                                if (currentTime - lastHitTime >= COOLDOWN_DURATION) {
                                    Explosion explosion = new Explosion(bossShip.getShipImageView().getX(), bossShip.getShipImageView().getY());
                                    platform.getChildren().add(explosion.getExplosionImageView());

                                    PauseTransition delay = new PauseTransition(Duration.seconds(0.5));
                                    delay.setOnFinished(event -> platform.getChildren().remove(explosion.getExplosionImageView()));
                                    delay.play();

                                    lifeBar.updateLifeBar(-20);

                                    // Remove bossShip and playerBullet
                                    platform.getChildren().remove(playerBullet.getBulletImagePreview());
                                    platform.getChildren().remove(playerBullet.getHitbox());

                                    // Remove all bossBullets
                                    platform.getChildren().removeAll(bossBullets);
                                    bossBullets.clear();

                                    // Add bossShip and bossBulletsToRemove to their respective lists for removal
                                    bossesToRemove.add(bossShip);
                                    bossBulletsToRemove.addAll(bossBullets);

                                    // Clear playerBulletsToRemove
                                    PlayerbulletsToRemove.clear();

                                    lastHitTime = currentTime;

                                    logger.info("BOSS HIT");

                                    break;
                                }

                            }
                        }
                        if (lifeBar.getCurrentLife() <= 0) {
                            this.stop();
                            logger.info("BOSS DIE");
                            score += 5;
                            victory();
                            return;
                        }
                    }
                    // Inside handleCollisions method where Boss is hit by
                    for (BossBullet bossBullet : bossBullets) {
                        if (bossBullet.getHitbox().getBoundsInParent().intersects(playerShip.getHitbox().getBoundsInParent())) {
                            long currentTime = System.currentTimeMillis();
                            if (currentTime - lastHitTime >= COOLDOWN_DURATION) {
                                bulletHitsPlayerCounter++;
                                System.out.println("Bullet Hits Player: " + bulletHitsPlayerCounter);

                                platform.getChildren().removeAll(bossBullet.getBulletImagePreview());

                                // Decrement Player's life
                                playerLives--;

                                // Update the displayed Lives
                                updateHearts();

                                lastHitTime = currentTime;

                                System.out.println("Losing a life");
                                break;
                            }
                        }
                        if (playerLives <= 0) {
                            this.stop();
                            GameOver(stage);
                            return;
                        }
                    }
                    // Remove boss bullets that have hit the player
                    bossBullets.removeAll(bossBulletsToRemove);
                    bossBulletsToRemove.clear();

                    // Scoring
                    scoreText.setText("Score: " + score);

                    enemyShips.removeAll(enemyShipsToRemove);
                    PlayerbulletsToRemove.clear();
                    enemyShipsToRemove.clear();
                    playerBullets.removeAll(PlayerbulletsToRemove);
                    uncommonEnemyShips.removeAll(uncommonEnemyToRemove);
                    uncommonEnemyToRemove.clear(); // Clear uncommonEnemyToRemove only once
                    bossBullets.removeAll(bossBulletsToShoot);
                    bossBullets.clear();

                }
        }.start();
        stage.show();
    }

    private double randomXPosition() {
        return Math.random() * (PANE_WIDTH + 60);  // adjust as needed
    }

    private double randomYPosition() {
        return Math.random() * (PANE_HEIGHT / 2 - 200) + 70;  // Y position between 100 and PANE_HEIGHT/2 - 100
    }

    private boolean checkBulletPlayerCollision(EnemyBullet enemyBullet, PlayerShip playerShip) {
        return enemyBullet.getBulletImagePreview().getBoundsInParent().intersects(playerShip.getShipImageView().getBoundsInParent());
    }

    public static void main(String[] args) {
        launch();
    }
}

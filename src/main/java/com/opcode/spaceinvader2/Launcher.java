package com.opcode.spaceinvader2;

import com.opcode.spaceinvader2.Boss.Boss;
import com.opcode.spaceinvader2.Boss.BossBullet;
import com.opcode.spaceinvader2.Enemy.EnemyBullet;
import com.opcode.spaceinvader2.Enemy.EnemyTeir2;
import com.opcode.spaceinvader2.Model.Explosion;
import com.opcode.spaceinvader2.Player.PlayerBullet;
import com.opcode.spaceinvader2.Enemy.EnemyShip;
import com.opcode.spaceinvader2.Player.PlayerShip;
import javafx.animation.AnimationTimer;
import javafx.animation.PauseTransition;
import javafx.application.Application;
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

import java.util.*;

public class Launcher extends Application {
    // UI Element
    private Text scoreText;
    public static final int PANE_WIDTH = 530;
    public static final int PANE_HEIGHT = 730;

    // Lists
    private List<PlayerBullet> playerBullets = new ArrayList<>();
    private List<EnemyShip> enemyShips = new ArrayList<>();
    private List<EnemyTeir2> uncommonEnemyShips = new ArrayList<>();
    private List<EnemyBullet> enemyBullets = new ArrayList<>();
    private List<BossBullet> bossBullets = new ArrayList<>();
    private List<ImageView> heartLives = new ArrayList<>();
    // Hit
    private List<PlayerBullet> PlayerbulletsToRemove = new ArrayList<>();

    private List<EnemyShip> enemyShipsToRemove = new ArrayList<>();
    private List<EnemyTeir2> uncommonEnemyToRemove = new ArrayList<>();
    private List<EnemyBullet> enemyTeir2sToShoot = new ArrayList<EnemyBullet>();
    private List<EnemyBullet> enemyBulletsToShoot = new ArrayList<>();
    private List<BossBullet> bossBulletsToShoot = new ArrayList<>();

    // Player action
    private int score = 0;
    private int playerLives = 3;


    // Image
    private Image background;
    private Image heartImg = new Image(Objects.requireNonNull((Launcher.class.getResource("/com/opcode/spaceinvader2/image/Heart.png"))).toExternalForm());

    // Boolean
    private boolean moveLeft = false;
    private boolean moveRight = false;
    //Animation Sprite
    private Boss boss; // Add a reference to the Boss
    private boolean spawnBoss = false;
    // Cooldown variables
    private static final long COOLDOWN_DURATION = 250; // 2 seconds in milliseconds
    private long lastHitTime = 0;
    private int bulletHitsPlayerCounter = 0;

    @Override
    public void start(Stage stage) {
        Pane startPane = new Pane();

        // Load and set the background image
        Image backgroundImage = new Image(Objects.requireNonNull(Launcher.class.getResource("/com/opcode/spaceinvader2/image/bg_02_v.png")).toExternalForm());
        ImageView backgroundImageView = new ImageView(backgroundImage);
        startPane.getChildren().add(backgroundImageView);  // Add background image to pane

        Scene startScene = new Scene(startPane, PANE_WIDTH, PANE_HEIGHT);

        Button startButton = new Button("Start Game");
        startButton.setLayoutX(PANE_WIDTH / 2 - 35);  // center the button horizontally
        startButton.setLayoutY(PANE_HEIGHT / 2 - 95); // center vertically

        Button exitButton = new Button("Exit Game");
        exitButton.setLayoutX(PANE_WIDTH / 2 - 32);  // center the button horizontally
        exitButton.setLayoutY(PANE_HEIGHT / 2 - 55);


        startButton.setOnAction(e -> {
            startGame(stage); // initiate the game when button is clicked
        });

        exitButton.setOnAction(e -> {
            stage.close(); // close the primary stage (window) to exit the program
        });


        startPane.getChildren().addAll(startButton, exitButton);
        stage.setScene(startScene);
        stage.setTitle("OP Space Invader");
        stage.show();
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

        Pane platform = new Pane();
        background = new Image(Objects.requireNonNull(Launcher.class.getResource("/com/opcode/spaceinvader2/image/bg_02_v.png")).toExternalForm());
        ImageView backgroundImg = new ImageView(background);
        backgroundImg.setFitHeight(background.getHeight());
        backgroundImg.setFitWidth(background.getWidth());
        PlayerShip playerShip = new PlayerShip();

        Scene scene = new Scene(platform, PANE_WIDTH, PANE_HEIGHT);
        platform.getChildren().addAll(backgroundImg, playerShip);

        stage.setScene(scene);
        stage.setTitle("OP Space Invader");



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
            }
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
        for (int i = 0; i < 2; i++) { // example, spawn 10 enemies
            EnemyShip enemy = new EnemyShip(randomXPosition(), randomYPosition(), PANE_WIDTH);
            System.out.println("X: " + this.randomXPosition() +" Y: " + this.randomYPosition());
            enemyShips.add(enemy);
            platform.getChildren().add(enemy.getShipImageView());
            enemy.getShipImageView().setY(randomYPosition());
        }

        // Add Uncommon enemy Ships
        for (int i = 0; i < 2; i++) {
            EnemyTeir2 enemyTeir2 = new EnemyTeir2(randomXPosition(), randomYPosition(), PANE_WIDTH);
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
                    boss.move();

                    if (boss.decideToShoot()) {
                        BossBullet bossBullet = boss.shoot();
                        platform.getChildren().add(bossBullet.getBulletImagePreview());
                        bossBulletsToShoot.add(bossBullet);
                    }

                    bossBullets.addAll(bossBulletsToShoot);
                    bossBullets.forEach(BossBullet::moveDown);
                }
            }

            // Player Movement
            private void handlePlayerMovement() {
                if (moveLeft) {
                    playerShip.moveLeft();
                }
                if (moveRight) {
                    playerShip.moveRight();
                }
            }

            // Player Bullet Action
            private void handlePlayerBulletAction() {
                playerBullets.forEach(PlayerBullet::moveUp);
            }

            private void bossEntry() {
                boss = new Boss(PANE_WIDTH, 200, 60);
                platform.getChildren().add(boss.getShipImageView());
                spawnBoss = true;
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



                private void handleCollisions () {
                    // Player Bullet Hit Enemy
                    playerBullets.forEach(playerBullet -> {
                        enemyShips.forEach(enemy -> {
                            if (playerBullet.getHitbox().getBoundsInParent().intersects(enemy.getHitBox().getBoundsInParent())) {

                                Explosion explosion = new Explosion(enemy.getShipImageView().getX(), enemy.getShipImageView().getY());
                                platform.getChildren().add(explosion.getExplosionImageView());

                                PauseTransition delay = new PauseTransition(Duration.seconds(0.5));
                                delay.setOnFinished(event -> platform.getChildren().remove(explosion.getExplosionImageView()));
                                delay.play();

                                platform.getChildren().remove(playerBullet.getBulletImagePreview());
                                platform.getChildren().remove(playerBullet.getHitbox()); // Remove the hitbox

                                platform.getChildren().remove(enemy.getShipImageView());
                                platform.getChildren().remove(enemy.getHitBox());  // Remove the hitbox
                                playerBullet.stop();

                                score += 1;

                                PlayerbulletsToRemove.add(playerBullet);
                                enemyShipsToRemove.add(enemy);

                            }
                        });

                        uncommonEnemyShips.forEach(enemy -> {
                            if (playerBullet.getHitbox().getBoundsInParent().intersects(enemy.getHitBox().getBoundsInParent())) {

                                Explosion explosion = new Explosion(enemy.getShipImageView().getX(), enemy.getShipImageView().getY());
                                platform.getChildren().add(explosion.getExplosionImageView());

                                PauseTransition delay = new PauseTransition(Duration.seconds(0.5));
                                delay.setOnFinished(event -> platform.getChildren().remove(explosion.getExplosionImageView()));
                                delay.play();

                                platform.getChildren().remove(playerBullet.getBulletImagePreview());
                                platform.getChildren().remove(playerBullet.getHitbox()); // Remove the hitbox

                                platform.getChildren().remove(enemy.getShipImageView());
                                platform.getChildren().remove(enemy.getHitBox());  // Remove the hitbox
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
                                platform.getChildren().remove(enemyBullet.getBulletImagePreview());
                                platform.getChildren().remove(enemyBullet.getHitbox());
                            }
                        });
                        if (uncommonEnemyShips.isEmpty() && enemyShips.isEmpty() && !spawnBoss) {
                            bossEntry();
                        }

                    });
                    // Inside handleCollisions method where player is hit by enemy bullet:
                    for (EnemyBullet enemyBullet : enemyBullets) {
                        if (enemyBullet.getHitbox().getBoundsInParent().intersects(playerShip.getHitbox().getBoundsInParent())) {
                            long currentTime = System.currentTimeMillis();
                            if (currentTime - lastHitTime >= COOLDOWN_DURATION) {
                                bulletHitsPlayerCounter++; // Increment the counter
                                System.out.println("Bullet hits player: " + bulletHitsPlayerCounter);

                                platform.getChildren().removeAll(enemyBullet.getBulletImagePreview());

                                // Decrement Player's Life
                                playerLives--;

                                // Update the displayed lives
                                updateHearts();

                                lastHitTime = currentTime;

                                System.out.println("Losing a Life");
                                break;
                            }
                        }
                        if (playerLives <= 0) {
                            this.stop();
                            System.out.println("Game Over");
                            return;
                        }
                    }
                    // Inside handleCollisions method where Boss is hit by

                    // Scoring
                    scoreText.setText("Score: " + score);

                    enemyShips.removeAll(enemyShipsToRemove);
                    PlayerbulletsToRemove.clear();
                    enemyShipsToRemove.clear();
                    playerBullets.removeAll(PlayerbulletsToRemove);
                    uncommonEnemyShips.removeAll(uncommonEnemyToRemove);
                    uncommonEnemyToRemove.clear(); // Clear uncommonEnemyToRemove only once



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

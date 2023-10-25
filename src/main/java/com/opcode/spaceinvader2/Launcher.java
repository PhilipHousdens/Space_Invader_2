package com.opcode.spaceinvader2;

import com.opcode.spaceinvader2.Enemy.EnemyBullet;
import com.opcode.spaceinvader2.Player.PlayerBullet;
import com.opcode.spaceinvader2.Enemy.EnemyShip;
import com.opcode.spaceinvader2.Player.PlayerShip;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Launcher extends Application {
    public static final int PANE_WIDTH = 530;
    public static final int PANE_HEIGHT = 730;

    // Lists
    private List<PlayerBullet> playerBullets = new ArrayList<>();
    private List<EnemyShip> enemyShips = new ArrayList<>();
    private List<EnemyBullet> enemyBullets = new ArrayList<>();
    // Hit
    private List<PlayerBullet> PlayerbulletsToRemove = new ArrayList<>();
    private List<EnemyShip> enemyShipsToRemove = new ArrayList<>();
    private List<EnemyBullet> enemyBulletsToShoot = new ArrayList<>();


    // Image
    private Image background;

    // Boolean
    private boolean moveLeft = false;
    private boolean moveRight = false;




    @Override
    public void start(Stage stage) throws IOException {

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

        // Add Common enemy ships
        for (int i = 0; i < 5; i++) { // example, spawn 10 enemies
            EnemyShip enemy = new EnemyShip(randomXPosition(), randomYPosition(), PANE_WIDTH);
            enemyShips.add(enemy);
            platform.getChildren().add(enemy.getShipImageView());
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

                // Enemy Collisions
                handleCollisions();
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

            private void handleCollisions() {
                playerBullets.forEach(playerBullet -> {
                    enemyShips.forEach(enemy -> {
                        if (playerBullet.getHitbox().getBoundsInParent().intersects(enemy.getHitBox().getBoundsInParent())) {

                            platform.getChildren().remove(playerBullet.getBulletImagePreview());
                            platform.getChildren().remove(playerBullet.getHitbox()); // Remove the hitbox

                            platform.getChildren().remove(enemy.getShipImageView());
                            platform.getChildren().remove(enemy.getHitBox());  // Remove the hitbox

                            PlayerbulletsToRemove.add(playerBullet);
                            enemyShipsToRemove.add(enemy);
                        }
                    });
                });
                playerBullets.removeAll(PlayerbulletsToRemove);
                enemyShips.removeAll(enemyShipsToRemove);
                PlayerbulletsToRemove.clear();
                enemyShipsToRemove.clear();

                enemyBullets.forEach(enemyBullet -> {
                    if (enemyBullet.getHitbox().getBoundsInParent().intersects(playerShip.getHitbox().getBoundsInParent())) {
                        // Remove enemy bullet
                        platform.getChildren().remove(enemyBullet.getBulletImagePreview());
                        platform.getChildren().remove(enemyBullet.getHitbox());

                        // Remove player ship and its hitbox
                        platform.getChildren().remove(playerShip.getShipImageView());
                        platform.getChildren().remove(playerShip.getHitbox());
                    }
                });
            }




        }.start();
        stage.show();
    }

    private double randomXPosition() {
        return Math.random() * (PANE_WIDTH);  // adjust as needed
    }

    private double randomYPosition() {
        return Math.random() * (PANE_HEIGHT / 4);  // only on the top half of the screen
    }

    public static void main(String[] args) {
        launch();
    }
}

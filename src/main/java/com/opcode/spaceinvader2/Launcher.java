package com.opcode.spaceinvader2;

import com.opcode.spaceinvader2.entity.Bullet;
import com.opcode.spaceinvader2.entity.EnemyShip;
import com.opcode.spaceinvader2.entity.PlayerShip;
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
    private List<Bullet> bullets = new ArrayList<>();
    private Image background;

    //Boolean
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
        EnemyShip enemyShip = new EnemyShip();


        Scene scene = new Scene(platform, PANE_WIDTH, PANE_HEIGHT);
        platform.getChildren().addAll(backgroundImg, playerShip,enemyShip);

        stage.setScene(scene);
        stage.setTitle("OP Space Invader");

        //Detect User Key
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case LEFT:
                    moveLeft = true;
                    break;
                case RIGHT:
                    moveRight = true;
                    break;
                case SPACE:
                    Bullet bullet = new Bullet(playerShip.getX() + playerShip.getShipImageView().getFitWidth()/ 2 + 10, playerShip.getY());
                    bullets.add(bullet);
                    platform.getChildren().add(bullet.getBulletImagePreview());
            }
        });

        //Detect Released
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

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                // Player Movement
                handlePlayerMovement();

                // Handle Player Bullet Action
                handlePlayerBulletAction();
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
                bullets.forEach(Bullet::moveUp);
            }

        }.start();
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}

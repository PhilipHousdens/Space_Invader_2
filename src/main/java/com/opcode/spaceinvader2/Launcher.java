package com.opcode.spaceinvader2;

import com.opcode.spaceinvader2.entity.Bullet;
import com.opcode.spaceinvader2.entity.PlayerShip;
import com.opcode.spaceinvader2.view.Platform;
import javafx.animation.AnimationTimer;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Launcher extends Application {
    private boolean moveLeft = true;

    private List<Bullet> bullets = new ArrayList<>();
    private boolean moveRight = true;
    @Override
    public void start(Stage stage) throws IOException {
        Platform platform = new Platform();
        PlayerShip playerShip = new PlayerShip();
        Scene scene = new Scene(platform, Platform.PANE_WIDTH, Platform.PANE_HEIGHT);

        // Detecting Key
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case LEFT:
                    moveLeft = true;
                    break;
                case RIGHT:
                    moveRight = true;
                    break;
                case SPACE:
                    Bullet playerBullet = new Bullet(playerShip.getX()/2, playerShip.getY());
                    bullets.add(playerBullet);
                    platform.getChildren().add(playerBullet.getBulletImagePreview());
                    break;
            }
        });

        // Detect when keys are released
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

        stage.setScene(scene);
        stage.setTitle("OP Space Invader");
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                // Bullet Movement and Cleanup
                handlePlayerBulletActions();

                cleanupOutOfBoundsBullets();
            }
            private void handlePlayerBulletActions() {
                bullets.forEach(Bullet::moveUp);
            }

            private void cleanupOutOfBoundsBullets() {
                bullets.removeIf(bullet -> {
                    if (bullet.getBulletImagePreview().getY() <= 0) {
                        platform.getChildren().remove(bullet.getBulletImagePreview());
                        return true;
                    }
                    return false;
                });
            }




        }.start();
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}

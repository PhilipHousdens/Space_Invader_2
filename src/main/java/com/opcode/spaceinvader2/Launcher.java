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
    private Image background;

    private List<Bullet> bullets = new ArrayList<>();

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

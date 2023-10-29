package com.opcode.spaceinvader2.Player;

import com.opcode.spaceinvader2.Launcher;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.*;

class PlayerBulletTest extends ApplicationTest {
    @Override
    public void start(Stage primaryStage) {
        // Start your JavaFX application
        Pane pane = new Pane();
        Scene scene = new Scene(pane, Launcher.PANE_WIDTH, Launcher.PANE_HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Test
    public void testMoveUp() {
        // Create a PlayerBullet
        Launcher launcher = new Launcher();
        PlayerBullet playerBullet = new PlayerBullet(100, 200);

        // Set initial position
        double initialY = playerBullet.getY();

        // Move up
        playerBullet.moveUp();

        // Check if the Y position has decreased
        assertEquals(initialY - PlayerBullet.BULLET_DURATION, playerBullet.getY());
    }
}
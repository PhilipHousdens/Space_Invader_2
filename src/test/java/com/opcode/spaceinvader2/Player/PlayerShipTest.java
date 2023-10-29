package com.opcode.spaceinvader2.Player;

import com.opcode.spaceinvader2.Launcher;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.*;

class PlayerShipTest extends ApplicationTest {

    @Override
    public void start(Stage primaryStage) {
        // Start your JavaFX application
        Pane pane = new Pane();
        Scene scene = new Scene(pane, Launcher.PANE_WIDTH, Launcher.PANE_HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Test
    public void testMoveLeft() {
        // Create a PlayerShip
        PlayerShip playerShip = new PlayerShip();

        // Set initial position
        double initialX = playerShip.getX();

        // Move left
        playerShip.moveLeft();

        // Check if the X position has decreased
        assertTrue(playerShip.getX() < initialX);
    }

    @Test
    public void testMoveRight() {

        // Create a PlayerShip
        PlayerShip playerShip = new PlayerShip();

        // Set initial position
        double initialX = playerShip.getX();

        // Move right
        playerShip.moveRight();

        // Check if the X position has increased
        assertTrue(playerShip.getX() > initialX);
    }
}
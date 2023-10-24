package com.opcode.spaceinvader2.entity;

import com.opcode.spaceinvader2.Launcher;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.Objects;

public class PlayerShip extends Pane {
    private ImageView shipImageView;

    public PlayerShip() throws RuntimeException{
        // Load the image for the player's ship
        Image shipImage = new Image(Objects.requireNonNull(Launcher.class.getResource("/com/opcode/spaceinvader2/image/DurrrSpaceShip.png")).toExternalForm());
        shipImageView = new ImageView(shipImage);
        getChildren().add(shipImageView);

        // Set initial position
        setTranslateX(400);
        setTranslateY(500);

        // Set up keyboard input handling
        setOnKeyPressed(event -> handleKeyPress(event.getCode()));
    }

    public void handleKeyPress(KeyCode code) {
        //Adjust the player ship's position
        switch (code) {
            case LEFT:
                moveLeft();
                break;
            case RIGHT:
                moveRight();
                break;
        }
    }

    private void moveLeft() {
        setTranslateX(getTranslateX() - 10); //
    }

    private void moveRight() {
        setTranslateX(getTranslateX() + 10);
    }
}

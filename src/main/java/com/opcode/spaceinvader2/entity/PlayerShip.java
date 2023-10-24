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
    private static final int MOVE_STEP = 20;  // Adjust the movement step
    private static final int MIN_X = 0;
    private static final int MAX_X = 450;
    private ImageView shipImageView;


    public PlayerShip() throws RuntimeException{
        // Load the image for the player's ship
        Image shipImage = new Image(Objects.requireNonNull(Launcher.class.getResource("/com/opcode/spaceinvader2/image/DurrrSpaceShip.png")).toExternalForm());
        shipImageView = new ImageView(shipImage);
        getChildren().add(shipImageView);

        // Set initial position
        setTranslateX(245);
        setTranslateY(600);

        // Set up keyboard input handling
        setOnKeyPressed(this::handleKeyPress);

        // Request focus for the player ship
        this.setFocusTraversable(true);
        this.requestFocus();
    }

    private void handleKeyPress(KeyEvent event) {
        // Adjust the player ship's position
        switch (event.getCode()) {
            case LEFT:
                moveLeft();
                break;
            case RIGHT:
                moveRight();
                break;
            case SPACE:
                shoot();
                break;
        }
    }

    public double getX() {
        return getTranslateX();
    }

    public double getY() {
        return getTranslateY();
    }

    private void moveLeft() {
        double newX = getTranslateX() - MOVE_STEP;
        setTranslateX(Math.max(newX, MIN_X));
        System.out.println(getTranslateX());
    }

    private void moveRight() {
        double newX = getTranslateX() + MOVE_STEP;
        setTranslateX(Math.min(newX, MAX_X));
        System.out.println(getTranslateX());
    }

    public void shoot() {
        Bullet bullet = new Bullet(getTranslateX() / 2 , getTranslateY());
        bullet.moveUp();
        getChildren().add(bullet);
    }



    // Inner class representing a bullet
    private class Bullet extends com.opcode.spaceinvader2.entity.Bullet {
        public Bullet(double startX, double startY) {
            super(startX, startY);
        }
    }
}

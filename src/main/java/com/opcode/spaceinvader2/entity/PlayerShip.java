package com.opcode.spaceinvader2.entity;

import com.opcode.spaceinvader2.Launcher;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PlayerShip extends Pane {
    private static final int MOVE_STEP = 20;  // Adjust the movement step
    private static final int MIN_X = 0;
    private static final int MAX_X = 450;
    private Timeline shootingTimeline;
    private List<com.opcode.spaceinvader2.entity.Bullet> bullets = new ArrayList<>();

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
        setOnKeyReleased(this::handleKeyRelease);

        // Request focus for the player ship
        this.setFocusTraversable(true);
        this.requestFocus();

        // Set up shooting timeline
        shootingTimeline = new Timeline(new KeyFrame(
                Duration.millis(200), // Adjust the shooting interval
                event -> shoot()));
        shootingTimeline.setCycleCount(Timeline.INDEFINITE);
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
                startShooting();
                break;
        }
    }
    private void handleKeyRelease(KeyEvent event) {
        // Stop shooting when SPACE key is released
        if (event.getCode() == KeyCode.SPACE) {
            stopShooting();
        }
    }

    private void startShooting() {
        // Start shooting when SPACE key is pressed
        shootingTimeline.play();
    }

    private void stopShooting() {
        // Stop shooting when SPACE key is released
        shootingTimeline.pause();
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
        getChildren().add(bullet);
        bullets.add(bullet);
        bullet.moveUp();
    }

    public List<com.opcode.spaceinvader2.entity.Bullet> getBullets() {
        return bullets;
    }



    // Inner class representing a bullet
    private class Bullet extends com.opcode.spaceinvader2.entity.Bullet {
        public Bullet(double startX, double startY) {
            super(startX, startY);
        }
    }
}

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
    private static final int MOVE_STEP = 10;  // Adjust the movement step
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
    }

    public double getX() {
        return getTranslateX();
    }

    public double getY() {
        return getTranslateY();
    }

    public void moveLeft() {
        double newX = getTranslateX() - MOVE_STEP;
        setTranslateX(Math.max(newX, MIN_X));
        System.out.println(getTranslateX());
    }

    public void moveRight() {
        double newX = getTranslateX() + MOVE_STEP;
        setTranslateX(Math.min(newX, MAX_X));
        System.out.println(getTranslateX());
    }
}

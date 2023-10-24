package com.opcode.spaceinvader2.entity;

import com.opcode.spaceinvader2.Launcher;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.Objects;

public class EnemyShip extends Pane {
    private ImageView shipImageView;
    private SequentialTransition transition;

    public EnemyShip() {
        // Load the image for the player's ship
        Image shipImage = new Image(Objects.requireNonNull(Launcher.class.getResource("/com/opcode/spaceinvader2/image/Nave.png")).toExternalForm());
        shipImageView = new ImageView(shipImage);

        getChildren().add(shipImageView);

        // Set initial position
        setTranslateX(100);
        setTranslateY(100);

        // Set up movement animations
        setupMovementAnimations();
    }
    private void setupMovementAnimations() {
        // Movement to the right
        TranslateTransition moveRight = new TranslateTransition(Duration.seconds(2), this);
        moveRight.setFromX(0);
        moveRight.setToX(450);

        // Movement to the left (return journey)
        TranslateTransition moveLeft = new TranslateTransition(Duration.seconds(2), this);
        moveLeft.setFromX(450);
        moveLeft.setToX(0);

        // Sequentially play the animations
        transition = new SequentialTransition(moveRight, moveLeft);
        transition.setCycleCount(TranslateTransition.INDEFINITE);
        transition.play();
    }
}

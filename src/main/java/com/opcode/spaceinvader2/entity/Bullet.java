package com.opcode.spaceinvader2.entity;

import com.opcode.spaceinvader2.Launcher;
import com.opcode.spaceinvader2.view.Platform;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.Objects;

public class Bullet extends Pane {
    private static final Duration BULLET_DURATION = Duration.seconds(2);
    private ImageView bulletImagePreview;
    private TranslateTransition transition;



    public Bullet(double initialX, double initialY) {
        //Load the image for the bullet
        Image bulletImage = new Image(Objects.requireNonNull(Launcher.class.getResource("/com/opcode/spaceinvader2/image/beams.png")).toExternalForm());
        bulletImagePreview = new ImageView(bulletImage);
        getChildren().add(bulletImagePreview);

        // Set initial position relative to the player ship
        setTranslateX(initialX / Platform.PANE_WIDTH + 10);
        System.out.println("Bullet X" + getTranslateX());
        setTranslateY(initialY - Platform.PANE_HEIGHT + 100);

        // Set up movement animation
        transition = new TranslateTransition(BULLET_DURATION, this);
        transition.setByY(-initialY);  // Move upward relative to the current position
        transition.setOnFinished(event -> getChildren().remove(this));  // Remove bullet when it reaches the top
    }

    public void move() {
        transition.play();
    }
}

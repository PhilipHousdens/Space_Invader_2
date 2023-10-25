package com.opcode.spaceinvader2.Model;

import com.opcode.spaceinvader2.Launcher;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.Objects;

public class AnimationSprite extends ImageView {
    private static final int NUM_FRAMES = 4;
    private static final int FRAME_WIDTH = 64;
    private static final int FRAME_HEIGHT = 64;

    Launcher launcher;

    private Image spriteSheet;
    private int currentFrame = 0;
    private int direction = 1; // 1 for right, -1 for left
    private double moveDistance = 5.0;

    public AnimationSprite(String imagePath) {
        Image spriteImage = new Image(Objects.requireNonNull(getClass().getResource(imagePath)).toExternalForm());
        ImageView spriteImageView = new ImageView(spriteImage);


        // Set up animation
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(100), event -> animateFrame())
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void animateFrame() {
        currentFrame = (currentFrame + 1) % NUM_FRAMES;
        setViewport(generateFrameRectangle(currentFrame));
    }

    public void move() {
        setTranslateX(getTranslateX() + direction * moveDistance);
    }

    private javafx.geometry.Rectangle2D generateFrameRectangle(int frameIndex) {
        double x = frameIndex * FRAME_WIDTH;
        return new javafx.geometry.Rectangle2D(x, 0, FRAME_WIDTH, FRAME_HEIGHT);
    }
}

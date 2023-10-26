package com.opcode.spaceinvader2.Model;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class AnimationSprite {

    private final ImageView imageView;
    private final Timeline timeline;
    private final int numFrames;
    private final int columns;
    private final int offsetX;
    private final int offsetY;
    private final int width;
    private final int height;
    private final Image spriteSheet;

    public AnimationSprite(
            ImageView imageView,
            Duration duration,
            int numFrames,
            int columns,
            int offsetX,
            int offsetY,
            int width,
            int height,
            Image spriteSheet
    ) {
        this.imageView = imageView;
        this.numFrames = numFrames;
        this.columns = columns;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.width = width;
        this.height = height;
        this.spriteSheet = spriteSheet;

        this.timeline = new Timeline(
                new KeyFrame(
                        duration,
                        event -> updateFrame()
                )
        );
    }

    public void play() {
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    public void stop() {
        timeline.stop();
    }

    private void updateFrame() {
        int currentFrame = (int) ((imageView.getViewport().getMinX() + offsetX) / width);
        currentFrame = (currentFrame + 1) % numFrames;

        int x = (currentFrame % columns) * width + offsetX;
        int y = (currentFrame / columns) * height + offsetY;

        imageView.setViewport(new Rectangle2D(x, y, width, height));
    }
}

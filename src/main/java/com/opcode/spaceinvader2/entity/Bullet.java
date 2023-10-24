package com.opcode.spaceinvader2.entity;

import com.opcode.spaceinvader2.Launcher;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.Objects;

public class Bullet extends Pane {
    private static final double BULLET_DURATION = 5.0;
    private ImageView bulletImagePreview;

    public Bullet(double initialX, double initialY) {
        //Load the image for the bullet
        Image bulletImage = new Image(Objects.requireNonNull(Launcher.class.getResource("/com/opcode/spaceinvader2/image/beams.png")).toExternalForm());
        bulletImagePreview = new ImageView(bulletImage);
        bulletImagePreview.setX(initialX);
        bulletImagePreview.setY(initialY);
    }

    public void moveUp() {
        bulletImagePreview.setY(bulletImagePreview.getY() - (BULLET_DURATION));
    }

    public double getY() {
        return bulletImagePreview.getY();
    }

    public ImageView getBulletImagePreview() {
        return bulletImagePreview;
    }
}

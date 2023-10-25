package com.opcode.spaceinvader2.Player;

import com.opcode.spaceinvader2.Launcher;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Objects;

public class PlayerShip extends Pane {
    private static final int MOVE_STEP = 10;
    private static final int MIN_X = 0;
    private static final int MAX_X = 450;
    private ImageView shipImageView;
    private Rectangle hitbox;

    public PlayerShip() throws RuntimeException {
        // Load the image for the player's ship
        Image shipImage = new Image(Objects.requireNonNull(Launcher.class.getResource("/com/opcode/spaceinvader2/image/DurrrSpaceShip.png")).toExternalForm());
        shipImageView = new ImageView(shipImage);
        getChildren().add(shipImageView);

        // Set initial position
        setTranslateX(245);
        setTranslateY(600);
        System.out.println("Initial Ship Position X: " + getX() + " Y: " + getY());

        // Center the hitbox to the shipImageView
        double hitboxX = getTranslateX();
        double hitboxY = getTranslateY();
        System.out.println("Initial Hitbox Position X: " + hitboxX + " Y: " + hitboxY);

        hitbox = new Rectangle(hitboxX, hitboxY, shipImageView.getFitWidth(), shipImageView.getFitHeight());
    }
    public double getX() {
        return getTranslateX();
    }

    public double getY() {
        return getTranslateY();
    }

    // Creating hit box
    public Rectangle getHitbox() {
        return hitbox;
    }

    public void moveLeft() {
        double newX = getTranslateX() - MOVE_STEP;
        setTranslateX(Math.max(newX, MIN_X));
        updateHitboxPosition();
    }

    public void moveRight() {
        double newX = getTranslateX() + MOVE_STEP;
        setTranslateX(Math.min(newX, MAX_X));
        updateHitboxPosition();
    }

    private void updateHitboxPosition() {
        hitbox.setX(getTranslateX() + 35);
        hitbox.setY(getTranslateY() + 20);

    }

    public ImageView getShipImageView() {
        return shipImageView;
    }
}

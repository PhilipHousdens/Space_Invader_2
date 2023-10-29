package com.opcode.spaceinvader2.Player;

import com.opcode.spaceinvader2.Launcher;
import com.opcode.spaceinvader2.Model.AnimatedSprite;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

import java.util.Objects;

public class PlayerShip extends Pane {
    static final int MOVE_STEP = 10;
    private static final int MIN_X = 0;
    private static final int MAX_X = 450;
    private AnimatedSprite shipImageView;
    private Rectangle hitbox;



    public PlayerShip() throws RuntimeException {
        // Load the image for the player's ship
        Image shipImage = new Image(Objects.requireNonNull(Launcher.class.getResource("/com/opcode/spaceinvader2/Pics/PlayerShip.png")).toExternalForm());
        shipImageView = new AnimatedSprite(shipImage,4, 4, 1, 0,0, 62,84);
        shipImageView.setSpeed(1);
        shipImageView.setTicksPerUpdate(2);
        getChildren().add(this.shipImageView);

        // Set initial position
        setTranslateX(245);
        setTranslateY(600);

        Launcher.logger.info("SHIP SPAWN POSITION =  X: {}, {}", getX(), getY());

        // Center the hitbox to the shipImageView
        double hitboxX = getTranslateX();
        double hitboxY = getTranslateY();

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
        double clampedX = Math.max(newX, MIN_X);
        setTranslateX(clampedX);
        updateHitboxPosition();
        Launcher.logger.debug("After moveLeft: X = {}, Hitbox X = {}", getTranslateX(), hitbox.getX());
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
    public void tick() {
        shipImageView.tick();
    }

    public ImageView getShipImageView() {
        return shipImageView;
    }
}

package com.opcode.spaceinvader2.entity;

import com.opcode.spaceinvader2.Launcher;
import javafx.animation.SequentialTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

import java.util.Objects;
import java.util.Random;

public class EnemyShip extends Pane {
    static final Random rand = new Random();
    private int direction = rand.nextInt(2) == 0 ? -1 : 1;  // -1 for left, 1 for right
    private double moveDistance = 0.2  + rand.nextDouble() * 1.8;  // Random distance between 1 and 6
    private final double gameWidth;
    private double verticalSpeed = 40;
    private ImageView shipImageView;
    private Rectangle hitbox;

    public EnemyShip(double initialX, double initialY, double gameWidth) {
        this.gameWidth = gameWidth;
        // Load the image for the player's ship
        Image shipImage = new Image(Objects.requireNonNull(Launcher.class.getResource("/com/opcode/spaceinvader2/image/Nave.png")).toExternalForm());
        shipImageView = new ImageView(shipImage);

        getChildren().add(shipImageView);

        // Set initial position
        setTranslateX(initialX);
        setTranslateY(initialY);
        hitbox = new Rectangle(initialX, initialY, shipImage.getWidth()/2, shipImage.getHeight()/2);

    }

    public ImageView getShipImageView() {
        return shipImageView;
    }

    public double getX() {
        return getShipImageView().getX();
    }

    public double getY() {
        return getShipImageView().getY();
    }

    public void move() {
        double newX = getX() + direction * moveDistance;

        // Check boundaries and change direction if reached the edges
        if (newX < 0 || newX > gameWidth - getShipImageView().getBoundsInLocal().getWidth()) {
            direction = -direction;
            newX = getX() + direction * moveDistance;
            double newY = getShipImageView().getY() + verticalSpeed;
            getShipImageView().setY(newY);
        }

        getShipImageView().setX(newX);

        hitbox.setX(newX);
        hitbox.setY(getShipImageView().getY());
    }

    public Rectangle getHitbox() {
        return hitbox;
    }

}

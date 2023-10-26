package com.opcode.spaceinvader2.Boss;


import com.opcode.spaceinvader2.Enemy.EnemyBullet;
import com.opcode.spaceinvader2.Launcher;
import com.opcode.spaceinvader2.Model.AnimationSprite;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.net.URL;
import java.util.Objects;
import java.util.Random;


public class Boss extends Pane {
    static final Random rand = new Random();
    private int direction = rand.nextInt(2) == 0 ? -1 : 1;  // -1 for left, 1 for right
    private double gameWidth;
    private double speed = 3;  // Adjust this value based on how fast you want the boss to move
    private double moveDistance = speed * direction;
    private double verticalSpeed = 0; // Adjust as needed
    private ImageView shipImageView;
    private Rectangle hitbox;
    private double initialX;
    private double initialY;

    public Boss(double gameWidth, double initialX, double initialY) {
        this.gameWidth = gameWidth;
        this.initialX = initialX;
        this.initialY = initialY;
        // Load the image for the player's ship
        Image shipImage = new Image(Objects.requireNonNull(Launcher.class.getResource("/com/opcode/spaceinvader2/image/Boss.png")).toExternalForm());
        shipImageView = new ImageView(shipImage);
        getChildren().addAll(shipImageView);

        shipImageView.setX(initialX);
        shipImageView.setY(initialY);

        System.out.println("Initial Ship Position X: " + initialX + " Y: " + initialY);

        hitbox = new Rectangle(initialX, initialY, shipImageView.getFitWidth(), shipImageView.getFitHeight());


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

    public Rectangle getBossHitBox() {
        return hitbox;
    }

    public BossBullet shoot() {
        BossBullet bossBullet = new BossBullet(getX() + getShipImageView().getFitWidth() / 2 + 10, getY() + shipImageView.getFitHeight());
        return bossBullet;
    }

    public boolean decideToShoot() {
        return rand.nextInt(100) < 5;
    }
}

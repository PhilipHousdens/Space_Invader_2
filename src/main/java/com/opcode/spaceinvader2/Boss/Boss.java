package com.opcode.spaceinvader2.Boss;

import com.opcode.spaceinvader2.Enemy.EnemyBullet;
import com.opcode.spaceinvader2.Launcher;
import com.opcode.spaceinvader2.Model.AnimationSprite;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

import java.util.Objects;
import java.util.Random;

public class Boss extends AnimationSprite {
    static final Random rand = new Random();
    private int direction = rand.nextInt(2) == 0 ? -1 : 1;  // -1 for left, 1 for right
    private double moveDistance = 0.2 + rand.nextDouble() * 1.8;  // Random distance between 1 and 6
    private final double gameWidth;
    private double verticalSpeed = 60;
    private int shotsFired = 0;
    private static final int MAX_SHOTS = 2;
    private Rectangle hitbox;

    public Boss(double initialX, double initialY, double gameWidth) {
        super("/com/opcode/spaceinvader2/image/beams.png");
        this.gameWidth = gameWidth;

        // Set initial position
        setTranslateX(initialX);
        setTranslateY(initialY);
        hitbox = new Rectangle(initialX, initialY, getFitWidth(), getFitHeight() / 2);
    }

    @Override
    public void move() {
        super.move(); // Call the move method of the AnimationSprite

        // Check boundaries and change direction if reached the edges
        if (getTranslateX() < 0 || getTranslateX() > gameWidth - getBoundsInLocal().getWidth()) {
            direction = -direction;
            setTranslateX(getTranslateX() + direction * moveDistance);
            setTranslateY(getTranslateY() + verticalSpeed);
        }

        hitbox.setX(getTranslateX());
        hitbox.setY(getTranslateY());
    }

    public Rectangle getHitBox() {
        return hitbox;
    }

    public EnemyBullet shoot() {
        EnemyBullet enemyBullet = new EnemyBullet(getTranslateX() + getFitWidth() / 2 + 10, getTranslateY() + getFitHeight());
        return enemyBullet;
    }

    public boolean decideToShoot() {
        return rand.nextInt(1000) < 5;
    }
}

package com.opcode.spaceinvader2.Enemy;

import com.opcode.spaceinvader2.Launcher;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

import java.util.Objects;


public class EnemyBullet extends Pane {
    private static final double BULLET_DURATION = 0.25;
    private ImageView bulletImagePreview;
    private Rectangle hitbox;

    public EnemyBullet(double initialX, double initialY) {
        //Load the image for the bullet
        Image bulletImage = new Image(Objects.requireNonNull(Launcher.class.getResource("/com/opcode/spaceinvader2/image/beams.png")).toExternalForm());
        bulletImagePreview = new ImageView(bulletImage);
        bulletImagePreview.setX(initialX);
        bulletImagePreview.setY(initialY);
        hitbox = new Rectangle(initialX, initialY, bulletImage.getWidth(), bulletImage.getHeight());
    }

    public void moveDown() {
        bulletImagePreview.setY(bulletImagePreview.getY() + (BULLET_DURATION));
        hitbox.setY(hitbox.getY() + (BULLET_DURATION));
    }

    public double getY() {
        return bulletImagePreview.getY();
    }

    public ImageView getBulletImagePreview() {
        return bulletImagePreview;
    }



    public Rectangle getHitbox() {
        return hitbox;
    }
}

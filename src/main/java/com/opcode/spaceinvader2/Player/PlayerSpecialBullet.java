package com.opcode.spaceinvader2.Player;
import com.opcode.spaceinvader2.Launcher;
import com.opcode.spaceinvader2.Model.AnimatedSprite;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import java.util.Objects;
public class PlayerSpecialBullet {
    private static final double BULLET_DURATION = 5.0;
    private AnimatedSprite bulletImagePreview;
    private Rectangle hitbox;
    private boolean isActive = true;
    public PlayerSpecialBullet(double initialX, double initialY) {
        //Load the image for the bullet
        Image bulletImage = new Image(Objects.requireNonNull(Launcher.class.getResource("/com/opcode/spaceinvader2/Pics/SpeacialBullet.png")).toExternalForm());
        bulletImagePreview = new AnimatedSprite(bulletImage, 1, 1, 3, 0, 0, 57, 328);

        bulletImagePreview.setX(initialX);
        bulletImagePreview.setY(initialY);
        hitbox = new Rectangle(initialX, initialY, bulletImage.getWidth()/2, bulletImage.getHeight()/2);
    }
    public void moveUp() {
        bulletImagePreview.tick();
        bulletImagePreview.setY(bulletImagePreview.getY() - (BULLET_DURATION));
        hitbox.setY(hitbox.getY() - (BULLET_DURATION));
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
// public void stop() {
// getChildren().removeAll(bulletImagePreview, hitbox);
// }
}
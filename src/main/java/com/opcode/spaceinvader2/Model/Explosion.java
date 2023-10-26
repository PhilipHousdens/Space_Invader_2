package com.opcode.spaceinvader2.Model;

import com.opcode.spaceinvader2.Launcher;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class Explosion {
    private ImageView explosionImageView;

    public Explosion(double x, double y) {
        Image explosionImage = new Image(Objects.requireNonNull(Launcher.class.getResource("/com/opcode/spaceinvader2/Pics/Explose.png")).toExternalForm() ); // Replace with the path to your explosion image
        explosionImageView = new ImageView(explosionImage);
        explosionImageView.setX(x);
        explosionImageView.setY(y);
    }

    public ImageView getExplosionImageView() {
        return explosionImageView;
    }

    // Add methods to play and stop the explosion animation if needed
}


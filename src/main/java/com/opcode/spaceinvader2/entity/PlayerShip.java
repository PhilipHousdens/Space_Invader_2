package com.opcode.spaceinvader2.entity;

import com.opcode.spaceinvader2.Launcher;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.Objects;

public class PlayerShip extends Pane {
    private ImageView shipImageView;

    public PlayerShip() throws RuntimeException, IOException {
        // Load the image for the player's ship
        Image shipImage = new Image(Objects.requireNonNull(Launcher.class.getResource("/com/opcode/spaceinvader2/image/DurrrSpaceShip.png")).toExternalForm());
        shipImageView = new ImageView(shipImage);
        getChildren().add(shipImageView);

        // Set initial position
        setTranslateX(400);
        setTranslateY(500);
    }
}

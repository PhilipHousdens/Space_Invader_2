package com.opcode.spaceinvader2.view;

import com.opcode.spaceinvader2.Launcher;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.Objects;

public class Platform extends Pane {
    public static final int PANE_WIDTH = 530;
    public static final int PANE_HEIGHT = 730;

    private Image background;

    public Platform() {
        background = new Image(Objects.requireNonNull(Launcher.class.getResource("/com/opcode/spaceinvader2/image/bg_02_v.png")).toExternalForm());
        ImageView backgroundImg = new ImageView(background);
        backgroundImg.setFitHeight(background.getHeight());
        backgroundImg.setFitWidth(background.getWidth());

        getChildren().addAll(backgroundImg);
    }
}

package com.opcode.spaceinvader2.view;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

import java.util.Objects;

public class Platform extends Pane {
    private static final int PANE_WIDTH = 750;
    private static final int PANE_HEIGHT = 630;

    private Image backgroundImg;

    Platform() {
        backgroundImg = new Image(Objects.requireNonNull(getClass().getResource("com/opcode/spaceinvader2/image/bg_02_v.png")).toExternalForm());
    }
}

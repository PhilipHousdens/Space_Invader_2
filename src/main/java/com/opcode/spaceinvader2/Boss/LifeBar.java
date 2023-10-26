package com.opcode.spaceinvader2.Boss;


import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class LifeBar {

    private static final int INITIAL_LIFE = 100;
    private int currentLife = INITIAL_LIFE;

    private ProgressBar lifeBar;

    public void initializeLifeBar(Pane root , double x, double y) {
        // Life bar components
        lifeBar = new ProgressBar(1.0);
        lifeBar.setLayoutX(x);
        lifeBar.setLayoutY(y);

        lifeBar.setStyle("-fx-accent: #AF2492");


        // Add components to the root layout
        root.getChildren().addAll(lifeBar);
    }

    public void updateLifeBar(int amount) {
        // Update life value
        currentLife += amount;

        // Ensure that life value stays within bounds (0 to 100)
        currentLife = Math.max(0, Math.min(currentLife, 100));

        // Update the life bar value and label
        lifeBar.setProgress(currentLife / 100.0);
    }

    public int getCurrentLife() {
        return currentLife;
    }
}

package com.opcode.spaceinvader2;

import com.opcode.spaceinvader2.entity.PlayerShip;
import com.opcode.spaceinvader2.view.Platform;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Launcher extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Platform platform = new Platform();
        PlayerShip playerShip = new PlayerShip();
        Scene scene = new Scene(platform, Platform.PANE_WIDTH, Platform.PANE_HEIGHT);

        // Handle the key press
        scene.setOnKeyPressed(event -> playerShip.handleKeyPress(event.getCode()));

        stage.setScene(scene);
        stage.setTitle("OP Space Invader");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}

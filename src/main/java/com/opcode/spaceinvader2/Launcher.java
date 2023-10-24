package com.opcode.spaceinvader2;

import com.opcode.spaceinvader2.view.Platform;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Launcher extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Platform platform = new Platform();
        Scene scene = new Scene(platform, Platform.PANE_WIDTH, Platform.PANE_HEIGHT);

        stage.setScene(scene);
        stage.setTitle("OP Space Invader");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
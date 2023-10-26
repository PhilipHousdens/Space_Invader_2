package com.opcode.spaceinvader2.Model;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class AnimatedSprite extends ImageView {
    private int count, columns, rows, offsetX, offsetY, width, height, curIndex, curColumnIndex, curRowIndex;

    private int speed = 1;
    private int tickCounter = 0;
    private int ticksPerUpdate = 2;

    public AnimatedSprite(Image image, int count, int columns, int rows, int offsetX, int offsetY, int width, int height) {
        this.setImage(image);
        this.count = count;
        this.columns = columns;
        this.rows = rows;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.width = width;
        this.height = height;
        this.curIndex = 0;
        this.curColumnIndex = 0;
        this.curRowIndex = 0;
        this.setViewport(new Rectangle2D(offsetX, offsetY, width, height));
    }

    public void tick() {
        if (tickCounter % ticksPerUpdate == 0) {
            curColumnIndex = curIndex % columns;
            curRowIndex = curIndex / columns;
            curIndex = (curIndex + 1) % (columns * rows * speed);
            interpolate();
        }
        tickCounter++;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setTicksPerUpdate(int ticksPerUpdate) {
        this.ticksPerUpdate = ticksPerUpdate;
    }

    protected void interpolate() {
        final int x = curColumnIndex * width + offsetX;
        final int y = curRowIndex * height + offsetY;
        this.setViewport(new Rectangle2D(x, y, width, height));
    }
}

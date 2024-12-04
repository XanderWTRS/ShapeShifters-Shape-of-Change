package com.Xander.shapeshifters;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class ConveyorBeltTile {
    private float x, y, width, height;
    private float speedX, speedY;

    public ConveyorBeltTile(float x, float y, float width, float height, float speedX, float speedY) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speedX = speedX;
        this.speedY = speedY;
    }

    public void render(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(0, 0, 25, 1);
        shapeRenderer.rect(x, y, width, height);
    }

    public boolean checkCollision(float playerX, float playerY, float playerWidth, float playerHeight) {
        return x < playerX + playerWidth && x + width > playerX &&
            y < playerY + playerHeight && y + height > playerY;
    }

    public float getSpeedX() {
        return speedX;
    }

    public float getSpeedY() {
        return speedY;
    }
}

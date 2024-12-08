package com.Xander.shapeshifters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ConveyorBeltTile {
    private final float x, y, width, height;
    private final float speedX, speedY;
    private final Texture texture;

    public ConveyorBeltTile(float x, float y, float speedX, float speedY, String texturePath) {
        this.x = x;
        this.y = y;
        width = 100;
        height = 100;
        this.speedX = speedX;
        this.speedY = speedY;
        this.texture = new Texture(texturePath);
    }

    public void render(SpriteBatch spriteBatch) {
        spriteBatch.draw(texture, x,y,width,height);
    }

    public boolean checkCollision(float playerX, float playerY, float playerWidth, float playerHeight) {
        return x < playerX + playerWidth && x + width > playerX &&
            y < playerY + playerHeight && y + height > playerY;
    }
    public void dispose()
    {
        texture.dispose();
    }

    public float getSpeedX() {
        return speedX;
    }

    public float getSpeedY() {
        return speedY;
    }

}

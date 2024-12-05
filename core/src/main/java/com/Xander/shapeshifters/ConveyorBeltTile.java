package com.Xander.shapeshifters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ConveyorBeltTile {
    private float x, y, width, height;
    private float speedX, speedY;
    private Texture texture;

    public ConveyorBeltTile(float x, float y, float width, float height, float speedX, float speedY, String texturePath) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
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

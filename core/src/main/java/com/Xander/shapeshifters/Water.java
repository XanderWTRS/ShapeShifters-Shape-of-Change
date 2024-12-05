package com.Xander.shapeshifters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Water
{
    private float x, y, width, height;
    private Texture texture;

    public Water(float x, float y, float width, float height, String texturePath) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.texture = new Texture(texturePath);
    }

    public void render(SpriteBatch spriteBatch) {
        spriteBatch.draw(texture,x,y,width,height);
    }

    public boolean checkCollision(float playerX, float playerY, float playerWidth, float playerHeight) {
        return playerX < x + width && playerX + playerWidth > x && playerY < y + height && playerY + playerHeight > y;
    }
    public void dispose()
    {
        texture.dispose();
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }
}

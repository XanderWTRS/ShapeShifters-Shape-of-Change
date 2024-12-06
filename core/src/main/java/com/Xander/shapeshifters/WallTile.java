package com.Xander.shapeshifters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class WallTile {
    private float x, y, width, height;
    private Texture wallTexture;

    public WallTile(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.wallTexture = new Texture("tiles/Wall-img.png");
    }

    public void render(SpriteBatch spriteBatch) {
        spriteBatch.draw(wallTexture, x, y, width, height);
    }

    public void dispose() {
        wallTexture.dispose();
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

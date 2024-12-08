package com.Xander.shapeshifters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class WallTile {
    private final float x, y, width, height;
    private final Texture wallTexture;

    public WallTile(float x, float y) {
        this.x = x;
        this.y = y;
        width = 100;
        height = 100;
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

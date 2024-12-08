package com.Xander.shapeshifters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class StickyTile
{
    private final float x, y, width, height;
    private final Texture texture;

    public StickyTile(float x, float y) {
        this.x = x;
        this.y = y;
        width = 100;
        height = 100;
        texture = new Texture("tiles/Mud-img.png");
    }

    public void render(SpriteBatch spriteBatch)
    {
        spriteBatch.draw(texture, x, y, width, height);
    }
    public boolean checkCollision(float playerX, float playerY, float playerWidth, float playerHeight)
    {
        return playerX < x + width && playerX + playerWidth > x && playerY < y + height && playerY + playerHeight > y;
    }
    public void dispose()
    {
        texture.dispose();
    }
}

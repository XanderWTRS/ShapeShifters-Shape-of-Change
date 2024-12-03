package com.Xander.shapeshifters;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class StickyTile
{
    private float x, y, width, height;

    public StickyTile(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void render(ShapeRenderer shapeRenderer)
    {
        shapeRenderer.setColor(Color.ORANGE);
        shapeRenderer.rect(x, y, width, height);
    }
    public boolean checkCollision(float playerX, float playerY, float playerWidth, float playerHeight)
    {
        return playerX < x + width && playerX + playerWidth > x && playerY < y + height && playerY + playerHeight > y;
    }
}

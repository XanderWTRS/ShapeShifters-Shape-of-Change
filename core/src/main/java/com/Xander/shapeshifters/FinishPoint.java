package com.Xander.shapeshifters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
public class FinishPoint
{
    private float x,y, width, height;
    private Texture finishTexture;

    public FinishPoint(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.finishTexture = new Texture("tiles/FinishPoint.png");
    }

    public void render(SpriteBatch spriteBatch)
    {
        spriteBatch.draw(finishTexture, x,y,width,height);
    }

    public boolean checkCollision(float playerX, float playerY, float playerWidth, float playerHeight)
    {
        return playerX < x + width && playerX + playerWidth > x && playerY < y + height && playerY + playerHeight > y;
    }
}

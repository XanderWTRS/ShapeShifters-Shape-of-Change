package com.Xander.shapeshifters;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class Player
{
    private Vector2 position;
    private float width;
    private float height;

    public Player(float x, float y, float width, float height)
    {
        this.position = new Vector2(x,y);
        this.width = width;
        this.height = height;
    }
    public void render(ShapeRenderer shapeRenderer)
    {
        shapeRenderer.rect(position.x, position.y, width, height);
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public void update(float deltaTime)
    {

    }

    public Vector2 getPosition()
    {
        return position;
    }
}

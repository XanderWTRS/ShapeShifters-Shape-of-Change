package com.Xander.shapeshifters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Coin {
    private boolean collected;
    private final Rectangle bounds;
    private final Texture texture;

    public Coin(float x, float y) {
        bounds = new Rectangle(x, y, 75, 75);
        texture = new Texture("tiles/Coin.png");
        collected = false;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public boolean isCollected() {
        return collected;
    }

    public void collect() {
        collected = true;
    }

    public void render(SpriteBatch batch) {
        if (!collected) {
            batch.draw(texture, bounds.x, bounds.y, bounds.width, bounds.height);
        }
    }

    public void dispose() {
        texture.dispose();
    }
}

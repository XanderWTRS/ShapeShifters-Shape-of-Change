package com.Xander.shapeshifters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class FragileTile {
    private float x, y, width, height;
    private Texture intactTexture;
    private Texture brokenTexture;
    private boolean isBroken;
    private boolean wasPlayerOver;

    public FragileTile(float x, float y) {
        this.x = x;
        this.y = y;
        width = 100;
        height = 100;
        this.intactTexture = new Texture("tiles/FragileTile-img.png");
        this.brokenTexture = new Texture("tiles/FragileTileBroken-img.png");
        this.isBroken = false;
        this.wasPlayerOver = false;
    }

    public void render(SpriteBatch spriteBatch) {
        if (isBroken) {
            spriteBatch.draw(brokenTexture, x, y, width, height);
        } else {
            spriteBatch.draw(intactTexture, x, y, width, height);
        }
    }

    public void checkPlayerInteraction(Player player) {
        boolean isPlayerOver = isPlayerOnTile(player);

        if (isPlayerOver) {
            wasPlayerOver = true;
        } else if (wasPlayerOver) {
            if (!player.getCurrentShape().equals(ShapeType.SQUARE)) {
                isBroken = true;
            }
            wasPlayerOver = false;
        }
    }
    public boolean isCollidingWith(Rectangle playerBounds) {
        return !(playerBounds.x + playerBounds.width <= x || playerBounds.x >= x + width ||
            playerBounds.y + playerBounds.height <= y || playerBounds.y >= y + height);
    }
    private boolean isPlayerOnTile(Player player) {
        return player.getPosition().x < x + width && player.getPosition().x + player.getWidth() > x &&
            player.getPosition().y < y + height && player.getPosition().y + player.getHeight() > y;
    }

    public boolean isBlocked() {
        return isBroken;
    }
    public void dispose() {
        intactTexture.dispose();
        brokenTexture.dispose();
    }
}

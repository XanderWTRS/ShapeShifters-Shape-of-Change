package com.Xander.shapeshifters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class OneWayTile {
    private float x, y, width, height;
    private Texture texture;
    private String direction;
    private String allowedDirection;


    public OneWayTile(float x, float y, float width, float height, String direction, String allowedDirection) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.direction = direction;
        this.texture = new Texture("tiles/OneWay" + direction + "-img.png");
        this.allowedDirection = allowedDirection;
    }

    public void render(SpriteBatch spriteBatch) {
        spriteBatch.draw(texture, x, y, width, height);
    }

    public boolean checkCollision(float playerX, float playerY, float playerWidth, float playerHeight) {
        return playerX < x + width && playerX + playerWidth > x &&
            playerY < y + height && playerY + playerHeight > y;
    }
    public boolean allowMovement(Player player) {
        String playerDirection = player.getCurrentDirection();

        if (playerDirection.equals("LEFT") && allowedDirection.equals("RIGHT")) {
            return false;
        } else if (playerDirection.equals("RIGHT") && allowedDirection.equals("LEFT")) {
            return false;
        } else if (playerDirection.equals("UP") && allowedDirection.equals("DOWN")) {
            return false;
        } else if (playerDirection.equals("DOWN") && allowedDirection.equals("UP")) {
            return false;
        }
        return true;
    }

    public String getAllowedDirection() {
        return allowedDirection;
    }

    public void setAllowedDirection(String allowedDirection) {
        this.allowedDirection = allowedDirection;
    }

    public void dispose() {
        texture.dispose();
    }
}

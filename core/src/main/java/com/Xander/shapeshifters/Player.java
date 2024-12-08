package com.Xander.shapeshifters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import java.util.List;

public class Player
{
    private Vector2 position;
    private float width;
    private float height;
    private float speed;
    private ShapeType currentShape;
    private float dashDistance;
    public boolean canDash;
    private float dashCooldownTime = 0.2f;
    private float dashCooldownTimer = 0f;
    private Sound dashSound;
    private String currentDirection;

    public Player(float x, float y, float width, float height)
    {
        this.position = new Vector2(x,y);
        this.width = width;
        this.height = height;
        this.speed = 200;
        this.currentShape = ShapeType.SQUARE;
        this.dashDistance = 300;
        this.canDash = true;
        dashSound = Gdx.audio.newSound(Gdx.files.internal("sounds/dash_sound.mp3"));
    }
    public void render(ShapeRenderer shapeRenderer)
    {
        switch(currentShape)
        {
            case SQUARE:
                shapeRenderer.setColor(1,0,0,1);
                shapeRenderer.rect(position.x, position.y, width,height);
                break;
            case CIRCLE:
                shapeRenderer.setColor(0, 1, 0, 1);
                shapeRenderer.circle(position.x + width / 2, position.y + height / 2, width / 2);
                break;
            case TRIANGLE:
                shapeRenderer.setColor(0, 0, 1, 1);
                shapeRenderer.triangle(position.x, position.y, position.x + width, position.y, position.x + width / 2, position.y + height);
                break;
            case STAR:
                shapeRenderer.setColor(1, 1, 0, 1);
                drawStar(shapeRenderer);
                break;
        }
    }
    public void update(float deltaTime, List<Water> waterBlocks, boolean onStickyTile, List<ConveyorBeltTile> conveyorBeltTiles, List<OneWayTile> oneWayTiles, List<FragileTile> fragileTiles, List<WallTile> wallTiles, List<Coin> coins)
    {
        float velocityX = 0;
        float velocityY = 0;
        currentDirection = "NONE";

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
            velocityX = -speed * deltaTime;
            currentDirection = "LEFT";
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            velocityX = speed * deltaTime;
            currentDirection = "RIGHT";
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
            velocityY = speed * deltaTime;
            currentDirection = "UP";
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) {
            velocityY = -speed * deltaTime;
            currentDirection = "DOWN";
        }

        boolean canMove = true;
        for (OneWayTile oneWayTile : oneWayTiles) {
            if (oneWayTile.checkCollision(position.x + velocityX, position.y + velocityY, width, height)) {
                if (!oneWayTile.allowMovement(this)) {
                    canMove = false;
                    break;
                }
            }
        }
        for (FragileTile fragileTile : fragileTiles) {
            fragileTile.checkPlayerInteraction(this);
            if (fragileTile.isBlocked()) {
                Rectangle playerBounds = new Rectangle(position.x + velocityX, position.y + velocityY, width, height);

                if (fragileTile.isCollidingWith(playerBounds)) {

                    if (velocityX > 0) velocityX = 0;
                    if (velocityX < 0) velocityX = 0;
                    if (velocityY > 0) velocityY = 0;
                    if (velocityY < 0) velocityY = 0;
                }
            }
        }

        if (canMove) {
            if (isCollidingWithWater(position.x + velocityX, position.y + velocityY, waterBlocks)) {
                velocityX = 0;
                velocityY = 0;
            }
            if (isCollidingWithWall(position.x + velocityX, position.y + velocityY, wallTiles)) {
                velocityX = 0;
                velocityY = 0;
            }

            position.x += velocityX;
            position.y += velocityY;
        }

        if (currentShape == ShapeType.CIRCLE)
        {
            speed = 400;
        } else {
            speed = 200;
        }
        if(onStickyTile)
        {
            if(currentShape == ShapeType.CIRCLE)
            {
                speed = 400;
            }
            else speed *= 0.25f;
        }

        for (ConveyorBeltTile conveyor : conveyorBeltTiles) {
            if (conveyor.checkCollision(position.x, position.y, width, height)) {
                position.x += conveyor.getSpeedX() * deltaTime;
                position.y += conveyor.getSpeedY() * deltaTime;
                break;
            }
        }


        if (dashCooldownTimer > 0) {
            dashCooldownTimer -= deltaTime;
        }

        float screenWidth = 1920;
        float screenHeight = 1080;
        position.x = MathUtils.clamp(position.x, 0, screenWidth - width);
        position.y = MathUtils.clamp(position.y, 0, screenHeight - height);
    }
    public void switchShape()
    {
        if (currentShape == ShapeType.SQUARE) {
            currentShape = ShapeType.CIRCLE;
        } else if (currentShape == ShapeType.CIRCLE) {
            currentShape = ShapeType.TRIANGLE;
        } else if (currentShape == ShapeType.TRIANGLE) {
            currentShape = ShapeType.STAR;
        } else if (currentShape == ShapeType.STAR) {
            currentShape = ShapeType.SQUARE;
        }
    }
    private void drawStar(ShapeRenderer shapeRenderer)
    {
        float centerX = position.x + width / 2;
        float centerY = position.y + height / 2;
        float radius = width / 2;

        shapeRenderer.line(centerX, centerY + radius, centerX, centerY - radius);
        shapeRenderer.line(centerX - radius, centerY, centerX + radius, centerY);
        shapeRenderer.line(centerX - radius / 2, centerY + radius / 2, centerX + radius / 2, centerY - radius / 2);
    }
    public boolean isCollidingWithWater(float newX, float newY, List<Water> waterBlocks) {
        for (Water water : waterBlocks) {
            if (water.checkCollision(newX, newY, width, height)) {
                return true;
            }
        }
        return false;
    }
    public void dashForward() {
        float velocityX = 0;
        float velocityY = 0;

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
            velocityX = -dashDistance;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            velocityX = dashDistance;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
            velocityY = dashDistance;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) {
            velocityY = -dashDistance;
        }

        position.x += velocityX;
        position.y += velocityY;

        dashSound = Gdx.audio.newSound(Gdx.files.internal("sounds/dash_sound.mp3"));
        AudioManager.playSound(dashSound);

        canDash = false;
    }
    public boolean isCollidingWithWall(float newX, float newY, List<WallTile> walls) {
        for (WallTile wall : walls) {
            Rectangle playerBounds = new Rectangle(newX, newY, width, height);

            if (playerBounds.overlaps(new Rectangle(wall.getX(), wall.getY(), wall.getWidth(), wall.getHeight()))) {
                return true;
            }
        }
        return false;
    }

    public Rectangle getBounds() {
        return new Rectangle(position.x, position.y, width, height);
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

    public Vector2 getPosition()
    {
        return position;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }
    public ShapeType getCurrentShape() {
        return currentShape;
    }
    public float getDashDistance() {
        return dashDistance;
    }
    public void startDashCooldown() {
        dashCooldownTimer = dashCooldownTime;
    }

    public boolean isCanDash() {
        return canDash;
    }

    public float getDashCooldownTime() {
        return dashCooldownTime;
    }

    public float getDashCooldownTimer() {
        return dashCooldownTimer;
    }

    public String getCurrentDirection() {
        return currentDirection;
    }
}

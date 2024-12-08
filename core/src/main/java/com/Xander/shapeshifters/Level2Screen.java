package com.Xander.shapeshifters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import java.util.List;
import java.util.ArrayList;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.utils.Array;

public class Level2Screen implements Screen {

    private final MainGame game;
    private Stage stage;
    private Skin skin;
    private boolean isPaused;
    private Table table;
    private Player player;

    private ShapeRenderer shapeRenderer;
    private FinishPoint finishPoint;
    private List<Water> waterBlocks;
    private List<StickyTile> stickyTiles;
    private List<ConveyorBeltTile> conveyorBeltTiles;
    private List<OneWayTile> oneWayTiles;
    private List<FragileTile> fragileTiles;
    private  List<WallTile> wallTiles;
    private List<Coin> coins;
    private int coinCount = 0;
    private Music level1Music;
    private SpriteBatch spriteBatch;
    private Texture backgroundTexture;
    private BitmapFont font;
    private Texture coinTexture;

    public Level2Screen(MainGame game) {
        this.game = game;
        waterBlocks = new ArrayList<>();
        stickyTiles = new ArrayList<>();
        conveyorBeltTiles = new ArrayList<>();
        oneWayTiles = new ArrayList<>();
        fragileTiles = new ArrayList<>();
        wallTiles = new ArrayList<>();
        coins = new ArrayList<>();
    }

    @Override
    public void show() {
        stage = new Stage();
        spriteBatch = new SpriteBatch();

        font = new BitmapFont();
        font.setColor(Color.BLACK);
        coinCount = game.getTotalCoins();
        coinTexture = new Texture("tiles/Coin-trans.png");

        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("uiskin.json"));

        level1Music = Gdx.audio.newMusic(Gdx.files.internal("sounds/level1_theme.mp3"));
        AudioManager.playMusic(level1Music, true);

        backgroundTexture = new Texture(Gdx.files.internal("tiles/Grass-img.png"));
        backgroundTexture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);

        player = new Player(100, 100, 50, 50);
        finishPoint = new FinishPoint(1500, 800, 100,100);

        coins.add(new Coin(100,200));
        coins.add(new Coin(400,800));
        coins.add(new Coin(900,500));

        table = new Table();
        table.top().left();
        table.setFillParent(true);

        TextButton resumeButton = new TextButton("Resume Game", skin);
        resumeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                resumeGame();
            }
        });

        TextButton mainMenuButton = new TextButton("Back to Main Menu", skin);
        mainMenuButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new MainMenuScreen(game));
            }
        });

        table.center();
        table.add(resumeButton).fillX().uniformX().pad(10).height(50).width(400);
        table.row().pad(10);
        table.add(mainMenuButton).fillX().uniformX().pad(10).height(50).width(400);

        table.setVisible(isPaused);
        stage.addActor(table);
        shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void render(float delta)
    {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        checkCoinCollisions();

        if (isPaused)
        {
            stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
            stage.draw();
        }
        else
        {
            spriteBatch.begin();
            spriteBatch.draw(backgroundTexture, 0,0,0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

            boolean onStickyTile = false;
            for (StickyTile stickyTile : stickyTiles)
            {
                if (stickyTile.checkCollision(player.getPosition().x, player.getPosition().y, player.getWidth(), player.getHeight()))
                {
                    onStickyTile = true;
                    break;
                }
            }

            player.update(Gdx.graphics.getDeltaTime(), waterBlocks, onStickyTile, conveyorBeltTiles, oneWayTiles, fragileTiles, wallTiles, coins);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

            for (Water water : waterBlocks) {
                water.render(spriteBatch);
            }

            for (StickyTile stickyTile : stickyTiles) {
                stickyTile.render(spriteBatch);
            }

            for (ConveyorBeltTile conveyor : conveyorBeltTiles) {
                conveyor.render(spriteBatch);
            }
            for (OneWayTile oneWayTile : oneWayTiles) {
                oneWayTile.render(spriteBatch);
            }
            for (FragileTile fragileTile : fragileTiles) {
                fragileTile.render(spriteBatch);
            }
            for (WallTile wall : wallTiles) {
                wall.render(spriteBatch);
            }
            for (Coin coin : coins) {
                coin.render(spriteBatch);
            }

            float coinImageX = 10;
            float coinImageY = Gdx.graphics.getHeight() - 75;
            float coinImageSize = 75;
            spriteBatch.draw(coinTexture, coinImageX, coinImageY, coinImageSize, coinImageSize);
            float counterX = coinImageX + coinImageSize + 10;
            float counterY = coinImageY + coinImageSize / 2 + 5;
            font.draw(spriteBatch, String.valueOf(coinCount), counterX, counterY);

            player.render(shapeRenderer);
            finishPoint.render(spriteBatch);

            spriteBatch.end();
            shapeRenderer.end();

            stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
            stage.draw();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            togglePause();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            player.switchShape();
        }
        if (finishPoint.checkCollision(player.getPosition().x, player.getPosition().y, player.getWidth(), player.getHeight())) {
            game.addCoins(coinCount);
            game.setScreen(new GameCompleteScreen(game, game.getTotalCoins()));
        }

        if (player.getCurrentShape().equals(ShapeType.TRIANGLE) && Gdx.input.isKeyJustPressed(Input.Keys.Q) && player.getDashCooldownTimer() <= 0) {
            float dashX = player.getPosition().x;
            float dashY = player.getPosition().y;

            if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
                dashX -= player.getDashDistance();
            }
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
                dashX += player.getDashDistance();
            }
            if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
                dashY += player.getDashDistance();
            }
            if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) {
                dashY -= player.getDashDistance();
            }

            boolean canDash = true;
            for (Water water : waterBlocks) {
                if (water.checkCollision(dashX, dashY, player.getWidth(), player.getHeight())) {
                    canDash = false;
                    break;
                }
            }

            if (canDash) {
                player.dashForward();
                player.startDashCooldown();
            }
        }
    }

    private void checkCoinCollisions() {
        for (Coin coin : coins) {
            if (!coin.isCollected() && player.getBounds().overlaps(coin.getBounds())) {
                coin.collect();
                coinCount++;
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void hide() {
        stage.dispose();
        shapeRenderer.dispose();
        if(level1Music != null)
        {
            level1Music.stop();
            level1Music.dispose();
        }
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void dispose()
    {
        for(StickyTile stickyTile : stickyTiles)
        {
            stickyTile.dispose();
        }
        for(Water waterTile : waterBlocks)
        {
            waterTile.dispose();
        }
        for(ConveyorBeltTile conveyorBeltTile : conveyorBeltTiles)
        {
            conveyorBeltTile.dispose();
        }
        for (OneWayTile oneWayTile : oneWayTiles) {
            oneWayTile.dispose();
        }
        for (FragileTile fragileTile : fragileTiles) {
            fragileTile.dispose();
        }
        if (stage != null) {
            stage.dispose();
        }
        if (spriteBatch != null) {
            spriteBatch.dispose();
        }
        if (backgroundTexture != null) {
            backgroundTexture.dispose();
        }
        if (font != null) {
            font.dispose();
        }
        coinTexture.dispose();
        shapeRenderer.dispose();
        skin.dispose();
    }

    public void togglePause() {
        isPaused = !isPaused;
        if (table != null) {
            table.setVisible(isPaused);
        }
    }

    private void resumeGame() {
        isPaused = false;
        table.setVisible(false);
    }
}

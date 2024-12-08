package com.Xander.shapeshifters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameCompleteScreen implements Screen {

    private final MainGame game;
    private SpriteBatch spriteBatch;
    private BitmapFont font;
    private int totalCoins;

    public GameCompleteScreen(MainGame game, int totalCoins) {
        this.game = game;
        this.totalCoins = totalCoins;
        spriteBatch = new SpriteBatch();
        font = new BitmapFont();
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        spriteBatch.begin();
        font.draw(spriteBatch, "GAME COMPLETE!", Gdx.graphics.getWidth() / 2f - 100, Gdx.graphics.getHeight() / 2f + 50);
        font.draw(spriteBatch, "Total Coins: " + (totalCoins - 1), Gdx.graphics.getWidth() / 2f - 100, Gdx.graphics.getHeight() / 2f);
        spriteBatch.end();
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
        font.dispose();
    }
}

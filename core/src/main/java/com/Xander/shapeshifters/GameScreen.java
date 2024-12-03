package com.Xander.shapeshifters;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class GameScreen implements Screen {
    private ShapeRenderer shapeRenderer;
    private Player player;

    public GameScreen(MainGame game) {
        shapeRenderer = new ShapeRenderer();
        player = new Player(100, 100, 50, 50);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        player.update(Gdx.graphics.getDeltaTime());

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        player.render(shapeRenderer);
        shapeRenderer.end();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void hide() {
        shapeRenderer.dispose();
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void dispose() {
    }
}

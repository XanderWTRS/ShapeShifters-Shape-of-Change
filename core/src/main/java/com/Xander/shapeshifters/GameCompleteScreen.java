package com.Xander.shapeshifters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class GameCompleteScreen implements Screen {

    private final MainGame game;
    private SpriteBatch spriteBatch;
    private BitmapFont titleFont;
    private BitmapFont coinFont;
    private int totalCoins;
    private Stage stage;
    private Skin skin;
    private Texture backgroundTexture;
    private Music endMenuMusic;

    public GameCompleteScreen(MainGame game, int totalCoins) {
        this.game = game;
        this.totalCoins = totalCoins;
        spriteBatch = new SpriteBatch();
    }

    @Override
    public void show() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("uiskin.json"));
        endMenuMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/end_menu_theme.mp3"));
        AudioManager.playMusic(endMenuMusic, true);

        titleFont = generateFont("fonts/TitleFont.ttf", 78);
        coinFont = generateFont("fonts/TitleFont.ttf", 40);
        titleFont.setColor(Color.BLACK);
        coinFont.setColor(Color.BLACK);

        backgroundTexture = new Texture(Gdx.files.internal("background.png"));

        Table table = new Table();
        table.setFillParent(true);
        table.center();

        Label titleLabel = new Label("GAME COMPLETE", new Label.LabelStyle(titleFont, Color.BLACK));
        Label coinLabel = new Label("Total Coins: " + (totalCoins - 7), new Label.LabelStyle(coinFont, Color.BLACK));

        TextButton backToMainMenuButton = new TextButton("Back to Main Menu", skin);
        backToMainMenuButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new MainMenuScreen(game));
            }
        });

        TextButton exitButton = new TextButton("Exit Game", skin);
        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });

        table.add(titleLabel).padBottom(30).row();
        table.add(coinLabel).padBottom(50).row();
        table.add(backToMainMenuButton).height(50).width(400).padBottom(15).row();
        table.add(exitButton).height(50).width(400).padBottom(15).row();

        stage.addActor(table);
    }

    private BitmapFont generateFont(String fontPath, int size) {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(fontPath));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = size;
        BitmapFont font = generator.generateFont(parameter);
        generator.dispose();
        return font;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        game.batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        game.batch.end();

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {
        if(endMenuMusic != null)
        {
            endMenuMusic.stop();
            endMenuMusic.dispose();
        }
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
        titleFont.dispose();
        coinFont.dispose();
        stage.dispose();
        skin.dispose();
    }
}

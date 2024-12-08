package com.Xander.shapeshifters;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MainGame extends Game
{
    public SpriteBatch batch;
    private AudioManager audioManager;
    private Screen previousScreen;
    private int totalCoins;
    @Override
    public void create()
    {
        batch = new SpriteBatch();
        audioManager = new AudioManager();
        this.setScreen(new MainMenuScreen(this));
    }
    @Override
    public void dispose()
    {
        batch.dispose();
        super.dispose();
    }

    public AudioManager getAudioManager()
    {
        return audioManager;
    }
    public Screen getPreviousScreen() {
        return previousScreen;
    }

    public void setPreviousScreen(Screen previousScreen) {
        this.previousScreen = previousScreen;
    }

    public int getTotalCoins() {
        return totalCoins;
    }
    public void addCoins(int coins)
    {
        totalCoins += coins;
    }
}

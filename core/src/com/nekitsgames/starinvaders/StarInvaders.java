package com.nekitsgames.starinvaders;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

public class StarInvaders extends Game {

    SpriteBatch batch;
    BitmapFont fontMain;
    BitmapFont fontLabel;

    @Override
    public void create() {
        batch = new SpriteBatch();

        //Init main font
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("assets/fonts/zorque.ttf"));
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size = 128;
        fontMain = generator.generateFont(parameter); // fontMain size 12 pixels

        //Init labelFont
        generator = new FreeTypeFontGenerator(Gdx.files.internal("assets/fonts/pdark.ttf"));
        parameter = new FreeTypeFontParameter();
        parameter.size = 64;
        fontLabel = generator.generateFont(parameter);

        generator.dispose(); // don't forget to dispose to avoid memory leaks!
        this.setScreen(new MainMenuScreen(this));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        super.dispose();
        batch.dispose();
        fontMain.dispose();
    }
}

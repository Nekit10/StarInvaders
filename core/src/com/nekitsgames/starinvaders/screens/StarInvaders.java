package com.nekitsgames.starinvaders.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.nekitsgames.starinvaders.API.logAPI.LogSystem;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class StarInvaders extends Game {

    SpriteBatch batch;
    BitmapFont fontMain;
    BitmapFont fontLabel;
    Properties prop;

    private String fontPath;
    private String mainFont;
    private String lableFont;
    private int mainFontSize;
    private int labelFontSize;
    public LogSystem log;

    public int WIDTH;
    public int HEIGHT;

    @Override
    public void create() {
        try {
            log = new LogSystem();
        } catch (IOException e) {
            e.printStackTrace();
        }

        log.Log("Starting game...", LogSystem.INFO);

        prop = new Properties();
        try {
            prop.load(new FileInputStream("properties/main.properties"));
        } catch (IOException e) {
            e.printStackTrace();
            log.Log("Error: " + e.getMessage(), LogSystem.ERROR);
        }
        fontPath = prop.getProperty("dir.fonts");
        mainFont = prop.getProperty("main.font");
        lableFont = prop.getProperty("label.font");
        WIDTH = Integer.parseInt(prop.getProperty("resolution.width"));
        HEIGHT = Integer.parseInt(prop.getProperty("resolution.height"));
        mainFontSize = (int) (HEIGHT * Double.parseDouble(prop.getProperty("main.font.size")));
        labelFontSize = (int) (HEIGHT * Double.parseDouble(prop.getProperty("label.font.size")));

        batch = new SpriteBatch();

        log.Log("Initializing fonts", LogSystem.INFO);

        //Init main font
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(fontPath + mainFont));
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size = mainFontSize;
        fontMain = generator.generateFont(parameter); // fontMain size 12 pixels

        //Init labelFont
        generator = new FreeTypeFontGenerator(Gdx.files.internal(fontPath + lableFont));
        parameter = new FreeTypeFontParameter();
        parameter.size = labelFontSize;
        fontLabel = generator.generateFont(parameter);

        generator.dispose(); // don't forget to dispose to avoid memory leaks!
        try {
            this.setScreen(new MainMenuScreen(this));
        } catch (IOException e) {
            e.printStackTrace();
            log.Log("Error: " + e.getMessage(), LogSystem.ERROR);
        }
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
        fontLabel.dispose();
        log = null;
    }
}

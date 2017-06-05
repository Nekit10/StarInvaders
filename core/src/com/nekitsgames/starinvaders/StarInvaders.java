/*
* Copyright (c) 20016 - 2017, NG Tech and/or its affiliates. All rights reserved.
* GNI GPL v3 licence . Use is subject to license terms
*/
package com.nekitsgames.starinvaders;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.nekitsgames.starinvaders.API.logAPI.LogSystem;
import com.nekitsgames.starinvaders.API.settingsApi.Settings2API;
import com.nekitsgames.starinvaders.classes.Exceptions.SettingsAccessException;
import com.nekitsgames.starinvaders.screens.MainMenuScreen;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 *  Runs game code.
 *
 *  @author Nikita Serba
 *  @version 2.0
 *  @since 1.0
 */
public class StarInvaders extends Game {

    public static final String RUSSIAN_CHARACTERS = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЬЪЫЬЭЮЯ"
            + "абвгдеёжзийклмнопрстуфхцчшщъыьэюя"
            + FreeTypeFontGenerator.DEFAULT_CHARS;
    public SpriteBatch batch;
    public BitmapFont fontMain;
    public BitmapFont fontLabel;
    public BitmapFont fontData;
    public LogSystem log;
    public Settings2API settingsGame;
    public Settings2API settingsGameData;
    public Settings2API settingsMain;
    public int WIDTH;
    public int HEIGHT;
    private Properties prop;
    private String fontPath;
    private String mainFont;
    private String labelFont;
    private String dataFont;
    private int mainFontSize;
    private int labelFontSize;
    private int dataFontSize;

    /**
     * Setting up fonts, loading log system, running game.
     *
     * @since 1.0
     */
    @Override
    public void create() {
        try {

            log = new LogSystem();

            log.Log("Starting game...", LogSystem.INFO);

            settingsGame = new Settings2API();
            settingsGame.load("game");
            settingsGameData = new Settings2API();
            settingsGameData.load("gamedata");
            settingsMain = new Settings2API();
            settingsMain.load("main");

            prop = new Properties();


            prop.load(new FileInputStream("properties/defaults.properties"));
            WIDTH = (int) settingsMain.get("resolution.width", Integer.parseInt(prop.getProperty("settings.resolution.width")));
            HEIGHT = (int) settingsMain.get("resolution.height", Integer.parseInt(prop.getProperty("settings.resolution.height")));


            boolean isRussian = settingsMain.get("lang", "us").equals("ru");

            prop.load(new FileInputStream("properties/main.properties"));
            fontPath = prop.getProperty("dir.fonts");
            if (isRussian)
                mainFont = prop.getProperty("main.font.ru"); else
                mainFont = prop.getProperty("main.font");
            if (isRussian)
                labelFont = prop.getProperty("label.font.ru"); else
                labelFont = prop.getProperty("label.font");
            dataFont = prop.getProperty("data.font");
            mainFontSize = (int) (HEIGHT * Double.parseDouble(prop.getProperty("main.font.size")));
            labelFontSize = (int) (HEIGHT * Double.parseDouble(prop.getProperty("label.font.size")));
            dataFontSize = (int) (HEIGHT * Double.parseDouble(prop.getProperty("data.font.size")));

            batch = new SpriteBatch();

            log.Log("Initializing fonts", LogSystem.INFO);

            //Init main font
            FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(fontPath + mainFont));
            FreeTypeFontParameter parameter = new FreeTypeFontParameter();
            if (isRussian)
                parameter.characters = RUSSIAN_CHARACTERS;
            parameter.size = mainFontSize;
            fontMain = generator.generateFont(parameter); // fontMain size 12 pixels

            //Init labelFont
            generator = new FreeTypeFontGenerator(Gdx.files.internal(fontPath + labelFont));
            parameter = new FreeTypeFontParameter();
            if (isRussian)
                parameter.characters = RUSSIAN_CHARACTERS;
            parameter.size = labelFontSize;
            fontLabel = generator.generateFont(parameter);

            //Init dataFont
            generator = new FreeTypeFontGenerator(Gdx.files.internal(fontPath + dataFont));
            parameter = new FreeTypeFontParameter();
            parameter.size = dataFontSize;
            fontData = generator.generateFont(parameter);

            generator.dispose(); // don't forget to dispose to avoid memory leaks!
            this.setScreen(new MainMenuScreen(this));
        } catch (Exception e) {
            e.printStackTrace();
            log.Log("Error: " + e.getMessage(), LogSystem.FATAL);
        }
    }

    /**
     * Running game rendering.
     *
     * @since 1.0
     */
    @Override
    public void render() {
        super.render();
    }

    /**
     * Cleaning.
     *
     * @since 1.0
     */
    @Override
    public void dispose() {
        try {
            settingsMain.save();
            settingsGame.save();
            settingsGameData.save();
        } catch (SettingsAccessException e) {
            e.printStackTrace();
        }
        super.dispose();
        batch.dispose();
        fontMain.dispose();
        fontLabel.dispose();
        try {
            log.save();
            log.dispose();
        } catch (IOException e) {
            e.printStackTrace();
        }
        log = null;

    }
}

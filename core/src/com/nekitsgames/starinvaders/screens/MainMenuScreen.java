/*
* Copyright (c) 20016 - 2017, NG Tech and/or its affiliates. All rights reserved.
* GNI GPL v3 licence . Use is subject to license terms
*/

package com.nekitsgames.starinvaders.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;
import com.nekitsgames.starinvaders.API.logAPI.LogSystem;
import com.nekitsgames.starinvaders.StarInvaders;
import com.nekitsgames.starinvaders.screens.settings.SettingsScreen;
import com.nekitsgames.starinvaders.screens.settings.ShipScreen;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Main menu screen
 *
 * @author Nikita Serba
 * @version 3.0
 * @since 1.1
 */
public class MainMenuScreen implements Screen {

    private static String label;
    private static String[] menuLables;
    private static int menuLabelsX;
    private static double menuLabelXAdd;
    private StarInvaders game;
    private OrthographicCamera camera;
    private GlyphLayout glyphLayout;
    private Properties prop;
    private Texture selectedImage;
    private Texture planet;
    private Texture planet2;
    private Rectangle planetRect;
    private Rectangle planet2Rect;
    private Music menuMusic;
    private Rectangle labelPos;

    private String selectedTexture;
    private String imagePath;
    private String soundPath;
    private String soundName;
    private int labelMarginTop;
    private int menuElementStep;
    private int menuMarginBottom;
    private int menuMarginRight;
    private int menuHeight;
    private int menuWidth;
    private int menuChangeLimit;

    private int pos = 0;
    private long lastMenuChange;

    private long login;

    /**
     * Init main menu screen
     *
     * @since 1.1
     * @param game - game class
     * @throws IOException if can't access properties files
     */
    public MainMenuScreen(StarInvaders game) throws IOException {
        game.log.Log("Initializing main menu", LogSystem.INFO);


        prop = new Properties();
        prop.load(new FileInputStream("properties/strings." + game.settingsMain.get("lang", "us") + ".properties"));

        label = prop.getProperty("menu.label");
        menuLables = prop.getProperty("menu.elements").split(";");

        prop.load(new FileInputStream("properties/main_menu.properties"));
        menuLabelXAdd = Double.parseDouble(prop.getProperty("menu.elements.position.x"));
        selectedTexture = prop.getProperty("menu.selected.texture");
        soundName = prop.getProperty("menu.sound");
        labelMarginTop = (int) (game.HEIGHT * Double.parseDouble(prop.getProperty("label.margin.top")));
        menuElementStep = (int) (game.HEIGHT * Double.parseDouble(prop.getProperty("menu.elements.step")));
        menuMarginBottom = (int) (game.HEIGHT * Double.parseDouble(prop.getProperty("menu.selected.margin.bottom")));
        menuMarginRight = (int) (game.WIDTH * Double.parseDouble(prop.getProperty("menu.selected.margin.right")));
        menuWidth = (int) (game.WIDTH * Double.parseDouble(prop.getProperty("menu.selected.width")));
        menuHeight = menuWidth;
        menuChangeLimit = Integer.parseInt(prop.getProperty("menu.change.limit"));

        String soundQ = "high/";

        switch ((int) game.settingsGame.get("audio", 0)) {
            case 0:
                soundQ = "high/";
                break;
            case 1:
                soundQ = "medium/";
                break;
            case 2:
                soundQ = "minimal/";
                break;
        }

        prop.load(new FileInputStream("properties/main.properties"));
        imagePath = prop.getProperty("dir.images");
        soundPath = prop.getProperty("dir.sound") + soundQ;

        planet = new Texture(imagePath + "/high/planet6.png");
        planet2 = new Texture(imagePath + "/high/planet7.png");

        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, game.WIDTH, game.HEIGHT);
        glyphLayout = new GlyphLayout(
                game.fontMain,
                label);
        labelPos = new Rectangle();

        selectedImage = new Texture(imagePath + selectedTexture);
        menuMusic = Gdx.audio.newMusic(Gdx.files.internal(soundPath + soundName));

        labelPos.x = (int) ((game.WIDTH) / 2 - glyphLayout.width / 2);
        labelPos.y = game.HEIGHT - labelMarginTop;

        menuLabelsX = (int) (game.WIDTH / 2 - glyphLayout.width / 2 + glyphLayout.width * menuLabelXAdd);

        planetRect = new Rectangle();
        planet2Rect = new Rectangle();

        do {
            planetRect.width = MathUtils.random(game.WIDTH * 0.26666f, game.WIDTH * 1.06666f);
            planetRect.height = planetRect.width;
            planetRect.x = MathUtils.random(0, game.WIDTH);
            planetRect.y = MathUtils.random(0, game.HEIGHT);
            planet2Rect.width = MathUtils.random(game.WIDTH * 0.26666f, game.WIDTH * 1.06666f);
            planet2Rect.height = planetRect.width;
            planet2Rect.x = MathUtils.random(0, game.WIDTH);
            planet2Rect.y = MathUtils.random(0, game.HEIGHT);
        } while (planetRect.overlaps(planet2Rect));
    }

    /**
     * Render main menu screen
     *
     * @since 1.1
     * @param delta - delta time
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(planet, planetRect.x, planetRect.y, planetRect.width, planetRect.height);
        game.batch.draw(planet2, planet2Rect.x, planet2Rect.y, planet2Rect.width, planet2Rect.height);
        game.fontMain.draw(game.batch, label, labelPos.x, labelPos.y);

        for (int i = 0; i < menuLables.length; i++)
            game.fontLabel.draw(game.batch, menuLables[i], menuLabelsX, labelPos.y - (i + 1) * menuElementStep);

        game.batch.draw(selectedImage, menuLabelsX - menuMarginRight, labelPos.y - (pos + 1) * menuElementStep - menuMarginBottom, menuWidth, menuHeight);
        game.batch.end();

        if (TimeUtils.nanoTime() - lastMenuChange > menuChangeLimit) {
            if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                pos++;
                lastMenuChange = TimeUtils.nanoTime();
            }
            if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
                pos--;
                lastMenuChange = TimeUtils.nanoTime();
            }
        }

        if (pos < 0)
            pos = 0;
        if (pos > menuLables.length - 1)
            pos = menuLables.length - 1;

        if ((Gdx.input.isKeyPressed(Input.Keys.ENTER) || Gdx.input.isKeyPressed(Input.Keys.SPACE)) && TimeUtils.nanoTime() - login > 500000000)
            switch (pos) {
                case 0:
                    try {
                        game.setScreen(new MainGameScreen(game));
                    } catch (IOException e) {
                        e.printStackTrace();
                        game.log.Log("Error: " + e.getMessage(), LogSystem.ERROR);
                        Gdx.app.exit();
                    }
                    dispose();
                    break;
                case 1:
                    try {
                        game.setScreen(new FightScreen(game, this));
                    } catch (IOException e) {
                        e.printStackTrace();
                        game.log.Log("Error: " + e.getMessage(), LogSystem.ERROR);
                        Gdx.app.exit();
                    }
                    break;
                case 2:
                    try {
                        game.setScreen(new SettingsScreen(game, this));
                    } catch (IOException e) {
                        e.printStackTrace();
                        game.log.Log("Error: " + e.getMessage(), LogSystem.ERROR);
                        Gdx.app.exit();
                    }
                    break;
                case 3:
                    try {
                        game.setScreen(new ShipScreen(game, this));
                    } catch (IOException e) {
                        e.printStackTrace();
                        game.log.Log("Error: " + e.getMessage(), LogSystem.ERROR);
                        Gdx.app.exit();
                    }
                    break;

                case 4:
                    Gdx.app.exit();
                    break;
            }
    }

    /**
     * Show main menu screen
     *
     * @since 1.1
     */
    @Override
    public void show() {
        login = TimeUtils.nanoTime();
        menuMusic.setLooping(true);
        try {
            menuMusic.setVolume(((Double) game.settingsGame.get("volume", 1.0)).floatValue());
        } catch (ClassCastException e) {
            menuMusic.setVolume(((Integer) game.settingsGame.get("volume", 1.0)).floatValue());
        }
        menuMusic.play();
    }

    /**
     * Resize main menu screen
     *
     * @since 1.1
     * @param width - new width
     * @param height - new height
     */
    @Override
    public void resize(int width, int height) {

    }

    /**
     * Pause main menu screen
     *
     * @since 1.1
     */
    @Override
    public void pause() {

    }

    /**
     * Resume main menu screen
     *
     * @since 1.1
     */
    @Override
    public void resume() {

    }

    /**
     * Hide main menu screen
     *
     * @since 1.1
     */
    @Override
    public void hide() {

    }

    /**
     * Clean
     *
     * @since 1.1
     */
    @Override
    public void dispose() {
        game.log.Log("Disposing main menu", LogSystem.INFO);
        game = null;
        camera = null;
        glyphLayout = null;
        prop = null;
        selectedImage.dispose();
        selectedImage = null;
        labelPos = null;
        menuMusic.dispose();
        menuMusic = null;
    }

}

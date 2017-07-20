package com.nekitsgames.starinvaders.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.TimeUtils;
import com.nekitsgames.starinvaders.API.logAPI.LogSystem;
import com.nekitsgames.starinvaders.StarInvaders;

import javax.imageio.IIOException;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class CopyrightScreen implements Screen {

    private StarInvaders game;
    private Texture copyrightTexture;
    private String imagePath;
    private OrthographicCamera camera;
    private long createTime;

    private static final long TIME = 2000000000;

    public CopyrightScreen(StarInvaders game) throws Exception {
        this.game = game;

        Properties prop = new Properties();

        prop.load(new FileInputStream("properties/main.properties"));
        imagePath = prop.getProperty("dir.images");

        camera = new OrthographicCamera();
        camera.setToOrtho(false, game.WIDTH, game.HEIGHT);

        createTime = TimeUtils.nanoTime();

        copyrightTexture = new Texture(imagePath + "/high/copyright.png");
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(copyrightTexture, 0, 0, game.WIDTH, game.HEIGHT);
        game.batch.end();

        if (TimeUtils.nanoTime() - createTime > TIME)
            try {
                game.setScreen(new MainMenuScreen(game));
            } catch (Exception e) {
                e.printStackTrace();
                game.log.Log("Error: " + e.getMessage(), LogSystem.FATAL);
            }

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}

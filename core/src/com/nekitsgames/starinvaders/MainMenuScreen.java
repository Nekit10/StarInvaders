package com.nekitsgames.starinvaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;

public class MainMenuScreen implements Screen {

    private StarInvaders game;
    private OrthographicCamera camera;
    private GlyphLayout glyphLayout;

    private Texture selectedImage;
    private Music menuMusic;

    private static final String label = "Star Invaders II";

    private static final String[] menuLables = {
            "Game",
            "Options",
            "Results",
            "Quit"
    };

    private static int menuLabelsX;

    private Rectangle labelPos;

    private int pos = 0;
    private long lastMenuChange;

    public MainMenuScreen(StarInvaders game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, MainGameScreen.WIDTH, MainGameScreen.HEIGHT);
        glyphLayout = new GlyphLayout(game.fontMain, label);
        labelPos = new Rectangle();

        selectedImage = new Texture("assets/images/selected.png");
        menuMusic = Gdx.audio.newMusic(Gdx.files.internal("assets/sound/menu_sound.mp3"));

        menuMusic.setLooping(true);
        menuMusic.play();

        labelPos.x = (int) ((MainGameScreen.WIDTH) / 2 - glyphLayout.width / 2);
        labelPos.y = MainGameScreen.HEIGHT - 200;

        menuLabelsX = (int) (MainGameScreen.WIDTH / 2 - glyphLayout.width / 2 + 300);
    }

    @Override
    public void dispose() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
            game.fontMain.draw(game.batch, label, labelPos.x, labelPos.y);

            for (int i = 0; i < menuLables.length; i++)
                game.fontLabel.draw(game.batch, menuLables[i], menuLabelsX, labelPos.y - (i+1) * 128);

            game.batch.draw(selectedImage, menuLabelsX - 60, labelPos.y - (pos + 1) * 128 - 32, 20, 20);
        game.batch.end();

        if (TimeUtils.nanoTime() - lastMenuChange > 300000000) {
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
        if (pos > 3)
            pos = 3;

        if (Gdx.input.isKeyPressed(Input.Keys.ENTER) || Gdx.input.isKeyPressed(Input.Keys.SPACE))
            switch (pos) {
                case 0:
                    game.setScreen(new MainGameScreen(game));
                break;

                case 1:
                    game.setScreen(new SettingsScreen(game));
                break;

                case 2:
                    game.setScreen(new BestScreen(game));
                break;

                case 3:
                    Gdx.app.exit();
                break;
            }
    }

    @Override
    public void show() {

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
}

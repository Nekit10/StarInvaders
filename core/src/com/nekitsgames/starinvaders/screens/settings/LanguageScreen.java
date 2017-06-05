package com.nekitsgames.starinvaders.screens.settings;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;
import com.nekitsgames.starinvaders.API.logAPI.LogSystem;
import com.nekitsgames.starinvaders.StarInvaders;
import com.nekitsgames.starinvaders.classes.Language;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class LanguageScreen implements Screen {

    private static String label;
    private static Language[] menuLables;
    private static int menuLabelsX;
    private static double menuLabelXAdd;
    private StarInvaders game;
    private OrthographicCamera camera;
    private GlyphLayout glyphLayout;
    private Properties prop;
    private Texture selectedImage;
    private Rectangle selectedRect;
    private Rectangle labelPos;

    private int pos = 0;
    private long lastMenuChange;

    private String selectedTexture;
    private String imagePath;
    private int labelMarginTop;
    private int menuElementStep;
    private int menuMarginBottom;
    private int menuMarginRight;
    private int menuHeight;
    private int menuWidth;
    private int menuChangeLimit;

    private long login;
    private SettingsScreen menu;

    private int selectedX, selectedY;
    private int selectedMarginRight;

    public LanguageScreen(StarInvaders game, SettingsScreen menu) throws IOException {
        this.menu = menu;

        game.log.Log("Initializing settings screen", LogSystem.INFO);


        selectedRect = new Rectangle();

        prop = new Properties();
        prop.load(new FileInputStream("properties/strings." + game.settingsMain.get("lang", "us") + ".properties"));

        label = prop.getProperty("settings.lang.label");

        prop.load(new FileInputStream("properties/settings/lang.properties"));
        menuLabelXAdd = Double.parseDouble(prop.getProperty("menu.elements.position.x"));
        selectedTexture = prop.getProperty("menu.selected.texture");
        labelMarginTop = (int) (game.HEIGHT * Double.parseDouble(prop.getProperty("label.margin.top")));
        menuElementStep = (int) (game.HEIGHT * Double.parseDouble(prop.getProperty("menu.elements.step")));
        menuMarginBottom = (int) (game.HEIGHT * Double.parseDouble(prop.getProperty("menu.selected.margin.bottom")));
        menuMarginRight = (int) (game.WIDTH * Double.parseDouble(prop.getProperty("menu.selected.margin.right")));
        menuHeight = (int) (game.HEIGHT * Double.parseDouble(prop.getProperty("menu.selected.height")));
        menuWidth = (int) (game.WIDTH * Double.parseDouble(prop.getProperty("menu.selected.width")));
        menuChangeLimit = Integer.parseInt(prop.getProperty("menu.change.limit"));
        selectedMarginRight = (int) (game.WIDTH * Double.parseDouble(prop.getProperty("menu.selected.2.margin.right")));
        selectedRect.height = (int) (game.HEIGHT * Double.parseDouble(prop.getProperty("menu.selected.height")));
        selectedRect.width = (int) (game.WIDTH * Double.parseDouble(prop.getProperty("menu.selected.width")));

        prop.load(new FileInputStream("properties/main.properties"));
        imagePath = prop.getProperty("dir.images");

        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, game.WIDTH, game.HEIGHT);
        glyphLayout = new GlyphLayout(game.fontMain, label);
        labelPos = new Rectangle();

        selectedImage = new Texture(imagePath + selectedTexture);

        labelPos.x = (int) ((game.WIDTH) / 2 - glyphLayout.width / 2);
        labelPos.y = game.HEIGHT - labelMarginTop;

        menuLabelsX = (int) (game.WIDTH / 2 - glyphLayout.width / 2 + glyphLayout.width * menuLabelXAdd);

        prop.load(new FileInputStream("properties/langs.properties"));
        String[] langs = prop.getProperty("langs.sym").split(";");

        menuLables = new Language[langs.length];

        for (int i = 0; i < langs.length; i++) {
            menuLables[i] = new Language(langs[i]);
        }
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
            game.fontLabel.draw(game.batch, menuLables[i].getName(), menuLabelsX, labelPos.y - (i + 1) * menuElementStep);

        game.batch.draw(selectedImage, menuLabelsX - menuMarginRight, labelPos.y - (pos + 1) * menuElementStep - menuMarginBottom, menuWidth, menuHeight);
        game.batch.draw(selectedImage, selectedX, selectedY, selectedRect.width, selectedRect.height);
        game.batch.end();

        int npos = 0;
        String sym = (String) game.settingsMain.get("lang", "us");

        try {
            prop.load(new FileInputStream("properties/strings." + sym + ".properties"));
        } catch (IOException e) {
            e.printStackTrace();
            game.log.Log("Error: " + e.getMessage(), LogSystem.ERROR);
            Gdx.app.exit();
        }

        String str = prop.getProperty("lang.name");

        for (int i = 0; i < menuLables.length; i++)
            if (str.equals(menuLables[i].getName())) {
                npos = i;
                break;
            }

        selectedX = menuLabelsX - selectedMarginRight;
        selectedY = (int) (labelPos.y - (npos + 1) * menuElementStep - menuMarginBottom);
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

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE))
            game.setScreen(menu);

        if ((Gdx.input.isKeyPressed(Input.Keys.ENTER) || Gdx.input.isKeyPressed(Input.Keys.SPACE)) & TimeUtils.nanoTime() - login > 500000000)
            try {
                game.settingsMain.set("lang", menuLables[pos].getSym());
                game.setScreen(new RestartScreen(game, menu));
            } catch (IOException e) {
                e.printStackTrace();
                game.log.Log("Error: " + e.getMessage(), LogSystem.ERROR);
                Gdx.app.exit();
            }
    }

    @Override
    public void show() {
        login = TimeUtils.nanoTime();
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
        game.log.Log("Disposing results screen", LogSystem.INFO);
        game = null;
        camera = null;
        glyphLayout = null;
        prop = null;
        selectedImage.dispose();
        selectedImage = null;
        labelPos = null;
    }

}

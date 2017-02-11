package com.nekitsgames.starinvaders.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.nekitsgames.starinvaders.API.logAPI.LogSystem;
import com.nekitsgames.starinvaders.API.settingsApi.SettingsSystem;
import com.nekitsgames.starinvaders.StarInvaders;
import com.nekitsgames.starinvaders.classes.Asteroid;
import com.nekitsgames.starinvaders.classes.AsteroidType;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;

public class MainGameScreen implements Screen {

    private OrthographicCamera camera;
    private StarInvaders game;
    private Properties prop;
    private SettingsSystem setings;
    private GlyphLayout glyphLayout;

    private Texture shipImage;
    private Texture lazerImage;
    private Texture hearthImage;
    private Sound lazerSound;
    private Music spaceSound;

    private Rectangle shipRect;
    private Rectangle rect;
    private Array<Rectangle> lazerRects;
    private long lastLazer;

    private Vector3 touchPos;
    private Iterator<Rectangle> iterator;

    private static int SHIP_WIDTH;
    private static int SHIP_HEIGHT;
    private static int SHIP_X;
    private static int SHIP_Y;
    private static String SHIP_FILE;
    private static String SHIP_SOUND;

    private static int SHIP_ONE_STEP_TOUCH;
    private static int SHIP_ONE_STEP_KEY;

    private static int LAZER_WIDTH;
    private static int LAZER_HEIGHT;
    private static int LAZER_STEP;
    private static long LAZER_WAIT_TIME;
    private static String LAZER_FILE;
    private static String LAZER_SOUND;

    private int hearthHeight;
    private int hearthWidth;

    private int hp;

    private static String image_path;
    private static String music_path;

    private static ArrayList<Asteroid> asteroids;
    private static AsteroidType[] typies;

    private static boolean showFPS;
    private static String FPSLabel;

    public MainGameScreen(StarInvaders game) throws IOException {
        game.log.Log("Initializing main game screen", LogSystem.INFO);

        prop = new Properties();
        setings = new SettingsSystem("game", game.log);

        prop.load(new FileInputStream("properties/ship.properties"));
        SHIP_WIDTH = (int) (game.WIDTH * Double.parseDouble(prop.getProperty("ship.1.width")));
        SHIP_HEIGHT = (int) (game.HEIGHT * Double.parseDouble(prop.getProperty("ship.1.height")));
        SHIP_Y = (int) (Double.parseDouble(prop.getProperty("ship.1.y")) * game.WIDTH);
        SHIP_ONE_STEP_TOUCH = (int) (game.WIDTH * Double.parseDouble(prop.getProperty("ship.1.step.mouse")));
        SHIP_ONE_STEP_KEY = (int) (game.WIDTH * Double.parseDouble(prop.getProperty("ship.1.step.key")));
        SHIP_FILE = prop.getProperty("ship.1.texture");

        prop.load(new FileInputStream("properties/amunition.properties"));
        LAZER_WIDTH = (int) (game.WIDTH * Double.parseDouble(prop.getProperty("amunition.1.width")));
        LAZER_HEIGHT = (int) (game.HEIGHT * Double.parseDouble(prop.getProperty("amunition.1.height")));
        LAZER_STEP = (int) (game.WIDTH * Double.parseDouble(prop.getProperty("amunition.1.step")));
        LAZER_WAIT_TIME = Long.parseLong(prop.getProperty("amunition.1.wait_time"));
        LAZER_FILE = prop.getProperty("amunition.1.texture");
        LAZER_SOUND = prop.getProperty("amunition.1.sound");

        prop.load(new FileInputStream("properties/main.properties"));
        music_path = prop.getProperty("dir.sound");
        image_path = prop.getProperty("dir.images");
        SHIP_SOUND = prop.getProperty("app.music");

        prop.load(new FileInputStream("properties/asteroids.properties"));
        int count = Integer.parseInt(prop.getProperty("asteroid.count"));
        typies = new AsteroidType[count];

        prop.load(new FileInputStream("properties/strings.us.properties"));
        FPSLabel = prop.getProperty("fps.label");

        prop.load(new FileInputStream("properties/game.properties"));
        hearthImage = new Texture(image_path + prop.getProperty("hearth.texture"));
        hearthHeight = (int) (game.HEIGHT * Double.parseDouble(prop.getProperty("hearth.height")));
        hearthWidth = (int) (game.WIDTH * Double.parseDouble(prop.getProperty("hearth.width")));

        asteroids = new ArrayList<Asteroid>();


        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, game.WIDTH, game.HEIGHT);

        touchPos = new Vector3();

        rect = new Rectangle();

        SHIP_X = game.WIDTH / 2 - SHIP_WIDTH / 2;

        shipImage = new Texture(image_path + SHIP_FILE);
        lazerImage = new Texture(image_path + LAZER_FILE);

        lazerSound = Gdx.audio.newSound(Gdx.files.internal(music_path + LAZER_SOUND));
        spaceSound = Gdx.audio.newMusic(Gdx.files.internal(music_path + SHIP_SOUND));

        spaceSound.setLooping(true);
        spaceSound.play();

        shipRect = new Rectangle();
        shipRect.x = SHIP_X;
        shipRect.y = SHIP_Y;
        shipRect.width = SHIP_WIDTH;
        shipRect.height = SHIP_HEIGHT;

        for (int i = 0; i < typies.length; i++) {
            prop.load(new FileInputStream("properties/asteroids.properties"));
            typies[i] = new AsteroidType(
                    Double.parseDouble(prop.getProperty("asteroid." + (i + 1) + ".width")),
                    Double.parseDouble(prop.getProperty("asteroid." + (i + 1) + ".height")),
                    Double.parseDouble(prop.getProperty("asteroid." + (i + 1) + ".step")),
                    Long.parseLong(prop.getProperty("asteroid." + (i + 1) + ".spawn_after")),
                    prop.getProperty("asteroid." + (i + 1) + ".texture"),
                    0,
                    image_path,
                    game.WIDTH,
                    game.HEIGHT,
                    (int) setings.get("difficulty", 2),
                    Double.parseDouble(prop.getProperty("asteroid." + (i + 1) + ".dif_cof")),
                    Integer.parseInt(prop.getProperty("asteroid." + (i + 1) + ".damage")),
                    prop.getProperty("asteroid." + (i + 1) + ".killable").equals("1")
            );
        }

        spawnAsteroids(asteroids, typies);

        lazerRects = new Array<Rectangle>();

        hp = 100;
    }

    private void spawnLazer(float x, float y) {
        rect = new Rectangle();
        rect.x = x;
        rect.y = y;
        rect.width = LAZER_WIDTH;
        rect.height = LAZER_HEIGHT;
        lazerRects.add(rect);
        lastLazer = TimeUtils.nanoTime();
        lazerSound.play();
    }

    private void spawnAsteroids(ArrayList<Asteroid> astrs, AsteroidType[] typs) {
        for (AsteroidType type : typs) {
            if (TimeUtils.nanoTime() - type.getLast_spawn() > type.getSpawn_after()) {
                astrs.add(new Asteroid(type, MathUtils.random(0, game.WIDTH), game.HEIGHT));
                astrs.get(astrs.size() - 1).getType().setLast_spawn(TimeUtils.nanoTime());
            }
        }
    }


    private void die() throws IOException {
        game.setScreen(new GameEndScreen(game));
        dispose();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();

        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.batch.draw(shipImage, shipRect.x, shipRect.y, shipRect.getWidth(), shipRect.getHeight());
        for (Rectangle lazerRect : lazerRects)
            game.batch.draw(lazerImage, lazerRect.x, lazerRect.y, lazerRect.getWidth(), lazerRect.getHeight());
        for (Asteroid astr : asteroids) {
            game.batch.draw(astr.getType().getTexture(), astr.getRect().x, astr.getRect().y, astr.getRect().getWidth(), astr.getRect().getHeight());
        }
        if (showFPS) {
            String fps = FPSLabel.replace("%FPS%", "" + Gdx.graphics.getFramesPerSecond());
            glyphLayout = new GlyphLayout(game.fontData, fps);
            game.fontData.draw(game.batch, fps, game.WIDTH - glyphLayout.width, game.HEIGHT - glyphLayout.height);
        }

        game.batch.draw(hearthImage, 0, game.HEIGHT - hearthHeight, hearthWidth, hearthHeight);

        String hps = hp + "%";
        glyphLayout = new GlyphLayout(game.fontData, hps);
        game.fontData.draw(game.batch, hps, hearthWidth + 20, game.HEIGHT - (hearthHeight - glyphLayout.height) / 1.5f);
        game.batch.end();

        if (Gdx.input.isTouched()) {
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            if (touchPos.x < game.WIDTH / 2)
                shipRect.x -= SHIP_ONE_STEP_TOUCH;
            else
                shipRect.x += SHIP_ONE_STEP_TOUCH;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
            shipRect.x -= SHIP_ONE_STEP_KEY * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            shipRect.x += SHIP_ONE_STEP_KEY * Gdx.graphics.getDeltaTime();
        if (shipRect.x > game.WIDTH - SHIP_WIDTH)
            shipRect.x = game.WIDTH - SHIP_WIDTH;
        if (shipRect.x < 0)
            shipRect.x = 0;
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) & TimeUtils.nanoTime() - lastLazer > LAZER_WAIT_TIME)
            spawnLazer(shipRect.x + SHIP_WIDTH / 2, shipRect.y + SHIP_HEIGHT);

        iterator = lazerRects.iterator();
        while (iterator.hasNext()) {
            rect = iterator.next();
            rect.y += LAZER_STEP * Gdx.graphics.getDeltaTime();
            ArrayList<Integer> rm = new ArrayList<>();
            for (Asteroid astr : asteroids)
                if (rect.overlaps(astr.getRect()) && astr.getType().isKillable()) {
                    rm.add(asteroids.indexOf(astr));
                    iterator.remove();
                }
            for (int i : rm)
                asteroids.remove(i);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE))
            try {
                game.setScreen(new PauseScreen(game, this));
            } catch (IOException e) {
                e.printStackTrace();
                game.log.Log("Error: " + e.getMessage(), LogSystem.ERROR);
                Gdx.app.exit();
            }

        spawnAsteroids(asteroids, typies);

        ArrayList<Integer> indexes = new ArrayList<>();

        for (Asteroid astr : asteroids) {
            astr.getRect().setPosition(
                    astr.getRect().x,
                    astr.getRect().y - astr.getType().getStep() * Gdx.graphics.getDeltaTime()
            );
            if (astr.getRect().y < 0 - astr.getType().getHeight())
                indexes.add(asteroids.indexOf(astr));
            if (astr.getRect().overlaps(shipRect)) {
                hp -= astr.getType().getDamage();
                indexes.add(asteroids.indexOf(astr));
            }
        }

        for (int i : indexes)
            asteroids.remove(i);

        if (hp <= 0)
            try {
                die();
            } catch (IOException e) {
                e.printStackTrace();
                game.log.Log("Error: " + e.getMessage(), LogSystem.ERROR);
                Gdx.app.exit();
            }
        if (hp > 200)
            hp = 200;

    }

    @Override
    public void dispose() {
        game.log.Log("Disposing main game screen", LogSystem.INFO);
        camera = null;
        game = null;
        prop = null;
        shipImage.dispose();
        lazerImage.dispose();
        shipImage = null;
        lazerImage = null;
        lazerSound.dispose();
        lazerSound = null;
        spaceSound.dispose();
        spaceSound = null;
        shipRect = null;
        rect = null;
        lazerRects = null;
        touchPos = null;
        iterator = null;
        asteroids = null;
        typies = null;
    }

    @Override
    public void show() {
        try {
            prop.load(new FileInputStream("properties/defaults.properties"));
        } catch (IOException e) {
            e.printStackTrace();
            game.log.Log("Error: " + e.getMessage(), LogSystem.ERROR);
            Gdx.app.exit();
        }
        showFPS = (boolean) setings.get("FPS.show", Boolean.parseBoolean(prop.getProperty("settings.FPS.show")));
        game.log.Log("Show FPS - " + showFPS, LogSystem.INFO);
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

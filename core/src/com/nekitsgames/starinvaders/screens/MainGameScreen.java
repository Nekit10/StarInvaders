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
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.TimeUtils;
import com.nekitsgames.starinvaders.API.logAPI.LogSystem;
import com.nekitsgames.starinvaders.API.settingsApi.SettingsSystem;
import com.nekitsgames.starinvaders.StarInvaders;
import com.nekitsgames.starinvaders.classes.Amunition;
import com.nekitsgames.starinvaders.classes.AmunitionType;
import com.nekitsgames.starinvaders.classes.Asteroid;
import com.nekitsgames.starinvaders.classes.AsteroidType;
import com.nekitsgames.starinvaders.screens.settings.SettingsScreen;

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

    private int distance;
    private long lastDis;

    private Texture shipImage;
    private Texture hearthImage;
    private Music spaceSound;

    private Rectangle shipRect;
    private Rectangle rect;

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

    private static int current_amunition = 0;
    private static int amunition_count;
    private static ArrayList<Amunition> amunitions;
    private static AmunitionType[] amunitionTypes;

    private int hearthHeight;
    private int hearthWidth;

    private int hp;

    private static String image_path;
    private static String music_path;

    private static ArrayList<Asteroid> asteroids;
    private static AsteroidType[] typies;

    private static boolean showFPS;
    private static String FPSLabel;

    private static int ship = 1;
    private static int ship_armour = 100;

    public MainGameScreen(StarInvaders game) throws IOException {
        game.log.Log("Initializing main game screen", LogSystem.INFO);

        prop = new Properties();
        setings = new SettingsSystem("game", game.log);

        prop.load(new FileInputStream("properties/main.properties"));
        music_path = prop.getProperty("dir.sound");
        image_path = prop.getProperty("dir.images");
        SHIP_SOUND = prop.getProperty("app.music");

        SettingsSystem gameplaySettings = new SettingsSystem("gameplay", game.log);

        prop.load(new FileInputStream("properties/ship.properties"));
        SHIP_WIDTH = (int) (game.WIDTH * Double.parseDouble(prop.getProperty("ship." + ship + ".width")));
        SHIP_HEIGHT = (int) (game.HEIGHT * Double.parseDouble(prop.getProperty("ship." + ship + ".height")));
        SHIP_Y = (int) (Double.parseDouble(prop.getProperty("ship." + ship + ".y")) * game.WIDTH);
        SHIP_ONE_STEP_TOUCH = (int) (game.WIDTH * (Double.parseDouble(prop.getProperty("ship." + ship + ".step.mouse"))) + (double) gameplaySettings.get("tech.data", 0.0));
        SHIP_ONE_STEP_KEY = (int) (game.WIDTH * Double.parseDouble(prop.getProperty("ship." + ship + ".step.key")));
        SHIP_FILE = prop.getProperty("ship." + ship +  ".texture");

        ship_armour = (int) gameplaySettings.get("armour.percent", 0);

        prop.load(new FileInputStream("properties/amunition.properties"));
        amunition_count = Integer.parseInt(prop.getProperty("amunition.count"));
        amunitionTypes = new AmunitionType[amunition_count];
        for (int i = 0; i < amunition_count; i++) {
            amunitionTypes[i] = new AmunitionType(
                    Double.parseDouble(prop.getProperty("amunition." + (i + 1) + ".width")),
                    Double.parseDouble(prop.getProperty("amunition." + (i + 1) + ".height")),
                    Double.parseDouble(prop.getProperty("amunition." + (i + 1) + ".step")),
                    Integer.parseInt(prop.getProperty("amunition." + (i + 1) + ".wait_time")),
                    prop.getProperty("amunition." + (i + 1) + ".texture"),
                    prop.getProperty("amunition." + (i + 1) + ".sound"),
                    prop.getProperty("amunition." + (i + 1) + ".expl_image"),
                    image_path,
                    music_path,
                    game,
                    Integer.parseInt(prop.getProperty("amunition." + (i + 1) + ".hp_asteroid"))
            );
        }

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

            int counts = Integer.parseInt(prop.getProperty("asteroid." + (i + 1) + ".hp.textures.count"));

            Texture[] textures = new Texture[counts + 1];

            textures[0] = (new Texture(image_path + prop.getProperty("asteroid." + (i + 1) + ".texture")));

            for (int j = 1; j <= counts; j++) {
                textures[j] = new Texture(image_path + prop.getProperty("asteroid." + (i + 1) + ".hp.texture." + j + ".texture"));
            }

            typies[i] = new AsteroidType(
                    Double.parseDouble(prop.getProperty("asteroid." + (i + 1) + ".width")),
                    Double.parseDouble(prop.getProperty("asteroid." + (i + 1) + ".height")),
                    Double.parseDouble(prop.getProperty("asteroid." + (i + 1) + ".step")),
                    Long.parseLong(prop.getProperty("asteroid." + (i + 1) + ".spawn_after")),
                    textures,
                    0,
                    image_path,
                    game.WIDTH,
                    game.HEIGHT,
                    (int) setings.get("difficulty", 2),
                    Double.parseDouble(prop.getProperty("asteroid." + (i + 1) + ".dif_cof")),
                    Integer.parseInt(prop.getProperty("asteroid." + (i + 1) + ".damage")),
                    prop.getProperty("asteroid." + (i + 1) + ".killable").equals("1"),
                    Integer.parseInt(prop.getProperty("asteroid." + (i + 1) + ".hp"))
            );
        }

        spawnAsteroids(asteroids, typies);

        amunitions = new ArrayList<>();

        hp = 100;
    }

    private void spawnLazer(int x, int y) {
        amunitions.add(new Amunition(amunitionTypes[current_amunition], x, y));
        amunitionTypes[current_amunition].setLast(TimeUtils.nanoTime());
        amunitionTypes[current_amunition].getStartSound().play();
    }

    private void spawnAsteroids(ArrayList<Asteroid> astrs, AsteroidType[] typs) {
        for (AsteroidType type : typs) {
            if (TimeUtils.nanoTime() - type.getLast_spawn() > type.getSpawn_after()) {
                astrs.add(new Asteroid(type, MathUtils.random(0, game.WIDTH), game.HEIGHT, 0));
                astrs.get(astrs.size() - 1).getType().setLast_spawn(TimeUtils.nanoTime());
            }
        }
    }


    private void die() throws IOException {
        game.setScreen(new GameEndScreen(game, distance));
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
        for (Amunition lazerRect : amunitions) {
            game.batch.draw(lazerRect.getType().getMainTexture(), lazerRect.getRect().x, lazerRect.getRect().y, lazerRect.getRect().getWidth(), lazerRect.getRect().getHeight());
        }
        for (Asteroid astr : asteroids) {
            game.batch.draw(
                    astr.getType().getTexture()[astr.getTexture()],
                    astr.getRect().x,
                    astr.getRect().y,
                    astr.getRect().getWidth(),
                    astr.getRect().getHeight());
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

        String dis = distance + " m";

        game.fontData.draw(game.batch, dis, 0, 0);
        game.batch.end();

        if (Gdx.input.isKeyPressed(Input.Keys.NUM_1))
            current_amunition = 0;

        if (Gdx.input.isKeyPressed(Input.Keys.NUM_2))
            current_amunition = 1;

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
            shipRect.x -= SHIP_ONE_STEP_KEY * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            shipRect.x += SHIP_ONE_STEP_KEY * Gdx.graphics.getDeltaTime();
        if (shipRect.x > game.WIDTH - SHIP_WIDTH)
            shipRect.x = game.WIDTH - SHIP_WIDTH;
        if (shipRect.x < 0)
            shipRect.x = 0;
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) & TimeUtils.nanoTime() - amunitionTypes[current_amunition].getLast() > amunitionTypes[current_amunition].getWait_time())
            spawnLazer((int) (shipRect.x + SHIP_WIDTH / 2), (int) (shipRect.y + SHIP_HEIGHT));

        ArrayList<Integer> rm1 = new ArrayList<>();

        for (Amunition amun: amunitions) {
            amun.getRect().y += amun.getType().getStep() * Gdx.graphics.getDeltaTime();
            ArrayList<Integer> rm = new ArrayList<>();
            for (Asteroid astr : asteroids)
                if (amun.getRect().overlaps(astr.getRect()) && astr.getType().isKillable()) {
                    astr.setHp(astr.getHp() - amun.getType().getHp_asteroid());
                    if (astr.getHp() <= 0)
                        rm.add(asteroids.indexOf(astr)); else
                        astr.setTexture(astr.getTexture() + 1);

                        rm1.add(amunitions.indexOf(amun));
                }
            for (int i : rm)
                asteroids.remove(i);
        }

        for (int i : rm1)
            amunitions.remove(i);

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
                hp -= astr.getType().getDamage() * (100 - ship_armour / 3) / 100;
                indexes.add(asteroids.indexOf(astr));
            }
        }

        for (int i : indexes)
            asteroids.remove(i);

        if (TimeUtils.nanoTime() - lastDis > 53333333) {
            distance++;
            lastDis = TimeUtils.nanoTime();
        }

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
        shipImage = null;
        spaceSound.dispose();
        spaceSound = null;
        shipRect = null;
        rect = null;
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

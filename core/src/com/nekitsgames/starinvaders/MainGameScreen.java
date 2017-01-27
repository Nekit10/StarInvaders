package com.nekitsgames.starinvaders;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;
import java.util.Locale;

public class MainGameScreen implements Screen {

	private OrthographicCamera camera;
	private StarInvaders game;

	private Texture shipImage;
	private Texture asteroidSmallIMage;
	private Texture asteroidBigImage;
	private Texture lazerImage;
	private Sound lazerSound;
	private Music spaceSound;

	private Rectangle shipRect;
	private Rectangle rect;
	private Array<Rectangle> smallAsteroidRects;
	private long lastSmallAsteroidTime;
	private Array<Rectangle> bigAsteroidRects;
	private long lastBigAsteroidTime;
	private Array<Rectangle> lazerRects;
	private long lastLazer;

	private Vector3 touchPos;
	private Iterator<Rectangle> iterator;

	public static final int WIDTH = 1920;
	public static final int HEIGHT = 1080;

	private static final int SHIP_WIDTH = 183;
	private static final int SHIP_HEIGHT = 183;
	private static final int SHIP_X = WIDTH / 2 - SHIP_WIDTH / 2;
	private static final int SHIP_Y = 60;

	private static final int SHIP_ONE_STEP_TOUCH = 5;
	private static final int SHIP_ONE_STEP_KEY = 300;

	private static final int ASTEROID_SMALL_WIDTH = 28;
	private static final int ASTEROID_SMALL_HEIGHT = 28;

	private static final int ASTEROID_SMALL_STEP = 400;
	private static final long ASTEROID_SMALL_SPAWN_AFTER = 199999999L;

	private static final int ASTEROID_BIG_WIDTH = 56;
	private static final int ASTEROID_BIG_HEIGHT = 56;

	private static final int ASTEROID_BIG_STEP = 300;
	private static final long ASTEROID_BIG_SPAWN_AFTER = 1000000000L;

	private static final int LAZER_WIDTH = 25;
	private static final int LAZER_HEIGHT = 40;
	private static final int LAZER_STEP = 500;
	private static final int LAZER_WAIT_TIME = 100000000;

	public MainGameScreen (StarInvaders game) {
		this.game = game;

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1920, 1080);

		touchPos = new Vector3();

		rect = new Rectangle();

		shipImage = new Texture("assets/images/ship.png");
		asteroidSmallIMage = new Texture("assets/images/asteroid_small.png");
		asteroidBigImage = new Texture("assets/images/asteroid_big.png");
		lazerImage = new Texture("assets/images/lazer.png");

		lazerSound = Gdx.audio.newSound(Gdx.files.internal("assets/sound/lazer_sound.wav"));
		spaceSound = Gdx.audio.newMusic(Gdx.files.internal("assets/sound/space_sound.mp3"));

		spaceSound.setLooping(true);
		spaceSound.play();

		shipRect = new Rectangle();
		shipRect.x = SHIP_X;
		shipRect.y = SHIP_Y;
		shipRect.width = SHIP_WIDTH;
		shipRect.height = SHIP_HEIGHT;

		smallAsteroidRects = new Array<Rectangle>();
		spawnSmallAsteroid();

		bigAsteroidRects = new Array<Rectangle>();
		spawnBigAsteroid();

		lazerRects = new Array<Rectangle>();
	}

	private void spawnSmallAsteroid() {
		rect = new Rectangle();
		rect.x = MathUtils.random(0, WIDTH - ASTEROID_SMALL_WIDTH);
		rect.y = HEIGHT - 60;
		rect.width = ASTEROID_SMALL_WIDTH;
		rect.height = ASTEROID_SMALL_HEIGHT;
		smallAsteroidRects.add(rect);
		lastSmallAsteroidTime = TimeUtils.nanoTime();
	}

	private void spawnBigAsteroid() {
		rect = new Rectangle();
		rect.x = MathUtils.random(0, WIDTH - ASTEROID_BIG_WIDTH);
		rect.y = HEIGHT - 60;
		rect.width = ASTEROID_BIG_WIDTH;
		rect.height = ASTEROID_BIG_HEIGHT;
		bigAsteroidRects.add(rect);
		lastBigAsteroidTime = TimeUtils.nanoTime();
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


	private void die() {
		game.setScreen(new GameEndScreen(game));
		dispose();
	}

	@Override
	public void render (float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();

		game.batch.setProjectionMatrix(camera.combined);

		game.batch.begin();
		game.batch.draw(shipImage, shipRect.x, shipRect.y);
		for (Rectangle smallAsteroidRect: smallAsteroidRects)
			game.batch.draw(asteroidSmallIMage, smallAsteroidRect.x, smallAsteroidRect.y);
		for (Rectangle bigAsteroidRect: bigAsteroidRects)
			game.batch.draw(asteroidBigImage, bigAsteroidRect.x, bigAsteroidRect.y);
		for (Rectangle lazerRect: lazerRects)
			game.batch.draw(lazerImage, lazerRect.x, lazerRect.y);
		game.batch.end();

		if (Gdx.input.isTouched()) {
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			if (touchPos.x < WIDTH / 2)
				shipRect.x -= SHIP_ONE_STEP_TOUCH; else
				shipRect.x += SHIP_ONE_STEP_TOUCH;
		}

		if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
			shipRect.x -= SHIP_ONE_STEP_KEY * Gdx.graphics.getDeltaTime();
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
			shipRect.x += SHIP_ONE_STEP_KEY * Gdx.graphics.getDeltaTime();
		if (shipRect.x > WIDTH - SHIP_WIDTH)
			shipRect.x = WIDTH - SHIP_WIDTH;
		if (shipRect.x < 0)
			shipRect.x = 0;

		if (TimeUtils.nanoTime() - lastSmallAsteroidTime > ASTEROID_SMALL_SPAWN_AFTER)
			spawnSmallAsteroid();

		iterator = smallAsteroidRects.iterator();
		while (iterator.hasNext()) {
			rect = iterator.next();
			rect.y -= ASTEROID_SMALL_STEP * Gdx.graphics.getDeltaTime();
			if (rect.y + ASTEROID_SMALL_HEIGHT < 0)
				iterator.remove();
			if (rect.overlaps(shipRect))
				die();
		}

		if (TimeUtils.nanoTime() - lastBigAsteroidTime > ASTEROID_BIG_SPAWN_AFTER)
			spawnBigAsteroid();

		iterator = bigAsteroidRects.iterator();
		while (iterator.hasNext()) {
			rect = iterator.next();
			rect.y -= ASTEROID_BIG_STEP * Gdx.graphics.getDeltaTime();
			if (rect.y + ASTEROID_BIG_HEIGHT < 0)
				iterator.remove();
			if (rect.overlaps(shipRect))
				die();
		}

		if (Gdx.input.isKeyPressed(Input.Keys.SPACE) & TimeUtils.nanoTime() - lastLazer > LAZER_WAIT_TIME)
			spawnLazer(shipRect.x + SHIP_WIDTH / 2, shipRect.y + SHIP_HEIGHT);

		iterator = lazerRects.iterator();
		while (iterator.hasNext()) {
			rect = iterator.next();
			rect.y += LAZER_STEP * Gdx.graphics.getDeltaTime();
			if (rect.y + ASTEROID_BIG_HEIGHT > HEIGHT)
				iterator.remove();

			for (int i = 0; i < smallAsteroidRects.size; i++)
				if (rect.overlaps(smallAsteroidRects.get(i))) {
					iterator.remove();
					smallAsteroidRects.removeIndex(i);
				}

			for (int i = 0; i < bigAsteroidRects.size; i++)
				if (rect.overlaps(bigAsteroidRects.get(i))) {
					iterator.remove();
					bigAsteroidRects.removeIndex(i);
				}
		}

		if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE))
			game.setScreen(new PauseScreen(game, this));
	}

	@Override
	public void dispose () {
		shipImage.dispose();
		asteroidBigImage.dispose();
		asteroidSmallIMage.dispose();
		spaceSound.dispose();
		lazerSound.dispose();
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

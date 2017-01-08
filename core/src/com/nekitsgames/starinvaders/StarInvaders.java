package com.nekitsgames.starinvaders;

import android.graphics.Bitmap;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;

public class StarInvaders extends ApplicationAdapter {

	private OrthographicCamera camera;
	private SpriteBatch batch;

	private Texture shipImage;
	private Texture asteroidSmallIMage;
	private Texture asteroidBigImage;
	private Sound lazerSound;
	private Music spaceSound;

	private Rectangle shipRect;
	private Rectangle asteroidRect;
	private Array<Rectangle> smallAsteroidRects;
	private long lastSmallAsteroidTime;
	private Array<Rectangle> bigAsteroidRects;
	private long lastBigAsteroidTime;

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
	private static final long ASTEROID_BIG_SPAWN_AFTER = 1000000000;

	@Override
	public void create () {
		batch = new SpriteBatch();

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1920, 1080);

		touchPos = new Vector3();

		asteroidRect = new Rectangle();

		shipImage = new Texture("ship.png");
		asteroidSmallIMage = new Texture("asteroid_small.png");
		asteroidBigImage = new Texture("asteroid_big.png");

		lazerSound = Gdx.audio.newSound(Gdx.files.internal("lazer_sound.wav"));
		spaceSound = Gdx.audio.newMusic(Gdx.files.internal("space_sound.mp3"));

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
	}

	private void spawnSmallAsteroid() {
		asteroidRect = new Rectangle();
		asteroidRect.x = MathUtils.random(0, WIDTH - ASTEROID_SMALL_WIDTH);
		asteroidRect.y = HEIGHT - 60;
		asteroidRect.width = ASTEROID_SMALL_WIDTH;
		asteroidRect.height = ASTEROID_SMALL_HEIGHT;
		smallAsteroidRects.add(asteroidRect);
		lastSmallAsteroidTime = TimeUtils.nanoTime();
	}

	private void spawnBigAsteroid() {
		asteroidRect = new Rectangle();
		asteroidRect.x = MathUtils.random(0, WIDTH - ASTEROID_BIG_WIDTH);
		asteroidRect.y = HEIGHT - 60;
		asteroidRect.width = ASTEROID_BIG_WIDTH;
		asteroidRect.height = ASTEROID_BIG_HEIGHT;
		bigAsteroidRects.add(asteroidRect);
		lastBigAsteroidTime = TimeUtils.nanoTime();
	}


	private void die() {
		Gdx.app.exit();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();

		batch.setProjectionMatrix(camera.combined);

		batch.begin();
		batch.draw(shipImage, shipRect.x, shipRect.y);
		for (Rectangle smallAsteroidRect: smallAsteroidRects)
			batch.draw(asteroidSmallIMage, smallAsteroidRect.x, smallAsteroidRect.y);
		for (Rectangle bigAsteroidRect: bigAsteroidRects)
			batch.draw(asteroidBigImage, bigAsteroidRect.x, bigAsteroidRect.y);
		batch.end();

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
			asteroidRect = iterator.next();
			asteroidRect.y -= ASTEROID_SMALL_STEP * Gdx.graphics.getDeltaTime();
			if (asteroidRect.y + ASTEROID_SMALL_HEIGHT < 0)
				iterator.remove();
			if (asteroidRect.overlaps(shipRect))
				die();
		}

		if (TimeUtils.nanoTime() - lastBigAsteroidTime > ASTEROID_BIG_SPAWN_AFTER)
			spawnBigAsteroid();

		iterator = bigAsteroidRects.iterator();
		while (iterator.hasNext()) {
			asteroidRect = iterator.next();
			asteroidRect.y -= ASTEROID_BIG_STEP * Gdx.graphics.getDeltaTime();
			if (asteroidRect.y + ASTEROID_BIG_HEIGHT < 0)
				iterator.remove();
			if (asteroidRect.overlaps(shipRect))
				die();
		}

		if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE))
		    Gdx.app.exit();
	}

	@Override
	public void dispose () {
		batch.dispose();
		shipImage.dispose();
		asteroidBigImage.dispose();
		asteroidSmallIMage.dispose();
		spaceSound.dispose();
		lazerSound.dispose();
	}
}

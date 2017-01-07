package com.nekitsgames.starinvaders;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class StarInvaders extends ApplicationAdapter {

	private OrthographicCamera camera;
	private SpriteBatch batch;

	private Texture shipImage;
	private Texture asteroidSmallIMage;
	private Texture asteroidBigImage;
	private Sound lazerSound;
	private Music spaceSound;

	private Rectangle shipRect;
	private Vector3 touchPos;

	private static final int WIDTH = 1920;
	private static final int HEIGHT = 1080;

	private static final int SHIP_WIDTH = 320;
	private static final int SHIP_HEIGHT = 320;
	private static final int SHIP_X = WIDTH / 2 - SHIP_WIDTH / 2;
	private static final int SHIP_Y = 60;

	private static final int SHIP_ONE_STEP_TOUCH = 5;
	private static final int SHIP_ONE_STEP_KEY = 300;
	
	@Override
	public void create () {
		batch = new SpriteBatch();

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1920, 1080);

		touchPos = new Vector3();

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

	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();

		batch.setProjectionMatrix(camera.combined);

		batch.begin();
		batch.draw(shipImage, shipRect.x, shipRect.y);
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

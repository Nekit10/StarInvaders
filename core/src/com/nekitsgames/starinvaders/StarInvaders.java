package com.nekitsgames.starinvaders;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class StarInvaders extends ApplicationAdapter {
	SpriteBatch batch;
	Texture shipImage;
	Texture asteroidSmallIMage;
	Texture asteroidBigImage;
	Sound lazerSound;
	Music spaceSound;
	
	@Override
	public void create () {
		batch = new SpriteBatch();

		shipImage = new Texture("ship.png");
		asteroidSmallIMage = new Texture("asteroid_small.png");
		asteroidBigImage = new Texture("asteroid_big.png");

		lazerSound = Gdx.audio.newSound(Gdx.files.internal("lazer_sound.wav"));
		spaceSound = Gdx.audio.newMusic(Gdx.files.internal("space_sound.mp3"));

		spaceSound.setLooping(true);
		spaceSound.play();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.end();
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

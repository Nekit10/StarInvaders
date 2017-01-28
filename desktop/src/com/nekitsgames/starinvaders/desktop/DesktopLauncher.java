package com.nekitsgames.starinvaders.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.nekitsgames.starinvaders.screens.MainGameScreen;
import com.nekitsgames.starinvaders.screens.StarInvaders;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Star Invaders";
		config.width = MainGameScreen.WIDTH;
		config.height = MainGameScreen.HEIGHT;
		config.fullscreen = true;
		config.allowSoftwareMode = true;
		new LwjglApplication(new StarInvaders(), config);
	}
}

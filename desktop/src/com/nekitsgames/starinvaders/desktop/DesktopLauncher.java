package com.nekitsgames.starinvaders.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.nekitsgames.starinvaders.StarInvaders;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Star Invaders";
		config.width = StarInvaders.WIDTH;
		config.height = StarInvaders.HEIGHT;
		config.fullscreen = true;
		config.allowSoftwareMode = true;
		new LwjglApplication(new StarInvaders(), config);
	}
}

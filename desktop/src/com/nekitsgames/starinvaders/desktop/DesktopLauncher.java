package com.nekitsgames.starinvaders.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.nekitsgames.starinvaders.screens.MainGameScreen;
import com.nekitsgames.starinvaders.screens.StarInvaders;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DesktopLauncher {
	public static void main (String[] arg) throws IOException {
		Properties prop = new Properties();
		prop.load(new FileInputStream("properties/main.properties"));
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = prop.getProperty("app.name") + prop.getProperty("app.version");
		config.width = Integer.parseInt(prop.getProperty("resolution.width"));
		config.height = Integer.parseInt(prop.getProperty("resolution.height"));
		config.fullscreen = true;
		config.allowSoftwareMode = true;
		new LwjglApplication(new StarInvaders(), config);
	}
}

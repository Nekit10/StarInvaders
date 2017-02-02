package com.nekitsgames.starinvaders.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.nekitsgames.starinvaders.API.logAPI.LogSystem;
import com.nekitsgames.starinvaders.API.settingsApi.SettingsSystem;
import com.nekitsgames.starinvaders.StarInvaders;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DesktopLauncher {
	public static void main (String[] arg) throws IOException {
		try {
			Properties prop = new Properties();
			SettingsSystem setings = new SettingsSystem("game", new LogSystem());
			prop.load(new FileInputStream("properties/main.properties"));
			LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
			config.title = prop.getProperty("app.name") + prop.getProperty("app.version");
			config.width = Integer.parseInt(prop.getProperty("resolution.width"));
			config.height = Integer.parseInt(prop.getProperty("resolution.height"));
			config.fullscreen = true;
			config.allowSoftwareMode = true;
			prop.load(new FileInputStream("properties/defaults.properties"));
			config.foregroundFPS = (Integer) setings.get("FPS.limit", Integer.parseInt(prop.getProperty("settings.FPS.limit")));
			config.backgroundFPS = (Integer) setings.get("FPS.limit", Integer.parseInt(prop.getProperty("settings.FPS.limit")));
			new LwjglApplication(new StarInvaders(), config);
		} catch (NullPointerException e) {

		}
	}
}

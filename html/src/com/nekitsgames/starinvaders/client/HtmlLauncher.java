package com.nekitsgames.starinvaders.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.nekitsgames.starinvaders.StarInvaders;

public class HtmlLauncher extends GwtApplication {

        @Override
        public GwtApplicationConfiguration getConfig () {
                return new GwtApplicationConfiguration(StarInvaders.WIDTH, StarInvaders.HEIGHT);
        }

        @Override
        public ApplicationListener createApplicationListener () {
                return new StarInvaders();
        }
}
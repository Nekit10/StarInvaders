package com.nekitsgames.starinvaders.classes;

import com.badlogic.gdx.math.Rectangle;

public class Asteroid {

    private AsteroidType type;
    private Rectangle rect;

    public Asteroid(AsteroidType type, int x, int y) {
        this.type = type;
        rect = new Rectangle();

        rect.x = x;
        rect.y = y;
        rect.width = type.getWidth();
        rect.height = type.getHeight();
    }

    public AsteroidType getType() {
        return type;
    }

    public Rectangle getRect() {
        return rect;
    }
}

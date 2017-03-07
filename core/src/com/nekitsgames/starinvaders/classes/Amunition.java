package com.nekitsgames.starinvaders.classes;

import com.badlogic.gdx.math.Rectangle;

public class Amunition {

    private AmunitionType type;
    private Rectangle rect;

    public Amunition(AmunitionType type, int x, int y) {
        this.type = type;
        rect = new Rectangle();

        rect.x = x;
        rect.y = y;
        rect.width = type.getWidth();
        rect.height = type.getHeight();

    }

    public AmunitionType getType() {
        return type;
    }

    public Rectangle getRect() {
        return rect;
    }
}

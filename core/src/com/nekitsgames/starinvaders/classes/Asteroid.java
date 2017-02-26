package com.nekitsgames.starinvaders.classes;

import com.badlogic.gdx.math.Rectangle;

public class Asteroid {

    private AsteroidType type;
    private Rectangle rect;
    private int texture;
    private int hp;

    public Asteroid(AsteroidType type, int x, int y, int texture) {
        this.type = type;
        this.texture = texture;
        rect = new Rectangle();

        rect.x = x;
        rect.y = y;
        rect.width = type.getWidth();
        rect.height = type.getHeight();

        hp = type.getHp();
    }

    public AsteroidType getType() {
        return type;
    }

    public Rectangle getRect() {
        return rect;
    }

    public int getTexture() {
        return texture;
    }

    public void setTexture(int texture) {
        this.texture = texture;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }
}

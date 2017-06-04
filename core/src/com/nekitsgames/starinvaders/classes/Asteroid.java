/*
* Copyright (c) 20016 - 2017, NG Tech and/or its affiliates. All rights reserved.
* GNI GPL v3 licence . Use is subject to license terms
*/

package com.nekitsgames.starinvaders.classes;

import com.badlogic.gdx.math.Rectangle;

/**
 * Asteroid class
 *
 * @author Nikita Serba
 * @version 2.0
 * @since 1.3
 */
public class Asteroid {

    private AsteroidType type;
    private Rectangle rect;
    private int texture;
    private int hp;

    /**
     * Create Asteroid
     *
     * @since 1.3
     * @param type - asteroid type
     * @param x - asteroid X position
     * @param y - asteroid Y position
     * @param texture - asteroid texture number
     */
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

    /**
     * Get asteroid type
     *
     * @since 1.3
     * @return asteroid type
     */
    public AsteroidType getType() {
        return type;
    }

    /**
     * Get asteroid rect
     *
     * @since 1.3
     * @return asteroid rect
     */
    public Rectangle getRect() {
        return rect;
    }

    /**
     * Get asteroid type
     *
     * @since 1.4
     * @return asteroid type
     */
    public int getTexture() {
        return texture;
    }

    /**
     * Set asteroid texture number
     *
     * @since 1.4
     * @param texture
     */
    public void setTexture(int texture) {
        this.texture = texture;
    }

    /**
     * Get asteroid hp
     *
     * @since 1.4
     * @return asteroid hp
     */
    public int getHp() {
        return hp;
    }

    /**
     * Set asteroid hp
     *
     * @since 1.4
     * @param hp asteroid hp
     */
    public void setHp(int hp) {
        this.hp = hp;
    }
}

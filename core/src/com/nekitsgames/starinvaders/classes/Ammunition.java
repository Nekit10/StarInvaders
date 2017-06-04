/*
* Copyright (c) 20016 - 2017, NG Tech and/or its affiliates. All rights reserved.
* GNI GPL v3 licence . Use is subject to license terms
*/

package com.nekitsgames.starinvaders.classes;

import com.badlogic.gdx.math.Rectangle;

/**
 * Ammunition class
 *
 * @author Nikita Serba
 * @version 1.0
 * @since 2.0
 */
public class Ammunition {

    private AmmunitionType type;
    private Rectangle rect;

    /**
     * Create ammunition class
     *
     * @since 2.0
     * @param type - ammunition type
     * @param x - ammunition X position
     * @param y - ammunition Y position
     */
    public Ammunition(AmmunitionType type, int x, int y) {
        this.type = type;
        rect = new Rectangle();

        rect.x = x;
        rect.y = y;
        rect.width = type.getWidth();
        rect.height = type.getHeight();

    }

    /**
     * Get ammunition type
     *
     * @since 2.0
     * @return ammunition type
     */
    public AmmunitionType getType() {
        return type;
    }

    /**
     * Get ammunition rect
     *
     * @since 2.0
     * @return ammunition rect
     */
    public Rectangle getRect() {
        return rect;
    }
}

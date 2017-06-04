/*
* Copyright (c) 20016 - 2017, NG Tech and/or its affiliates. All rights reserved.
* GNI GPL v3 licence . Use is subject to license terms
*/

package com.nekitsgames.starinvaders.classes;

import com.badlogic.gdx.graphics.Texture;

/**
 * Asteroid type class
 *
 * @author Nikita Serba
 * @version 2.0
 * @since 1.3
 */
public class AsteroidType {

    private int width;
    private int height;
    private int step;
    private long spawn_after;
    private Texture[] texture;
    private long last_spawn;
    private int damage;
    private boolean killable;
    private int hp;


    /**
     * Create asteroid type
     *
     * @since 1.3
     * @param width - asteroid with
     * @param height - asteroid height
     * @param step - asteroid step
     * @param spawn_after - asteroid spawn delay
     * @param texture - asteroid textures
     * @param last_spawn - asteroid last spawn time
     * @param image_path - asteroid image path
     * @param res_height - height
     * @param res_width - width
     * @param dif - difficulty
     * @param dif_cof - difficulty coefficient
     * @param damage - asteroid damage
     * @param killable - is killable
     * @param hp - asteroid hp
     */
    public AsteroidType(double width, double height, double step, long spawn_after, Texture[] texture, long last_spawn, String image_path, int res_height, int res_width, int dif, double dif_cof, int damage, boolean killable, int hp) {
        this.width = (int) (res_width * width);
        this.height = (int) (res_height * height);
        this.step = (int) (step * res_height * (dif + 1) * (dif == 0 ? 1 : 0.3));
        this.spawn_after = (int) (spawn_after * dif_cof / Math.ceil((dif + 1f) / 2f));
        this.texture = texture;
        this.last_spawn = last_spawn;
        this.damage = damage;
        this.killable = killable;
        this.hp = hp;
    }

    /**
     * Get asteroid with
     *
     * @since 1.3
     * @return asteroid with
     */
    public int getWidth() {
        return width;
    }

    /**
     * Get asteroid height
     *
     * @since 1.3
     * @return asteroid height
     */
    public int getHeight() {
        return height;
    }

    /**
     * Get asteroid step
     *
     * @since 1.3
     * @return asteroid height
     */
    public int getStep() {
        return step;
    }

    /**
     * Get asteroid spawn delay
     *
     * @since 1.3
     * @return asteroid spawn delay
     */
    public long getSpawn_after() {
        return spawn_after;
    }

    /**
     * Get asteroid with
     *
     * @since 1.3
     * @return asteroid last spawn time
     */
    public long getLast_spawn() {
        return last_spawn;
    }

    /**
     * Set asteroid last spawn time
     *
     * @since 1.3
     * @param last_spawn - last asteroid spawn time
     */
    public void setLast_spawn(long last_spawn) {
        this.last_spawn = last_spawn;
    }

    /**
     * Get asteroid texture
     *
     * @since 1.3
     * @deprecated since 1.4, use getTextures instead
     * @return asteroid texture
     */
    @Deprecated
    public Texture getTexture () {
        return texture[0];
    }

    /**
     * Get asteroid textures
     *
     * @since 1.4
     * @return asteroid textures
     */
    public Texture[] getTextures() {
        return texture;
    }

    /**
     * Get asteroid damage
     *
     * @since 1.4
     * @return asteroid damage
     */
    public int getDamage() {
        return damage;
    }

    /**
     * Is asteroid killable
     *
     * @since 1.4
     * @return true, if asteroid is killable
     */
    public boolean isKillable() {
        return killable;
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
}

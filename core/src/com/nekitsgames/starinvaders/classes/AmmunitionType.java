/*
* Copyright (c) 20016 - 2017, NG Tech and/or its affiliates. All rights reserved.
* GNI GPL v3 licence . Use is subject to license terms
*/

package com.nekitsgames.starinvaders.classes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.nekitsgames.starinvaders.StarInvaders;

/**
 * Ammunition type class
 *
 * @author Nikita Serba
 * @version 1.0
 * @since 2.0
 */
public class AmmunitionType {

    private int width;
    private int height;
    private int step;
    private int wait_time;
    private Texture mainTexture;
    private Sound startSound;
    private Texture explImage;
    private long last;
    private int hp_asteroid;
    private int damage;

    /**
     * Create ammunitionn type
     *
     * @since 2.0
     * @param width - ammunition width
     * @param height - ammunition height
     * @param step - ammunition step
     * @param wait_time - ammunition delay
     * @param mainTexture - ammunition texture
     * @param startSound - ammunition start sound
     * @param explImage - ammunition expl sound
     * @param image_path - ammunition image path
     * @param sound_path - ammunition aound path
     * @param game - game class
     * @param hp_asteroid - ammunition damage
     */
    public AmmunitionType(double width, double height, double step, int wait_time, String mainTexture, String startSound, String explImage, String image_path, String sound_path, StarInvaders game, int hp_asteroid, int damage) {
        this.width = (int) (width * game.WIDTH);
        this.height = (int) (height * game.HEIGHT);
        this.step = (int) (step * game.HEIGHT);
        this.wait_time = wait_time;
        this.mainTexture = new Texture(image_path + mainTexture);
        this.startSound = Gdx.audio.newSound(Gdx.files.internal(sound_path + startSound));
        this.explImage = new Texture(image_path + explImage);
        this.hp_asteroid = hp_asteroid;
        this.damage = damage;
    }

    /**
     * Get ammunition width
     *
     * @since 2.0
     * @return ammunition width
     */
    public int getWidth() {
        return width;
    }

    /**
     * Get ammunition height
     *
     * @since 2.0
     * @return ammunition height
     */
    public int getHeight() {
        return height;
    }

    /**
     * Get ammunition step
     *
     * @since 2.0
     * @return ammunition step
     */
    public int getStep() {
        return step;
    }

    /**
     * Get ammunition wait time
     *
     * @since 2.0
     * @return ammunition wait time
     */
    public int getWait_time() {
        return wait_time;
    }

    /**
     * Get ammunition main texture
     *
     * @since 2.0
     * @return ammunition texture
     */
    public Texture getMainTexture() {
        return mainTexture;
    }

    /**
     * Get ammunition star sound
     *
     * @since 2.0
     * @return ammunition start sound
     */
    public Sound getStartSound() {
        return startSound;
    }

    /**
     * Get ammunition damage
     *
     * @since 2.0
     * @return ammunition damage
     */
    public int getHp_asteroid() {
        return hp_asteroid;
    }

    /**
     * Get ammunition expl image
     *
     * @since 2.0
     * @return ammunition expl image texture
     */
    public Texture getExplImage() {
        return explImage;
    }

    /**
     * Get ammunition last start time
     *
     * @since 2.0
     * @return ammunition last start time
     */
    public long getLast() {
        return last;
    }

    /**
     * Set last start time
     *
     * @since 2.0
     * @param last - start time
     */
    public void setLast(long last) {
        this.last = last;
    }

    /**
     * Get ammunition damage
     *
     * @return ammunition damage
     * @since 2.1
     */
    public int getDamage() {
        return damage;
    }
}

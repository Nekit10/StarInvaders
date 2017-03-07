package com.nekitsgames.starinvaders.classes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.nekitsgames.starinvaders.StarInvaders;

public class AmunitionType {

    private int width;
    private int height;
    private int step;
    private int wait_time;
    private Texture mainTexture;
    private Sound startSound;
    private Texture explImage;
    private long last;
    private int hp_asteroid;

    public AmunitionType(double width, double height, double step, int wait_time, String mainTexture, String startSound, String explImage, String image_path, String sound_path, StarInvaders game, int hp_asteroid) {
        this.width = (int) (width * game.WIDTH);
        this.height = (int) (height * game.HEIGHT);
        this.step = (int) (step * game.HEIGHT);
        this.wait_time = wait_time;
        this.mainTexture = new Texture(image_path + mainTexture);
        this.startSound = Gdx.audio.newSound(Gdx.files.internal(sound_path + startSound));
        this.explImage = new Texture(image_path + explImage);
        this.hp_asteroid = hp_asteroid;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getStep() {
        return step;
    }

    public int getWait_time() {
        return wait_time;
    }

    public Texture getMainTexture() {
        return mainTexture;
    }

    public Sound getStartSound() {
        return startSound;
    }

    public int getHp_asteroid() {
        return hp_asteroid;
    }

    public Texture getExplImage() {
        return explImage;
    }

    public long getLast() {
        return last;
    }

    public void setLast(long last) {
        this.last = last;
    }
}

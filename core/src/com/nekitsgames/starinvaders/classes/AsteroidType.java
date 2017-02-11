package com.nekitsgames.starinvaders.classes;

import com.badlogic.gdx.graphics.Texture;

public class AsteroidType {

    private int width;
    private int height;
    private int step;
    private long spawn_after;
    private Texture texture;
    private long last_spawn;
    private int damage;
    private boolean killable;

    public AsteroidType(double width, double height, double step, long spawn_after, String texture, long last_spawn, String image_path, int res_height, int res_width, int dif, double dif_cof, int damage, boolean killable) {
        this.width = (int) (res_width * width);
        this.height = (int) (res_height * height);
        this.step = (int) (step * res_height * (dif + 1) * (dif == 0 ? 1 : 0.3));
        this.spawn_after = (int) (spawn_after * dif_cof / Math.ceil((dif + 1f) / 2f));
        this.texture = new Texture(image_path + texture);
        this.last_spawn = last_spawn;
        this.damage = damage;
        this.killable = killable;
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

    public long getSpawn_after() {
        return spawn_after;
    }

    public long getLast_spawn() {
        return last_spawn;
    }

    public void setLast_spawn(long last_spawn) {
        this.last_spawn = last_spawn;
    }

    public Texture getTexture() {
        return texture;
    }

    public int getDamage() {
        return damage;
    }

    public boolean isKillable() {
        return killable;
    }
}

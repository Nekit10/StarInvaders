package com.nekitsgames.starinvaders.classes;

import com.badlogic.gdx.graphics.Texture;

public class AsteroidType {

    private int width;
    private int height;
    private int step;
    private long spawn_after;
    private Texture texture;
    private long last_spawn;

    public AsteroidType(int width, int height, double step, long spawn_after, String texture, long last_spawn, String image_path, int res_height) {
        this.width = width;
        this.height = height;
        this.step = (int) (step * res_height);
        this.spawn_after = spawn_after;
        this.texture = new Texture(image_path + texture);
        this.last_spawn = last_spawn;
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
}

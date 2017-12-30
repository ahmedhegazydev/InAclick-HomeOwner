package com.example.hp.in_a_click.model;

/**
 * Created by marco.granatiero on 03/02/2015.
 */
public class GameEntity {
    public int imageResId;
    public int titleResId;
    public String title;

    public GameEntity(int imageResId, String title) {
        this.imageResId = imageResId;
        this.title = title;
    }

    public GameEntity(int imageResId, int titleResId) {
        this.imageResId = imageResId;
        this.titleResId = titleResId;
    }
}

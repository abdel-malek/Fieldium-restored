package com.bluebrand.fieldiumadmin.Model;

import java.io.Serializable;

/**
 * Created by r.desouki on 12/22/2016.
 */
public class Game implements Serializable {
    private int game_type_id;
    private String name;
    private Image image;
    private int minimum_duration;
    private int increament_factor;


    public int getMinimum_duration() {
        return minimum_duration;
    }

    public void setMinimum_duration(int minimum_duration) {
        this.minimum_duration = minimum_duration;
    }

    public int getIncreament_factor() {
        return increament_factor;
    }

    public void setIncreament_factor(int increament_factor) {
        this.increament_factor = increament_factor;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public int getGame_type_id() {
        return game_type_id;
    }

    public void setGame_type_id(int game_type_id) {
        this.game_type_id = game_type_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}

package com.bluebrand.fieldium.core.model;

import java.io.Serializable;

/**
 * Created by Player on 12/22/2016.
 */
public class Game implements Serializable {
    private int id;
    private String name;
    private String imageUrl;
    private boolean isSelected=false;
    private int minimum_duration;
    private int increament_factor;


    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

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
}

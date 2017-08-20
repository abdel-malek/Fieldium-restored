package com.bluebrand.fieldium.core.model;

import java.io.Serializable;


/**
 * Created by Player on 5/29/2016.
 */
public class City implements Serializable {

    private int id;
    private String name;
    private String imageUrl;
//    private String foreign_name;

    public City() {
    }

    public City(int id, String name) {
        this.id = id;
        this.name = name;

    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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


    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this.getId() == ((City) o).getId())
            return true;
        else return false;
    }
}

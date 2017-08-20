package com.bluebrand.fieldiumadmin.Model;

import java.io.Serializable;

/**
 * Created by r.desouki on 12/15/2016.
 */
public class Amenity implements Serializable {
    private int field_amenity_id;
    private int amenity_id;
    private int field_id;
    private String name;
    private Image image;

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public int getField_amenity_id() {
        return field_amenity_id;
    }

    public void setField_amenity_id(int field_amenity_id) {
        this.field_amenity_id = field_amenity_id;
    }

    public int getAmenity_id() {
        return amenity_id;
    }

    public void setAmenity_id(int amenity_id) {
        this.amenity_id = amenity_id;
    }

    public int getField_id() {
        return field_id;
    }

    public void setField_id(int field_id) {
        this.field_id = field_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

package com.bluebrand.fieldiumadmin.Model;

import java.io.Serializable;

/**
 * Created by r.desouki on 12/22/2016.
 */
public class Game implements Serializable {
    private int game_type_id;
    private String name;
    private Image image;


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
}

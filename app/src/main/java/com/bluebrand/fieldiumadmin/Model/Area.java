package com.bluebrand.fieldiumadmin.Model;

import java.io.Serializable;

/**
 * Created by r.desouki on 1/11/2017.
 */
public class Area implements Serializable {
    private int id;
    private String name;

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
}

package com.bluebrand.fieldiumadmin.Model;

import java.io.Serializable;

/**
 * Created by r.desouki on 8/24/2016.
 */
public class RefusedMsg implements Serializable{
    int id;
    String text;

    public RefusedMsg() {

    }

    public RefusedMsg(int id, String text) {
        this.id = id;
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}

package com.bluebrand.fieldiumadmin.Model;

import java.io.Serializable;

/**
 * Created by User on 6/16/2016.
 */
public class Image implements Serializable {
    private int id ;
    private String url ;
    private int index ;
    private String name ;
    private String type;


    public final static String MAIN="MAIN";
    public final static String FRONT="FRONT ";
    public final static String REAR="REAR";
    public final static String RIGHT="RIGHT";
    public  final static String LEFT="LEFT";
    public  final static String CONSOLE="CONSOLE";
    public  final static String INTERNAL="INTERNAL";



    public int getType() {
        if(this.type.equals(MAIN))
            return 1;
        if(this.type.equals(LEFT))
            return 2;
        if(this.type.equals(RIGHT))
            return 3;
        if(this.type.equals(FRONT))
            return 4;
        if(this.type.equals(REAR))
            return 5;
        if(this.type.equals(CONSOLE))
            return 6;
        if(this.type.equals(INTERNAL))
            return 7;
        else
            return -1;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

package com.bluebrand.fieldiumadmin.API;

/**
 * Created by farah on 4/22/16.
 */
public enum APIFormat {

    JSON ,
    XML ,
    HTML ,
    RSS;
    @Override
    public String toString (){
        switch (this) {

            case JSON :
                return "json";
            case XML :
                return "xml";
            case HTML :
                return "html";
            case RSS :
                return "rss";
            default :
                return "";
        }
    }

}
package com.bluebrand.fieldium.core.model;

import java.io.Serializable;

/**
 * Created by Player on 1/12/2017.
 */
public class AvailableTime implements Serializable {
    private String start;
    private String end;


    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {

        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }
}

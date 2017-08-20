package com.bluebrand.fieldium.core.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Developer19 on 3/6/2017.
 */

public class Availability implements Serializable{
    private List<AvailableTime> availableTimes;
    private List<AvailableTime> busyTimes;

    public Availability() {
    availableTimes=new ArrayList<>();
        busyTimes=new ArrayList<>();
    }

    public List<AvailableTime> getAvailableTimes() {
        return availableTimes;
    }

    public void setAvailableTimes(List<AvailableTime> availableTimes) {
        this.availableTimes = availableTimes;
    }

    public List<AvailableTime> getBusyTimes() {
        return busyTimes;
    }

    public void setBusyTimes(List<AvailableTime> busyTimes) {
        this.busyTimes = busyTimes;
    }
}

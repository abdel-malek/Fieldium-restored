package com.bluebrand.fieldiumadmin.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by r.desouki on 12/15/2016.
 */
public class Field implements Serializable {
    private int field_id;
    private String name;
    private String phone;
    private int company_id;
    private int hour_rate;
    private Calendar open_time;
    private String open_time_string;
    private Calendar close_time;
    private String close_time_string;
    private String description;
    private int area_x;
    private int area_y;
    private int max_capacity;
    private List<Amenity> amenities;
    private List<Game>  games;
    private List<Image> images=new ArrayList<>();
    private String Location;
    private boolean AutoConfirm;
    private  ArrayList<Integer>ChildsFieldsIds;

    @Override
    public String toString() {
        return name;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getCompany_id() {
        return company_id;
    }

    public void setCompany_id(int company_id) {
        this.company_id = company_id;
    }

    public int getHour_rate() {
        return hour_rate;
    }

    public void setHour_rate(int hour_rate) {
        this.hour_rate = hour_rate;
    }

    public Calendar getOpen_time() {
        return open_time;
    }

    public void setOpen_time(Calendar open_time) {
        this.open_time = open_time;
    }

    public Calendar getClose_time() {
        return close_time;
    }

    public void setClose_time(Calendar close_time) {
        this.close_time = close_time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getArea_x() {
        return area_x;
    }

    public void setArea_x(int area_x) {
        this.area_x = area_x;
    }

    public int getArea_y() {
        return area_y;
    }

    public void setArea_y(int area_y) {
        this.area_y = area_y;
    }

    public int getMax_capacity() {
        return max_capacity;
    }

    public void setMax_capacity(int max_capacity) {
        this.max_capacity = max_capacity;
    }

    public List<Amenity> getAmenities() {
        return amenities;
    }

    public void setAmenities(List<Amenity> amenities) {
        this.amenities = amenities;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public String getOpen_time_string() {
        return open_time_string;
    }

    public void setOpen_time_string(String open_time_string) {
        this.open_time_string = open_time_string;
    }

    public String getClose_time_string() {
        return close_time_string;
    }

    public void setClose_time_string(String close_time_string) {
        this.close_time_string = close_time_string;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public boolean isAutoConfirm() {
        return AutoConfirm;
    }

    public void setAutoConfirm(boolean autoConfirm) {
        AutoConfirm = autoConfirm;
    }

    public List<Game> getGames() {
        return games;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }

    public ArrayList<Integer> getChildsFieldsIds() {
        return ChildsFieldsIds;
    }

    public void setChildsFieldsIds(ArrayList<Integer> childsFieldsIds) {
        ChildsFieldsIds = childsFieldsIds;
    }
}

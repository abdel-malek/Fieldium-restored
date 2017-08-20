package com.bluebrand.fieldium.core.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Farah Etmeh on 4/15/16.
 */
public class Field /*extends Company*/ implements Serializable {
    private String name;
    private int id;
    private String phone;
    private BigDecimal hourRate;
    private String openTime;
    private String closeTime;
    private String description;
    private int maxCapacity;
    private double area_x;
    private double area_y;
    private BigDecimal area = new BigDecimal(0);
    private ArrayList<Image> images;
    private ArrayList<Amenity> amenities;
    private ArrayList<Game> games;
    private List<String> availableTimes;
    private Company company;


    private ArrayList<String> barCodes;
    private ArrayList<String> compatiblePrinters;
    private String code;
    private String color;
    private int pageYield;

    public Field() {
        this.images=new ArrayList<>();
        this.amenities=new ArrayList<>();
        this.games=new ArrayList<>();
        this.availableTimes=new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public BigDecimal getHourRate() {
        return hourRate;
    }

    public void setHourRate(BigDecimal hourRate) {
        this.hourRate = hourRate;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public String getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(String closeTime) {
        this.closeTime = closeTime;
    }

    public double getArea_x() {
        return area_x;
    }

    public void setArea_x(double area_x) {
        this.area_x = area_x;
    }

    public double getArea_y() {
        return area_y;
    }

    public void setArea_y(double area_y) {
        this.area_y = area_y;
    }

    public BigDecimal calculateArea(double x, double y) {
        area = (new BigDecimal(x)).multiply(new BigDecimal(y));
        return area;
    }

    public ArrayList<Amenity> getAmenities() {
        return amenities;
    }

    public void setAmenities(ArrayList<Amenity> amenities) {
        this.amenities = amenities;
    }

    public List<String> getAvailableTimes() {
        return availableTimes;
    }

    public void setAvailableTimes(List<String> availableTimes) {
        this.availableTimes = availableTimes;
    }
//    public BigDecimal getOldPrice() {
//        return oldPrice;
//    }
//
//    public void setOldPrice(BigDecimal oldPrice) {
//        this.oldPrice = oldPrice;
//    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


/*    public String getParentsPath() {
        String path = "";
        if (parentCatgory != null) {
            return parentCatgory.getParentsPath();
        } else
            return "";
    }*/

    public ArrayList<Image> getImages() {
        return images;
    }

    public void setImages(ArrayList<Image> images) {

        this.images = images;
    }

    public ArrayList<String> getBarCodes() {
        return barCodes;
    }

    public void setBarCodes(ArrayList<String> barCodes) {
        this.barCodes = barCodes;
    }

    public ArrayList<String> getCompatiblePrinters() {
        return compatiblePrinters;
    }

    public void setCompatiblePrinters(ArrayList<String> compatiblePrinters) {
        this.compatiblePrinters = compatiblePrinters;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public ArrayList<Game> getGames() {
        return games;
    }

    public void setGames(ArrayList<Game> games) {
        this.games = games;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}

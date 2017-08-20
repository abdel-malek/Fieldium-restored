package com.bluebrand.fieldium.core.model;

import android.view.View;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Player on 12/22/2016.
 */
public class Offer implements Serializable {
    private int id;
    private int valid;
    private int type;
    private String code;
    private String from_hour;
    private String to_hour;
    private String start_date;
    private String expiry_date;
    private int set_of_minutes;
    private int booked_hours;
    private int value;
    private String description;
    private ArrayList<Company> companies;
    private ArrayList<Game> games;
    private String message;
    private View.OnClickListener requestBtnClickListener;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getValid() {
        return valid;
    }

    public void setValid(int valid) {
        this.valid = valid;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getExpiry_date() {
        return expiry_date;
    }

    public void setExpiry_date(String expiry_date) {
        this.expiry_date = expiry_date;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFrom_hour() {
        return from_hour;
    }

    public void setFrom_hour(String from_hour) {
        this.from_hour = from_hour;
    }

    public String getTo_hour() {
        return to_hour;
    }

    public void setTo_hour(String to_hour) {
        this.to_hour = to_hour;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageUrl() {
        return from_hour;
    }

    public void setImageUrl(String imageUrl) {
        this.from_hour = imageUrl;
    }

    public ArrayList<Company> getCompanies() {
        return companies;
    }

    public void setCompanies(ArrayList<Company> companies) {
        this.companies = companies;
    }


    public View.OnClickListener getRequestBtnClickListener() {
        return requestBtnClickListener;
    }

    public void setRequestBtnClickListener(View.OnClickListener requestBtnClickListener) {
        this.requestBtnClickListener = requestBtnClickListener;
    }

    public Offer() {
        companies = new ArrayList<>();
        games = new ArrayList<>();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<Game> getGames() {
        return games;
    }

    public void setGames(ArrayList<Game> games) {
        this.games = games;
    }

    public int getSet_of_minutes() {
        return set_of_minutes;
    }

    public void setSet_of_minutes(int set_of_minutes) {
        this.set_of_minutes = set_of_minutes;
    }

    public int getBooked_hours() {
        return booked_hours;
    }

    public void setBooked_hours(int booked_hours) {
        this.booked_hours = booked_hours;
    }
}

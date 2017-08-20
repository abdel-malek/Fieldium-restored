package com.bluebrand.fieldiumadmin.Model;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Created by r.desouki on 12/14/2016.
 */
public class Reservation implements Serializable{
    private int booking_id;
    private int field_id;
    private String Player_Name;
    private String Player_Phone;
    private int state_id;
    private String creation_date;
    private Calendar date;
    private String date_string;
    private Calendar start;
    private String start_string;
    private String end_string;
    private int duration;
    private int total;
    private String notes;
    private int manually;
    private String company_name;
    private String field_name;
    private boolean isAutoConfirm;
    private String gameName;

    final static int PENDING=1;
    final static int APPROVED=2;

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public boolean isAutoConfirm() {
        return isAutoConfirm;
    }

    public void setAutoConfirm(boolean autoConfirm) {
        isAutoConfirm = autoConfirm;
    }

    public boolean isPending(){
        return state_id == 1;
    }

    public boolean isApproved(){
        return state_id == 2;
    }

    public String getField_name() {
        return field_name;
    }

    public void setField_name(String field_name) {
        this.field_name = field_name;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getPlayer_Phone() {
        return Player_Phone;
    }

    public void setPlayer_Phone(String player_Phone) {
        Player_Phone = player_Phone;
    }

    public int getBooking_id() {
        return booking_id;
    }

    public void setBooking_id(int booking_id) {
        this.booking_id = booking_id;
    }

    public int getField_id() {
        return field_id;
    }

    public void setField_id(int field_id) {
        this.field_id = field_id;
    }

    public String getPlayer_Name() {
        return Player_Name;
    }

    public void setPlayer_Name(String Player_Name) {
        this.Player_Name = Player_Name;
    }


    public int getState_id() {
        return state_id;
    }

    public void setState_id(int state_id) {
        this.state_id = state_id;
    }

    public String getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(String creation_date) {
        this.creation_date = creation_date;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public Calendar getStart() {
        return start;
    }

    public void setStart(Calendar start) {
        this.start = start;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getManually() {
        return manually;
    }

    public void setManually(int manually) {
        this.manually = manually;
    }

    public String getDate_string() {
        return date_string;
    }

    public void setDate_string(String date_string) {
        this.date_string = date_string;
    }

    public String getStart_string() {
        return start_string;
    }

    public void setStart_string(String start_string) {
        this.start_string = start_string;
    }

    public String getEnd_string() {
        return end_string;
    }

    public void setEnd_string(String end_string) {
        this.end_string = end_string;
    }
}

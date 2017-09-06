package com.bluebrand.fieldium.core.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Player on 1/8/2017.
 */
public class Booking implements Serializable {
    private int id;
    private BookingStatus state;
    private String date;
    private String startTime;
    private String duration;
    private BigDecimal total = new BigDecimal(0);
    private String cancelReason;
    private String note;
    private Field field;
    private List<AvailableTime> availableTimes;
    private Game game;
    private String subTotal;
    private Voucher voucher=new Voucher();
    private String arCurrency;
    private String enCurrency;


    public Booking() {
        availableTimes = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BookingStatus getState() {
        return state;
    }

    public void setState(BookingStatus state) {
        this.state = state;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public BigDecimal getTotal() {

        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public List<AvailableTime> getAvailableTimes() {
        return availableTimes;
    }

    public void setAvailableTimes(List<AvailableTime> availableTimes) {
        this.availableTimes = availableTimes;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }

    public Voucher getVoucher() {
        return voucher;
    }

    public void setVoucher(Voucher voucher) {
        this.voucher = voucher;
    }

    public String getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(String subTotal) {
        this.subTotal = subTotal;
    }

    public String getArCurrency() {
        return arCurrency;
    }

    public void setArCurrency(String arCurrency) {
        this.arCurrency = arCurrency;
    }

    public String getEnCurrency() {
        return enCurrency;
    }

    public void setEnCurrency(String enCurrency) {
        this.enCurrency = enCurrency;
    }
}

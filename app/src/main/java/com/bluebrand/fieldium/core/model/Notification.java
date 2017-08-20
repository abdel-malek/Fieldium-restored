package com.bluebrand.fieldium.core.model;

/**
 * Created by b.srour on 7/20/2016.
 */
public class Notification {
    private String message_text;
    private int notification_type;
    private String message_body;
    private String notification_time;
    private Booking booking;
    public Notification() {
        this.message_text ="";
        this.message_body = "";
    }

    public String getMessage_text() {
        return message_text;
    }

    public void setMessage_text(String message_text) {
        this.message_text = message_text;
    }

    public String getMessage_body() {
        return message_body;
    }

    public void setMessage_body(String message_body) {
        this.message_body = message_body;
    }

    public String getNotification_time() {
        return notification_time;
    }

    public void setNotification_time(String notification_time) {
        this.notification_time = notification_time;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public int getNotification_type() {
        return notification_type;
    }

    public void setNotification_type(int notification_type) {
        this.notification_type = notification_type;
    }
}

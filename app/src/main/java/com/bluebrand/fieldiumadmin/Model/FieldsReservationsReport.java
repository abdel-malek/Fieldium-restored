package com.bluebrand.fieldiumadmin.Model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by r.desouki on 1/31/2017.
 */
public class FieldsReservationsReport {
    private static int total=0;
    List<ReportItem>fieldReservationsReports=new ArrayList<>();

    public static int getTotal() {
        return total;
    }

    public static void setTotal(int total) {
        FieldsReservationsReport.total = total;
    }

    public List<ReportItem> getFieldReservationsReports() {
        return fieldReservationsReports;
    }

    public void setFieldReservationsReports(List<ReportItem> fieldReservationsReports) {
        this.fieldReservationsReports = fieldReservationsReports;
    }

    public void newFieldReservationsReport(int field_id, String field_name, int booking_id, String string_date, String string_start, int duration, int player_id, String player_name, Boolean manually, int total){
        FieldReservationsReport fieldReservationsReport=new FieldReservationsReport(field_id, field_name, booking_id, string_date, string_start, duration, player_id, player_name, manually, total);
        fieldReservationsReports.add(fieldReservationsReport);
    }

    public class FieldReservationsReport extends ReportItem {
        private int field_id;
        private String field_name;
        private int booking_id;
        private Calendar date;
        private String string_date;
        private Calendar start;
        private String string_start;
        private int duration;
        private int player_id;
        private String player_name;
        private Boolean manually;
        private int total;

        public FieldReservationsReport(int field_id, String field_name, int booking_id, String string_date, String string_start, int duration, int player_id, String player_name, Boolean manually, int total) {
            this.field_id = field_id;
            this.field_name = field_name;
            this.booking_id = booking_id;
            this.string_date = string_date;
            this.string_start = string_start;
            this.duration = duration;
            this.player_id = player_id;
            this.player_name = player_name;
            this.manually = manually;
            this.total = total;
        }

        public int getField_id() {
            return field_id;
        }

        public void setField_id(int field_id) {
            this.field_id = field_id;
        }

        public String getField_name() {
            return field_name;
        }

        public void setField_name(String field_name) {
            this.field_name = field_name;
        }

        public int getBooking_id() {
            return booking_id;
        }

        public void setBooking_id(int booking_id) {
            this.booking_id = booking_id;
        }

        public Calendar getDate() {
            return date;
        }

        public void setDate(Calendar date) {
            this.date = date;
        }

        public String getString_date() {
            return string_date;
        }

        public void setString_date(String string_date) {
            this.string_date = string_date;
        }

        public Calendar getStart() {
            return start;
        }

        public void setStart(Calendar start) {
            this.start = start;
        }

        public String getString_start() {
            return string_start;
        }

        public void setString_start(String string_start) {
            this.string_start = string_start;
        }

        public int getDuration() {
            return duration;
        }

        public void setDuration(int duration) {
            this.duration = duration;
        }

        public int getPlayer_id() {
            return player_id;
        }

        public void setPlayer_id(int player_id) {
            this.player_id = player_id;
        }

        public String getPlayer_name() {
            return player_name;
        }

        public void setPlayer_name(String player_name) {
            this.player_name = player_name;
        }

        public Boolean getManually() {
            return manually;
        }

        public void setManually(Boolean manually) {
            this.manually = manually;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }
    }
}

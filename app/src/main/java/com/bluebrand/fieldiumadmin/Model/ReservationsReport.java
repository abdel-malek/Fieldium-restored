package com.bluebrand.fieldiumadmin.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by r.desouki on 1/31/2017.
 */
public class ReservationsReport {
    private static int all_bookings_number;
    private static int all_total;
    private List<ReportItem> reservationReportList=new ArrayList<>();

    public static int getAll_bookings_number() {
        return all_bookings_number;
    }

    public static void setAll_bookings_number(int all_bookings_number) {
        ReservationsReport.all_bookings_number = all_bookings_number;
    }

    public static int getAll_total() {
        return all_total;
    }

    public static void setAll_total(int all_total) {
        ReservationsReport.all_total = all_total;
    }


    public List<ReportItem> getReservationReportList() {
        return reservationReportList;
    }

    public void setReservationReportList(List<ReportItem> reservationReportList) {
        this.reservationReportList = reservationReportList;
    }

    public void newReservationReport(int id, String name, int booking_num, int total){
        ReservationReport reservationReport=new ReservationReport(id, name, booking_num, total);
        reservationReportList.add(reservationReport);
    }


    public class ReservationReport extends ReportItem {
        private int field_id;
        private int bookings_number;
        private int total;
        private String field_name;

        ReservationReport(int id,String name,int booking_num,int total){
            this.field_id=id;
            this.field_name=name;
            this.bookings_number=booking_num;
            this.total=total;
        }


        public int getField_id() {
            return field_id;
        }

        public void setField_id(int field_id) {
            this.field_id = field_id;
        }

        public int getBookings_number() {
            return bookings_number;
        }

        public void setBookings_number(int bookings_number) {
            this.bookings_number = bookings_number;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public String getField_name() {
            return field_name;
        }

        public void setField_name(String field_name) {
            this.field_name = field_name;
        }
    }
}

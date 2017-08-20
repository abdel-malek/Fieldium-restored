package com.bluebrand.fieldiumadmin.Parser;

import com.tradinos.core.network.TradinosParser;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.bluebrand.fieldiumadmin.Model.Reservation;

/**
 * Created by r.desouki on 5/11/2016.
 */
public class ReservationParser implements TradinosParser<Reservation> {
    public ReservationParser() {
    }

    @Override
    public Reservation Parse(String jsonText) throws JSONException {
        Reservation reservation;
        JSONObject jsonObject = new JSONObject(jsonText);
        reservation = Parse(jsonObject);
        return reservation;
    }

    public Reservation Parse(JSONObject jsonObject) throws JSONException {
        Reservation reservation = new Reservation();
        reservation.setAutoConfirm(jsonObject.getInt("auto_confirm")==1?true:false);
        reservation.setCompany_name(jsonObject.getString("company_name"));
        reservation.setField_name(jsonObject.getString("field_name"));
        reservation.setBooking_id(jsonObject.getInt("booking_id"));
        reservation.setField_id(jsonObject.getInt("field_id"));
        reservation.setPlayer_Name(jsonObject.getString("player_name"));
        reservation.setPlayer_Phone(jsonObject.getString("player_phone"));
        reservation.setState_id(jsonObject.getInt("state_id"));
        reservation.setCreation_date(jsonObject.getString("creation_date"));

        java.text.DateFormat  DateFormat= new java.text.SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        java.text.DateFormat  StartFormat= new java.text.SimpleDateFormat("hh:mm a", Locale.ENGLISH);
        SimpleDateFormat _24HourSDF = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH);
        SimpleDateFormat _12HourSDF = new SimpleDateFormat("hh:mm a", Locale.ENGLISH);

        try {
            Date _24HourDt = _24HourSDF.parse(jsonObject.getString("start"));
            Calendar startCalendar = Calendar.getInstance();
            startCalendar.setTime(StartFormat.parse(_12HourSDF.format(_24HourDt)));
            reservation.setStart(startCalendar);
            reservation.setStart_string(_12HourSDF.format(_24HourDt));

            Calendar dateCalendar = Calendar.getInstance();
            dateCalendar.setTime(DateFormat.parse(jsonObject.getString("date")));
            reservation.setDate(dateCalendar);
            reservation.setDate_string(jsonObject.getString("date"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        reservation.setDuration(jsonObject.getInt("duration"));
        Calendar endTime = (Calendar) reservation.getStart().clone();
        endTime.add(Calendar.HOUR,reservation.getDuration());
        reservation.setEnd_string(StartFormat.format(endTime.getTime()));
        reservation.setTotal(jsonObject.getInt("total"));
        reservation.setNotes(jsonObject.getString("notes"));
        reservation.setManually(jsonObject.getInt("manually"));
        reservation.setGameName(jsonObject.getString("game_type_name"));
        return reservation;
    }
}

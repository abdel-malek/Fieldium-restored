package com.bluebrand.fieldium;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.bluebrand.fieldium.core.model.Booking;

/**
 * Created  on 8/1/17.
 */
public class FieldiumApplication extends Application {

    private Booking booking;
    private int game_id;//saving selected game id in home screen for get fields by company
    SharedPreferences mPrefs;
    private  int mYear,mMonth,mDay;
    public FieldiumApplication(){

//        setBooking(new Order());
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mPrefs = getSharedPreferences("booking", Context.MODE_PRIVATE);
//        mPrefs = getSharedPreferences("int_year", Context.MODE_PRIVATE);
//        mPrefs = getSharedPreferences("int_month", Context.MODE_PRIVATE);
//        mPrefs = getSharedPreferences("int_day", Context.MODE_PRIVATE);
        setBooking(new Booking());
        setGame_id(0);

    }

    public int getGame_id() {
        game_id=mPrefs.getInt("game_id",0);
        return game_id;
    }

    public void setGame_id(int game_id) {
        this.game_id = game_id;
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        prefsEditor.putInt("game_id", game_id);
        prefsEditor.commit();
    }

    public Booking getBooking() {
        if(booking ==null) {
            Gson gson = new Gson();
            String json = mPrefs.getString("booking", "");
            booking = gson.fromJson(json, Booking.class);
        }
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson  gson = new Gson();
        String json = gson.toJson(booking);
        prefsEditor.putString("booking", json);
        prefsEditor.commit();

    }


}

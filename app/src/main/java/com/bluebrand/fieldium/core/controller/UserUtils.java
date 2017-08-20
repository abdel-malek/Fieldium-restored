package com.bluebrand.fieldium.core.controller;

import android.content.Context;
import android.content.SharedPreferences;

import static java.util.concurrent.TimeUnit.*;

import com.bluebrand.fieldium.core.model.City;
import com.bluebrand.fieldium.core.model.Country;
import com.bluebrand.fieldium.core.model.Game;
import com.bluebrand.fieldium.core.model.Image;
import com.google.gson.Gson;
import com.bluebrand.fieldium.core.model.Player;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Farah Etmeh on 5/10/16.
 */
public class UserUtils {
    Context context;

    public UserUtils(Context context) {
        this.context = context;
    }

    public static UserUtils getInstance(Context context) {
        return new UserUtils(context);
    }

    SharedPreferences mPrefs;

    public void Save(Player customer) {
        SharedPreferences preferences = context.getSharedPreferences("LoginUser", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("id", customer.getId());
        editor.putString("name", customer.getName());
        editor.putString("email", customer.getEmail());
        editor.putString("serverId", customer.getServerId());
        editor.putString("user_token", customer.getToken());
        editor.putString("address", customer.getAddress());
        editor.putString("phone", customer.getPhone());
        editor.putString("image", customer.getProfileImage().getName());
        editor.putString("lang", customer.getLanguage());

        Gson gson = new Gson();
        String games = gson.toJson(customer.getGames());
        editor.putString("games", games);
        editor.commit();

    }


    public Boolean IsLogged() {
        SharedPreferences preferences = context.getSharedPreferences("LoginUser", Context.MODE_PRIVATE);
        if (preferences == null || preferences.getInt("id", 0) == 0)
            return false;
        else
            return true;
    }


    public Player Get() {
        SharedPreferences preferences = context.getSharedPreferences("LoginUser", Context.MODE_PRIVATE);
        if (preferences == null || preferences.getInt("id", 0) == 0)
            return null;
        else {
            Player player = new Player();
            player.setId(preferences.getInt("id", 0));
            player.setName(preferences.getString("name", ""));
            player.setEmail(preferences.getString("email", ""));
            // player.setPassword(preferences.getString("password", ""));
            player.setPhone(preferences.getString("phone", ""));
            player.setToken(preferences.getString("user_token", "123"));
            player.setAddress(preferences.getString("address", ""));
            player.setLanguage(preferences.getString("lang", "en"));
            player.setServerId(this.getServerId());
            Gson gson = new Gson();
            String games = preferences.getString("games", "");

            Type gameType = new TypeToken<ArrayList<Game>>() {
            }.getType();
            player.setGames((ArrayList<Game>) gson.fromJson(games, gameType));
            Image image = new Image();
            image.setName(preferences.getString("image", ""));
            player.setProfileImage(image);
            return player;
        }
    }

    public Boolean IsRegistered() {
        SharedPreferences preferences = context.getSharedPreferences("phone", Context.MODE_PRIVATE);
        if (preferences == null || !preferences.getBoolean("registered", false))
            return false;
        else
            return true;
    }

    public String GetPhone() {
        SharedPreferences preferences = context.getSharedPreferences("phone", Context.MODE_PRIVATE);
        if (preferences == null)
            return null;
        else
            return preferences.getString("phone", "");

    }

    public void SignOut() {
        SharedPreferences mSharedPreference = context.getSharedPreferences("LoginUser", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mSharedPreference.edit();
        editor.clear();
        editor.commit();
    }

    public void SavePhone(String phone) {
        SharedPreferences preferences = context.getSharedPreferences("phone", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("phone", phone);
        editor.putBoolean("registered", true);
        editor.commit();
    }

    public Boolean IsRegisterCompleted() {
        SharedPreferences preferences = context.getSharedPreferences("complete_register", Context.MODE_PRIVATE);
        if (preferences == null || !preferences.getBoolean("complete_register", false))
            return false;
        else
            return true;
    }

    public void CompleteRegister() {
        SharedPreferences preferences = context.getSharedPreferences("complete_register", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("complete_register", true);
        editor.commit();
    }

    public void saveServerId(String server_id) {
        SharedPreferences preferences = context.getSharedPreferences("serverId", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("serverId", server_id);
        editor.commit();
    }

    public String getServerId() {
        SharedPreferences preferences = context.getSharedPreferences("serverId", Context.MODE_PRIVATE);
        if (preferences == null)
            return null;
        else
            return preferences.getString("serverId", "");
    }

    public void DeletePhone() {
        SharedPreferences mSharedPreference = context.getSharedPreferences("phone", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mSharedPreference.edit();
        editor.clear();
        editor.commit();
    }

    public void saveCountry(Country country) {
        SharedPreferences preferences = context.getSharedPreferences("country", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        String mCountry = gson.toJson(country);
        editor.putString("country", mCountry);
        editor.commit();
    }

    public Country getCountry() {
        SharedPreferences preferences = context.getSharedPreferences("country", Context.MODE_PRIVATE);
        if (preferences == null)
            return new Country();
        else {
            Gson gson = new Gson();
            String country = preferences.getString("country", "");

            Type countryType = new TypeToken<Country>() {
            }.getType();
//            gson.toJson(foo, fooType);
//            gson.fromJson(json, fooType);
            return (Country) gson.fromJson(country, countryType);
          /*  return preferences.getString("country", "");*/
        }
    }

    public void saveCity(City city) {
        SharedPreferences preferences = context.getSharedPreferences("city", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        String mCountry = gson.toJson(city);
        editor.putString("city", mCountry);
        editor.commit();
    }

    public City getCity() {
        SharedPreferences preferences = context.getSharedPreferences("city", Context.MODE_PRIVATE);
        if (preferences == null)
            return new City();
        else {
            Gson gson = new Gson();
            String city = preferences.getString("city", "");

            Type countryType = new TypeToken<City>() {
            }.getType();
            return (City) gson.fromJson(city, countryType);
        }
    }

    public void SetCounterStartCalendarForNewCode(Calendar currentTime) {
        SharedPreferences preferences = context.getSharedPreferences("counterStartCalendar", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
//        currentTime.add(Calendar.MINUTE,15);
        Gson gson = new Gson();
        String calendar = gson.toJson(currentTime);
        editor.putString("calendar", calendar);
        editor.commit();
    }

    public Calendar GetCounterStartCalendarForNewCode() {
        SharedPreferences preferences = context.getSharedPreferences("counterStartCalendar", Context.MODE_PRIVATE);
        if (preferences == null)
            return Calendar.getInstance();
        else {
            Gson gson = new Gson();
            String calender = preferences.getString("calendar", "");
            if (calender.equals(""))
                return Calendar.getInstance();
            Type calType = new TypeToken<Calendar>() {
            }.getType();
            return (Calendar) gson.fromJson(calender, calType);
        }
    }

    public void SetCounterStartTimeForNewCode(long date) {
        SharedPreferences preferences = context.getSharedPreferences("counterTime", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong("counterTime", date);
        editor.commit();
    }

    public Long GetCounterStartTimeForNewCode() {
        SharedPreferences preferences = context.getSharedPreferences("counterTime", Context.MODE_PRIVATE);
//        long MAX_DURATION = MILLISECONDS.convert(15, MINUTES);
        if (preferences == null)
            return Long.valueOf("0");
        else
            return preferences.getLong("counterTime", Long.valueOf("0"));

    }

    public void DeleteCounter() {
        SharedPreferences mSharedPreference = context.getSharedPreferences("counterTime", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mSharedPreference.edit();
        editor.clear();
        editor.commit();
    }

    public void DeleteCalendarCounter() {
        SharedPreferences mSharedPreference = context.getSharedPreferences("counterStartCalendar", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mSharedPreference.edit();
        editor.clear();
        editor.commit();
    }

  /*  public void SetBooleanDateSelected(boolean date) {
        SharedPreferences preferences = context.getSharedPreferences("booleanDateSelected", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("booleanDateSelected", date);
        editor.commit();
    }
    public void SetBooleanTimeSelected(boolean time) {
        SharedPreferences preferences = context.getSharedPreferences("booleanTimeSelected", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("booleanTimeSelected", time);
        editor.commit();
    }

    public boolean GetBooleanDateSelected() {
        SharedPreferences preferences = context.getSharedPreferences("booleanDateSelected", Context.MODE_PRIVATE);
        if (preferences == null)
            return true;
        else
            return preferences.getBoolean("booleanDateSelected",true);

    }
    public boolean GetBooleanTimeSelected() {
        SharedPreferences preferences = context.getSharedPreferences("booleanTimeSelected", Context.MODE_PRIVATE);
        if (preferences == null)
            return true;
        else
            return preferences.getBoolean("booleanTimeSelected",true);

    }*/
}

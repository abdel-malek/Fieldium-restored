package com.bluebrand.fieldium.core.parser;

import com.bluebrand.fieldium.core.model.Availability;
import com.bluebrand.fieldium.core.model.AvailableTime;
import com.bluebrand.network.TradinosParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Player on 12/22/2016.
 */
public class AvailableTimeParser implements TradinosParser<Availability> {
    @Override
    public Availability Parse(String text) throws JSONException {
        JSONObject jsonObject = new JSONObject(text);
        return Parse(jsonObject);
    }

    public Availability Parse(JSONObject jsonObject) throws JSONException {
        Availability availability = new Availability();
        JSONArray availableTimesArray = jsonObject.getJSONArray("available");
        JSONArray busyTimesArray = jsonObject.getJSONArray("busy");
        ArrayList<AvailableTime> availableTimes = new ArrayList<>();
        ArrayList<AvailableTime> busyTimes = new ArrayList<>();
        if (availableTimesArray != null) {
            for (int i = 0; i < availableTimesArray.length(); i++) {
                AvailableTime availableTime = new AvailableTime();

                availableTime.setStart(convertTime(availableTimesArray.getJSONObject(i).optString("start")));
                availableTime.setEnd(convertTime(availableTimesArray.getJSONObject(i).optString("end")));
                availableTimes.add(availableTime);
            }
        }
        availability.setAvailableTimes(availableTimes);

        if (busyTimesArray != null) {
            for (int i = 0; i < busyTimesArray.length(); i++) {
                AvailableTime availableTime = new AvailableTime();
                availableTime.setStart(convertTime(busyTimesArray.getJSONObject(i).optString("start")));
                availableTime.setEnd(convertTime(busyTimesArray.getJSONObject(i).optString("end")));
                busyTimes.add(availableTime);
            }
        }
        availability.setBusyTimes(busyTimes);

        return availability;
    }

    public String convertTime(String time) {
        String formattedTime = "";
        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm aa", Locale.ENGLISH);
        SimpleDateFormat formatter1 = new SimpleDateFormat("kk:mm", Locale.ENGLISH);
        try {
            formattedTime = formatter.format(formatter1.parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formattedTime;
    }
}

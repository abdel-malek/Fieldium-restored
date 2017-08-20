package com.bluebrand.fieldium.core.parser;

import com.bluebrand.fieldium.core.model.City;
import com.bluebrand.network.TradinosParser;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by Player on 12/22/2016.
 */
public class AreasParser implements TradinosParser<ArrayList<City>> {
    @Override
    public ArrayList<City> Parse(String text) throws JSONException {
        JSONArray jsonArray = new JSONArray(text);
        return Parse(jsonArray);
    }

    public ArrayList<City> Parse(JSONArray jsonarray) throws JSONException {
        ArrayList<City> cities = new ArrayList<>();
        if (jsonarray != null) {
            for (int i = 0; i < jsonarray.length(); i++) {
                if (jsonarray.getJSONObject(i).optInt("active")!=0) {
                    City city = new City();
                    city.setId(jsonarray.getJSONObject(i).getInt("area_id"));
                    city.setName(jsonarray.getJSONObject(i).optString("name"));
                    cities.add(city);
                }
            }
        }
        return cities;
    }
}

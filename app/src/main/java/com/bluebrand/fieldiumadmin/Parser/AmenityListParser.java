package com.bluebrand.fieldiumadmin.Parser;

import com.tradinos.core.network.TradinosParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import com.bluebrand.fieldiumadmin.Model.Amenity;

/**
 * Created by r.desouki on 12/15/2016.
 */
public class AmenityListParser implements TradinosParser<List<Amenity>> {

    public List<Amenity> Parse(String jsonText) throws JSONException {
        List<Amenity> amenities = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(jsonText);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            amenities.add(new  AmenityParser().Parse(jsonObject));
        }
        return amenities ;
    }
}
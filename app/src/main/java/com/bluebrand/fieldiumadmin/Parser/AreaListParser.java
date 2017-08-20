package com.bluebrand.fieldiumadmin.Parser;

import com.tradinos.core.network.TradinosParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import com.bluebrand.fieldiumadmin.Model.Area;

/**
 * Created by r.desouki on 1/11/2017.
 */
public class AreaListParser implements TradinosParser<List<Area>> {

    public List<Area> Parse(String jsonText) throws JSONException {
        List<Area> areas = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(jsonText);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            areas.add(new  AreaParser().Parse(jsonObject));
        }
        return areas ;
    }
}

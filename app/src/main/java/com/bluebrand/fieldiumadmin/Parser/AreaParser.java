package com.bluebrand.fieldiumadmin.Parser;

import com.tradinos.core.network.TradinosParser;

import org.json.JSONException;
import org.json.JSONObject;

import com.bluebrand.fieldiumadmin.Model.Area;

/**
 * Created by r.desouki on 1/11/2017.
 */
public class AreaParser implements TradinosParser<Area> {
    @Override
    public Area Parse(String text) throws JSONException {
        JSONObject jsonObject = new JSONObject(text);
        return Parse(jsonObject);
    }

    public Area Parse(JSONObject jsonObject) throws JSONException {
        Area area = new Area();
        area.setId(jsonObject.getInt("area_id"));
        area.setName(jsonObject.getString("name"));

        return area;
    }
}

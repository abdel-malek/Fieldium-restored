package com.bluebrand.fieldium.core.parser;

import com.bluebrand.fieldium.core.model.Amenity;
import com.bluebrand.network.TradinosParser;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Player on 12/22/2016.
 */
public class AmenityParser implements TradinosParser<Amenity> {
    @Override
    public Amenity Parse(String text) throws JSONException {
        JSONObject jsonObject = new JSONObject(text);
        return  Parse(jsonObject) ;
    }
    public Amenity Parse (JSONObject jsonObject) throws JSONException {
        Amenity amenity = new Amenity() ;
        amenity.setId(jsonObject.getInt("amenity_id"));
        amenity.setImageUrl(jsonObject.optString("image_url"));
        amenity.setName(jsonObject.optString("name"));
        return  amenity ;
    }
}

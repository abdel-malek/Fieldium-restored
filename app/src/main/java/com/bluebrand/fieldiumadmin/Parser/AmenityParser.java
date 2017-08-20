package com.bluebrand.fieldiumadmin.Parser;

import com.tradinos.core.network.TradinosParser;

import org.json.JSONException;
import org.json.JSONObject;

import com.bluebrand.fieldiumadmin.Model.Amenity;
import com.bluebrand.fieldiumadmin.Model.Image;

/**
 * Created by r.desouki on 12/15/2016.
 */
public class AmenityParser  implements TradinosParser<Amenity> {
    public AmenityParser() {
    }

    @Override
    public Amenity Parse(String jsonText) throws JSONException {
        Amenity amenity;
        JSONObject jsonObject = new JSONObject(jsonText);
        amenity = Parse(jsonObject);
        return amenity;
    }

    public Amenity Parse(JSONObject jsonObject) throws JSONException {
        Amenity amenity=new Amenity();
        amenity.setAmenity_id(jsonObject.getInt("amenity_id"));
        amenity.setName(jsonObject.getString("name"));

        if (!jsonObject.getString("image").equals("null")) {
            Image image = new Image() ;
            image.setUrl(jsonObject.optString("image_url"));
            amenity.setImage(image);
        }

        return amenity;
    }
}
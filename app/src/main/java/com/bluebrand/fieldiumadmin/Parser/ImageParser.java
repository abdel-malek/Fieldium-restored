package com.bluebrand.fieldiumadmin.Parser;

import com.tradinos.core.network.TradinosParser;

import org.json.JSONException;
import org.json.JSONObject;

import com.bluebrand.fieldiumadmin.Model.Image;

/**
 */
public class ImageParser implements TradinosParser<Image> {

    @Override
    public Image Parse(String data) throws JSONException{
        JSONObject jsonObject = new JSONObject(data);
        return  Parse(jsonObject) ;
    }


    public Image Parse(JSONObject jsonObject) throws JSONException {
        Image image = new Image() ;
        try {
        image.setId(jsonObject.getInt("image_id"));
        image.setUrl(jsonObject.optString("image_url"));
        }
        catch (JSONException e){

        }
        return  image ;
    }
}

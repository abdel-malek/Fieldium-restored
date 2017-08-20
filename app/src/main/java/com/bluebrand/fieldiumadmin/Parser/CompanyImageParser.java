package com.bluebrand.fieldiumadmin.Parser;

import com.tradinos.core.network.TradinosParser;

import org.json.JSONException;
import org.json.JSONObject;

import com.bluebrand.fieldiumadmin.Model.Image;

/**
 */
public class CompanyImageParser implements TradinosParser<Image> {

    @Override
    public Image Parse(String data) throws JSONException{
        JSONObject jsonObject = new JSONObject(data);
        return  Parse(jsonObject) ;
    }


    public Image Parse(JSONObject jsonObject) throws JSONException {
        Image image = new Image() ;
        image.setName(jsonObject.getString("image_name"));
        return  image ;
    }
}

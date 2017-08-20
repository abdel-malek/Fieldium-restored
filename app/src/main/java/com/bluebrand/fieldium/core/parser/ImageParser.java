package com.bluebrand.fieldium.core.parser;

import com.bluebrand.fieldium.core.model.Image;
import com.bluebrand.network.TradinosParser;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Farah Etmeh Etmeh on 4/15/16.
 */
public class ImageParser implements TradinosParser<Image> {

    @Override
    public Image Parse(String data) throws JSONException{
        JSONObject jsonObject = new JSONObject(data);
        return  Parse(jsonObject) ;
    }

    public Image Parse (JSONObject jsonObject) throws JSONException {
        Image image = new Image() ;
        image.setId(jsonObject.optInt("image_id"));
        image.setUrl(jsonObject.optString("image_url"));
        image.setName(jsonObject.optString("name"));
        return  image ;
    }

}

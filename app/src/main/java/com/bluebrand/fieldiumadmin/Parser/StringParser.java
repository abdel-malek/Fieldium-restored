package com.bluebrand.fieldiumadmin.Parser;

import com.tradinos.core.network.TradinosParser;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by r.desouki on 5/24/2016.
 */

public class StringParser implements TradinosParser<String> {
    public StringParser() {
    }

    @Override
    public String Parse(String jsonText) throws JSONException {
        String string;
        JSONObject jsonObject = new JSONObject(jsonText);
        string = Parse(jsonObject);
        return string;
    }

    public String Parse(JSONObject jsonObject) throws JSONException {
        String string="";
        try {
            string=jsonObject.getString("message");
        }catch (JSONException e){

        }

        return string;
    }
}


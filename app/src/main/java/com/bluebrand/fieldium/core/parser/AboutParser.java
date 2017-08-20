package com.bluebrand.fieldium.core.parser;

import com.bluebrand.fieldium.core.model.AboutModel;
import com.bluebrand.network.TradinosParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

/**
 * Created by Player on 9/18/2016.
 */
public class AboutParser implements TradinosParser {
    @Override
    public AboutModel Parse(String data) throws JSONException {
        JSONObject jsonObject = new JSONObject(data);
        AboutModel aboutModel = new AboutModel();
        aboutModel.setEmail(jsonObject.optString("email"));
        aboutModel.setPhone(jsonObject.optString("phone"));
        aboutModel.setMobile(jsonObject.optString("mobile"));
        return aboutModel;
    }
}

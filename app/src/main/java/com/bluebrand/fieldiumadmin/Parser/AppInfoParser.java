package com.bluebrand.fieldiumadmin.Parser;

import com.bluebrand.fieldiumadmin.Model.AppInfo;
import com.tradinos.core.network.TradinosParser;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by farah Etmeh on 4/15/16.
 */
public class AppInfoParser implements TradinosParser<AppInfo> {

    @Override
    public AppInfo Parse(String jsonText) throws JSONException {
        AppInfo appInfo;
        JSONObject jsonObject = new JSONObject(jsonText);
        appInfo = Parse(jsonObject);
        return appInfo;
    }



    public AppInfo Parse(JSONObject jsonObject) throws JSONException{
        AppInfo appInfo = new AppInfo () ;
        appInfo.setPhone(jsonObject.getString("phone"));
        appInfo.setMobile(jsonObject.getString("mobile"));
        appInfo.setEmail(jsonObject.getString("email"));

        return appInfo;
    }
}

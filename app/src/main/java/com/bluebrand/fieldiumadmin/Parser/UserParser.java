package com.bluebrand.fieldiumadmin.Parser;

import com.tradinos.core.network.TradinosParser;
import org.json.JSONException;
import org.json.JSONObject;


import com.bluebrand.fieldiumadmin.Model.User;

/**
 * Created by farah Etmeh on 4/15/16.
 */
public class UserParser implements TradinosParser<User> {

    @Override
    public User Parse(String jsonText) throws JSONException {
        User user;
        JSONObject jsonObject = new JSONObject(jsonText);
        user = Parse(jsonObject);
        return user;
    }



    public User Parse(JSONObject jsonObject) throws JSONException{
        User user = new User () ;
        user.setId(jsonObject.getInt("user_id"));
        user.setRole_id(jsonObject.getInt("role_id"));
        user.setName(jsonObject.getString("name"));
        user.setUsername(jsonObject.getString("username"));
        user.setPassword(jsonObject.getString("password"));
        user.setProfile_picture(jsonObject.getString("profile_picture"));
        user.setCompany_id(jsonObject.getInt("company_id"));
        user.setCurrency(jsonObject.getString("en_currency_symbol"));
        return user;
    }
}

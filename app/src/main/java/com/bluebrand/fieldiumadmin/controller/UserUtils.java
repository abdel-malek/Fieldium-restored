package com.bluebrand.fieldiumadmin.controller;

import android.content.Context;
import android.content.SharedPreferences;


import com.bluebrand.fieldiumadmin.Model.User;

/**
 * Created by malek on 5/10/16.
 */
public class UserUtils {
    Context context ;
    public UserUtils(Context context) {
        this.context = context ;
    }

    public static UserUtils getInstance(Context  context) {
        return new UserUtils(context);
    }


    public void Save( User customer) {
        SharedPreferences preferences = context.getSharedPreferences("LoginUser", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("id", customer.getId());
        editor.putInt("company_id", customer.getCompany_id());
        editor.putString("name", customer.getName());
        editor.putString("username", customer.getUsername());
        editor.putString("password", customer.getPassword());
        editor.putString("profile_picture", customer.getProfile_picture());
        editor.putString("currency", customer.getCurrency());

        editor.commit();
    }


    public Boolean IsLogged() {
        SharedPreferences preferences = context.getSharedPreferences("LoginUser", Context.MODE_PRIVATE);
        return !(preferences == null || preferences.getInt("id", -1) == -1);
    }


    public User Get() {
        SharedPreferences preferences = context.getSharedPreferences("LoginUser", Context.MODE_PRIVATE);
        if (preferences == null || preferences.getInt("id",-1)==-1)
            return null;
        else {
            User user = new User();
            user.setId(preferences.getInt("id", -1));
            user.setId(preferences.getInt("company_id", -1));
            user.setName(preferences.getString("name", ""));
            user.setUsername(preferences.getString("username", ""));
            user.setPassword(preferences.getString("password", ""));
            user.setProfile_picture(preferences.getString("profile_picture", ""));
            user.setCurrency(preferences.getString("currency", ""));

            return user;
        }
    }




    public void SignOut() {
        SharedPreferences mSharedPreference = context.getSharedPreferences("LoginUser", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mSharedPreference.edit();
        editor.clear();
        editor.commit();
    }
}
